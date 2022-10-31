package io.github.bioplethora.integration;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.registry.BPEntities;
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
        registerLoot(BPEntities.CREPHOXL.get().create(world), "loot_tables/entities/crephoxl.json");
        registerLoot(BPEntities.NANDBRI.get().create(world), "loot_tables/entities/nandbri.json");
        registerLoot(BPEntities.CUTTLEFISH.get().create(world), "loot_tables/entities/cuttlefish.json");
        registerLoot(BPEntities.FROSTBITE_GOLEM.get().create(world), "loot_tables/entities/frostbite_golem.json");
        registerLoot(BPEntities.ALTYRUS.get().create(world), "loot_tables/entities/altyrus.json");
        registerLoot(BPEntities.PEAGUIN.get().create(world), "loot_tables/entities/alphanum_mausoleum.json");
    }

    public static void registerLoot(LivingEntity entity, String tableFile) {
        JERAPI.getInstance().getMobRegistry().register(entity, new ResourceLocation(Bioplethora.MOD_ID, tableFile));
    }
}
