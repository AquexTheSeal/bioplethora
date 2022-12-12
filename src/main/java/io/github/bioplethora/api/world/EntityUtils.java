package io.github.bioplethora.api.world;

import io.github.bioplethora.api.mixin.IPlayerEntityMixin;
import io.github.bioplethora.entity.SummonableMonsterEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Predicate;

public class EntityUtils {

    public static Predicate<Entity> IsNotPet(Entity owner) {
        return (entity) -> {
            if (entity instanceof TameableEntity) {
                return ((TameableEntity) entity).getOwner() != owner;
            } else if (entity instanceof SummonableMonsterEntity) {
                return ((SummonableMonsterEntity) entity).getOwner() != owner;
            } else {
                return entity != owner;
            }
        };
    }

    public static void shakeNearbyPlayersScreen(LivingEntity mob, int radius, int timeInTicks) {
        double x = mob.getX(), y = mob.getY(), z = mob.getZ();
        AxisAlignedBB area = new AxisAlignedBB(x - (radius / 2d), y, z - (radius / 2d), x + (radius / 2d), y + (radius / 2d), z + (radius / 2d));
        World world = mob.level;

        for (PlayerEntity entityIterator : world.getEntitiesOfClass(PlayerEntity.class, area)) {
            ((IPlayerEntityMixin) entityIterator).setScreenShaking(timeInTicks);
        }
    }


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

    public static EquipmentSlotType getSlotTypeFromItem(ItemStack stack, LivingEntity living) {
        if (living.getMainHandItem() == stack) {
            return EquipmentSlotType.MAINHAND;

        } else if (living.getOffhandItem() == stack) {
            return EquipmentSlotType.OFFHAND;

        } else {
            return EquipmentSlotType.MAINHAND;
        }
    }

    public static void swingAHand(ItemStack stack, LivingEntity living) {
        if (living.getMainHandItem() == stack) {
            living.swing(Hand.MAIN_HAND);

        } else if (living.getOffhandItem() == stack) {
            living.swing(Hand.OFF_HAND);
        }
    }

    public static <T extends Entity> List<T> getEntitiesInArea(Class<? extends T> entityClass, World world, BlockPos pos, double xzRad, double yRad) {
        return getEntitiesInArea(entityClass, world, pos.getX(), pos.getY(), pos.getZ(), xzRad, yRad);
    }

    public static boolean isLiving(Entity entity) {
        return entity instanceof LivingEntity;
    }
}
