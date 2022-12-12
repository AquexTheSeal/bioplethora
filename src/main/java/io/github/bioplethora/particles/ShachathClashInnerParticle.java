package io.github.bioplethora.particles;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.bioplethora.api.extras.ClientUtils;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ShachathClashInnerParticle extends SpriteTexturedParticle {

    private ShachathClashInnerParticle(ClientWorld clientWorld, double p_i232384_2_, double p_i232384_4_, double p_i232384_6_, IAnimatedSprite sprite) {
        super(clientWorld, p_i232384_2_, p_i232384_4_, p_i232384_6_);
        this.lifetime = 10;
        float f = 1.0F;
        this.rCol = f;
        this.gCol = f;
        this.bCol = f;
        this.quadSize = 2.5F;
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
        return ShachathClashInnerParticle.CLASH_SPECIAL;
    }

    public static IParticleRenderType CLASH_SPECIAL = new IParticleRenderType() {
        public void begin(BufferBuilder pBuilder, TextureManager pTextureManager) {
            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(true);
            pTextureManager.bind(AtlasTexture.LOCATION_PARTICLES);
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            RenderSystem.alphaFunc(516, 0.003921569F);
            pBuilder.begin(7, DefaultVertexFormats.PARTICLE);
        }

        public void end(Tessellator pTesselator) {
            pTesselator.end();
        }

        public String toString() {
            return "CLASH_SPECIAL";
        }
    };
    
    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite sprites;

        public Factory(IAnimatedSprite iAnimatedSprite) {
            this.sprites = iAnimatedSprite;
        }

        public Particle createParticle(BasicParticleType pType, ClientWorld pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            ShachathClashInnerParticle particle = new ShachathClashInnerParticle(pLevel, pX, pY, pZ, this.sprites);
            particle.setColor(1.0f, 1.0f, 1.0f);
            return particle;
        }
    }
}
