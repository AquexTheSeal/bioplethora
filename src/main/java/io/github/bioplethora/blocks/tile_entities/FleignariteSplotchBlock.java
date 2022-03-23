package io.github.bioplethora.blocks.tile_entities;

import io.github.bioplethora.blocks.BPFlatBlock;
import io.github.bioplethora.registry.BPBlocks;
import io.github.bioplethora.registry.BPParticles;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class FleignariteSplotchBlock extends BPFlatBlock implements IWaterLoggable {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public FleignariteSplotchBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, Boolean.valueOf(true)));
    }

    @Override
    public void animateTick(BlockState pState, World pLevel, BlockPos pPos, Random pRand) {
        if (pLevel.isEmptyBlock(pPos.below()) || isFree(pLevel.getBlockState(pPos.below())) && pPos.getY() >= 0) {
            pLevel.destroyBlock(pPos, false);
        }
        super.animateTick(pState, pLevel, pPos, pRand);
    }

    @Override
    public boolean canSurvive(BlockState pState, IWorldReader pLevel, BlockPos pPos) {
        return !isFree(pLevel.getBlockState(pPos.below()));
    }

    public static boolean isFree(BlockState pState) {
        Material material = pState.getMaterial();
        return pState.isAir() || pState.is(BlockTags.FIRE) || material.isLiquid() || material.isReplaceable() || pState.getBlock() instanceof BPFlatBlock;
    }

    @Override
    public void stepOn(World pLevel, BlockPos pPos, Entity pEntity) {

        pLevel.addParticle(BPParticles.ANTIBIO_SPELL.get(), pPos.getX() + pLevel.random.nextDouble(),
                pPos.getY() + 0.5D, pPos.getZ() + pLevel.random.nextDouble(),
                0d, 0.05d, 0d);

        if (pEntity instanceof LivingEntity) {
            pEntity.playSound(SoundEvents.SLIME_BLOCK_BREAK, 1.0F, 1.0F);
            ((LivingEntity) pEntity).addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 60, 1, false, false, false));
            ((LivingEntity) pEntity).addEffect(new EffectInstance(Effects.CONFUSION, 60, 0, false, false, false));
            pLevel.destroyBlock(pPos, false);
            pLevel.setBlock(pPos, BPBlocks.FLEIGNARITE_REMAINS.get().defaultBlockState(), 2);
        }

        super.stepOn(pLevel, pPos, pEntity);
    }

    @Override
    public BlockRenderType getRenderShape(BlockState pState) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new FleignariteSplotchTileEntity();
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
