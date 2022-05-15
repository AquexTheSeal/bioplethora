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

package io.github.bioplethora.world.biomes.provider.helper;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.INoiseRandom;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class BPNetherProviderGatherer {
    public static List<RegistryKey<Biome>> netherWhitelist = new ArrayList<>();

    public static List<RegistryKey<Biome>> getNetherBiomeGather(Registry<Biome> biomeRegistry) {

        for (Map.Entry<RegistryKey<Biome>, Biome> entry : biomeRegistry.entrySet()) {
            if (entry.getValue().getBiomeCategory() == Biome.Category.NETHER && !entry.getKey().location().getNamespace().equals("ultra_amplified_dimension")) {
                if (!netherWhitelist.contains(entry.getKey())) {
                    netherWhitelist.add(entry.getKey());
                }
            }
        }

        netherWhitelist.sort(Comparator.comparing(key -> key.location().toString()));
        return netherWhitelist;
    }

    public static int randomNetherBiome(INoiseRandom random, Registry<Biome> registry) {
        return registry.getId(registry.get(netherWhitelist.get(random.nextRandom(netherWhitelist.size()))));
    }
}
