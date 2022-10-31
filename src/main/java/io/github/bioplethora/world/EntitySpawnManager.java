package io.github.bioplethora.world;

import io.github.bioplethora.api.world.WorldgenUtils;
import io.github.bioplethora.config.BPConfig;
import io.github.bioplethora.registry.BPEntities;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.world.MobSpawnInfoBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Used for spawning Bioplethora's mobs in vanilla biomes, spawning in Bioplethora's biomes will be handled inside the biome class itself.
 */
public class EntitySpawnManager {

    public static void onBiomeLoadingEvent(final BiomeLoadingEvent event) {
        BioplethoraMobSpawns.acceptMobSpawns(event);
    }

    public static class BioplethoraMobSpawns {

        static Integer spawnMultiplier = BPConfig.COMMON.mobSpawnWeightMultiplier.get();
        static EntityClassification creature = EntityClassification.CREATURE;
        static EntityClassification monster = EntityClassification.MONSTER;
        static EntityClassification ambient = EntityClassification.AMBIENT;
        static EntityClassification waterCreature = EntityClassification.WATER_CREATURE;
        static EntityClassification waterAmbient = EntityClassification.WATER_AMBIENT;

        public static final Consumer<MobSpawnInfoBuilder> OVERWORLD_ENTITIES = (builder) -> {
            // Cavern Fleignar
            if (BPConfig.COMMON.spawnCavernFleignar.get()) {
                builder.addSpawn(monster, new MobSpawnInfo.Spawners(BPEntities.CAVERN_FLEIGNAR.get(), 120 * spawnMultiplier, 4, 6));
            }
        };

        public static final Consumer<MobSpawnInfoBuilder> DESERT_ENTITIES = (builder) -> {
            // Nandbri
            if(BPConfig.COMMON.spawnNandbri.get()) {
                builder.addSpawn(creature, new MobSpawnInfo.Spawners(BPEntities.NANDBRI.get(), 3 * spawnMultiplier, 1, 1));
                builder.addSpawn(monster, new MobSpawnInfo.Spawners(BPEntities.NANDBRI.get(), 30 * spawnMultiplier, 1, 1));
            }
        };

        public static final Consumer<MobSpawnInfoBuilder> SWAMP_ENTITIES = (builder) -> {
            // Trapjaw
            if (BPConfig.COMMON.spawnTrapjaw.get()) {
                builder.addSpawn(creature, new MobSpawnInfo.Spawners(BPEntities.TRAPJAW.get(), 5 * spawnMultiplier, 1, 1));
                builder.addSpawn(monster, new MobSpawnInfo.Spawners(BPEntities.TRAPJAW.get(), 14 * spawnMultiplier, 1, 1));
            }
        };

        public static final Consumer<MobSpawnInfoBuilder> FOREST_ENTITIES = (builder) -> {
            //Crephoxl
            if (BPConfig.COMMON.spawnCrephoxl.get()) {
                builder.addSpawn(creature, new MobSpawnInfo.Spawners(BPEntities.CREPHOXL.get(), 10 * spawnMultiplier, 1, 1));
                builder.addSpawn(monster, new MobSpawnInfo.Spawners(BPEntities.CREPHOXL.get(), 7 * spawnMultiplier, 1, 1));
            }

            //Alphem
            if (BPConfig.COMMON.spawnAlphem.get()) {
                builder.addSpawn(creature, new MobSpawnInfo.Spawners(BPEntities.ALPHEM.get(), 18 * spawnMultiplier, 4, 10));
                builder.addSpawn(monster, new MobSpawnInfo.Spawners(BPEntities.ALPHEM.get(), 5 * spawnMultiplier, 4, 10));
            }
        };

        public static final Consumer<MobSpawnInfoBuilder> JUNGLE_ENTITIES = (builder) -> {
            //Crephoxl
            if (BPConfig.COMMON.spawnCrephoxl.get()) {
                builder.addSpawn(creature, new MobSpawnInfo.Spawners(BPEntities.CREPHOXL.get(), 10 * spawnMultiplier, 1, 1));
                builder.addSpawn(monster, new MobSpawnInfo.Spawners(BPEntities.CREPHOXL.get(), 7 * spawnMultiplier, 1, 1));
            }
        };

