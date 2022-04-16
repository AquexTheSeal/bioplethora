package io.github.bioplethora.world.blockplacer;

import com.mojang.serialization.Codec;
import io.github.bioplethora.registry.worldgen.BPBlockPlacers;
import net.minecraft.block.BlockState;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.blockplacer.BlockPlacer;
import net.minecraft.world.gen.blockplacer.BlockPlacerType;

import java.util.Random;

public class LavaEdgeBlockPlacer extends BlockPlacer {
    public static final Codec<LavaEdgeBlockPlacer> CODEC;
    public static final LavaEdgeBlockPlacer INSTANCE = new LavaEdgeBlockPlacer();

    @Override
    public void place(IWorld pLevel, BlockPos pPos, BlockState pState, Random pRandom) {

        for (int radY = pPos.getY() - 7; radY <= pPos.getY() + 7; radY++) {
            for (int radX = pPos.getX() - 7; radX <= pPos.getX() + 7; radX++) {
                for (int radZ = pPos.getZ() - 7; radZ <= pPos.getZ() + 7; radZ++) {

                    BlockPos pos = new BlockPos(radX, radY, radZ);

                    if (pLevel.getFluidState(pos).is(FluidTags.LAVA)) {
                        pLevel.setBlock(pPos, pState, 2);
                    }
                }
            }
        }
    }

    @Override
    protected BlockPlacerType<?> type() {
        return BPBlockPlacers.LAVA_EDGE_PLACER.get();
    }

    static {
        CODEC = Codec.unit(() -> INSTANCE);
    }
}
