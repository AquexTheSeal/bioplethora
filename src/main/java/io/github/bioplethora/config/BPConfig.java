package io.github.bioplethora.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class BPConfig {

    public static final ForgeConfigSpec COMMON_SPEC;
    public static final BPCommonConfig COMMON;
    public static final ForgeConfigSpec WORLDGEN_SPEC;
    public static final BPWorldgenConfig WORLDGEN;

    static {
        final Pair<BPCommonConfig, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(BPCommonConfig::new);
        COMMON_SPEC = commonSpecPair.getRight();
        COMMON = commonSpecPair.getLeft();

        final Pair<BPWorldgenConfig, ForgeConfigSpec> worldgenSpecPair = new ForgeConfigSpec.Builder().configure(BPWorldgenConfig::new);
        WORLDGEN_SPEC = worldgenSpecPair.getRight();
        WORLDGEN = worldgenSpecPair.getLeft();
    }

    public static final boolean IN_HELLMODE = BPConfig.COMMON.hellMode.get();
}
