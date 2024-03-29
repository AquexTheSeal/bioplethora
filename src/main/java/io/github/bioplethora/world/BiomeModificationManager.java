package io.github.bioplethora.world;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.api.world.WorldgenUtils;
import io.github.bioplethora.config.BPConfig;
import io.github.bioplethora.registry.BPParticles;
import io.github.bioplethora.registry.worldgen.BPConfiguredSurfaceBuilders;
import net.minecraft.block.ChorusPlantBlock;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = Bioplethora.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BiomeModificationManager {

    @SubscribeEvent
    public static void surfaceBuilderModification(final FMLServerAboutToStartEvent event) {
        MutableRegistry<Biome> biome = event.getServer().registryAccess().registryOrThrow(Registry.BIOME_REGISTRY);

        if (!BPConfig.WORLDGEN.createNewSpongeBiome.get()) {
            changeSurfaceBuilder(biome, Biomes.END_HIGHLANDS, () -> BPConfiguredSurfaceBuilders.ENDURION_SURFACE);
            changeSurfaceBuilder(biome, Biomes.END_MIDLANDS, () -> BPConfiguredSurfaceBuilders.ENDURION_SURFACE);
            changeSurfaceBuilder(biome, Biomes.SMALL_END_ISLANDS, () -> BPConfiguredSurfaceBuilders.ENDURION_SURFACE);
        }
    }

    @SubscribeEvent
    public static void changeOthers(final BiomeLoadingEvent event) {
        if (WorldgenUtils.getBiomeFromEvent(event, "end_highlands")) {
            event.setEffects(new BiomeAmbience.Builder()
                    .waterColor(-6599759)
                    .waterFogColor(-13158998)
                    .fogColor(-12378263)
                    .skyColor(-12378263)
                    .ambientParticle(new ParticleEffectAmbience(BPParticles.NIGHT_GAZE.get(), 0.04F))
                    .ambientLoopSound(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_MOOD)
                    .ambientMoodSound(new MoodSoundAmbience(SoundEvents.AMBIENT_NETHER_WASTES_MOOD, 6000, 8, 2.0D))
                    .build());
        }
        if (WorldgenUtils.getBiomeFromEvent(event, "end_midlands") || WorldgenUtils.getBiomeFromEvent(event, "end_barrens")) {
            event.setEffects(new BiomeAmbience.Builder()
                    .waterColor(-6599759)
                    .waterFogColor(-13158998)
                    .fogColor(-12378263)
                    .skyColor(-12378263)
                    .ambientParticle(new ParticleEffectAmbience(BPParticles.NIGHT_GAZE.get(), 0.03F))
                    .ambientLoopSound(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_MOOD)
                    .ambientMoodSound(new MoodSoundAmbience(SoundEvents.AMBIENT_NETHER_WASTES_MOOD, 6000, 8, 2.0D))
                    .build());
        }
    }

    public static void changeSurfaceBuilder(MutableRegistry<Biome> mutable, RegistryKey<Biome> biome, Supplier<ConfiguredSurfaceBuilder<?>> surface) {
        mutable.get(biome).getGenerationSettings().surfaceBuilder = surface;
    }
}
