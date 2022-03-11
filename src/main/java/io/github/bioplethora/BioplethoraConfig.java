package io.github.bioplethora;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class BioplethoraConfig {

    public static final ForgeConfigSpec COMMON_SPEC;
    public static final Common COMMON;

    static {
        final Pair<Common, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = commonSpecPair.getRight();
        COMMON = commonSpecPair.getLeft();
    }

    public static final boolean getHellMode = BioplethoraConfig.COMMON.hellMode.get();

    public static class Common {

        public final ForgeConfigSpec.ConfigValue<Boolean> hellMode;
        public final ForgeConfigSpec.ConfigValue<Boolean> hellModeReminder;
        public final ForgeConfigSpec.ConfigValue<Boolean> startupBiopedia;

        public final ForgeConfigSpec.ConfigValue<Integer> mobHealthMultiplier;
        public final ForgeConfigSpec.ConfigValue<Integer> mobMeeleeDamageMultiplier;
        public final ForgeConfigSpec.ConfigValue<Integer> mobSpawnWeightMultiplier;
        public final ForgeConfigSpec.ConfigValue<Integer> mobArmorMultiplier;
        public final ForgeConfigSpec.ConfigValue<Integer> mobMovementSpeedMultiplier;

        public final ForgeConfigSpec.ConfigValue<Boolean> allowMobCaps;
        public final ForgeConfigSpec.ConfigValue<Boolean> replaceCreativeTabBackground;
        public final ForgeConfigSpec.ConfigValue<Boolean> antibioCompatibility;
        public final ForgeConfigSpec.ConfigValue<Boolean> announceAlphemKing;

        //Ecoharmless
        public final ForgeConfigSpec.ConfigValue<Boolean> spawnCuttlefish;

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

        Common(ForgeConfigSpec.Builder builder) {

            //===================================
            //        MAIN MECHANICS
            //===================================
            builder.push("Main Mechanics");
            hellMode = builder.define("Hell Mode (Adds more mechanics and buffs to mobs to make the game harder)", true);
            hellModeReminder = builder.define("Hell Mode Reminder (Reminds you everytime you join the world, enter a dimension, or respawn)", true);
            startupBiopedia = builder.define("Gives you a Biopedia upon joining the world", true);
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
            allowMobCaps = builder.define("Should specific mobs, especially bosses, have a damage limit?", true);
            replaceCreativeTabBackground = builder.define("Replace the Bioplethora's creative tabs' background images with a custom one?", true);
            antibioCompatibility = builder.define("Can all Anti-bio Enchantments be used on a single weapon together?", true);
            announceAlphemKing = builder.define("Announce the summon of the Alphem King to all players in the world?", true);
            builder.pop();

            //===================================
            //          MOB SPAWNS
            //===================================
            builder.push("Mob Spawns");
            //Ecoharmless
            builder.push("Ecoharmless Mobs");
            spawnCuttlefish = builder.define("Enable mob spawning for Cuttlefishes", true);
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
            altyrusMobCap = builder.define("Damage Limit of the Altyrus", 70);
            heliobladeMobCap = builder.define("Damage Limit of the Helioblade", 60);
            alphemKingMobCap = builder.define("Damage Limit of the Helioblade", 45);
        }
    }
}
