package io.github.bioplethora.world.features;

import com.mojang.serialization.Codec;
import io.github.bioplethora.registry.BPBlocks;
import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class EndLandsCavernFeature extends Feature<NoFeatureConfig> {

    public EndLandsCavernFeature(Codec<NoFeatureConfig> pCodec) {
        super(pCodec);
    }

    @Override
    public boolean place(ISeedReader world, ChunkGenerator chunkGen, Random rand, BlockPos pos, NoFeatureConfig config) {
        pos = new BlockPos(pos.getX(), 20 + rand.nextInt(20), pos.getZ());
        int radius = 7 + rand.nextInt(11);
        for (int sx = -radius; sx <= radius; sx++) {
            for (int sy = -radius; sy <= radius; sy++) {
                for (int sz = -radius; sz <= radius; sz++) {
                    if (sx * sx + sy * sy + sz * sz <= radius * radius) {
                        BlockPos.Mutable tPos = pos.offset(sx, sy, sz).mutable();
                        if (!world.getBlockState(tPos).isAir()) {
                            if (replaceWithEndurion(world, tPos)) {
                                this.setBlock(world, tPos, BPBlocks.ENDURION.get().defaultBlockState());
                            } else {
                                this.setBlock(world, tPos, Blocks.WATER.defaultBlockState());
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    public boolean replaceWithEndurion(ISeedReader world, BlockPos pos) {
        return world.getBlockState(pos.offset(1, 0, 0)).isAir() || world.getBlockState(pos.offset(0, 0, 1)).isAir() || world.getBlockState(pos.offset(-1, 0, 0)).isAir() || world.getBlockState(pos.offset(0, 0, -1)).isAir() || world.getBlockState(pos.offset(0, -1, 0)).isAir();
    }
}
