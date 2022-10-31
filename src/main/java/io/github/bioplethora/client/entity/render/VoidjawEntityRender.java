package io.github.bioplethora.client.entity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.bioplethora.client.entity.model.TrapjawEntityModel;
import io.github.bioplethora.client.entity.model.VoidjawEntityModel;
import io.github.bioplethora.client.entity.render.layer.EurydnEntityGlowLayer;
import io.github.bioplethora.client.entity.render.layer.VoidjawImpulseLayer;
import io.github.bioplethora.entity.creatures.TrapjawEntity;
import io.github.bioplethora.entity.creatures.VoidjawEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class VoidjawEntityRender extends GeoEntityRenderer<VoidjawEntity> {
    @OnlyIn(Dist.CLIENT)
    private static float alp = 1.0F;

    public VoidjawEntityRender(EntityRendererManager renderManager) {
        super(renderManager, new VoidjawEntityModel());
        this.addLayer(new VoidjawImpulseLayer(this));
        this.shadowRadius = 0.5F;
    }

    @Override
    public void renderEarly(VoidjawEntity animatable, MatrixStack stackIn, float ticks, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        float size = 0.5F;
        if (animatable.isBaby()) {
            stackIn.scale(size, size, size);
        }
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.getVehicle() == animatable) {
            alp = 0.15F;
        } else {
            alp = 1.0F;
        }
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
    }

    @Override
    public RenderType getRenderType(VoidjawEntity animatable, float partialTicks, MatrixStack stack, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }

    @Override
    public void renderRecursively(GeoBone bone, MatrixStack stack, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        super.renderRecursively(bone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alp);
    }
}
