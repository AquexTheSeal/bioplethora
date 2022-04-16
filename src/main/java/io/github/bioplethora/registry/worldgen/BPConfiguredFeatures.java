package io.github.bioplethora.registry.worldgen;

import com.google.common.collect.ImmutableList;
import io.github.bioplethora.registry.BPBlocks;
import io.github.bioplethora.world.BPFeatureGeneration;
import io.github.bioplethora.world.feature_config.FleignariteSplotchConfig;
import io.github.bioplethora.world.feature_config.PendentBlocksFeatureConfig;
import net.minecraft.block.AbstractTopPlantBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockplacer.BlockPlacer;
import net.minecraft.world.gen.blockplacer.DoublePlantBlockPlacer;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.CaveEdgeConfig;
import net.minecraft.world.gen.placement.NoiseDependant;
import net.minecraft.world.gen.placement.Placement;

public class BPConfiguredFeatures {

    //--------------------------------
    //     TEMPLATED FEATURES
    //--------------------------------

    // Mushrooms
    public static final ConfiguredFeature<?, ?> SOUL_BIGSHROOM_CONFIG = makeDecoratedClusterPlants(
            new DefaultFlowersFeature(BlockClusterFeatureConfig.CODEC), BPBlocks.SOUL_BIGSHROOM.get(), new SimpleBlockPlacer(), 15
    );

    // Grasses
    public static final ConfiguredFeature<?, ?> SOUL_TALL_GRASS_CONFIG = makeNoProjectionClusterPlants(
            Feature.RANDOM_PATCH, BPBlocks.SOUL_TALL_GRASS.get(), new DoublePlantBlockPlacer()
    ).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE).decorated(Placement.COUNT_NOISE.configured(new NoiseDependant(-0.8D, 5, 10)));

    // Vines
    public static final ConfiguredFeature<?, ?> BASALT_SPELEOTHERM_CONFIG = makePendentConfig(
            Blocks.BASALT, BPBlocks.BASALT_SPELEOTHERM_PLANT.get(), BPBlocks.FIERY_BASALT_SPELEOTHERM.get(), BPBlocks.BASALT_SPELEOTHERM.get(),
            ImmutableList.of(Blocks.BASALT, Blocks.NETHERRACK, Blocks.BLACKSTONE),
            1, 8, 132, 115
    );

    public static final ConfiguredFeature<?, ?> THONTUS_THISTLE_CONFIG = makePendentConfig(
            Blocks.NETHERRACK, BPBlocks.THONTUS_THISTLE_PLANT.get(), BPBlocks.BERRIED_THONTUS_THISTLE.get(), BPBlocks.THONTUS_THISTLE.get(),
            ImmutableList.of(Blocks.NETHERRACK, Blocks.BLACKSTONE),
            2, 10, 132, 125
    );

    public static final ConfiguredFeature<?, ?> TURQUOISE_PENDENT_CONFIG = makePendentConfig(
            Blocks.WARPED_WART_BLOCK, BPBlocks.TURQUOISE_PENDENT_PLANT.get(), BPBlocks.BLOSSOMING_TURQUOISE_PENDENT.get(), BPBlocks.TURQUOISE_PENDENT.get(),
            ImmutableList.of(Blocks.WARPED_WART_BLOCK, Blocks.NETHERRACK, Blocks.BLACKSTONE),
            2, 8, 132, 122
    );

    public static final ConfiguredFeature<?, ?> CERISE_IVY_CONFIG = makePendentConfig(
            Blocks.NETHER_WART_BLOCK, BPBlocks.CERISE_IVY_PLANT.get(), BPBlocks.SEEDED_CERISE_IVY.get(), BPBlocks.CERISE_IVY.get(),
            ImmutableList.of(Blocks.NETHER_WART_BLOCK, Blocks.NETHERRACK, Blocks.BLACKSTONE),
            1, 8, 132, 122
    );

    public static final ConfiguredFeature<?, ?> SOUL_ETERN_CONFIG = makePendentConfig(
            Blocks.SOUL_SOIL, BPBlocks.SOUL_ETERN_PLANT.get(), BPBlocks.FLOURISHED_SOUL_ETERN.get(), BPBlocks.SOUL_ETERN.get(),
            ImmutableList.of(Blocks.SOUL_SOIL, Blocks.SOUL_SAND, Blocks.NETHERRACK, Blocks.BLACKSTONE),
            2, 10, 132, 125
    );

    //--------------------------------------
    //    CUSTOMIZED FEATURES
    //--------------------------------------

    public static final ConfiguredFeature<?, ?> FLEIGNARITE_REMAINS_CONFIG = BPFeatures.FLEIGNARITE_PATCH.get()
            .configured(new FleignariteSplotchConfig(BPBlocks.FLEIGNARITE_REMAINS.get().defaultBlockState(),
                    BPBlocks.FLEIGNARITE_SPLOTCH.get().defaultBlockState(),
                    BPFeatureGeneration.stoneBlockstates(),
                    ImmutableList.of(Blocks.CAVE_AIR.defaultBlockState(), Blocks.AIR.defaultBlockState()),
                    ImmutableList.of(Blocks.CAVE_AIR.defaultBlockState(), Blocks.AIR.defaultBlockState())))

            .decorated(Placement.CARVING_MASK.configured(new CaveEdgeConfig(GenerationStage.Carving.AIR, 0.1F))
                    .count(FeatureSpread.of(14, 27))
            );

    public static final ConfiguredFeature<?, ?> FLEIGNARITE_VINES_CONFIG = BPFeatures.PENDENT_FLEIGNARITE.get()
            .configured(new PendentBlocksFeatureConfig.Builder()
                    .setTopBlock(Blocks.STONE).setMiddleBlock(BPBlocks.FLEIGNARITE_VINES_PLANT.get())
                    .setFruitedBlock(BPBlocks.FLEIGNARITE_VINES_PLANT.get()).setEndBlock(BPBlocks.FLEIGNARITE_VINES.get().defaultBlockState().setValue(AbstractTopPlantBlock.AGE, 23))
                    .setWhitelist(BPFeatureGeneration.stoneBlocks())
                    .setMinLength(1)
                    .setMaxLength(2)
                    .build())
            .range(128).squared().count(512);

    //---------------------------
    //   FEATURE HELPERS
    //---------------------------

    public static ConfiguredFeature<?, ?> makePendentConfig(Block top, Block middle, Block fruited, Block end, ImmutableList<Block> whitelist, int minLength, int maxLength, int range, int count) {
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
