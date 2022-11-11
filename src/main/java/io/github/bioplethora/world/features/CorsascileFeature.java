package io.github.bioplethora.world.features;

import com.mojang.serialization.Codec;
import io.github.bioplethora.registry.BPBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.KelpTopBlock;
import net.minecraft.block.TallSeaGrassBlock;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.feature.SeaGrassFeature;

import java.util.Random;

public class CorsascileFeature extends Feature<ProbabilityConfig> {

    public CorsascileFeature(Codec<ProbabilityConfig> pCodec) {
        super(pCodec);
    }

    @Override
    public boolean place(ISeedReader world, ChunkGenerator chunkGen, Random rand, BlockPos pos, ProbabilityConfig config) {

        boolean flag = false;
        int i = rand.nextInt(32) - rand.nextInt(32);
        int j = rand.nextInt(32) - rand.nextInt(32);
        int k = world.getHeight(Heightmap.Type.OCEAN_FLOOR, pos.getX() + i, pos.getZ() + j);
        BlockPos blockpos = new BlockPos(pos.getX() + i, k, pos.getZ() + j);
        if (world.getBlockState(blockpos).is(Blocks.WATER) && world.getBlockState(blockpos.above()).is(Blocks.WATER)) {
            BlockState blockstate = BPBlocks.ENREDE_CORSASCILE.get().defaultBlockState().setValue(TallSeaGrassBlock.HALF, DoubleBlockHalf.LOWER);
            BlockState blockstate1 = BPBlocks.ENREDE_CORSASCILE.get().defaultBlockState().setValue(TallSeaGrassBlock.HALF, DoubleBlockHalf.UPPER);
            if (blockstate.canSurvive(world, blockpos)) {
                world.setBlock(blockpos, blockstate, 2);
                world.setBlock(blockpos.above(), blockstate1, 2);
                flag = true;
            }
        }

        return flag;
    }
}
