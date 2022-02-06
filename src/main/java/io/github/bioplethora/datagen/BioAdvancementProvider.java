package io.github.bioplethora.datagen;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.registry.BioplethoraEntities;
import io.github.bioplethora.registry.BioplethoraItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.criterion.*;
import net.minecraft.data.AdvancementProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.commons.lang3.Validate;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;
import java.util.function.Consumer;

public class BioAdvancementProvider extends AdvancementProvider {

    // TODO: 01/02/2022 W.I.P. Datagenerator
    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(Bioplethora.MOD_ID, "textures/block/bellophite_core_block.png");
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
    private final DataGenerator datagen;

    public BioAdvancementProvider(DataGenerator generatorIn) {
        super(generatorIn);
        this.datagen = generatorIn;
    }

    public void register(Consumer<Advancement> t) {
        // STARTUP
        Advancement bioStartup = registerAdvancement("bioplethora_startup", FrameType.TASK, BioplethoraItems.BIOPEDIA.get()).addCriterion("startup",
                PositionTrigger.Instance.located(LocationPredicate.inDimension(RegistryKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation("overworld"))))).save(t, id("bioplethora_startup"));

        // ENTITIES
        Advancement bellophgolemKill = registerAdvancement("bellophgolem_kill", FrameType.GOAL, BioplethoraItems.BELLOPHGOLEM_SPAWN_EGG.get())
                .parent(bioStartup).addCriterion("bellophgolem", KilledTrigger.Instance.playerKilledEntity(EntityPredicate.Builder.entity()
                        .of(BioplethoraEntities.BELLOPHGOLEM.getId()))).save(t, id("bellophgolem_kill"));

        Advancement alphemKill = registerAdvancement("alphem_kill", FrameType.GOAL, BioplethoraItems.ALPHEM_SPAWN_EGG.get())
                .parent(bioStartup).addCriterion("alphem", KilledTrigger.Instance.playerKilledEntity(EntityPredicate.Builder.entity()
                        .of(BioplethoraEntities.ALPHEM.getId()))).save(t, id("alphem_kill"));


        // ITEMS
        Advancement bellophiteObtain = registerAdvancement("bellophite_obtain", FrameType.TASK, BioplethoraItems.BELLOPHITE.get())
                .parent(bellophgolemKill).addCriterion("bellophite",
                InventoryChangeTrigger.Instance.hasItems(BioplethoraItems.BELLOPHITE.get())).save(t, id("bellophgolem_obtain"));

        Advancement bellophiteArrowObtain = registerAdvancement("bellophite_obtain", FrameType.TASK, BioplethoraItems.BELLOPHITE.get())
                .parent(bellophgolemKill).addCriterion("bellophite",
                InventoryChangeTrigger.Instance.hasItems(BioplethoraItems.BELLOPHITE.get())).save(t, id("bellophgolem_obtain"));
    }

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
