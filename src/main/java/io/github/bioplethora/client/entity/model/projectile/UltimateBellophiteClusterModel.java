package io.github.bioplethora.client.entity.model.projectile;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.projectile.UltimateBellophiteClusterEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class UltimateBellophiteClusterModel extends AnimatedGeoModel<UltimateBellophiteClusterEntity> {

    @Override
    public ResourceLocation getModelLocation(UltimateBellophiteClusterEntity object) {
        return new ResourceLocation(Bioplethora.MOD_ID, "geo/projectiles/bellophite_cluster.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(UltimateBellophiteClusterEntity object) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/projectiles/ultimate_bellophite_cluster.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(UltimateBellophiteClusterEntity animatable) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/projectiles/bellophite_cluster.animation.json");
    }
}
