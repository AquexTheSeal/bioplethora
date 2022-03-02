package io.github.bioplethora.event;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.item.BioplethoraSpawnEggItem;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Bioplethora.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCommonRegister {

    @SubscribeEvent
    public static void onRegisterEntities(final RegistryEvent.Register<EntityType<?>> event) {
        BioplethoraSpawnEggItem.initUnaddedEggs();
    }
}
