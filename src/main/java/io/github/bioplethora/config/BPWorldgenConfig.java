package io.github.bioplethora.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class BPWorldgenConfig {
    // ---------------- Key Configurations ----------------
    public final ForgeConfigSpec.ConfigValue<Boolean> createNewSpongeBiome;

    // ---------------- Vanilla Overworld Biomes ----------------

    // ---------------- Custom Overworld Biomes ----------------

    // ---------------- Vanilla Nether Biomes ----------------

    // ---------------- Custom Nether Biomes ----------------

    // ---------------- Vanilla End Biomes ----------------
    public final ForgeConfigSpec.ConfigValue<Boolean> cyraLakesEnd;
    public final ForgeConfigSpec.ConfigValue<Integer> cyraLakesEndAmount;

    public final ForgeConfigSpec.ConfigValue<Boolean> chorusVegetationMidlands;
    public final ForgeConfigSpec.ConfigValue<Boolean> chorusVegetationHighlands;
    public final ForgeConfigSpec.ConfigValue<Boolean> chorusLanternMidlands;
    public final ForgeConfigSpec.ConfigValue<Integer> chorusLanternMidlandsAmount;
    public final ForgeConfigSpec.ConfigValue<Boolean> chorusLanternHighlands;
    public final ForgeConfigSpec.ConfigValue<Integer> chorusLanternHighlandsAmount;

    public final ForgeConfigSpec.ConfigValue<Boolean> endSpongeHighlands;
    public final ForgeConfigSpec.ConfigValue<Integer> endSpongeHighlandsAmount;
    public final ForgeConfigSpec.ConfigValue<Boolean> endSpongeMidlands;
    public final ForgeConfigSpec.ConfigValue<Integer> endSpongeMidlandsAmount;

    public final ForgeConfigSpec.ConfigValue<Boolean> endSpikeHighlands;
    public final ForgeConfigSpec.ConfigValue<Integer> endSpikeHighlandsAmount;
    public final ForgeConfigSpec.ConfigValue<Boolean> endSpikeMidlands;
    public final ForgeConfigSpec.ConfigValue<Integer> endSpikeMidlandsAmount;

    public final ForgeConfigSpec.ConfigValue<Boolean> endIcicleIslands;
    public final ForgeConfigSpec.ConfigValue<Integer> endIcicleIslandsAmount;

    public final ForgeConfigSpec.ConfigValue<Boolean> endFrozenIslands;
    public final ForgeConfigSpec.ConfigValue<Integer> endFrozenIslandsAmount;

    // ---------------- Custom End Biomes ----------------

    protected BPWorldgenConfig(ForgeConfigSpec.Builder builder) {
        builder.push("KEY CONFIGURATIONS");
        createNewSpongeBiome = builder
                .comment("Remove all features from End Highlands, Midlands, and Barrens to a new Biome called the Lavender Lakes")
                .define("Move vanilla End Biome features to a new Biome?", false)
        ;

        builder.push("VANILLA END FEATURES");
        cyraLakesEnd = builder.define(shouldGen("Cyra Lakes", "End"), true);
        cyraLakesEndAmount = builder.define(genAmount("Cyra Lakes", "End"), 60);

        chorusVegetationMidlands  = builder.define(shouldGen("Chorus Vegetation", "End Midlands"), true);
        chorusVegetationHighlands = builder.define(shouldGen("Chorus Vegetation", "End Highlands"), true);

        chorusLanternHighlands = builder.define(shouldGen("Chorus Lantern Plants", "End Highlands"), true);
        chorusLanternHighlandsAmount = builder.define(genAmount("Chorus Lantern Plants", "End Highlands"), 65);

        chorusLanternMidlands = builder.define(shouldGen("Chorus Lantern Plants", "End Midlands"), true);
        chorusLanternMidlandsAmount = builder.define(genAmount("Chorus Lantern Plants", "End Midlands"), 55);

        endSpongeHighlands = builder.define(shouldGen("End Sponges", "End Highlands"), true);
        endSpongeHighlandsAmount = builder.define(genAmount("End Sponges", "End Highlands"), 233);

        endSpongeMidlands = builder.define(shouldGen("End Sponges", "End Midlands"), true);
        endSpongeMidlandsAmount = builder.define(genAmount("End Sponges", "End Midlands"), 275);

        endSpikeHighlands = builder.define(shouldGen("End Spikes", "End Highlands"), true);
        endSpikeHighlandsAmount = builder.define(genAmount("End Spikes", "End Highlands"), 135);

        endSpikeMidlands = builder.define(shouldGen("End Spikes", "End Midlands"), true);
        endSpikeMidlandsAmount = builder.define(genAmount("End Spikes", "End Midlands"), 115);

        endIcicleIslands = builder.define(shouldGen("End Icicles", "Small End Islands"), true);
        endIcicleIslandsAmount = builder.define(genAmount("End Icicles", "Small End Islands"), 10);

        endFrozenIslands = builder.define(shouldGen("Frozen End Islands", "Small End Islands"), true);
        endFrozenIslandsAmount = builder.define(genAmount("Frozen End Islands", "Small End Islands"), 5);
    }

    protected String shouldGen(String feature, String place) {
        return "Should " + feature + " generate in the " + place + "?";
    }

    protected String genAmount(String feature, String place) {
        return "Generation range of " + feature + " in the " + place;
    }
}
