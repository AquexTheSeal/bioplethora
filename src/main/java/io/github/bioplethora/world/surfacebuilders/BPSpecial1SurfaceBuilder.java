package io.github.bioplethora.world.surfacebuilders;

import com.mojang.serialization.Codec;
import io.github.bioplethora.registry.BPBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.DefaultSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

import java.util.Random;

public class BPSpecial1SurfaceBuilder extends DefaultSurfaceBuilder {

    public BPSpecial1SurfaceBuilder(Codec<SurfaceBuilderConfig> p_i232124_1_) {
        super(p_i232124_1_);
    }

    @Override
    protected void apply(Random p_206967_1_, IChunk p_206967_2_, Biome p_206967_3_, int p_206967_4_, int p_206967_5_, int p_206967_6_, double p_206967_7_, BlockState p_206967_9_, BlockState p_206967_10_, BlockState p_206967_11_, BlockState p_206967_12_, BlockState p_206967_13_, int p_206967_14_) {
        BlockState blockstate = p_206967_11_;
        BlockState blockstate1 = p_206967_12_;
        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
        int i = -1;
        int j = (int)(p_206967_7_ / 3.0D + 3.0D + p_206967_1_.nextDouble() * 0.25D);
        int k = p_206967_4_ & 15;
        int l = p_206967_5_ & 15;

        for(int i1 = p_206967_6_; i1 >= 0; --i1) {
            blockpos$mutable.set(k, i1, l);
            BlockState blockstate2 = p_206967_2_.getBlockState(blockpos$mutable);
            if (blockstate2.isAir()) {
                i = -1;
            } else if (blockstate2.is(p_206967_9_.getBlock())) {
                if (i == -1) {
                    if (j <= 0) {
                        blockstate = Blocks.AIR.defaultBlockState();
                        blockstate1 = p_206967_9_;
                    } else if (i1 >= p_206967_14_ - 4 && i1 <= p_206967_14_ + 1) {
                        blockstate = p_206967_11_;
                        blockstate1 = p_206967_12_;
                    }

                    if (i1 < p_206967_14_ && blockstate.isAir()) {
                        blockstate = p_206967_10_;
                        blockpos$mutable.set(k, i1, l);
                    }

                    i = j;
                    if (i1 >= p_206967_14_ - 1) {
                        p_206967_2_.setBlockState(blockpos$mutable, blockstate, false);
                        clearBelow(p_206967_2_, blockpos$mutable, blockstate, blockstate1);
                    } else if (i1 < p_206967_14_ - 7 - j) {
                        blockstate = Blocks.AIR.defaultBlockState();
                        blockstate1 = p_206967_9_;
                        p_206967_2_.setBlockState(blockpos$mutable, p_206967_13_, false);
                        clearBelow(p_206967_2_, blockpos$mutable, blockstate, blockstate1);
                    } else {
                        p_206967_2_.setBlockState(blockpos$mutable, blockstate1, false);
                        clearBelow(p_206967_2_, blockpos$mutable, blockstate, blockstate1);
                    }
                }
            }
        }
    }

    public void clearBelow(IChunk p_206967_2_, BlockPos.Mutable  blockpos$mutable, BlockState solid, BlockState liquid) {
        for (int yf = -1; yf > -20; yf--) {
            BlockPos blockpos = blockpos$mutable.offset(0, yf, 0);
                if (replaceWithWater(p_206967_2_, blockpos)) {
                p_206967_2_.setBlockState(blockpos$mutable, liquid, false);
            } else {
                p_206967_2_.setBlockState(blockpos$mutable, solid, false);
            }
        }
    }

    public boolean replaceWithWater(IChunk world, BlockPos pos) {
        if (world.getBlockState(pos.offset(1, 0, 0)).isAir() || world.getBlockState(pos.offset(0, 0, 1)).isAir() || world.getBlockState(pos.offset(-1, 0, 0)).isAir() || world.getBlockState(pos.offset(0, 0, -1)).isAir() || world.getBlockState(pos.offset(0, -1, 0)).isAir()) {
            return false;
        }
        return true;
    }
}
