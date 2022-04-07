package io.github.bioplethora.api.world;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public class EntityUtils {

    public static void knockbackAwayFromUser(float force, LivingEntity user, LivingEntity target) {
        target.knockback(force, MathHelper.sin(user.yRot * ((float) Math.PI / 180F)), -MathHelper.cos(user.yRot * ((float) Math.PI / 180F)));
    }

    public static <T extends Entity> List<T> getEntitiesInArea(Class<? extends T> entityClass, World world, double x, double y, double z, double xzRad, double yRad) {
        return world.getEntitiesOfClass(entityClass, new AxisAlignedBB(
                x - (xzRad / 2d), y - (yRad / 2d), z - (xzRad / 2d),
                x + (xzRad / 2d), y + (yRad / 2d), z + (xzRad / 2d)
                )
        );
    }

    public static <T extends Entity> List<T> getEntitiesInArea(Class<? extends T> entityClass, World world, BlockPos pos, double xzRad, double yRad) {
        return getEntitiesInArea(entityClass, world, pos.getX(), pos.getY(), pos.getZ(), xzRad, yRad);
    }

    public static boolean isLiving(Entity entity) {
        return entity instanceof LivingEntity;
    }
}
