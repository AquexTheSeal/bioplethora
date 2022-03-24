package io.github.bioplethora.client.entity.model;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.creatures.TerraithEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TerraithEntityModel extends AnimatedGeoModel<TerraithEntity> {

    @Override
    public ResourceLocation getModelLocation(TerraithEntity object) {
        return new ResourceLocation(Bioplethora.MOD_ID, "geo/terraith.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(TerraithEntity object) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/terraith.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(TerraithEntity animatable) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/terraith.animation.json");
    }
}
