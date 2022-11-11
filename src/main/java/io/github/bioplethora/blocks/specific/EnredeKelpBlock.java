package io.github.bioplethora.blocks.specific;

import com.google.common.collect.ImmutableList;
import io.github.bioplethora.enums.BioPlantShape;
import io.github.bioplethora.enums.BioPlantType;
import io.github.bioplethora.registry.BPBlocks;
import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;

public class EnredeKelpBlock extends KelpBlock {

    public EnredeKelpBlock(Properties properties) {
        super(properties);
    }

    protected AbstractTopPlantBlock getHeadBlock() {
        return (AbstractTopPlantBlock) BPBlocks.ENREDE_KELP.get();
    }
}
