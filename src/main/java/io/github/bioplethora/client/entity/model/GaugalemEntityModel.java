package io.github.bioplethora.client.entity.model;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.CuttlefishEntity;
import io.github.bioplethora.entity.GaugalemEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GaugalemEntityModel extends AnimatedGeoModel<GaugalemEntity> {

    @Override
    public ResourceLocation getModelLocation(GaugalemEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "geo/gaugalem.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(GaugalemEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/gaugalem.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(GaugalemEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/gaugalem.animation.json");
    }
}
