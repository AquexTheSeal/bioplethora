package io.github.bioplethora.client.entity.render.layer;

import com.mojang.blaze3d.matrix.MatrixStack;
import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.creatures.EurydnEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class EurydnEntityGlowLayer extends GeoLayerRenderer<EurydnEntity> {

    private static final ResourceLocation GLOW_FIERY = new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/layers/fiery_eurydn_glow_layer.png");
    private static final ResourceLocation GLOW_SOUL = new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/layers/soul_eurydn_glow_layer.png");

    private static final ResourceLocation TRSLCT_FIERY = new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/fiery_eurydn.png");
    private static final ResourceLocation TRSLCT_SOUL = new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/soul_eurydn.png");

    private static final ResourceLocation MODEL = new ResourceLocation(Bioplethora.MOD_ID, "geo/eurydn.geo.json");

    public EurydnEntityGlowLayer(IGeoRenderer<EurydnEntity> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, EurydnEntity entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {

        boolean isFiery = entityLivingBaseIn.variant == EurydnEntity.Variant.FIERY;

        if (!entityLivingBaseIn.isDeadOrDying()) {
            RenderType cameo =  isFiery ? RenderType.eyes(GLOW_FIERY) : RenderType.eyes(GLOW_SOUL);

            this.getRenderer().render(this.getEntityModel().getModel(MODEL), entityLivingBaseIn, partialTicks, cameo, matrixStackIn, bufferIn, bufferIn.getBuffer(cameo), packedLightIn, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 0.75f);
        }

        if (!entityLivingBaseIn.level.isEmptyBlock(entityLivingBaseIn.blockPosition())) {
            RenderType trslct =  isFiery ? RenderType.eyes(TRSLCT_FIERY) : RenderType.eyes(TRSLCT_SOUL);

            matrixStackIn.scale(1.5F, 1.5F, 1.5F);
            this.getRenderer().render(this.getEntityModel().getModel(MODEL), entityLivingBaseIn, partialTicks, trslct, matrixStackIn, bufferIn, bufferIn.getBuffer(trslct), packedLightIn, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 0.15f);
        }
    }
}