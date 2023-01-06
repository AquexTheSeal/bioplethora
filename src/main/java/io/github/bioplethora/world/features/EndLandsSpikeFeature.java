package io.github.bioplethora.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class EndLandsSpikeFeature extends Feature<NoFeatureConfig> {

    public EndLandsSpikeFeature(Codec<NoFeatureConfig> pCodec) {
        super(pCodec);
    }

    @Override
    public boolean place(ISeedReader world, ChunkGenerator chunkGen, Random rand, BlockPos pos, NoFeatureConfig config) {

        boolean large = world.getRandom().nextInt(5) == 0;
        int tipMin = (int) ((large ? 25 : 10) * 0.7);
        int tipRand = (int) ((large ? 35 : 20) * 0.6);
        int radiusMin = large ? 4 : 2;
        int radiusRand = large ? 6 : 3;

        pos = new BlockPos(pos.getX(), 2, pos.getZ());
        while (world.isEmptyBlock(pos) && pos.getY() < 100) {
            pos = pos.above();
        }

        if (!world.getBlockState(pos.above()).is(Blocks.END_STONE)) {
            return false;
        }

        int tip = tipMin + world.getRandom().nextInt(tipRand);
        int topX = world.getRandom().nextInt(tip) - tip / 2;
        int topZ = world.getRandom().nextInt(tip) - tip / 2;

        int radius = radiusMin + world.getRandom().nextInt(radiusRand);
        Vector3d to = new Vector3d(pos.getX() + topX, pos.getY() - tip, pos.getZ() + topZ);

        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                double fromCenter = Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2));
                if(fromCenter <= radius) {
                    Vector3d from = new Vector3d(pos.getX() + x, pos.getY(), pos.getZ() + z);

                    if(world.getBlockState(new BlockPos(from).below()).isAir()) {
                        continue;
                    }

                    Vector3d per = to.subtract(from).normalize();
                    Vector3d current = from.add(0, 0, 0);
                    double distance = from.distanceTo(to);

                    for (double i = 0; i < distance; i++) {
                        BlockPos targetPos = new BlockPos(current);
                        world.setBlock(targetPos, Blocks.END_STONE.defaultBlockState(), 3);
                        current = current.add(per);
                    }
                }
            }
        }
        return true;
    }
}
