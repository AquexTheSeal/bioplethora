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

    public static class Common {

        public final ForgeConfigSpec.ConfigValue<Boolean> hellMode;
        public final ForgeConfigSpec.ConfigValue<Boolean> hellModeReminder;
        public final ForgeConfigSpec.ConfigValue<Boolean> startupBiopedia;

        public final ForgeConfigSpec.ConfigValue<Integer> mobHealthMultiplier;
        public final ForgeConfigSpec.ConfigValue<Integer> mobMeeleeDamageMultiplier;
        public final ForgeConfigSpec.ConfigValue<Integer> mobSpawnWeightMultiplier;
        public final ForgeConfigSpec.ConfigValue<Integer> mobArmorMultiplier;
        public final ForgeConfigSpec.ConfigValue<Integer> mobMovementSpeedMultiplier;

        Common(ForgeConfigSpec.Builder builder) {

            builder.push("Hell Mode");
            hellMode = builder.define("Hell Mode (Adds more mechanics and buffs to mobs to make the game harder)", true);
            hellModeReminder = builder.define("Hell Mode Reminder (Reminds you everytime you join the world, enter a dimension, or respawn)", true);
            startupBiopedia = builder.define("Gives you a Biopedia upon joining the world", true);
            builder.pop();
            builder.push("Mob Stat Multipliers");
            mobHealthMultiplier = builder.define("Multiplier for all Bioplethora mobs' health", 1);
            mobMeeleeDamageMultiplier = builder.define("Multiplier for all Bioplethora mobs' meelee damage", 1);
            mobSpawnWeightMultiplier = builder.define("Multiplier for all Bioplethora mobs' spawn weights", 1);
            mobArmorMultiplier = builder.define("Multiplier for all Bioplethora mobs' armor points", 1);
            mobMovementSpeedMultiplier = builder.define("Multiplier for all Bioplethora mobs' movement speed", 1);
            builder.pop();
        }
    }
}
