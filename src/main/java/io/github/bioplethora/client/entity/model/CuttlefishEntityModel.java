package io.github.bioplethora.client.entity.model;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.creatures.CuttlefishEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CuttlefishEntityModel extends AnimatedGeoModel<CuttlefishEntity> {

    @Override
    public ResourceLocation getModelLocation(CuttlefishEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "geo/cuttlefish.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(CuttlefishEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/cuttlefish.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(CuttlefishEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/cuttlefish.animation.json");
    }
}
