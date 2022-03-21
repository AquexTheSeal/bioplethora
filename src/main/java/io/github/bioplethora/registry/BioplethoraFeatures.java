package io.github.bioplethora.registry;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.world.feature_config.PendentBlocksFeatureConfig;
import io.github.bioplethora.world.features.FleignaritePatchFeature;
import io.github.bioplethora.world.features.PendentBlocksFeature;
import io.github.bioplethora.world.features.PendentFleignariteFeature;
import net.minecraft.world.gen.feature.BlockWithContextConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BioplethoraFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Bioplethora.MOD_ID);

    public static final RegistryObject<Feature<PendentBlocksFeatureConfig>> PENDENT_BLOCKS = FEATURES.register("pendent_blocks", () -> new PendentBlocksFeature(PendentBlocksFeatureConfig.CODEC.stable()));

    public static final RegistryObject<Feature<BlockWithContextConfig>> FLEIGNARITE_PATCH = FEATURES.register("fleignarite_patch", () -> new FleignaritePatchFeature(BlockWithContextConfig.CODEC.stable()));
    public static final RegistryObject<Feature<PendentBlocksFeatureConfig>> PENDENT_FLEIGNARITE = FEATURES.register("pendent_fleignarite", () -> new PendentFleignariteFeature(PendentBlocksFeatureConfig.CODEC.stable()));
}
