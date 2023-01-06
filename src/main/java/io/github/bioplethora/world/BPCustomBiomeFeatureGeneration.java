package io.github.bioplethora.world;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.config.BPConfig;
import io.github.bioplethora.registry.worldgen.BPBiomes;
import io.github.bioplethora.registry.worldgen.BPConfiguredFeatures;
import io.github.bioplethora.registry.worldgen.BPConfiguredWorldCarvers;
import io.github.bioplethora.registry.worldgen.BPTreeConfiguredFeatures;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = Bioplethora.MOD_ID)
public class BPCustomBiomeFeatureGeneration {

    @SubscribeEvent
    public static void generateFeatures(final BiomeLoadingEvent event) {
        RegistryKey<Biome> key = RegistryKey.create(Registry.BIOME_REGISTRY, event.getName());
        Set<BiomeDictionary.Type> types = BiomeDictionary.getTypes(key);
        List<Supplier<ConfiguredCarver<?>>> airCarver = event.getGeneration().getCarvers(GenerationStage.Carving.AIR);
        List<Supplier<ConfiguredCarver<?>>> liqCarver = event.getGeneration().getCarvers(GenerationStage.Carving.LIQUID);

        List<Supplier<ConfiguredFeature<?, ?>>> localDeco = event.getGeneration().getFeatures(GenerationStage.Decoration.LOCAL_MODIFICATIONS);
        List<Supplier<ConfiguredFeature<?, ?>>> topLayerDeco = event.getGeneration().getFeatures(GenerationStage.Decoration.TOP_LAYER_MODIFICATION);
        List<Supplier<ConfiguredFeature<?, ?>>> undergroundDeco = event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_DECORATION);
        List<Supplier<ConfiguredFeature<?, ?>>> vegDeco = event.getGeneration().getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION);

        if (BiomeDictionary.hasType(key, BPBiomes.Type.CRYEANUM)) {
            vegDeco.add(() -> BPConfiguredFeatures.KYRIA);
            vegDeco.add(() -> BPConfiguredFeatures.KYRIA_BELINE);
            vegDeco.add(() -> BPConfiguredFeatures.KYRIA_IDE_FAN);
            vegDeco.add(() -> BPConfiguredFeatures.RED_TWI);
            vegDeco.add(() -> BPConfiguredFeatures.PINK_TWI);
            vegDeco.add(() -> BPTreeConfiguredFeatures.CRYEANUM_FOREST_TREES);
        }
        if (BiomeDictionary.hasType(key, BPBiomes.Type.CAERI_PLAINS)) {
            undergroundDeco.add(() -> BPConfiguredFeatures.CAERI_CAVERN);

            //vegDeco.add(() -> BPConfiguredFeatures.CELESTIA_BUD);

            vegDeco.add(() -> BPConfiguredFeatures.IRION_GRASS);
            vegDeco.add(() -> BPConfiguredFeatures.IRION_TALL_GRASS);
            vegDeco.add(() -> BPConfiguredFeatures.ARTAIRIUS);
            vegDeco.add(() -> BPConfiguredFeatures.BYRSS_LANTERN_PLANT_PATCH);
        }
        if (BiomeDictionary.hasType(key, BPBiomes.Type.CAERI_FOREST)) {
            airCarver.add(() -> BPConfiguredWorldCarvers.CAERI_FORMERS);
            undergroundDeco.add(() -> BPConfiguredFeatures.CAERI_CAVERN);

            //vegDeco.add(() -> BPConfiguredFeatures.CELESTIA_BUD);

            vegDeco.add(() -> BPTreeConfiguredFeatures.CAERI_FOREST_TREES);
            vegDeco.add(() -> BPConfiguredFeatures.IRION_GRASS);
            vegDeco.add(() -> BPConfiguredFeatures.IRION_TALL_GRASS);
            vegDeco.add(() -> BPConfiguredFeatures.ARTAIRIUS);
            vegDeco.add(() -> BPConfiguredFeatures.BYRSS_LANTERN_FOREST_PATCH);
        }
        if (BiomeDictionary.hasType(key, BPBiomes.Type.WINTERFEST)) {
            if (BPConfig.WORLDGEN.endIcicleIslands.get()) vegDeco.add(() -> BPConfiguredFeatures.END_ISLANDS_ICICLE_PATCH);
            if (BPConfig.WORLDGEN.endFrozenIslands.get()) vegDeco.add(() -> BPConfiguredFeatures.END_FROZEN_ISLAND_DECORATED);
            vegDeco.add(() -> BPConfiguredFeatures.FROSTEM);
            vegDeco.add(() -> BPConfiguredFeatures.SPINXELTHORN);
            vegDeco.add(() -> BPConfiguredFeatures.GLACYNTH);
        }
        if (BiomeDictionary.hasType(key, BPBiomes.Type.LAVENDER_LAKE)) {
            //if (BPConfig.WORLDGEN.endSpongeHighlands.get()) liqCarver.add(() -> BPConfiguredWorldCarvers.END_SPRINGS_CARVER);
            //if (BPConfig.WORLDGEN.endSpongeHighlands.get()) undergroundDeco.add(() -> BPConfiguredFeatures.END_LAND_ROCK);

            if (BPConfig.WORLDGEN.chorusLanternHighlands.get()) vegDeco.add(() -> BPConfiguredFeatures.CHORUS_LANTERN_HIGHLANDS_PATCH);
            if (BPConfig.WORLDGEN.endSpikeHighlands.get()) vegDeco.add(() -> BPConfiguredFeatures.END_LAND_SPIKE_PATCH_HL);
            if (BPConfig.WORLDGEN.chorusVegetationHighlands.get()) vegDeco.add(() -> BPConfiguredFeatures.CHORUS_IDON);
            if (BPConfig.WORLDGEN.chorusVegetationHighlands.get()) vegDeco.add(() -> BPConfiguredFeatures.CHORUS_IDE_FAN);
            if (BPConfig.WORLDGEN.chorusVegetationHighlands.get()) vegDeco.add(() -> BPConfiguredFeatures.ENREDE_KELP);
            if (BPConfig.WORLDGEN.chorusVegetationHighlands.get()) vegDeco.add(() -> BPConfiguredFeatures.ENREDE_CORSASCILE);
            if (BPConfig.WORLDGEN.chorusVegetationHighlands.get()) vegDeco.add(() -> BPConfiguredFeatures.OCHAIM_PURPLE);
            if (BPConfig.WORLDGEN.chorusVegetationHighlands.get()) vegDeco.add(() -> BPConfiguredFeatures.OCHAIM_RED);
            if (BPConfig.WORLDGEN.chorusVegetationHighlands.get()) vegDeco.add(() -> BPConfiguredFeatures.OCHAIM_GREEN);

            if (BPConfig.WORLDGEN.endSpongeHighlands.get()) localDeco.add(() -> BPConfiguredFeatures.END_LAND_SPONGE_PATCH_HL);
            //if (BPConfig.WORLDGEN.endSpongeHighlands.get()) localDeco.add(() -> BPConfiguredFeatures.END_LANDS_CAVERN);
        }
        if (BiomeDictionary.hasType(key, BPBiomes.Type.LAVENDER_POND)) {
            //if (BPConfig.WORLDGEN.endSpongeMidlands.get()) liqCarver.add(() -> BPConfiguredWorldCarvers.END_SPRINGS_CARVER);

            if (BPConfig.WORLDGEN.chorusLanternMidlands.get()) vegDeco.add(() -> BPConfiguredFeatures.CHORUS_LANTERN_MIDLANDS_PATCH);
            if (BPConfig.WORLDGEN.endSpikeMidlands.get()) vegDeco.add(() -> BPConfiguredFeatures.END_LAND_SPIKE_PATCH_ML);
            if (BPConfig.WORLDGEN.chorusVegetationMidlands.get()) vegDeco.add(() -> BPConfiguredFeatures.CHORUS_IDON);
            if (BPConfig.WORLDGEN.chorusVegetationMidlands.get()) vegDeco.add(() -> BPConfiguredFeatures.CHORUS_IDE_FAN);
            if (BPConfig.WORLDGEN.chorusVegetationMidlands.get()) vegDeco.add(() -> BPConfiguredFeatures.ENREDE_KELP);
            if (BPConfig.WORLDGEN.chorusVegetationMidlands.get()) vegDeco.add(() -> BPConfiguredFeatures.ENREDE_CORSASCILE);
            if (BPConfig.WORLDGEN.chorusVegetationMidlands.get()) vegDeco.add(() -> BPConfiguredFeatures.OCHAIM_PURPLE);
            if (BPConfig.WORLDGEN.chorusVegetationMidlands.get()) vegDeco.add(() -> BPConfiguredFeatures.OCHAIM_RED);
            if (BPConfig.WORLDGEN.chorusVegetationMidlands.get()) vegDeco.add(() -> BPConfiguredFeatures.OCHAIM_GREEN);

            if (BPConfig.WORLDGEN.endSpongeMidlands.get()) localDeco.add(() -> BPConfiguredFeatures.END_LAND_SPONGE_PATCH_ML);
        }
    }
}
