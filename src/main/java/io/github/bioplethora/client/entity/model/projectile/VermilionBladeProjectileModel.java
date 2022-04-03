package io.github.bioplethora.client.entity.model.projectile;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.projectile.VermilionBladeProjectileEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

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

    @Override
    public void setLivingAnimations(VermilionBladeProjectileEntity entity, Integer uniqueID, @SuppressWarnings("rawtypes") AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);

        IBone head = this.getAnimationProcessor().getBone("vermilion_blade");
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        head.setRotationX((extraData.headPitch) * ((float) Math.PI / 180F));
        head.setRotationY((extraData.netHeadYaw) * ((float) Math.PI / 270F));
    }
}
