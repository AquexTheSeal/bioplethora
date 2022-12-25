package io.github.bioplethora.api;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public interface IAdvancedGeoModel<E extends LivingEntity> {

    default void adaptHeadOnLook(EntityModelData data, IBone... bones) {
        for (IBone bone : bones) {
            adaptHeadOnLook(data, bone);
        }
    }

    default void adaptHeadOnLook(EntityModelData data, IBone bone) {
        bone.setRotationX((data.headPitch) * ((float) Math.PI / 180F));
        bone.setRotationY((data.netHeadYaw) * ((float) Math.PI / 270F));
    }
}
