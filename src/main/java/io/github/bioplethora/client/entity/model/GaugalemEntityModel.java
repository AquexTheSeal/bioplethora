package io.github.bioplethora.client.entity.model;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.creatures.GaugalemEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class GaugalemEntityModel extends AnimatedGeoModel<GaugalemEntity> {

    @Override
    public ResourceLocation getModelLocation(GaugalemEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "geo/gaugalem.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(GaugalemEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/gaugalem.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(GaugalemEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/gaugalem.animation.json");
    }

    @Override
    public void setLivingAnimations(GaugalemEntity entity, Integer uniqueID, @SuppressWarnings("rawtypes") AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);

        IBone head = this.getAnimationProcessor().getBone("gaugalem");
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        head.setRotationX((extraData.headPitch) * ((float) Math.PI / 180F));
    }
}
