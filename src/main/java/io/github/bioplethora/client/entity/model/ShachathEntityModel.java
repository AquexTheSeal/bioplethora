package io.github.bioplethora.client.entity.model;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.creatures.ShachathEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class ShachathEntityModel extends AnimatedGeoModel<ShachathEntity> {

    @Override
    public ResourceLocation getModelLocation(ShachathEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "geo/shachath.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(ShachathEntity entity) {
        if (entity.isClone()) {
            return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/shachath_clone.png");
        } else {
            return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/shachath.png");
        }
    }

    @Override
    public ResourceLocation getAnimationFileLocation(ShachathEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/shachath.animation.json");
    }

    @Override
    public void setLivingAnimations(ShachathEntity entity, Integer uniqueID, @SuppressWarnings("rawtypes") AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);

        IBone head = this.getAnimationProcessor().getBone("Head");
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        head.setRotationX((extraData.headPitch) * ((float) Math.PI / 180F));
        head.setRotationY((extraData.netHeadYaw) * ((float) Math.PI / 270F));
    }
}
