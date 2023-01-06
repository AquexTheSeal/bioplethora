package io.github.bioplethora.world;

import com.google.common.collect.ImmutableList;
import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.api.world.WorldgenUtils;
import io.github.bioplethora.config.BPConfig;
import io.github.bioplethora.registry.worldgen.BPConfiguredFeatures;
import io.github.bioplethora.registry.worldgen.BPConfiguredWorldCarvers;
import io.github.bioplethora.registry.worldgen.BPWorldCarvers;
import net.fabricmc.fabric.mixin.biome.MixinTheEndBiomeSource;
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
import net.minecraft.world.gen.carver.ConfiguredCarver;
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

        List<Supplier<ConfiguredCarver<?>>> liqCarver = event.getGeneration().getCarvers(GenerationStage.Carving.LIQUID);

        List<Supplier<ConfiguredFeature<?, ?>>> localDeco = event.getGeneration().getFeatures(GenerationStage.Decoration.LOCAL_MODIFICATIONS);
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

            if (!BPConfig.WORLDGEN.createNewSpongeBiome.get()) {
                if (WorldgenUtils.getBiomeFromEvent(event, WorldgenUtils.END_HIGHLANDS)) {

                    //if (BPConfig.WORLDGEN.endSpongeHighlands.get()) liqCarver.add(() -> BPConfiguredWorldCarvers.END_SPRINGS_CARVER);
                    //if (BPConfig.WORLDGEN.endSpongeHighlands.get()) undergroundDeco.add(() -> BPConfiguredFeatures.END_LAND_ROCK);

                    if (BPConfig.WORLDGEN.chorusVegetationHighlands.get()) topLayerDeco.add(() -> BPConfiguredFeatures.ENREDE_KELP);

                    if (BPConfig.WORLDGEN.chorusLanternHighlands.get()) vegDeco.add(() -> BPConfiguredFeatures.CHORUS_LANTERN_HIGHLANDS_PATCH);
                    if (BPConfig.WORLDGEN.chorusVegetationHighlands.get()) vegDeco.add(() -> BPConfiguredFeatures.CHORUS_IDON);
                    if (BPConfig.WORLDGEN.chorusVegetationHighlands.get()) vegDeco.add(() -> BPConfiguredFeatures.CHORUS_IDE_FAN);
                    if (BPConfig.WORLDGEN.chorusVegetationHighlands.get()) vegDeco.add(() -> BPConfiguredFeatures.ENREDE_CORSASCILE);
                    if (BPConfig.WORLDGEN.chorusVegetationHighlands.get()) vegDeco.add(() -> BPConfiguredFeatures.OCHAIM_PURPLE);
                    if (BPConfig.WORLDGEN.chorusVegetationHighlands.get()) vegDeco.add(() -> BPConfiguredFeatures.OCHAIM_RED);
                    if (BPConfig.WORLDGEN.chorusVegetationHighlands.get()) vegDeco.add(() -> BPConfiguredFeatures.OCHAIM_GREEN);

                    if (BPConfig.WORLDGEN.endSpikeHighlands.get()) localDeco.add(() -> BPConfiguredFeatures.END_LAND_SPIKE_PATCH_HL);
                    if (BPConfig.WORLDGEN.endSpongeHighlands.get()) localDeco.add(() -> BPConfiguredFeatures.END_LAND_SPONGE_PATCH_HL);
                    //if (BPConfig.WORLDGEN.endSpongeHighlands.get()) localDeco.add(() -> BPConfiguredFeatures.END_LANDS_CAVERN);
                }

                if (WorldgenUtils.getBiomeFromEvent(event, WorldgenUtils.END_MIDLANDS) || WorldgenUtils.getBiomeFromEvent(event, WorldgenUtils.END_BARRENS)) {

                    //if (BPConfig.WORLDGEN.endSpongeMidlands.get()) liqCarver.add(() -> BPConfiguredWorldCarvers.END_SPRINGS_CARVER);

                    if (BPConfig.WORLDGEN.chorusVegetationHighlands.get()) topLayerDeco.add(() -> BPConfiguredFeatures.ENREDE_KELP);

                    if (BPConfig.WORLDGEN.chorusLanternMidlands.get()) vegDeco.add(() -> BPConfiguredFeatures.CHORUS_LANTERN_MIDLANDS_PATCH);
                    if (BPConfig.WORLDGEN.chorusVegetationMidlands.get()) vegDeco.add(() -> BPConfiguredFeatures.CHORUS_IDON);
                    if (BPConfig.WORLDGEN.chorusVegetationMidlands.get()) vegDeco.add(() -> BPConfiguredFeatures.CHORUS_IDE_FAN);
                    if (BPConfig.WORLDGEN.chorusVegetationMidlands.get()) vegDeco.add(() -> BPConfiguredFeatures.ENREDE_KELP);
                    if (BPConfig.WORLDGEN.chorusVegetationMidlands.get()) vegDeco.add(() -> BPConfiguredFeatures.ENREDE_CORSASCILE);
                    if (BPConfig.WORLDGEN.chorusVegetationMidlands.get()) vegDeco.add(() -> BPConfiguredFeatures.OCHAIM_PURPLE);
                    if (BPConfig.WORLDGEN.chorusVegetationMidlands.get()) vegDeco.add(() -> BPConfiguredFeatures.OCHAIM_RED);
                    if (BPConfig.WORLDGEN.chorusVegetationMidlands.get()) vegDeco.add(() -> BPConfiguredFeatures.OCHAIM_GREEN);

                    if (BPConfig.WORLDGEN.endSpikeMidlands.get()) localDeco.add(() -> BPConfiguredFeatures.END_LAND_SPIKE_PATCH_ML);
                    if (BPConfig.WORLDGEN.endSpongeMidlands.get()) localDeco.add(() -> BPConfiguredFeatures.END_LAND_SPONGE_PATCH_ML);

                }
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
