package io.github.bioplethora.client.entity.model.others;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.others.BellophiteShieldWaveEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BellophiteShieldWaveModel extends AnimatedGeoModel<BellophiteShieldWaveEntity> {

    @Override
    public ResourceLocation getModelLocation(BellophiteShieldWaveEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "geo/others/bellophite_shield_wave.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(BellophiteShieldWaveEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/others/bellophite_shield_wave.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(BellophiteShieldWaveEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/others/bellophite_shield_wave.animation.json");
    }
}
