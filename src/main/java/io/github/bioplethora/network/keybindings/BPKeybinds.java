package io.github.bioplethora.network.keybindings;

import io.github.bioplethora.Bioplethora;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;

@OnlyIn(Dist.CLIENT)
public class BPKeybinds {
    public static KeyBinding verticalMountUp;
    public static KeyBinding verticalMountDown;

    public static void register(final FMLClientSetupEvent event) {
        /*
        verticalMountUp = create("vertical_up", GLFW.GLFW_KEY_SPACE);
        ClientRegistry.registerKeyBinding(verticalMountUp);

        verticalMountDown = create("vertical_down", GLFW.GLFW_KEY_LEFT_CONTROL);
        ClientRegistry.registerKeyBinding(verticalMountDown);
        */
    }

    private static KeyBinding create(String name,  int key) {
        return new KeyBinding("key." + Bioplethora.MOD_ID + "." + name, key, "key.category." + Bioplethora.MOD_ID);
    }
}
