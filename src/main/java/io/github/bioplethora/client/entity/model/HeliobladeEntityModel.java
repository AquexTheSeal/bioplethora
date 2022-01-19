package io.github.bioplethora.client.entity.model;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.creatures.HeliobladeEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class HeliobladeEntityModel extends AnimatedGeoModel<HeliobladeEntity> {

    @Override
    public ResourceLocation getModelLocation(HeliobladeEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "geo/helioblade.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(HeliobladeEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/helioblade.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(HeliobladeEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/helioblade.animation.json");
    }

    @Override
    public void setLivingAnimations(HeliobladeEntity entity, Integer uniqueID, @SuppressWarnings("rawtypes") AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);

        IBone head = this.getAnimationProcessor().getBone("Head");
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        head.setRotationX((extraData.headPitch) * ((float) Math.PI / 180F));
        head.setRotationY((extraData.netHeadYaw) * ((float) Math.PI / 270F));
    }
}
