package io.github.bioplethora.event.helper;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.blocks.api.mixin.IPlayerEntityMixin;
import io.github.bioplethora.config.BPConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.TickEvent;

public class RenderEventHelper {

    public static boolean reverseCurseAlpha;
    public static double curseAlpha;

    public static void onRenderingPlayer(RenderPlayerEvent event) {
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

    public static void onCameraSetup(EntityViewRenderEvent.CameraSetup event) {
        PlayerEntity player = Minecraft.getInstance().player;
        IPlayerEntityMixin playermx = (IPlayerEntityMixin) player;
        float delta = Minecraft.getInstance().getFrameTime();
        float ticksExistedDelta = player.tickCount + delta;
        float shakeAmplitude;
        if (playermx.getScreenShaking() > 0 && !Minecraft.getInstance().isPaused() && player.level.isClientSide()) {
            shakeAmplitude = 0.05F;
            event.setPitch((float) (event.getPitch() + shakeAmplitude * Math.cos(ticksExistedDelta * 3 + 2) * 25));
            event.setYaw((float) (event.getYaw() + shakeAmplitude * Math.cos(ticksExistedDelta * 5 + 1) * 25));
            event.setRoll((float) (event.getRoll() + shakeAmplitude * Math.cos(ticksExistedDelta * 4) * 25));
            playermx.setScreenShaking(playermx.getScreenShaking() - 1);
        }
    }

    public static void onFogDensity(EntityViewRenderEvent.FogDensity event) {
        Minecraft mc = Minecraft.getInstance();
        BlockPos blockpos = Minecraft.getInstance().getCameraEntity().blockPosition();
        if (mc.level.registryAccess().registryOrThrow(Registry.BIOME_REGISTRY).getKey(mc.level.getBiome(blockpos)).toString().equals("minecraft:small_end_islands")) {
            event.setDensity(0.2F);
            event.setCanceled(true);
        } else if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.clientLevel.dimension() == World.END && Minecraft.getInstance().player.isUnderWater()) {
            event.setDensity(0.03F);
            event.setCanceled(true);
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
