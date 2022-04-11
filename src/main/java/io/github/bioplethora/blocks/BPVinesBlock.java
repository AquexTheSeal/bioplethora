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

public abstract class BPVinesBlock extends AbstractBodyPlantBlock {
    public static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);

    public BPVinesBlock(AbstractBlock.Properties properties) {
        super(properties, Direction.DOWN, SHAPE, true);
    }

    public abstract Block getFruitedBodyBlock();

    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, IWorld pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (pFacing == this.growthDirection.getOpposite() && !pState.canSurvive(pLevel, pCurrentPos)) {
            pLevel.getBlockTicks().scheduleTick(pCurrentPos, this, 1);
        }
        AbstractTopPlantBlock abstracttopplantblock = this.getHeadBlock();
        if (pFacing == this.growthDirection) {
            Block block = pFacingState.getBlock();
            if (block != getBodyBlock() && block != getFruitedBodyBlock() && block != abstracttopplantblock) {
                return abstracttopplantblock.getStateForPlacement(pLevel);
            }
        }
        if (this.scheduleFluidTicks) {
            pLevel.getLiquidTicks().scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }
        return pState;
    }

    @Override
    public boolean canSurvive(BlockState pState, IWorldReader pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.relative(this.growthDirection.getOpposite());
        BlockState blockstate = pLevel.getBlockState(blockpos);
        Block block = blockstate.getBlock();
        if (!this.canAttachToBlock(block)) {
            return false;
        } else {
            return block == this.getHeadBlock() || block == this.getFruitedBodyBlock() || block == this.getBodyBlock() || blockstate.isFaceSturdy(pLevel, blockpos, this.growthDirection);
        }
    }

    @Override
    public void performBonemeal(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        world.setBlock(pos, getFruitedBodyBlock().defaultBlockState(), 2);
        super.performBonemeal(world, random, pos, state);
    }

    public static class BasaltSpeleothermBlock extends BPVinesBlock {

        public BasaltSpeleothermBlock(AbstractBlock.Properties properties) {
            super(properties);
        }

        @Override
        protected AbstractTopPlantBlock getHeadBlock() {
            return (AbstractTopPlantBlock) BPBlocks.BASALT_SPELEOTHERM.get();
        }

        @Override
        public Block getFruitedBodyBlock() {
            return BPBlocks.FIERY_BASALT_SPELEOTHERM.get();
        }
    }

    public static class FieryBasaltSpeleothermBlock extends BPVinesBlock {

        public FieryBasaltSpeleothermBlock(AbstractBlock.Properties properties) {
            super(properties);
        }

        @Override
        protected AbstractTopPlantBlock getHeadBlock() {
            return (AbstractTopPlantBlock) BPBlocks.BASALT_SPELEOTHERM.get();
        }

        @Override
        protected Block getBodyBlock() {
            return BPBlocks.BASALT_SPELEOTHERM_PLANT.get();
        }

        @Override
        public Block getFruitedBodyBlock() {
            return BPBlocks.FIERY_BASALT_SPELEOTHERM.get();
        }
    }
}
