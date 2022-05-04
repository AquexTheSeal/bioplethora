package io.github.bioplethora.world.featureconfigs;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.feature.IFeatureConfig;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class NBTFeatureConfig implements IFeatureConfig {

    public static final Codec<NBTFeatureConfig> CODEC = RecordCodecBuilder.create((codecRecorder) -> codecRecorder.group(
            Codec.STRING.fieldOf("feature").forGetter((config) -> config.feature),
            Codec.INT.fieldOf("yOffset").forGetter((config) -> config.yOffset),
            BlockState.CODEC.listOf().fieldOf("whitelist").forGetter((config) -> config.whitelist.stream().map(Block::defaultBlockState).collect(Collectors.toList()))
            ).apply(codecRecorder, NBTFeatureConfig::new));

    private final String feature;
    private final int yOffset;
    private final Set<Block> whitelist;

    NBTFeatureConfig(String feature, int yOffset, List<BlockState> whitelist) {
        this.feature = feature;
        this.yOffset = yOffset;
        this.whitelist = whitelist.stream().map(AbstractBlock.AbstractBlockState::getBlock).collect(Collectors.toSet());
    }

    public String getFeature() {
        return feature;
    }

    public int getYOffset() {
        return yOffset;
    }

    public Set<Block> getWhitelist() {
        return whitelist;
    }

    public static class Builder {
        private String feature = null;
        private int yOffset = 0;
        private List<BlockState> whitelist = ImmutableList.of(Blocks.GRASS_BLOCK.defaultBlockState());

        public NBTFeatureConfig.Builder setFeature(String feature) {
            this.feature = feature;
            return this;
        }

        public NBTFeatureConfig.Builder setYOffset(int yOffset) {
            this.yOffset = yOffset;
            return this;
        }

        public NBTFeatureConfig.Builder setWhitelist(ImmutableList<BlockState> whitelist) {
            this.whitelist = whitelist;
            return this;
        }

        public NBTFeatureConfig build() {
            return new NBTFeatureConfig(feature, yOffset, whitelist);
        }
    }
}
