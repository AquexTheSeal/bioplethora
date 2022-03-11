package io.github.bioplethora.blocks.tile_entities;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.gui.container.ReinforcingTableContainer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CraftingTableBlock;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class ReinforcingTableBlock extends CraftingTableBlock {
    private static final ITextComponent CONTAINER_TITLE = new TranslationTextComponent("container." + Bioplethora.MOD_ID + ".reinforce");
    public static final DirectionProperty FACING_DIRECTION = HorizontalBlock.FACING;

    public ReinforcingTableBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new ReinforcingTableTileEntity();
    }

    public INamedContainerProvider getMenuProvider(BlockState pState, World pLevel, BlockPos pPos) {
        return new SimpleNamedContainerProvider((i, inventory, entity) -> new ReinforcingTableContainer(i, inventory, IWorldPosCallable.create(pLevel, pPos)), CONTAINER_TITLE);
    }

    public ActionResultType use(BlockState pState, World pLevel, BlockPos pPos, PlayerEntity pPlayer, Hand pHand, BlockRayTraceResult pHit) {
        if (pLevel.isClientSide) {
            return ActionResultType.SUCCESS;
        } else {
            NetworkHooks.openGui((ServerPlayerEntity) pPlayer, pState.getMenuProvider(pLevel, pPos));
            //pPlayer.openMenu(pState.getMenuProvider(pLevel, pPos));
            return ActionResultType.CONSUME;
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.defaultBlockState().setValue(FACING_DIRECTION, context.getHorizontalDirection());
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING_DIRECTION, rotation.rotate(state.getValue(FACING_DIRECTION)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING_DIRECTION)));
    }

    @Override
    protected void createBlockStateDefinition (StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING_DIRECTION);
    }
}
