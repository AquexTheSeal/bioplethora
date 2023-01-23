package io.github.bioplethora.api.world;

import io.github.bioplethora.network.BPNetwork;
import io.github.bioplethora.network.functions.BPSpawnParticlePacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SSpawnParticlePacket;
import net.minecraft.particles.IParticleData;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class EffectUtils {

    public static <T extends IParticleData> int sendParticles(ServerWorld serverWorld, T pType, double pPosX, double pPosY, double pPosZ, int pParticleCount, double pXOffset, double pYOffset, double pZOffset, double pXSpeed, double pYSpeed, double pZSpeed) {
        BPSpawnParticlePacket sspawnparticlepacket = new BPSpawnParticlePacket(pType, false, pPosX, pPosY, pPosZ, (float)pXOffset, (float)pYOffset, (float)pZOffset, (float)pXSpeed, (float)pYSpeed, (float)pZSpeed, pParticleCount);
        int i = 0;

        for(int j = 0; j < serverWorld.players().size(); ++j) {
            ServerPlayerEntity serverplayerentity = serverWorld.players().get(j);
            if (sendParticles(serverplayerentity, false, pPosX, pPosY, pPosZ, sspawnparticlepacket)) {
                ++i;
            }
        }

        return i;
    }

    public static <T extends IParticleData> void createParticleBall(ServerWorld serverWorld, T pType, double pPosX, double pPosY, double pPosZ, double pSpeed, int pSize) {
        for(int i = -pSize; i <= pSize; ++i) {
            for(int j = -pSize; j <= pSize; ++j) {
                for(int k = -pSize; k <= pSize; ++k) {
                    double d3 = (double)j + (serverWorld.random.nextDouble() - serverWorld.random.nextDouble()) * 0.5D;
                    double d4 = (double)i + (serverWorld.random.nextDouble() - serverWorld.random.nextDouble()) * 0.5D;
                    double d5 = (double)k + (serverWorld.random.nextDouble() - serverWorld.random.nextDouble()) * 0.5D;
                    double d6 = (double) MathHelper.sqrt(d3 * d3 + d4 * d4 + d5 * d5) / pSpeed + serverWorld.random.nextGaussian() * 0.05D;
                    sendParticles(serverWorld, pType, pPosX, pPosY, pPosZ, 1, 0, 0, 0, d3 / d6, d4 / d6, d5 / d6);
                    if (i != -pSize && i != pSize && j != -pSize && j != pSize) {
                        k += pSize * 2 - 1;
                    }
                }
            }
        }
    }

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

    private static boolean sendParticles(ServerPlayerEntity pPlayer, boolean pLongDistance, double pPosX, double pPosY, double pPosZ, BPSpawnParticlePacket packet) {
        if (pPlayer.getLevel().isClientSide()) {
            return false;
        } else {
            BlockPos blockpos = pPlayer.blockPosition();
            if (blockpos.closerThan(new Vector3d(pPosX, pPosY, pPosZ), pLongDistance ? 512.0D : 32.0D)) {
                BPNetwork.sendPacketToPlayer(pPlayer, packet);
                return true;
            } else {
                return false;
            }
        }
    }
}
