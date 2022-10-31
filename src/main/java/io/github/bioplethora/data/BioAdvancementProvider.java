package io.github.bioplethora.data;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.registry.BPEntities;
import io.github.bioplethora.registry.BPItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.criterion.*;
import net.minecraft.data.AdvancementProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;
import org.apache.commons.lang3.Validate;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class BioAdvancementProvider extends AdvancementProvider {

    // TODO: 01/02/2022 W.I.P. Datagenerator
    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(Bioplethora.MOD_ID, "textures/block/frostbite_metal_core_block.png");
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
    private final DataGenerator datagen;

    public BioAdvancementProvider(DataGenerator generatorIn, ExistingFileHelper exFileHelper) {
        super(generatorIn, exFileHelper);
        this.datagen = generatorIn;
    }

    /**
     * List every advancements here.
     */
    public void register(Consumer<Advancement> t) {
        // STARTUP
        Advancement bioStartup = registerAdvancement("bioplethora_startup", FrameType.TASK, BPItems.BIOPEDIA.get()).addCriterion("startup",
                PositionTrigger.Instance.located(LocationPredicate.inDimension(RegistryKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation("overworld"))))).save(t, id("bioplethora_startup"));

        // ENTITY KILL
        Advancement frostbite_golemKill = entityKillAdvancement(BPEntities.FROSTBITE_GOLEM, BPItems.FROSTBITE_GOLEM_SPAWN_EGG, FrameType.GOAL, bioStartup, t);
        Advancement altyrusKill = entityKillAdvancement(BPEntities.ALTYRUS, BPItems.ALTYRUS_SPAWN_EGG, FrameType.CHALLENGE, frostbite_golemKill, t);

        Advancement alphemKill = entityKillAdvancement(BPEntities.ALPHEM, BPItems.ALPHEM_SPAWN_EGG, FrameType.TASK, bioStartup, t);
        Advancement alphemKingKill = entityKillAdvancement(BPEntities.ALPHEM_KING, BPItems.ALPHEM_KING_SPAWN_EGG, FrameType.CHALLENGE, alphemKill, t);

        // ENTITY TAME
        Advancement peaguinTame = entityTameAdvancement(BPEntities.PEAGUIN, BPItems.PEAGUIN_SPAWN_EGG, FrameType.TASK, bioStartup, t);
        Advancement trapjawTame = entityTameAdvancement(BPEntities.TRAPJAW, BPItems.TRAPJAW_SPAWN_EGG, FrameType.GOAL, bioStartup, t);

        // CUSTOM TRIGGERS
        Advancement grylynenSummon = customTriggerAdvancement("grylynen_summon", BPItems.GREEN_GRYLYNEN_CRYSTAL, FrameType.TASK, bioStartup, t);
    }

    //==================================================
    //            ADVANCEMENT FORMATS
    //==================================================
    public Advancement entityKillAdvancement(Supplier<? extends EntityType<?>> entity, RegistryObject<Item> iconItem, FrameType achievementLevel, Advancement parent, Consumer<Advancement> consumer) {
        ResourceLocation registryName = entity.get().getRegistryName();

        return registerAdvancement( registryName.getPath() + "_kill", achievementLevel, iconItem.get())
                .parent(parent).addCriterion( registryName.getPath(), KilledTrigger.Instance.playerKilledEntity(EntityPredicate.Builder.entity()
                        .of(registryName))).save(consumer, id(registryName.getPath() + "_kill"));
    }

    public Advancement entityTameAdvancement(Supplier<? extends EntityType<?>> entity, RegistryObject<Item> iconItem, FrameType achievementLevel, Advancement parent, Consumer<Advancement> consumer) {
        ResourceLocation registryName = entity.get().getRegistryName();

        return registerAdvancement( registryName.getPath() + "_tame", achievementLevel, iconItem.get())
                .parent(parent).addCriterion(registryName.getPath(), TameAnimalTrigger.Instance.tamedAnimal(EntityPredicate.Builder.entity()
                        .of(registryName).build())).save(consumer, id(registryName.getPath() + "_tame"));
    }

    public Advancement customTriggerAdvancement(String name, RegistryObject<Item> iconItem, FrameType achievementLevel, Advancement parent, Consumer<Advancement> consumer) {
        return registerAdvancement(name, achievementLevel, iconItem.get())
                .parent(parent).addCriterion(name, new ImpossibleTrigger.Instance()).save(consumer, id(name));
    }

    //================================================================
    //             OTHER ADVANCEMENT GENERATOR HELPERS
    //================================================================
    private static Path getPath(Path pathIn, Advancement advancementIn) {
        return pathIn.resolve("data/" + advancementIn.getId().getNamespace() + "/advancements/" + advancementIn.getId().getPath() + ".json");
    }

    private static String id(String save) {
        return Bioplethora.MOD_ID + ":" + save;
    }

    @Override
    public void run(DirectoryCache cache) {
        Path path = this.datagen.getOutputFolder();
        Set<ResourceLocation> set = Sets.newHashSet();
        Consumer<Advancement> consumer = (advancement) -> {
            if (!set.add(advancement.getId())) {
                throw new IllegalStateException("Duplicate advancement " + advancement.getId());
            } else {
                Path path1 = getPath(path, advancement);
                try {
                    IDataProvider.save(GSON, cache, advancement.deconstruct().serializeToJson(), path1);
                } catch (IOException e) {
                    Bioplethora.LOGGER.error("Couldn't save advancement {}", path1, e);
                }
            }
        };
        this.register(consumer);
    }

    private Advancement.Builder registerAdvancement(String name, FrameType type, IItemProvider... items) {
        Validate.isTrue(items.length > 0);
        return Advancement.Builder.advancement().display(items[0],
                new TranslationTextComponent("advancements.bioplethora." + name + ".title"),
                new TranslationTextComponent("advancements.bioplethora." + name + ".desc"),
                BACKGROUND_TEXTURE, type, true, true, false);
    }
}
