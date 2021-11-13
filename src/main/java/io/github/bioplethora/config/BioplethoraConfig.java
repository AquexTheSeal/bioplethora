package io.github.bioplethora.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class BioplethoraConfig {

    public static class Common {
        public final ForgeConfigSpec.ConfigValue<Integer> mobHealthMultiplier;
        public final ForgeConfigSpec.ConfigValue<Integer> mobMeeleeDamageMultiplier;
        public final ForgeConfigSpec.ConfigValue<Integer> mobSpawnWeightMultiplier;
        public final ForgeConfigSpec.ConfigValue<Integer> mobArmorMultiplier;
        public final ForgeConfigSpec.ConfigValue<Integer> mobMovementSpeedMultiplier;

        Common(ForgeConfigSpec.Builder builder) {
            builder.push("Mob Stat Multipliers");
            mobHealthMultiplier = builder.define("Multiplier for all Bioplethora mobs' health", 1);
            mobMeeleeDamageMultiplier = builder.define("Multiplier for all Bioplethora mobs' meelee damage", 1);
            mobSpawnWeightMultiplier = builder.define("Multiplier for all Bioplethora mobs' spawn weights", 1);
            mobArmorMultiplier = builder.define("Multiplier for all Bioplethora mobs' armor points", 1);
            mobMovementSpeedMultiplier = builder.define("Multiplier for all Bioplethora mobs' movement speed", 1);
        }
    }

    public static final ForgeConfigSpec COMMON_SPEC;
    public static final Common COMMON;

    static {
        final Pair<Common, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = commonSpecPair.getRight();
        COMMON = commonSpecPair.getLeft();
    }
}
