package io.github.bioplethora.registry;

import io.github.bioplethora.Bioplethora;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BPSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Bioplethora.MOD_ID);

    public static final RegistryObject<SoundEvent> CREPHOXL_IDLE = SOUNDS.register("crephoxl_idle", () -> new SoundEvent(new ResourceLocation(Bioplethora.MOD_ID, "crephoxl_idle")));
    public static final RegistryObject<SoundEvent> CREPHOXL_HURT = SOUNDS.register("crephoxl_hurt", () -> new SoundEvent(new ResourceLocation(Bioplethora.MOD_ID, "crephoxl_hurt")));
    public static final RegistryObject<SoundEvent> CREPHOXL_DEATH = SOUNDS.register("crephoxl_death", () -> new SoundEvent(new ResourceLocation(Bioplethora.MOD_ID, "crephoxl_death")));
    
    public static final RegistryObject<SoundEvent> BELLOPHGOLEM_IDLE = SOUNDS.register("bellophgolem_idle", () -> new SoundEvent(new ResourceLocation(Bioplethora.MOD_ID, "bellophgolem_idle")));
    public static final RegistryObject<SoundEvent> BELLOPHGOLEM_HURT = SOUNDS.register("bellophgolem_hurt", () -> new SoundEvent(new ResourceLocation(Bioplethora.MOD_ID, "bellophgolem_hurt")));
    public static final RegistryObject<SoundEvent> BELLOPHGOLEM_DEATH = SOUNDS.register("bellophgolem_death", () -> new SoundEvent(new ResourceLocation(Bioplethora.MOD_ID, "bellophgolem_death")));

    public static final RegistryObject<SoundEvent> MYLIOTHAN_IDLE = SOUNDS.register("myliothan_idle", () -> new SoundEvent(new ResourceLocation(Bioplethora.MOD_ID, "myliothan_idle")));
    
    public static final RegistryObject<SoundEvent> HELIOBLADE_IDLE = SOUNDS.register("helioblade_idle", () -> new SoundEvent(new ResourceLocation(Bioplethora.MOD_ID, "helioblade_idle")));
    public static final RegistryObject<SoundEvent> HELIOBLADE_HURT = SOUNDS.register("helioblade_hurt", () -> new SoundEvent(new ResourceLocation(Bioplethora.MOD_ID, "helioblade_hurt")));
    public static final RegistryObject<SoundEvent> HELIOBLADE_DEATH = SOUNDS.register("helioblade_death", () -> new SoundEvent(new ResourceLocation(Bioplethora.MOD_ID, "helioblade_death")));

    public static final RegistryObject<SoundEvent> ALTYRUS_IDLE = SOUNDS.register("altyrus_idle", () -> new SoundEvent(new ResourceLocation(Bioplethora.MOD_ID, "altyrus_idle")));
    public static final RegistryObject<SoundEvent> ALTYRUS_CHARGE = SOUNDS.register("altyrus_charge", () -> new SoundEvent(new ResourceLocation(Bioplethora.MOD_ID, "altyrus_charge")));

    public static final RegistryObject<SoundEvent> ALPHEM_STEP = SOUNDS.register("alphem_step", () -> new SoundEvent(new ResourceLocation(Bioplethora.MOD_ID, "alphem_step")));

    public static final RegistryObject<SoundEvent> ALPHEM_KING_ROAR = SOUNDS.register("alphem_king_roar", () -> new SoundEvent(new ResourceLocation(Bioplethora.MOD_ID, "alphem_king_roar")));

    public static final RegistryObject<SoundEvent> TRUE_DEFENSE_CLASH = SOUNDS.register("true_defense_clash", () -> new SoundEvent(new ResourceLocation(Bioplethora.MOD_ID, "true_defense_clash")));

}
