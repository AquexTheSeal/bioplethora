package io.github.bioplethora.blocks;

import io.github.bioplethora.enums.BioPlantShape;
import io.github.bioplethora.enums.BioPlantType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;

public class BPRotatablePlantBlock extends BPPlantBlock {
    public static final DirectionProperty FACING_DIRECTION = HorizontalBlock.FACING;

    public BPRotatablePlantBlock(BioPlantType type, BioPlantShape shape, Properties properties) {
        super(type, shape, properties);
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
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING_DIRECTION);
    }
}
