package io.github.bioplethora.client.entity.model.projectile;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.projectile.AlphanumObliteratorSpearEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AlphanumObliteratorSpearModel extends AnimatedGeoModel<AlphanumObliteratorSpearEntity> {

    @Override
    public ResourceLocation getModelLocation(AlphanumObliteratorSpearEntity object) {
        return new ResourceLocation(Bioplethora.MOD_ID, "geo/projectiles/alphanum_obliterator_spear.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(AlphanumObliteratorSpearEntity object) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/projectiles/alphanum_obliterator_spear.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(AlphanumObliteratorSpearEntity animatable) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/projectiles/alphanum_obliterator_spear.animation.json");
    }
}
