package io.github.bioplethora.client.entity.render.projectile;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.projectile.BellophiteArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.projectile.SpectralArrowEntity;
import net.minecraft.util.ResourceLocation;

public class BellophiteArrowRender extends ArrowRenderer<BellophiteArrowEntity> {

    public static final ResourceLocation BELLOPHITE_ARROW_LOCATION = new ResourceLocation(Bioplethora.MOD_ID, "textures/projectiles/bellophite_arrow.png");

    public BellophiteArrowRender(EntityRendererManager p_i46549_1_) {
        super(p_i46549_1_);
    }

    public ResourceLocation getTextureLocation(BellophiteArrowEntity p_110775_1_) {
        return BELLOPHITE_ARROW_LOCATION;
    }
}
