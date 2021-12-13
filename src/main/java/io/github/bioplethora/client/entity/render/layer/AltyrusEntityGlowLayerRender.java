package io.github.bioplethora.client.entity.render.layer;

import com.mojang.blaze3d.matrix.MatrixStack;
import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.creatures.AltyrusEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class AltyrusEntityGlowLayerRender extends GeoLayerRenderer<AltyrusEntity> {

    public AltyrusEntityGlowLayerRender(IGeoRenderer<AltyrusEntity> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, AltyrusEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    public ResourceLocation getTextureLocation(AltyrusEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/altyrus.png");
    }

    public static RenderType getRenderType(ResourceLocation textureLocation) {
        return RenderType.eyes(textureLocation);
    }
}
