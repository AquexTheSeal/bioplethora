package io.github.bioplethora.client.entity.render.layer;

import com.mojang.blaze3d.matrix.MatrixStack;
import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.creatures.VoidjawEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class VoidjawImpulseLayer extends GeoLayerRenderer<VoidjawEntity> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/voidjaw.png");
    private static final ResourceLocation MODEL = new ResourceLocation(Bioplethora.MOD_ID, "geo/voidjaw.geo.json");

    public VoidjawImpulseLayer(IGeoRenderer<VoidjawEntity> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, VoidjawEntity entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entityLivingBaseIn.isSaving()) {
            RenderType trslct = RenderType.eyes(TEXTURE);
            matrixStackIn.scale(1.5F, 1.5F, 1.5F);
            this.getRenderer().render(this.getEntityModel().getModel(MODEL), entityLivingBaseIn, partialTicks, trslct, matrixStackIn, bufferIn, bufferIn.getBuffer(trslct), packedLightIn, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 0.15f);
        }
    }
}