package io.github.bioplethora.registry.worldgen;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.world.surfacebuilderconfigs.CarpetedSurfaceBuilderConfig;
import io.github.bioplethora.world.surfacebuilderconfigs.NoisySurfaceBuilderConfig;
import io.github.bioplethora.world.surfacebuilders.CarpetedSurfaceBuilder;
import io.github.bioplethora.world.surfacebuilders.NoisySurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BPSurfaceBuilders {

    public static final DeferredRegister<SurfaceBuilder<?>> SURFACE_BUILDERS = DeferredRegister.create(ForgeRegistries.SURFACE_BUILDERS, Bioplethora.MOD_ID);

    public static final RegistryObject<SurfaceBuilder<NoisySurfaceBuilderConfig>> NOISY = SURFACE_BUILDERS.register("noisy", () -> new NoisySurfaceBuilder(NoisySurfaceBuilderConfig.CODEC));
    public static final RegistryObject<SurfaceBuilder<CarpetedSurfaceBuilderConfig>> CARPETED = SURFACE_BUILDERS.register("carpeted", () -> new CarpetedSurfaceBuilder(CarpetedSurfaceBuilderConfig.CODEC));

}
