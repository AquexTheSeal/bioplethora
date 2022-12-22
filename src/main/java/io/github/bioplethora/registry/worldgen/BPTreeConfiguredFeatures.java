package io.github.bioplethora.registry.worldgen;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;

public class BPTreeConfiguredFeatures {

    public static final ConfiguredFeature<?, ?> ENIVILE_TREE_CONFIG = BPFeatures.ENIVILE_TREE.get()
            .configured(new NoFeatureConfig())
            .decorated(Placement.NOPE.configured(IPlacementConfig.NONE)
            );
    public static final ConfiguredFeature<?, ?> CRYEANUM_FOREST_TREES = Feature.RANDOM_SELECTOR
            .configured(new MultipleRandomFeatureConfig(ImmutableList.of(ENIVILE_TREE_CONFIG.weighted(0.31333334F)), ENIVILE_TREE_CONFIG))
            .decorated(Placement.COUNT_MULTILAYER.configured(new FeatureSpreadConfig(2))
            );

    public static final ConfiguredFeature<?, ?> CAERULWOOD_TREE_CONFIG = BPFeatures.CAERULWOOD_TREE.get()
            .configured(new NoFeatureConfig())
            .decorated(Placement.NOPE.configured(IPlacementConfig.NONE)
            );
    public static final ConfiguredFeature<?, ?> CAERI_FOREST_TREES = Feature.RANDOM_SELECTOR
            .configured(new MultipleRandomFeatureConfig(ImmutableList.of(CAERULWOOD_TREE_CONFIG.weighted(0.6666667F)), CAERULWOOD_TREE_CONFIG))
            .decorated(Features.Placements.HEIGHTMAP_SQUARE)
            .decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(6, 0.15F, 1))
            );
}
