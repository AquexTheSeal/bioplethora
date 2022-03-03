package io.github.bioplethora.client.entity.model.projectile;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.projectile.CryoblazeEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CryoblazeModel extends AnimatedGeoModel<CryoblazeEntity> {

    @Override
    public ResourceLocation getModelLocation(CryoblazeEntity object) {
        return new ResourceLocation(Bioplethora.MOD_ID, "geo/projectiles/cryoblaze.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(CryoblazeEntity object) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/projectiles/cryoblaze.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(CryoblazeEntity animatable) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/projectiles/cryoblaze.animation.json");
    }
}
