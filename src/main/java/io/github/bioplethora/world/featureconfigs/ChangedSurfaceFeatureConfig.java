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

public class ChangedSurfaceFeatureConfig implements IFeatureConfig {

    public static final Codec<ChangedSurfaceFeatureConfig> CODEC = RecordCodecBuilder.create((codecRecorder) -> codecRecorder.group(
            BlockState.CODEC.fieldOf("common").forGetter((config) -> config.common),
            BlockState.CODEC.fieldOf("uncommon").forGetter((config) -> config.uncommon),
            BlockState.CODEC.listOf().fieldOf("whitelist").forGetter((config) -> config.whitelist.stream().map(Block::defaultBlockState).collect(Collectors.toList()))
    ).apply(codecRecorder, ChangedSurfaceFeatureConfig::new));

    private final BlockState common;
    private final BlockState uncommon;
    private final Set<Block> whitelist;

    ChangedSurfaceFeatureConfig(BlockState common, BlockState uncommon, List<BlockState> whitelist) {
        this.common = common;
        this.uncommon = uncommon;
        this.whitelist = whitelist.stream().map(AbstractBlock.AbstractBlockState::getBlock).collect(Collectors.toSet());
    }

    public BlockState getCommon() {
        return common;
    }

    public BlockState getUncommon() {
        return uncommon;
    }

    public Set<Block> getWhitelist() {
        return whitelist;
    }

    public static class Builder {
        private BlockState common;
        private BlockState uncommon;
        private List<BlockState> whitelist = ImmutableList.of(Blocks.GRASS_BLOCK.defaultBlockState());

        public ChangedSurfaceFeatureConfig.Builder setCommon(BlockState common) {
            this.common = common;
            return this;
        }

        public ChangedSurfaceFeatureConfig.Builder setUncommon(BlockState uncommon) {
            this.uncommon = uncommon;
            return this;
        }

        public ChangedSurfaceFeatureConfig.Builder setWhitelist(ImmutableList<BlockState> whitelist) {
            this.whitelist = whitelist;
            return this;
        }

        public ChangedSurfaceFeatureConfig build() {
            return new ChangedSurfaceFeatureConfig(common, uncommon, whitelist);
        }
    }
}
