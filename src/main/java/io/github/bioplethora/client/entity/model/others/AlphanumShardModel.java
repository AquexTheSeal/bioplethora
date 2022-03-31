package io.github.bioplethora.client.entity.model.others;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.others.AlphanumShardEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AlphanumShardModel extends AnimatedGeoModel<AlphanumShardEntity> {

    @Override
    public ResourceLocation getModelLocation(AlphanumShardEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "geo/others/alphanum_shard.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(AlphanumShardEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/others/alphanum_shard.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(AlphanumShardEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/others/alphanum_shard.animation.json");
    }
}
