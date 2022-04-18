package io.github.bioplethora.client.entity.model;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.creatures.EurydnEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.AnimationProcessor;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class EurydnEntityModel extends AnimatedGeoModel<EurydnEntity> {

    @Override
    public ResourceLocation getModelLocation(EurydnEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "geo/eurydn.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EurydnEntity entity) {
        if (entity.variant == EurydnEntity.Variant.FIERY) {
            return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/fiery_eurydn.png");
        } else if (entity.variant == EurydnEntity.Variant.SOUL) {
            return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/soul_eurydn.png");
        } else {
            throw new IllegalStateException("Invalid Eurydn variant!");
        }
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EurydnEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/eurydn.animation.json");
    }

    @Override
    public void setLivingAnimations(EurydnEntity entity, Integer uniqueID, @SuppressWarnings("rawtypes") AnimationEvent event) {
        super.setLivingAnimations(entity, uniqueID, event);

        AnimationProcessor ap = this.getAnimationProcessor();
        EntityModelData extraData = (EntityModelData) event.getExtraDataOfType(EntityModelData.class).get(0);
        IBone head = ap.getBone("head");
        IBone tailfront = ap.getBone("tailfront"), tailmid = ap.getBone("tailmid"), tailbot = ap.getBone("tailbot");

        head.setRotationX((extraData.headPitch) * ((float) Math.PI / 180F));
        head.setRotationY((extraData.netHeadYaw) * ((float) Math.PI / 270F));

        tailfront.setRotationX(tailfront.getRotationX() + this.tailRotationHelper(0.4F, 0.5F * 0.1F, 3F, -0.1F, event.getLimbSwing(), event.getLimbSwingAmount(), false));
        tailmid.setRotationX(tailmid.getRotationX() + this.tailRotationHelper(0.4F, 0.5F * 0.1F, 3F, -0.1F, event.getLimbSwing(), event.getLimbSwingAmount(), true));
        tailbot.setRotationX(tailbot.getRotationX() + this.tailRotationHelper(0.4F, 0.5F * 0.1F, 3F, -0.1F, event.getLimbSwing(), event.getLimbSwingAmount(), true));
    }

    public float tailRotationHelper(float speed, float degree, float offset, float weight, float limbswing, float limbswingamount, boolean neg) {
        float rotation = (MathHelper.cos(limbswing * (speed) + offset) * (degree) * limbswingamount) + (weight * limbswingamount);
        return neg ? -rotation : rotation;
    }
}
