package io.github.bioplethora.registry;

import io.github.bioplethora.Bioplethora;
import net.minecraft.block.WoodType;
import net.minecraft.client.renderer.Atlases;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Bioplethora.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BPWoodTypes {
    public static final WoodType PETRAWOOD = WoodType.create("petrawood");

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void registerWoodType(FMLClientSetupEvent event) {
        Atlases.addWoodType(PETRAWOOD);
    }
}
