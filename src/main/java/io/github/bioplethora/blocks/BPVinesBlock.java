package io.github.bioplethora.blocks;

import io.github.bioplethora.registry.BPBlocks;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.World;

public abstract class BPVinesBlock extends AbstractBodyPlantBlock {
    public static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);

    protected BPVinesBlock(AbstractBlock.Properties properties) {
        super(properties, Direction.DOWN, SHAPE, true);
    }

    public static class SpiritDanglerBlock extends BPVinesBlock {
        public SpiritDanglerBlock(Properties properties) {
            super(properties);
        }
        @Override
        protected AbstractTopPlantBlock getHeadBlock() {
            return (AbstractTopPlantBlock) BPBlocks.SPIRIT_DANGLER.get();
        }
    }

    public static class SpinxelthornBlock extends BPVinesBlock {
        public SpinxelthornBlock(Properties properties) {
            super(properties);
        }
        @Override
        protected AbstractTopPlantBlock getHeadBlock() {
            return (AbstractTopPlantBlock) BPBlocks.SPINXELTHORN.get();
        }
    }
    public static class GlacynthBlock extends BPVinesBlock {
        public GlacynthBlock(Properties properties) {
            super(properties);
        }
        @Override
        protected AbstractTopPlantBlock getHeadBlock() {
            return (AbstractTopPlantBlock) BPBlocks.GLACYNTH.get();
        }
        @Override
        public void entityInside(BlockState p_196262_1_, World p_196262_2_, BlockPos p_196262_3_, Entity p_196262_4_) {
            super.entityInside(p_196262_1_, p_196262_2_, p_196262_3_, p_196262_4_);
            p_196262_2_.destroyBlock(p_196262_3_, false);
        }
    }
}
