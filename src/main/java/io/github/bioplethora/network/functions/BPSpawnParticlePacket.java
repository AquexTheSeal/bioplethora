package io.github.bioplethora.network.functions;

import io.github.bioplethora.Bioplethora;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class BPSpawnParticlePacket {

    public double x;
    public double y;
    public double z;
    public float xDist;
    public float yDist;
    public float zDist;
    public float xSpeed;
    public float ySpeed;
    public float zSpeed;
    public int count;
    public boolean overrideLimiter;
    public IParticleData particle;

    public <T extends IParticleData> BPSpawnParticlePacket(T pParticle, boolean pOverrideLimiter, double pX, double pY, double pZ, float pXDist, float pYDist, float pZDist, float xSpeed, float ySpeed, float zSpeed, int pCount) {
        this.particle = pParticle;
        this.overrideLimiter = pOverrideLimiter;
        this.x = pX;
        this.y = pY;
        this.z = pZ;
        this.xDist = pXDist;
        this.yDist = pYDist;
        this.zDist = pZDist;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.zSpeed = zSpeed;
        this.count = pCount;
    }

    public static void spawnParticles(BPSpawnParticlePacket message, Supplier<NetworkEvent.Context> context) {
        Minecraft minecraft = Minecraft.getInstance();
        
        if (minecraft.level == null) {
            return;
        }
        
        if (message.getCount() == 0) {
            double d0 = message.getXSpeed() * message.getXDist();
            double d2 = message.getYSpeed() * message.getYDist();
            double d4 = message.getZSpeed() * message.getZDist();

            try {
                minecraft.level.addParticle(message.getParticle(), message.isOverrideLimiter(), message.getX(), message.getY(), message.getZ(), d0, d2, d4);
            } catch (Throwable throwable1) {
                Bioplethora.LOGGER.warn("Could not spawn particle effect {}", message.getParticle());
            }
        } else {
            for(int i = 0; i < message.getCount(); ++i) {
                double d1 = minecraft.level.random.nextGaussian() * (double)message.getXDist();
                double d3 = minecraft.level.random.nextGaussian() * (double)message.getYDist();
                double d5 = minecraft.level.random.nextGaussian() * (double)message.getZDist();
                double d6 = message.getXSpeed();
                double d7 = message.getYSpeed();
                double d8 = message.getZSpeed();

                try {
                    minecraft.level.addParticle(message.getParticle(), message.isOverrideLimiter(), message.getX() + d1, message.getY() + d3, message.getZ() + d5, d6, d7, d8);
                } catch (Throwable throwable) {
                    Bioplethora.LOGGER.warn("Could not spawn particle effect {}", message.getParticle());
                    return;
                }
            }
        }
    }

    public static BPSpawnParticlePacket decode(PacketBuffer buffer) {
        ParticleType<?> particletype = Registry.PARTICLE_TYPE.byId(buffer.readInt());
        if (particletype == null) {
            particletype = ParticleTypes.BARRIER;
        }
        return new BPSpawnParticlePacket(
                readParticle(buffer, particletype), buffer.readBoolean(), 
                buffer.readDouble(), buffer.readDouble(), buffer.readDouble(), 
                buffer.readFloat(), buffer.readFloat(), buffer.readFloat(), 
                buffer.readFloat(), buffer.readFloat(), buffer.readFloat(), 
                buffer.readInt()
        );
    }

    public static <T extends IParticleData> T readParticle(PacketBuffer pBuffer, ParticleType<T> pParticleType) {
        return pParticleType.getDeserializer().fromNetwork(pParticleType, pBuffer);
    }
    
    public static void encode(BPSpawnParticlePacket message, PacketBuffer pBuffer) {
        pBuffer.writeInt(Registry.PARTICLE_TYPE.getId(message.particle.getType()));
        pBuffer.writeBoolean(message.overrideLimiter);
        pBuffer.writeDouble(message.x);
        pBuffer.writeDouble(message.y);
        pBuffer.writeDouble(message.z);
        pBuffer.writeFloat(message.xDist);
        pBuffer.writeFloat(message.yDist);
        pBuffer.writeFloat(message.zDist);
        pBuffer.writeFloat(message.xSpeed);
        pBuffer.writeFloat(message.ySpeed);
        pBuffer.writeFloat(message.zSpeed);
        pBuffer.writeInt(message.count);
        message.particle.writeToNetwork(pBuffer);
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isOverrideLimiter() {
        return this.overrideLimiter;
    }
    
    @OnlyIn(Dist.CLIENT)
    public double getX() {
        return this.x;
    }
    
    @OnlyIn(Dist.CLIENT)
    public double getY() {
        return this.y;
    }
    
    @OnlyIn(Dist.CLIENT)
    public double getZ() {
        return this.z;
    }
    
    @OnlyIn(Dist.CLIENT)
    public float getXDist() {
        return this.xDist;
    }
    
    @OnlyIn(Dist.CLIENT)
    public float getYDist() {
        return this.yDist;
    }
    
    @OnlyIn(Dist.CLIENT)
    public float getZDist() {
        return this.zDist;
    }
    
    @OnlyIn(Dist.CLIENT)
    public float getXSpeed() {
        return this.xSpeed;
    }
    
    @OnlyIn(Dist.CLIENT)
    public float getYSpeed() {
        return this.ySpeed;
    }
    
    @OnlyIn(Dist.CLIENT)
    public float getZSpeed() {
        return this.zSpeed;
    }

    @OnlyIn(Dist.CLIENT)
    public int getCount() {
        return this.count;
    }

    @OnlyIn(Dist.CLIENT)
    public IParticleData getParticle() {
        return this.particle;
    }
}
