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

        /*
        boolean isNegative = false;
        boolean isNegative2 = false;
        int hurtTime = entity.hurtTime;

        IBone head = this.getAnimationProcessor().getBone("head");
        IBone bodytop = this.getAnimationProcessor().getBone("bodytop");
        IBone bodymid = this.getAnimationProcessor().getBone("bodymid");
        IBone arml = this.getAnimationProcessor().getBone("arml");
        IBone armr = this.getAnimationProcessor().getBone("armr");
        IBone legl = this.getAnimationProcessor().getBone("legl");
        IBone legr = this.getAnimationProcessor().getBone("legr");

        if (entity.hurtTime >= 10) {
            isNegative = Math.random() <= 0.5;
            isNegative2 = Math.random() >= 0.5;
        }
        if (entity.hurtTime > 0) {
            randomHurtAnimSideward(head, hurtTime, isNegative);
            randomHurtAnimSideward(bodytop, hurtTime, isNegative2);
            randomHurtAnimSideward(bodymid, hurtTime, isNegative);

            randomHurtAnimForward(arml, hurtTime, isNegative2);
            randomHurtAnimForward(armr, hurtTime, isNegative);
            randomHurtAnimForward(legl, hurtTime, isNegative);
            randomHurtAnimForward(legr, hurtTime, isNegative2);
        } else if (entity.hurtTime == 0) {
            clearAnimation(head);
            clearAnimation(bodytop);
            clearAnimation(bodymid);
            clearAnimation(arml);
            clearAnimation(armr);
            clearAnimation(legl);
            clearAnimation(legr);
        }
        */
    }

    public float hurtEasingAnim(int hurtTime, int speed, int movementAmount) {
        return MathHelper.cos(speed) * movementAmount;
    }

    public void randomHurtAnimForward(IBone bone, int hurtTime, boolean isNegative) {
        bone.setPositionZ(hurtEasingAnim(hurtTime, 45, isNegative ? 2 : -2));
        bone.setRotationX(hurtEasingAnim(hurtTime, 45, isNegative ? 15 : -15));
    }

    public void randomHurtAnimSideward(IBone bone, int hurtTime, boolean isNegative) {
        bone.setRotationY(hurtEasingAnim(hurtTime, 45, isNegative ? 15 : -15));
    }

    public void clearAnimation(IBone bone) {
        bone.setPositionX(0);
        bone.setPositionY(0);
        bone.setPositionZ(0);
    }
}
