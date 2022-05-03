package io.github.bioplethora.particles;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FallingLeavesParticle extends SpriteTexturedParticle {
    private final float rotSpeed;
    private final IAnimatedSprite sprites;

    private FallingLeavesParticle(ClientWorld clientWorld, double p_i232384_2_, double p_i232384_4_, double p_i232384_6_, double p_i232384_8_, double p_i232384_10_, double p_i232384_12_, IAnimatedSprite sprite) {
        super(clientWorld, p_i232384_2_, p_i232384_4_, p_i232384_6_);
        this.sprites = sprite;
        this.lifetime = 100;
        float f = 1.0F;
        this.rCol = f;
        this.gCol = f;
        this.bCol = f;
        this.xd = p_i232384_8_ + (Math.random() * 2.0D - 1.0D) * (double)0.05F;
        this.yd = p_i232384_10_ + (Math.random() * 2.0D - 1.0D) * (double)0.05F;
        this.zd = p_i232384_12_ + (Math.random() * 2.0D - 1.0D) * (double)0.05F;

        this.rotSpeed = ((float)Math.random() - 0.5F) * 0.1F;
        this.roll = (float)Math.random() * ((float)Math.PI * 2F);

        this.quadSize = 0.5F;
        this.setSpriteFromAge(sprite);
    }

    public int getLightColor(float pPartialTick) {
        return 15728880;
    }

    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            this.setSpriteFromAge(this.sprites);
            this.oRoll = this.roll;
            this.roll += (float)Math.PI * this.rotSpeed * 2.0F;
            this.yd -= 0.0075D;
            this.move(this.xd, this.yd, this.zd);
            this.xd *= 1.1F;
            this.yd *= 0.9F;
            this.zd *= 0.9F;
            if (this.onGround) {
                this.oRoll = this.roll = 0.0F;
                this.xd *= 0.7F;
                this.zd *= 0.7F;
            }
        }
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
            FallingLeavesParticle particle = new FallingLeavesParticle(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed, this.sprites);
            particle.setColor(1.0f, 1.0f, 1.0f);
            return particle;
        }
    }
}
