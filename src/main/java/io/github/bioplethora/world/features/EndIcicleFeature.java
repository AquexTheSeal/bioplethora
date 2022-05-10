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

public class EndIcicleFeature extends Feature<NoFeatureConfig> {

    public EndIcicleFeature(Codec<NoFeatureConfig> pCodec) {
        super(pCodec);
    }

    @Override
    public boolean place(ISeedReader world, ChunkGenerator chunkGen, Random rand, BlockPos pos, NoFeatureConfig config) {

        pos = new BlockPos(pos.getX(), EndIcicleFeature.clampRandom(rand, 60, 40), pos.getZ());

        BlockPos.Mutable mutablePos = pos.mutable();
        // Side Mutable Positions
        BlockPos.Mutable mutablePos1 = pos.mutable().move(1, clampRandom(rand, 6, 2), 0);
        BlockPos.Mutable mutablePos2 = pos.mutable().move(0, clampRandom(rand, 7, 3), 1);
        BlockPos.Mutable mutablePos3 = pos.mutable().move(-1, clampRandom(rand, 8, 2), 0);
        BlockPos.Mutable mutablePos4 = pos.mutable().move(0, clampRandom(rand, 4, 3), -1);

        createPillar(clampRandom(rand, 36, 24), world, rand, mutablePos);

        createPillar(clampRandom(rand, 12, 3), world, rand, mutablePos1);
        createPillar(clampRandom(rand, 17, 4), world, rand, mutablePos2);
        createPillar(clampRandom(rand, 20, 3), world, rand, mutablePos3);
        createPillar(clampRandom(rand, 17, 2), world, rand, mutablePos4);
        return true;
    }

    public void createPillar(int length, ISeedReader world, Random rand, BlockPos.Mutable mutable) {
        for (int i = 0; i < length; i++) {
            if (world.isEmptyBlock(mutable)) {
                if (i < length - 2) {
                    this.setBlock(world, mutable, Blocks.ICE.defaultBlockState());
                } else {
                    this.setBlock(world, mutable, Blocks.SNOW_BLOCK.defaultBlockState());
                }
            }
            mutable.move(Direction.UP);
        }
    }

    public static int clampRandom(Random rand, int randVal, int min) {
        return MathHelper.clamp(rand.nextInt(randVal), min, randVal);
    }
}
