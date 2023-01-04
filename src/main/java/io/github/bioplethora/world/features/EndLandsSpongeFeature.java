package io.github.bioplethora.world.features;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import io.github.bioplethora.registry.BPBlocks;
import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.carver.UnderwaterCaveWorldCarver;
import net.minecraft.world.gen.feature.BasaltDeltasFeature;
import net.minecraft.world.gen.feature.structure.BasaltDeltasStructure;

import java.util.Random;

public class EndLandsSpongeFeature extends BasaltDeltasStructure {
    private static final ImmutableList<Block> CANNOT_REPLACE = ImmutableList.of(Blocks.BEDROCK);
    private static final Direction[] DIRECTIONS = Direction.values();

    public EndLandsSpongeFeature(Codec<BasaltDeltasFeature> pCodec) {
        super(pCodec);
    }

    @Override
    public boolean place(ISeedReader level, ChunkGenerator chunkGen, Random rand, BlockPos pos, BasaltDeltasFeature config) {
        boolean flag = false;
        boolean flag1 = rand.nextDouble() < 0.9D;
        int i = flag1 ? config.rimSize().sample(rand) : 0;
        int j = flag1 ? config.rimSize().sample(rand) : 0;
        boolean flag2 = flag1 && i != 0 && j != 0;
        int k = config.size().sample(rand);
        int l = config.size().sample(rand);
        int m = config.size().sample(rand);
        int i1 = Math.max(k, l);
        for (BlockPos blockpos : BlockPos.withinManhattan(pos, k, l, m)) {
            if (blockpos.distManhattan(pos) > i1) {
                break;
            }

            if (isClearer(level, blockpos, config)) {
                if (flag2) {
                    flag = true;
                    this.setBlock(level, blockpos, config.rim());
                    this.carveOpenings(level, blockpos, config);
                }

                BlockPos blockpos1 = blockpos.offset(i, 0, j);
                if (isClearer(level, blockpos1, config)) {
                    flag = true;
                    this.carveOpenings(level, blockpos1, config);
                }
            }
        }

        return flag;
    }

    public void carveOpenings(IWorld pLevel, BlockPos pPos, BasaltDeltasFeature config) {
        if (isClearOptimized(pLevel, pPos)) {
            if (pLevel.getBlockState(pPos.above()).is(BPBlocks.ENREDE_KELP.get()) || pLevel.getBlockState(pPos.above()).is(BPBlocks.ENREDE_KELP_PLANT.get())) {
                this.setBlock(pLevel, pPos, BPBlocks.ENREDE_KELP_PLANT.get().defaultBlockState());
            } else {
                this.setBlock(pLevel, pPos, config.contents());
            }
        }
        /*
        for (int sy = -15; sy <= -1; sy++) {
            BlockPos.Mutable tPos = pPos.offset(0, sy, 0).mutable();
            if (isClearOptimized(pLevel, tPos)) {
                if (pLevel.getBlockState(tPos.above()).is(BPBlocks.ENREDE_KELP.get()) || pLevel.getBlockState(tPos.above()).is(BPBlocks.ENREDE_KELP_PLANT.get())) {
                    this.setBlock(pLevel, tPos, BPBlocks.ENREDE_KELP_PLANT.get().defaultBlockState());
                } else {
                    this.setBlock(pLevel, tPos, config.contents());
                }
            }
        }
        */
    }

    private static boolean isClearer(IWorld pLevel, BlockPos pPos, BasaltDeltasFeature pConfig) {
        BlockState blockstate = pLevel.getBlockState(pPos);
        if (blockstate.is(pConfig.contents().getBlock())) {
            return false;
        } else if (CANNOT_REPLACE.contains(blockstate.getBlock())) {
            return false;
        } else {
            for (Direction direction : DIRECTIONS) {
                boolean flag = pLevel.getBlockState(pPos.relative(direction)).isAir();
                if (flag && direction != Direction.UP || !flag && direction == Direction.UP) {
                    return false;
                }
            }

            return true;
        }
    }

    private static boolean isClear(IWorld pLevel, BlockPos pPos, BasaltDeltasFeature pConfig) {
        BlockState blockstate = pLevel.getBlockState(pPos);
        if (CANNOT_REPLACE.contains(blockstate.getBlock())) {
            return false;
        } else {
            for(Direction direction : DIRECTIONS) {
                boolean flag = pLevel.isEmptyBlock(pPos.relative(direction));
                if (flag && direction != Direction.UP) {
                    return false;
                }
            }

            return true;
        }
    }

    private static boolean isClearOptimized(IWorld world, BlockPos pos) {
        boolean flag = world.isEmptyBlock(pos.east()) || world.isEmptyBlock(pos.west()) || world.isEmptyBlock(pos.south()) || world.isEmptyBlock(pos.north()) || world.isEmptyBlock(pos.below());
        return !flag;
    }
}
