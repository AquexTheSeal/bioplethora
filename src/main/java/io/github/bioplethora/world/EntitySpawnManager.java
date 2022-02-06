package io.github.bioplethora.world;

import io.github.bioplethora.BioplethoraConfig;
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
        BioplethoraMobSpawns.acceptMobSpawns(event);
    }

    private static class BioplethoraMobSpawns {

        static Integer spawnMultiplier = BioplethoraConfig.COMMON.mobSpawnWeightMultiplier.get();
        static EntityClassification creature = EntityClassification.CREATURE;
        static EntityClassification monster = EntityClassification.MONSTER;
        static EntityClassification waterCreature = EntityClassification.WATER_CREATURE;
        static EntityClassification waterAmbient = EntityClassification.WATER_AMBIENT;

        private static final Consumer<MobSpawnInfoBuilder> FOREST_ENTITIES = (builder) -> {
            //Crephoxl
            builder.addSpawn(creature, new MobSpawnInfo.Spawners(BioplethoraEntities.CREPHOXL.get(), 10 * spawnMultiplier, 1, 1));
            builder.addSpawn(monster, new MobSpawnInfo.Spawners(BioplethoraEntities.CREPHOXL.get(), 7 * spawnMultiplier, 1, 1));

            //Alphem
            builder.addSpawn(creature, new MobSpawnInfo.Spawners(BioplethoraEntities.ALPHEM.get(), 18 * spawnMultiplier, 4, 10));
            builder.addSpawn(monster, new MobSpawnInfo.Spawners(BioplethoraEntities.ALPHEM.get(), 5 * spawnMultiplier, 4, 10));
        };

        private static final Consumer<MobSpawnInfoBuilder> JUNGLE_ENTITIES = (builder) -> {
            //Crephoxl
            builder.addSpawn(creature, new MobSpawnInfo.Spawners(BioplethoraEntities.CREPHOXL.get(), 10 * spawnMultiplier, 1, 1));
            builder.addSpawn(monster, new MobSpawnInfo.Spawners(BioplethoraEntities.CREPHOXL.get(), 7 * spawnMultiplier, 1, 1));
        };

        private static final Consumer<MobSpawnInfoBuilder> TAIGA_ENTITIES = (builder) -> {
            //Bellophgolem
            builder.addSpawn(monster, new MobSpawnInfo.Spawners(BioplethoraEntities.BELLOPHGOLEM.get(), 5 * spawnMultiplier, 1, 1));

            //Crephoxl
            builder.addSpawn(creature, new MobSpawnInfo.Spawners(BioplethoraEntities.CREPHOXL.get(), 10 * spawnMultiplier, 1, 1));
            builder.addSpawn(monster, new MobSpawnInfo.Spawners(BioplethoraEntities.CREPHOXL.get(), 7 * spawnMultiplier, 1, 1));

            //Peaguin
            builder.addSpawn(creature, new MobSpawnInfo.Spawners(BioplethoraEntities.PEAGUIN.get(), 15 * spawnMultiplier, 3, 6));
        };

        private static final Consumer<MobSpawnInfoBuilder> ICY_ENTITIES = (builder) -> {
            //Bellophgolem
            builder.addSpawn(monster, new MobSpawnInfo.Spawners(BioplethoraEntities.BELLOPHGOLEM.get(), 5 * spawnMultiplier, 1, 1));

            //Peaguin
            builder.addSpawn(creature, new MobSpawnInfo.Spawners(BioplethoraEntities.PEAGUIN.get(), 25 * spawnMultiplier, 3, 6));
        };

        private static final Consumer<MobSpawnInfoBuilder> SAVANNA_ENTITIES = (builder) -> {
            //Alphem
            builder.addSpawn(creature, new MobSpawnInfo.Spawners(BioplethoraEntities.ALPHEM.get(), 18 * spawnMultiplier, 4, 10));
            builder.addSpawn(monster, new MobSpawnInfo.Spawners(BioplethoraEntities.ALPHEM.get(), 5 * spawnMultiplier, 4, 10));
            //Dwarf Mossadile
            builder.addSpawn(creature, new MobSpawnInfo.Spawners(BioplethoraEntities.DWARF_MOSSADILE.get(), 30 * BioplethoraConfig.COMMON.mobSpawnWeightMultiplier.get(), 4, 5));
        };

        private static final Consumer<MobSpawnInfoBuilder> WATER_ENTITIES = (builder) -> {
            //Cuttlefish
            builder.addSpawn(waterCreature, new MobSpawnInfo.Spawners(BioplethoraEntities.CUTTLEFISH.get(), 70 * BioplethoraConfig.COMMON.mobSpawnWeightMultiplier.get(), 1, 4));
            //Myliothan
            builder.addSpawn(waterCreature, new MobSpawnInfo.Spawners(BioplethoraEntities.MYLIOTHAN.get(), BioplethoraConfig.COMMON.mobSpawnWeightMultiplier.get(), 1, 1));
        };

        private static final Consumer<MobSpawnInfoBuilder> NETHER_ENTITIES = (builder) -> {
            //Dwarf Mossadile
            builder.addSpawn(monster, new MobSpawnInfo.Spawners(BioplethoraEntities.DWARF_MOSSADILE.get(), 15 * BioplethoraConfig.COMMON.mobSpawnWeightMultiplier.get(), 4, 6));
        };

        private static final Consumer<MobSpawnInfoBuilder> END_ENTITIES = (builder) -> {
            //Gaugalem
            builder.addSpawn(monster, new MobSpawnInfo.Spawners(BioplethoraEntities.GAUGALEM.get(), 2 * BioplethoraConfig.COMMON.mobSpawnWeightMultiplier.get(), 1, 1));
        };

        public static void acceptMobSpawns(BiomeLoadingEvent event) {
            MobSpawnInfoBuilder spawnInfoBuilder = event.getSpawns();
            RegistryKey<Biome> biome = RegistryKey.create(ForgeRegistries.Keys.BIOMES, Objects.requireNonNull(event.getName(), "Bioplethora Mob Spawning"));
            boolean hasOverworldType = BiomeDictionary.hasType(biome, BiomeDictionary.Type.OVERWORLD);

            switch (event.getCategory()) {
                case FOREST:
                    if (hasOverworldType && BiomeDictionary.hasType(biome, BiomeDictionary.Type.FOREST))
                        FOREST_ENTITIES.accept(spawnInfoBuilder);
                case JUNGLE:
                    if (hasOverworldType && BiomeDictionary.hasType(biome, BiomeDictionary.Type.JUNGLE))
                        JUNGLE_ENTITIES.accept(spawnInfoBuilder);
                case TAIGA:
                    if (hasOverworldType && (BiomeDictionary.hasType(biome, BiomeDictionary.Type.CONIFEROUS) || BiomeDictionary.hasType(biome, BiomeDictionary.Type.COLD)))
                        TAIGA_ENTITIES.accept(spawnInfoBuilder);
                case ICY:
                    if (hasOverworldType && BiomeDictionary.hasType(biome, BiomeDictionary.Type.COLD))
                        ICY_ENTITIES.accept(spawnInfoBuilder);
                case SAVANNA:
                    if (hasOverworldType && BiomeDictionary.hasType(biome, BiomeDictionary.Type.SAVANNA))
                        SAVANNA_ENTITIES.accept(spawnInfoBuilder);
                case OCEAN:
                    if (hasOverworldType || BiomeDictionary.hasType(biome, BiomeDictionary.Type.RIVER))
                        WATER_ENTITIES.accept(spawnInfoBuilder);
                case NETHER:
                    if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.NETHER))
                        NETHER_ENTITIES.accept(spawnInfoBuilder);
                case THEEND:
                    if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.END))
                        END_ENTITIES.accept(spawnInfoBuilder);
                    break;
                default:
                    if (!BiomeDictionary.hasType(biome, BiomeDictionary.Type.OCEAN) && !BiomeDictionary.hasType(biome, BiomeDictionary.Type.RIVER))
                        break;
            }
        }
    }
}
