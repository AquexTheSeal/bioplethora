package io.github.bioplethora.world;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.api.world.WorldgenUtils;
import io.github.bioplethora.config.BPCommonConfig;
import io.github.bioplethora.config.BPConfig;
import io.github.bioplethora.registry.worldgen.BPConfiguredSurfaceBuilders;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.DimensionType;
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

        if (BPConfig.COMMON.changeEndBiomeSurfaces.get()) {
            changeSurfaceBuilder(biome, Biomes.END_HIGHLANDS, () -> BPConfiguredSurfaceBuilders.ENDURION_SURFACE);
            changeSurfaceBuilder(biome, Biomes.END_MIDLANDS, () -> BPConfiguredSurfaceBuilders.ENDURION_SURFACE);
        }
    }

    @SubscribeEvent
    public static void changeOthers(final BiomeLoadingEvent event) {
        if (WorldgenUtils.getBiomeFromEvent(event, "end_highlands")) {
            event.setEffects(new BiomeAmbience.Builder()
                    .waterColor(-13817728)
                    .waterFogColor(-13158998)
                    .fogColor(-12378263)
                    .skyColor(-12378263)
                    .ambientParticle(new ParticleEffectAmbience(ParticleTypes.CRIMSON_SPORE, 0.20F))
                    .ambientLoopSound(SoundEvents.AMBIENT_NETHER_WASTES_LOOP)
                    .ambientMoodSound(new MoodSoundAmbience(SoundEvents.AMBIENT_NETHER_WASTES_MOOD, 6000, 8, 2.0D))
                    .build());
        }
        if (WorldgenUtils.getBiomeFromEvent(event, "small_end_islands")) {
            event.setEffects(new BiomeAmbience.Builder()
                    .waterColor(-14271360)
                    .waterFogColor(-13348438)
                    .fogColor(-14791063)
                    .skyColor(-14791063)
                    .ambientParticle(new ParticleEffectAmbience(ParticleTypes.WHITE_ASH, 0.25F))
                    .ambientLoopSound(SoundEvents.AMBIENT_BASALT_DELTAS_LOOP)
                    .ambientMoodSound(new MoodSoundAmbience(SoundEvents.AMBIENT_BASALT_DELTAS_MOOD, 6000, 8, 2.0D))
                    .build());
        }
    }

    public static void changeSurfaceBuilder(MutableRegistry<Biome> mutable, RegistryKey<Biome> biome, Supplier<ConfiguredSurfaceBuilder<?>> surface) {
        mutable.get(biome).getGenerationSettings().surfaceBuilder = surface;
    }
}
