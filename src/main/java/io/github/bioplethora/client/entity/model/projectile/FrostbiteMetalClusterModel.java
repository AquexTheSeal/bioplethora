package io.github.bioplethora.client.entity.model.projectile;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.projectile.FrostbiteMetalClusterEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FrostbiteMetalClusterModel extends AnimatedGeoModel<FrostbiteMetalClusterEntity> {

    @Override
    public ResourceLocation getModelLocation(FrostbiteMetalClusterEntity object) {
        return new ResourceLocation(Bioplethora.MOD_ID, "geo/projectiles/frostbite_metal_cluster.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(FrostbiteMetalClusterEntity object) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/projectiles/frostbite_metal_cluster.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(FrostbiteMetalClusterEntity animatable) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/projectiles/frostbite_metal_cluster.animation.json");
    }
}
