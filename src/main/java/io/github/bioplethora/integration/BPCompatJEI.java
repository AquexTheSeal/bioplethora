package io.github.bioplethora.integration;

import io.github.bioplethora.Bioplethora;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import net.minecraft.util.ResourceLocation;

@JeiPlugin
public class BPCompatJEI implements IModPlugin {
    public static final ResourceLocation BP_MOD = new ResourceLocation(Bioplethora.MOD_ID, "jei");

    public ResourceLocation getPluginUid() {
        return BP_MOD;
    }
}
