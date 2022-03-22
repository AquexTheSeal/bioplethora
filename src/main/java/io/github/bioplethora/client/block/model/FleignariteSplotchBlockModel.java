package io.github.bioplethora.client.block.model;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.blocks.tile_entities.FleignariteSplotchTileEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FleignariteSplotchBlockModel extends AnimatedGeoModel<FleignariteSplotchTileEntity> {

    @Override
    public ResourceLocation getModelLocation(FleignariteSplotchTileEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "geo/blocks/fleignarite_splotch.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(FleignariteSplotchTileEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/block/fleignarite_splotch.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(FleignariteSplotchTileEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "animations/blocks/fleignarite_splotch.animation.json");
    }
}
