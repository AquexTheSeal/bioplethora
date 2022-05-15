package io.github.bioplethora.blocks;

import io.github.bioplethora.registry.BPBlocks;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import java.util.Random;

public abstract class BPVinesTopBlock extends AbstractTopPlantBlock {
    protected static final VoxelShape SHAPE = Block.box(4.0D, 9.0D, 4.0D, 12.0D, 16.0D, 12.0D);

    public BPVinesTopBlock(AbstractBlock.Properties properties) {
        super(properties, Direction.DOWN, SHAPE, true, 0.1D);
    }

    protected boolean canGrowInto(BlockState pState) {
        return PlantBlockHelper.isValidGrowthState(pState);
    }

    protected int getBlocksToGrowWhenBonemealed(Random pRandom) {
        return PlantBlockHelper.getBlocksToGrowWhenBonemealed(pRandom);
    }

    public boolean canSurviveOnLeafUtil(BlockState pState, IWorldReader pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.relative(this.growthDirection.getOpposite());
        BlockState blockstate = pLevel.getBlockState(blockpos);
        Block block = blockstate.getBlock();
        if (!this.canAttachToBlock(block)) {
            return false;
        } else {
            return block == this.getHeadBlock() || block == this.getBodyBlock() || block instanceof LeavesBlock || blockstate.isFaceSturdy(pLevel, blockpos, this.growthDirection);
        }
    }

    public static class PinkTwiTopBlock extends BPVinesTopBlock {
        public PinkTwiTopBlock(Properties properties) {
            super(properties);
        }
        @Override
        protected Block getBodyBlock() {
            return BPBlocks.PINK_TWI_PLANT.get();
        }
        @Override
        public boolean canSurvive(BlockState pState, IWorldReader pLevel, BlockPos pPos) {
            return canSurviveOnLeafUtil(pState, pLevel, pPos);
        }
    }
    public static class RedTwiTopBlock extends BPVinesTopBlock {
        public RedTwiTopBlock(Properties properties) {
            super(properties);
        }
        @Override
        protected Block getBodyBlock() {
            return BPBlocks.RED_TWI_PLANT.get();
        }
        @Override
        public boolean canSurvive(BlockState pState, IWorldReader pLevel, BlockPos pPos) {
            return canSurviveOnLeafUtil(pState, pLevel, pPos);
        }
    }

    public static class SpiritDanglerTopBlock extends BPVinesTopBlock {
        public SpiritDanglerTopBlock(Properties properties) {
            super(properties);
        }
        @Override
        protected Block getBodyBlock() {
            return BPBlocks.SPIRIT_DANGLER_PLANT.get();
        }
    }

    public static class SpinxelthornTopBlock extends BPVinesTopBlock {
        public SpinxelthornTopBlock(Properties properties) {
            super(properties);
        }
        @Override
        protected Block getBodyBlock() {
            return BPBlocks.SPINXELTHORN_PLANT.get();
        }
    }
    public static class GlacynthTopBlock extends BPVinesTopBlock {
        public GlacynthTopBlock(Properties properties) {
            super(properties);
        }
        @Override
        protected Block getBodyBlock() {
            return BPBlocks.GLACYNTH_PLANT.get();
        }
        @Override
        public void entityInside(BlockState p_196262_1_, World p_196262_2_, BlockPos p_196262_3_, Entity p_196262_4_) {
            super.entityInside(p_196262_1_, p_196262_2_, p_196262_3_, p_196262_4_);
            p_196262_2_.destroyBlock(p_196262_3_, false);
        }
    }
}
