package io.github.bioplethora.client.entity.model.others;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.others.GrylynenCoreBombEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GrylynenCoreBombModel extends AnimatedGeoModel<GrylynenCoreBombEntity> {

    @Override
    public ResourceLocation getModelLocation(GrylynenCoreBombEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "geo/others/grylynen_core_bomb.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(GrylynenCoreBombEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/others/grylynen_core_bomb.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(GrylynenCoreBombEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/others/grylynen_core_bomb.animation.json");
    }
}
