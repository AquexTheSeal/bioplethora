package io.github.bioplethora.world.featureconfigs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class ExpandedLakeFeatureConfig implements IFeatureConfig {

    public static final Codec<ExpandedLakeFeatureConfig> CODEC = RecordCodecBuilder.create((codecRecorder) -> codecRecorder.group(
            BlockState.CODEC.fieldOf("liquid").forGetter((config) -> config.liquid),
            BlockState.CODEC.fieldOf("sides").forGetter((config) -> config.sides),
            BlockState.CODEC.fieldOf("base").forGetter((config) -> config.base)
            ).apply(codecRecorder, ExpandedLakeFeatureConfig::new));

    private final BlockState liquid;
    private final BlockState sides;
    private final BlockState base;

    ExpandedLakeFeatureConfig(BlockState liquid, BlockState sides, BlockState base) {
        this.liquid = liquid;
        this.sides = sides;
        this.base = base;
    }

    public BlockState getLiquid() {
        return liquid;
    }

    public BlockState getSides() {
        return sides;
    }

    public BlockState getBase() {
        return base;
    }

    public static class Builder {
        private BlockState liquid;
        private BlockState sides;
        private BlockState base;

        public ExpandedLakeFeatureConfig.Builder setLiquid(BlockState liquid) {
            this.liquid = liquid;
            return this;
        }

        public ExpandedLakeFeatureConfig.Builder setSides(BlockState sides) {
            this.sides = sides;
            return this;
        }

        public ExpandedLakeFeatureConfig.Builder setBase(BlockState base) {
            this.base = base;
            return this;
        }

        public ExpandedLakeFeatureConfig build() {
            return new ExpandedLakeFeatureConfig(liquid, sides, base);
        }
    }
}
