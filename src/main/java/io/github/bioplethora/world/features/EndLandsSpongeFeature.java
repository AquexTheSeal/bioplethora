package io.github.bioplethora.world.features;

import com.mojang.serialization.Codec;
import io.github.bioplethora.registry.BPBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class EndLandsSpongeFeature extends Feature<NoFeatureConfig> {

    final boolean isHighlands;

    public EndLandsSpongeFeature(Codec<NoFeatureConfig> pCodec, boolean isHighlands) {
        super(pCodec);
        this.isHighlands = isHighlands;
    }

    @Override
    public boolean place(ISeedReader world, ChunkGenerator chunkGen, Random rand, BlockPos pos, NoFeatureConfig config) {

        if (isHighlands) {
            if (rand.nextInt(3) != 1) return false;

            this.placeSponge(4, 9, world, rand, pos);
            this.placeSponge(2, 4, world, rand, pos.offset(-8 + rand.nextInt(5), -4, -8 + rand.nextInt(5)));
            this.placeSponge(3, 3, world, rand, pos.offset(8 - rand.nextInt(6), -5, 8 - rand.nextInt(6)));
            this.placeSponge(1, 4, world, rand, pos.offset(8 + rand.nextInt(4), -5, 8 + rand.nextInt(7)));
            this.placeSponge(2, 5, world, rand, pos.offset(-8 - rand.nextInt(3), -4, -8 - rand.nextInt(8)));
            return true;
        } else {
            if (rand.nextInt(2) != 1) return false;

            this.placeSponge(7, 4, world, rand, pos);
            this.placeSponge(4, 3, world, rand, pos.offset(-8 + rand.nextInt(5), -1, -8 + rand.nextInt(5)));
            this.placeSponge(3, 2, world, rand, pos.offset(8 - rand.nextInt(6), 0, 8 - rand.nextInt(6)));
            this.placeSponge(4, 2, world, rand, pos.offset(8 + rand.nextInt(4), 0, 8 + rand.nextInt(7)));
            this.placeSponge(2, 2, world, rand, pos.offset(-8 - rand.nextInt(3), -1, -8 - rand.nextInt(8)));
            return true;
        }
    }

    public void placeSponge(int radius, int randomYHeight, ISeedReader world, Random rand, BlockPos pos) {
        if (checkPlacement(world, pos)) {
            int yRand = 4 + rand.nextInt(randomYHeight);
            double radHelper = radius;
            for (int sy = -yRand; sy <= yRand; sy++) {
                radHelper += 0.5D;
                radius = (int) Math.abs(radHelper);
                addCircle(radius, world, rand, pos, sy, -yRand);
            }
        }
    }

    public void addCircle(int radius, ISeedReader world, Random rand, BlockPos pos, int sy, int minY) {
        for (int sx = -radius; sx <= radius; sx++) {
            for (int sz = -radius; sz <= radius; sz++) {
                if (sx * sx + sz * sz <= radius * radius) {
                    BlockPos.Mutable newPos = pos.offset(sx, sy, sz).mutable();
                    if (world.isEmptyBlock(newPos) || world.getBlockState(newPos).getBlock() instanceof LeavesBlock) {
                        setBlock(world, newPos, BPBlocks.ENDURION.get().defaultBlockState());
                    }
                }
            }
        }
        int rad2 = (radius - 1);
        for (int sx = -rad2; sx <= rad2; sx++) {
            for (int sz = -rad2; sz <= rad2; sz++) {
                if (sx * sx + sz * sz <= rad2 * rad2) {
                    BlockPos.Mutable newPos2 = pos.offset(sx, sy, sz).mutable();
                    if (sy == minY) {
                        if (world.isEmptyBlock(newPos2) || world.getBlockState(newPos2).getBlock() instanceof LeavesBlock) {
                            setBlock(world, newPos2, BPBlocks.ENDURION.get().defaultBlockState());
                        }
                    } else {
                        setBlock(world, newPos2, Blocks.WATER.defaultBlockState());
                    }
                }
            }
        }
    }

    public boolean checkPlacement(ISeedReader world, BlockPos pos) {
        int checkRad = 1;
        for (int x = -checkRad; x < checkRad; x++) {
            for (int z = -checkRad; z < checkRad; z++) {
                BlockPos.Mutable checkPos = pos.mutable().move(x, -1, z);
                if (world.isEmptyBlock(checkPos) || world.getBlockState(checkPos).getBlock() instanceof LeavesBlock) {
                    return false;
                }
            }
        }
        for (int x = -3; x < 3; x++) {
            for (int z = -3; z < 3; z++) {
                if (!world.hasChunk(world.getChunk(pos).getPos().x + x, world.getChunk(pos).getPos().z + z)) {
                    return false;
                }
            }
        }
        return world.getBlockState(pos.offset(0, -1, 0)).getBlock() == Blocks.END_STONE ||
                world.getBlockState(pos.offset(0, -1, 0)).getBlock() == BPBlocks.ENDURION.get();
    }
}
