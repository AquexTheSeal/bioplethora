package io.github.bioplethora.particles;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class NightGazeParticle extends RisingParticle {
    private final IAnimatedSprite sprites;

    protected NightGazeParticle(ClientWorld pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed, float pQuadSizeMultiplier, IAnimatedSprite pSprites) {
        super(pLevel, pX, pY, pZ, 0.1F, -0.1F, 0.1F, pXSpeed, pYSpeed, pZSpeed, pQuadSizeMultiplier, pSprites, 0.0F, 20, -5.0E-3D, false);
        this.sprites = pSprites;
        float f = 1.0F;
        this.rCol = f;
        this.gCol = f;
        this.bCol = f;
        this.lifetime = 80;
    }

    public int getLightColor(float pPartialTick) {
        return 15728880;
    }

    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            this.setSpriteFromAge(this.sprites);
            this.yd += 0.025D;
            this.move(this.xd, this.yd, this.zd);
            if (this.y == this.yo) {
                this.xd *= 1.1D;
                this.zd *= 1.1D;
            }

            this.xd *= (double)0.96F;
            this.yd *= (double)0.96F;
            this.zd *= (double)0.96F;
            if (this.onGround) {
                this.xd *= (double)0.7F;
                this.zd *= (double)0.7F;
            }

        }
    }

    public void render(IVertexBuilder pBuffer, ActiveRenderInfo pRenderInfo, float pPartialTicks) {
        super.render(pBuffer, pRenderInfo, pPartialTicks);
        this.alpha = 1.0F - MathHelper.clamp(((float)this.age + pPartialTicks) / (float)this.lifetime, 0.0F, 1.0F);
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite sprites;

        public Factory(IAnimatedSprite iAnimatedSprite) {
            this.sprites = iAnimatedSprite;
        }

        public Particle createParticle(BasicParticleType pType, ClientWorld pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            return new NightGazeParticle(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed, 1F, this.sprites);
        }
    }
}
