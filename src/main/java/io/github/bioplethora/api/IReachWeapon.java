package io.github.bioplethora.api;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;

/**
 *
 */
public interface IReachWeapon {

    /**
     * @return Total Reach Distance (Default Distance {which is 4} + Additional Reach Distance)
     */
    double getReachDistance();

    /**
     * Place it on the Weapon's onEntitySwing method.
     */
    default boolean onEntitySwing(ItemStack stack, LivingEntity entity) {

        double range = getReachDistance();
        double distance = range * range;
        Vector3d vec = entity.getEyePosition(1);
        Vector3d vec1 = entity.getViewVector(1);
        Vector3d targetVec = vec.add(vec1.x * range, vec1.y * range, vec1.z * range);
        AxisAlignedBB aabb = entity.getBoundingBox().expandTowards(vec1.scale(range)).inflate(4.0D, 4.0D, 4.0D);
        EntityRayTraceResult result = ProjectileHelper.getEntityHitResult(entity, vec, targetVec, aabb, EntityPredicates.NO_CREATIVE_OR_SPECTATOR, distance);

        if ((result != null ? result.getEntity() : null) != null) {
            if (entity instanceof PlayerEntity) {
                ((PlayerEntity) entity).attack(result.getEntity());
            }
        }

        return false;
    }
}
