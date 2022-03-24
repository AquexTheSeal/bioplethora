package io.github.bioplethora.entity.mixin_helpers;

public interface IAbstractArrowMixin {

    boolean getShouldExplode();

    void setShouldExplode(boolean shouldExplode);

    float getExplosionRadius();

    void setExplosionRadius(float explosionRadius);
}
