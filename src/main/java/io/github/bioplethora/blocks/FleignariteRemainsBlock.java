package io.github.bioplethora.blocks;

import io.github.bioplethora.registry.BioplethoraParticles;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class FleignariteRemainsBlock extends BPFlatBlock implements IWaterLoggable {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public FleignariteRemainsBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, Boolean.TRUE));
    }

    @Override
    public void stepOn(World pLevel, BlockPos pPos, Entity pEntity) {
        pLevel.addParticle(BioplethoraParticles.ANTIBIO_SPELL.get(), pPos.getX() + pLevel.random.nextDouble(),
                pPos.getY() + 0.5D, pPos.getZ() + pLevel.random.nextDouble(),
                0d, 0.05d, 0d);
        super.stepOn(pLevel, pPos, pEntity);
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
}
