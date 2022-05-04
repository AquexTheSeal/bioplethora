package io.github.bioplethora.blocks;

import io.github.bioplethora.registry.BPBlocks;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PlantBlockHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

import java.util.Random;

public abstract class BPFruitableVinesTopBlock extends BPVinesTopBlock {
    public BPFruitableVinesTopBlock(AbstractBlock.Properties properties) {
        super(properties);
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
        BlockPos blockpos = pos.relative(this.growthDirection.getOpposite());
        BlockState blockstate = reader.getBlockState(blockpos);
        Block block = blockstate.getBlock();
        if (!this.canAttachToBlock(block)) {
            return false;
        } else {
            return block == this.getHeadBlock() || block == this.getFruitedBodyBlock() || block == this.getBodyBlock() || blockstate.isFaceSturdy(reader, blockpos, this.growthDirection);
        }
    }

    public static class BasaltSpeleothermTopBlock extends BPFruitableVinesTopBlock {

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

    public static class ThontusThistleTopBlock extends BPFruitableVinesTopBlock {

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

    public static class TurquoisePendentTopBlock extends BPFruitableVinesTopBlock {

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

    public static class CeriseIvyTopBlock extends BPFruitableVinesTopBlock {

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

    public static class SoulEternTopBlock extends BPFruitableVinesTopBlock {

        public SoulEternTopBlock(AbstractBlock.Properties properties) {
            super(properties);
        }

        @Override
        public Block getFruitedBodyBlock() {
            return BPBlocks.FLOURISHED_SOUL_ETERN.get();
        }

        @Override
        protected Block getBodyBlock() {
            return BPBlocks.SOUL_ETERN_PLANT.get();
        }
    }
}
