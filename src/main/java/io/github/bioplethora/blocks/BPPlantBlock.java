package io.github.bioplethora.blocks;

import com.google.common.collect.ImmutableList;
import io.github.bioplethora.enums.BioPlantShape;
import io.github.bioplethora.enums.BioPlantType;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;

public class BPPlantBlock extends BushBlock  {

    public BioPlantType type;
    public BioPlantShape shape;

    public BPPlantBlock(BioPlantType type, BioPlantShape shape, Properties properties) {
        super(properties);
        this.type = type;
        this.shape = shape;
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

    @Override
    public VoxelShape getShape(BlockState pState, IBlockReader pLevel, BlockPos pPos, ISelectionContext pContext) {
        Vector3d vector3d = pState.getOffset(pLevel, pPos);
        return getShape().move(vector3d.x, vector3d.y, vector3d.z);
    }

    public AbstractBlock.OffsetType getOffsetType() {
        return OffsetType.XYZ;
    }

    public ImmutableList<Block> getWhitelist() {
        return type.getWhitelist();
    }

    public VoxelShape getShape() {
        return shape.getShape();
    }
}
