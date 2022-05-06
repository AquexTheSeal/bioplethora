package io.github.bioplethora.world.features;

import com.mojang.serialization.Codec;
import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.world.featureconfigs.NBTFeatureConfig;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.Random;

public class NBTFeature extends Feature<NBTFeatureConfig> {

    public NBTFeature(Codec<NBTFeatureConfig> config) {
        super(config);
    }

    public boolean place(ISeedReader world, ChunkGenerator generator, Random random, BlockPos pos, NBTFeatureConfig config) {
        BlockPos.Mutable mutablePos = new BlockPos.Mutable().set(pos);

        int rad = 2;
        for (int x = -rad; x <= rad; x++) {
            for (int z = -rad; z <= rad; z++) {
                if (Math.abs(x * z) > rad && Math.abs(x * z) < rad * 2) {
                    mutablePos.set(pos).move(-x, -1, -z);
                    if (!world.getBlockState(mutablePos).canOcclude()) {
                        return false;
                    }
                }
            }
        }

        TemplateManager tManager = world.getLevel().getStructureManager();
        Template template = tManager.get(new ResourceLocation(Bioplethora.MOD_ID, "features/" + config.getFeature()));

        if (template == null) {
            Bioplethora.LOGGER.warn("NBT does not exist!: " + new ResourceLocation(Bioplethora.MOD_ID, "features/" + config.getFeature()));
            return false;
        }

        BlockPos halfOfNBT = new BlockPos(template.getSize().getX() / 2, 0, template.getSize().getZ() / 2);
        BlockPos.Mutable placementLocation = mutablePos.set(pos).move(-halfOfNBT.getX(), config.getYOffset(), -halfOfNBT.getZ());

        Rotation rotation = Rotation.getRandom(random);
        PlacementSettings placementsettings = new PlacementSettings().setRotation(rotation).setRotationPivot(halfOfNBT).addProcessor(BlockIgnoreStructureProcessor.STRUCTURE_AND_AIR).setIgnoreEntities(true);
        template.placeInWorldChunk(world, placementLocation, placementsettings, random);

        return true;
    }
}
