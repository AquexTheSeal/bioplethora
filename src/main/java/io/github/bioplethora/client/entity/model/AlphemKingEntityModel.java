package io.github.bioplethora.client.entity.model;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.creatures.AlphemKingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.AnimationProcessor;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class AlphemKingEntityModel extends AnimatedGeoModel<AlphemKingEntity> {

    @Override
    public ResourceLocation getModelLocation(AlphemKingEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "geo/alphem_king.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(AlphemKingEntity entity) {
        if (!entity.isBerserked()) {
            return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/alphem_king.png");
        } else {
            return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/alphem_king_berserked.png");
        }
    }

    @Override
    public ResourceLocation getAnimationFileLocation(AlphemKingEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/alphem_king.animation.json");
    }
    @Override
    public void setLivingAnimations(AlphemKingEntity entity, Integer uniqueID, @SuppressWarnings("rawtypes") AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);

        AnimationProcessor ap = this.getAnimationProcessor();
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        IBone head = ap.getBone("head"), bodytop = ap.getBone("bodytop"), bodymid = ap.getBone("bodymid");
        IBone arml = ap.getBone("arml"), armr = ap.getBone("armr");
        IBone legl = ap.getBone("legl"), legr = ap.getBone("legr");

        float tickCountNeg = 0.0F - (float) entity.tickCount;
        float lerpHelper = MathHelper.lerp(tickCountNeg, entity.hurtTime, entity.hurtTime) / entity.hurtDuration;
        float pi = (float) Math.PI;
        float rotationScale = entity.isBerserked() ? 0.75f : 0.3f;

        head.setRotationX((extraData.headPitch) * ((float) Math.PI / 180F));
        head.setRotationY((extraData.netHeadYaw) * ((float) Math.PI / 270F));

        if (entity.isCharging()) {
            arml.setRotationX(90);
            armr.setRotationX(90);
        }

        if (entity.hurtTime > 0) {
            lerpHelper = lerpHelper * lerpHelper * lerpHelper;
            head.setRotationX(head.getRotationX() + -MathHelper.sin(lerpHelper * pi) * rotationScale);
            bodytop.setRotationX(bodytop.getRotationX() + -MathHelper.sin(lerpHelper * pi) * rotationScale);
            bodymid.setRotationX(bodymid.getRotationX() + -MathHelper.sin(lerpHelper * pi) * rotationScale);
        }
    }
}
