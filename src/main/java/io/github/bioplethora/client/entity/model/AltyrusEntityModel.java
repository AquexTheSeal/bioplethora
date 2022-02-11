package io.github.bioplethora.client.entity.model;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.creatures.AltyrusEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class AltyrusEntityModel extends AnimatedGeoModel<AltyrusEntity> {

    @Override
    public ResourceLocation getModelLocation(AltyrusEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "geo/altyrus.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(AltyrusEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/altyrus.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(AltyrusEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/altyrus.animation.json");
    }

    @Override
    public void setLivingAnimations(AltyrusEntity entity, Integer uniqueID, @SuppressWarnings("rawtypes") AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);

        IBone head = this.getAnimationProcessor().getBone("head");
        IBone altyrus = this.getAnimationProcessor().getBone("altyrus");
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);

        if (entity.isCharging()) {
            altyrus.setRotationX((extraData.headPitch) * ((float) Math.PI / 180F));
            altyrus.setRotationY((extraData.netHeadYaw) * ((float) Math.PI / 270F));
        } else {
            head.setRotationX((extraData.headPitch) * ((float) Math.PI / 180F));
            head.setRotationY((extraData.netHeadYaw) * ((float) Math.PI / 270F));
        }
    }
}
