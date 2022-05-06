package io.github.bioplethora.world;

import io.github.bioplethora.Bioplethora;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = Bioplethora.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BiomeModificationManager {

    @SubscribeEvent
    public static void surfaceBuilderModification(final FMLServerAboutToStartEvent event) {
        MutableRegistry<Biome> biome = event.getServer().registryAccess().registryOrThrow(Registry.BIOME_REGISTRY);

        //changeSurfaceBuilder(biome, Biomes.END_HIGHLANDS, () -> BPConfiguredSurfaceBuilders.END_HIGHLANDS_SURFACE);
        //changeSurfaceBuilder(biome, Biomes.END_MIDLANDS, () -> BPConfiguredSurfaceBuilders.END_HIGHLANDS_SURFACE);
    }

    public static void changeSurfaceBuilder(MutableRegistry<Biome> mutable, RegistryKey<Biome> biome, Supplier<ConfiguredSurfaceBuilder<?>> surface) {
        mutable.get(biome).getGenerationSettings().surfaceBuilder = surface;
    }
}
