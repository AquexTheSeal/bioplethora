package io.github.bioplethora.blocks;

import io.github.bioplethora.enums.BioPlantShape;
import io.github.bioplethora.enums.BioPlantType;
import io.github.bioplethora.registry.BPBlockstateProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BPIdeFanBlock extends BPPlantBlock {
    public static final BooleanProperty BUDDED = BPBlockstateProperties.BUDDED;

    public BPIdeFanBlock(BioPlantType type, BioPlantShape shape, Properties properties) {
        super(type, shape, properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(BUDDED, false));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext pContext) {
        return pContext.getLevel().random.nextInt(3) == 1 ?
                super.getStateForPlacement(pContext).setValue(BUDDED, true) : super.getStateForPlacement(pContext);
    }

    @Override
    public void entityInside(BlockState pState, World pLevel, BlockPos pPos, Entity pEntity) {
        super.entityInside(pState, pLevel, pPos, pEntity);
        if (pState.getValue(BUDDED)) {
            pEntity.playSound(this.soundType.getPlaceSound(), 1.0F, 1.0F);
            pLevel.setBlock(pPos, pState.setValue(BUDDED, false), 2);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BUDDED);
    }
}
