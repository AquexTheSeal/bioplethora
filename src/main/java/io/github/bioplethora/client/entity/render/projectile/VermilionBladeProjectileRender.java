package io.github.bioplethora.client.entity.render.projectile;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.client.entity.model.projectile.VermilionBladeProjectileModel;
import io.github.bioplethora.entity.projectile.VermilionBladeProjectileEntity;
import io.github.bioplethora.registry.BPItems;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class VermilionBladeProjectileRender<T extends Entity & IRendersAsItem> extends EntityRenderer<T> {

    private final net.minecraft.client.renderer.ItemRenderer itemRenderer;
    private final boolean fullBright;

    public VermilionBladeProjectileRender(EntityRendererManager p_i226035_1_, net.minecraft.client.renderer.ItemRenderer p_i226035_2_, float p_i226035_3_, boolean p_i226035_4_) {
        super(p_i226035_1_);
        this.itemRenderer = p_i226035_2_;
        this.fullBright = p_i226035_4_;
    }

    public VermilionBladeProjectileRender(EntityRendererManager p_i50957_1_, net.minecraft.client.renderer.ItemRenderer p_i50957_2_) {
        this(p_i50957_1_, p_i50957_2_, 1.0F, false);
    }

    protected int getBlockLightLevel(T pEntity, BlockPos pPos) {
        return this.fullBright ? 15 : super.getBlockLightLevel(pEntity, pPos);
    }

    public void render(T pEntity, float pEntityYaw, float pPartialTicks, MatrixStack pMatrixStack, IRenderTypeBuffer pBuffer, int pPackedLight) {
        if (pEntity.tickCount >= 2) {

            VermilionBladeProjectileEntity verm = (VermilionBladeProjectileEntity) pEntity;
            pMatrixStack.pushPose();
            RenderSystem.enableRescaleNormal();

            pMatrixStack.scale(2.5F * (verm.bladeSize / 2F), 2.5F * (verm.bladeSize / 2F), 2.5F * (verm.bladeSize / 2F));
            pMatrixStack.mulPose(Vector3f.YP.rotationDegrees(MathHelper.lerp(pPartialTicks, pEntity.yRotO, pEntity.yRot) - 90.0F));
            pMatrixStack.mulPose(Vector3f.ZP.rotationDegrees(MathHelper.lerp(pPartialTicks, pEntity.xRotO, pEntity.xRot) - 45.0F));

            Vector3f rotation = new Vector3f(1.0F, 1.0F, 0.0F);
            rotation.normalize();
            pMatrixStack.mulPose(rotation.rotationDegrees(180.0F));
            pMatrixStack.translate(-0.1D, -0.65D, 0.0D);
            this.itemRenderer.renderStatic(pEntity.getItem(), ItemCameraTransforms.TransformType.GROUND, pPackedLight, OverlayTexture.NO_OVERLAY, pMatrixStack, pBuffer);

            pMatrixStack.popPose();
            RenderSystem.disableRescaleNormal();
            super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
        }
    }

    public ResourceLocation getTextureLocation(Entity pEntity) {
        return AtlasTexture.LOCATION_BLOCKS;
    }
}