package io.github.bioplethora.registry;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.AlphemEntity;
import io.github.bioplethora.entity.BellophgolemEntity;
import io.github.bioplethora.entity.CrephoxlEntity;
import io.github.bioplethora.entity.NandbriEntity;
import io.github.bioplethora.entity.projectile.BellophiteClusterEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BioplethoraEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, Bioplethora.MOD_ID);

    //Avifauna
    public static final RegistryObject<EntityType<CrephoxlEntity>> CREPHOXL = ENTITIES.register("crephoxl", () -> EntityType.Builder.of(CrephoxlEntity::new, EntityClassification.MONSTER).sized(3.5f, 5f).build(new ResourceLocation(Bioplethora.MOD_ID, "crephoxl").toString()));

    //Fairy
    public static final RegistryObject<EntityType<AlphemEntity>> ALPHEM = ENTITIES.register("alphem", () -> EntityType.Builder.of(AlphemEntity::new, EntityClassification.MONSTER).sized(1.2f, 1.4f).build(new ResourceLocation(Bioplethora.MOD_ID, "alphem").toString()));
    public static final RegistryObject<EntityType<BellophgolemEntity>> BELLOPHGOLEM = ENTITIES.register("bellophgolem", () -> EntityType.Builder.of(BellophgolemEntity::new, EntityClassification.MONSTER).sized(3.5f, 4.75f).build(new ResourceLocation(Bioplethora.MOD_ID, "bellophgolem").toString()));

    //Reptilia
    public static final RegistryObject<EntityType<NandbriEntity>> NANDBRI = ENTITIES.register("nandbri", () -> EntityType.Builder.of(NandbriEntity::new, EntityClassification.MONSTER).sized(2.6f, 1.15f).build(new ResourceLocation(Bioplethora.MOD_ID, "nandbri").toString()));

    //others ---
    //Projectiles
    public static final RegistryObject<EntityType<BellophiteClusterEntity>> BELLOPHITE_CLUSTER= ENTITIES.register("bellophite_cluster", () -> EntityType.Builder.<BellophiteClusterEntity>of(BellophiteClusterEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20)
            .build(new ResourceLocation(Bioplethora.MOD_ID, "bellophite_cluster").toString()));
}
