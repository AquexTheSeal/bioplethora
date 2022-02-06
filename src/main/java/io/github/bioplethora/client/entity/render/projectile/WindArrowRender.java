package io.github.bioplethora.client.entity.render.projectile;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.projectile.WindArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class WindArrowRender extends ArrowRenderer<WindArrowEntity> {

    public static final ResourceLocation WIND_ARROW_LOCATION = new ResourceLocation(Bioplethora.MOD_ID, "textures/projectiles/wind_arrow.png");

    public WindArrowRender(EntityRendererManager p_i46549_1_) {
        super(p_i46549_1_);
    }

    public ResourceLocation getTextureLocation(WindArrowEntity p_110775_1_) {
        return WIND_ARROW_LOCATION;
    }
}
