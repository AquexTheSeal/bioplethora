package io.github.bioplethora.client.entity.render.projectile;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.client.entity.model.projectile.BellophiteClusterModel;
import io.github.bioplethora.entity.AlphemEntity;
import io.github.bioplethora.entity.projectile.BellophiteClusterEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class BellophiteClusterRender extends GeoProjectilesRenderer<BellophiteClusterEntity> {

    public BellophiteClusterRender(EntityRendererManager renderManager) {
        super(renderManager, new BellophiteClusterModel());
    }

    protected int getBlockLightLevel(BellophiteClusterEntity entityIn, BlockPos partialTicks) {
        return 15;
    }

    @Override
    public RenderType getRenderType(BellophiteClusterEntity animatable, float partialTicks, MatrixStack stack,
                                    IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }

    @Override
    public void renderEarly(BellophiteClusterEntity animatable, MatrixStack stackIn, float ticks,
                            IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, int packedOverlayIn,
                            float red, float green, float blue, float partialTicks) {
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn,
                red, green, blue, partialTicks);
        stackIn.scale(animatable.tickCount > 2 ? 1F : 0.0F, animatable.tickCount > 2 ? 1F : 0.0F,
                animatable.tickCount > 2 ? 1F : 0.0F);
    }
}
