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

public class EntitySpawnManager {

    public static void onBiomeLoadingEvent(final BiomeLoadingEvent event) {
        MobSpawnHandler.spawnMobs(event);
    }

    private static class MobSpawnHandler {

        private static final Consumer<MobSpawnInfoBuilder> FOREST_ENTITIES = (builder) -> {
            //Crephoxl
            builder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(BioplethoraEntities.CREPHOXL.get(), 10, 1, 1));
            builder.addSpawn(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(BioplethoraEntities.CREPHOXL.get(), 10, 1, 1));
        };

        private static final Consumer<MobSpawnInfoBuilder> JUNGLE_ENTITIES = (builder) -> {
            //Crephoxl
            builder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(BioplethoraEntities.CREPHOXL.get(), 15, 1, 1));
            builder.addSpawn(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(BioplethoraEntities.CREPHOXL.get(), 10, 1, 1));
        };

        private static final Consumer<MobSpawnInfoBuilder> TAIGA_ENTITIES = (builder) -> {
            //Crephoxl
            builder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(BioplethoraEntities.CREPHOXL.get(), 15, 1, 1));
            builder.addSpawn(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(BioplethoraEntities.CREPHOXL.get(), 10, 1, 1));
        };

        public static void spawnMobs(BiomeLoadingEvent event) {
            MobSpawnInfoBuilder spawnInfoBuilder = event.getSpawns();
            RegistryKey<Biome> biome = RegistryKey.create(ForgeRegistries.Keys.BIOMES, Objects.requireNonNull(event.getName(), "THE"));

            switch (event.getCategory()) {
                case FOREST:
                    if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.OVERWORLD))
                        FOREST_ENTITIES.accept(spawnInfoBuilder);
                case JUNGLE:
                    if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.OVERWORLD))
                        JUNGLE_ENTITIES.accept(spawnInfoBuilder);
                case TAIGA:
                    if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.OVERWORLD))
                        TAIGA_ENTITIES.accept(spawnInfoBuilder);
                case THEEND:
                case NETHER:
                    break;
                default:
                    if (!BiomeDictionary.hasType(biome, BiomeDictionary.Type.OCEAN) && !BiomeDictionary.hasType(biome, BiomeDictionary.Type.RIVER))
//						OVERWORLD_MOBS.accept(spawnInfoBuilder);
                        break;
            }
        }
    }
}
