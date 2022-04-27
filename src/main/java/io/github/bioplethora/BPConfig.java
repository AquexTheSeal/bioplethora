package io.github.bioplethora;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class BPConfig {

    public static final ForgeConfigSpec COMMON_SPEC;
    public static final Common COMMON;

    static {
        final Pair<Common, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = commonSpecPair.getRight();
        COMMON = commonSpecPair.getLeft();
    }

    public static final boolean IN_HELLMODE = BPConfig.COMMON.hellMode.get();

    public static class Common {

        public final ForgeConfigSpec.ConfigValue<Boolean> hellMode;
        public final ForgeConfigSpec.ConfigValue<Boolean> hellModeReminder;
        public final ForgeConfigSpec.ConfigValue<Boolean> startupBiopedia;

        public final ForgeConfigSpec.ConfigValue<Integer> mobHealthMultiplier;
        public final ForgeConfigSpec.ConfigValue<Integer> mobMeeleeDamageMultiplier;
        public final ForgeConfigSpec.ConfigValue<Integer> mobSpawnWeightMultiplier;
        public final ForgeConfigSpec.ConfigValue<Integer> mobArmorMultiplier;
        public final ForgeConfigSpec.ConfigValue<Integer> mobMovementSpeedMultiplier;

        public final ForgeConfigSpec.ConfigValue<Boolean> enableCustomModelPositions;
        public final ForgeConfigSpec.ConfigValue<Boolean> enableCustomModelAnimations;
        public final ForgeConfigSpec.ConfigValue<Boolean> allowMobCaps;
        public final ForgeConfigSpec.ConfigValue<Boolean> replaceCreativeTabBackground;
        public final ForgeConfigSpec.ConfigValue<Boolean> antibioCompatibility;
        public final ForgeConfigSpec.ConfigValue<Boolean> announceAlphemKing;
        public final ForgeConfigSpec.ConfigValue<Boolean> alphemCurseOverlay;

        //Ecoharmless
        public final ForgeConfigSpec.ConfigValue<Boolean> spawnCuttlefish;

        public final ForgeConfigSpec.ConfigValue<Boolean> spawnSoulEurydn;
        public final ForgeConfigSpec.ConfigValue<Boolean> spawnFieryEurydn;
        public final ForgeConfigSpec.ConfigValue<Boolean> spawnOnofish;

        //Plethoneutral
        public final ForgeConfigSpec.ConfigValue<Boolean> spawnPeaguin;
        public final ForgeConfigSpec.ConfigValue<Boolean> spawnCavernFleignar;
        public final ForgeConfigSpec.ConfigValue<Boolean> spawnNandbri;

        //Dangerum
        public final ForgeConfigSpec.ConfigValue<Boolean> spawnAlphem;
        public final ForgeConfigSpec.ConfigValue<Boolean> spawnGaugalem;
        public final ForgeConfigSpec.ConfigValue<Boolean> spawnDwarfMossadile;
        public final ForgeConfigSpec.ConfigValue<Boolean> spawnTrapjaw;

        public final ForgeConfigSpec.ConfigValue<Boolean> spawnWoodenGrylynen;
        public final ForgeConfigSpec.ConfigValue<Boolean> spawnStoneGrylynen;
        public final ForgeConfigSpec.ConfigValue<Boolean> spawnGoldenGrylynen;
        public final ForgeConfigSpec.ConfigValue<Boolean> spawnIronGrylynen;
        public final ForgeConfigSpec.ConfigValue<Boolean> spawnDiamondGrylynen;
        public final ForgeConfigSpec.ConfigValue<Boolean> spawnNetheriteGrylynen;

        //Hellsent
        public final ForgeConfigSpec.ConfigValue<Boolean> spawnCrephoxl;
        public final ForgeConfigSpec.ConfigValue<Boolean> spawnBellophgolem;

        //Elderia
        public final ForgeConfigSpec.ConfigValue<Boolean> spawnMyliothan;

        //Mob Caps
        public final ForgeConfigSpec.ConfigValue<Integer> altyrusMobCap;
        public final ForgeConfigSpec.ConfigValue<Integer> heliobladeMobCap;
        public final ForgeConfigSpec.ConfigValue<Integer> alphemKingMobCap;

        //Weapon Mechanics
        public final ForgeConfigSpec.ConfigValue<Double> toxinRushSpeedModifier;

        Common(ForgeConfigSpec.Builder builder) {

            //===================================
            //        MAIN MECHANICS
            //===================================
            builder.push("Main Mechanics");
            hellMode = builder.define("Hell Mode (Adds more mechanics and buffs to mobs to make the game harder, hellmode also makes items better)", false);
            hellModeReminder = builder.define("Hell Mode Reminder (Reminds you everytime you join the world, enter a dimension, or respawn)", true);
            startupBiopedia = builder.define("Gives you a Biopedia upon joining the world", true);
            builder.pop();

            //===================================
            //          WEAPON MECHANICS
            //===================================
            builder.push("Weapon Mechanics");
            toxinRushSpeedModifier = builder.defineInRange("Multiplier for dashing speed of Nandbric Shortsword Toxin Rush ability. Minimum value of 0.5, maximum value of 5", 2.5f, 0.5f, 5f);
            builder.pop();

            //===================================
            //     MOB STAT MULTIPLIERS
            //===================================
            builder.push("Mob Stat Multipliers");
            mobHealthMultiplier = builder.define("Multiplier for all Bioplethora mobs' health", 1);
            mobMeeleeDamageMultiplier = builder.define("Multiplier for all Bioplethora mobs' meelee damage", 1);
            mobSpawnWeightMultiplier = builder.define("Multiplier for all Bioplethora mobs' spawn weights", 1);
            mobArmorMultiplier = builder.define("Multiplier for all Bioplethora mobs' armor points", 1);
            mobMovementSpeedMultiplier = builder.define("Multiplier for all Bioplethora mobs' movement speed", 1);
            builder.pop();

            builder.push("Other Mechanics");
            enableCustomModelPositions = builder.define("Should the player model change arm positions when holding specific items? (Has risk of crash)", true);
            enableCustomModelAnimations = builder.define("Should the player model have custom animations when using specific items? (Has risk of crash)", true);
            allowMobCaps = builder.define("Should specific mobs, especially bosses, have a damage limit?", true);
            replaceCreativeTabBackground = builder.define("Replace the Bioplethora's creative tabs' background images with a custom one?", false);
            antibioCompatibility = builder.define("Can all Anti-bio Enchantments be used on a single weapon together?", true);
            announceAlphemKing = builder.define("Announce the summon of the Alphem King to all players in the world?", true);
            alphemCurseOverlay = builder.define("Enable the Alphem Curse overlay when fighting the Alphem King? (The overlay can conceal your health, hunger, and armor bars)", false);
            builder.pop();

            //===================================
            //          MOB SPAWNS
            //===================================
            builder.push("Mob Spawns");
            //Ecoharmless
            builder.push("Ecoharmless Mobs");
            spawnCuttlefish = builder.define("Enable mob spawning for Cuttlefishes", true);
            spawnOnofish = builder.define("Enable mob spawning for Onofishes", true);

            spawnSoulEurydn = builder.define("Enable mob spawning for Soul Eurydnia", true);
            spawnFieryEurydn = builder.define("Enable mob spawning for Fiery Eurydnia", true);
            builder.pop();
            //Plethoneutral
            builder.push("Plethoneutral Mobs");
            spawnPeaguin = builder.define("Enable mob spawning for Peaguins", true);
            spawnCavernFleignar = builder.define("Enable mob spawning for Cavern Fleignars", true);
            spawnNandbri = builder.define("Enable mob spawning for Nandbris", true);
            builder.pop();
            //Dangerum
            builder.push("Dangerum Mobs");
            spawnAlphem = builder.define("Enable mob spawning for Alphems", true);
            spawnGaugalem = builder.define("Enable mob spawning for Gaugalems", true);
            spawnDwarfMossadile = builder.define("Enable mob spawning for Dwarf Mossadiles", true);
            spawnTrapjaw = builder.define("Enable mob spawning for Trapjaws", true);

            spawnWoodenGrylynen = builder.define("Enable mob spawning for Wooden Grylynens", true);
            spawnStoneGrylynen = builder.define("Enable mob spawning for Stone Grylynens", true);
            spawnGoldenGrylynen = builder.define("Enable mob spawning for Golden Grylynens", true);
            spawnIronGrylynen = builder.define("Enable mob spawning for Iron Grylynens", true);
            spawnDiamondGrylynen = builder.define("Enable mob spawning for Diamond Grylynens", true);
            spawnNetheriteGrylynen = builder.define("Enable mob spawning for Netherite Grylynens", true);
            builder.pop();
            //Hellsent
            builder.push("Hellsent Mobs");
            spawnCrephoxl = builder.define("Enable mob spawning for Crephoxls", true);
            spawnBellophgolem = builder.define("Enable mob spawning for Bellophgolems", true);
            builder.pop();
            //Elderia
            builder.push("Elderia Mobs");
            spawnMyliothan = builder.define("Enable mob spawning for Myliothans", true);
            builder.pop();
            builder.pop();

            //Mob Cap Limits
            builder.push("Mob Damage Limits (Only applies if Mob Caps are enabled)");
            altyrusMobCap = builder.define("Damage Limit of the Altyrus", 40);
            heliobladeMobCap = builder.define("Damage Limit of the Helioblade", 30);
            alphemKingMobCap = builder.define("Damage Limit of the Alphem King", 30);
            builder.pop();
        }
    }
}
