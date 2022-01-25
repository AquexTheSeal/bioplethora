package io.github.bioplethora.event;

import io.github.bioplethora.Bioplethora;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Bioplethora.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCommonRegister {

    /*@SubscribeEvent
    public static void registerEggs(RegistryEvent.Register<EntityType<?>> event) {
        BioplethoraSpawnEggItem.registerEggs();
    }*/
}
