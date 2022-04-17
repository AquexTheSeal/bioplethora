package io.github.bioplethora.registry.worldgen;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.world.feature_config.FleignariteSplotchConfig;
import io.github.bioplethora.world.feature_config.PendentBlocksFeatureConfig;
import io.github.bioplethora.world.features.FleignaritePatchFeature;
import io.github.bioplethora.world.features.LavaEdgeClusterFeature;
import io.github.bioplethora.world.features.PendentBlocksFeature;
import io.github.bioplethora.world.features.PendentFleignariteFeature;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BPFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Bioplethora.MOD_ID);

    public static final RegistryObject<Feature<BlockClusterFeatureConfig>> LAVA_EDGE_CLUSTER = FEATURES.register("lava_edge_cluster", () -> new LavaEdgeClusterFeature(BlockClusterFeatureConfig.CODEC.stable()));

    public static final RegistryObject<Feature<PendentBlocksFeatureConfig>> PENDENT_BLOCKS = FEATURES.register("pendent_blocks", () -> new PendentBlocksFeature(PendentBlocksFeatureConfig.CODEC.stable()));

    public static final RegistryObject<Feature<FleignariteSplotchConfig>> FLEIGNARITE_PATCH = FEATURES.register("fleignarite_patch", () -> new FleignaritePatchFeature(FleignariteSplotchConfig.CODEC.stable()));
    public static final RegistryObject<Feature<PendentBlocksFeatureConfig>> PENDENT_FLEIGNARITE = FEATURES.register("pendent_fleignarite", () -> new PendentFleignariteFeature(PendentBlocksFeatureConfig.CODEC.stable()));
}
