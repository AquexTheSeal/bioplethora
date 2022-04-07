package io.github.bioplethora.api.world;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemUtils {

    public static void shootWithItem(LivingEntity shooter, ProjectileEntity projectile, World world) {
        projectile.setOwner(shooter);
        projectile.setPos(shooter.getX(), shooter.getY() + 1.5, shooter.getZ());
        projectile.shootFromRotation(projectile, shooter.xRot, shooter.yHeadRot, 0, 1F, 0);
        world.addFreshEntity(projectile);
    }

    public static void shootWithItemBreakable(LivingEntity shooter, ProjectileEntity projectile, World world, ItemStack stack, int breakAmount) {
        shootWithItem(shooter, projectile, world);
        if (!((PlayerEntity) shooter).isCreative()) {
            if (shooter.getMainHandItem() == stack) {
                stack.hurtAndBreak(breakAmount, shooter, (entity1) -> entity1.broadcastBreakEvent(EquipmentSlotType.MAINHAND));
            } else if (shooter.getOffhandItem() == stack) {
                stack.hurtAndBreak(breakAmount, shooter, (entity1) -> entity1.broadcastBreakEvent(EquipmentSlotType.OFFHAND));
            }
        }
    }

    public static float projAngleX(LivingEntity entity) {
        return -MathHelper.sin(entity.yHeadRot * ((float) Math.PI / 180F)) * MathHelper.cos(entity.xRot * ((float) Math.PI / 180F));
    }

    public static float projAngleY(LivingEntity entity) {
        return MathHelper.sin(entity.xRot * ((float) Math.PI / 180F));
    }

    public static float projAngleZ(LivingEntity entity) {
        return MathHelper.cos(entity.yHeadRot * ((float) Math.PI / 180F)) * MathHelper.cos(entity.xRot * ((float) Math.PI / 180F));
    }

    public static boolean checkCooldownUsable(LivingEntity entity, ItemStack stack) {
        if (entity instanceof PlayerEntity) {
            return !((PlayerEntity) entity).getCooldowns().isOnCooldown(stack.getItem());
        } else {
            return true;
        }
    }

    public static void setStackOnCooldown(LivingEntity entity, ItemStack stack, int cooldown, boolean checkGamemode) {
        if (entity instanceof PlayerEntity) {
            if (checkGamemode) {
                if (EntityPredicates.NO_CREATIVE_OR_SPECTATOR.test(entity)) {
                    ((PlayerEntity) entity).getCooldowns().addCooldown(stack.getItem(), cooldown);
                }
            } else {
                ((PlayerEntity) entity).getCooldowns().addCooldown(stack.getItem(), cooldown);
            }
        }
    }
}
