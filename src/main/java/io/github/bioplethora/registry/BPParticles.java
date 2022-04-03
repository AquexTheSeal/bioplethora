package io.github.bioplethora.registry;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.particles.WindPoofParticleType;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BPParticles {

    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Bioplethora.MOD_ID);

    public static final RegistryObject<WindPoofParticleType> WIND_POOF = PARTICLES.register("wind_poof", WindPoofParticleType::new);
    public static final RegistryObject<BasicParticleType> NIGHT_GAZE = PARTICLES.register("night_gaze", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> ANTIBIO_SPELL = PARTICLES.register("antibio_spell", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> TRUE_DEFENSE_CLASH = PARTICLES.register("true_defense_clash", () -> new BasicParticleType(true));
}
