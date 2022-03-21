package io.github.bioplethora.world.features;

import com.mojang.serialization.Codec;
import io.github.bioplethora.world.BPFeatureGeneration;
import io.github.bioplethora.world.feature_config.PendentBlocksFeatureConfig;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;

import java.util.Random;

public class PendentFleignariteFeature extends PendentBlocksFeature {

    public PendentFleignariteFeature(Codec<PendentBlocksFeatureConfig> config) {
        super(config);
    }

    @Override
    public boolean place(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, PendentBlocksFeatureConfig config) {
        if (!world.isEmptyBlock(pos)) {
            return false;
        } else {
            if (BPFeatureGeneration.isFleignariteChunk(pos, world)) {
                if (!validPlace(world, pos, config)) {
                    return false;
                } else {
                    this.generateTopPart(world, rand, pos, config);
                    this.generatePendentsInSurroundings(world, rand, pos, config);
                    return true;
                }
            } else {
                return false;
            }
        }
    }

    public boolean validPlace(ISeedReader world, BlockPos pos, PendentBlocksFeatureConfig config) {

        BlockState blockstateTop2 = world.getBlockState(pos.above(2));
        BlockState blockstateTop = world.getBlockState(pos.above());
        BlockState blockstate = world.getBlockState(pos);
        BlockState blockstateBot = world.getBlockState(pos);

        return config.getWhitelist().contains(blockstateTop.getBlock()) &&
                config.getWhitelist().contains(blockstateTop2.getBlock()) &&
                blockstate.isAir() &&
                blockstateBot.isAir();
    }
}
