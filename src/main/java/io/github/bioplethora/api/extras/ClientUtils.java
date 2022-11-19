package io.github.bioplethora.api.extras;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.bioplethora.mixin.accessors.ParticleAccessor;
import io.github.bioplethora.mixin.accessors.SpriteTexturedParticleAccessor;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;

import java.util.function.Consumer;

public class ClientUtils {
    private static final Vector3f ROTATION_VECTOR = Util.make(new Vector3f(0.5F, 0.5F, 0.5F), Vector3f::normalize);
    private static final Vector3f TRANSFORM_VECTOR = new Vector3f(-1.0F, -1.0F, 0.0F);
    
    public static void renderRotatedParticle(SpriteTexturedParticle particle, IVertexBuilder vertexBuilder, ActiveRenderInfo activeRenderInfo, float partialTicks, Consumer<Quaternion> consumer) {
        Vector3d vec3 = activeRenderInfo.getPosition();
        float f = (float) (MathHelper.lerp(partialTicks, particle.xo, particle.x) - vec3.x());
        float f1 = (float) (MathHelper.lerp(partialTicks, particle.yo, particle.y) - vec3.y());
        float f2 = (float) (MathHelper.lerp(partialTicks, particle.zo, particle.z) - vec3.z());
        Quaternion quaternion = new Quaternion(ROTATION_VECTOR, 0.0F, true);
        consumer.accept(quaternion);
        TRANSFORM_VECTOR.transform(quaternion);
        Vector3f[] avector3f = new Vector3f[]{new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)};
        float f3 = particle.getQuadSize(partialTicks);

        for (int i = 0; i < 4; ++i) {
            Vector3f vector3f = avector3f[i];
            vector3f.transform(quaternion);
            vector3f.mul(f3);
            vector3f.add(f, f1, f2);
        }

        int j = ((ParticleAccessor) particle).invokeGetLightColor(partialTicks);
        makeCornerVertex(particle, vertexBuilder, avector3f[0], ((SpriteTexturedParticleAccessor) particle).invokeGetU1(), ((SpriteTexturedParticleAccessor) particle).invokeGetV1(), j);
        makeCornerVertex(particle, vertexBuilder, avector3f[1], ((SpriteTexturedParticleAccessor) particle).invokeGetU1(), ((SpriteTexturedParticleAccessor) particle).invokeGetV0(), j);
        makeCornerVertex(particle, vertexBuilder, avector3f[2], ((SpriteTexturedParticleAccessor) particle).invokeGetU0(), ((SpriteTexturedParticleAccessor) particle).invokeGetV0(), j);
        makeCornerVertex(particle, vertexBuilder, avector3f[3], ((SpriteTexturedParticleAccessor) particle).invokeGetU0(), ((SpriteTexturedParticleAccessor) particle).invokeGetV1(), j);
    }

    private static void makeCornerVertex(SpriteTexturedParticle particle, IVertexBuilder p_233994_, Vector3f p_233995_, float p_233996_, float p_233997_, int p_233998_) {
        p_233994_.vertex(p_233995_.x(), p_233995_.y(), p_233995_.z()).uv(p_233996_, p_233997_).color(particle.rCol, particle.gCol, particle.bCol, particle.alpha).uv2(p_233998_).endVertex();
    }
}
