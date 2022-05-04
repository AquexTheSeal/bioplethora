package io.github.bioplethora.world.features;

import com.mojang.serialization.Codec;
import io.github.bioplethora.world.featureconfigs.ChangedSurfaceFeatureConfig;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class ChangedSurfaceFeature extends Feature<ChangedSurfaceFeatureConfig> {

    public ChangedSurfaceFeature(Codec<ChangedSurfaceFeatureConfig> pCodec) {
        super(pCodec);
    }

    @Override
    public boolean place(ISeedReader seedReader, ChunkGenerator generator, Random random, BlockPos pos, ChangedSurfaceFeatureConfig config) {
        int i = 0;

        for (int j = 0; j < 16 + random.nextInt(16); ++j) {
            BlockPos blockpos = this.getPos(random, pos, config);
            if (seedReader.isEmptyBlock(blockpos) && blockpos.getY() < 255 && config.getWhitelist().contains(seedReader.getBlockState(pos.below()).getBlock())) {

                if (Math.random() < 0.75) {
                    setBlock(seedReader, pos.below(), config.getCommon());
                } else {
                    setBlock(seedReader, pos.below(), config.getUncommon());
                }
                generateSurrounding(seedReader, random, pos, config);

                ++i;
            }
        }

        return i > 0;
    }

    public void generateSurrounding(ISeedReader seedReader, Random random, BlockPos pos, ChangedSurfaceFeatureConfig config) {
        for (int j = 0; j < 12 + random.nextInt(16); ++j) {
            BlockPos blockpos = this.getPos(random, pos, config);
            if (seedReader.isEmptyBlock(blockpos) && blockpos.getY() < 255 && config.getWhitelist().contains(seedReader.getBlockState(pos).getBlock())) {

                if (Math.random() < 0.75) {
                    setBlock(seedReader, pos.below(), config.getCommon());
                } else {
                    setBlock(seedReader, pos.below(), config.getUncommon());
                }
            }
        }
    }

    public BlockPos getPos(Random pRandom, BlockPos pPos, ChangedSurfaceFeatureConfig pConfig) {
        return pPos.offset(
                pRandom.nextInt(16) - pRandom.nextInt(16),
                pRandom.nextInt(16) - pRandom.nextInt(16),
                pRandom.nextInt(16) - pRandom.nextInt(16)
        );
    }
}
