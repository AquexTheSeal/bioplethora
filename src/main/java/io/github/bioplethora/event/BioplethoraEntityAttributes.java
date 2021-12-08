package io.github.bioplethora.event;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.*;
import io.github.bioplethora.registry.BioplethoraEntities;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Bioplethora.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BioplethoraEntityAttributes {
    @SubscribeEvent
    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {

        //Avifauna
        event.put(BioplethoraEntities.CREPHOXL.get(), CrephoxlEntity.setCustomAttributes().build());
        event.put(BioplethoraEntities.PEAGUIN.get(), PeaguinEntity.setCustomAttributes().build());

        //Fairy
        event.put(BioplethoraEntities.ALPHEM.get(), AlphemEntity.setCustomAttributes().build());
        event.put(BioplethoraEntities.BELLOPHGOLEM.get(), BellophgolemEntity.setCustomAttributes().build());

        //Reptilia
        event.put(BioplethoraEntities.NANDBRI.get(), NandbriEntity.setCustomAttributes().build());

        //maritime
        event.put(BioplethoraEntities.CUTTLEFISH.get(), CuttlefishEntity.setCustomAttributes().build());

        //elite undead
        event.put(BioplethoraEntities.GAUGALEM.get(), GaugalemEntity.setCustomAttributes().build());
    }
}
