package io.github.bioplethora.mixin;

import io.github.bioplethora.helpers.mixin.IAbstractArrowMixin;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AbstractArrowEntity.class)
public class AbstractArrowEntityMixin implements IAbstractArrowMixin {

    boolean shouldExplode;
    float explosionRadius;

    @Override
    public boolean getShouldExplode() {
        return this.shouldExplode;
    }

    @Override
    public void setShouldExplode(boolean shouldExplode) {
        this.shouldExplode = shouldExplode;
    }

    @Override
    public float getExplosionRadius() {
        return this.explosionRadius;
    }

    @Override
    public void setExplosionRadius(float explosionRadius) {
        this.explosionRadius = explosionRadius;
    }
}
