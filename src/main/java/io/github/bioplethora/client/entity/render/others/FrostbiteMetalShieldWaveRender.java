package io.github.bioplethora.client.entity.render.others;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.client.entity.model.others.FrostbiteMetalShieldWaveModel;
import io.github.bioplethora.entity.others.FrostbiteMetalShieldWaveEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class FrostbiteMetalShieldWaveRender extends GeoProjectilesRenderer<FrostbiteMetalShieldWaveEntity> {

    private static final RenderType BEAM = RenderType.entitySmoothCutout(new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/others/altyrus_summoning.png"));

    public FrostbiteMetalShieldWaveRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new FrostbiteMetalShieldWaveModel());
    }

    @Override
    public RenderType getRenderType(FrostbiteMetalShieldWaveEntity animatable, float partialTicks, MatrixStack stack, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.eyes(getTextureLocation(animatable));
    }

    protected int getBlockLightLevel(FrostbiteMetalShieldWaveEntity entityIn, BlockPos partialTicks) {
        return 15;
    }

    @Override
    public void renderEarly(FrostbiteMetalShieldWaveEntity animatable, MatrixStack stackIn, float ticks, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
    }

    @Override
    public void renderRecursively(GeoBone bone, MatrixStack stack, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        super.renderRecursively(bone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}
