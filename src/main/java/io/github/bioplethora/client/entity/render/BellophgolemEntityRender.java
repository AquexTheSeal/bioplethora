package io.github.bioplethora.client.entity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.client.entity.model.AlphemEntityModel;
import io.github.bioplethora.client.entity.model.BellophgolemEntityModel;
import io.github.bioplethora.entity.AlphemEntity;
import io.github.bioplethora.entity.BellophgolemEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class BellophgolemEntityRender extends GeoEntityRenderer<BellophgolemEntity> {

    public BellophgolemEntityRender(EntityRendererManager renderManager) {
        super(renderManager, new BellophgolemEntityModel());
        this.shadowRadius = 2.2F;
    }

    @Override
    public void renderEarly(BellophgolemEntity animatable, MatrixStack stackIn, float ticks, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
    }

    @Override
    public ResourceLocation getTextureLocation(BellophgolemEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/bellophgolem.png");
    }

    @Override
    public RenderType getRenderType(BellophgolemEntity animatable, float partialTicks, MatrixStack stack, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }

    @Override
    public void renderRecursively(GeoBone bone, MatrixStack stack, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        super.renderRecursively(bone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    @Override
    protected float getDeathMaxRotation(BellophgolemEntity entity) {
        return 0.0F;
    }
}
