package io.github.bioplethora.integration;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.registry.BioplethoraEntities;
import jeresources.compatibility.JERAPI;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class BPCompatJER {

    public static void register() {
        registerMobLoot();
    }

    private static void registerMobLoot() {

        World world = JERAPI.getInstance().getWorld();

        registerLoot(BioplethoraEntities.CREPHOXL.get().create(world), "loot_tables/entities/crephoxl.json");
        registerLoot(BioplethoraEntities.NANDBRI.get().create(world), "loot_tables/entities/nandbri.json");
        registerLoot(BioplethoraEntities.CUTTLEFISH.get().create(world), "loot_tables/entities/cuttlefish.json");
        registerLoot(BioplethoraEntities.BELLOPHGOLEM.get().create(world), "loot_tables/entities/bellophgolem.json");
        registerLoot(BioplethoraEntities.ALTYRUS.get().create(world), "loot_tables/entities/altyrus.json");
        registerLoot(BioplethoraEntities.PEAGUIN.get().create(world), "loot_tables/entities/alphanum_mausoleum.json");
    }

    public static void registerLoot(LivingEntity entity, String tableFile) {
        JERAPI.getInstance().getMobRegistry().register(entity, new ResourceLocation(Bioplethora.MOD_ID, tableFile));
    }
}
