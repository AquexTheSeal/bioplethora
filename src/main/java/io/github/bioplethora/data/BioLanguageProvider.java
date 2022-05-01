package io.github.bioplethora.data;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.enchantments.AntibioEnchantment;
import io.github.bioplethora.registry.*;
import io.github.bioplethora.registry.worldgen.BPBiomes;
import net.minecraft.block.AbstractSignBlock;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.potion.Effect;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.fml.RegistryObject;
import org.apache.commons.lang3.text.WordUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public class BioLanguageProvider extends LanguageProvider {

    //==========================================================
    //                     VARIABLES
    //================================  ==========================

    List<Supplier<? extends EntityType<?>>> manualEntityList = new ArrayList<>();
    List<Supplier<? extends Block>> manualBlockList = new ArrayList<>();
    List<Supplier<? extends Item>> manualItemList = new ArrayList<>();

    //==========================================================
    //                   CONSTRUCTORS
    //==========================================================

    public BioLanguageProvider(DataGenerator gen, String modid, String locale) {
        super(gen, modid, locale);
    }

    //==========================================================
    //               TRANSLATION REGISTERER
    //==========================================================

    @Override
    protected void addTranslations() {
        addItems(BPItems.ITEMS.getEntries());
        addBlocks(BPBlocks.BLOCKS.getEntries());
        addEntityTypes(BPEntities.ENTITIES.getEntries());
        addEffects(BPEffects.EFFECTS.getEntries());
        addEnchantments(BPEnchantments.ENCHANTMENTS.getEntries());
        addBiomes(BPBiomes.BIOMES.getEntries());

        this.addDeathMessages();
        this.addSoundSubtitles();
        this.addTooltipHelper();

        this.addEnchDescIntegration();
    }

    //=======================================================
    //            MANUAL TRANSLATION DATAGENS
    //=======================================================

    public void manualEntityTranslations() {
        addManualEntity(BPEntities.ALTYRUS_SUMMONING, "Altyrus Summoning Core");
    }

    public void manualBlockTranslations() {
    }

    public void addDeathMessages() {
        add(deathMessageFormat("indirectCastration", 0), "%s was castrated into shreds by %s");
        add(deathMessageFormat("indirectCastration", 1), "%s was brutally obliterated by %s");
        add(deathMessageFormat("indirectCastration", 2), "%s was blasted out by %s");

        add(deathMessageFormat("helioSlashed", 0), "%s was sliced up by %s");
        add(deathMessageFormat("helioSlashed", 1), "%s was fatally slashed by %s");
        add(deathMessageFormat("helioSlashed", 2), "%s was cut into pieces by %s");

        add(deathMessageFormat("antibio", 0), "%s was fired up by %s");
        add(deathMessageFormat("antibio", 1), "%s was painfully poisoned by %s");
        add(deathMessageFormat("antibio", 2), "%s was vanished by %s");

        add(deathMessageFormat("reinforcedFleignarite", 0), "%s got his armor shattered by %s");
        add(deathMessageFormat("reinforcedFleignarite", 1), "%s was brutally smashed into pieces by %s");
        add(deathMessageFormat("reinforcedFleignarite", 2), "The skull of %s was brutally smashed up by %s");
    }

    public void addSoundSubtitles() {
        add(mobSubtitlesFormat(BPEntities.CREPHOXL, "idle"), "Crephoxl Grunts");
        add(mobSubtitlesFormat(BPEntities.CREPHOXL, "hurt"), "Crephoxl Hurts");
        add(mobSubtitlesFormat(BPEntities.CREPHOXL, "death"), "Crephoxl Dies");

        add(mobSubtitlesFormat(BPEntities.BELLOPHGOLEM, "idle"), "Bellophgolem Grunts");
        add(mobSubtitlesFormat(BPEntities.BELLOPHGOLEM, "hurt"), "Bellophgolem Hurts");
        add(mobSubtitlesFormat(BPEntities.BELLOPHGOLEM, "death"), "Bellophgolem Dies");

        add(mobSubtitlesFormat(BPEntities.HELIOBLADE, "idle"), "Helioblade Grunts");
        add(mobSubtitlesFormat(BPEntities.HELIOBLADE, "hurt"), "Helioblade Hurts");
        add(mobSubtitlesFormat(BPEntities.HELIOBLADE, "death"), "Helioblade Dies");

        add(mobSubtitlesFormat(BPEntities.ALTYRUS, "idle"), "Altyrus Groans");
        add(mobSubtitlesFormat(BPEntities.ALTYRUS, "charge"), "Altyrus Charges");

        add(mobSubtitlesFormat(BPEntities.ALPHEM, "step"), "Alphem Walks");

        add(mobSubtitlesFormat(BPEntities.ALPHEM_KING, "roar"), "Alphem King Roars");

        add(mobSubtitlesFormat(BPEntities.MYLIOTHAN, "idle"), "Myliothan Cries");
    }

    public void addTooltipHelper() {
        // Singular Item Tooltips
        addSkilledItem(BPItems.CREPHOXL_HAMMER, "dysfunction", "Increased damage for enemies with 50 or more health. Enemies attacked with this weapon gets debuffs for a very short duration.");
        addSkilledItem(BPItems.CREPHOXL_HAMMER, "deathsweep", "Hitting an entity while crouching will deal 80% of this tool's base damage to nearby entities within a 2-block radius. 1.5 second cooldown.");
        addSkilledItem(BPItems.CREPHOXL_HAMMER, "aerial_shockwave", "On right click on the ground, this weapon creates a damaging shockwave on block right-click position, dealing 9 damage to nearby entities & sending them flying into the air. 3-second cooldown.");

        addSkilledItem(BPItems.BELLOPHITE_ARROW, "sharper_tip", "The Bellophgolem arrow deals more damage than the regular arrow.");
        addSkilledItem(BPItems.BELLOPHITE_ARROW, "core_energy", "On hit (block or entity), the arrow deals magic damages and debuffs all nearby mobs.");

        addSkilledItem(BPItems.BELLOPHITE_SHIELD, "recovery_bulwark", "The Bellophite shield has more durability than the regular shield, and it gives a resistance and regeneration effect on using.");
        addSkilledItem(BPItems.BELLOPHITE_SHIELD, "core_impulse", "This shield stacks core points every attack this shield blocks. Once the core points stacks up to 4, this shield releases a shockwave, damaging all nearby entities, then resets the core points back to 0. Letting go of the shield resets all the core points.");

        addManualSkilledItem(BPItems.ARBITRARY_BALLISTA, "heavy_duty_ballista", "Heavy-Duty Ballista", "This giant crossbow greatly enhances the shooting projectile's speed and power. When shooting, the crossbow causes a small explosion that recoils the user backwards.");

        addSkilledItem(BPItems.NANDBRIC_SHORTSWORD, "toxin_rush", "Upon right-clicking at a mob that the crosshair is pointing at, the shortsword takes control over the player's mind, causing the player to rush toward the target and strike it with force upon impact. Best used by holding down right-click.");

        // Armor/Weapon set tooltips
        addManualItemSkilledTooltip("fleignarite_weapon", "gooey_stun", "The fleignarite scales allows this weapon to slowly regenerate it's durability. When the target is hurt using this item, the fleignarite scales used for this item causes a quick stun that gives nausea and slowness for a short duration.");

        addManualItemSkilledTooltip("reinforced_fleignarite_weapon", "deadly_blow", "The fleignarite scales allows this weapon to slowly regenerate it's durability. This weapon can cause a very damaging blow that deals extra damage which pierces armor and gives large amounts of debuffs every now and then thanks to it's bellophite attachment.");

        add("item.bioplethora.ecoharmless_spawn_egg.desc", "ECOHARMLESS");
        add("item.bioplethora.plethoneutral_spawn_egg.desc", "PLETHONEUTRAL");
        add("item.bioplethora.dangerum_spawn_egg.desc", "DANGERUM");
        add("item.bioplethora.hellsent_spawn_egg.desc", "HELLSENT");
        add("item.bioplethora.elderia_spawn_egg.desc", "ELDERIA");
    }

    public void addEnchDescIntegration() {
        addAntibioEnchantmentDesc(BPEnchantments.ANTIBIO_ECOHARMLESS);
        addAntibioEnchantmentDesc(BPEnchantments.ANTIBIO_PLETHONEUTRAL);
        addAntibioEnchantmentDesc(BPEnchantments.ANTIBIO_DANGERUM);
        addAntibioEnchantmentDesc(BPEnchantments.ANTIBIO_HELLSENT);
        addAntibioEnchantmentDesc(BPEnchantments.ANTIBIO_ELDERIA);
    }

    //=========================================================
    //               TRANSLATION KEY FORMATS
    //=========================================================

    public String deathMessageFormat(String death, int deathVariant) {
        return "death.attack." + death + ".indirect_" + deathVariant;
    }

    public String mobSubtitlesFormat(Supplier<? extends EntityType<?>> entitySupplier, String action) {
        EntityType<?> entity = entitySupplier.get();
        return subtitlesFormat(entity.getRegistryName().getPath(), action);
    }

    public String subtitlesFormat(String subtitle1, String subtitle2) {
        return "subtitles." + Bioplethora.MOD_ID + "." + subtitle1 + "." + subtitle2;
    }

    public String tooltipSkillHelper(RegistryObject<Item> item, String skill) {
        return "item." + Bioplethora.MOD_ID + "." + item.getId().getPath() + "." + skill + ".skill";
    }

    public String tooltipSkillDescHelper(RegistryObject<Item> item, String skillDesc) {
        return "item." + Bioplethora.MOD_ID + "." + item.getId().getPath() + "." + skillDesc + ".desc";
    }

    public String manualItemSkillHelper(String itemId, String skill) {
        return "item." + Bioplethora.MOD_ID + "." + itemId + "." + skill + ".skill";
    }

    public String manualItemSkillDescHelper(String itemId, String skillDesc) {
        return "item." + Bioplethora.MOD_ID + "." + itemId + "." + skillDesc + ".desc";
    }

    public String enchDescIntegrationFormat(RegistryObject<Enchantment> enchantment) {
        return "enchantment." + Bioplethora.MOD_ID + "." + enchantment.getId().getPath() + ".desc";
    }

    //===============================================================
    //               TRANSLATION RESULT TEXT FORMATS
    //===============================================================

    public String capitalizedSpacedRegistryObject(RegistryObject<?> registryObject) {
        return capitalizedSpacedText(registryObject.getId().getPath());
    }

    public String capitalizedSpacedText(String string) {
        return WordUtils.capitalize(string.replace("_", " "));
    }

    public String antibioEnchDescIntegration(RegistryObject<Enchantment> enchantment) {
        return "Deals more damage against entities classified as " +
                WordUtils.capitalize(enchantment.getId().getPath().replace("antibio_", ""));
    }

    //=============================================================
    //                     LANGUAGE EASER
    //=============================================================

    public void addSkilledItem(RegistryObject<Item> item, String skillId, String description) {
        add(tooltipSkillHelper(item, skillId), capitalizedSpacedText(skillId));
        add(tooltipSkillDescHelper(item, skillId), description);
    }

    public void addManualSkilledItem(RegistryObject<Item> item, String skillId, String skillTranslation, String description) {
        add(tooltipSkillHelper(item, skillId), skillTranslation);
        add(tooltipSkillDescHelper(item, skillId), description);
    }

    public void addManualItemSkilledTooltip(String itemId, String skillId, String description) {
        add(manualItemSkillHelper(itemId, skillId), capitalizedSpacedText(skillId));
        add(manualItemSkillDescHelper(itemId, skillId), description);
    }

    public void addAntibioEnchantmentDesc(RegistryObject<Enchantment> enchantment) {
        add(enchDescIntegrationFormat(enchantment), antibioEnchDescIntegration(enchantment));
    }

    //==================================================================
    //                   MANUAL TRANSLATION HELPERS
    //==================================================================

    public void addManualEntity(Supplier<? extends EntityType<?>> entitySupplier, String customName) {
        manualEntityList.add(entitySupplier);
        addEntityType(entitySupplier, customName);
    }

    public void addManualBlock(Supplier<? extends Block> blockSupplier, String customName) {
        manualBlockList.add(blockSupplier);
        addBlock(blockSupplier, customName);
    }

    public void addManualItem(Supplier<? extends Item> itemSupplier, String customName) {
        manualItemList.add(itemSupplier);
        addItem(itemSupplier, customName);
    }

    //=========================================================
    //            AUTOMATIC TRANSLATION DATAGENS
    //=========================================================

    public void addItems(Collection<RegistryObject<Item>> items) {
        for (RegistryObject<Item> item : items) {
            if (!manualItemList.contains(item)) {
                addItem(item, capitalizedSpacedRegistryObject(item));
            }
        }
    }

    public void addBlocks(Collection<RegistryObject<Block>> blocks) {
        manualBlockTranslations();

        for (RegistryObject<Block> block : blocks) {
            if (!manualBlockList.contains(block) && !(block.get() instanceof AbstractSignBlock)) {
                addBlock(block, capitalizedSpacedRegistryObject(block));
            }
        }
    }

    public void addEntityTypes(Collection<RegistryObject<EntityType<?>>> entities) {
        manualEntityTranslations();

        for (RegistryObject<EntityType<?>> entity : entities) {
            if (!manualEntityList.contains(entity)) {
                addEntityType(entity, capitalizedSpacedRegistryObject(entity));
            }
        }
    }

    public void addEffects(Collection<RegistryObject<Effect>> effects) {
        for (RegistryObject<Effect> effect : effects) {
            addEffect(effect, capitalizedSpacedRegistryObject(effect));
        }
    }

    public void addEnchantments(Collection<RegistryObject<Enchantment>> enchantments) {
        for (RegistryObject<Enchantment> enchantment : enchantments) {

            String antibioSpecial = WordUtils.capitalize(enchantment.getId().getPath().replace("_", ": "));

            if (enchantment.get() instanceof AntibioEnchantment) {
                addEnchantment(enchantment, antibioSpecial);
            } else {
                addEnchantment(enchantment, capitalizedSpacedRegistryObject(enchantment));
            }
        }
    }

    public void addBiomes(Collection<RegistryObject<Biome>> effects) {
        for (RegistryObject<Biome> biome : effects) {
            addBiome(biome, capitalizedSpacedRegistryObject(biome));
        }
    }

    //=========================================================
    //                       ADDITIONS
    //=========================================================
    public void addBiome(Supplier<? extends Biome> key, String name) {
        add(key.get(), name);
    }

    public void add(Biome key, String name) {
        add("biome." + Bioplethora.MOD_ID + "." + key.getRegistryName().getPath(), name);
    }
}