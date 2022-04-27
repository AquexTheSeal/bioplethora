package io.github.bioplethora.blocks;

import io.github.bioplethora.registry.BPBlocks;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractBodyPlantBlock;
import net.minecraft.block.AbstractTopPlantBlock;
import net.minecraft.block.Block;
import net.minecraft.util.Direction;
import net.minecraft.util.math.shapes.VoxelShape;

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
}
