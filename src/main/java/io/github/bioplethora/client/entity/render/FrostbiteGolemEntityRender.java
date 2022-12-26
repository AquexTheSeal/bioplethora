package io.github.bioplethora.client.entity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.client.entity.model.FrostbiteGolemEntityModel;
import io.github.bioplethora.client.entity.render.layer.FrostbiteGolemEntityGlowLayer;
import io.github.bioplethora.entity.creatures.FrostbiteGolemEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class FrostbiteGolemEntityRender extends GeoEntityRenderer<FrostbiteGolemEntity> {

    public FrostbiteGolemEntityRender(EntityRendererManager renderManager) {
        super(renderManager, new FrostbiteGolemEntityModel());
        this.addLayer(new FrostbiteGolemEntityGlowLayer(this));
        this.shadowRadius = 2.2F;
    }

    @Override
    public void renderEarly(FrostbiteGolemEntity animatable, MatrixStack stackIn, float ticks, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
    }

    @Override
    public ResourceLocation getTextureLocation(FrostbiteGolemEntity entity) {
        if (((LivingEntity) entity).getHealth() <= 100) {
            return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/frostbite_golem_cracked.png");
        } else {
            return new ResourceLocation(Bioplethora.MOD_ID, "textures/entity/frostbite_golem.png");
        }
    }

    @Override
    public RenderType getRenderType(FrostbiteGolemEntity animatable, float partialTicks, MatrixStack stack, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }

    @Override
    public void renderRecursively(GeoBone bone, MatrixStack stack, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        super.renderRecursively(bone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    @Override
    protected float getDeathMaxRotation(FrostbiteGolemEntity entity) {
        return 0.0F;
    }
}
