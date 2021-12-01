package io.github.bioplethora.client.entity.render.projectile;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.client.entity.model.projectile.BellophiteClusterModel;
import io.github.bioplethora.entity.AlphemEntity;
import io.github.bioplethora.entity.BellophgolemEntity;
import io.github.bioplethora.entity.projectile.BellophiteClusterEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

/*
* Credited author:
* AzureDoom [MCDoom mod]
*/

public class BellophiteClusterRender extends GeoProjectilesRenderer<BellophiteClusterEntity> {

    private static final RenderType BEAM = RenderType
            .entitySmoothCutout(new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/projectiles/bellophite_cluster.png"));

    public BellophiteClusterRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new BellophiteClusterModel());
    }

    @Override
    public RenderType getRenderType(BellophiteClusterEntity animatable, float partialTicks, MatrixStack stack, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }

    protected int getBlockLightLevel(BellophiteClusterEntity entityIn, BlockPos partialTicks) {
        return 15;
    }

    @Override
    public void renderEarly(BellophiteClusterEntity animatable, MatrixStack stackIn, float ticks, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
    }

    @Override
    public void renderRecursively(GeoBone bone, MatrixStack stack, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        super.renderRecursively(bone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    /*@Override
    public void render(GeoModel model, BellophiteClusterEntity animatable, float partialTicks, RenderType type, MatrixStack matrixStackIn, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        super.render(model, animatable, partialTicks, type, matrixStackIn, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        float f = getY(animatable, partialTicks);
    }

    @Override
    public void renderEarly(BellophiteClusterEntity animatable, MatrixStack stackIn, float ticks, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
        stackIn.scale(animatable.tickCount > 2 ? 1.0F : 0.0F, animatable.tickCount > 2 ? 1.0F : 0.0F, animatable.tickCount > 2 ? 1.0F : 0.0F);
    }

    public static float getY(BellophiteClusterEntity p_229051_0_, float p_229051_1_) {
        float f = (float) p_229051_0_.tickCount + p_229051_1_;
        float f1 = MathHelper.sin(f * 0.2F) / 2.0F + 0.5F;
        f1 = (f1 * f1 + f1) * 0.4F;
        return f1 - 1.4F;
    }*/
}