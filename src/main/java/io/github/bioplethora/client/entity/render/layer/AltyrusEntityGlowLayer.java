package io.github.bioplethora.client.entity.render.layer;

import com.mojang.blaze3d.matrix.MatrixStack;
import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.creatures.AltyrusEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class AltyrusEntityGlowLayer extends GeoLayerRenderer<AltyrusEntity> {

    private static final ResourceLocation GLOW_DEFAULT = new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/layers/altyrus_glow_layer_default.png");
    private static final ResourceLocation GLOW_ATTACK_OR_SHOOT = new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/layers/altyrus_glow_layer_shoot_or_attack.png");
    private static final ResourceLocation GLOW_SUMMONING = new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/layers/altyrus_glow_layer_summoning.png");
    private static final ResourceLocation MODEL = new ResourceLocation(Bioplethora.MOD_ID, "geo/altyrus.geo.json");

    public AltyrusEntityGlowLayer(IGeoRenderer<AltyrusEntity> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, AltyrusEntity entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if(!entityLivingBaseIn.isDeadOrDying()) {

            if (entityLivingBaseIn.getAttacking() || entityLivingBaseIn.isCharging()) {
                RenderType cameo =  RenderType.eyes(GLOW_ATTACK_OR_SHOOT);
                this.getRenderer().render(this.getEntityModel().getModel(MODEL), entityLivingBaseIn, partialTicks, cameo, matrixStackIn, bufferIn, bufferIn.getBuffer(cameo), packedLightIn, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);

            } else if (entityLivingBaseIn.isSummoning()) {
                RenderType cameo =  RenderType.eyes(GLOW_SUMMONING);
                this.getRenderer().render(this.getEntityModel().getModel(MODEL), entityLivingBaseIn, partialTicks, cameo, matrixStackIn, bufferIn, bufferIn.getBuffer(cameo), packedLightIn, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);

            } else {
                RenderType cameo =  RenderType.eyes(GLOW_DEFAULT);
                this.getRenderer().render(this.getEntityModel().getModel(MODEL), entityLivingBaseIn, partialTicks, cameo, matrixStackIn, bufferIn, bufferIn.getBuffer(cameo), packedLightIn, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
            }
        }
    }
}