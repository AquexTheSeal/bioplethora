package io.github.bioplethora.client.entity.model.others;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.others.AltyrusSummoningEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AltyrusSummoningModel extends AnimatedGeoModel<AltyrusSummoningEntity> {

    @Override
    public ResourceLocation getModelLocation(AltyrusSummoningEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "geo/others/altyrus_summoning.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(AltyrusSummoningEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/others/altyrus_summoning.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(AltyrusSummoningEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/others/altyrus_summoning.animation.json");
    }
}
