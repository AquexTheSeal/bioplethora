package io.github.bioplethora.mixin_helpers;

public interface IAbstractArrowMixin {

    boolean getShouldExplode();

    void setShouldExplode(boolean shouldExplode);

    float getExplosionRadius();

    void setExplosionRadius(float explosionRadius);
}
