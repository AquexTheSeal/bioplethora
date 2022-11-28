package io.github.bioplethora.client.entity.model;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.blocks.api.IAdvancedGeoModel;
import io.github.bioplethora.entity.creatures.MyuthineEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import javax.annotation.Nullable;

public class MyuthineEntityModel extends AnimatedGeoModel<MyuthineEntity> implements IAdvancedGeoModel<MyuthineEntity> {

    @Override
    public ResourceLocation getModelLocation(MyuthineEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "geo/myuthine.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(MyuthineEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/myuthine.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(MyuthineEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/myuthine.animation.json");
    }

    @Override
    public void setLivingAnimations(MyuthineEntity entity, Integer uniqueID, @Nullable AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);

        IBone head = getB("bodytop");
        float tickCountNeg = entity.ageInTicks - (float) entity.tickCount;
        float lerpHelper = MathHelper.lerp(tickCountNeg, entity.prevHurtTime, entity.hurtTime) / entity.hurtDuration;
        float pi = (float) Math.PI;

        this.adaptHeadOnLook(extraData, head);

        if (entity.prevHurtTime > 0 && !Minecraft.getInstance().isPaused()) {
            lerpHelper = lerpHelper * lerpHelper * lerpHelper;
            head.setRotationX(head.getRotationX() + -MathHelper.sin(lerpHelper * pi) * 0.35f);
        }
    }

    public IBone getB(String bone) {
        return this.getAnimationProcessor().getBone(bone);
    }
}
