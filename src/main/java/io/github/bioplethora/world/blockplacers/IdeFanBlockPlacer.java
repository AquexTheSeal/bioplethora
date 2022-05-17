package io.github.bioplethora.world.blockplacers;

import com.mojang.serialization.Codec;
import io.github.bioplethora.blocks.BPIdeFanBlock;
import io.github.bioplethora.registry.worldgen.BPBlockPlacers;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.blockplacer.BlockPlacer;
import net.minecraft.world.gen.blockplacer.BlockPlacerType;

import java.util.Random;

public class IdeFanBlockPlacer extends BlockPlacer {
    public static final IdeFanBlockPlacer INSTANCE = new IdeFanBlockPlacer();
    public static final Codec<IdeFanBlockPlacer> CODEC = Codec.unit(() -> INSTANCE);

    @Override
    public void place(IWorld pLevel, BlockPos pPos, BlockState pState, Random pRandom) {
        if (pLevel.isEmptyBlock(pPos.east()) && pLevel.isEmptyBlock(pPos.west()) && pLevel.isEmptyBlock(pPos.north()) && pLevel.isEmptyBlock(pPos.south())) {
            pLevel.setBlock(pPos, pState.setValue(BPIdeFanBlock.BUDDED, pRandom.nextInt(3) == 1), 2);
        }
    }

    @Override
    protected BlockPlacerType<?> type() {
        return BPBlockPlacers.IDE_FAN_PLACER.get();
    }
}
