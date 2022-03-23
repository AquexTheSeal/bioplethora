package io.github.bioplethora.blocks;

import io.github.bioplethora.registry.BPBlocks;
import net.minecraft.block.*;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

import javax.annotation.Nullable;

public class FleignariteVinesBlock extends AbstractBodyPlantBlock implements IWaterLoggable {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);

    public FleignariteVinesBlock(AbstractBlock.Properties properties) {
        super(properties, Direction.DOWN, SHAPE, false);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, Boolean.valueOf(true)));
    }

    protected AbstractTopPlantBlock getHeadBlock() {
        return (AbstractTopPlantBlock) BPBlocks.FLEIGNARITE_VINES.get();
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext pContext) {
        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());
        boolean flag = fluidstate.getType() == Fluids.WATER;
        return super.getStateForPlacement(pContext).setValue(WATERLOGGED, flag);
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(WATERLOGGED);
    }

    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, IWorld pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (pState.getValue(WATERLOGGED)) {
            pLevel.getLiquidTicks().scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }

        return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }

    @Override
    public Fluid takeLiquid(IWorld p_204508_1_, BlockPos p_204508_2_, BlockState p_204508_3_) {
        return IWaterLoggable.super.takeLiquid(p_204508_1_, p_204508_2_, p_204508_3_);
    }

    @Override
    public boolean placeLiquid(IWorld pLevel, BlockPos pPos, BlockState pState, FluidState pFluidState) {
        return IWaterLoggable.super.placeLiquid(pLevel, pPos, pState, pFluidState);
    }

    @Override
    public boolean canPlaceLiquid(IBlockReader pLevel, BlockPos pPos, BlockState pState, Fluid pFluid) {
        return IWaterLoggable.super.canPlaceLiquid(pLevel, pPos, pState, pFluid);
    }
}
