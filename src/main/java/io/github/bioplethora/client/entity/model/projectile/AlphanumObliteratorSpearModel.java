package io.github.bioplethora.client.entity.model.projectile;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.projectile.AlphanumObliteratorSpearEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

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

    @Override
    public void setLivingAnimations(AlphanumObliteratorSpearEntity entity, Integer uniqueID, @SuppressWarnings("rawtypes") AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);

        IBone head = this.getAnimationProcessor().getBone("spear");
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        head.setRotationX((extraData.headPitch) * ((float) Math.PI / 180F));
        head.setRotationY((extraData.netHeadYaw) * ((float) Math.PI / 270F));
    }
}
