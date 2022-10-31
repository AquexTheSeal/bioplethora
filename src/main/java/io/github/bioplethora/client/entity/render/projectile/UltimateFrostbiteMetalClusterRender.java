package io.github.bioplethora.client.entity.render.projectile;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.bioplethora.client.entity.model.projectile.UltimateFrostbiteMetalClusterModel;
import io.github.bioplethora.entity.projectile.UltimateFrostbiteMetalClusterEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;


public class UltimateFrostbiteMetalClusterRender extends GeoProjectilesRenderer<UltimateFrostbiteMetalClusterEntity> {

    public UltimateFrostbiteMetalClusterRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new UltimateFrostbiteMetalClusterModel());
    }

    @Override
    public RenderType getRenderType(UltimateFrostbiteMetalClusterEntity animatable, float partialTicks, MatrixStack stack, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.eyes(getTextureLocation(animatable));
    }

    protected int getBlockLightLevel(UltimateFrostbiteMetalClusterEntity entityIn, BlockPos partialTicks) {
        return 15;
    }

    @Override
    public void renderEarly(UltimateFrostbiteMetalClusterEntity animatable, MatrixStack stackIn, float ticks, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
    }

    @Override
    public void renderRecursively(GeoBone bone, MatrixStack stack, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        super.renderRecursively(bone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}