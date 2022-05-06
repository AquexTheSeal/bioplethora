package io.github.bioplethora.world;

import io.github.bioplethora.registry.worldgen.BPBiomes;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.registries.ForgeRegistries;

import static net.minecraftforge.common.BiomeDictionary.Type.*;

public class BPBiomeGeneration {

    public static void generateBiomes() {
        addBiome(BPBiomes.ROCKY_WOODLANDS.get(), BiomeManager.BiomeType.COOL, 20, COLD, DENSE, MOUNTAIN, FOREST);
    }

    private static void addBiome(Biome biome, BiomeManager.BiomeType type, int weight, BiomeDictionary.Type... types) {
        ResourceLocation keyRL = ForgeRegistries.BIOMES.getKey(biome);
        assert keyRL != null;
        RegistryKey<Biome> key = RegistryKey.create(ForgeRegistries.Keys.BIOMES, keyRL);

        BiomeDictionary.addTypes(key, types);
        BiomeManager.addBiome(type, new BiomeManager.BiomeEntry(key, weight));
    }
}
