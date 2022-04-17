package io.github.bioplethora.world.blockplacer;

import com.mojang.serialization.Codec;
import io.github.bioplethora.blocks.SmallMushroomBlock;
import io.github.bioplethora.registry.worldgen.BPBlockPlacers;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.blockplacer.BlockPlacer;
import net.minecraft.world.gen.blockplacer.BlockPlacerType;

import java.util.Random;

public class MinishroomBlockPlacer extends BlockPlacer {
    public static final Codec<MinishroomBlockPlacer> CODEC;
    public static final MinishroomBlockPlacer INSTANCE = new MinishroomBlockPlacer();

    @Override
    public void place(IWorld pLevel, BlockPos pPos, BlockState pState, Random pRandom) {
        pLevel.setBlock(pPos, pState
                .setValue(SmallMushroomBlock.MINISHROOMS, 1 + pRandom.nextInt(3))
                        .setValue(HorizontalBlock.FACING, getPlacementDirection(pRandom)),
                2);
    }

    public Direction getPlacementDirection(Random pRandom) {
        switch (pRandom.nextInt(4)) {
            default:
                return Direction.NORTH;
            case 1:
                return Direction.SOUTH;
            case 2:
                return Direction.EAST;
            case 3:
                return Direction.WEST;
        }
    }

    @Override
    protected BlockPlacerType<?> type() {
        return BPBlockPlacers.MINISHROOM_PLACER.get();
    }

    static {
        CODEC = Codec.unit(() -> INSTANCE);
    }
}
