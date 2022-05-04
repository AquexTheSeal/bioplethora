package io.github.bioplethora.registry.worldgen;

import io.github.bioplethora.api.world.WorldgenUtils;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import java.util.Random;

public class BPNBTTrees {

    public static class CaerulwoodNBTTree extends WorldgenUtils.NBTTree {

        @Override
        protected ConfiguredFeature<?, ?> getTree(Random random) {
            return BPTreeConfiguredFeatures.CAERULWOOD_TREE_CONFIG;
        }
    }
}
