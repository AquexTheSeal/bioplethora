package io.github.bioplethora.world.biomes.nether;

import net.minecraft.client.audio.BackgroundMusicTracks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.carver.ConfiguredCarvers;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;

import java.util.function.Supplier;

public class CryeanumPlains {

    public static Biome make(final Supplier<ConfiguredSurfaceBuilder<?>> surfaceBuilder) {
        double d0 = 0.7D;
        double d1 = 0.15D;
        MobSpawnInfo.Builder spawnInfoBuilder = new MobSpawnInfo.Builder();
        spawnInfoBuilder.addSpawn(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.SKELETON, 20, 5, 5));
        spawnInfoBuilder.addSpawn(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.GHAST, 50, 4, 4));
        spawnInfoBuilder.addSpawn(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.ENDERMAN, 1, 4, 4));
        spawnInfoBuilder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.STRIDER, 60, 1, 2));
        spawnInfoBuilder.addMobCharge(EntityType.SKELETON, 0.7D, 0.15D);
        spawnInfoBuilder.addMobCharge(EntityType.GHAST, 0.7D, 0.15D);
        spawnInfoBuilder.addMobCharge(EntityType.ENDERMAN, 0.7D, 0.15D);
        spawnInfoBuilder.addMobCharge(EntityType.STRIDER, 0.7D, 0.15D);

        MobSpawnInfo mobspawninfo = spawnInfoBuilder.build();
        BiomeGenerationSettings.Builder biomegenerationsettings$builder = new BiomeGenerationSettings.Builder();

        biomegenerationsettings$builder.surfaceBuilder(surfaceBuilder);

        biomegenerationsettings$builder.addStructureStart(StructureFeatures.NETHER_BRIDGE);
        biomegenerationsettings$builder.addStructureStart(StructureFeatures.NETHER_FOSSIL);
        biomegenerationsettings$builder.addStructureStart(StructureFeatures.RUINED_PORTAL_NETHER);
        biomegenerationsettings$builder.addStructureStart(StructureFeatures.BASTION_REMNANT);

        biomegenerationsettings$builder.addCarver(GenerationStage.Carving.AIR, ConfiguredCarvers.NETHER_CAVE);

        biomegenerationsettings$builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.SPRING_LAVA);
        biomegenerationsettings$builder.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Features.BASALT_PILLAR);
        biomegenerationsettings$builder.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.SPRING_OPEN);
        biomegenerationsettings$builder.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.GLOWSTONE_EXTRA);
        biomegenerationsettings$builder.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.GLOWSTONE);
        biomegenerationsettings$builder.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.PATCH_CRIMSON_ROOTS);
        biomegenerationsettings$builder.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.PATCH_FIRE);
        biomegenerationsettings$builder.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.PATCH_SOUL_FIRE);
        biomegenerationsettings$builder.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.ORE_MAGMA);
        biomegenerationsettings$builder.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.SPRING_CLOSED);
        biomegenerationsettings$builder.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.ORE_SOUL_SAND);

        DefaultBiomeFeatures.addNetherDefaultOres(biomegenerationsettings$builder);
        Biome.Builder builder1 = new Biome.Builder();
        builder1.precipitation(Biome.RainType.NONE);
        builder1.biomeCategory(Biome.Category.NETHER);
        builder1.depth(0.1F);
        builder1.scale(0.2F);
        builder1.temperature(2.0F);
        builder1.downfall(0.0F);

        builder1.specialEffects((new BiomeAmbience.Builder())
                .waterColor(4159204)
                .waterFogColor(329011)
                .fogColor(1787717).
                skyColor(calculateSkyColor(2.0F))
                .ambientParticle(new ParticleEffectAmbience(ParticleTypes.END_ROD, 0.00625F))
                .ambientLoopSound(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_LOOP)
                .ambientMoodSound(new MoodSoundAmbience(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_MOOD, 6000, 8, 2.0D))
                .ambientAdditionsSound(new SoundAdditionsAmbience(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_ADDITIONS, 0.0111D))
                .backgroundMusic(BackgroundMusicTracks.createGameMusic(SoundEvents.MUSIC_BIOME_SOUL_SAND_VALLEY))
                .build());

        builder1.mobSpawnSettings(mobspawninfo);
        builder1.generationSettings(biomegenerationsettings$builder.build());
        return builder1.build();
    }


    private static int calculateSkyColor(float pTemperature) {
        float lvt_1_1_ = pTemperature / 3.0F;
        lvt_1_1_ = MathHelper.clamp(lvt_1_1_, -1.0F, 1.0F);
        return MathHelper.hsvToRgb(0.62222224F - lvt_1_1_ * 0.05F, 0.5F + lvt_1_1_ * 0.1F, 1.0F);
    }
}
