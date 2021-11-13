package io.github.bioplethora.client.entity.model;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.AlphemEntity;
import io.github.bioplethora.entity.CrephoxlEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AlphemEntityModel extends AnimatedGeoModel<AlphemEntity> {

    @Override
    public ResourceLocation getModelLocation(AlphemEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "geo/alphem.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(AlphemEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/alphem.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(AlphemEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/alphem.animation.json");
    }
}
