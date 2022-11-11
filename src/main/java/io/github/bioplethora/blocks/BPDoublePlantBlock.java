package io.github.bioplethora.blocks;

import com.google.common.collect.ImmutableList;
import io.github.bioplethora.enums.BioPlantShape;
import io.github.bioplethora.enums.BioPlantType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.common.PlantType;

public class BPDoublePlantBlock extends DoublePlantBlock {

    public BioPlantType type;
    public BioPlantShape shape;

    public BPDoublePlantBlock(BioPlantType type, BioPlantShape shape, Properties properties) {
        super(properties);
        this.type = type;
        this.shape = shape;
    }

    @Override
    public boolean canSurvive(BlockState pState, IWorldReader pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.below();
        BlockState groundState = pLevel.getBlockState(blockpos);
        if (pState.getValue(HALF) == DoubleBlockHalf.UPPER) {
            if (pState.getBlock() != this) return surviveCondition(pState, pLevel, pPos);
            return groundState.is(this) && groundState.getValue(HALF) == DoubleBlockHalf.LOWER;
        } else {
            if (pState.getBlock() == this) return surviveCondition(pState, pLevel, pPos);
            return this.mayPlaceOn(groundState, pLevel, blockpos);
        }
    }

    @Override
    public PlantType getPlantType(IBlockReader world, BlockPos pos) {
        return PlantType.NETHER;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, IBlockReader reader, BlockPos pos) {
        return getWhitelist().contains(state.getBlock());
    }

    public boolean surviveCondition(BlockState pState, IWorldReader pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.below();
        if (pState.getBlock() == this)
            return getWhitelist().contains(pLevel.getBlockState(blockpos).getBlock());
        return this.mayPlaceOn(pLevel.getBlockState(blockpos), pLevel, blockpos);
    }

    @Override
    public void placeAt(IWorld p_196390_1_, BlockPos p_196390_2_, int p_196390_3_) {
        super.placeAt(p_196390_1_, p_196390_2_, p_196390_3_);
    }

    public ImmutableList<Block> getWhitelist() {
        return type.getWhitelist().get();
    }

    @Override
    public VoxelShape getShape(BlockState pState, IBlockReader pLevel, BlockPos pPos, ISelectionContext pContext) {
        return getShape();
    }

    public VoxelShape getShape() {
        return shape.getShape();
    }
}
