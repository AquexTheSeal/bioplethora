package io.github.bioplethora.client.entity.model.others;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.others.PrimordialRingEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PrimordialRingEntityModel extends AnimatedGeoModel<PrimordialRingEntity> {

    @Override
    public ResourceLocation getModelLocation(PrimordialRingEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "geo/others/primordial_ring.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(PrimordialRingEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/layers/altyrus_glow_layer_summoning.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(PrimordialRingEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/others/primordial_ring.animation.json");
    }
}
