package io.github.bioplethora.particles;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.world.ClientWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
* @Credit MaxBogomol
*/
@OnlyIn(Dist.CLIENT)
public class WindPoofParticle extends SpriteTexturedParticle {

    private final IAnimatedSprite sprites;
    private float maxScale;
    private float maxAlpha;
    private float loseScale = 0.01F;

    private WindPoofParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, Color tint, double diameter, IAnimatedSprite sprites) {
        super(world, x, y, z, velocityX, velocityY, velocityZ);
        this.sprites = sprites;

        setColor(tint.getRed()/255.0F, tint.getGreen()/255.0F, tint.getBlue()/255.0F);
        setSize((float)diameter, (float)diameter);

        final float PARTICLE_SCALE_FOR_ONE_METRE = 0.5F;
        this.quadSize = PARTICLE_SCALE_FOR_ONE_METRE * (float)diameter;

        this.lifetime = 100;

        final float ALPHA_VALUE = 1.0F;
        this.alpha = ALPHA_VALUE;

        this.xd = velocityX; this.yd = velocityY; this.zd = velocityZ;

        this.hasPhysics = true;
        maxScale = this.quadSize;
        maxAlpha = this.alpha;
    }

    @Override
    protected int getLightColor(float partialTick)
    {
        final int BLOCK_LIGHT = 15;
        final int SKY_LIGHT = 15;
        final int FULL_BRIGHTNESS_VALUE = LightTexture.pack(BLOCK_LIGHT, SKY_LIGHT);
        return FULL_BRIGHTNESS_VALUE;
    }

    @Override
    public void tick() {
        xo = x; yo = y; zo = z;
        quadSize = quadSize - loseScale;
        alpha = alpha - (maxAlpha/(maxScale/loseScale));

        if (quadSize <=0) {
            this.remove();
        }

        move(xd, yd, zd);

        if (onGround) {
            this.remove();
        }

        if (yo == y && yd > 0) {
            this.remove();
        }

        if (this.age++ >= this.lifetime) {
            this.remove();
        }
    }

    public IParticleRenderType getRenderType() {
        return NORMAL_RENDER;
    }

    public static final void beginRenderCommon(BufferBuilder buffer, TextureManager manager) {
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        RenderSystem.alphaFunc(GL11.GL_GREATER, 0.003921569F);
        RenderSystem.disableLighting();

        manager.bind(AtlasTexture.LOCATION_PARTICLES);
        manager.getTexture(AtlasTexture.LOCATION_PARTICLES).setBlurMipmap(true, false);
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.PARTICLE);
    }

    public static final void endRenderCommon() {
        Minecraft.getInstance().textureManager.getTexture(AtlasTexture.LOCATION_PARTICLES).restoreLastBlurMipmap();
        RenderSystem.alphaFunc(GL11.GL_GREATER, 0.1F);
        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
    }

    public static final IParticleRenderType NORMAL_RENDER = new IParticleRenderType() {
        @Override
        public void begin(BufferBuilder pBuilder, TextureManager pTextureManager) {
            beginRenderCommon(pBuilder, pTextureManager);
        }

        @Override
        public void end(Tessellator pTesselator) {
            pTesselator.end();
            endRenderCommon();
        }

        @Override
        public String toString() {
            return "bioplethora:wind_poof";
        }
    };

    public static final IParticleRenderType DIW_RENDER = new IParticleRenderType() {
        @Override
        public void begin(BufferBuilder pBuilder, TextureManager pTextureManager) {
            beginRenderCommon(pBuilder, pTextureManager);
            RenderSystem.disableDepthTest();
        }

        @Override
        public void end(Tessellator pTesselator) {
            pTesselator.end();
            RenderSystem.enableDepthTest();
            endRenderCommon();
        }

        @Override
        public String toString() {
            return "bioplethora:no_depth_wind_poof";
        }
    };

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<WindPoofParticleData> {
        private final IAnimatedSprite sprites;

        public Factory(IAnimatedSprite p_i50563_1_) {
            this.sprites = p_i50563_1_;
        }

        public Particle createParticle(WindPoofParticleData flameParticleData, ClientWorld world, double xPos, double yPos, double zPos, double xVelocity, double yVelocity, double zVelocity) {
            WindPoofParticle windPoof = new WindPoofParticle(world, xPos, yPos, zPos, xVelocity, yVelocity, zVelocity,
                    flameParticleData.getTint(), flameParticleData.getDiameter(),
                    sprites);
            windPoof.pickSprite(sprites);
            return windPoof;
        }
    }
}
