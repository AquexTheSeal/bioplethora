package io.github.bioplethora.world;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.config.BPConfig;
import io.github.bioplethora.registry.worldgen.BPBiomes;
import io.github.bioplethora.registry.worldgen.BPConfiguredFeatures;
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

        List<Supplier<ConfiguredFeature<?, ?>>> topLayerDeco = event.getGeneration().getFeatures(GenerationStage.Decoration.TOP_LAYER_MODIFICATION);
        List<Supplier<ConfiguredFeature<?, ?>>> undergroundDeco = event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_DECORATION);
        List<Supplier<ConfiguredFeature<?, ?>>> vegDeco = event.getGeneration().getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION);

        List<Supplier<ConfiguredCarver<?>>> test = event.getGeneration().getCarvers(GenerationStage.Carving.AIR);

        if (BiomeDictionary.hasType(key, BPBiomes.Type.CRYEANUM)) {
            vegDeco.add(() -> BPConfiguredFeatures.KYRIA);
            vegDeco.add(() -> BPConfiguredFeatures.KYRIA_BELINE);
            vegDeco.add(() -> BPConfiguredFeatures.KYRIA_IDE_FAN);
            vegDeco.add(() -> BPConfiguredFeatures.RED_TWI);
            vegDeco.add(() -> BPConfiguredFeatures.PINK_TWI);
            vegDeco.add(() -> BPTreeConfiguredFeatures.CRYEANUM_FOREST_TREES);
        }
        if (BiomeDictionary.hasType(key, BPBiomes.Type.CAERI_PLAINS)) {
            vegDeco.add(() -> BPTreeConfiguredFeatures.CAERI_PLAINS_TREES);
            vegDeco.add(() -> BPConfiguredFeatures.IRION_GRASS);
            vegDeco.add(() -> BPConfiguredFeatures.IRION_TALL_GRASS);
            vegDeco.add(() -> BPConfiguredFeatures.ARTAIRIUS);
            vegDeco.add(() -> BPConfiguredFeatures.BYRSS_LANTERN_PLANT_PATCH);
        }
        if (BiomeDictionary.hasType(key, BPBiomes.Type.CAERI_FOREST)) {
            vegDeco.add(() -> BPTreeConfiguredFeatures.CAERI_FOREST_TREES);
            vegDeco.add(() -> BPConfiguredFeatures.IRION_GRASS);
            vegDeco.add(() -> BPConfiguredFeatures.IRION_TALL_GRASS);
            vegDeco.add(() -> BPConfiguredFeatures.ARTAIRIUS);
            vegDeco.add(() -> BPConfiguredFeatures.BYRSS_LANTERN_FOREST_PATCH);
        }
        if (BiomeDictionary.hasType(key, BPBiomes.Type.LAVENDER_LAKE)) {
            if (BPConfig.WORLDGEN.chorusLanternMidlands.get()) vegDeco.add(() -> BPConfiguredFeatures.CHORUS_LANTERN_MIDLANDS_PATCH);
            if (BPConfig.WORLDGEN.endSpongeMidlands.get()) vegDeco.add(() -> BPConfiguredFeatures.END_LAND_SPONGE_PATCH_ML);
            if (BPConfig.WORLDGEN.endSpikeMidlands.get()) vegDeco.add(() -> BPConfiguredFeatures.END_LAND_SPIKE_PATCH_ML);
            if (BPConfig.WORLDGEN.chorusVegetationMidlands.get()) vegDeco.add(() -> BPConfiguredFeatures.CHORUS_IDON);
            if (BPConfig.WORLDGEN.chorusVegetationMidlands.get()) vegDeco.add(() -> BPConfiguredFeatures.CHORUS_IDE_FAN);
        }
    }
}
