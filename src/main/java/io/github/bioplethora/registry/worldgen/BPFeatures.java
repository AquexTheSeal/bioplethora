package io.github.bioplethora.registry.worldgen;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.world.featureconfigs.ExpandedLakeFeatureConfig;
import io.github.bioplethora.world.featureconfigs.FleignariteSplotchConfig;
import io.github.bioplethora.world.featureconfigs.NBTFeatureConfig;
import io.github.bioplethora.world.featureconfigs.PendentBlocksFeatureConfig;
import io.github.bioplethora.world.features.*;
import io.github.bioplethora.world.features.treefeatures.CaerulwoodTreeFeature;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BPFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Bioplethora.MOD_ID);

    public static final RegistryObject<Feature<BlockClusterFeatureConfig>> LAVA_EDGE_CLUSTER = FEATURES.register("lava_edge_cluster", () -> new LavaEdgeClusterFeature(BlockClusterFeatureConfig.CODEC.stable()));
    public static final RegistryObject<Feature<PendentBlocksFeatureConfig>> PENDENT_BLOCKS = FEATURES.register("pendent_blocks", () -> new PendentBlocksFeature(PendentBlocksFeatureConfig.CODEC.stable()));
    public static final RegistryObject<Feature<FleignariteSplotchConfig>> FLEIGNARITE_PATCH = FEATURES.register("fleignarite_patch", () -> new FleignaritePatchFeature(FleignariteSplotchConfig.CODEC.stable()));
    public static final RegistryObject<Feature<PendentBlocksFeatureConfig>> PENDENT_FLEIGNARITE = FEATURES.register("pendent_fleignarite", () -> new PendentFleignariteFeature(PendentBlocksFeatureConfig.CODEC.stable()));
    public static final RegistryObject<Feature<ExpandedLakeFeatureConfig>> EXPANDED_LAKE = FEATURES.register("expanded_lake", () -> new ExpandedLakeFeature(ExpandedLakeFeatureConfig.CODEC));
    public static final RegistryObject<Feature<BlockClusterFeatureConfig>> SINGULAR_BLOCK = FEATURES.register("singular_block", () -> new SingularBlockFeature(BlockClusterFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NBTFeatureConfig>> NBT_DRIVEN = FEATURES.register("nbt_driven", () -> new NBTFeature(NBTFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NoFeatureConfig>> END_LAND_SPIKE = FEATURES.register("end_land_spike", () -> new EndLandsSpikeFeature(NoFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NoFeatureConfig>> END_ICICLE = FEATURES.register("end_icicle", () -> new EndIcicleFeature(NoFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NoFeatureConfig>> END_FROZEN_ISLAND = FEATURES.register("end_frozen_island", () -> new EndFrozenIslandFeature(NoFeatureConfig.CODEC));

    // Trees
    public static final RegistryObject<Feature<NoFeatureConfig>> CAERULWOOD_TREE = FEATURES.register("caerulwood_tree", () -> new CaerulwoodTreeFeature(NoFeatureConfig.CODEC));

}
