package io.github.bioplethora.world;

import io.github.bioplethora.config.BioplethoraConfig;
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
            builder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(BioplethoraEntities.CREPHOXL.get(), 10 * BioplethoraConfig.COMMON.mobSpawnWeightMultiplier.get(), 1, 1));
            builder.addSpawn(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(BioplethoraEntities.CREPHOXL.get(), 7 * BioplethoraConfig.COMMON.mobSpawnWeightMultiplier.get(), 1, 1));

            //Alphem
            builder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(BioplethoraEntities.ALPHEM.get(), 18 * BioplethoraConfig.COMMON.mobSpawnWeightMultiplier.get(), 4, 10));
            builder.addSpawn(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(BioplethoraEntities.ALPHEM.get(), 5 * BioplethoraConfig.COMMON.mobSpawnWeightMultiplier.get(), 4, 10));
        };

        private static final Consumer<MobSpawnInfoBuilder> JUNGLE_ENTITIES = (builder) -> {
            //Crephoxl
            builder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(BioplethoraEntities.CREPHOXL.get(), 10 * BioplethoraConfig.COMMON.mobSpawnWeightMultiplier.get(), 1, 1));
            builder.addSpawn(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(BioplethoraEntities.CREPHOXL.get(), 7 * BioplethoraConfig.COMMON.mobSpawnWeightMultiplier.get(), 1, 1));
        };

        private static final Consumer<MobSpawnInfoBuilder> TAIGA_ENTITIES = (builder) -> {
            //Bellophgolem
            builder.addSpawn(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(BioplethoraEntities.BELLOPHGOLEM.get(), 5 * BioplethoraConfig.COMMON.mobSpawnWeightMultiplier.get(), 1, 1));

            //Crephoxl
            builder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(BioplethoraEntities.CREPHOXL.get(), 10 * BioplethoraConfig.COMMON.mobSpawnWeightMultiplier.get(), 1, 1));
            builder.addSpawn(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(BioplethoraEntities.CREPHOXL.get(), 7 * BioplethoraConfig.COMMON.mobSpawnWeightMultiplier.get(), 1, 1));

            //Peaguin
            builder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(BioplethoraEntities.PEAGUIN.get(), 15 * BioplethoraConfig.COMMON.mobSpawnWeightMultiplier.get(), 3, 6));
        };

        private static final Consumer<MobSpawnInfoBuilder> ICY_ENTITIES = (builder) -> {
            //Bellophgolem
            builder.addSpawn(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(BioplethoraEntities.BELLOPHGOLEM.get(), 5 * BioplethoraConfig.COMMON.mobSpawnWeightMultiplier.get(), 1, 1));

            //Peaguin
            builder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(BioplethoraEntities.PEAGUIN.get(), 25 * BioplethoraConfig.COMMON.mobSpawnWeightMultiplier.get(), 3, 6));
        };

        private static final Consumer<MobSpawnInfoBuilder> SAVANNA_ENTITIES = (builder) -> {
            //Alphem
            builder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(BioplethoraEntities.ALPHEM.get(), 18 * BioplethoraConfig.COMMON.mobSpawnWeightMultiplier.get(), 4, 10));
            builder.addSpawn(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(BioplethoraEntities.ALPHEM.get(), 5 * BioplethoraConfig.COMMON.mobSpawnWeightMultiplier.get(), 4, 10));
        };

        private static final Consumer<MobSpawnInfoBuilder> WATER_ENTITIES = (builder) -> {
            //Cuttlefish
            builder.addSpawn(EntityClassification.WATER_CREATURE, new MobSpawnInfo.Spawners(BioplethoraEntities.CUTTLEFISH.get(), 35 * BioplethoraConfig.COMMON.mobSpawnWeightMultiplier.get(), 1, 4));
        };

        private static final Consumer<MobSpawnInfoBuilder> END_ENTITIES = (builder) -> {
            //Elite Undead
            builder.addSpawn(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(BioplethoraEntities.GAUGALEM.get(), 10 * BioplethoraConfig.COMMON.mobSpawnWeightMultiplier.get(), 1, 2));
        };

        public static void spawnMobs(BiomeLoadingEvent event) {
            MobSpawnInfoBuilder spawnInfoBuilder = event.getSpawns();
            RegistryKey<Biome> biome = RegistryKey.create(ForgeRegistries.Keys.BIOMES, Objects.requireNonNull(event.getName(), "Biome Spawning Stuff."));

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
                case ICY:
                    if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.OVERWORLD))
                        ICY_ENTITIES.accept(spawnInfoBuilder);
                case SAVANNA:
                    if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.OVERWORLD))
                        SAVANNA_ENTITIES.accept(spawnInfoBuilder);
                case OCEAN:
                    if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.OCEAN) || BiomeDictionary.hasType(biome, BiomeDictionary.Type.RIVER))
                        WATER_ENTITIES.accept(spawnInfoBuilder);
                case THEEND:
                    if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.END))
                        END_ENTITIES.accept(spawnInfoBuilder);
                    break;
                default:
                    if (!BiomeDictionary.hasType(biome, BiomeDictionary.Type.OCEAN) && !BiomeDictionary.hasType(biome, BiomeDictionary.Type.RIVER))
						//OVERWORLD_MOBS.accept(spawnInfoBuilder);
                        break;
            }
        }
    }
}
