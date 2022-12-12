package io.github.bioplethora.world.blockplacers;

import com.mojang.serialization.Codec;
import io.github.bioplethora.api.world.BlockUtils;
import io.github.bioplethora.registry.worldgen.BPBlockPlacers;
import net.minecraft.block.BlockState;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.blockplacer.BlockPlacer;
import net.minecraft.world.gen.blockplacer.BlockPlacerType;

import java.util.Random;

public class LavaEdgeBlockPlacer extends BlockPlacer {
    public static final LavaEdgeBlockPlacer INSTANCE = new LavaEdgeBlockPlacer();
    public static final Codec<LavaEdgeBlockPlacer> CODEC = Codec.unit(() -> INSTANCE);

    @Override
    public void place(IWorld pLevel, BlockPos pPos, BlockState pState, Random pRandom) {

        if (BlockUtils.checkNearestTaggedFluid(checkForLiquid(pPos), pLevel, FluidTags.LAVA)) {
            pLevel.setBlock(pPos, pState, 2);
        }
    }

    public AxisAlignedBB checkForLiquid(BlockPos pPos) {
        int areaint = 14;
        double x = pPos.getX(), y = pPos.getY(), z = pPos.getZ();
        return new AxisAlignedBB(
                x - (areaint / 2d), y - (areaint / 2d), z - (areaint / 2d),
                x + (areaint / 2d), y + (areaint / 2d), z + (areaint / 2d)
        );
    }

    @Override
    protected BlockPlacerType<?> type() {
        return BPBlockPlacers.LAVA_EDGE_PLACER.get();
    }
}
