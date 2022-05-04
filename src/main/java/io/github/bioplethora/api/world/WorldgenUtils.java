package io.github.bioplethora.api.world;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import java.util.Random;

public class WorldgenUtils {

    public static final String BASALT_DELTAS = getVanillaBiome("basalt_deltas");
    public static final String NETHER_WASTES = getVanillaBiome("nether_wastes");
    public static final String WARPED_FOREST = getVanillaBiome("warped_forest");
    public static final String CRIMSON_FOREST = getVanillaBiome("crimson_forest");
    public static final String SOUL_SAND_VALLEY = getVanillaBiome("soul_sand_valley");

    public static final String END_HIGHLANDS = getVanillaBiome("end_highlands");
    public static final String SMALL_END_ISLANDS = getVanillaBiome("small_end_islands");
    public static final String END_MIDLANDS = getVanillaBiome("end_midlands");
    public static final String END_BARRENS = getVanillaBiome("end_barrens");

    public static String getVanillaBiome(String biomeId) {
        return "minecraft:" + biomeId;
    }

    public static boolean getBiomeFromEvent(BiomeLoadingEvent event, String biome) {
        return new ResourceLocation(biome).equals(event.getName());
    }

    public static abstract class NBTTree {

        protected abstract ConfiguredFeature<?, ?> getTree(Random random);

        public boolean placeAt(ISeedReader worldIn, ChunkGenerator chunkGenerator, BlockPos pos, BlockState belowPos, Random random) {

            ConfiguredFeature<?, ?> tree = this.getTree(random);
            if (tree == null) {
                return false;

            } else {
                worldIn.setBlock(pos, Blocks.AIR.defaultBlockState(), 4);
                if (tree.place(worldIn, chunkGenerator, random, pos)) {
                    return true;
                } else {
                    worldIn.setBlock(pos, belowPos, 4);
                    return false;
                }
            }
        }
    }
}
