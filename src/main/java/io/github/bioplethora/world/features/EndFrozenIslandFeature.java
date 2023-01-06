package io.github.bioplethora.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class EndFrozenIslandFeature extends Feature<NoFeatureConfig> {

    public EndFrozenIslandFeature(Codec<NoFeatureConfig> pCodec) {
        super(pCodec);
    }

    @Override
    public boolean place(ISeedReader world, ChunkGenerator chunkGen, Random rand, BlockPos pos, NoFeatureConfig config) {

        if (rand.nextInt(3) != 1) return false;

        pos = new BlockPos((pos.getX() - 10) + rand.nextInt(20), 40 + rand.nextInt(20), (pos.getZ() - 10) + rand.nextInt(20));

        this.placeLayer(rand.nextInt(7) + 5, Blocks.ICE, world, rand, true,
                pos.offset(-3 + rand.nextInt(6), 0, -3 + rand.nextInt(6)));
        this.placeLayer(rand.nextInt(7) + 6, Blocks.SNOW_BLOCK, world, rand, false,
                pos.offset(-3 + rand.nextInt(6), -1, -3 + rand.nextInt(4)));
        this.placeLayer(rand.nextInt(6) + 5, Blocks.ICE, world, rand, false,
                pos.offset(-3 + rand.nextInt(6), -2, -3 + rand.nextInt(3)));
        this.placeLayer(rand.nextInt(4) + 3, Blocks.ICE, world, rand, false,
                pos.offset(-3 + rand.nextInt(6), -3, -3 + rand.nextInt(3)));

        if (rand.nextInt(3) == 1) {
            this.placeLayer(rand.nextInt(2) + 2, Blocks.ICE, world, rand, false,
                    pos.offset(-3 + rand.nextInt(6), -4, -3 + rand.nextInt(2)));
        }

        return true;
    }

    public void placeLayer(int radius, Block block, ISeedReader world, Random rand, boolean placeIcicle, BlockPos pos) {
        for (int sx = -radius; sx <= radius; sx++) {
            for (int sz = -radius; sz <= radius; sz++) {
                if (sx * sx + sz * sz <= radius * radius) {
                    BlockPos.Mutable newPos = pos.offset(sx, 0, sz).mutable();
                    if (world.isEmptyBlock(newPos)) {
                        setBlock(world, newPos, block.defaultBlockState());
                    }
                }
            }
        }

        if (placeIcicle) {
            EndIcicleFeature.createSpike(world, pos);
        }
    }

    public void placeIcicleLength(ISeedReader world, Random rand, boolean onSides, BlockPos.Mutable mutable) {
        if (canPlaceIcicle(world, mutable)) {
            int length = onSides ? 3 + rand.nextInt(3) : 6 + rand.nextInt(5);
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
    }

    public boolean canPlaceIcicle(ISeedReader world, BlockPos pos) {
        int checkRad = 1;
        for (int x = -checkRad; x < checkRad; x++) {
            for (int z = -checkRad; z < checkRad; z++) {
                BlockPos.Mutable checkPos = pos.mutable().move(x, 0, z);
                if (world.isEmptyBlock(checkPos)) {
                    return false;
                }
            }
        }
        return true;
    }
}
