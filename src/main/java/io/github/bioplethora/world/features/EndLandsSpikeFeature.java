package io.github.bioplethora.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class EndLandsSpikeFeature extends Feature<NoFeatureConfig> {

    public EndLandsSpikeFeature(Codec<NoFeatureConfig> pCodec) {
        super(pCodec);
    }

    @Override
    public boolean place(ISeedReader world, ChunkGenerator chunkGen, Random rand, BlockPos pos, NoFeatureConfig config) {
        if (world.isEmptyBlock(pos) && !world.isEmptyBlock(pos.below())) {
            pos = pos.below();
            BlockPos.Mutable mutablePos = pos.mutable();
            // Side Mutable Positions
            BlockPos.Mutable mutablePos1 = pos.mutable().move(1, 0, 0);
            BlockPos.Mutable mutablePos2 = pos.mutable().move(0, 0, 1);
            BlockPos.Mutable mutablePos3 = pos.mutable().move(-1, 0, 0);
            BlockPos.Mutable mutablePos4 = pos.mutable().move(0, 0, -1);

            createPillar(clampRandom(rand, 16, 6), world, rand, mutablePos);

            createPillar(clampRandom(rand, 6, 3), world, rand, mutablePos1);
            createPillar(clampRandom(rand, 7, 4), world, rand, mutablePos2);
            createPillar(clampRandom(rand, 8, 3), world, rand, mutablePos3);
            createPillar(clampRandom(rand, 7, 2), world, rand, mutablePos4);
            return true;
        } else return false;
    }

    public void createPillar(int length, ISeedReader world, Random rand, BlockPos.Mutable mutable) {
        for (int i = 0; i < length; i++) {
            if (world.isEmptyBlock(mutable)) {
                this.setBlock(world, mutable, Blocks.END_STONE.defaultBlockState());
            }
            mutable.move(Direction.UP);
        }
    }

    public int clampRandom(Random rand, int initVal, int min) {
        return MathHelper.clamp(rand.nextInt(initVal), min, initVal);
    }
}
