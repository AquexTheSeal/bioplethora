package io.github.bioplethora.client.entity.model;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.creatures.AlphemKingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AlphemKingEntityModel extends AnimatedGeoModel<AlphemKingEntity> {

    public int chargeTime = 0;

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

        int i = entity.hurtTime;

        IBone head = this.getAnimationProcessor().getBone("head");
        IBone bodytop = this.getAnimationProcessor().getBone("bodytop");
        IBone bodymid = this.getAnimationProcessor().getBone("bodymid");
        IBone arml = this.getAnimationProcessor().getBone("arml");
        IBone armr = this.getAnimationProcessor().getBone("armr");
        IBone legl = this.getAnimationProcessor().getBone("legl");
        IBone legr = this.getAnimationProcessor().getBone("legr");

        if (entity.isCharging()) {
            ++chargeTime;
            if (chargeTime == 1) {
                head.setScaleX(1.2F * headEasingAnim(chargeTime));
                head.setScaleY(1.2F * headEasingAnim(chargeTime));
                head.setScaleZ(1.2F * headEasingAnim(chargeTime));
            }

            if (chargeTime >= 50) {
                head.setScaleX(headEasingAnim(chargeTime));
                head.setScaleY(headEasingAnim(chargeTime));
                head.setScaleZ(headEasingAnim(chargeTime));
            }

            if (chargeTime == 69) {
                chargeTime = 0;
            }
        }

        if (entity.hurtTime == 10) {
            head.setPositionZ(-2F * hurtEasingAnim(i));
            bodytop.setPositionZ(2F * hurtEasingAnim(i));
            bodymid.setPositionZ(-2F * hurtEasingAnim(i));
            arml.setPositionZ(2F * hurtEasingAnim(i));
            armr.setPositionZ(-2F * hurtEasingAnim(i));
            legl.setPositionZ(-2F * hurtEasingAnim(i));
            legr.setPositionZ(2F * hurtEasingAnim(i));
        }

        if (entity.hurtTime <= 9) {
            head.setPositionZ(2F * hurtEasingAnim(i));
            bodytop.setPositionZ(-2F * hurtEasingAnim(i));
            bodymid.setPositionZ(2F * hurtEasingAnim(i));
            arml.setPositionZ(-2F * hurtEasingAnim(i));
            armr.setPositionZ(2F * hurtEasingAnim(i));
            legl.setPositionZ(2F * hurtEasingAnim(i));
            legr.setPositionZ(-2F * hurtEasingAnim(i));
        }
    }

    public float hurtEasingAnim(int i) {
        return MathHelper.sin(i * 180F);
        //return 1.5F * MathHelper.triangleWave((float)i - 10, 10.0F);
    }

    public float headEasingAnim(int i) {
        return MathHelper.sin(i * 22.5F);
    }
}