        public static final Consumer<MobSpawnInfoBuilder> TAIGA_ENTITIES = (builder) -> {
            //FrostbiteGolem
            if (BPConfig.COMMON.spawnFrostbiteGolem.get()) {
                builder.addSpawn(monster, new MobSpawnInfo.Spawners(BPEntities.FROSTBITE_GOLEM.get(), 5 * spawnMultiplier, 1, 1));
            }

            //Crephoxl
            if (BPConfig.COMMON.spawnCrephoxl.get()) {
                builder.addSpawn(creature, new MobSpawnInfo.Spawners(BPEntities.CREPHOXL.get(), 10 * spawnMultiplier, 1, 1));
                builder.addSpawn(monster, new MobSpawnInfo.Spawners(BPEntities.CREPHOXL.get(), 7 * spawnMultiplier, 1, 1));
            }

            //Peaguin
            if (BPConfig.COMMON.spawnPeaguin.get()) {
                builder.addSpawn(creature, new MobSpawnInfo.Spawners(BPEntities.PEAGUIN.get(), 15 * spawnMultiplier, 3, 6));
            }
        };

        public static final Consumer<MobSpawnInfoBuilder> ICY_ENTITIES = (builder) -> {
            //FrostbiteGolem
            if (BPConfig.COMMON.spawnFrostbiteGolem.get()) {
                builder.addSpawn(monster, new MobSpawnInfo.Spawners(BPEntities.FROSTBITE_GOLEM.get(), 5 * spawnMultiplier, 1, 1));
            }

            //Peaguin
            if (BPConfig.COMMON.spawnPeaguin.get()) {
                builder.addSpawn(creature, new MobSpawnInfo.Spawners(BPEntities.PEAGUIN.get(), 25 * spawnMultiplier, 3, 6));
            }
        };

        public static final Consumer<MobSpawnInfoBuilder> SAVANNA_ENTITIES = (builder) -> {
            //Alphem
            if (BPConfig.COMMON.spawnAlphem.get()) {
                builder.addSpawn(creature, new MobSpawnInfo.Spawners(BPEntities.ALPHEM.get(), 18 * spawnMultiplier, 4, 10));
                builder.addSpawn(monster, new MobSpawnInfo.Spawners(BPEntities.ALPHEM.get(), 5 * spawnMultiplier, 4, 10));
            }

            //Dwarf Mossadile
            if (BPConfig.COMMON.spawnDwarfMossadile.get()) {
                builder.addSpawn(creature, new MobSpawnInfo.Spawners(BPEntities.DWARF_MOSSADILE.get(), 30 * BPConfig.COMMON.mobSpawnWeightMultiplier.get(), 4, 5));
            }

            // Trapjaw
            if(BPConfig.COMMON.spawnTrapjaw.get()) {
                builder.addSpawn(creature, new MobSpawnInfo.Spawners(BPEntities.TRAPJAW.get(), 4 * spawnMultiplier, 1, 1));
                builder.addSpawn(monster, new MobSpawnInfo.Spawners(BPEntities.TRAPJAW.get(), 14 * spawnMultiplier, 1, 1));
            }
        };

        public static final Consumer<MobSpawnInfoBuilder> WATER_ENTITIES = (builder) -> {
            //Cuttlefish
            if (BPConfig.COMMON.spawnCuttlefish.get()) {
                builder.addSpawn(waterCreature, new MobSpawnInfo.Spawners(BPEntities.CUTTLEFISH.get(), 40 * BPConfig.COMMON.mobSpawnWeightMultiplier.get(), 1, 4));
            }

            //Myliothan
            if (BPConfig.COMMON.spawnMyliothan.get()) {
                builder.addSpawn(waterCreature, new MobSpawnInfo.Spawners(BPEntities.MYLIOTHAN.get(), 5 * BPConfig.COMMON.mobSpawnWeightMultiplier.get(), 1, 1));
            }
        };

        public static final Consumer<MobSpawnInfoBuilder> BASALT_DELTAS_ENTITIES = (builder) -> {
            //Fiery Eurydn
            createSpawn(builder, monster, BPEntities.FIERY_EURYDN, 8, 1, 3, BPConfig.COMMON.spawnFieryEurydn);
        };

        public static final Consumer<MobSpawnInfoBuilder> NETHER_WASTES_ENTITIES = (builder) -> {
            //Dwarf Mossadile
            createSpawn(builder, monster, BPEntities.DWARF_MOSSADILE, 45, 2, 3, BPConfig.COMMON.spawnDwarfMossadile);
        };

        public static final Consumer<MobSpawnInfoBuilder> WARPED_FOREST_ENTITIES = (builder) -> {
            //Dwarf Mossadile
            createSpawn(builder, monster, BPEntities.DWARF_MOSSADILE, 1, 1, 2, BPConfig.COMMON.spawnDwarfMossadile);
        };

