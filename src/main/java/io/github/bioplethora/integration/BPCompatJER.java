package io.github.bioplethora.integration;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.creatures.*;
import io.github.bioplethora.registry.BioplethoraEntities;
import jeresources.compatibility.JERAPI;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class BPCompatJER {

    public static void register() {
        registerMobLoot();
    }

    private static void registerMobLoot() {

        World world = JERAPI.getInstance().getWorld();

        CrephoxlEntity crephoxl = BioplethoraEntities.CREPHOXL.get().create(world);
        JERAPI.getInstance().getMobRegistry().register(crephoxl, new ResourceLocation(Bioplethora.MOD_ID, "loot_tables/entities/crephoxl.json"));
        NandbriEntity nandbri = BioplethoraEntities.NANDBRI.get().create(world);
        JERAPI.getInstance().getMobRegistry().register(nandbri, new ResourceLocation(Bioplethora.MOD_ID, "loot_tables/entities/nandbri.json"));
        CuttlefishEntity cuttlefish = BioplethoraEntities.CUTTLEFISH.get().create(world);
        JERAPI.getInstance().getMobRegistry().register(cuttlefish, new ResourceLocation(Bioplethora.MOD_ID, "loot_tables/entities/cuttlefish.json"));
        BellophgolemEntity bellophgolem = BioplethoraEntities.BELLOPHGOLEM.get().create(world);
        JERAPI.getInstance().getMobRegistry().register(bellophgolem, new ResourceLocation(Bioplethora.MOD_ID, "loot_tables/entities/bellophgolem.json"));
        AltyrusEntity altyrus = BioplethoraEntities.ALTYRUS.get().create(world);
        JERAPI.getInstance().getMobRegistry().register(altyrus, new ResourceLocation(Bioplethora.MOD_ID, "loot_tables/entities/altyrus.json"));
        PeaguinEntity peaguin = BioplethoraEntities.PEAGUIN.get().create(world);
        JERAPI.getInstance().getMobRegistry().register(peaguin, new ResourceLocation(Bioplethora.MOD_ID, "loot_tables/entities/peaguin.json"));
    }
}
