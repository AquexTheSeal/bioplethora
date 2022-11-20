package io.github.bioplethora.particles;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.bioplethora.api.extras.ClientUtils;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FrostbiteEyeParticle extends SpriteTexturedParticle {

    private FrostbiteEyeParticle(ClientWorld clientWorld, double p_i232384_2_, double p_i232384_4_, double p_i232384_6_, IAnimatedSprite sprite) {
        super(clientWorld, p_i232384_2_, p_i232384_4_, p_i232384_6_);
        this.lifetime = 20;
        float f = 1.0F;
        this.rCol = f;
        this.gCol = f;
        this.bCol = f;
        this.quadSize = 1.0F;
        this.setSpriteFromAge(sprite);
    }

    public int getLightColor(float pPartialTick) {
        return 15728880;
    }

    public float getQuadSize(float p_234003_) {
        return this.quadSize * MathHelper.clamp(((float)this.age + p_234003_) / (float)this.lifetime * 0.75F, 0.0F, 1.0F);
    }

    public void tick() {
        if (this.age++ >= this.lifetime) {
            this.remove();
        }
    }

    public void render(IVertexBuilder pBuffer, ActiveRenderInfo pRenderInfo, float pPartialTicks) {
        super.render(pBuffer, pRenderInfo, pPartialTicks);
        this.alpha = 1.0F - MathHelper.clamp(((float)this.age + pPartialTicks) / (float)this.lifetime, 0.0F, 1.0F);
    }

    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite sprites;

        public Factory(IAnimatedSprite iAnimatedSprite) {
            this.sprites = iAnimatedSprite;
        }

        public Particle createParticle(BasicParticleType pType, ClientWorld pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            FrostbiteEyeParticle particle = new FrostbiteEyeParticle(pLevel, pX, pY, pZ, this.sprites);
            particle.setColor(1.0f, 1.0f, 1.0f);
            particle.setAlpha(1.0F);
            return particle;
        }
    }
}