        public static final Consumer<MobSpawnInfoBuilder> CRIMSON_FOREST_ENTITIES = (builder) -> {
            //Dwarf Mossadile
            createSpawn(builder, monster, BPEntities.DWARF_MOSSADILE, 4, 1, 3, BPConfig.COMMON.spawnDwarfMossadile);
        };

        public static final Consumer<MobSpawnInfoBuilder> SOUL_SAND_VALLEY_ENTITIES = (builder) -> {
            //Soul Eurydn
            createSpawn(builder, monster, BPEntities.SOUL_EURYDN, 6, 1, 3, BPConfig.COMMON.spawnSoulEurydn);
        };

        public static final Consumer<MobSpawnInfoBuilder> END_ENTITIES = (builder) -> {
            //Gaugalem
            createSpawn(builder, monster, BPEntities.GAUGALEM, 2, 1, 1, BPConfig.COMMON.spawnGaugalem);

            //Onofish
            createSpawn(builder, monster, BPEntities.ONOFISH, 6, 1, 2, BPConfig.COMMON.spawnOnofish);

            //Voidjaw
            createSpawn(builder, monster, BPEntities.VOIDJAW, 3, 1, 1, BPConfig.COMMON.spawnOnofish);
        };

        public static void acceptMobSpawns(BiomeLoadingEvent event) {
            MobSpawnInfoBuilder spawnInfoBuilder = event.getSpawns();
            RegistryKey<Biome> biome = RegistryKey.create(ForgeRegistries.Keys.BIOMES, event.getName());
            boolean hasOverworldType = BiomeDictionary.hasType(biome, BiomeDictionary.Type.OVERWORLD);

            if (hasOverworldType) {
                OVERWORLD_ENTITIES.accept(spawnInfoBuilder);
            }

            switch (event.getCategory()) {
                case FOREST:
                    if (hasOverworldType && BiomeDictionary.hasType(biome, BiomeDictionary.Type.FOREST))
                        FOREST_ENTITIES.accept(spawnInfoBuilder);
                case DESERT:
                    if (hasOverworldType && BiomeDictionary.hasType(biome, BiomeDictionary.Type.DRY))
                        DESERT_ENTITIES.accept(spawnInfoBuilder);
                case SWAMP:
                    if (hasOverworldType && BiomeDictionary.hasType(biome, BiomeDictionary.Type.SWAMP))
                        SWAMP_ENTITIES.accept(spawnInfoBuilder);
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
                    if (WorldgenUtils.getBiomeFromEvent(event, WorldgenUtils.BASALT_DELTAS)) {
                        BASALT_DELTAS_ENTITIES.accept(spawnInfoBuilder);
                    }
                    if (WorldgenUtils.getBiomeFromEvent(event, WorldgenUtils.NETHER_WASTES)) {
                        NETHER_WASTES_ENTITIES.accept(spawnInfoBuilder);
                    }
                    if (WorldgenUtils.getBiomeFromEvent(event, WorldgenUtils.WARPED_FOREST)) {
                        WARPED_FOREST_ENTITIES.accept(spawnInfoBuilder);
                    }
                    if (WorldgenUtils.getBiomeFromEvent(event, WorldgenUtils.CRIMSON_FOREST)) {
                        CRIMSON_FOREST_ENTITIES.accept(spawnInfoBuilder);
                    }
                    if (WorldgenUtils.getBiomeFromEvent(event, WorldgenUtils.SOUL_SAND_VALLEY)) {
                        SOUL_SAND_VALLEY_ENTITIES.accept(spawnInfoBuilder);
                    }
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

    public static void createSpawn(MobSpawnInfoBuilder builder, EntityClassification classification, Supplier<? extends EntityType<?>> entity, int initWeight, int minSpawn, int maxSpawn, @Nullable ForgeConfigSpec.ConfigValue<Boolean> config) {
        if (config != null) {
            if (config.get()) {
                builder.addSpawn(classification, new MobSpawnInfo.Spawners(entity.get(), initWeight * BPConfig.COMMON.mobSpawnWeightMultiplier.get(), minSpawn, maxSpawn));
            }
        } else {
            builder.addSpawn(classification, new MobSpawnInfo.Spawners(entity.get(), initWeight * BPConfig.COMMON.mobSpawnWeightMultiplier.get(), minSpawn, maxSpawn));
        }
    }
}
