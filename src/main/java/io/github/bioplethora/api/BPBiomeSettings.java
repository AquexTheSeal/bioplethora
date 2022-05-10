package io.github.bioplethora.api;

import io.github.bioplethora.registry.worldgen.BPConfiguredFeatures;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;

public class BPBiomeSettings {

    public static void addDefaultCaeriVegetation(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, BPConfiguredFeatures.IRION_GRASS);
        builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, BPConfiguredFeatures.IRION_TALL_GRASS);
        builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, BPConfiguredFeatures.ARTAIRIUS);
    }

    // End
    public static Biome baseEndBiome(BiomeGenerationSettings.Builder pGenerationSettingsBuilder) {
        MobSpawnInfo.Builder mobspawninfo$builder = new MobSpawnInfo.Builder();
        DefaultBiomeFeatures.endSpawns(mobspawninfo$builder);

        return (new Biome.Builder())
                .precipitation(Biome.RainType.NONE).biomeCategory(Biome.Category.THEEND).depth(0.1F).scale(0.2F)
                .temperature(0.5F).downfall(0.5F)
                .specialEffects(
                        (new BiomeAmbience.Builder())
                        .waterColor(4159204)
                        .waterFogColor(329011)
                        .fogColor(10518688)
                        .skyColor(0)
                        .ambientMoodSound(MoodSoundAmbience.LEGACY_CAVE_SETTINGS).build()
                )
                .mobSpawnSettings(mobspawninfo$builder.build())
                .generationSettings(pGenerationSettingsBuilder.build()).build();
    }
}
