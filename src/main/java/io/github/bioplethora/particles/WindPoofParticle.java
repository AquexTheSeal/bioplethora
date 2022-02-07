package io.github.bioplethora.particles;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WindPoofParticle extends SpriteTexturedParticle {

    private final IAnimatedSprite sprites;

    private WindPoofParticle(ClientWorld clientWorld, double p_i232384_2_, double p_i232384_4_, double p_i232384_6_, double p_i232384_8_, double p_i232384_10_, double p_i232384_12_, IAnimatedSprite sprite) {
        super(clientWorld, p_i232384_2_, p_i232384_4_, p_i232384_6_);
        this.sprites = sprite;
        this.lifetime = 20;
        float f = 1.0F;
        this.rCol = f;
        this.gCol = f;
        this.bCol = f;
        this.xd = p_i232384_8_ + (Math.random() * 2.0D - 1.0D) * (double)0.05F;
        this.yd = p_i232384_10_ + (Math.random() * 2.0D - 1.0D) * (double)0.05F;
        this.zd = p_i232384_12_ + (Math.random() * 2.0D - 1.0D) * (double)0.05F;
        this.quadSize = 1.0F;
        this.setSpriteFromAge(sprite);
    }

    public int getLightColor(float p_189214_1_) {
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
            this.yd += 0.004D;
            this.move(this.xd, this.yd, this.zd);
            this.xd *= 0.9F;
            this.yd *= 0.9F;
            this.zd *= 0.9F;
            if (this.onGround) {
                this.xd *= 0.7F;
                this.zd *= 0.7F;
            }
        }
    }

    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite sprites;

        public Factory(IAnimatedSprite p_i50563_1_) {
            this.sprites = p_i50563_1_;
        }

        public Particle createParticle(BasicParticleType p_199234_1_, ClientWorld p_199234_2_, double p_199234_3_, double p_199234_5_, double p_199234_7_, double p_199234_9_, double p_199234_11_, double p_199234_13_) {
            WindPoofParticle windPoof = new WindPoofParticle(p_199234_2_, p_199234_3_, p_199234_5_, p_199234_7_, p_199234_9_, p_199234_11_, p_199234_13_, this.sprites);
            windPoof.setColor(1.0f, 1.0f, 1.0f);
            return windPoof;
        }
    }
}
