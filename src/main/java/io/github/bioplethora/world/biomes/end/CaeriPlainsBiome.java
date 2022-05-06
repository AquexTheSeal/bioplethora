package io.github.bioplethora.world.biomes.end;

import io.github.bioplethora.api.BPBiomeSettings;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;

import java.util.function.Supplier;

public class CaeriPlainsBiome {

    public static Biome make(final Supplier<ConfiguredSurfaceBuilder<?>> surfaceBuilder) {

        BiomeGenerationSettings.Builder biomeGenSettings = (new BiomeGenerationSettings.Builder()).surfaceBuilder(surfaceBuilder);

        biomeGenSettings.addStructureStart(StructureFeatures.END_CITY);
        biomeGenSettings.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Features.END_GATEWAY);

        return BPBiomeSettings.baseEndBiome(biomeGenSettings);
    }
}
