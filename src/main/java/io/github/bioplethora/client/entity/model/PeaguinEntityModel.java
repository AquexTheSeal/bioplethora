package io.github.bioplethora.client.entity.model;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.creatures.PeaguinEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PeaguinEntityModel extends AnimatedGeoModel<PeaguinEntity> {

    @Override
    public ResourceLocation getModelLocation(PeaguinEntity entity) {
        if (((LivingEntity) entity).isBaby()) {
            return new ResourceLocation(Bioplethora.MOD_ID, "geo/baby_peaguin.geo.json");
        } else {
            return new ResourceLocation(Bioplethora.MOD_ID, "geo/peaguin.geo.json");
        }
    }

    @Override
    public ResourceLocation getTextureLocation(PeaguinEntity entity) {
        if (((LivingEntity) entity).isBaby()) {
            return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/baby_peaguin.png");
        } else {
            return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/peaguin.png");
        }
    }

    @Override
    public ResourceLocation getAnimationFileLocation(PeaguinEntity entity) {
        if (((LivingEntity) entity).isBaby()) {
            return new ResourceLocation(Bioplethora.MOD_ID, "animations/baby_peaguin.animation.json");
        } else {
            return new ResourceLocation(Bioplethora.MOD_ID, "animations/peaguin.animation.json");
        }
    }

    /*@Override
    public void setLivingAnimations(PeaguinEntity entity, Integer uniqueID, @SuppressWarnings("rawtypes") AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);

        if (!((LivingEntity) entity).isBaby()) {
            IBone head = this.getAnimationProcessor().getBone("head");
            EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
            head.setRotationX((extraData.headPitch) * ((float) Math.PI / 180F));
            head.setRotationY((extraData.netHeadYaw) * ((float) Math.PI / 270F));
        }
    }*/
}
