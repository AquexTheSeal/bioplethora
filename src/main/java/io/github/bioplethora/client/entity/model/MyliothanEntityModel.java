package io.github.bioplethora.client.entity.model;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.creatures.MyliothanEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class MyliothanEntityModel extends AnimatedGeoModel<MyliothanEntity> {

    @Override
    public ResourceLocation getModelLocation(MyliothanEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "geo/myliothan.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(MyliothanEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/myliothan.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(MyliothanEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/myliothan.animation.json");
    }

    @Override
    public void setLivingAnimations(MyliothanEntity entity, Integer uniqueID, @SuppressWarnings("rawtypes") AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);

        IBone head = this.getAnimationProcessor().getBone("head");
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        head.setRotationX((extraData.headPitch) * ((float) Math.PI / 180F));
        head.setRotationY((extraData.netHeadYaw) * ((float) Math.PI / 270F));
    }
}
