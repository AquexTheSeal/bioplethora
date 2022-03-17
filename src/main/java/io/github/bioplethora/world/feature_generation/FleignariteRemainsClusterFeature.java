package io.github.bioplethora.world.feature_generation;

import com.google.common.collect.ImmutableList;
import io.github.bioplethora.registry.BioplethoraConfiguredFeatures;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Supplier;

public class FleignariteRemainsClusterFeature {

    public static void generateCluster(final BiomeLoadingEvent event) {
        RegistryKey<Biome> key = RegistryKey.create(Registry.BIOME_REGISTRY, event.getName());
        Set<BiomeDictionary.Type> types = BiomeDictionary.getTypes(key);

        List<Supplier<ConfiguredFeature<?, ?>>> undergroundDeco = event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_DECORATION);

        if (types.contains(BiomeDictionary.Type.OVERWORLD)) {
            undergroundDeco.add(() -> BioplethoraConfiguredFeatures.FLEIGNARITE_REMAINS_CONFIG);
            undergroundDeco.add(() -> BioplethoraConfiguredFeatures.FLEIGNARITE_VINES_CONFIG);
        }

        if (types.contains(BiomeDictionary.Type.END)) {
            undergroundDeco.add(() -> BioplethoraConfiguredFeatures.PENDENT_VINES_CONFIG);
        }
    }

    public static List<BlockState> validPlacements() {
        return ImmutableList.of(Blocks.STONE.defaultBlockState(), Blocks.ANDESITE.defaultBlockState(), Blocks.DIORITE.defaultBlockState(), Blocks.GRANITE.defaultBlockState());
    }

    public static Random seedFleignarChunk(int pChunkX, int pChunkZ, long pSeed, long l) {
        return new Random(pSeed + ((long) pChunkX * pChunkX * 4987142) + (pChunkX * 5947611L) + (long) pChunkZ * pChunkZ * 4392871L + (pChunkZ * 389711L) ^ l);
    }

    public static boolean canSpawnOnChunk(BlockPos pos, IWorld clientWorld) {
        ChunkPos chunkpos = new ChunkPos(pos);
        return seedFleignarChunk(chunkpos.x, chunkpos.z, ((ISeedReader) clientWorld).getSeed(), 987234911L).nextInt(10) == 0;
    }
}
