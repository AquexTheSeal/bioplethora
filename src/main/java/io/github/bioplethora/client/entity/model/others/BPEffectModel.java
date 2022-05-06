package io.github.bioplethora.client.entity.model.others;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.others.BPEffectEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BPEffectModel extends AnimatedGeoModel<BPEffectEntity> {

    @Override
    public ResourceLocation getModelLocation(BPEffectEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "geo/others/bp_effect.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(BPEffectEntity entity) {
        if (entity.getEffectType() != null) {
            return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/others/" + entity.getEffectType().getTexture() + ".png");
        } else {
            Bioplethora.LOGGER.info("EffectType for BPEffectEntity is null!");
            return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/others/infernal_quarterstaff_slash.png");
        }
    }

    @Override
    public ResourceLocation getAnimationFileLocation(BPEffectEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/others/bp_effect.animation.json");
    }

    /*
    @Override
    public void setLivingAnimations(InfernalQuarterstaffSlashEntity entity, Integer uniqueID, @Nullable AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("bone");
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        head.setRotationY((extraData.netHeadYaw) * ((float) Math.PI / 270F));
    }*/
}
