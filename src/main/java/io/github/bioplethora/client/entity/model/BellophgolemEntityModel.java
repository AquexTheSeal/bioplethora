package io.github.bioplethora.client.entity.model;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.AlphemEntity;
import io.github.bioplethora.entity.BellophgolemEntity;
import io.github.bioplethora.entity.CrephoxlEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class BellophgolemEntityModel extends AnimatedGeoModel<BellophgolemEntity> {

    @Override
    public ResourceLocation getModelLocation(BellophgolemEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "geo/bellophgolem.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(BellophgolemEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/bellophgolem.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(BellophgolemEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/bellophgolem.animation.json");
    }

    @Override
    public void setLivingAnimations(BellophgolemEntity entity, Integer uniqueID, @SuppressWarnings("rawtypes") AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);

        IBone head = this.getAnimationProcessor().getBone("head");
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        head.setRotationX((extraData.headPitch) * ((float) Math.PI / 180F));
        head.setRotationY((extraData.netHeadYaw) * ((float) Math.PI / 270F));
    }
}
