package io.github.bioplethora.world.worldcarvers;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import io.github.bioplethora.registry.BPBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.carver.CaveWorldCarver;
import net.minecraft.world.gen.carver.NetherCaveCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import org.apache.commons.lang3.mutable.MutableBoolean;

import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;

public class CaeriFormersWorldCarver extends CaveWorldCarver {
    private static final Direction[] DIRECTIONS = Direction.values();

    public CaeriFormersWorldCarver(Codec<ProbabilityConfig> p_i231921_1_) {
        super(p_i231921_1_, 256);
        this.replaceableBlocks = ImmutableSet.of(Blocks.END_STONE, BPBlocks.ENDURION.get(), BPBlocks.CRYOSOIL.get(), BPBlocks.IRION.get());
    }

    protected int getCaveBound() {
        return 10;
    }

    protected float getThickness(Random pRandom) {
        return (pRandom.nextFloat() * 2.0F + pRandom.nextFloat()) * 2.0F;
    }

    protected double getYScale() {
        return 5.0D;
    }

    protected int getCaveY(Random p_230361_1_) {
        return p_230361_1_.nextInt(this.genHeight);
    }

    protected boolean carveBlock(IChunk p_230358_1_, Function<BlockPos, Biome> p_230358_2_, BitSet p_230358_3_, Random p_230358_4_, BlockPos.Mutable p_230358_5_, BlockPos.Mutable p_230358_6_, BlockPos.Mutable p_230358_7_, int p_230358_8_, int p_230358_9_, int p_230358_10_, int p_230358_11_, int p_230358_12_, int p_230358_13_, int p_230358_14_, int p_230358_15_, MutableBoolean p_230358_16_) {
        int i = p_230358_13_ | p_230358_15_ << 4 | p_230358_14_ << 8;
        if (p_230358_3_.get(i)) {
            return false;
        } else {
            p_230358_3_.set(i);
            p_230358_5_.set(p_230358_11_, p_230358_14_, p_230358_12_);
            if (this.canReplaceBlock(p_230358_1_.getBlockState(p_230358_5_))) {
                BlockState blockstate;
                if (p_230358_14_ <= 31) {
                    blockstate = Blocks.END_STONE.defaultBlockState();
                } else {
                    blockstate = CAVE_AIR;
                }

                p_230358_1_.setBlockState(p_230358_5_, blockstate, false);
                return true;
            } else {
                return false;
            }
        }
    }
}
