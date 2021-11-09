package io.github.bioplethora.client.entity.model;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.CrephoxlEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CrephoxlEntityModel extends AnimatedGeoModel<CrephoxlEntity> {

    @Override
    public ResourceLocation getModelLocation(CrephoxlEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "geo/crephoxl.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(CrephoxlEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/crephoxl.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(CrephoxlEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/crephoxl.animation.json");
    }
}
