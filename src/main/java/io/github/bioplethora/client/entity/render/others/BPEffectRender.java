package io.github.bioplethora.client.entity.render.others;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.bioplethora.client.entity.model.others.BPEffectModel;
import io.github.bioplethora.entity.others.BPEffectEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class BPEffectRender extends GeoProjectilesRenderer<BPEffectEntity> {

    public BPEffectRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new BPEffectModel());
    }

    @Override
    public RenderType getRenderType(BPEffectEntity animatable, float partialTicks, MatrixStack stack, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entitySmoothCutout(getTextureLocation(animatable));
    }

    protected int getBlockLightLevel(BPEffectEntity entityIn, BlockPos partialTicks) {
        return 15;
    }

    @Override
    public void renderEarly(BPEffectEntity animatable, MatrixStack stackIn, float ticks, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        float f = 1;
        f = (float) ((double) f * animatable.getEffectType().getScale());
        stackIn.scale(f, f, f);
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, renderTypeBuffer.getBuffer(RenderType.eyes(getTextureLocation(animatable))), packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
    }

    @Override
    public void renderLate(BPEffectEntity animatable, MatrixStack stackIn, float partialTicks, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        //stackIn.mulPose(Vector3f.YP.rotationDegrees(MathHelper.lerp(partialTicks, animatable.yRotO, animatable.yRot) - 90.0F));
        super.renderLate(animatable, stackIn, partialTicks, renderTypeBuffer, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    @Override
    public void renderRecursively(GeoBone bone, MatrixStack stack, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        super.renderRecursively(bone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}
