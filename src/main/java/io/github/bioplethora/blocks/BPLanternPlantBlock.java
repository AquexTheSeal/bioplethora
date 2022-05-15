package io.github.bioplethora.blocks;

import io.github.bioplethora.enums.BioPlantShape;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.piglin.PiglinTasks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tags.BlockTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.PlantType;
import net.minecraftforge.fml.RegistryObject;

import javax.annotation.Nullable;

public class BPLanternPlantBlock extends DoublePlantBlock {

    Block fruit;
    public BioPlantShape shape;

    public BPLanternPlantBlock(Block fruit, BioPlantShape shape, AbstractBlock.Properties properties) {
        super(properties);
        this.fruit = fruit;
        this.shape = shape;
        this.registerDefaultState(this.stateDefinition.any().setValue(HALF, DoubleBlockHalf.UPPER));
    }

    public BPLanternPlantBlock(RegistryObject<Block> fruit, BioPlantShape shape, AbstractBlock.Properties properties) {
        this(fruit.get(), shape, properties);
    }

    @Override
    public boolean canSurvive(BlockState pState, IWorldReader pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.above();
        BlockState groundState = pLevel.getBlockState(blockpos);
        if (pState.getValue(HALF) == DoubleBlockHalf.LOWER) {
            if (pState.getBlock() != this) return surviveCondition(pState, pLevel, pPos);
            return groundState.is(this) && groundState.getValue(HALF) == DoubleBlockHalf.UPPER;
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
        return state.getBlock() == fruit;
    }

    public boolean surviveCondition(BlockState pState, IWorldReader pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.above();
        if (pState.getBlock() == this)
            return pLevel.getBlockState(blockpos).getBlock() == fruit;
        return this.mayPlaceOn(pLevel.getBlockState(blockpos), pLevel, blockpos);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext pContext) {
        BlockPos blockpos = pContext.getClickedPos();
        return blockpos.getY() < 255 &&
                pContext.getLevel().getBlockState(blockpos.below()).canBeReplaced(pContext) ?
                this.defaultBlockState() : null;
    }

    public void setPlacedBy(World pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {
        pLevel.setBlock(pPos.below(), this.defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER), 3);
    }

    @Override
    public void placeAt(IWorld p_196390_1_, BlockPos p_196390_2_, int p_196390_3_) {
        p_196390_1_.setBlock(p_196390_2_, this.defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER), p_196390_3_);
        p_196390_1_.setBlock(p_196390_2_.above(), this.defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER), p_196390_3_);
        p_196390_1_.setBlock(p_196390_2_.above().above(), this.fruit.defaultBlockState(), p_196390_3_);
    }

    @Override
    public VoxelShape getShape(BlockState pState, IBlockReader pLevel, BlockPos pPos, ISelectionContext pContext) {
        return getShape();
    }

    public VoxelShape getShape() {
        return shape.getShape();
    }

    @Override
    public OffsetType getOffsetType() {
        return OffsetType.NONE;
    }

    @OnlyIn(Dist.CLIENT)
    public long getSeed(BlockState pState, BlockPos pPos) {
        return MathHelper.getSeed(pPos.getX(), pPos.below(pState.getValue(HALF) == DoubleBlockHalf.UPPER ? 0 : 1).getY(), pPos.getZ());
    }

    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, IWorld pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        DoubleBlockHalf doubleblockhalf = pState.getValue(HALF);
            if (pFacing.getAxis() != Direction.Axis.Y || doubleblockhalf == DoubleBlockHalf.UPPER != (pFacing == Direction.DOWN) || pFacingState.is(this) && pFacingState.getValue(HALF) != doubleblockhalf) {
            return doubleblockhalf == DoubleBlockHalf.UPPER && pFacing == Direction.UP && !pState.canSurvive(pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : updS1(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
        } else {
            return Blocks.AIR.defaultBlockState();
        }
    }

    public BlockState updS1(BlockState pState, Direction pFacing, BlockState pFacingState, IWorld pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        return !pState.canSurvive(pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : pState;
    }

    public void playerWillDestroy(World pLevel, BlockPos pPos, BlockState pState, PlayerEntity pPlayer) {
        if (!pLevel.isClientSide) {
            if (pPlayer.isCreative()) {
                preventCreativeDropFromBottomPart(pLevel, pPos, pState, pPlayer);
            } else {
                dropResources(pState, pLevel, pPos, (TileEntity)null, pPlayer, pPlayer.getMainHandItem());
            }
        }

        pLevel.levelEvent(pPlayer, 2001, pPos, getId(pState));
        if (this.is(BlockTags.GUARDED_BY_PIGLINS)) {
            PiglinTasks.angerNearbyPiglins(pPlayer, false);
        }
    }

    protected static void preventCreativeDropFromBottomPart(World pLevel, BlockPos pPos, BlockState pState, PlayerEntity pPlayer) {
        DoubleBlockHalf doubleblockhalf = pState.getValue(HALF);
        if (doubleblockhalf == DoubleBlockHalf.LOWER) {
            BlockPos blockpos = pPos.above();
            BlockState blockstate = pLevel.getBlockState(blockpos);
            if (blockstate.getBlock() == pState.getBlock() && blockstate.getValue(HALF) == DoubleBlockHalf.UPPER) {
                pLevel.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 35);
                pLevel.levelEvent(pPlayer, 2001, blockpos, Block.getId(blockstate));
            }
        }

    }
}
