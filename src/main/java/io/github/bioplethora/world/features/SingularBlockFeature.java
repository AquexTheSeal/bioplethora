package io.github.bioplethora.world.features;

import com.mojang.serialization.Codec;
import io.github.bioplethora.blocks.BPLanternPlantBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class SingularBlockFeature extends Feature<BlockClusterFeatureConfig> {

    public SingularBlockFeature(Codec<BlockClusterFeatureConfig> pCodec) {
        super(pCodec);
    }

    @Override
    public boolean place(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, BlockClusterFeatureConfig config) {
        BlockState state = config.stateProvider.getState(rand, pos);

        if (state.getBlock() instanceof DoublePlantBlock) {

            if (state.getBlock() instanceof BPLanternPlantBlock) {
                if (pos.getY() >= 55 && world.isEmptyBlock(pos) && world.isEmptyBlock(pos.above()) && world.isEmptyBlock(pos.above().above())) {
                    ((BPLanternPlantBlock) state.getBlock()).placeAt(world, pos, 3);
                    return true;
                } else {
                    return false;
                }
            } else {
                if (world.isEmptyBlock(pos) && world.isEmptyBlock(pos.above())) {
                    ((DoublePlantBlock) state.getBlock()).placeAt(world, pos, 3);
                    return true;
                } else {
                    return false;
                }
            }

        } else if (world.isEmptyBlock(pos)) {
            this.setBlock(world, pos, state);
            return true;

        } else {
            return false;
        }
    }
}
