package io.github.bioplethora.registry.worldgen;

import com.minecraftabnormals.abnormals_core.core.util.BiomeUtil;
import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.world.biomes.RockyWoodlandBiome;
import io.github.bioplethora.world.biomes.end.CaeriForestBiome;
import io.github.bioplethora.world.biomes.end.CaeriPlainsBiome;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Objects;

public class BPBiomes {

    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, Bioplethora.MOD_ID);

    // Overworld
    public static final RegistryObject<Biome> ROCKY_WOODLANDS = BIOMES.register("rocky_woodlands",
            () -> RockyWoodlandBiome.make(() -> BPConfiguredSurfaceBuilders.ROCKY_WOODLANDS_SURFACE, 0.15f, 1.4f)
    );

    // End
    public static final RegistryObject<Biome> CAERI_PLAINS = BIOMES.register("caeri_plains",
            () -> CaeriPlainsBiome.make(() -> BPConfiguredSurfaceBuilders.CAERI_SURFACE)
    );
    public static final RegistryObject<Biome> CAERI_FOREST = BIOMES.register("caeri_forest",
            () -> CaeriForestBiome.make(() -> BPConfiguredSurfaceBuilders.CAERI_SURFACE)
    );

    public static final class Type {
        public static final BiomeDictionary.Type CAERI = BiomeDictionary.Type.getType("CAERI");
        public static final BiomeDictionary.Type CAERI_PLAINS = BiomeDictionary.Type.getType("CAERI_PLAINS");
        public static final BiomeDictionary.Type CAERI_FOREST = BiomeDictionary.Type.getType("CAERI_FOREST");
    }

    //==============================================
    //                OTHERS
    //==============================================
    private static final HashMap<ResourceLocation, Biome> BP_BIOMES = new HashMap<>();

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = Bioplethora.MOD_ID)
    public static class RegistryEvents {

        @SubscribeEvent
        public static void addBiomeDictionary(FMLCommonSetupEvent event) {

            BiomeDictionary.addTypes(getKey(CAERI_PLAINS.get()),
                    Type.CAERI, BiomeDictionary.Type.END,
                    Type.CAERI_PLAINS, BiomeDictionary.Type.PLAINS,
                    BiomeDictionary.Type.DENSE, BiomeDictionary.Type.WET,
                    BiomeDictionary.Type.MAGICAL
            );
            BiomeDictionary.addTypes(getKey(CAERI_FOREST.get()),
                    Type.CAERI, BiomeDictionary.Type.END,
                    Type.CAERI_FOREST, BiomeDictionary.Type.FOREST,
                    BiomeDictionary.Type.DENSE, BiomeDictionary.Type.WET,
                    BiomeDictionary.Type.MAGICAL
            );
        }

        @SubscribeEvent
        public static void addEndBiomes(RegistryEvent.Register<Biome> event) {
            register(CAERI_PLAINS.get(), CAERI_PLAINS.getId(), 6, 6, event);
            register(CAERI_FOREST.get(), CAERI_FOREST.getId(), 8, 8, event);
        }
    }

    public static RegistryKey<Biome> getKey(Biome biome) {
        ResourceLocation keyRL = ForgeRegistries.BIOMES.getKey(biome);
        assert keyRL != null;
        return RegistryKey.create(ForgeRegistries.Keys.BIOMES, keyRL);
    }

    public static Biome[] getBiomes() {
        return BP_BIOMES.values().toArray(new Biome[0]);
    }

    private static void register(Biome biome, ResourceLocation registryName, float weight, float weightRange, RegistryEvent.Register<Biome> event) {
        BiomeUtil.addEndBiome(RegistryKey.create(Registry.BIOME_REGISTRY, Objects.requireNonNull(biome.getRegistryName())), MathHelper.floor(weight));
        BP_BIOMES.put(registryName, ForgeRegistries.BIOMES.getValue(registryName));
    }
}
