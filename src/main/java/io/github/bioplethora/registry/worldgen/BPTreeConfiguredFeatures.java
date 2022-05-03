package io.github.bioplethora.registry.worldgen;

import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;

public class BPTreeConfiguredFeatures {

    public static final ConfiguredFeature<?, ?> CAERULWOOD_TREE_CONFIG = BPFeatures.CAERULWOOD_TREE.get().configured(new NoFeatureConfig()).decorated(Placement.NOPE.configured(IPlacementConfig.NONE));
}
