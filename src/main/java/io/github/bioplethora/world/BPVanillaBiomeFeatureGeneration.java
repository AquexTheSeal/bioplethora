package io.github.bioplethora.world;

import com.google.common.collect.ImmutableList;
import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.api.world.WorldgenUtils;
import io.github.bioplethora.config.BPConfig;
import io.github.bioplethora.registry.worldgen.BPConfiguredFeatures;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = Bioplethora.MOD_ID)
public class BPVanillaBiomeFeatureGeneration {

    @SubscribeEvent
    public static void generateFeatures(final BiomeLoadingEvent event) {
        RegistryKey<Biome> key = RegistryKey.create(Registry.BIOME_REGISTRY, event.getName());
        Set<BiomeDictionary.Type> types = BiomeDictionary.getTypes(key);

        List<Supplier<ConfiguredFeature<?, ?>>> topLayerDeco = event.getGeneration().getFeatures(GenerationStage.Decoration.TOP_LAYER_MODIFICATION);

        List<Supplier<ConfiguredFeature<?, ?>>> undergroundDeco = event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_DECORATION);
        List<Supplier<ConfiguredFeature<?, ?>>> vegDeco = event.getGeneration().getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION);

        if (types.contains(BiomeDictionary.Type.OVERWORLD)) {
            undergroundDeco.add(() -> BPConfiguredFeatures.FLEIGNARITE_REMAINS);
            undergroundDeco.add(() -> BPConfiguredFeatures.FLEIGNARITE_VINES);
            vegDeco.add(() -> BPConfiguredFeatures.FLEIGNARITE_REMAINS);
            vegDeco.add(() -> BPConfiguredFeatures.FLEIGNARITE_VINES);
        }

        if (types.contains(BiomeDictionary.Type.NETHER)) {
            if (WorldgenUtils.getBiomeFromEvent(event, WorldgenUtils.BASALT_DELTAS)) {
                vegDeco.add(() -> BPConfiguredFeatures.BASALT_SPELEOTHERM);

                vegDeco.add(() -> BPConfiguredFeatures.LAVA_SPIRE);
            }
            if (WorldgenUtils.getBiomeFromEvent(event, WorldgenUtils.NETHER_WASTES)) {
                vegDeco.add(() -> BPConfiguredFeatures.THONTUS_THISTLE);

                vegDeco.add(() -> BPConfiguredFeatures.LAVA_SPIRE);
            }
            if (WorldgenUtils.getBiomeFromEvent(event, WorldgenUtils.WARPED_FOREST)) {
                vegDeco.add(() -> BPConfiguredFeatures.WARPED_DANCER);

                vegDeco.add(() -> BPConfiguredFeatures.TURQUOISE_PENDENT);
            }
            if (WorldgenUtils.getBiomeFromEvent(event, WorldgenUtils.CRIMSON_FOREST)) {
                vegDeco.add(() -> BPConfiguredFeatures.CERISE_IVY);

                vegDeco.add(() -> BPConfiguredFeatures.LAVA_SPIRE);
            }
            if (WorldgenUtils.getBiomeFromEvent(event, WorldgenUtils.SOUL_SAND_VALLEY)) {
                vegDeco.add(() -> BPConfiguredFeatures.SOUL_MINISHROOM);
                vegDeco.add(() -> BPConfiguredFeatures.SOUL_BIGSHROOM);

                vegDeco.add(() -> BPConfiguredFeatures.SOUL_SPROUTS);
                vegDeco.add(() -> BPConfiguredFeatures.SOUL_TALL_GRASS);

                vegDeco.add(() -> BPConfiguredFeatures.SPIRIT_DANGLER);

                vegDeco.add(() -> BPConfiguredFeatures.SOUL_ETERN);
            }
        }

        if (types.contains(BiomeDictionary.Type.END)) {
            if (BPConfig.WORLDGEN.cyraLakesEnd.get()) undergroundDeco.add(() -> BPConfiguredFeatures.CYRA_LAKE);

            if (WorldgenUtils.getBiomeFromEvent(event, WorldgenUtils.END_HIGHLANDS)) {
                if (BPConfig.WORLDGEN.chorusLanternHighlands.get()) vegDeco.add(() -> BPConfiguredFeatures.CHORUS_LANTERN_HIGHLANDS_PATCH);
                if (BPConfig.WORLDGEN.endSpikeHighlands.get()) vegDeco.add(() -> BPConfiguredFeatures.END_LAND_SPIKE_PATCH);
            }

            if (WorldgenUtils.getBiomeFromEvent(event, WorldgenUtils.END_MIDLANDS)) {
                if (BPConfig.WORLDGEN.chorusLanternMidlands.get()) vegDeco.add(() -> BPConfiguredFeatures.CHORUS_LANTERN_MIDLANDS_PATCH);

            }

            if (WorldgenUtils.getBiomeFromEvent(event, WorldgenUtils.SMALL_END_ISLANDS)) {
                if (BPConfig.WORLDGEN.endIcicleIslands.get()) vegDeco.add(() -> BPConfiguredFeatures.END_ISLANDS_ICICLE_PATCH);
                if (BPConfig.WORLDGEN.endFrozenIslands.get()) vegDeco.add(() -> BPConfiguredFeatures.END_FROZEN_ISLAND_DECORATED);

                vegDeco.add(() -> BPConfiguredFeatures.FROSTEM);

                vegDeco.add(() -> BPConfiguredFeatures.SPINXELTHORN);
                vegDeco.add(() -> BPConfiguredFeatures.GLACYNTH);
            }
        }
    }

    public static ImmutableList<Block> stoneBlocks() {
        return ImmutableList.of(Blocks.STONE, Blocks.ANDESITE, Blocks.DIORITE, Blocks.GRANITE);
    }

    public static ImmutableList<BlockState> stoneBlockstates() {
        return ImmutableList.of(Blocks.STONE.defaultBlockState(), Blocks.ANDESITE.defaultBlockState(), Blocks.DIORITE.defaultBlockState(), Blocks.GRANITE.defaultBlockState());
    }

    public static Random seedFleignarChunk(int pChunkX, int pChunkZ, long pSeed, long l) {
        return new Random(pSeed + ((long) pChunkX * pChunkX * 4987142) + (pChunkX * 5947611L) + (long) pChunkZ * pChunkZ * 4392871L + (pChunkZ * 389711L) ^ l);
    }

    public static boolean isFleignariteChunk(BlockPos pos, ISeedReader seedReader) {
        ChunkPos chunkpos = new ChunkPos(pos);
        return seedFleignarChunk(chunkpos.x, chunkpos.z, seedReader.getSeed(), 987234911L).nextInt(20) == 0;
    }
}
