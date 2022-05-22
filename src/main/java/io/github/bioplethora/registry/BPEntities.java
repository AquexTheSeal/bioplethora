package io.github.bioplethora.registry;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.BPBoatEntity;
import io.github.bioplethora.entity.creatures.*;
import io.github.bioplethora.entity.others.*;
import io.github.bioplethora.entity.projectile.*;
import io.github.bioplethora.enums.BPEffectTypes;
import io.github.bioplethora.enums.BPGrylynenTier;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BPEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, Bioplethora.MOD_ID);

    //Ecoharmless
    public static final RegistryObject<EntityType<CuttlefishEntity>> CUTTLEFISH = ENTITIES.register("cuttlefish", () -> EntityType.Builder.of(CuttlefishEntity::new, EntityClassification.WATER_CREATURE).sized(0.75f, 0.6f).build(new ResourceLocation(Bioplethora.MOD_ID, "cuttlefish").toString()));
    public static final RegistryObject<EntityType<OnofishEntity>> ONOFISH = ENTITIES.register("onofish", () -> EntityType.Builder.of(OnofishEntity::new, EntityClassification.CREATURE).sized(0.75f, 0.85f).build(new ResourceLocation(Bioplethora.MOD_ID, "onofish").toString()));

    public static final RegistryObject<EntityType<EurydnEntity>> SOUL_EURYDN = ENTITIES.register("soul_eurydn", () -> EntityType.Builder.of((EntityType.IFactory<EurydnEntity>) (type, world) ->
            new EurydnEntity(type, world, EurydnEntity.Variant.SOUL), EntityClassification.CREATURE).sized(1.8f, 1.1f).build(new ResourceLocation(Bioplethora.MOD_ID, "oul_eurydn").toString()));
    public static final RegistryObject<EntityType<EurydnEntity>> FIERY_EURYDN = ENTITIES.register("fiery_eurydn", () -> EntityType.Builder.of((EntityType.IFactory<EurydnEntity>) (type, world) ->
            new EurydnEntity(type, world, EurydnEntity.Variant.FIERY), EntityClassification.CREATURE).sized(1.8f, 1.1f).build(new ResourceLocation(Bioplethora.MOD_ID, "fiery_eurydn").toString()));

    //Plethoneutral
    public static final RegistryObject<EntityType<PeaguinEntity>> PEAGUIN = ENTITIES.register("peaguin", () -> EntityType.Builder.of(PeaguinEntity::new, EntityClassification.WATER_CREATURE).sized(1.2f, 1.4f).build(new ResourceLocation(Bioplethora.MOD_ID, "peaguin").toString()));
    public static final RegistryObject<EntityType<NandbriEntity>> NANDBRI = ENTITIES.register("nandbri", () -> EntityType.Builder.of(NandbriEntity::new, EntityClassification.MONSTER).sized(2.6f, 1.15f).build(new ResourceLocation(Bioplethora.MOD_ID, "nandbri").toString()));
    public static final RegistryObject<EntityType<CavernFleignarEntity>> CAVERN_FLEIGNAR = ENTITIES.register("cavern_fleignar", () -> EntityType.Builder.of(CavernFleignarEntity::new, EntityClassification.MONSTER).sized(0.8f, 4.5f).build(new ResourceLocation(Bioplethora.MOD_ID, "cavern_fleignar").toString()));

    //Dangerum
    public static final RegistryObject<EntityType<AlphemEntity>> ALPHEM = ENTITIES.register("alphem", () -> EntityType.Builder.of(AlphemEntity::new, EntityClassification.CREATURE).sized(1.2f, 1.4f).build(new ResourceLocation(Bioplethora.MOD_ID, "alphem").toString()));
    public static final RegistryObject<EntityType<GaugalemEntity>> GAUGALEM = ENTITIES.register("gaugalem", () -> EntityType.Builder.of(GaugalemEntity::new, EntityClassification.MONSTER).sized(1.2f, 4.0f).build(new ResourceLocation(Bioplethora.MOD_ID, "gaugalem").toString()));
    public static final RegistryObject<EntityType<DwarfMossadileEntity>> DWARF_MOSSADILE = ENTITIES.register("dwarf_mossadile", () -> EntityType.Builder.of(DwarfMossadileEntity::new, EntityClassification.MONSTER).sized(1.4f, 0.5f).build(new ResourceLocation(Bioplethora.MOD_ID, "dwarf_mossadile").toString()));
    public static final RegistryObject<EntityType<TrapjawEntity>> TRAPJAW = ENTITIES.register("trapjaw", () -> EntityType.Builder.of(TrapjawEntity::new, EntityClassification.MONSTER).sized(2.2f, 1.5f).build(new ResourceLocation(Bioplethora.MOD_ID, "trapjaw").toString()));
    public static final RegistryObject<EntityType<TerraithEntity>> TERRAITH = ENTITIES.register("terraith", () -> EntityType.Builder.of(TerraithEntity::new, EntityClassification.MONSTER).sized(1.5F, 1.8F).build(new ResourceLocation(Bioplethora.MOD_ID, "terraith").toString()));
    public static final RegistryObject<EntityType<MyuthineEntity>> MYUTHINE = ENTITIES.register("myuthine", () -> EntityType.Builder.of(MyuthineEntity::new, EntityClassification.MONSTER).sized(1.0F, 3.2F).build(new ResourceLocation(Bioplethora.MOD_ID, "myuthine").toString()));

    public static final RegistryObject<EntityType<GrylynenEntity>> WOODEN_GRYLYNEN = ENTITIES.register("wooden_grylynen", () -> EntityType.Builder.of((EntityType.IFactory<GrylynenEntity>) (type, world) ->
                    new GrylynenEntity(type, world, BPGrylynenTier.WOODEN), EntityClassification.MONSTER).sized(1f, 1.8f).build(new ResourceLocation(Bioplethora.MOD_ID, "wooden_grylynen").toString()));
    public static final RegistryObject<EntityType<GrylynenEntity>> STONE_GRYLYNEN = ENTITIES.register("stone_grylynen", () -> EntityType.Builder.of((EntityType.IFactory<GrylynenEntity>) (type, world) ->
                    new GrylynenEntity(type, world, BPGrylynenTier.STONE), EntityClassification.MONSTER).sized(1f, 1.8f).build(new ResourceLocation(Bioplethora.MOD_ID, "stone_grylynen").toString()));
    public static final RegistryObject<EntityType<GrylynenEntity>> GOLDEN_GRYLYNEN = ENTITIES.register("golden_grylynen", () -> EntityType.Builder.of((EntityType.IFactory<GrylynenEntity>) (type, world) ->
            new GrylynenEntity(type, world, BPGrylynenTier.GOLDEN), EntityClassification.MONSTER).sized(1f, 1.8f).build(new ResourceLocation(Bioplethora.MOD_ID, "golden_grylynen").toString()));
    public static final RegistryObject<EntityType<GrylynenEntity>> IRON_GRYLYNEN = ENTITIES.register("iron_grylynen", () -> EntityType.Builder.of((EntityType.IFactory<GrylynenEntity>) (type, world) ->
            new GrylynenEntity(type, world, BPGrylynenTier.IRON), EntityClassification.MONSTER).sized(1f, 1.8f).build(new ResourceLocation(Bioplethora.MOD_ID, "iron_grylynen").toString()));
    public static final RegistryObject<EntityType<GrylynenEntity>> DIAMOND_GRYLYNEN = ENTITIES.register("diamond_grylynen", () -> EntityType.Builder.of((EntityType.IFactory<GrylynenEntity>) (type, world) ->
            new GrylynenEntity(type, world, BPGrylynenTier.DIAMOND), EntityClassification.MONSTER).sized(1f, 1.8f).build(new ResourceLocation(Bioplethora.MOD_ID, "diamond_grylynen").toString()));
    public static final RegistryObject<EntityType<GrylynenEntity>> NETHERITE_GRYLYNEN = ENTITIES.register("netherite_grylynen", () -> EntityType.Builder.of((EntityType.IFactory<GrylynenEntity>) (type, world) ->
            new GrylynenEntity(type, world, BPGrylynenTier.NETHERITE), EntityClassification.MONSTER).sized(1f, 1.8f).build(new ResourceLocation(Bioplethora.MOD_ID, "netherite_grylynen").toString()));

    //Hellsent
    public static final RegistryObject<EntityType<CrephoxlEntity>> CREPHOXL = ENTITIES.register("crephoxl", () -> EntityType.Builder.of(CrephoxlEntity::new, EntityClassification.MONSTER).sized(3.5f, 5f).build(new ResourceLocation(Bioplethora.MOD_ID, "crephoxl").toString()));
    public static final RegistryObject<EntityType<BellophgolemEntity>> BELLOPHGOLEM = ENTITIES.register("bellophgolem", () -> EntityType.Builder.of(BellophgolemEntity::new, EntityClassification.MONSTER).sized(3.5f, 4.75f).build(new ResourceLocation(Bioplethora.MOD_ID, "bellophgolem").toString()));
    public static final RegistryObject<EntityType<HeliobladeEntity>> HELIOBLADE = ENTITIES.register("helioblade", () -> EntityType.Builder.of(HeliobladeEntity::new, EntityClassification.MONSTER).sized(0.6f, 1.8f).build(new ResourceLocation(Bioplethora.MOD_ID, "helioblade").toString()));
    public static final RegistryObject<EntityType<TelemreyeEntity>> TELEMREYE = ENTITIES.register("telemreye", () -> EntityType.Builder.of(TelemreyeEntity::new, EntityClassification.MONSTER).sized(2.6f, 1.15f).build(new ResourceLocation(Bioplethora.MOD_ID, "telemreye").toString()));

    //Elderia
    public static final RegistryObject<EntityType<AltyrusEntity>> ALTYRUS = ENTITIES.register("altyrus", () -> EntityType.Builder.of(AltyrusEntity::new, EntityClassification.MONSTER).sized(3.5f, 4.75f).build(new ResourceLocation(Bioplethora.MOD_ID, "altyrus").toString()));
    public static final RegistryObject<EntityType<MyliothanEntity>> MYLIOTHAN = ENTITIES.register("myliothan", () -> EntityType.Builder.of(MyliothanEntity::new, EntityClassification.MONSTER).sized(14.5f, 5.5f).build(new ResourceLocation(Bioplethora.MOD_ID, "myliothan").toString()));
    public static final RegistryObject<EntityType<AlphemKingEntity>> ALPHEM_KING = ENTITIES.register("alphem_king", () -> EntityType.Builder.of(AlphemKingEntity::new, EntityClassification.MONSTER).sized(3.5f, 4.75f).build(new ResourceLocation(Bioplethora.MOD_ID, "alphem_king").toString()));

    //Projectiles
    public static final RegistryObject<EntityType<BellophiteClusterEntity>> BELLOPHITE_CLUSTER = ENTITIES.register("bellophite_cluster", () -> EntityType.Builder.<BellophiteClusterEntity>of(BellophiteClusterEntity::new, EntityClassification.MISC).sized(2.0F, 2.0F).clientTrackingRange(4)
            .build(new ResourceLocation(Bioplethora.MOD_ID, "bellophite_cluster").toString()));
    public static final RegistryObject<EntityType<BellophiteArrowEntity>> BELLOPHITE_ARROW = ENTITIES.register("bellophite_arrow", () -> EntityType.Builder.<BellophiteArrowEntity>of(BellophiteArrowEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F).clientTrackingRange(4)
            .build(new ResourceLocation(Bioplethora.MOD_ID, "textures/projectiles").toString()));
    public static final RegistryObject<EntityType<WindblazeEntity>> WINDBLAZE = ENTITIES.register("windblaze", () -> EntityType.Builder.<WindblazeEntity>of(WindblazeEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F).clientTrackingRange(4)
            .build(new ResourceLocation(Bioplethora.MOD_ID, "windblaze").toString()));
    public static final RegistryObject<EntityType<UltimateBellophiteClusterEntity>> ULTIMATE_BELLOPHITE_CLUSTER = ENTITIES.register("ultimate_bellophite_cluster", () -> EntityType.Builder.<UltimateBellophiteClusterEntity>of(UltimateBellophiteClusterEntity::new, EntityClassification.MISC).sized(2.0F, 2.0F).clientTrackingRange(4)
            .build(new ResourceLocation(Bioplethora.MOD_ID, "ultimate_bellophite_cluster").toString()));
    public static final RegistryObject<EntityType<MagmaBombEntity>> MAGMA_BOMB = ENTITIES.register("magma_bomb", () -> EntityType.Builder.<MagmaBombEntity>of(MagmaBombEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F).clientTrackingRange(4)
            .build(new ResourceLocation(Bioplethora.MOD_ID, "magma_bomb").toString()));
    public static final RegistryObject<EntityType<VermilionBladeProjectileEntity>> VERMILION_BLADE_PROJECTILE = ENTITIES.register("vermilion_blade_projectile", () -> EntityType.Builder.<VermilionBladeProjectileEntity>of(VermilionBladeProjectileEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F).clientTrackingRange(4)
            .build(new ResourceLocation(Bioplethora.MOD_ID, "vermilion_blade_projectile").toString()));
    public static final RegistryObject<EntityType<WindArrowEntity>> WIND_ARROW = ENTITIES.register("wind_arrow", () -> EntityType.Builder.<WindArrowEntity>of(WindArrowEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F).clientTrackingRange(4)
            .build(new ResourceLocation(Bioplethora.MOD_ID, "textures/projectiles").toString()));
    public static final RegistryObject<EntityType<CryoblazeEntity>> CRYOBLAZE = ENTITIES.register("cryoblaze", () -> EntityType.Builder.<CryoblazeEntity>of(CryoblazeEntity::new, EntityClassification.MISC).sized(2.0F, 2.0F).clientTrackingRange(4)
            .build(new ResourceLocation(Bioplethora.MOD_ID, "cryoblaze").toString()));
    public static final RegistryObject<EntityType<AlphanumObliteratorSpearEntity>> ALPHANUM_OBLITERATOR_SPEAR = ENTITIES.register("alphanum_obliterator_spear", () -> EntityType.Builder.<AlphanumObliteratorSpearEntity>of(AlphanumObliteratorSpearEntity::new, EntityClassification.MISC).sized(1.0F, 1.0F).clientTrackingRange(4)
            .build(new ResourceLocation(Bioplethora.MOD_ID, "alphanum_obliterator_spear").toString()));
    public static final RegistryObject<EntityType<WindyEssenceEntity>> WINDY_ESSENCE = ENTITIES.register("windy_essence", () -> EntityType.Builder.of(WindyEssenceEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F).clientTrackingRange(4)
            .build(new ResourceLocation(Bioplethora.MOD_ID, "windy_essence").toString()));
    public static final RegistryObject<EntityType<AbyssalScalesEntity>> ABYSSAL_SCALES = ENTITIES.register("abyssal_scales", () -> EntityType.Builder.<AbyssalScalesEntity>of(AbyssalScalesEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F).clientTrackingRange(4)
            .build(new ResourceLocation(Bioplethora.MOD_ID, "abyssal_scales").toString()));

    public static final RegistryObject<EntityType<CryeanumGaidiusEntity>> CRYEANUM_GAIDIUS = ENTITIES.register("cryeanum_gaidius", () -> EntityType.Builder.<CryeanumGaidiusEntity>of(CryeanumGaidiusEntity::new, EntityClassification.MISC)
            .sized(1.5F, 1.5F).build(new ResourceLocation(Bioplethora.MOD_ID, "cryeanum_gaidius").toString()));
        public static final RegistryObject<EntityType<NetheriteObsidianGaidiusEntity>> NETHERITE_OBSIDIAN_GAIDIUS = ENTITIES.register("netherite_obsidian_gaidius", () -> EntityType.Builder.<NetheriteObsidianGaidiusEntity>of(NetheriteObsidianGaidiusEntity::new, EntityClassification.MISC)
            .sized(1.5F, 1.5F).build(new ResourceLocation(Bioplethora.MOD_ID, "netherite_obsidian_gaidius").toString()));
    public static final RegistryObject<EntityType<EchoGaidiusEntity>> ECHO_GAIDIUS = ENTITIES.register("echo_gaidius", () -> EntityType.Builder.<EchoGaidiusEntity>of(EchoGaidiusEntity::new, EntityClassification.MISC)
            .sized(1.5F, 1.5F).build(new ResourceLocation(Bioplethora.MOD_ID, "echo_gaidius").toString()));

    //Others
    public static final RegistryObject<EntityType<BPBoatEntity>> CAERULWOOD_BOAT = createBoat("caerulwood");
    public static final RegistryObject<EntityType<BPBoatEntity>> ENIVILE_BOAT = createBoat("enivile");

    public static final RegistryObject<EntityType<PrimordialRingEntity>> PRIMORDIAL_RING = ENTITIES.register("primordial_ring", () -> EntityType.Builder.of(PrimordialRingEntity::new, EntityClassification.MONSTER).sized(3.5f, 2f).build(new ResourceLocation(Bioplethora.MOD_ID, "primordial_ring").toString()));
    public static final RegistryObject<EntityType<AltyrusSummoningEntity>> ALTYRUS_SUMMONING = ENTITIES.register("altyrus_summoning", () -> EntityType.Builder.of(AltyrusSummoningEntity::new, EntityClassification.MISC).sized(2.0F, 2.0F).clientTrackingRange(4)
            .build(new ResourceLocation(Bioplethora.MOD_ID, "altyrus_summoning").toString()));
    public static final RegistryObject<EntityType<BellophiteShieldWaveEntity>> BELLOPHITE_SHIELD_WAVE = ENTITIES.register("bellophite_shield_wave", () -> EntityType.Builder.of(BellophiteShieldWaveEntity::new, EntityClassification.MISC).clientTrackingRange(4)
            .build(new ResourceLocation(Bioplethora.MOD_ID, "bellophite_shield_wave").toString()));
    public static final RegistryObject<EntityType<GrylynenCoreBombEntity>> GRYLYNEN_CORE_BOMB = ENTITIES.register("grylynen_core_bomb", () -> EntityType.Builder.of(GrylynenCoreBombEntity::new, EntityClassification.MISC).sized(1.0F, 1.0F).clientTrackingRange(4)
            .build(new ResourceLocation(Bioplethora.MOD_ID, "grylynen_core_bomb").toString()));
    public static final RegistryObject<EntityType<AlphanumShardEntity>> ALPHANUM_SHARD = ENTITIES.register("alphanum_shard", () -> EntityType.Builder.of(AlphanumShardEntity::new, EntityClassification.MISC).sized(0.8F, 1.5F).clientTrackingRange(4)
            .build(new ResourceLocation(Bioplethora.MOD_ID, "alphanum_shard").toString()));

    public static final RegistryObject<EntityType<BPEffectEntity>> CREPHOXL_HAMMER_SMASH = createEffectEntity("crephoxl_hammer_smash", BPEffectTypes.AERIAL_SHOCKWAVE);
    public static final RegistryObject<EntityType<BPEffectEntity>> INFERNAL_QUARTERSTAFF_SLASH = createEffectEntity("infernal_quarterstaff_slash", BPEffectTypes.FLAMING_SLASH);
    public static final RegistryObject<EntityType<BPEffectEntity>> INFERNAL_QUARTERSTAFF_SOUL_PURGE = createEffectEntity("infernal_quarterstaff_soul_purge", BPEffectTypes.SOUL_PURGE);
    public static final RegistryObject<EntityType<BPEffectEntity>> INFERNAL_QUARTERSTAFF_AIR_JUMP = createEffectEntity("infernal_quarterstaff_air_jump", BPEffectTypes.AIR_JUMP);
    public static final RegistryObject<EntityType<BPEffectEntity>> INFERNAL_QUARTERSTAFF_FLAMING_SNIPE = createEffectEntity("infernal_quarterstaff_flaming_snipe", BPEffectTypes.FLAMING_SNIPE);

    public static final RegistryObject<EntityType<BPEffectEntity>> MYLIOTHAN_ROAR = createEffectEntity("myliothan_roar", BPEffectTypes.MYLIOTHAN_ROAR);

    //============================
    //       HELPERS
    //============================
    public static RegistryObject<EntityType<BPBoatEntity>> createBoat(String woodType) {
        return ENTITIES.register(woodType + "_boat", () -> EntityType.Builder.<BPBoatEntity>of(BPBoatEntity::new, EntityClassification.MISC).sized(1.375F, 0.5625F).build(new ResourceLocation(Bioplethora.MOD_ID, woodType + "_boat").toString()));
    }

    public static RegistryObject<EntityType<? extends GaidiusBaseEntity>> createGaidius(String name, EntityType.IFactory<? extends GaidiusBaseEntity> gaidius) {
        return ENTITIES.register(name, () -> EntityType.Builder.of(gaidius, EntityClassification.MISC).sized(2.0F, 2.0F).build(new ResourceLocation(Bioplethora.MOD_ID, name).toString()));
    }

    public static RegistryObject<EntityType<BPEffectEntity>> createEffectEntity(String registryName, BPEffectTypes effectType) {
        return ENTITIES.register(registryName, () -> EntityType.Builder.of((EntityType.IFactory<BPEffectEntity>) (type, world) ->
                        new BPEffectEntity(type, world, effectType), EntityClassification.MISC).sized(0.5F, 0.5F).clientTrackingRange(4)
                .build(new ResourceLocation(Bioplethora.MOD_ID, registryName).toString()));

    }
}
