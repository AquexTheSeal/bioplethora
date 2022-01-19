package io.github.bioplethora.client.entity.model.projectile;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.projectile.VermilionBladeProjectileEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class VermilionBladeProjectileModel extends AnimatedGeoModel<VermilionBladeProjectileEntity> {

    @Override
    public ResourceLocation getModelLocation(VermilionBladeProjectileEntity object) {
        return new ResourceLocation(Bioplethora.MOD_ID, "geo/projectiles/vermilion_blade_projectile.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(VermilionBladeProjectileEntity object) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/item/vermilion_blade.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(VermilionBladeProjectileEntity animatable) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/projectiles/vermilion_blade_projectile.animation.json");
    }
}
