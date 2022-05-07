package io.github.bioplethora.registry.worldgen;

import com.google.common.collect.ImmutableList;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;

public class BPTreeConfiguredFeatures {

    public static final ConfiguredFeature<?, ?> CAERULWOOD_TREE_CONFIG = BPFeatures.CAERULWOOD_TREE.get()
            .configured(new NoFeatureConfig())
            .decorated(Placement.NOPE.configured(IPlacementConfig.NONE)
            );
    public static final ConfiguredFeature<?, ?> CAERI_PLAINS_TREES = Feature.RANDOM_SELECTOR
            .configured(new MultipleRandomFeatureConfig(ImmutableList.of(CAERULWOOD_TREE_CONFIG.weighted(0.31333334F)), CAERULWOOD_TREE_CONFIG))
            .decorated(Features.Placements.HEIGHTMAP_SQUARE)
            .decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(1, 0.035F, 1))
            );
    public static final ConfiguredFeature<?, ?> CAERI_FOREST_TREES = Feature.RANDOM_SELECTOR
            .configured(new MultipleRandomFeatureConfig(ImmutableList.of(CAERULWOOD_TREE_CONFIG.weighted(0.6666667F)), CAERULWOOD_TREE_CONFIG))
            .decorated(Features.Placements.HEIGHTMAP_SQUARE)
            .decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(10, 0.1F, 1))
            );
}
