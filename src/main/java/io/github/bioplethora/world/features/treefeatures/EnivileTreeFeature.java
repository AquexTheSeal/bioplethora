package io.github.bioplethora.world.features.treefeatures;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import io.github.bioplethora.world.features.NBTTreeFeature;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class EnivileTreeFeature extends NBTTreeFeature {

    public EnivileTreeFeature(Codec<NoFeatureConfig> config) {
        super(config);
    }

    @Override
    public ImmutableList<String> getPossibleNBTTrees() {
        return ImmutableList.of(
                "enivile_tree_small_1",
                "enivile_tree_small_2",
                "enivile_tree_small_3",
                "enivile_tree_medium_1",
                "enivile_tree_medium_2",
                "enivile_tree_large_1",
                "enivile_tree_large_2"
        );
    }

    @Override
    public boolean lowerYLevel(Random rand) {
        return false;
    }

    @Override
    public boolean getSpawningCondition(ISeedReader world, Random random, BlockPos pos) {
        return defaultTreeCanPlace(world, random, pos);
    }
}
