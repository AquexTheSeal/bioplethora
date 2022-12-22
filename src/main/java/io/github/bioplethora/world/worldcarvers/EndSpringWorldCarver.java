package io.github.bioplethora.world.worldcarvers;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import io.github.bioplethora.registry.BPBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.carver.CaveWorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import org.apache.commons.lang3.mutable.MutableBoolean;

import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;

public class EndSpringWorldCarver extends CaveWorldCarver {
    private static final Direction[] DIRECTIONS = Direction.values();

    public EndSpringWorldCarver(Codec<ProbabilityConfig> p_i231921_1_) {
        super(p_i231921_1_, 256);
        this.replaceableBlocks = ImmutableSet.of(Blocks.END_STONE, BPBlocks.ENDURION.get(), BPBlocks.CRYOSOIL.get(), BPBlocks.IRION.get());
    }

    @Override
    protected double getYScale() {
        return 10D;
    }

    protected int getCaveBound() {
        return 20;
    }

    protected int getCaveY(Random p_230361_1_) {
        return p_230361_1_.nextInt(p_230361_1_.nextInt(60) + 8);
    }

    protected float getThickness(Random pRandom) {
        return (pRandom.nextFloat() * 4.0F + pRandom.nextFloat()) * 4.0F;
    }

    protected boolean carveBlock(IChunk level, Function<BlockPos, Biome> pred, BitSet bitset, Random rand, BlockPos.Mutable pos, BlockPos.Mutable pos2, BlockPos.Mutable pos3, int p_230358_8_, int p_230358_9_, int p_230358_10_, int p_230358_11_, int p_230358_12_, int p_230358_13_, int p_230358_14_, int p_230358_15_, MutableBoolean p_230358_16_) {
        int i = p_230358_13_ | p_230358_15_ << 4 | p_230358_14_ << 8;
        if (bitset.get(i)) {
            return false;
        } else {
            bitset.set(i);
            pos.set(p_230358_11_, p_230358_14_, p_230358_12_);
            if (this.canReplaceBlock(level.getBlockState(pos))) {
                BlockState blockstate;

                if (checkIfClear(level, pos)) {
                    blockstate = WATER.createLegacyBlock();
                } else {
                    blockstate = BPBlocks.ENDURION.get().defaultBlockState();
                }

                level.setBlockState(pos, blockstate, false);
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean checkIfClear(IChunk pLevel, BlockPos pos) {
        for (Direction direction : DIRECTIONS) {
            boolean flag = pLevel.getBlockState(pos.relative(direction)).isAir();
            if (flag && direction != Direction.UP) {
                return false;
            }
        }
        return true;
    }
}
