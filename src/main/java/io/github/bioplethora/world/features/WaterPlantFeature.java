package io.github.bioplethora.world.features;

import com.mojang.serialization.Codec;
import io.github.bioplethora.registry.BPBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TallSeaGrassBlock;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.ProbabilityConfig;

import java.util.Random;

public class WaterPlantFeature extends Feature<BlockStateFeatureConfig> {

    public WaterPlantFeature(Codec<BlockStateFeatureConfig> pCodec) {
        super(pCodec);
    }

    @Override
    public boolean place(ISeedReader world, ChunkGenerator chunkGen, Random rand, BlockPos pos, BlockStateFeatureConfig config) {

        boolean flag = false;
        int i = rand.nextInt(32) - rand.nextInt(32);
        int j = rand.nextInt(32) - rand.nextInt(32);
        int k = world.getHeight(Heightmap.Type.OCEAN_FLOOR, pos.getX() + i, pos.getZ() + j);
        BlockPos blockpos = new BlockPos(pos.getX() + i, k, pos.getZ() + j);
        if (world.getBlockState(blockpos).is(Blocks.WATER)) {
            if (config.state.canSurvive(world, blockpos)) {
                world.setBlock(blockpos, config.state, 2);
                flag = true;
            }
        }

        return flag;
    }
}
