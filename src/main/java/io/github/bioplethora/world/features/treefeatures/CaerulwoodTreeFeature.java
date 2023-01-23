package io.github.bioplethora.world.features.treefeatures;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import io.github.bioplethora.world.features.NBTTreeFeature;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class CaerulwoodTreeFeature extends NBTTreeFeature {

    public CaerulwoodTreeFeature(Codec<NoFeatureConfig> config) {
        super(config);
    }

    @Override
    public ImmutableList<String> getPossibleNBTTrees() {
        return ImmutableList.of(
                "caerulwood_tree_small_1",
                "caerulwood_tree_small_2",
                "caerulwood_tree_small_3",
                "caerulwood_tree_medium_1",
                "caerulwood_tree_medium_2",
                "caerulwood_tree_large_1",
                "caerulwood_tree_large_2",
                "caerulwood_tree_large_3",
                "caerulwood_tree_large_4"
        );
    }

    @Override
    public boolean lowerYLevel(Random rand) {
        return getRandomNBTTree(rand).equals("caerulwood_tree_large_1") || getRandomNBTTree(rand).equals("caerulwood_tree_large_2") || getRandomNBTTree(rand).equals("caerulwood_tree_large_3") || getRandomNBTTree(rand).equals("caerulwood_tree_large_4");
    }

    @Override
    public boolean getSpawningCondition(ISeedReader world, Random random, BlockPos pos) {
        return defaultTreeCanPlace(world, random, pos);
    }
}
