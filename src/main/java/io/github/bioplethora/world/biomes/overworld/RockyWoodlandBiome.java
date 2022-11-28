package io.github.bioplethora.world.biomes.overworld;

import io.github.bioplethora.registry.BPEntities;
import net.minecraft.client.audio.BackgroundMusicTracks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;

import java.util.function.Supplier;

public class RockyWoodlandBiome {

    public static Biome make(final Supplier<ConfiguredSurfaceBuilder<?>> surfaceBuilder, float depth, float scale) {
        MobSpawnInfo.Builder spawnInfo = new MobSpawnInfo.Builder();
        DefaultBiomeFeatures.farmAnimals(spawnInfo);
        DefaultBiomeFeatures.commonSpawns(spawnInfo);

        spawnInfo.addSpawn(EntityClassification.CREATURE,
                new MobSpawnInfo.Spawners(BPEntities.ALPHEM.get(), 50, 2, 6));

        BiomeGenerationSettings.Builder biomeGenSettings = (new BiomeGenerationSettings.Builder()).surfaceBuilder(surfaceBuilder);

        biomeGenSettings.addStructureStart(StructureFeatures.VILLAGE_SNOWY);
        biomeGenSettings.addStructureStart(StructureFeatures.MINESHAFT);
        biomeGenSettings.addStructureStart(StructureFeatures.RUINED_PORTAL_SWAMP);
        biomeGenSettings.addStructureStart(StructureFeatures.BURIED_TREASURE);

        DefaultBiomeFeatures.addDefaultCarvers(biomeGenSettings);
        DefaultBiomeFeatures.addDefaultCarvers(biomeGenSettings);

        DefaultBiomeFeatures.addTaigaTrees(biomeGenSettings);
        DefaultBiomeFeatures.addTaigaTrees(biomeGenSettings);

        DefaultBiomeFeatures.addSurfaceFreezing(biomeGenSettings);

        DefaultBiomeFeatures.addDefaultLakes(biomeGenSettings);
        DefaultBiomeFeatures.addTaigaGrass(biomeGenSettings);
        DefaultBiomeFeatures.addFerns(biomeGenSettings);
        DefaultBiomeFeatures.addForestFlowers(biomeGenSettings);
        DefaultBiomeFeatures.addDefaultMonsterRoom(biomeGenSettings);
        DefaultBiomeFeatures.addDefaultUndergroundVariety(biomeGenSettings);
        DefaultBiomeFeatures.addDefaultOres(biomeGenSettings);
        DefaultBiomeFeatures.addSwampClayDisk(biomeGenSettings);
        DefaultBiomeFeatures.addDefaultMushrooms(biomeGenSettings);
        DefaultBiomeFeatures.addDesertExtraVegetation(biomeGenSettings);
        DefaultBiomeFeatures.addDefaultSprings(biomeGenSettings);
        DefaultBiomeFeatures.addBambooVegetation(biomeGenSettings);
        DefaultBiomeFeatures.addMossyStoneBlock(biomeGenSettings);

        biomeGenSettings.addFeature(GenerationStage.Decoration.LAKES, Features.LAKE_LAVA);

        return (new Biome.Builder()).precipitation(Biome.RainType.SNOW).biomeCategory(Biome.Category.ICY).depth(depth).scale(scale)
                .temperature(-0.5F).downfall(0.5F).specialEffects((new BiomeAmbience.Builder())
                        .fogColor(12638463)
                        .waterColor(4159204)
                        .waterFogColor(329011)
                        .skyColor(7972607)
                        .foliageColorOverride(-13266085)
                        .grassColorOverride(-9923462)
                        .ambientLoopSound(SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP)
                        .ambientMoodSound(new MoodSoundAmbience(SoundEvents.AMBIENT_WARPED_FOREST_MOOD, 6000, 8, 2.0D))
                        .ambientAdditionsSound(new SoundAdditionsAmbience(SoundEvents.AMBIENT_NETHER_WASTES_ADDITIONS, 0.0111D))
                        .backgroundMusic(BackgroundMusicTracks.createGameMusic(SoundEvents.MUSIC_BIOME_CRIMSON_FOREST))
                        .build())
                .mobSpawnSettings(spawnInfo.build()).generationSettings(biomeGenSettings.build()).build();
    }
}
