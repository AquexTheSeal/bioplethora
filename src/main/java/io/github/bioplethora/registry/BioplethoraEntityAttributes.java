package io.github.bioplethora.registry;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.creatures.*;
import io.github.bioplethora.entity.others.PrimordialRingEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Bioplethora.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BioplethoraEntityAttributes {
    @SubscribeEvent
    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {

        //Ecoharmless
        event.put(BioplethoraEntities.CREPHOXL.get(), CrephoxlEntity.setCustomAttributes().build());
        event.put(BioplethoraEntities.PEAGUIN.get(), PeaguinEntity.setCustomAttributes().build());

        //Plethoneutral
        event.put(BioplethoraEntities.ALPHEM.get(), AlphemEntity.setCustomAttributes().build());
        event.put(BioplethoraEntities.BELLOPHGOLEM.get(), BellophgolemEntity.setCustomAttributes().build());
        event.put(BioplethoraEntities.ALTYRUS.get(), AltyrusEntity.setCustomAttributes().build());
        event.put(BioplethoraEntities.DWARF_MOSSADILE.get(), DwarfMossadileEntity.setCustomAttributes().build());

        //Dangerum
        event.put(BioplethoraEntities.NANDBRI.get(), NandbriEntity.setCustomAttributes().build());
        event.put(BioplethoraEntities.HELIOBLADE.get(), HeliobladeEntity.setCustomAttributes().build());

        //Hellsent
        event.put(BioplethoraEntities.CUTTLEFISH.get(), CuttlefishEntity.setCustomAttributes().build());

        //Elderia
        event.put(BioplethoraEntities.GAUGALEM.get(), GaugalemEntity.setCustomAttributes().build());
        event.put(BioplethoraEntities.MYLIOTHAN.get(), MyliothanEntity.setCustomAttributes().build());

        //Others
        event.put(BioplethoraEntities.PRIMORDIAL_RING.get(), PrimordialRingEntity.setCustomAttributes().build());
    }
}
