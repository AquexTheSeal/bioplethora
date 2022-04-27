package io.github.bioplethora.client.entity.model;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.creatures.OnofishEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class OnofishEntityModel extends AnimatedGeoModel<OnofishEntity> {

    @Override
    public ResourceLocation getModelLocation(OnofishEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "geo/onofish.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(OnofishEntity entity) {
        switch (entity.getVariant()) {
            default: return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/onofish/onofish_purple.png");
            case 2: return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/onofish/onofish_blue.png");
            case 3: return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/onofish/onofish_cyan.png");
            case 4: return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/onofish/onofish_magenta.png");
        }
    }

    @Override
    public ResourceLocation getAnimationFileLocation(OnofishEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/onofish.animation.json");
    }
}
