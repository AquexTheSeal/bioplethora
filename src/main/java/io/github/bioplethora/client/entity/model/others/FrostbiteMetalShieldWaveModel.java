package io.github.bioplethora.client.entity.model.others;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.others.FrostbiteMetalShieldWaveEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FrostbiteMetalShieldWaveModel extends AnimatedGeoModel<FrostbiteMetalShieldWaveEntity> {

    @Override
    public ResourceLocation getModelLocation(FrostbiteMetalShieldWaveEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "geo/others/frostbite_metal_shield_wave.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(FrostbiteMetalShieldWaveEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/others/frostbite_metal_shield_wave.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(FrostbiteMetalShieldWaveEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/others/frostbite_metal_shield_wave.animation.json");
    }
}
