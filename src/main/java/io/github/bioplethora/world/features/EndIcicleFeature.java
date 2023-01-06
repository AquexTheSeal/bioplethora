package io.github.bioplethora.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
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
        pos = new BlockPos(pos.getX(), 35 + rand.nextInt(35), pos.getZ());
        createSpike(world, pos);
        return true;
    }

    public static void createSpike(ISeedReader world, BlockPos pos) {
        boolean large = world.getRandom().nextInt(4) == 0;
        int tipMin = large ? 25 : 10;
        int tipRand = large ? 35 : 20;
        int radiusMin = large ? 6 : 4;
        int radiusRand = large ? 6 : 4;

        int tip = tipMin + world.getRandom().nextInt(tipRand);
        int topX = world.getRandom().nextInt(tip) - tip / 2;
        int topZ = world.getRandom().nextInt(tip) - tip / 2;

        int radius = radiusMin + world.getRandom().nextInt(radiusRand);

        Vector3d to = new Vector3d(pos.getX() + topX, pos.getY() + tip, pos.getZ() + topZ);
        Vector3d opto = new Vector3d(pos.getX() - topX, pos.getY() - tip, pos.getZ() - topZ);

        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                double fromCenter = Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2));
                if (fromCenter <= radius) {
                    Vector3d from = new Vector3d(pos.getX() + x, pos.getY(), pos.getZ() + z);

                    if (world.getBlockState(new BlockPos(from).above()).isAir()) {
                        continue;
                    }

                    Vector3d per = to.subtract(from).normalize();
                    Vector3d current = from.add(0, 0, 0);
                    double distance = from.distanceTo(to);

                    for (double i = 0; i < distance; i++) {
                        BlockPos targetPos = new BlockPos(current);
                        world.setBlock(targetPos, Blocks.ICE.defaultBlockState(), 3);
                        current = current.add(per);
                    }

                    Vector3d opper = opto.subtract(from).normalize();
                    Vector3d opcurrent = from.add(0, 0, 0);
                    double opdistance = from.distanceTo(opto);

                    for (double i = 0; i < opdistance; i++) {
                        BlockPos targetPos = new BlockPos(opcurrent);
                        world.setBlock(targetPos, Blocks.ICE.defaultBlockState(), 3);
                        opcurrent = opcurrent.add(opper);
                    }
                }
            }
        }
    }
}
