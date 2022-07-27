package io.github.bioplethora.client.entity.model;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.creatures.TrapjawEntity;
import io.github.bioplethora.entity.creatures.VoidjawEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class VoidjawEntityModel extends AnimatedGeoModel<VoidjawEntity> {

    @Override
    public ResourceLocation getModelLocation(VoidjawEntity entity) {
        if (!entity.isSaddled()) {
            return new ResourceLocation(Bioplethora.MOD_ID, "geo/voidjaw.geo.json");
        } else {
            return new ResourceLocation(Bioplethora.MOD_ID, "geo/voidjaw_saddled.geo.json");
        }
    }

    @Override
    public ResourceLocation getTextureLocation(VoidjawEntity entity) {
        if (entity.isCardinalVariant()) {
            return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/cardinal_voidjaw.png");
        } else {
            return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/voidjaw.png");
        }
    }

    @Override
    public ResourceLocation getAnimationFileLocation(VoidjawEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/voidjaw.animation.json");
    }
}
