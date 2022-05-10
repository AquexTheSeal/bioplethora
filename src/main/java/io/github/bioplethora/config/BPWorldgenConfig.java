package io.github.bioplethora.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class BPWorldgenConfig {

    // ---------------- Vanilla Overworld Biomes ----------------

    // ---------------- Custom Overworld Biomes ----------------

    // ---------------- Vanilla Nether Biomes ----------------

    // ---------------- Custom Nether Biomes ----------------

    // ---------------- Vanilla End Biomes ----------------
    public final ForgeConfigSpec.ConfigValue<Boolean> cyraLakesEnd;
    public final ForgeConfigSpec.ConfigValue<Integer> cyraLakesEndAmount;

    public final ForgeConfigSpec.ConfigValue<Boolean> chorusLanternHighlands;
    public final ForgeConfigSpec.ConfigValue<Integer> chorusLanternHighlandsAmount;

    public final ForgeConfigSpec.ConfigValue<Boolean> endSpikeHighlands;
    public final ForgeConfigSpec.ConfigValue<Integer> endSpikeHighlandsAmount;

    public final ForgeConfigSpec.ConfigValue<Boolean> chorusLanternMidlands;
    public final ForgeConfigSpec.ConfigValue<Integer> chorusLanternMidlandsAmount;

    public final ForgeConfigSpec.ConfigValue<Boolean> endIcicleIslands;
    public final ForgeConfigSpec.ConfigValue<Integer> endIcicleIslandsAmount;

    public final ForgeConfigSpec.ConfigValue<Boolean> endFrozenIslands;
    public final ForgeConfigSpec.ConfigValue<Integer> endFrozenIslandsAmount;

    // ---------------- Custom End Biomes ----------------

    protected BPWorldgenConfig(ForgeConfigSpec.Builder builder) {
        builder.push("VANILLA END FEATURES");
        cyraLakesEnd = builder.define(shouldGen("Cyra Lakes", "End"), true);
        cyraLakesEndAmount = builder.define(genAmount("Cyra Lakes", "End"), 60);

        chorusLanternHighlands = builder.define(shouldGen("Chorus Lantern Plants", "End Highlands"), true);
        chorusLanternHighlandsAmount = builder.define(genAmount("Chorus Lantern Plants", "End Highlands"), 95);

        endSpikeHighlands = builder.define(shouldGen("End Spikes", "End Highlands"), true);
        endSpikeHighlandsAmount = builder.define(genAmount("End Spikes", "End Highlands"), 35);

        chorusLanternMidlands = builder.define(shouldGen("Chorus Lantern Plants", "End Midlands"), true);
        chorusLanternMidlandsAmount = builder.define(genAmount("Chorus Lantern Plants", "End Midlands"), 75);

        endIcicleIslands = builder.define(shouldGen("End Icicles", "Small End Islands"), true);
        endIcicleIslandsAmount = builder.define(genAmount("End Icicles", "Small End Islands"), 10);

        endFrozenIslands = builder.define(shouldGen("Frozen End Islands", "Small End Islands"), true);
        endFrozenIslandsAmount = builder.define(genAmount("Frozen End Islands", "Small End Islands"), 12);
    }

    protected String shouldGen(String feature, String place) {
        return "Should " + feature + " generate in the " + place + "?";
    }

    protected String genAmount(String feature, String place) {
        return "Generation range of " + feature + " in the " + place;
    }
}
