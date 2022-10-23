package io.github.bioplethora.particles;

import com.mojang.serialization.Codec;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.registry.Registry;

/**
 * @Credit MaxBogomol
 */
public class WindPoofParticleType extends ParticleType<WindPoofParticleData> implements IParticleData {

    private static final boolean ALWAYS_SHOW_REGARDLESS_OF_DISTANCE_FROM_PLAYER = false;

    public WindPoofParticleType() {
        super(ALWAYS_SHOW_REGARDLESS_OF_DISTANCE_FROM_PLAYER, WindPoofParticleData.DESERIALIZER);
    }

    public Codec<WindPoofParticleData> codec() {
        return WindPoofParticleData.CODEC;
    }

    @Override
    public ParticleType<?> getType() {
        return this;
    }

    @Override
    public void writeToNetwork(PacketBuffer pBuffer) {

    }

    @Override
    public String writeToString() {
        return Registry.PARTICLE_TYPE.getKey(this).toString();
    }
}
