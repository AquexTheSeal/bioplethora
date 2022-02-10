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

        public final ForgeConfigSpec.ConfigValue<Boolean> spawnCuttlefish;

        public final ForgeConfigSpec.ConfigValue<Boolean> spawnPeaguin;

        public final ForgeConfigSpec.ConfigValue<Boolean> spawnAlphem;
        public final ForgeConfigSpec.ConfigValue<Boolean> spawnGaugalem;
        public final ForgeConfigSpec.ConfigValue<Boolean> spawnDwarfMossadile;

        public final ForgeConfigSpec.ConfigValue<Boolean> spawnCrephoxl;
        public final ForgeConfigSpec.ConfigValue<Boolean> spawnBellophgolem;

        public final ForgeConfigSpec.ConfigValue<Boolean> spawnMyliothan;

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

            //===================================
            //          MOB SPAWNS
            //===================================
            builder.push("Mob Spawns");
            //Ecoharmless
            builder.push("Ecoharmless Mobs");
            spawnCuttlefish = builder.define("Enable spawning Cuttlefishes", true);
            builder.pop();
            //Plethoneutral
            builder.push("Plethoneutral Mobs");
            spawnPeaguin = builder.define("Enable spawning Peaguins", true);
            builder.pop();
            //Dangerum
            builder.push("Dangerum Mobs");
            spawnAlphem = builder.define("Enable spawning Alphems", true);
            spawnGaugalem = builder.define("Enable spawning Gaugalems", true);
            spawnDwarfMossadile = builder.define("Enable spawning Dwarf Mossadiles", true);
            builder.pop();
            //Hellsent
            builder.push("Hellsent Mobs");
            spawnCrephoxl = builder.define("Enable spawning Crephoxls", true);
            spawnBellophgolem = builder.define("Enable spawning Bellophgolems", true);
            builder.pop();
            //Elderia
            builder.push("Elderia Mobs");
            spawnMyliothan = builder.define("Enable spawning Myliothans", true);
            builder.pop();
            builder.pop();
        }
    }
}
