package io.github.bioplethora.world.blockplacers;

import com.mojang.serialization.Codec;
import io.github.bioplethora.api.world.BlockUtils;
import io.github.bioplethora.registry.worldgen.BPBlockPlacers;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.blockplacer.BlockPlacer;
import net.minecraft.world.gen.blockplacer.BlockPlacerType;
import net.minecraft.world.gen.blockplacer.DoublePlantBlockPlacer;

import java.util.Random;

import static net.minecraft.block.DoublePlantBlock.HALF;
import static net.minecraft.state.properties.BlockStateProperties.WATERLOGGED;

public class WaterloggedDoublePlantBlockPlacer extends BlockPlacer {
    public static final WaterloggedDoublePlantBlockPlacer INSTANCE = new WaterloggedDoublePlantBlockPlacer();
    public static final Codec<WaterloggedDoublePlantBlockPlacer> CODEC = Codec.unit(() -> INSTANCE);

    @Override
    public void place(IWorld pLevel, BlockPos pPos, BlockState pState, Random pRandom) {
        if (pLevel.isWaterAt(pPos) && pLevel.isWaterAt(pPos.above())) {
            pLevel.setBlock(pPos, pState.setValue(HALF, DoubleBlockHalf.LOWER).setValue(WATERLOGGED, true), 2);
            pLevel.setBlock(pPos.above(), pState.setValue(HALF, DoubleBlockHalf.UPPER).setValue(WATERLOGGED, true), 2);
        }
    }

    @Override
    protected BlockPlacerType<?> type() {
        return BPBlockPlacers.WATERLOGGED_DOUBLE_PLANT.get();
    }
}
