package io.github.bioplethora.client.entity.render.projectile;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.client.entity.model.projectile.VermilionBladeProjectileModel;
import io.github.bioplethora.entity.projectile.VermilionBladeProjectileEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class VermilionBladeProjectileRender extends GeoProjectilesRenderer<VermilionBladeProjectileEntity> {

    private static final RenderType BEAM = RenderType.entitySmoothCutout(new ResourceLocation(Bioplethora.MOD_ID, "textures/item/vermilion_blade.png"));

    public VermilionBladeProjectileRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new VermilionBladeProjectileModel());
    }

    @Override
    public RenderType getRenderType(VermilionBladeProjectileEntity animatable, float partialTicks, MatrixStack stack, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.eyes(getTextureLocation(animatable));
    }

    protected int getBlockLightLevel(VermilionBladeProjectileEntity entityIn, BlockPos partialTicks) {
        return 15;
    }

    @Override
    public void renderEarly(VermilionBladeProjectileEntity animatable, MatrixStack stackIn, float ticks, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        float f = 1;
        f = f * (float) animatable.bladeSize;
        stackIn.scale(f, f, f);
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
    }

    @Override
    public void renderRecursively(GeoBone bone, MatrixStack stack, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        super.renderRecursively(bone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}