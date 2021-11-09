package io.github.bioplethora.world;

import io.github.bioplethora.registry.BioplethoraEntities;
import net.minecraft.entity.EntityClassification;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.world.MobSpawnInfoBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;
import java.util.function.Consumer;

public class BiomeloadingSubscriber {

    /*public static void onBiomeLoadingEvent(final BiomeLoadingEvent event) {
        MobSpawnHandler.addMobSpawns(event);
    }

    private static class MobSpawnHandler {
        private static final Consumer<MobSpawnInfoBuilder> AVIFAUNA = (builder) -> {
            builder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(BioplethoraEntities.CREPHOXL.get(), 20, 1, 2));
        };

        public static void addMobSpawns(BiomeLoadingEvent event) {
            MobSpawnInfoBuilder spawnInfoBuilder = event.getSpawns();
            RegistryKey<Biome> biome = RegistryKey.create(ForgeRegistries.Keys.BIOMES, Objects.requireNonNull(event.getName(), "n"));

            switch (event.getCategory()) {
                case FOREST:
                    if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.FOREST))
                        AVIFAUNA.accept(spawnInfoBuilder);
                case JUNGLE:
                    if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.JUNGLE))
                        AVIFAUNA.accept(spawnInfoBuilder);
            }
        }
    }*/
}
