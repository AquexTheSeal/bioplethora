package io.github.bioplethora.blocks.api.mixin;

public interface IAbstractArrowMixin {

    boolean getShouldExplode();

    void setShouldExplode(boolean shouldExplode);

    float getExplosionRadius();

    void setExplosionRadius(float explosionRadius);
}
