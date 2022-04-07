package io.github.bioplethora.client.entity.model;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.creatures.AlphemKingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.AnimationProcessor;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;

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
        IBone head = ap.getBone("head"), bodytop = ap.getBone("bodytop"), bodymid = ap.getBone("bodymid");
        IBone arml = ap.getBone("arml"), armr = ap.getBone("armr");
        IBone legl = ap.getBone("legl"), legr = ap.getBone("legr");

        float tickCountNeg = 0.0F - (float) entity.tickCount;
        float lerpHelper = MathHelper.lerp(tickCountNeg, entity.hurtTime, entity.hurtTime) / entity.hurtDuration;
        float pi = (float) Math.PI;
        if (entity.hurtTime > 0) {
            lerpHelper = lerpHelper * lerpHelper * lerpHelper;
            head.setRotationX(head.getRotationX() + -MathHelper.sin(lerpHelper * pi) * 0.3f);
            bodytop.setRotationX(bodytop.getRotationX() + -MathHelper.sin(lerpHelper * pi) * 0.3f);
            bodymid.setRotationX(bodymid.getRotationX() + -MathHelper.sin(lerpHelper * pi) * 0.3f);
        }
    }
}
