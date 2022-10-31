package io.github.bioplethora.client.entity.render.layer;

import com.mojang.blaze3d.matrix.MatrixStack;
import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.creatures.ShachathEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class ShachathEntityGlowLayer extends GeoLayerRenderer<ShachathEntity> {

    private static final ResourceLocation GLOW = new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/layers/shachath_glow_layer.png");
    private static final ResourceLocation MODEL = new ResourceLocation(Bioplethora.MOD_ID, "geo/shachath.geo.json");

    public ShachathEntityGlowLayer(IGeoRenderer<ShachathEntity> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, ShachathEntity entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        RenderType eyesRender =  RenderType.eyes(GLOW);
        if(!entityLivingBaseIn.isDeadOrDying()) {
            this.getRenderer().render(this.getEntityModel().getModel(MODEL), entityLivingBaseIn, partialTicks, eyesRender, matrixStackIn, bufferIn, bufferIn.getBuffer(eyesRender), packedLightIn, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
        }
    }
}