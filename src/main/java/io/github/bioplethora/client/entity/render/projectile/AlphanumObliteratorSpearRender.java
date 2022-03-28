package io.github.bioplethora.client.entity.render.projectile;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.client.entity.model.projectile.AlphanumObliteratorSpearModel;
import io.github.bioplethora.entity.projectile.AlphanumObliteratorSpearEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class AlphanumObliteratorSpearRender extends GeoProjectilesRenderer<AlphanumObliteratorSpearEntity> {

    private static final RenderType BEAM = RenderType.entitySmoothCutout(new ResourceLocation(Bioplethora.MOD_ID, "textures/projectiles/alphanum_obliterator_spear.png"));

    public AlphanumObliteratorSpearRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new AlphanumObliteratorSpearModel());
    }

    @Override
    public RenderType getRenderType(AlphanumObliteratorSpearEntity animatable, float partialTicks, MatrixStack stack, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return BEAM;
    }

    protected int getBlockLightLevel(AlphanumObliteratorSpearEntity entityIn, BlockPos partialTicks) {
        return 15;
    }

    @Override
    public void renderEarly(AlphanumObliteratorSpearEntity animatable, MatrixStack stackIn, float ticks, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        float size = 1.2F;
        stackIn.scale(size, size, size);
        stackIn.mulPose(Vector3f.YP.rotationDegrees(-90));
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
    }

    @Override
    public void renderRecursively(GeoBone bone, MatrixStack stack, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        super.renderRecursively(bone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}