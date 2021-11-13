package io.github.bioplethora.registry;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.AlphemEntity;
import io.github.bioplethora.entity.CrephoxlEntity;
import io.github.bioplethora.entity.NandbriEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Bioplethora.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityAttributesHandler {
    @SubscribeEvent
    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {

        //Avifauna
        event.put(BioplethoraEntities.CREPHOXL.get(), CrephoxlEntity.setCustomAttributes().build());

        //Fairy
        event.put(BioplethoraEntities.ALPHEM.get(), AlphemEntity.setCustomAttributes().build());

        //Reptilia
        event.put(BioplethoraEntities.NANDBRI.get(), NandbriEntity.setCustomAttributes().build());
    }
}
