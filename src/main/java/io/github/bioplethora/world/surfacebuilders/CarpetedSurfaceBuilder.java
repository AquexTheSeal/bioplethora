package io.github.bioplethora.world.surfacebuilders;

import com.mojang.serialization.Codec;
import io.github.bioplethora.world.surfacebuilderconfigs.CarpetedSurfaceBuilderConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

import java.util.Random;

public class CarpetedSurfaceBuilder extends SurfaceBuilder<CarpetedSurfaceBuilderConfig> {

    public CarpetedSurfaceBuilder(Codec<CarpetedSurfaceBuilderConfig> codec) {
        super(codec);
    }

    public void apply(Random random, IChunk chunk, Biome biome, int x, int z, int height, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, CarpetedSurfaceBuilderConfig config) {
        this.apply(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, config.getTopMaterial(), config.getCarpet(), config.getUnderMaterial(), config.getUnderwaterMaterial(), seaLevel);
    }

    protected void apply(Random random, IChunk chunk, Biome biome, int x, int z, int height, double noise, BlockState defaultBlock, BlockState defaultFluid, BlockState topMaterial, BlockState carpet, BlockState underMaterial, BlockState underwaterMaterial, int seaLevel) {

        BlockState state = topMaterial;
        BlockState state1 = carpet;
        BlockState state2 = underMaterial;

        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
        int i = -1;
        int j = (int)(noise / 3.0D + 3.0D + random.nextDouble() * 0.25D);
        int k = x & 15;
        int l = z & 15;

        for(int i1 = height; i1 >= 0; --i1) {
            blockpos$mutable.set(k, i1, l);
            BlockState blockstate2 = chunk.getBlockState(blockpos$mutable);
            if (blockstate2.isAir()) {
                i = -1;
            } else if (blockstate2.is(defaultBlock.getBlock())) {
                if (i == -1) {
                    if (j <= 0) {
                        state = Blocks.AIR.defaultBlockState();
                        state1 = Blocks.AIR.defaultBlockState();
                        state2 = defaultBlock;
                    } else if (i1 >= seaLevel - 4 && i1 <= seaLevel + 1) {
                        state = topMaterial;
                        state1 = carpet;
                        state2 = underMaterial;
                    }

                    if (i1 < seaLevel && (state == null || state1 == null || state.isAir())) {
                        if (biome.getTemperature(blockpos$mutable.set(x, i1, z)) < 0.15F) {
                            state = Blocks.ICE.defaultBlockState();
                            state1 = Blocks.AIR.defaultBlockState();
                        } else {
                            state = defaultFluid;
                            state1 = defaultFluid;
                        }

                        blockpos$mutable.set(k, i1, l);
                    }

                    i = j;
                    if (i1 >= seaLevel - 1) {

                        chunk.setBlockState(blockpos$mutable, state, false);
                        if (random.nextInt(4) == 1) {
                            if (chunk.getBlockState(blockpos$mutable.offset(0, 1, 0)).isAir()) {
                                chunk.setBlockState(blockpos$mutable.offset(0, 1, 0), state1, false);
                            }
                        }

                    } else if (i1 < seaLevel - 7 - j) {
                        state = Blocks.AIR.defaultBlockState();
                        state1 = Blocks.AIR.defaultBlockState();
                        state2 = defaultBlock;
                        chunk.setBlockState(blockpos$mutable, underwaterMaterial, false);
                    } else {
                        chunk.setBlockState(blockpos$mutable, state2, false);
                    }
                } else if (i > 0) {
                    --i;
                    chunk.setBlockState(blockpos$mutable, state2, false);
                    if (i == 0 && state2.is(Blocks.SAND) && j > 1) {
                        i = random.nextInt(4) + Math.max(0, i1 - 63);
                        state2 = state2.is(Blocks.RED_SAND) ? Blocks.RED_SANDSTONE.defaultBlockState() : Blocks.SANDSTONE.defaultBlockState();
                    }
                }
            }
        }

    }
}
