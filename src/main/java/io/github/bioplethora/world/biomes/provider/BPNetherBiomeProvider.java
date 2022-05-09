/*
 * Copyright 2022 Infernal Studios
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.bioplethora.world.biomes.provider;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.world.biomes.provider.helper.BPNetherLayer;
import io.github.bioplethora.world.biomes.provider.helper.BPNetherProviderGatherer;
import io.github.bioplethora.world.biomes.provider.helper.BPSeedHolder;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeRegistry;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.LazyAreaLayerContext;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.area.IAreaFactory;
import net.minecraft.world.gen.area.LazyArea;
import net.minecraft.world.gen.layer.Layer;
import net.minecraft.world.gen.layer.ZoomLayer;

import javax.annotation.Nonnull;
import java.util.function.LongFunction;

public class BPNetherBiomeProvider extends BiomeProvider {
    
    public static void registerBiomeProvider() {
        Registry.register(Registry.BIOME_SOURCE, new ResourceLocation(Bioplethora.MOD_ID, "bp_nether_biome_provider"), BP_NETHER_CODEC);
    }

    public static final Codec<BPNetherBiomeProvider> BP_NETHER_CODEC =
            RecordCodecBuilder.create((instance) -> instance.group(
                            Codec.LONG.fieldOf("seed").orElseGet(BPSeedHolder::getSeed).forGetter((biomeSource) -> biomeSource.seed),
                            RegistryLookupCodec.create(Registry.BIOME_REGISTRY).forGetter((biomeProvider) -> biomeProvider.biomeRegistry),
                            Codec.intRange(1, 20).fieldOf("biome_size").orElse(1).forGetter((biomeSource) -> biomeSource.biomeSize))
                    .apply(instance, BPNetherBiomeProvider::new)
            );

    public final long seed;
    private final int biomeSize;
    private final Layer biomeLayer;
    protected final Registry<Biome> biomeRegistry;

    public BPNetherBiomeProvider(long seed, Registry<Biome> biomeRegistry, int size) {
        super(BPNetherProviderGatherer.getNetherBiomeGather(biomeRegistry).stream().map((registryKey) -> () -> (Biome) biomeRegistry.get(registryKey)));

        this.seed = seed;
        this.biomeLayer = buildWorldProcedure(seed, size, biomeRegistry);
        this.biomeRegistry = biomeRegistry;
        this.biomeSize = size;
    }

    public static Layer buildWorldProcedure(long seed, int biomeSize, Registry<Biome> biomeRegistry) {
        IAreaFactory<LazyArea> layerFactory = build((salt) -> new LazyAreaLayerContext(25, seed, salt),
                biomeSize,
                seed,
                biomeRegistry);
        return new Layer(layerFactory);
    }

    public static <T extends IArea, C extends IExtendedNoiseRandom<T>> IAreaFactory<T> build(LongFunction<C> contextFactory, int biomeSize, long seed, Registry<Biome> biomeRegistry) {
        IAreaFactory<T> layerFactory = (new BPNetherLayer(seed, biomeRegistry)).run(contextFactory.apply(200L));

        for (int currentExtraZoom = 0; currentExtraZoom < biomeSize; currentExtraZoom++) {
            if ((currentExtraZoom + 2) % 3 != 0) {
                layerFactory = ZoomLayer.NORMAL.run(contextFactory.apply(2001L + currentExtraZoom), layerFactory);
            } else {
                layerFactory = ZoomLayer.FUZZY.run(contextFactory.apply(2000L + (currentExtraZoom * 31L)), layerFactory);
            }
        }

        return layerFactory;
    }

    @Override
    public Biome getNoiseBiome(int x, int y, int z) {
        int biomeID = this.biomeLayer.area.get(x, z);
        Biome biome = this.biomeRegistry.byId(biomeID);
        if (biome == null) {
            if (SharedConstants.IS_RUNNING_IN_IDE) {
                throw Util.pauseInIde(new IllegalStateException("Unknown biome id: " + biomeID));
            } else {
                return this.biomeRegistry.get(BiomeRegistry.byId(0));
            }
        } else {
            return biome;
        }
    }

    @Nonnull
    @Override
    protected Codec<? extends BiomeProvider> codec() {
        return BP_NETHER_CODEC;
    }

    @Nonnull
    @Override
    public BiomeProvider withSeed(long seed) {
        return new BPNetherBiomeProvider(seed, this.biomeRegistry, this.biomeSize);
    }
}
