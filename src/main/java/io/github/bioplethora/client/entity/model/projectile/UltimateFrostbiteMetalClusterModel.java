package io.github.bioplethora.client.entity.model.projectile;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.projectile.UltimateFrostbiteMetalClusterEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class UltimateFrostbiteMetalClusterModel extends AnimatedGeoModel<UltimateFrostbiteMetalClusterEntity> {

    @Override
    public ResourceLocation getModelLocation(UltimateFrostbiteMetalClusterEntity object) {
        return new ResourceLocation(Bioplethora.MOD_ID, "geo/projectiles/frostbite_metal_cluster.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(UltimateFrostbiteMetalClusterEntity object) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/projectiles/ultimate_frostbite_metal_cluster.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(UltimateFrostbiteMetalClusterEntity animatable) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/projectiles/frostbite_metal_cluster.animation.json");
    }
}
