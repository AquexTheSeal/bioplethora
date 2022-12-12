package io.github.bioplethora.client.entity.model;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.api.IAdvancedGeoModel;
import io.github.bioplethora.entity.creatures.VoidjawEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class VoidjawEntityModel extends AnimatedGeoModel<VoidjawEntity> implements IAdvancedGeoModel<VoidjawEntity> {

    @Override
    public ResourceLocation getModelLocation(VoidjawEntity entity) {
        if (!entity.isSaddled()) {
            return new ResourceLocation(Bioplethora.MOD_ID, "geo/voidjaw.geo.json");
        } else {
            return new ResourceLocation(Bioplethora.MOD_ID, "geo/voidjaw_saddled.geo.json");
        }
    }

    @Override
    public ResourceLocation getTextureLocation(VoidjawEntity entity) {
        if (entity.isCardinalVariant()) {
            return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/cardinal_voidjaw.png");
        } else {
            return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/voidjaw.png");
        }
    }

    @Override
    public ResourceLocation getAnimationFileLocation(VoidjawEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/voidjaw.animation.json");
    }

    @Override
    public void setLivingAnimations(VoidjawEntity entity, Integer uniqueID, @SuppressWarnings("rawtypes") AnimationEvent event) {
        super.setLivingAnimations(entity, uniqueID, event);
        EntityModelData extraData = (EntityModelData) event.getExtraDataOfType(EntityModelData.class).get(0);
        adaptHeadOnLook(extraData, getAnimationProcessor().getBone("trapjaw"));
    }
}
