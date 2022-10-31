package io.github.bioplethora.client.entity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.client.entity.model.ShachathEntityModel;
import io.github.bioplethora.client.entity.render.layer.ShachathEntityGlowLayer;
import io.github.bioplethora.entity.creatures.ShachathEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class ShachathEntityRender extends GeoEntityRenderer<ShachathEntity> {

    public ShachathEntityRender(EntityRendererManager renderManager) {
        super(renderManager, new ShachathEntityModel());
        this.addLayer(new ShachathEntityGlowLayer(this));
        this.shadowRadius = 0.8F;
    }

    @Override
    public void renderEarly(ShachathEntity animatable, MatrixStack stackIn, float ticks, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
    }

    @Override
    public RenderType getRenderType(ShachathEntity animatable, float partialTicks, MatrixStack stack, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }

    @Override
    public ResourceLocation getTextureLocation(ShachathEntity entity) {
        if (entity.isClone()) {
            return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/shachath_clone.png");
        } else {
            return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/shachath.png");
        }
    }

    @Override
    public void renderRecursively(GeoBone bone, MatrixStack stack, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        IVertexBuilder smCutout = rtb.getBuffer(RenderType.entitySmoothCutout(whTexture));

        if (bone.getName().equals("RightHand")) {
            if (!bone.isHidden) {
                stack.pushPose();
                //position / translation
                stack.translate(0.3D, 0.75D, -0.05D);
                //rotation
                stack.mulPose(Vector3f.XP.rotationDegrees(-75));
                stack.mulPose(Vector3f.YP.rotationDegrees(0));
                stack.mulPose(Vector3f.ZP.rotationDegrees(0));
                //size / scale
                stack.scale(1.0f, 1.0f, 1.0f);
                Minecraft.getInstance().getItemRenderer().renderStatic(this.mainHand.getStack(), ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, packedLightIn, packedOverlayIn, stack, this.rtb);
                stack.popPose();
                bufferIn = smCutout;
            }
        }
        if (bone.getName().equals("LeftHand")) {
            if (!bone.isHidden) {
                stack.pushPose();
                //position / translation
                stack.translate(-0.3D, 0.75D, -0.05D);
                //rotation
                stack.mulPose(Vector3f.XP.rotationDegrees(-75));
                stack.mulPose(Vector3f.YP.rotationDegrees(0));
                stack.mulPose(Vector3f.ZP.rotationDegrees(0));
                //size / scale
                stack.scale(1.0f, 1.0f, 1.0f);
                Minecraft.getInstance().getItemRenderer().renderStatic(this.offHand.getStack(), ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, packedLightIn, packedOverlayIn, stack, this.rtb);
                stack.popPose();
                bufferIn = smCutout;
            }
        }

        super.renderRecursively(bone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}
