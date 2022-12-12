package io.github.bioplethora.api.mixin;

public interface IAbstractArrowMixin {

    boolean getShouldExplode();

    void setShouldExplode(boolean shouldExplode);

    float getExplosionRadius();

    void setExplosionRadius(float explosionRadius);
}
