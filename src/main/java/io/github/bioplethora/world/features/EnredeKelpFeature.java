package io.github.bioplethora.world.features;

import com.mojang.serialization.Codec;
import io.github.bioplethora.registry.BPBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.KelpTopBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class EnredeKelpFeature extends Feature<NoFeatureConfig> {

    public EnredeKelpFeature(Codec<NoFeatureConfig> pCodec) {
        super(pCodec);
    }

    @Override
    public boolean place(ISeedReader world, ChunkGenerator chunkGen, Random rand, BlockPos pos, NoFeatureConfig config) {

        int i = 0;
        int j = world.getHeight(Heightmap.Type.OCEAN_FLOOR, pos.getX(), pos.getZ());
        BlockPos blockpos = new BlockPos(pos.getX(), j, pos.getZ());
        if (world.getBlockState(blockpos).is(Blocks.WATER)) {
            BlockState blockstate = BPBlocks.ENREDE_KELP.get().defaultBlockState();
            BlockState blockstate1 = BPBlocks.ENREDE_KELP_PLANT.get().defaultBlockState();
            int k = 3 + rand.nextInt(10);

            for(int l = 0; l <= k; ++l) {
                if (world.getBlockState(blockpos).is(Blocks.WATER) && world.getBlockState(blockpos.above()).is(Blocks.WATER) && blockstate1.canSurvive(world, blockpos)) {
                    if (l == k) {
                        world.setBlock(blockpos, blockstate.setValue(KelpTopBlock.AGE, rand.nextInt(4) + 20), 2);
                        ++i;
                    } else {
                        world.setBlock(blockpos, blockstate1, 2);
                    }
                } else if (l > 0) {
                    BlockPos blockpos1 = blockpos.below();
                    if (blockstate.canSurvive(world, blockpos1) && !world.getBlockState(blockpos1.below()).is(BPBlocks.ENREDE_KELP.get())) {
                        world.setBlock(blockpos1, blockstate.setValue(KelpTopBlock.AGE, rand.nextInt(4) + 20), 2);
                        ++i;
                    }
                    break;
                }

                blockpos = blockpos.above();
            }
        }

        return i > 0;
    }
}
