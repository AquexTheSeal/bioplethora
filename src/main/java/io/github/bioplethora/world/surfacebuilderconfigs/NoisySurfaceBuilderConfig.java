package io.github.bioplethora.world.surfacebuilderconfigs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig;

public class NoisySurfaceBuilderConfig implements ISurfaceBuilderConfig {

    public static final Codec<NoisySurfaceBuilderConfig> CODEC = RecordCodecBuilder.create((p_237204_0_) -> {
        return p_237204_0_.group(BlockState.CODEC.fieldOf("top_material").forGetter((p_237207_0_) -> {
            return p_237207_0_.topMaterial;
        }), BlockState.CODEC.fieldOf("uncommon_top_material").forGetter((p_237206_0_) -> {
            return p_237206_0_.topMaterialUncommon;
        }), BlockState.CODEC.fieldOf("under_material").forGetter((p_237206_0_) -> {
            return p_237206_0_.underMaterial;
        }), BlockState.CODEC.fieldOf("underwater_material").forGetter((p_237205_0_) -> {
            return p_237205_0_.underwaterMaterial;
        })).apply(p_237204_0_, NoisySurfaceBuilderConfig::new);
    });

    private final BlockState topMaterial;
    private final BlockState topMaterialUncommon;
    private final BlockState underMaterial;
    private final BlockState underwaterMaterial;

    public NoisySurfaceBuilderConfig(BlockState top, BlockState topUncommon, BlockState under, BlockState underwater) {
        this.topMaterial = top;
        this.topMaterialUncommon = topUncommon;
        this.underMaterial = under;
        this.underwaterMaterial = underwater;
    }

    public BlockState getTopMaterial() {
        return this.topMaterial;
    }

    public BlockState getTopMaterialUncommon() {
        return topMaterialUncommon;
    }

    public BlockState getUnderMaterial() {
        return this.underMaterial;
    }

    public BlockState getUnderwaterMaterial() {
        return this.underwaterMaterial;
    }
}
