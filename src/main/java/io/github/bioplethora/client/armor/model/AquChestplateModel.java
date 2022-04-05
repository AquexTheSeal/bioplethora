package io.github.bioplethora.client.armor.model;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.item.armor.AquChestplateItem;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AquChestplateModel extends AnimatedGeoModel<AquChestplateItem> {

    @Override
    public ResourceLocation getModelLocation(AquChestplateItem object) {
        return new ResourceLocation(Bioplethora.MOD_ID, "geo/armors/aqu_chestplate.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(AquChestplateItem object) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/models/armor/aqu_layer_1.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(AquChestplateItem animatable) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/armors/aqu_chestplate.animation.json");
    }
}
