package io.github.bioplethora.client.entity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.client.entity.model.AlphemEntityModel;
import io.github.bioplethora.entity.AlphemEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class AlphemEntityRender extends GeoEntityRenderer<AlphemEntity> {

    public AlphemEntityRender(EntityRendererManager renderManager) {
        super(renderManager, new AlphemEntityModel());
        this.shadowRadius = 0.5F;
    }

    @Override
    public void renderEarly(AlphemEntity animatable, MatrixStack stackIn, float ticks, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
    }

    @Override
    public ResourceLocation getTextureLocation(AlphemEntity entity) {
        return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/alphem.png");
    }

    @Override
    public RenderType getRenderType(AlphemEntity animatable, float partialTicks, MatrixStack stack, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }

    @Override
    public void renderRecursively(GeoBone bone, MatrixStack stack, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (bone.getName().equals("right_hand") || bone.getName().equals("right_arm")) {
            if (!bone.isHidden) {
                stack.pushPose();

                //position / translation
                stack.translate(0.45D, 0.15D, -0.15D);

                //rotation
                stack.mulPose(Vector3f.XP.rotationDegrees(-75));
                stack.mulPose(Vector3f.YP.rotationDegrees(-15));
                stack.mulPose(Vector3f.ZP.rotationDegrees(0));

                //size / scale
                stack.scale(1.0f, 1.0f, 1.0f);

                Minecraft.getInstance().getItemRenderer().renderStatic(this.mainHand.getStack(), ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, packedLightIn, packedOverlayIn, stack, this.rtb);
                stack.popPose();
                bufferIn = rtb.getBuffer(RenderType.entitySmoothCutout(whTexture));
            }
        }
        if (bone.getName().equals("left_hand") || bone.getName().equals("left_arm")) {
            if (!bone.isHidden) {
                stack.pushPose();

                //position / translation
                stack.translate(-0.45D, 0.15D, -0.25D);

                //rotation
                stack.mulPose(Vector3f.XP.rotationDegrees(-75));
                stack.mulPose(Vector3f.YP.rotationDegrees(15));
                stack.mulPose(Vector3f.ZP.rotationDegrees(0));

                //size / scale
                stack.scale(1.0f, 1.0f, 1.0f);

                Minecraft.getInstance().getItemRenderer().renderStatic(this.offHand.getStack(), ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, packedLightIn, packedOverlayIn, stack, this.rtb);
                stack.popPose();
                bufferIn = rtb.getBuffer(RenderType.entitySmoothCutout(whTexture));
            }
        }
        super.renderRecursively(bone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    @Override
    protected float getDeathMaxRotation(AlphemEntity entity) {
        return 0.0F;
    }
}
