package io.github.bioplethora.event.helper;

import io.github.bioplethora.api.mixin.IAbstractArrowMixin;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.ProjectileImpactEvent;

public class ArrowMixinHelper {

    public static void onProjectileImpact(ProjectileImpactEvent event) {
        Entity entity = event.getEntity();
        World level = entity.level;
        double x = entity.getX(), y = entity.getY(), z = entity.getZ();

        if (entity instanceof AbstractArrowEntity) {
            AbstractArrowEntity arrow = (AbstractArrowEntity) entity;
            IAbstractArrowMixin mxArrow = (IAbstractArrowMixin) arrow;

            if (mxArrow.getShouldExplode()) {
                level.explode(null, x, y, z, mxArrow.getExplosionRadius(), Explosion.Mode.BREAK);

                // Wind Arrow Bug Fix
                arrow.remove();
            }
        }
    }
}
