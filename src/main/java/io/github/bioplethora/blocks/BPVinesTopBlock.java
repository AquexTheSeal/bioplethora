package io.github.bioplethora.blocks;

import io.github.bioplethora.registry.BPBlocks;
import net.minecraft.block.*;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IWorldReader;

import java.util.Random;

public abstract class BPVinesTopBlock extends AbstractTopPlantBlock {
    protected static final VoxelShape SHAPE = Block.box(4.0D, 9.0D, 4.0D, 12.0D, 16.0D, 12.0D);

    public BPVinesTopBlock(AbstractBlock.Properties properties) {
        super(properties, Direction.DOWN, SHAPE, true, 0.1D);
    }

    protected boolean canGrowInto(BlockState pState) {
        return PlantBlockHelper.isValidGrowthState(pState);
    }

    protected int getBlocksToGrowWhenBonemealed(Random pRandom) {
        return PlantBlockHelper.getBlocksToGrowWhenBonemealed(pRandom);
    }

    public abstract Block getFruitedBodyBlock();

    @Override
    public boolean canSurvive(BlockState state, IWorldReader reader, BlockPos pos) {
        BlockPos blockpos =  pos.relative(this.growthDirection.getOpposite());
        BlockState blockstate = reader.getBlockState(blockpos);
        Block block = blockstate.getBlock();
        if (!this.canAttachToBlock(block)) {
            return false;
        } else {
            return block == this.getHeadBlock() || block == this.getFruitedBodyBlock() || block == this.getBodyBlock() || blockstate.isFaceSturdy(reader, blockpos, this.growthDirection);
        }
    }

    public static class BasaltSpeleothermTopBlock extends BPVinesTopBlock {

        public BasaltSpeleothermTopBlock(AbstractBlock.Properties properties) {
            super(properties);
        }

        @Override
        public Block getFruitedBodyBlock() {
            return BPBlocks.FIERY_BASALT_SPELEOTHERM.get();
        }

        @Override
        protected Block getBodyBlock() {
            return BPBlocks.BASALT_SPELEOTHERM_PLANT.get();
        }
    }

    public static class ThontusThistleTopBlock extends BPVinesTopBlock {

        public ThontusThistleTopBlock(AbstractBlock.Properties properties) {
            super(properties);
        }

        @Override
        public Block getFruitedBodyBlock() {
            return BPBlocks.BERRIED_THONTUS_THISTLE.get();
        }

        @Override
        protected Block getBodyBlock() {
            return BPBlocks.THONTUS_THISTLE_PLANT.get();
        }
    }

    public static class TurquoisePendentTopBlock extends BPVinesTopBlock {

        public TurquoisePendentTopBlock(AbstractBlock.Properties properties) {
            super(properties);
        }

        @Override
        public Block getFruitedBodyBlock() {
            return BPBlocks.BLOSSOMING_TURQUOISE_PENDENT.get();
        }

        @Override
        protected Block getBodyBlock() {
            return BPBlocks.TURQUOISE_PENDENT_PLANT.get();
        }
    }

    public static class CeriseIvyTopBlock extends BPVinesTopBlock {

        public CeriseIvyTopBlock(AbstractBlock.Properties properties) {
            super(properties);
        }

        @Override
        public Block getFruitedBodyBlock() {
            return BPBlocks.SEEDED_CERISE_IVY.get();
        }

        @Override
        protected Block getBodyBlock() {
            return BPBlocks.CERISE_IVY_PLANT.get();
        }
    }
}
