package io.github.bioplethora.blocks;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public class BPDoublePlantBlock extends DoublePlantBlock {

    public BioPlantType type;

    public BPDoublePlantBlock(BioPlantType type, Properties properties) {
        super(properties);
        this.type = type;
    }

    @Override
    public boolean canSurvive(BlockState pState, IWorldReader pLevel, BlockPos pPos) {
        if (pState.getValue(HALF) != DoubleBlockHalf.UPPER) {
            return super.canSurvive(pState, pLevel, pPos);
        } else {
            BlockState blockstate = pLevel.getBlockState(pPos.below());
            if (pState.getBlock() != this) return surviveCondition(pState, pLevel, pPos);
            return blockstate.is(this) && blockstate.getValue(HALF) == DoubleBlockHalf.LOWER;
        }
    }

    public boolean surviveCondition(BlockState state, IWorldReader reader, BlockPos pos) {
        BlockPos blockpos = pos.below();
        if (state.getBlock() == this) return getWhitelist().contains(reader.getBlockState(blockpos).getBlock());
        return this.mayPlaceOn(reader.getBlockState(blockpos), reader, blockpos);
    }

    public ImmutableList<Block> getWhitelist() {
        return type.getWhitelist();
    }
}
