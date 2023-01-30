package io.github.bioplethora.world.features;

import com.mojang.serialization.Codec;
import io.github.bioplethora.registry.BPBlocks;
import io.github.bioplethora.registry.BPTags;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChorusFlowerBlock;
import net.minecraft.block.ChorusPlantBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.ChorusPlantFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import javax.annotation.Nullable;
import java.util.Random;

public class ChorusMychrodegiaFeature extends ChorusPlantFeature {

    public ChorusMychrodegiaFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(ISeedReader world, ChunkGenerator chunkGen, Random rand, BlockPos pos, NoFeatureConfig config) {
        if (world.isEmptyBlock(pos) && world.getBlockState(pos.below()).is(BPTags.Blocks.CHORUS_GROWABLE)) {
            world.setBlock(pos, ((ChorusPlantBlock)Blocks.CHORUS_PLANT).getStateForPlacement(world, pos), 2);
            growTreeRecursive(world, pos, rand, pos, 32, 0);
            return true;
        } else {
            return false;
        }
    }

    private static void growTreeRecursive(IWorld pLevel, BlockPos pBranchPos, Random pRand, BlockPos pOriginalBranchPos, int pMaxHorizontalDistance, int pIterations) {
        ChorusPlantBlock chorusplantblock = (ChorusPlantBlock) Blocks.CHORUS_PLANT;
        int i = pRand.nextInt(7) + 3;
        if (pIterations == 0) {
            ++i;
        }

        for (int j = 0; j < i; ++j) {
            BlockPos blockpos = pBranchPos.above(j + 1);
            if (!allNeighborsEmpty(pLevel, blockpos, null)) {
                return;
            }

            pLevel.setBlock(blockpos, chorusplantblock.getStateForPlacement(pLevel, blockpos), 2);
            pLevel.setBlock(blockpos.below(), chorusplantblock.getStateForPlacement(pLevel, blockpos.below()), 2);
        }

        boolean flag = false;
        if (pIterations < 4) {
            int l = pRand.nextInt(4);
            if (pIterations == 0) {
                ++l;
            }

            for (int k = 0; k < l; ++k) {
                Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(pRand);
                BlockPos blockpos1 = pBranchPos.above(i).relative(direction);
                if (Math.abs(blockpos1.getX() - pOriginalBranchPos.getX()) < pMaxHorizontalDistance && Math.abs(blockpos1.getZ() - pOriginalBranchPos.getZ()) < pMaxHorizontalDistance && pLevel.isEmptyBlock(blockpos1) && pLevel.isEmptyBlock(blockpos1.below()) && allNeighborsEmpty(pLevel, blockpos1, direction.getOpposite())) {
                    flag = true;
                    pLevel.setBlock(blockpos1, chorusplantblock.getStateForPlacement(pLevel, blockpos1), 2);
                    pLevel.setBlock(blockpos1.relative(direction.getOpposite()), chorusplantblock.getStateForPlacement(pLevel, blockpos1.relative(direction.getOpposite())), 2);
                    growTreeRecursive(pLevel, blockpos1, pRand, pOriginalBranchPos, pMaxHorizontalDistance, pIterations + 1);
                }
            }
        }

        if (!flag) {
            if (pRand.nextInt(3) == 0) {
                growTreeRecursive(pLevel, pBranchPos, pRand, pOriginalBranchPos, pMaxHorizontalDistance, pIterations + 1);
            } else {
                createPetals(pLevel, pBranchPos.above(i), pRand, pOriginalBranchPos, pIterations);
            }
        }
    }

    public static void createPetals(IWorld world, BlockPos pos, Random pRand, BlockPos pOriginalBranchPos, int pIterations) {
        int size = 1 + world.getRandom().nextInt(3);
        for (int x = -size; x < size; x++) {
            for (int z = -size; z < size; z++) {
                BlockPos.Mutable squarePos = pos.mutable().move(x, 0, z);
                world.setBlock(squarePos, BPBlocks.CHORUS_MYCHRODEGIA.get().defaultBlockState(), 2);
                if (world.getRandom().nextInt(2) == 0) {
                    if (world.isEmptyBlock(squarePos.below())) {
                        world.setBlock(squarePos.below(), BPBlocks.CHORUS_MYCHRODEGIA_PART.get().defaultBlockState(), 2);
                    }
                }
            }
        }
        int petalLength = 5 + world.getRandom().nextInt(5) * (size / 2);
        for (int l = 0; l < petalLength; l++) {
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                BlockPos.Mutable petalPos = pos.relative(direction, size + l).mutable();
                int petalDeg = l == petalLength / 2 ? 0 : 1;
                for (int t = -size; t < size; t++) {
                    world.setBlock(petalPos.offset(t, petalDeg, t), BPBlocks.CHORUS_MYCHRODEGIA.get().defaultBlockState(), 2);
                    if (world.getRandom().nextInt(2) == 0) {
                        if (world.isEmptyBlock(petalPos.offset(t, petalDeg - 1, t))) {
                            world.setBlock(petalPos.offset(t, petalDeg - 1, t), BPBlocks.CHORUS_MYCHRODEGIA_PART.get().defaultBlockState(), 2);
                        }
                    }
                }
            }
        }
    }

    private static boolean allNeighborsEmpty(IWorldReader pLevel, BlockPos pPos, @Nullable Direction pExcludingSide) {
        for(Direction direction : Direction.Plane.HORIZONTAL) {
            if (direction != pExcludingSide && !pLevel.isEmptyBlock(pPos.relative(direction))) {
                return false;
            }
        }

        return true;
    }
}
