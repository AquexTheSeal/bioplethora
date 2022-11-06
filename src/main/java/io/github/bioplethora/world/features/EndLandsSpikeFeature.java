package io.github.bioplethora.world.features;

import com.mojang.serialization.Codec;
import io.github.bioplethora.registry.BPBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.ChorusPlantFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;

import java.util.Random;

public class EndLandsSpikeFeature extends Feature<NoFeatureConfig> {

    public EndLandsSpikeFeature(Codec<NoFeatureConfig> pCodec) {
        super(pCodec);
    }

    @Override
    public boolean place(ISeedReader world, ChunkGenerator chunkGen, Random rand, BlockPos pos, NoFeatureConfig config) {

        this.placeSponge(3, world, rand, pos);
        this.placeSponge(1, world, rand, pos.offset(-6 + rand.nextInt(5), -(4 - rand.nextInt(3)), -4 + rand.nextInt(4)));
        this.placeSponge(2, world, rand, pos.offset(6 - rand.nextInt(6), -(3 - rand.nextInt(2)), 2 - rand.nextInt(5)));
        this.placeSponge(1, world, rand, pos.offset(6 + rand.nextInt(4), -(2 - rand.nextInt(3)), 3 + rand.nextInt(4)));
        this.placeSponge(2, world, rand, pos.offset(-6 - rand.nextInt(3), -(3 - rand.nextInt(3)), -3 - rand.nextInt(3)));
        return true;
    }

    public void placeSponge(int radius, ISeedReader world, Random rand, BlockPos pos) {
        if (checkPlacement(world, pos)) {
            int yRand = 24;
            double radHelper = radius;
            for (int sy = 0; sy >= -yRand; sy--) {
                radHelper -= 0.25D;
                radius = (int) radHelper;
                addCircle(radius, world, rand, pos, sy);
            }
        }
    }

    public void addCircle(int radius, ISeedReader world, Random rand, BlockPos pos, int sy) {
        for (int sx = -radius; sx <= radius; sx++) {
            for (int sz = -radius; sz <= radius; sz++) {
                if (sx * sx + sz * sz <= radius * radius) {
                    BlockPos.Mutable newPos = pos.offset(sx, sy, sz).mutable();
                    if (world.isEmptyBlock(newPos) || world.getBlockState(newPos).getBlock() instanceof LeavesBlock) {
                        Block p0 = world.getBlockState(pos).getBlock(), p1 = world.getBlockState(pos.offset(0, 1, 0)).getBlock();

                        if (p0 == BPBlocks.ENDURION.get() || p1 == BPBlocks.ENDURION.get() || p0 == Blocks.WATER || p1 == Blocks.WATER) {
                            setBlock(world, newPos, BPBlocks.ENDURION.get().defaultBlockState());
                        } else {
                            setBlock(world, newPos, Blocks.END_STONE.defaultBlockState());
                        }
                    }
                }
            }
        }
        int rad2 = (radius - 1);
        for (int sx = -rad2; sx <= rad2; sx++) {
            for (int sz = -rad2; sz <= rad2; sz++) {
                if (sx * sx + sz * sz <= rad2 * rad2) {
                    BlockPos.Mutable newPos2 = pos.offset(sx, sy, sz).mutable();
                    if (world.isEmptyBlock(newPos2) || world.getBlockState(newPos2).getBlock() instanceof LeavesBlock) {
                        setBlock(world, newPos2, BPBlocks.ENDURION.get().defaultBlockState());
                    }
                }
            }
        }
    }

    public boolean checkPlacement(ISeedReader world, BlockPos pos) {
        int checkRad = 2;
        for (int x = -checkRad; x < checkRad; x++) {
            for (int z = -checkRad; z < checkRad; z++) {
                BlockPos.Mutable checkPos = pos.mutable().move(x, 1, z);
                if (world.isEmptyBlock(checkPos) || world.getBlockState(checkPos).getBlock() instanceof LeavesBlock) {
                    return false;
                }
            }
        }
        return !world.isEmptyBlock(pos) && world.isEmptyBlock(pos.offset(0, -3, 0));
    }
}
