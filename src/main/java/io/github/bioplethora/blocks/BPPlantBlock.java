package io.github.bioplethora.blocks;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;

public class BPPlantBlock extends BushBlock {

    public BioPlantType type;

    public BPPlantBlock(BioPlantType type, Properties properties) {
        super(properties);
        this.type = type;
    }

    @Override
    public boolean canSurvive(BlockState state, IWorldReader reader, BlockPos pos) {
        BlockPos blockpos = pos.below();
        if (state.getBlock() == this) return getWhitelist().contains(reader.getBlockState(blockpos).getBlock());
        return this.mayPlaceOn(reader.getBlockState(blockpos), reader, blockpos);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, IBlockReader reader, BlockPos pos) {
        return getWhitelist().contains(state.getBlock());
    }

    public ImmutableList<Block> getWhitelist() {
        return type.getWhitelist();
    }
}
