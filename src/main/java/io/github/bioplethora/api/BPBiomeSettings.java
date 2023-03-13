package io.github.bioplethora.api;

import io.github.bioplethora.registry.worldgen.BPConfiguredFeatures;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;

public class BPBiomeSettings {

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

    // Caeri
    public static Biome caeriEndBiome(BiomeGenerationSettings.Builder pGenerationSettingsBuilder) {
        MobSpawnInfo.Builder mobspawninfo$builder = new MobSpawnInfo.Builder();
        DefaultBiomeFeatures.endSpawns(mobspawninfo$builder);

        return (new Biome.Builder())
                .precipitation(Biome.RainType.NONE).biomeCategory(Biome.Category.THEEND).depth(0.2F).scale(0.15F)
                .temperature(0.5F).downfall(0.5F)
                .specialEffects(
                        (new BiomeAmbience.Builder())
                                .waterColor(-14271360)
                                .waterFogColor(-13348438)
                                .fogColor(-14791063)
                                .skyColor(-14791063)
                                .ambientParticle(new ParticleEffectAmbience(ParticleTypes.WHITE_ASH, 0.005F))
                                .ambientLoopSound(SoundEvents.AMBIENT_WARPED_FOREST_LOOP)
                                .ambientMoodSound(new MoodSoundAmbience(SoundEvents.AMBIENT_WARPED_FOREST_MOOD, 6000, 8, 2.0D))
                                .build()
                )
                .mobSpawnSettings(mobspawninfo$builder.build())
                .generationSettings(pGenerationSettingsBuilder.build()).build();
    }

    // Winterfest
    public static Biome winterfestBiome(BiomeGenerationSettings.Builder pGenerationSettingsBuilder) {
        MobSpawnInfo.Builder mobspawninfo$builder = new MobSpawnInfo.Builder();
        DefaultBiomeFeatures.endSpawns(mobspawninfo$builder);

        return (new Biome.Builder())
                .precipitation(Biome.RainType.NONE).biomeCategory(Biome.Category.THEEND).depth(0.1F).scale(0.2F)
                .temperature(0.5F).downfall(0.5F)
                .specialEffects(
                        (new BiomeAmbience.Builder())
                                .waterColor(-14271360)
                                .waterFogColor(-13348438)
                                .fogColor(-14791063)
                                .skyColor(-14791063)
                                .ambientParticle(new ParticleEffectAmbience(ParticleTypes.WHITE_ASH, 0.25F))
                                .ambientLoopSound(SoundEvents.AMBIENT_BASALT_DELTAS_LOOP)
                                .ambientMoodSound(new MoodSoundAmbience(SoundEvents.AMBIENT_BASALT_DELTAS_MOOD, 6000, 8, 2.0D))
                                .build()
                )
                .mobSpawnSettings(mobspawninfo$builder.build())
                .generationSettings(pGenerationSettingsBuilder.build()).build();
    }
}
