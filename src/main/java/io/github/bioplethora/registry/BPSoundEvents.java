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
    
    public static final RegistryObject<SoundEvent> FROSTBITE_GOLEM_IDLE = SOUNDS.register("frostbite_golem_idle", () -> new SoundEvent(new ResourceLocation(Bioplethora.MOD_ID, "frostbite_golem_idle")));
    public static final RegistryObject<SoundEvent> FROSTBITE_GOLEM_HURT = SOUNDS.register("frostbite_golem_hurt", () -> new SoundEvent(new ResourceLocation(Bioplethora.MOD_ID, "frostbite_golem_hurt")));
    public static final RegistryObject<SoundEvent> FROSTBITE_GOLEM_DEATH = SOUNDS.register("frostbite_golem_death", () -> new SoundEvent(new ResourceLocation(Bioplethora.MOD_ID, "frostbite_golem_death")));

    public static final RegistryObject<SoundEvent> MYLIOTHAN_IDLE = SOUNDS.register("myliothan_idle", () -> new SoundEvent(new ResourceLocation(Bioplethora.MOD_ID, "myliothan_idle")));
    
    public static final RegistryObject<SoundEvent> SHACHATH_IDLE = SOUNDS.register("shachath_idle", () -> new SoundEvent(new ResourceLocation(Bioplethora.MOD_ID, "shachath_idle")));
    public static final RegistryObject<SoundEvent> SHACHATH_HURT = SOUNDS.register("shachath_hurt", () -> new SoundEvent(new ResourceLocation(Bioplethora.MOD_ID, "shachath_hurt")));
    public static final RegistryObject<SoundEvent> SHACHATH_DEATH = SOUNDS.register("shachath_death", () -> new SoundEvent(new ResourceLocation(Bioplethora.MOD_ID, "shachath_death")));
    public static final RegistryObject<SoundEvent> SHACHATH_SLASH = SOUNDS.register("shachath_slash", () -> new SoundEvent(new ResourceLocation(Bioplethora.MOD_ID, "shachath_slash")));

    public static final RegistryObject<SoundEvent> ALTYRUS_IDLE = SOUNDS.register("altyrus_idle", () -> new SoundEvent(new ResourceLocation(Bioplethora.MOD_ID, "altyrus_idle")));
    public static final RegistryObject<SoundEvent> ALTYRUS_CHARGE = SOUNDS.register("altyrus_charge", () -> new SoundEvent(new ResourceLocation(Bioplethora.MOD_ID, "altyrus_charge")));

    public static final RegistryObject<SoundEvent> ALPHEM_STEP = SOUNDS.register("alphem_step", () -> new SoundEvent(new ResourceLocation(Bioplethora.MOD_ID, "alphem_step")));

    public static final RegistryObject<SoundEvent> ALPHEM_KING_ROAR = SOUNDS.register("alphem_king_roar", () -> new SoundEvent(new ResourceLocation(Bioplethora.MOD_ID, "alphem_king_roar")));

    public static final RegistryObject<SoundEvent> TRUE_DEFENSE_CLASH = SOUNDS.register("true_defense_clash", () -> new SoundEvent(new ResourceLocation(Bioplethora.MOD_ID, "true_defense_clash")));

}
