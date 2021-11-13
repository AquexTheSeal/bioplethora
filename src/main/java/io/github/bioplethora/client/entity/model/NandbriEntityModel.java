package io.github.bioplethora.client.entity.model;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.CrephoxlEntity;
import io.github.bioplethora.entity.NandbriEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class NandbriEntityModel extends AnimatedGeoModel<NandbriEntity> {

    @Override
    public ResourceLocation getModelLocation(NandbriEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "geo/nandbri.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(NandbriEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/nandbri.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(NandbriEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/nandbri.animation.json");
    }
}
