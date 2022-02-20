package io.github.bioplethora.registry;

import io.github.bioplethora.Bioplethora;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BioplethoraParticles {

    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Bioplethora.MOD_ID);

    public static final RegistryObject<BasicParticleType> WIND_POOF = PARTICLES.register("wind_poof", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> NIGHT_GAZE = PARTICLES.register("night_gaze", () -> new BasicParticleType(true));
}