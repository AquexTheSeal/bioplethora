package io.github.bioplethora.blocks;

import io.github.bioplethora.registry.BPBlocks;
import net.minecraft.block.*;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.server.ServerWorld;

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

    public abstract Block getFruitedBodyBlock();

    public boolean canSurvive(BlockState pState, IWorldReader pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.relative(this.growthDirection.getOpposite());
        BlockState blockstate = pLevel.getBlockState(blockpos);
        Block block = blockstate.getBlock();
        if (!this.canAttachToBlock(block)) {
            return false;
        } else {
            return block == this.getHeadBlock() || (block != this.getBodyBlock() && block != this.getFruitedBodyBlock()) || blockstate.isFaceSturdy(pLevel, blockpos, this.growthDirection);
        }
    }

    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, IWorld pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (pFacing == this.growthDirection.getOpposite() && !pState.canSurvive(pLevel, pCurrentPos)) {
            pLevel.getBlockTicks().scheduleTick(pCurrentPos, this, 1);
        }

        if (pLevel.getBlockState(pCurrentPos.below()) == getBodyBlock().defaultBlockState() || pLevel.getBlockState(pCurrentPos.below()) == getFruitedBodyBlock().defaultBlockState()) {
            pLevel.setBlock(pCurrentPos, getBodyBlock().defaultBlockState(), 2);
        }

        if (pFacing != this.growthDirection || !pFacingState.is(this) && !(pFacingState.is(this.getBodyBlock()) || pFacingState.is(this.getFruitedBodyBlock()))) {
            if (this.scheduleFluidTicks) {
                pLevel.getLiquidTicks().scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
            }

            return pState;
        } else {
            return this.getBodyBlock().defaultBlockState();
        }
    }

    @Override
    public void tick(BlockState p_225534_1_, ServerWorld p_225534_2_, BlockPos p_225534_3_, Random p_225534_4_) {
        super.tick(p_225534_1_, p_225534_2_, p_225534_3_, p_225534_4_);

        if (p_225534_2_.getBlockState(p_225534_3_.below()) == getBodyBlock().defaultBlockState() || p_225534_2_.getBlockState(p_225534_3_.below()) == getFruitedBodyBlock().defaultBlockState()) {
            p_225534_2_.setBlock(p_225534_3_, getBodyBlock().defaultBlockState(), 2);
        }
    }

    public static class BasaltSpeleothermTopBlock extends BPVinesTopBlock {

        public BasaltSpeleothermTopBlock(AbstractBlock.Properties properties) {
            super(properties);
        }

        @Override
        public Block getFruitedBodyBlock() {
            return BPBlocks.FIERY_BASALT_SPELEOTHERM.get();
        }

        @Override
        protected Block getBodyBlock() {
            return BPBlocks.BASALT_SPELEOTHERM_PLANT.get();
        }
    }
}
