package io.github.bioplethora.client.entity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.client.entity.model.AltyrusEntityModel;
import io.github.bioplethora.client.entity.render.layer.AltyrusEntityGlowLayer;
import io.github.bioplethora.entity.creatures.AltyrusEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class AltyrusEntityRender extends GeoEntityRenderer<AltyrusEntity> {

    private static final ResourceLocation GLOW = new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/layers/altyrus_glow_layer.png");
    private static final ResourceLocation MODEL = new ResourceLocation(Bioplethora.MOD_ID, "geo/altyrus.geo.json");

    public AltyrusEntityRender(EntityRendererManager renderManager) {
        super(renderManager, new AltyrusEntityModel());
        this.addLayer(new AltyrusEntityGlowLayer(this));
        this.shadowRadius = 1.5F;
    }

    @Override
    public void renderEarly(AltyrusEntity animatable, MatrixStack stackIn, float ticks, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
    }

    @Override
    public ResourceLocation getTextureLocation(AltyrusEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/altyrus.png");
    }

    @Override
    public RenderType getRenderType(AltyrusEntity animatable, float partialTicks, MatrixStack stack, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }

    @Override
    public void renderRecursively(GeoBone bone, MatrixStack matrixStack, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        super.renderRecursively(bone, matrixStack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    @Override
    protected float getDeathMaxRotation(AltyrusEntity entity) {
        return 0.0F;
    }
}
