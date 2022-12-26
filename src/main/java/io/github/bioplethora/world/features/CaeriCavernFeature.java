package io.github.bioplethora.world.features;

import com.mojang.serialization.Codec;
import io.github.bioplethora.registry.BPBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class CaeriCavernFeature extends Feature<NoFeatureConfig> {

    public CaeriCavernFeature(Codec<NoFeatureConfig> pCodec) {
        super(pCodec);
    }

    @Override
    public boolean place(ISeedReader world, ChunkGenerator chunkGen, Random rand, BlockPos pos, NoFeatureConfig config) {
        pos = new BlockPos(pos.getX(), 20 + rand.nextInt(25), pos.getZ());
        int radius = 6 + rand.nextInt(9);
        for (int sx = -(radius + 2); sx <= (radius + 2); sx++) {
            for (int sy = -(radius + 2); sy <= (radius + 2); sy++) {
                for (int sz = -(radius + 2); sz <= (radius + 2); sz++) {
                    if (sx * sx + sy * sy + sz * sz <= (radius + 2) *(radius + 2)) {
                        BlockPos.Mutable tPos = pos.offset(sx, sy, sz).mutable();
                        if (world.getBlockState(tPos).getBlock() == Blocks.END_STONE) {
                            this.setBlock(world, tPos, Blocks.BLACKSTONE.defaultBlockState());
                        }
                    }
                }
            }
        }
        for (int sx = -radius; sx <= radius; sx++) {
            for (int sy = -radius; sy <= radius; sy++) {
                for (int sz = -radius; sz <= radius; sz++) {
                    if (sx * sx + sy * sy + sz * sz <= radius * radius) {
                        BlockPos.Mutable tPos = pos.offset(sx, sy, sz).mutable();
                        if (replaceWithEndStone(world, tPos)) {
                            this.setBlock(world, tPos, BPBlocks.CRYOSOIL.get().defaultBlockState());
                        } else {
                            this.setBlock(world, tPos, Blocks.AIR.defaultBlockState());
                        }
                    }
                }
            }
        }
        return true;
    }

    public boolean replaceWithEndStone(ISeedReader world, BlockPos pos) {
        return world.getBlockState(pos.offset(1, 0, 0)).getBlock() == Blocks.WATER ||
                world.getBlockState(pos.offset(0, 0, 1)).getBlock() == Blocks.WATER ||
                world.getBlockState(pos.offset(-1, 0, 0)).getBlock() == Blocks.WATER ||
                world.getBlockState(pos.offset(0, 0, -1)).getBlock() == Blocks.WATER ||
                world.getBlockState(pos.offset(0, -1, 0)).getBlock() == Blocks.WATER;
    }
}
