package io.github.bioplethora.world.biomes.end.configurable;

import io.github.bioplethora.registry.BPParticles;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;

import java.util.function.Supplier;

public class LavenderLakesBiome {

    public static Biome make(final Supplier<ConfiguredSurfaceBuilder<?>> surfaceBuilder) {

        BiomeGenerationSettings.Builder biomeGenSettings = (new BiomeGenerationSettings.Builder()).surfaceBuilder(surfaceBuilder);
        MobSpawnInfo.Builder mobspawninfo$builder = new MobSpawnInfo.Builder();
        DefaultBiomeFeatures.endSpawns(mobspawninfo$builder);

        biomeGenSettings.addStructureStart(StructureFeatures.END_CITY);
        biomeGenSettings.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Features.END_GATEWAY);
        biomeGenSettings.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.CHORUS_PLANT);

        return(new Biome.Builder())
                .precipitation(Biome.RainType.NONE).biomeCategory(Biome.Category.THEEND).depth(0.1F).scale(0.2F)
                .temperature(0.5F).downfall(0.5F)
                .specialEffects(new BiomeAmbience.Builder()
                                .waterColor(-13817728)
                                .waterFogColor(-13158998)
                                .fogColor(-12378263)
                                .skyColor(-12378263)
                                .ambientParticle(new ParticleEffectAmbience(BPParticles.NIGHT_GAZE.get(), 0.14F))
                                .ambientLoopSound(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_MOOD)
                                .ambientMoodSound(new MoodSoundAmbience(SoundEvents.AMBIENT_NETHER_WASTES_MOOD, 6000, 8, 2.0D))
                                .build())
                .mobSpawnSettings(mobspawninfo$builder.build())
                .generationSettings(biomeGenSettings.build()).build();
    }
}
