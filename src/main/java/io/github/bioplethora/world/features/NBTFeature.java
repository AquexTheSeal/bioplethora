package io.github.bioplethora.world.features;

import com.mojang.serialization.Codec;
import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.world.featureconfigs.NBTFeatureConfig;
import net.minecraft.block.BlockState;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;

import java.util.Random;

public class NBTFeature extends Feature<NBTFeatureConfig> {

    public NBTFeature(Codec<NBTFeatureConfig> config) {
        super(config);
    }

    public boolean place(ISeedReader world, ChunkGenerator generator, Random random, BlockPos pos, NBTFeatureConfig config) {
        int ci = (pos.getX() >> 4) << 4;
        int ck = (pos.getZ() >> 4) << 4;
        int count = random.nextInt(1) + 1;
        for (int a = 0; a < count; a++) {
            int i = ci + random.nextInt(16);
            int k = ck + random.nextInt(16);
            int j = world.getHeight(Heightmap.Type.WORLD_SURFACE_WG, i, k);
            j = Math.abs(random.nextInt(Math.max(1, j)) - 24);
            BlockState blockAt = world.getBlockState(new BlockPos(i, j, k));

            if (config.getWhitelist().contains(blockAt.getBlock()))
                continue;

            Rotation rotation = Rotation.values()[random.nextInt(3)];
            Mirror mirror = Mirror.values()[random.nextInt(2)];
            BlockPos spawnTo = new BlockPos(i, j + config.getYOffset(), k);

            Template template = world.getLevel().getStructureManager().getOrCreate(new ResourceLocation(Bioplethora.MOD_ID, "features/" + config.getFeature()));
            if (template == null) return false;
            template.placeInWorldChunk(world, spawnTo, new PlacementSettings().setRotation(rotation).setRandom(random).setMirror(mirror)
                    .addProcessor(BlockIgnoreStructureProcessor.STRUCTURE_AND_AIR).setChunkPos(null).setIgnoreEntities(true), random);
        }
        return true;
    }
}
