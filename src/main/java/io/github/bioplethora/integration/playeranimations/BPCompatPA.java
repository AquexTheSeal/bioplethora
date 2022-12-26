package io.github.bioplethora.integration.playeranimations;

import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.api.layered.modifier.AbstractFadeModifier;
import dev.kosmx.playerAnim.api.layered.modifier.SpeedModifier;
import dev.kosmx.playerAnim.core.util.Ease;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationFactory;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import dev.kosmx.playerAnim.mixin.BipedEntityModelMixin;
import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.item.weapons.InfernalQuarterstaffItem;
import io.github.bioplethora.registry.BPItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.logging.Logger;

@Mod.EventBusSubscriber(modid = Bioplethora.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class BPCompatPA {

    @SubscribeEvent
    public static void itemHoldAnimationHandler(TickEvent.PlayerTickEvent event) {
        if (event.side.isClient()) {
            /**
            ModifierLayer<IAnimation> animation = (ModifierLayer<IAnimation>) PlayerAnimationAccess.getPlayerAssociatedData((AbstractClientPlayerEntity) event.player).get(new ResourceLocation(Bioplethora.MOD_ID, "animation"));

            Bioplethora.LOGGER.debug(String.valueOf(PlayerAnimationRegistry.getAnimations()));

            if (event.player.getMainHandItem().getItem() instanceof InfernalQuarterstaffItem) {
                if (event.player.getDeltaMovement().x() == 0 || event.player.getDeltaMovement().y() == 0 || event.player.getDeltaMovement().z() == 0) {
                    addAnim(animation, "animation.model.iq_right_idle");
                } else {
                    addAnim(animation, "animation.model.iq_or_side_run");
                }
            }
             **/
        }
    }

    public static void addAnim(ModifierLayer<IAnimation> layer, String id) {
        KeyframeAnimationPlayer animation = new KeyframeAnimationPlayer(PlayerAnimationRegistry.getAnimation(new ResourceLocation(Bioplethora.MOD_ID, id)));
        boolean stopFlag = true;
        if (layer != null) {
            if (layer.getAnimation() instanceof KeyframeAnimationPlayer) {
                stopFlag = ((KeyframeAnimationPlayer) layer.getAnimation()).getTick() >= ((KeyframeAnimationPlayer) layer.getAnimation()).getStopTick() - 1;
            }
            if (stopFlag) {
                layer.setAnimation(animation);
            }
        }
    }
}
