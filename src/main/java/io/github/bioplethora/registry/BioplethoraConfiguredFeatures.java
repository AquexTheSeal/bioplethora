package io.github.bioplethora.registry;

import com.google.common.collect.ImmutableList;
import io.github.bioplethora.world.feature_config.PendentBlocksFeatureConfig;
import io.github.bioplethora.world.feature_generation.FleignariteRemainsClusterFeature;
import net.minecraft.block.AbstractTopPlantBlock;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.BlockWithContextConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.placement.CaveEdgeConfig;
import net.minecraft.world.gen.placement.Placement;

public class BioplethoraConfiguredFeatures {
    /*
    public static final ConfiguredFeature<?, ?> FLEIGNARITE_REMAINS_CONFIG = BioplethoraFeatures.CAVE_PATCH.get().configured((
                    new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(BioplethoraBlocks.FLEIGNARITE_REMAINS.get().defaultBlockState()),
                            SimpleBlockPlacer.INSTANCE)).tries(45).build())
            .decorated(Features.Placements.HEIGHTMAP_DOUBLE).count(12);
     */

    public static final ConfiguredFeature<?, ?> FLEIGNARITE_REMAINS_CONFIG = Feature.SIMPLE_BLOCK
            .configured(new BlockWithContextConfig(BioplethoraBlocks.FLEIGNARITE_REMAINS.get().defaultBlockState(),
                    FleignariteRemainsClusterFeature.validPlacements(),
                    ImmutableList.of(Blocks.CAVE_AIR.defaultBlockState(), Blocks.AIR.defaultBlockState()),
                    ImmutableList.of(Blocks.CAVE_AIR.defaultBlockState(), Blocks.AIR.defaultBlockState())))

                    .decorated(Placement.CARVING_MASK.configured(new CaveEdgeConfig(GenerationStage.Carving.AIR, 0.05F))
                            .count(FeatureSpread.of(7, 13))
                    );

    public static final ConfiguredFeature<?, ?> FLEIGNARITE_VINES_CONFIG = BioplethoraFeatures.PENDENT_BLOCKS.get()
            .configured(new PendentBlocksFeatureConfig.Builder()
                    .setTopBlock(Blocks.WEEPING_VINES_PLANT)
                    .setMiddleBlock(Blocks.WEEPING_VINES_PLANT)
                    .setEndBlock(Blocks.WEEPING_VINES.defaultBlockState().setValue(AbstractTopPlantBlock.AGE, 23))
                    .setWhitelist(ImmutableList.of(Blocks.STONE, Blocks.DIORITE, Blocks.ANDESITE, Blocks.GRANITE))
                    .setMaxLength(3)
                    .build())
            .range(128).squared().count(80);

    public static final ConfiguredFeature<?, ?> PENDENT_VINES_CONFIG = BioplethoraFeatures.PENDENT_BLOCKS.get()
            .configured(new PendentBlocksFeatureConfig.Builder()
                    .setTopBlock(Blocks.WEEPING_VINES_PLANT)
                    .setMiddleBlock(Blocks.WEEPING_VINES_PLANT)
                    .setEndBlock(Blocks.WEEPING_VINES.defaultBlockState().setValue(AbstractTopPlantBlock.AGE, 23))
                    .setWhitelist(ImmutableList.of(Blocks.END_STONE))
                    .setMinLength(8)
                    .setMaxLength(13)
                    .build())
            .range(128).squared().count(80);
}
