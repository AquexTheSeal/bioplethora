package io.github.bioplethora.client.entity.model;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.AlphemEntity;
import io.github.bioplethora.entity.BellophgolemEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BellophgolemEntityModel extends AnimatedGeoModel<BellophgolemEntity> {

    @Override
    public ResourceLocation getModelLocation(BellophgolemEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "geo/bellophgolem.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(BellophgolemEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/bellophgolem.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(BellophgolemEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/bellophgolem.animation.json");
    }
}
