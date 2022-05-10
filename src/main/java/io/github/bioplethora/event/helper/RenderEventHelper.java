package io.github.bioplethora.event.helper;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.api.mixin.IPlayerEntityMixin;
import io.github.bioplethora.config.BPConfig;
import io.github.bioplethora.item.weapons.InfernalQuarterstaffItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.TickEvent;

public class RenderEventHelper {

    public static boolean reverseCurseAlpha;
    public static double curseAlpha;

    public static void onRenderingPlayer(RenderPlayerEvent event) {

        if (event.getPlayer().getMainHandItem().getItem() instanceof InfernalQuarterstaffItem) {
            renderInfernalQuarterstaff(event);
        }
    }

    public static void renderInfernalQuarterstaff(RenderPlayerEvent event) {
        ClientPlayerEntity player = ((ClientPlayerEntity) event.getPlayer());
        MatrixStack stack = event.getMatrixStack();
        PlayerModel<AbstractClientPlayerEntity> model = event.getRenderer().getModel();

        /*
        model.rightArm.xRot = 55F; model.rightArm.yRot = 22.5F;

        model.leftArm.setPos(-7F, 11F, -4F);
        model.leftArm.xRot = -60F; model.leftArm.yRot = -17F; model.leftArm.zRot = -10F;

        model.body.setPos(-4F, 11F, -6F);
        model.body.xRot = -22.5F;

        model.head.setPos(-4, 23, -10);

         */

        //event.getRenderer().render(player, player.yRot, event.getPartialRenderTick(), stack, event.getBuffers(), event.getLight());
    }

    public static void onRenderingOverlay(RenderGameOverlayEvent.Pre event) {

        int getWidth = event.getWindow().getGuiScaledWidth(), getHeight = event.getWindow().getGuiScaledHeight();
        Minecraft mc = Minecraft.getInstance();
        PlayerEntity player = mc.player;
        IPlayerEntityMixin mxPlayer = (IPlayerEntityMixin) player;

        if (mxPlayer.hasAlphanumCurse()) {
            if (EntityPredicates.NO_CREATIVE_OR_SPECTATOR.test(player)) {
                renderAlphanumCurse(getWidth, getHeight);
            }
        }
    }

    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (!reverseCurseAlpha) { curseAlpha += 0.001; if (curseAlpha >= 0.10) { reverseCurseAlpha = true; }
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
