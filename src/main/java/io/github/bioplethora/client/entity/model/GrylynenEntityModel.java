package io.github.bioplethora.client.entity.model;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.creatures.GrylynenEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class GrylynenEntityModel extends AnimatedGeoModel<GrylynenEntity> {

    @Override
    public ResourceLocation getModelLocation(GrylynenEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "geo/grylynen.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(GrylynenEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/grylynen/grylynen_" + entity.getGrylynenTier().getTierName() + ".png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(GrylynenEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/grylynen.animation.json");
    }

    @Override
    public void setLivingAnimations(GrylynenEntity entity, Integer uniqueID, @SuppressWarnings("rawtypes") AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);

        IBone head = this.getAnimationProcessor().getBone("head");
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        head.setRotationX((extraData.headPitch) * ((float) Math.PI / 180F));
        head.setRotationY((extraData.netHeadYaw) * ((float) Math.PI / 270F));
    }
}
