package io.github.bioplethora.client.entity.model;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.creatures.TrapjawEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TrapjawEntityModel extends AnimatedGeoModel<TrapjawEntity> {

    @Override
    public ResourceLocation getModelLocation(TrapjawEntity entity) {
        if (!entity.isSaddled()) {
            return new ResourceLocation(Bioplethora.MOD_ID, "geo/trapjaw.geo.json");
        } else {
            return new ResourceLocation(Bioplethora.MOD_ID, "geo/trapjaw_saddled.geo.json");
        }
    }

    @Override
    public ResourceLocation getTextureLocation(TrapjawEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/trapjaw.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(TrapjawEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/trapjaw.animation.json");
    }
}
