package io.github.bioplethora.registry;

import io.github.bioplethora.Bioplethora;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BioplethoraSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Bioplethora.MOD_ID);

    public static final RegistryObject<SoundEvent> BELLOPHGOLEM_IDLE = SOUNDS.register("bellophgolem_idle", () -> new SoundEvent(new ResourceLocation(Bioplethora.MOD_ID, "bellophgolem_idle")));
    public static final RegistryObject<SoundEvent> BELLOPHGOLEM_HURT = SOUNDS.register("bellophgolem_hurt", () -> new SoundEvent(new ResourceLocation(Bioplethora.MOD_ID, "bellophgolem_hurt")));
    public static final RegistryObject<SoundEvent> BELLOPHGOLEM_DEATH = SOUNDS.register("bellophgolem_death", () -> new SoundEvent(new ResourceLocation(Bioplethora.MOD_ID, "bellophgolem_death")));
}
