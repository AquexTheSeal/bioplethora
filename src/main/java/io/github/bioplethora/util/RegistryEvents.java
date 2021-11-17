package io.github.bioplethora.util;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.items.BioplethoraSpawnEggItem;

import net.minecraft.entity.EntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Bioplethora.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistryEvents {
    /*@SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onEntitiesRegistry(RegistryEvent.Register<EntityType<?>> event) {
        BioplethoraSpawnEggItem.registerEggs();
    }*/
}