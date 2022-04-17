package io.github.bioplethora.blocks;

import io.github.bioplethora.enums.BioPlantShape;
import io.github.bioplethora.enums.BioPlantType;
import io.github.bioplethora.registry.BPBlockstateProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;

import javax.annotation.Nullable;

public class SmallMushroomBlock extends BPPlantBlock {
    public static final IntegerProperty MINISHROOMS = BPBlockstateProperties.MINISHROOMS;
    public static final DirectionProperty FACING_DIRECTION = HorizontalBlock.FACING;

    public SmallMushroomBlock(BioPlantType type, Properties properties) {
        super(type, BioPlantShape.MINISHROOM, properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(MINISHROOMS, 1));
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext pContext) {
        BlockState blockstate = pContext.getLevel().getBlockState(pContext.getClickedPos());

        if (blockstate.is(this)) {
            return blockstate
                    .setValue(MINISHROOMS, Math.min(3, blockstate.getValue(MINISHROOMS) + 1))
                    .setValue(FACING_DIRECTION, pContext.getHorizontalDirection());
        } else {
            return super.getStateForPlacement(pContext);
        }
    }

    public boolean canBeReplaced(BlockState pState, BlockItemUseContext pUseContext) {
        return pUseContext.getItemInHand().getItem() == this.asItem() && pState.getValue(MINISHROOMS) < 3 || super.canBeReplaced(pState, pUseContext);
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
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING_DIRECTION, MINISHROOMS);
    }
}
