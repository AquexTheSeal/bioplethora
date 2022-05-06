package io.github.bioplethora.world.featureconfigs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class NBTFeatureConfig implements IFeatureConfig {

    public static final Codec<NBTFeatureConfig> CODEC = RecordCodecBuilder.create((codecRecorder) -> codecRecorder.group(
            Codec.STRING.fieldOf("feature").forGetter((config) -> config.feature),
            Codec.INT.fieldOf("yOffset").forGetter((config) -> config.yOffset)
            ).apply(codecRecorder, NBTFeatureConfig::new));

    private final String feature;
    private final int yOffset;

    NBTFeatureConfig(String feature, int yOffset) {
        this.feature = feature;
        this.yOffset = yOffset;
    }

    public String getFeature() {
        return feature;
    }

    public int getYOffset() {
        return yOffset;
    }

    public static class Builder {
        private String feature = null;
        private int yOffset = 0;

        public NBTFeatureConfig.Builder setFeature(String feature) {
            this.feature = feature;
            return this;
        }

        /**
         * Sets the Y Level Offset for the feature.
         */
        public NBTFeatureConfig.Builder setYOffset(int yOffset) {
            this.yOffset = MathHelper.clamp(yOffset, -255, 255);
            return this;
        }

        public NBTFeatureConfig build() {
            return new NBTFeatureConfig(feature, yOffset);
        }
    }
}
