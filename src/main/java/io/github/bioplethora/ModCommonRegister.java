package io.github.bioplethora;

import io.github.bioplethora.item.BioplethoraSpawnEggItem;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.RegistryEvent;

public class ModCommonRegister {

    public static void registerEggs(RegistryEvent.Register<EntityType<?>> event) {
        BioplethoraSpawnEggItem.registerEggs();
    }
}
