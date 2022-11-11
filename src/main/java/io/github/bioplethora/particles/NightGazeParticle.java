package io.github.bioplethora.particles;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class NightGazeParticle extends RisingParticle {

    protected NightGazeParticle(ClientWorld pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed, float pQuadSizeMultiplier, IAnimatedSprite pSprites) {
        super(pLevel, pX, pY, pZ, 0.1F, -0.1F, 0.1F, pXSpeed, pYSpeed, pZSpeed, pQuadSizeMultiplier, pSprites, 0.0F, 20, -5.0E-3D, false);
        this.rCol = 0.7294118F;
        this.gCol = 0.69411767F;
        this.bCol = 0.7607843F;
        this.lifetime = 80;
    }

    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_LIT;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite sprites;

        public Factory(IAnimatedSprite iAnimatedSprite) {
            this.sprites = iAnimatedSprite;
        }

        public Particle createParticle(BasicParticleType pType, ClientWorld pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            Random random = pLevel.random;
            double d0 = (double)random.nextFloat() * -1.9D * (double)random.nextFloat() * 0.1D;
            double d1 = (double)random.nextFloat() * -0.5D * (double)random.nextFloat() * 0.1D * 5.0D;
            double d2 = (double)random.nextFloat() * -1.9D * (double)random.nextFloat() * 0.1D;
            return new NightGazeParticle(pLevel, pX, pY, pZ, d0, d1, d2, 0.75F, this.sprites);
        }
    }
}
