package io.github.bioplethora.registry.worldgen;

import com.google.common.collect.ImmutableList;
import io.github.bioplethora.config.BPConfig;
import io.github.bioplethora.registry.BPBlocks;
import io.github.bioplethora.world.BPVanillaBiomeFeatureGeneration;
import io.github.bioplethora.world.blockplacers.LavaEdgeBlockPlacer;
import io.github.bioplethora.world.blockplacers.MinishroomBlockPlacer;
import io.github.bioplethora.world.featureconfigs.ExpandedLakeFeatureConfig;
import io.github.bioplethora.world.featureconfigs.FleignariteSplotchConfig;
import io.github.bioplethora.world.featureconfigs.PendentBlocksFeatureConfig;
import net.minecraft.block.AbstractTopPlantBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockplacer.BlockPlacer;
import net.minecraft.world.gen.blockplacer.DoublePlantBlockPlacer;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.*;

public class BPConfiguredFeatures {

    //--------------------------------
    //     TEMPLATED FEATURES
    //--------------------------------

    // Nether Mushrooms
    public static final ConfiguredFeature<?, ?> SOUL_MINISHROOM = register("soul_minishroom", makeDecoratedClusterPlants(
            new DefaultFlowersFeature(BlockClusterFeatureConfig.CODEC), BPBlocks.SOUL_MINISHROOM.get(), new MinishroomBlockPlacer(), 15
    ));
    
    public static final ConfiguredFeature<?, ?> SOUL_BIGSHROOM = register("soul_bigshroom", makeDecoratedClusterPlants(
            new DefaultFlowersFeature(BlockClusterFeatureConfig.CODEC), BPBlocks.SOUL_BIGSHROOM.get(), new SimpleBlockPlacer(), 12
    ));

    // Nether Grasses
    public static final ConfiguredFeature<?, ?> SOUL_SPROUTS = register("soul_sprouts", makeDecoratedClusterPlants(
            new DefaultFlowersFeature(BlockClusterFeatureConfig.CODEC), BPBlocks.SOUL_SPROUTS.get(), new SimpleBlockPlacer(), 26
    ));

