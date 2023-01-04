package io.github.bioplethora.world.features;

import com.mojang.serialization.Codec;
import io.github.bioplethora.api.world.BlockUtils;
import io.github.bioplethora.registry.BPBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.pathfinding.PathType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.OctavesNoiseGenerator;
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
        boolean isOpenAir = rand.nextBoolean();
        int radius = 7 + rand.nextInt(7);

        for (int sy = -(radius + 2); sy <= (radius + 2); sy++) {
            for (int sx = -(radius + 2); sx <= (radius + 2); sx++) {
                for (int sz = -(radius + 2); sz <= (radius + 2); sz++) {
                    if (sx * sx + sy * sy + sz * sz <= (radius + 2) * (radius + 2)) {
                        BlockPos.Mutable tPos = pos.offset(sx, sy, sz).mutable();
                        if (world.getBlockState(tPos).is(Blocks.END_STONE)) {
                            this.setBlock(world, tPos, BPBlocks.TENEDEBRIS.get().defaultBlockState());
                        }
                    }
                }
            }
        }
        for (int sy = -radius; sy <= radius; sy++) {
            for (int sx = -radius; sx <= radius; sx++) {
                for (int sz = -radius; sz <= radius; sz++) {
                    if (sx * sx + sy * sy + sz * sz <= radius * radius) {
                        BlockPos.Mutable tPos = pos.offset(sx, sy, sz).mutable();
                        if (!world.isEmptyBlock(tPos) && !world.isWaterAt(tPos)) {
                            if (replaceWithStone(world, tPos, isOpenAir)) {
                                this.setBlock(world, tPos, BPBlocks.TENEDEBRIS.get().defaultBlockState());
                            } else {
                                this.setBlock(world, tPos, Blocks.AIR.defaultBlockState());
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    public boolean replaceWithStone(ISeedReader world, BlockPos pos, boolean isOpenAir) {
        boolean airFlag = world.isEmptyBlock(pos.east()) || world.isEmptyBlock(pos.west()) || world.isEmptyBlock(pos.south()) || world.isEmptyBlock(pos.north()) || world.isEmptyBlock(pos.below());
        boolean waterFlag = world.isWaterAt(pos.east()) || world.isWaterAt(pos.west()) || world.isWaterAt(pos.south()) || world.isWaterAt(pos.north()) || world.isWaterAt(pos.below());
        if (isOpenAir) {
            return airFlag || waterFlag;
        }
        return waterFlag;
    }
}
