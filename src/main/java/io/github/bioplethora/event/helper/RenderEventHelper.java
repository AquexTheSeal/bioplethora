package io.github.bioplethora.event.helper;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import io.github.bioplethora.BPConfig;
import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.helpers.mixin.IPlayerEntityMixin;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.TickEvent;

public class RenderEventHelper {

    public static boolean reverseCurseAlpha;
    public static double curseAlpha;

    public static void onRenderingPlayer(RenderPlayerEvent.Pre event) {
        Minecraft mc = Minecraft.getInstance();
        PlayerEntity player = event.getPlayer();
        Vector3d lookVec = player.getViewVector(1.0F);

        event.getPlayer().getMainHandItem();
        MatrixStack stack = event.getMatrixStack();
        stack.pushPose();
        stack.mulPose(Vector3f.XP.rotationDegrees(0));
        stack.mulPose(Vector3f.YP.rotationDegrees(0));
        stack.mulPose(Vector3f.ZP.rotationDegrees(0));

        stack.translate(player.getX() + lookVec.x() * 4, 15, player.getZ() + lookVec.z() * 4);
        IBakedModel model = mc.getItemRenderer().getModel(event.getPlayer().getMainHandItem(), null, null);
        mc.getItemRenderer().render(event.getPlayer().getMainHandItem(), ItemCameraTransforms.TransformType.GROUND, true, stack, event.getBuffers(), event.getLight(), 1, model);
        stack.popPose();

        MatrixStack stack1 = event.getMatrixStack();
        stack1.pushPose();
        stack1.mulPose(Vector3f.XP.rotationDegrees(0));
        stack1.mulPose(Vector3f.YP.rotationDegrees(0));
        stack1.mulPose(Vector3f.ZP.rotationDegrees(0));

        stack1.translate(player.getX() + lookVec.x() * 4, 15, player.getZ() + lookVec.z() * 4);
        IBakedModel model1 = mc.getItemRenderer().getModel(event.getPlayer().getMainHandItem(), null, null);
        mc.getItemRenderer().render(event.getPlayer().getMainHandItem(), ItemCameraTransforms.TransformType.GROUND, true, stack1, event.getBuffers(), event.getLight(), 1, model1);
        stack1.popPose();
    }

    public static void onRenderingOverlay(RenderGameOverlayEvent.Pre event) {

        int getWidth = event.getWindow().getGuiScaledWidth(), getHeight = event.getWindow().getGuiScaledHeight();
        Minecraft mc = Minecraft.getInstance();
        PlayerEntity player = mc.player;
        IPlayerEntityMixin mxPlayer = (IPlayerEntityMixin) player;

        if (mxPlayer.hasAlphanumCurse()) {
            renderAlphanumCurse(getWidth, getHeight);
        }
    }

    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (!reverseCurseAlpha) { curseAlpha += 0.001; if (curseAlpha >= 0.25) { reverseCurseAlpha = true; }
        } else { curseAlpha -= 0.001; if (curseAlpha <= 0) { reverseCurseAlpha = false; }}
    }


    protected static void renderAlphanumCurse(double width, double height) {
        if (BPConfig.COMMON.alphemCurseOverlay.get()) {
            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.enableBlend();
            RenderSystem.blendFunc(770, 771);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, (float) curseAlpha);
            Minecraft.getInstance().getTextureManager().bind(new ResourceLocation(Bioplethora.MOD_ID, "textures/misc/alphanum_curse.png"));
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuilder();
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
            bufferbuilder.vertex(0.0D, height, -90.0D).uv(0.0F, 1.0F).endVertex();
            bufferbuilder.vertex(width, height, -90.0D).uv(1.0F, 1.0F).endVertex();
            bufferbuilder.vertex(width, 0.0D, -90.0D).uv(1.0F, 0.0F).endVertex();
            bufferbuilder.vertex(0.0D, 0.0D, -90.0D).uv(0.0F, 0.0F).endVertex();
            tessellator.end();
            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();
        }
    }
}
