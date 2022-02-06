package io.github.bioplethora.particles;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WindPoofParticle extends SpriteTexturedParticle {

    private final IAnimatedSprite sprites;

    private WindPoofParticle(ClientWorld clientWorld, double xCoordIn, double yCoordIn, double zCoordIn, double num, IAnimatedSprite sprite) {
        super(clientWorld, xCoordIn, yCoordIn, zCoordIn, 0.0D, 0.0D, 0.0D);
        this.sprites = sprite;
        this.lifetime = 40;
        float f = 1.0f; //this.random.nextFloat() * 0.6F + 0.4F;
        this.rCol = f;
        this.gCol = f;
        this.bCol = f;
        this.quadSize = 1.0F - (float) num * 0.5F;
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
            WindPoofParticle windPoof = new WindPoofParticle(p_199234_2_, p_199234_3_, p_199234_5_, p_199234_7_, p_199234_9_, this.sprites);
            windPoof.setColor(1.0f, 1.0f, 1.0f);
            return windPoof;
        }
    }
}
