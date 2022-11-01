package io.github.bioplethora.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class EndLandsSpongeFeature extends Feature<NoFeatureConfig> {

    public EndLandsSpongeFeature(Codec<NoFeatureConfig> pCodec) {
        super(pCodec);
    }

    @Override
    public boolean place(ISeedReader world, ChunkGenerator chunkGen, Random rand, BlockPos pos, NoFeatureConfig config) {

        if (rand.nextInt(3) != 1) return false;

        this.placeSponge(5, 16, world, rand, pos);
        this.placeSponge(2, 7, world, rand, pos.offset(-8 + rand.nextInt(5), -2, -8 + rand.nextInt(5)));
        this.placeSponge(3, 9, world, rand, pos.offset(8 - rand.nextInt(6), -2, 8 - rand.nextInt(6)));
        this.placeSponge(1, 8, world, rand, pos.offset(8 + rand.nextInt(4), -2, 8 + rand.nextInt(7)));
        this.placeSponge(2, 7, world, rand, pos.offset(-8 - rand.nextInt(3), -2, -8 - rand.nextInt(8)));
        return true;
    }

    public void placeSponge(int radius, int randomYHeight, ISeedReader world, Random rand, BlockPos pos) {
        if (checkPlacement(world, pos)) {
            int yRand = 4 + rand.nextInt(randomYHeight);
            double radHelper = radius;
            for (int sy = -yRand; sy <= yRand; sy++) {
                radHelper += 0.5D;
                radius = (int) Math.abs(radHelper);
                addCircle(radius, world, rand, pos, sy, yRand);
            }
        }
    }

    public void addCircle(int radius, ISeedReader world, Random rand, BlockPos pos, int sy, int maxY) {
        for (int sx = -radius; sx <= radius; sx++) {
            for (int sz = -radius; sz <= radius; sz++) {
                if (sx * sx + sz * sz <= radius * radius) {
                    BlockPos.Mutable newPos = pos.offset(sx, sy, sz).mutable();
                    if (sy < maxY - 7) {
                        if (sx * sx + sy * sy + sz * sz <= radius * 7) {
                            setBlock(world, newPos, Blocks.WATER.defaultBlockState());
                        } else {
                            if (world.isEmptyBlock(newPos)) {
                                setBlock(world, newPos, Blocks.END_STONE.defaultBlockState());
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean checkPlacement(ISeedReader world, BlockPos pos) {
        int checkRad = 2;
        for (int x = -checkRad; x < checkRad; x++) {
            for (int z = -checkRad; z < checkRad; z++) {
                BlockPos.Mutable checkPos = pos.mutable().move(x, -1, z);
                if (world.isEmptyBlock(checkPos) || world.getBlockState(checkPos).getBlock() instanceof LeavesBlock) {
                    return false;
                }
            }
        }
        for (int x = -1; x < 1; x++) {
            for (int z = -1; z < 1; z++) {
                if (!world.hasChunk(world.getChunk(pos).getPos().x + x, world.getChunk(pos).getPos().z + z)) {
                    return false;
                }
            }
        }
        return true;
    }
}
