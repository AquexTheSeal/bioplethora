package io.github.bioplethora.world.features;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import net.minecraft.block.*;
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
    private static final ImmutableList<Block> CANNOT_REPLACE = ImmutableList.of(Blocks.BEDROCK, Blocks.NETHER_BRICKS, Blocks.NETHER_BRICK_FENCE, Blocks.NETHER_BRICK_STAIRS, Blocks.NETHER_WART, Blocks.CHEST, Blocks.SPAWNER);
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
        int i1 = Math.max(k, l);
        for (BlockPos blockpos : BlockPos.withinManhattan(pos, k, 0, l)) {
            if (blockpos.distManhattan(pos) > i1) {
                break;
            }

            if (isClearer(level, blockpos, config)) {
                if (flag2) {
                    flag = true;
                    this.setBlock(level, blockpos, config.rim());
                }

                BlockPos blockpos1 = blockpos.offset(i, 0, j);
                if (isClearer(level, blockpos1, config)) {
                    flag = true;
                    this.setBlock(level, blockpos1, config.contents());
                }

                for (int yf = -1; yf > -14; yf--) {

                    BlockPos blockpos2 = blockpos.offset(0, yf, 0);
                    if (isClear(level, blockpos2, config)) {
                        this.setBlock(level, blockpos2, config.contents());
                    } else {
                        this.setBlock(level, blockpos2, config.rim());
                    }
                }
            }
        }

        return flag;
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
                boolean flag = pLevel.getBlockState(pPos.relative(direction)).isAir();
                if (flag && direction != Direction.UP) {
                    return false;
                }
            }

            return true;
        }
    }
}
