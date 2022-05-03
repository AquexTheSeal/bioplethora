package io.github.bioplethora.blocks.specific;

import io.github.bioplethora.blocks.BPPlantBlock;
import io.github.bioplethora.enums.BioPlantShape;
import io.github.bioplethora.enums.BioPlantType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LavaSpireBlock extends BPPlantBlock {

    public LavaSpireBlock(Properties properties) {
        super(BioPlantType.ALL_NETHER, BioPlantShape.SIMPLE_PLANT, properties);
    }

    @Override
    public void entityInside(BlockState pState, World pLevel, BlockPos pPos, Entity pEntity) {
        super.entityInside(pState, pLevel, pPos, pEntity);
        pEntity.setSecondsOnFire(5);
    }
}