    public static final ConfiguredFeature<?, ?> SOUL_TALL_GRASS = register("soul_tall_grass", makeNoProjectionClusterPlants(
            Feature.RANDOM_PATCH, BPBlocks.SOUL_TALL_GRASS.get(), new DoublePlantBlockPlacer()
    ).count(22).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE).decorated(Placement.COUNT_NOISE.configured(new NoiseDependant(-0.8D, 5, 10))
    ));

    // Other Nether Plants
    public static final ConfiguredFeature<?, ?> LAVA_SPIRE = register("lava_spire", makeDecoratedClusterPlants(
            BPFeatures.LAVA_EDGE_CLUSTER.get(), BPBlocks.LAVA_SPIRE.get(), new LavaEdgeBlockPlacer(), 19
    ));

    public static final ConfiguredFeature<?, ?> WARPED_DANCER = register("warped_dancer", makeDecoratedClusterPlants(
            new DefaultFlowersFeature(BlockClusterFeatureConfig.CODEC), BPBlocks.SOUL_SPROUTS.get(), new SimpleBlockPlacer(), 15
    ));

    // Nether Vines
    public static final ConfiguredFeature<?, ?> SPIRIT_DANGLER = register("spirit_dangler", makePendentConfig(
            Blocks.SOUL_SOIL, BPBlocks.SPIRIT_DANGLER_PLANT.get(), BPBlocks.SPIRIT_DANGLER.get(),
            ImmutableList.of(Blocks.SOUL_SOIL, Blocks.SOUL_SAND, Blocks.NETHERRACK, Blocks.BLACKSTONE),
            1, 8, 132, 115
    ));
    
    // Nether Fruitable Vines
    public static final ConfiguredFeature<?, ?> BASALT_SPELEOTHERM = register("basalt_speleotherm", makeFruitablePendentConfig(
            Blocks.BASALT, BPBlocks.BASALT_SPELEOTHERM_PLANT.get(), BPBlocks.FIERY_BASALT_SPELEOTHERM.get(), BPBlocks.BASALT_SPELEOTHERM.get(),
            ImmutableList.of(Blocks.BASALT, Blocks.NETHERRACK, Blocks.BLACKSTONE),
            1, 8, 132, 115
    ));

    public static final ConfiguredFeature<?, ?> THONTUS_THISTLE = register("thontus_thistle", makeFruitablePendentConfig(
            Blocks.NETHERRACK, BPBlocks.THONTUS_THISTLE_PLANT.get(), BPBlocks.BERRIED_THONTUS_THISTLE.get(), BPBlocks.THONTUS_THISTLE.get(),
            ImmutableList.of(Blocks.NETHERRACK, Blocks.BLACKSTONE),
            2, 10, 132, 125
    ));

    public static final ConfiguredFeature<?, ?> TURQUOISE_PENDENT = register("turquoise_pendent", makeFruitablePendentConfig(
            Blocks.WARPED_WART_BLOCK, BPBlocks.TURQUOISE_PENDENT_PLANT.get(), BPBlocks.BLOSSOMING_TURQUOISE_PENDENT.get(), BPBlocks.TURQUOISE_PENDENT.get(),
            ImmutableList.of(Blocks.WARPED_WART_BLOCK, Blocks.NETHERRACK, Blocks.BLACKSTONE),
            2, 8, 132, 122
    ));

    public static final ConfiguredFeature<?, ?> CERISE_IVY = register("cerise_ivy", makeFruitablePendentConfig(
            Blocks.NETHER_WART_BLOCK, BPBlocks.CERISE_IVY_PLANT.get(), BPBlocks.SEEDED_CERISE_IVY.get(), BPBlocks.CERISE_IVY.get(),
            ImmutableList.of(Blocks.NETHER_WART_BLOCK, Blocks.NETHERRACK, Blocks.BLACKSTONE),
            1, 8, 132, 122
    ));

    public static final ConfiguredFeature<?, ?> SOUL_ETERN = register("soul_etern", makeFruitablePendentConfig(
            Blocks.SOUL_SOIL, BPBlocks.SOUL_ETERN_PLANT.get(), BPBlocks.FLOURISHED_SOUL_ETERN.get(), BPBlocks.SOUL_ETERN.get(),
            ImmutableList.of(Blocks.SOUL_SOIL, Blocks.SOUL_SAND, Blocks.NETHERRACK, Blocks.BLACKSTONE),
            2, 10, 132, 125
    ));

    // End Plants
    public static final ConfiguredFeature<?, ?> IRION_GRASS = register("irion_grass", makeDecoratedClusterPlants(
            new DefaultFlowersFeature(BlockClusterFeatureConfig.CODEC), BPBlocks.IRION_GRASS.get(), new SimpleBlockPlacer(), 32
    ));

    public static final ConfiguredFeature<?, ?> IRION_TALL_GRASS = register("irion_tall_grass", makeNoProjectionClusterPlants(
            Feature.RANDOM_PATCH, BPBlocks.IRION_TALL_GRASS.get(), new DoublePlantBlockPlacer()
    ).count(13).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE).decorated(Placement.COUNT_NOISE.configured(new NoiseDependant(-0.8D, 5, 10))
    ));

    public static final ConfiguredFeature<?, ?> ARTAIRIUS = register("artairius", makeNoProjectionClusterPlants(
            Feature.RANDOM_PATCH, BPBlocks.ARTAIRIUS.get(), new DoublePlantBlockPlacer()
    ).count(8).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE).decorated(Placement.COUNT_NOISE.configured(new NoiseDependant(-0.8D, 5, 10))
    ));

    public static final ConfiguredFeature<?, ?> FROSTEM = register("frostem", makeDecoratedClusterPlants(
            new DefaultFlowersFeature(BlockClusterFeatureConfig.CODEC), BPBlocks.FROSTEM.get(), new SimpleBlockPlacer(), 48
    ));

    public static final ConfiguredFeature<?, ?> SPINXELTHORN = register("spinxelthorn", makePendentConfig(
            Blocks.END_STONE, BPBlocks.SPINXELTHORN_PLANT.get(), BPBlocks.SPINXELTHORN.get(),
            ImmutableList.of(Blocks.END_STONE),
            1, 8, 156, 132
    ));

    public static final ConfiguredFeature<?, ?> GLACYNTH = register("glacynth", makePendentConfig(
            Blocks.ICE, BPBlocks.GLACYNTH_PLANT.get(), BPBlocks.GLACYNTH.get(),
            ImmutableList.of(Blocks.ICE, Blocks.BLUE_ICE, Blocks.PACKED_ICE, Blocks.FROSTED_ICE),
            3, 9, 132, 115
    ));

    //--------------------------------------
    //    CUSTOMIZED FEATURES
    //--------------------------------------

    public static final ConfiguredFeature<?, ?> FLEIGNARITE_REMAINS = register("fleignarite_remains", BPFeatures.FLEIGNARITE_PATCH.get()
            .configured(new FleignariteSplotchConfig(BPBlocks.FLEIGNARITE_REMAINS.get().defaultBlockState(),
                    BPBlocks.FLEIGNARITE_SPLOTCH.get().defaultBlockState(),
                    BPVanillaBiomeFeatureGeneration.stoneBlockstates(),
                    ImmutableList.of(Blocks.CAVE_AIR.defaultBlockState(), Blocks.AIR.defaultBlockState()),
                    ImmutableList.of(Blocks.CAVE_AIR.defaultBlockState(), Blocks.AIR.defaultBlockState())))

            .decorated(Placement.CARVING_MASK.configured(new CaveEdgeConfig(GenerationStage.Carving.AIR, 0.1F))
                    .count(FeatureSpread.of(14, 27))
            ));

    public static final ConfiguredFeature<?, ?> FLEIGNARITE_VINES = register("fleignarite_vines", BPFeatures.PENDENT_FLEIGNARITE.get()
            .configured(new PendentBlocksFeatureConfig.Builder()
                    .setTopBlock(Blocks.STONE).setMiddleBlock(BPBlocks.FLEIGNARITE_VINES_PLANT.get())
                    .setFruitedBlock(BPBlocks.FLEIGNARITE_VINES_PLANT.get()).setEndBlock(BPBlocks.FLEIGNARITE_VINES.get().defaultBlockState().setValue(AbstractTopPlantBlock.AGE, 23))
                    .setWhitelist(BPVanillaBiomeFeatureGeneration.stoneBlocks())
                    .setMinLength(1)
                    .setMaxLength(2)
                    .build())
            .range(128).squared().count(512));

    // End Plants
    public static final ConfiguredFeature<?, ?> CYRA_LAKE = register("cyra_lake", BPFeatures.EXPANDED_LAKE.get()
            .configured(new ExpandedLakeFeatureConfig.Builder()
                    .setLiquid(Blocks.WATER.defaultBlockState())
                    .setSides(BPBlocks.CYRA.get().defaultBlockState())
                    .setBase(Blocks.OBSIDIAN.defaultBlockState())
                    .build())
            .range(BPConfig.WORLDGEN.cyraLakesEndAmount.get()).squared().count(1));

    public static final ConfiguredFeature<?, ?> BYRSS_LANTERN_PLANT = register("byrss_lantern_plant", BPFeatures.SINGULAR_BLOCK.get()
            .configured(new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(BPBlocks.BYRSS_LANTERN_PLANT.get().defaultBlockState()), new DoublePlantBlockPlacer()).build())
            .decorated(Placement.NOPE.configured(IPlacementConfig.NONE)
            ));
    public static final ConfiguredFeature<?, ?> BYRSS_LANTERN_PLANT_PATCH = register("byrss_lantern_plant_patch", Feature.RANDOM_SELECTOR
            .configured(new MultipleRandomFeatureConfig(ImmutableList.of(BYRSS_LANTERN_PLANT.weighted(0.4222667F)), BYRSS_LANTERN_PLANT))
            .range(85).squared().count(2)
            .decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(2, 0.025F, 1))
            ));
    public static final ConfiguredFeature<?, ?> BYRSS_LANTERN_FOREST_PATCH = register("byrss_lantern_forest_patch", Feature.RANDOM_SELECTOR
            .configured(new MultipleRandomFeatureConfig(ImmutableList.of(BYRSS_LANTERN_PLANT.weighted(0.4446667F)), BYRSS_LANTERN_PLANT))
            .range(110).squared().count(5)
            .decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(2, 0.025F, 1))
            ));

    public static final ConfiguredFeature<?, ?> END_LAND_SPIKE = register("end_land_spike", BPFeatures.END_LAND_SPIKE.get()
            .configured(new NoFeatureConfig()).decorated(Placement.NOPE.configured(IPlacementConfig.NONE)
            ));
    public static final ConfiguredFeature<?, ?> END_LAND_SPIKE_PATCH = register("end_land_spike_patch", Feature.RANDOM_SELECTOR
            .configured(new MultipleRandomFeatureConfig(ImmutableList.of(END_LAND_SPIKE.weighted(0.4446667F)), END_LAND_SPIKE))
            .decorated(Features.Placements.HEIGHTMAP_SQUARE).range(BPConfig.WORLDGEN.endSpikeHighlandsAmount.get()).squared().count(2)
            .decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(4, 0.075F, 1))
            ));

    public static final ConfiguredFeature<?, ?> CHORUS_LANTERN_PLANT = register("chorus_lantern_plant", BPFeatures.SINGULAR_BLOCK.get()
            .configured(new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(BPBlocks.CHORUS_LANTERN_PLANT.get().defaultBlockState()), new DoublePlantBlockPlacer()).build())
            .decorated(Placement.NOPE.configured(IPlacementConfig.NONE)
            ));
    public static final ConfiguredFeature<?, ?> BYRSS_LANTERN_HIGHLANDS_PATCH = register("byrss_lantern_highlands_patch", Feature.RANDOM_SELECTOR
            .configured(new MultipleRandomFeatureConfig(ImmutableList.of(CHORUS_LANTERN_PLANT.weighted(0.4022667F)), CHORUS_LANTERN_PLANT))
            .range(BPConfig.WORLDGEN.chorusLanternHighlandsAmount.get()).squared().count(4)
            .decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(2, 0.035F, 1))
            ));
    public static final ConfiguredFeature<?, ?> BYRSS_LANTERN_MIDLANDS_PATCH = register("byrss_lantern_midlands_patch", Feature.RANDOM_SELECTOR
            .configured(new MultipleRandomFeatureConfig(ImmutableList.of(CHORUS_LANTERN_PLANT.weighted(0.4224447F)), CHORUS_LANTERN_PLANT))
            .range(BPConfig.WORLDGEN.chorusLanternMidlandsAmount.get()).squared().count(3)
            .decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(1, 0.035F, 1))
            ));

    public static final ConfiguredFeature<?, ?> END_ISLANDS_ICICLE = register("end_islands_icicle", BPFeatures.END_ICICLE.get()
            .configured(new NoFeatureConfig()).decorated(Placement.NOPE.configured(IPlacementConfig.NONE)
            ));
    public static final ConfiguredFeature<?, ?> END_ISLANDS_ICICLE_PATCH = register("end_islands_icicle_patch", Feature.RANDOM_SELECTOR
            .configured(new MultipleRandomFeatureConfig(ImmutableList.of(END_ISLANDS_ICICLE.weighted(0.2224447F)), END_ISLANDS_ICICLE))
            .range(BPConfig.WORLDGEN.endIcicleIslandsAmount.get()).squared().count(1)
            .decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(1, 0.035F, 1))
            ));

    public static final ConfiguredFeature<?, ?> END_FROZEN_ISLAND = register("end_frozen_island", BPFeatures.END_FROZEN_ISLAND.get()
            .configured(new NoFeatureConfig()).decorated(Placement.NOPE.configured(IPlacementConfig.NONE)
            ));
    public static final ConfiguredFeature<?, ?> END_FROZEN_ISLAND_DECORATED = register("end_frozen_island_decorated",
            END_FROZEN_ISLAND.range(BPConfig.WORLDGEN.endFrozenIslandsAmount.get()).squared().count(1)
                    .decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(1, 0.035F, 1))
                    ));

    //---------------------------
    //   FEATURE HELPERS
    //---------------------------

    private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String pId, ConfiguredFeature<FC, ?> pConfiguredFeature) {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, pId, pConfiguredFeature);
    }

    public static ConfiguredFeature<?, ?> makePendentConfig(Block top, Block middle, Block end, ImmutableList<Block> whitelist, int minLength, int maxLength, int range, int count) {
        return BPFeatures.PENDENT_BLOCKS.get()
                .configured(new PendentBlocksFeatureConfig.Builder()
                        .setTopBlock(top).setMiddleBlock(middle)
                        .setFruitedBlock(middle).setEndBlock(end.defaultBlockState().setValue(AbstractTopPlantBlock.AGE, 23))
                        .setWhitelist(whitelist)
                        .setMinLength(minLength)
                        .setMaxLength(maxLength)
                        .build())
                .range(range).squared().count(count);
    }

    public static ConfiguredFeature<?, ?> makeFruitablePendentConfig(Block top, Block middle, Block fruited, Block end, ImmutableList<Block> whitelist, int minLength, int maxLength, int range, int count) {
        return BPFeatures.PENDENT_BLOCKS.get()
                .configured(new PendentBlocksFeatureConfig.Builder()
                        .setTopBlock(top).setMiddleBlock(middle)
                        .setFruitedBlock(fruited).setEndBlock(end.defaultBlockState().setValue(AbstractTopPlantBlock.AGE, 23))
                        .setWhitelist(whitelist)
                        .setMinLength(minLength)
                        .setMaxLength(maxLength)
                        .build())
                .range(range).squared().count(count);
    }

    public static ConfiguredFeature<?, ?> makeClusterPlants(Feature<BlockClusterFeatureConfig> feature, Block block, BlockPlacer placer) {
        return feature.configured(new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(block.defaultBlockState()), placer).tries(64).build());
    }

    public static ConfiguredFeature<?, ?> makeNoProjectionClusterPlants(Feature<BlockClusterFeatureConfig> feature, Block block, BlockPlacer placer) {
        return feature.configured(new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(block.defaultBlockState()), placer).noProjection().tries(64).build());
    }

    public static ConfiguredFeature<?, ?> makeDecoratedClusterPlants(Feature<BlockClusterFeatureConfig> feature, Block block, BlockPlacer placer, int count) {
        return makeClusterPlants(feature, block, placer).decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(count);
    }
}
