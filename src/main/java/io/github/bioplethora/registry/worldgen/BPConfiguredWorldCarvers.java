package io.github.bioplethora.registry.worldgen;

import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.carver.ICarverConfig;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.ProbabilityConfig;

public class BPConfiguredWorldCarvers {

    public static final ConfiguredCarver<?> END_SPRINGS_CARVER = register("end_springs_carver", BPWorldCarvers.END_SPRINGS.get().configured(new ProbabilityConfig(0.2F)));
    public static final ConfiguredCarver<?> CAERI_FORMERS = register("caeri_formers", BPWorldCarvers.CAERI_FORMERS.get().configured(new ProbabilityConfig(0.3F)));

    private static <CC extends ICarverConfig> ConfiguredCarver<CC> register(String pId, ConfiguredCarver<CC> pConfiguredCarver) {
        return Registry.register(WorldGenRegistries.CONFIGURED_CARVER, pId, pConfiguredCarver);
    }
}
