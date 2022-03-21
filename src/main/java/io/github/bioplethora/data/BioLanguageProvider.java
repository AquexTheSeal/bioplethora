package io.github.bioplethora.data;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.enchantments.AntibioEnchantment;
import io.github.bioplethora.registry.*;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.potion.Effect;
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
    //==========================================================

    List<Supplier<? extends EntityType<?>>> manualEntityList = new ArrayList<>();

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
        addItems(BioplethoraItems.ITEMS.getEntries());
        addBlocks(BioplethoraBlocks.BLOCKS.getEntries());
        addEntityTypes(BioplethoraEntities.ENTITIES.getEntries());
        addEffects(BioplethoraEffects.EFFECTS.getEntries());
        addEnchantments(BioplethoraEnchantments.ENCHANTMENTS.getEntries());

        this.addDeathMessages();
        this.addSoundSubtitles();
        this.addTooltipHelper();
    }

    //=======================================================
    //            MANUAL TRANSLATION DATAGENS
    //=======================================================

    public void manualEntityTranslations() {
        addManualEntity(BioplethoraEntities.ALTYRUS_SUMMONING, "Altyrus Summoning Core");
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
    }

    public void addSoundSubtitles() {
        add(mobSubtitlesFormat(BioplethoraEntities.CREPHOXL, "idle"), "Crephoxl Grunts");
        add(mobSubtitlesFormat(BioplethoraEntities.CREPHOXL, "hurt"), "Crephoxl Hurts");
        add(mobSubtitlesFormat(BioplethoraEntities.CREPHOXL, "death"), "Crephoxl Dies");

        add(mobSubtitlesFormat(BioplethoraEntities.BELLOPHGOLEM, "idle"), "Bellophgolem Grunts");
        add(mobSubtitlesFormat(BioplethoraEntities.BELLOPHGOLEM, "hurt"), "Bellophgolem Hurts");
        add(mobSubtitlesFormat(BioplethoraEntities.BELLOPHGOLEM, "death"), "Bellophgolem Dies");

        add(mobSubtitlesFormat(BioplethoraEntities.HELIOBLADE, "idle"), "Helioblade Grunts");
        add(mobSubtitlesFormat(BioplethoraEntities.HELIOBLADE, "hurt"), "Helioblade Hurts");
        add(mobSubtitlesFormat(BioplethoraEntities.HELIOBLADE, "death"), "Helioblade Dies");

        add(mobSubtitlesFormat(BioplethoraEntities.ALTYRUS, "idle"), "Altyrus Groans");
        add(mobSubtitlesFormat(BioplethoraEntities.ALTYRUS, "charge"), "Altyrus Charges");

        add(mobSubtitlesFormat(BioplethoraEntities.ALPHEM, "step"), "Alphem Walks");

        add(mobSubtitlesFormat(BioplethoraEntities.ALPHEM_KING, "roar"), "Alphem King Roars");

        add(mobSubtitlesFormat(BioplethoraEntities.MYLIOTHAN, "idle"), "Myliothan Cries");
    }

    public void addTooltipHelper() {
        addSkilledItem(BioplethoraItems.CREPHOXL_HAMMER, "dysfunction", "Increased damage for enemies with 50 or more health. Enemies attacked with this weapon gets debuffs for a very short duration.");
        addSkilledItem(BioplethoraItems.CREPHOXL_HAMMER, "deathsweep", "Hitting an entity while crouching will deal 80% of this tool's base damage to nearby entities within a 2-block radius. 1.5 second cooldown.");
        addSkilledItem(BioplethoraItems.CREPHOXL_HAMMER, "aerial_shockwave", "On right click on the ground, this weapon creates a damaging shockwave on block right-click position, dealing 9 damage to nearby entities & sending them flying into the air. 3-second cooldown.");

        addSkilledItem(BioplethoraItems.BELLOPHITE_ARROW, "sharper_tip", "The Bellophgolem arrow deals more damage than the regular arrow.");
        addSkilledItem(BioplethoraItems.BELLOPHITE_ARROW, "core_energy", "On hit (block or entity), the arrow deals magic damages and debuffs all nearby mobs.");

        addSkilledItem(BioplethoraItems.BELLOPHITE_SHIELD, "recovery_bulwark", "The Bellophite shield has more durability than the regular shield, and it gives a resistance and regeneration effect on using.");
        addSkilledItem(BioplethoraItems.BELLOPHITE_SHIELD, "core_impulse", "This shield stacks core points every attack this shield blocks. Once the core points stacks up to 4, this shield releases a shockwave, damaging all nearby entities, then resets the core points back to 0. Letting go of the shield resets all the core points.");

        add("item.bioplethora.ecoharmless_spawn_egg.desc", "ECOHARMLESS");
        add("item.bioplethora.plethoneutral_spawn_egg.desc", "PLETHONEUTRAL");
        add("item.bioplethora.dangerum_spawn_egg.desc", "DANGERUM");
        add("item.bioplethora.hellsent_spawn_egg.desc", "HELLSENT");
        add("item.bioplethora.elderia_spawn_egg.desc", "ELDERIA");
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

    //===============================================================
    //               TRANSLATION RESULT TEXT FORMATS
    //===============================================================

    public String capitalizedSpacedRegistryObject(RegistryObject<?> registryObject) {
        return capitalizedSpacedText(registryObject.getId().getPath());
    }

    public String capitalizedSpacedText(String string) {
        return WordUtils.capitalize(string.replace("_", " "));
    }

    //=============================================================
    //                     LANGUAGE EASER
    //=============================================================

    public void addSkilledItem(RegistryObject<Item> item, String skillId, String description) {
        add(tooltipSkillHelper(item, skillId), capitalizedSpacedText(skillId));
        add(tooltipSkillDescHelper(item, skillId), description);
    }


    //==================================================================
    //                   MANUAL TRANSLATION HELPERS
    //==================================================================

    public void addManualEntity(Supplier<? extends EntityType<?>> entitySupplier, String customName) {
        manualEntityList.add(entitySupplier);
        addEntityType(entitySupplier, customName);
    }

    //=========================================================
    //            AUTOMATIC TRANSLATION DATAGENS
    //=========================================================

    public void addItems(Collection<RegistryObject<Item>> items) {
        for (RegistryObject<Item> item : items) {
            addItem(item, capitalizedSpacedRegistryObject(item));
        }
    }

    public void addBlocks(Collection<RegistryObject<Block>> blocks) {
        for (RegistryObject<Block> block : blocks) {
            addBlock(block, capitalizedSpacedRegistryObject(block));
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
}