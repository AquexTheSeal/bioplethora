package io.github.bioplethora.network.keybindings;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.network.BPNetwork;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Bioplethora.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class InputEvents {

    private static void onInput(Minecraft mc, int key, int action) {
        if (mc.screen == null && BPKeybinds.verticalMountUp.consumeClick()) {
            BPNetwork.CHANNEL.sendToServer(new VerticalMountUpPacket(key));
        }
        if (mc.screen == null && BPKeybinds.verticalMountDown.consumeClick()) {
            BPNetwork.CHANNEL.sendToServer(new VerticalMountDownPacket(key));
        }
    }

    @SubscribeEvent
    public static void onKeyPressed(InputEvent.KeyInputEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return;
        onInput(mc, event.getKey(), event.getAction());
    }

    @SubscribeEvent
    public static void onMouseClicked(InputEvent.MouseInputEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return;
        onInput(mc, event.getButton(), event.getAction());
    }
}
