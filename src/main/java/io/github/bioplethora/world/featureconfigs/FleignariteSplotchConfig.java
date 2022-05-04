package io.github.bioplethora.world.featureconfigs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.IFeatureConfig;

import java.util.List;

public class FleignariteSplotchConfig implements IFeatureConfig {
    public static final Codec<FleignariteSplotchConfig> CODEC = RecordCodecBuilder.create((p_236638_0_) -> {
        return p_236638_0_.group(
                BlockState.CODEC.fieldOf("to_place").forGetter((p_236641_0_) -> {
            return p_236641_0_.toPlace;
        }), BlockState.CODEC.fieldOf("to_place_rare").forGetter((p_236640_0_) -> {
            return p_236640_0_.toPlaceRare;
        }), BlockState.CODEC.listOf().fieldOf("place_on").forGetter((p_236640_0_) -> {
            return p_236640_0_.placeOn;
        }), BlockState.CODEC.listOf().fieldOf("place_in").forGetter((p_236639_0_) -> {
            return p_236639_0_.placeIn;
        }), BlockState.CODEC.listOf().fieldOf("place_under").forGetter((p_236637_0_) -> {
            return p_236637_0_.placeUnder;
        })).apply(p_236638_0_, FleignariteSplotchConfig::new);
    });
    public final BlockState toPlace;
    public final BlockState toPlaceRare;
    public final List<BlockState> placeOn;
    public final List<BlockState> placeIn;
    public final List<BlockState> placeUnder;

    public FleignariteSplotchConfig(BlockState toPlace, BlockState toPlaceRare, List<BlockState> p_i51439_2_, List<BlockState> p_i51439_3_, List<BlockState> p_i51439_4_) {
        this.toPlace = toPlace;
        this.toPlaceRare = toPlaceRare;
        this.placeOn = p_i51439_2_;
        this.placeIn = p_i51439_3_;
        this.placeUnder = p_i51439_4_;
    }
}
