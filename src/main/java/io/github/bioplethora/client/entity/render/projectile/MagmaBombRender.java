package io.github.bioplethora.client.entity.render.projectile;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.projectile.MagmaBombEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;

public class MagmaBombRender extends EntityRenderer<MagmaBombEntity> {

    private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(Bioplethora.MOD_ID, "textures/item/magma_bomb.png");
    private static final RenderType RENDER_TYPE = RenderType.eyes(TEXTURE_LOCATION);

    public MagmaBombRender(EntityRendererManager manager) {
        super(manager);
    }

    public void render(MagmaBombEntity entity, float v, float v1, MatrixStack stack, IRenderTypeBuffer buffer, int i) {
        stack.pushPose();
        stack.scale(2.0F, 2.0F, 2.0F);
        stack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        stack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
        MatrixStack.Entry matrixstack$entry = stack.last();
        Matrix4f matrix4f = matrixstack$entry.pose();
        Matrix3f matrix3f = matrixstack$entry.normal();
        IVertexBuilder ivertexbuilder = buffer.getBuffer(RENDER_TYPE);
        vertex(ivertexbuilder, matrix4f, matrix3f, i, 0.0F, 0, 0, 1);
        vertex(ivertexbuilder, matrix4f, matrix3f, i, 1.0F, 0, 1, 1);
        vertex(ivertexbuilder, matrix4f, matrix3f, i, 1.0F, 1, 1, 0);
        vertex(ivertexbuilder, matrix4f, matrix3f, i, 0.0F, 1, 0, 0);
        stack.popPose();
        super.render(entity, v, v1, stack, buffer, i);
    }

    private static void vertex(IVertexBuilder builder, Matrix4f matrix4f, Matrix3f matrix3f, int i, float v, int i1, int i2, int i3) {
        builder.vertex(matrix4f, v - 0.5F, (float)i1 - 0.25F, 0.0F).color(255, 255, 255, 255).uv((float)i2, (float)i3).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(i).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
    }

    public ResourceLocation getTextureLocation(MagmaBombEntity entity) {
        return TEXTURE_LOCATION;
    }
}
