package io.github.bioplethora.registry;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.world.feature_config.PendentBlocksFeatureConfig;
import io.github.bioplethora.world.features.CavePatchFeature;
import io.github.bioplethora.world.features.PendentBlocksFeature;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BioplethoraFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Bioplethora.MOD_ID);

    public static final RegistryObject<Feature<BlockClusterFeatureConfig>> CAVE_PATCH = FEATURES.register("cave_patch", () -> new CavePatchFeature(BlockClusterFeatureConfig.CODEC));
    public static final RegistryObject<Feature<PendentBlocksFeatureConfig>> PENDENT_BLOCKS = FEATURES.register("pendent_blocks", () -> new PendentBlocksFeature(PendentBlocksFeatureConfig.CODEC.stable()));
}
