package io.github.bioplethora.client.entity.render.projectile;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.projectile.FrostbiteMetalArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class FrostbiteMetalArrowRender extends ArrowRenderer<FrostbiteMetalArrowEntity> {

    public static final ResourceLocation BELLOPHITE_ARROW_LOCATION = new ResourceLocation(Bioplethora.MOD_ID, "textures/projectiles/frostbite_metal_arrow.png");

    public FrostbiteMetalArrowRender(EntityRendererManager p_i46549_1_) {
        super(p_i46549_1_);
    }

    public ResourceLocation getTextureLocation(FrostbiteMetalArrowEntity p_110775_1_) {
        return BELLOPHITE_ARROW_LOCATION;
    }
}
