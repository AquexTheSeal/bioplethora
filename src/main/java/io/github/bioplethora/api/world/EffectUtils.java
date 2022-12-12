package io.github.bioplethora.api.world;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.IParticleData;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class EffectUtils {

    public static void addCircleParticleForm(ServerWorld serverWorld, Entity entity, IParticleData particle, int count, double offsetCircle, double speed) {
        serverWorld.sendParticles(particle, entity.getX(), entity.getY(), entity.getZ(), count, offsetCircle, offsetCircle, offsetCircle, speed);
    }

    public static void addCircleParticleForm(World world, Entity entity, IParticleData particle, int count, double offsetCircle, double speed) {
        addCircleParticleForm(world, entity, particle, count, offsetCircle, speed, 0);
    }

    public static void addCircleParticleForm(World world, Entity entity, IParticleData particle, int count, double offsetCircle, double speed, double yOffset) {
        if (!world.isClientSide()) {
            ((ServerWorld) world).sendParticles(particle, entity.getX(), entity.getY() + yOffset, entity.getZ(), count, offsetCircle, offsetCircle, offsetCircle, speed);
        }
    }

    public static void addEffectNoIcon(LivingEntity target, Effect effect, int duration, int amplifier) {
        target.addEffect(new EffectInstance(effect, duration, amplifier, false, false, false));
    }
}
