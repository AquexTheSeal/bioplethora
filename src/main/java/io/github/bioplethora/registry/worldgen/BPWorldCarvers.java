package io.github.bioplethora.registry.worldgen;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.world.featureconfigs.ExpandedLakeFeatureConfig;
import io.github.bioplethora.world.featureconfigs.FleignariteSplotchConfig;
import io.github.bioplethora.world.featureconfigs.NBTFeatureConfig;
import io.github.bioplethora.world.featureconfigs.PendentBlocksFeatureConfig;
import io.github.bioplethora.world.features.*;
import io.github.bioplethora.world.features.treefeatures.CaerulwoodTreeFeature;
import io.github.bioplethora.world.features.treefeatures.EnivileTreeFeature;
import io.github.bioplethora.world.worldcarvers.EndSpringWorldCarver;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BPWorldCarvers {
    public static final DeferredRegister<WorldCarver<?>> WORLD_CARVERS = DeferredRegister.create(ForgeRegistries.WORLD_CARVERS, Bioplethora.MOD_ID);

    public static final RegistryObject<WorldCarver<ProbabilityConfig>> END_SPRINGS = WORLD_CARVERS.register("end_springs", () -> new EndSpringWorldCarver(ProbabilityConfig.CODEC));
}
