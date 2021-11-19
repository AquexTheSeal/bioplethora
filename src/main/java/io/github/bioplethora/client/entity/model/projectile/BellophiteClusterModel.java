package io.github.bioplethora.client.entity.model.projectile;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.projectile.BellophiteClusterEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BellophiteClusterModel extends AnimatedGeoModel<BellophiteClusterEntity> {

    @Override
    public ResourceLocation getModelLocation(BellophiteClusterEntity object) {
        return new ResourceLocation(Bioplethora.MOD_ID, "geo/projectiles/bellophite_cluster.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(BellophiteClusterEntity object) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/projectiles/bellophite_cluster.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(BellophiteClusterEntity animatable) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/projectiles/bellophite_cluster.animation.json");
    }
}
