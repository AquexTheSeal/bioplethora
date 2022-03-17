package io.github.bioplethora.world.feature_generation;

import io.github.bioplethora.registry.BioplethoraConfiguredFeatures;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public class PendentVinesFeature {

    public static void generateVines(final BiomeLoadingEvent event) {
        RegistryKey<Biome> key = RegistryKey.create(Registry.BIOME_REGISTRY, event.getName());
        Set<BiomeDictionary.Type> types = BiomeDictionary.getTypes(key);

        List<Supplier<ConfiguredFeature<?, ?>>> base = event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_DECORATION);

        if (types.contains(BiomeDictionary.Type.OVERWORLD)) {
            base.add(() -> BioplethoraConfiguredFeatures.FLEIGNARITE_VINES_CONFIG);
        }

        if (types.contains(BiomeDictionary.Type.END)) {
            base.add(() -> BioplethoraConfiguredFeatures.PENDENT_VINES_CONFIG);
        }
    }
}
