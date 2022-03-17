package io.github.bioplethora.world.feature_config;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.IFeatureConfig;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PendentBlocksFeatureConfig implements IFeatureConfig {

    public static final Codec<PendentBlocksFeatureConfig> CODEC = RecordCodecBuilder.create((codecRecorder) -> {
        return codecRecorder.group(BlockStateProvider.CODEC.fieldOf("base_block_provider").forGetter((config) -> {
            return config.topBlockProvider;
        }), BlockStateProvider.CODEC.fieldOf("block_provider").forGetter((config) -> {
            return config.middleBlockProvider;
        }), BlockStateProvider.CODEC.fieldOf("end_block_provider").forGetter((config) -> {
            return config.endBlockProvider;
        }), Codec.INT.fieldOf("min_length").forGetter((config) -> {
            return config.minimalYSize;
        }), Codec.INT.fieldOf("max_length").forGetter((config) -> {
            return config.maximalYSize;
        }), BlockState.CODEC.listOf().fieldOf("whitelist").forGetter((config) -> {
            return config.whitelist.stream().map(Block::defaultBlockState).collect(Collectors.toList());
        })).apply(codecRecorder, PendentBlocksFeatureConfig::new);
    });

    private final BlockStateProvider topBlockProvider;
    private final BlockStateProvider middleBlockProvider;
    private final BlockStateProvider endBlockProvider;
    private final int minimalYSize;
    private final int maximalYSize;
    private final Set<Block> whitelist;

    PendentBlocksFeatureConfig(BlockStateProvider baseBlockProvider, BlockStateProvider blockProvider, BlockStateProvider endBlockProvider, int minLength, int maxLength, List<BlockState> whitelist) {
        this.topBlockProvider = baseBlockProvider;
        this.middleBlockProvider = blockProvider;
        this.endBlockProvider = endBlockProvider;
        this.minimalYSize = minLength;
        this.maximalYSize = maxLength;
        this.whitelist = whitelist.stream().map(AbstractBlock.AbstractBlockState::getBlock).collect(Collectors.toSet());

    }

    public BlockStateProvider getTopBlockProvider() {
        return topBlockProvider;
    }

    public BlockStateProvider getMiddleBlockProvider() {
        return middleBlockProvider;
    }

    public BlockStateProvider getEndBlockProvider() {
        return endBlockProvider;
    }

    public int getMaximalYSize() {
        return maximalYSize;
    }

    public int getMinimalYSize() {
        return minimalYSize;
    }

    public int getMaxPossibleLength() {
        int returnValue = this.minimalYSize - maximalYSize;
        if (returnValue <= 0)
            returnValue = 1;

        return returnValue;
    }

    public Set<Block> getWhitelist() {
        return whitelist;
    }


    public static class Builder {
        private BlockStateProvider topBlockProvider = new SimpleBlockStateProvider(Blocks.OAK_LOG.defaultBlockState());
        private BlockStateProvider middleBlockProvider = new SimpleBlockStateProvider(Blocks.OAK_LEAVES.defaultBlockState());
        private BlockStateProvider endBlockProvider = new SimpleBlockStateProvider(Blocks.AIR.defaultBlockState());
        private List<Block> whitelist = ImmutableList.of(Blocks.GRASS_BLOCK);
        private int minLength = 1;
        private int maxLength = 10;

        public PendentBlocksFeatureConfig.Builder setTopBlock(Block block) {
            if (block != null) {
                topBlockProvider = new SimpleBlockStateProvider(block.defaultBlockState());
            } else {
                topBlockProvider = new SimpleBlockStateProvider(Blocks.STONE.defaultBlockState());
            }
            return this;
        }

        public PendentBlocksFeatureConfig.Builder setTopBlock(BlockState state) {
            if (state != null) {
                topBlockProvider = new SimpleBlockStateProvider(state);
            } else {
                topBlockProvider = new SimpleBlockStateProvider(Blocks.STONE.defaultBlockState());
            }
            return this;
        }

        public PendentBlocksFeatureConfig.Builder setTopBlock(BlockStateProvider provider) {
            if (provider != null) {
                topBlockProvider = provider;
            } else {
                topBlockProvider = new SimpleBlockStateProvider(Blocks.STONE.defaultBlockState());
            }
            return this;
        }

        public PendentBlocksFeatureConfig.Builder setMiddleBlock(Block block) {
            if (block != null) {
                middleBlockProvider = new SimpleBlockStateProvider(block.defaultBlockState());
            } else {
                middleBlockProvider = new SimpleBlockStateProvider(Blocks.STONE.defaultBlockState());
            }
            return this;
        }

        public PendentBlocksFeatureConfig.Builder setMiddleBlock(BlockState state) {
            if (state != null) {
                middleBlockProvider = new SimpleBlockStateProvider(state);
            } else {
                middleBlockProvider = new SimpleBlockStateProvider(Blocks.STONE.defaultBlockState());
            }
            return this;
        }

        public PendentBlocksFeatureConfig.Builder setMiddleBlock(BlockStateProvider provider) {
            if (provider != null) {
                middleBlockProvider = provider;
            } else {
                middleBlockProvider = new SimpleBlockStateProvider(Blocks.STONE.defaultBlockState());
            }
            return this;
        }

        public PendentBlocksFeatureConfig.Builder setEndBlock(Block block) {
            if (block != null) {
                endBlockProvider = new SimpleBlockStateProvider(block.defaultBlockState());
            } else {
                endBlockProvider = middleBlockProvider;
            }
            return this;
        }

        public PendentBlocksFeatureConfig.Builder setEndBlock(BlockState state) {
            if (state != null) {
                endBlockProvider = new SimpleBlockStateProvider(state);
            } else {
                endBlockProvider = middleBlockProvider;
            }
            return this;
        }

        public PendentBlocksFeatureConfig.Builder setEndBlock(BlockStateProvider provider) {
            if (provider != null) {
                endBlockProvider = provider;
            } else {
                endBlockProvider = middleBlockProvider;
            }
            return this;
        }

        public PendentBlocksFeatureConfig.Builder setMinLength(int minLength) {
            this.minLength = minLength;
            return this;
        }

        public PendentBlocksFeatureConfig.Builder setMaxLength(int maxPossibleHeight) {
            if (maxPossibleHeight != 0)
                this.maxLength = maxPossibleHeight + 1;
            else
                this.maxLength = 1;
            return this;
        }

        public PendentBlocksFeatureConfig.Builder setWhitelist(ImmutableList<Block> whitelist) {
            this.whitelist = whitelist;
            return this;
        }

        public PendentBlocksFeatureConfig.Builder copy(PendentBlocksFeatureConfig config) {
            this.topBlockProvider = config.topBlockProvider;
            this.middleBlockProvider = config.middleBlockProvider;
            this.endBlockProvider = config.endBlockProvider;
            this.minLength = config.minimalYSize;
            this.maxLength = config.maximalYSize;
            return this;
        }

        public PendentBlocksFeatureConfig build() {
            return new PendentBlocksFeatureConfig(topBlockProvider, middleBlockProvider, endBlockProvider, minLength, maxLength, this.whitelist.stream().map(Block::defaultBlockState).collect(Collectors.toList()));
        }
    }
}
