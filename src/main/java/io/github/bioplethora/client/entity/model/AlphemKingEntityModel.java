package io.github.bioplethora.client.entity.model;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.api.IAdvancedGeoModel;
import io.github.bioplethora.entity.creatures.AlphemKingEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.AnimationProcessor;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class AlphemKingEntityModel extends AnimatedGeoModel<AlphemKingEntity> implements IAdvancedGeoModel<AlphemKingEntity> {

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

        adaptHeadOnLook(extraData, head);

        float tickCountNeg = entity.ageInTicks - (float) entity.tickCount;
        float lerpHelper = MathHelper.lerp(tickCountNeg, entity.prevHurtTime, entity.hurtTime) / entity.hurtDuration;
        float pi = (float) Math.PI;
        float rotationScale = entity.isBerserked() ? 0.65f : 0.45f;

        if (entity.isCharging()) {
            arml.setRotationX(70);
            armr.setRotationX(70);
        }

        if (entity.prevHurtTime > 0 && !Minecraft.getInstance().isPaused()) {
            lerpHelper = lerpHelper * lerpHelper * lerpHelper;
            head.setRotationX(head.getRotationX() + -MathHelper.sin(lerpHelper * pi) * rotationScale);
            bodytop.setRotationX(bodytop.getRotationX() + -MathHelper.sin(lerpHelper * pi) * rotationScale);

            float cosVal = MathHelper.cos(lerpHelper * 2 * pi);
            bodytop.setRotationY(bodytop.getRotationX() + (entity.hurtRandomizer ? cosVal * (rotationScale / 2) : -cosVal * -(rotationScale / 2)));

            bodymid.setRotationX(bodymid.getRotationX() + -MathHelper.sin(lerpHelper * pi) * rotationScale);
        }
    }
}
