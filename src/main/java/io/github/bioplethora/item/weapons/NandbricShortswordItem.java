package io.github.bioplethora.item.weapons;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.UseAction;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class NandbricShortswordItem extends SwordItem {
    public NandbricShortswordItem(IItemTier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity entity, LivingEntity source) {
        boolean retval = super.hurtEnemy(stack, entity, source);

        World world = entity.level;
        double x = entity.getX(), y = entity.getY(), z = entity.getZ();
        BlockPos pos = new BlockPos(x, y, z);

        entity.addEffect(new EffectInstance(Effects.POISON, 5, 5));
        entity.hurtTime = 2;

        world.playSound(null, pos, SoundEvents.ZOMBIE_INFECT, SoundCategory.HOSTILE, 1, 1);

        return retval;
    }

    public UseAction getUseAnimation(ItemStack stack) {
        return UseAction.BLOCK;
    }

    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    public ActionResult<ItemStack> use(World world, PlayerEntity entity, Hand hand) {
        ItemStack itemstack = entity.getItemInHand(hand);
        if (entity.getCooldowns().isOnCooldown(itemstack.getItem())) {
            return ActionResult.fail(itemstack);
        } else {
            entity.startUsingItem(hand);
            return ActionResult.success(itemstack);
        }
    }

    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        super.onUsingTick(stack, player, count);

        double range = 12.0D;
        double distance = range * range;

        Vector3d vec = player.getEyePosition(1);
        Vector3d vec1 = player.getViewVector(1);
        Vector3d finalVec = vec.add(vec1.x * range, vec1.y * range, vec1.z * range);

        AxisAlignedBB aabb = player.getBoundingBox().expandTowards(vec1.scale(range)).inflate(4.0D, 4.0D, 4.0D);

        EntityRayTraceResult result = ProjectileHelper.getEntityHitResult(player, vec, finalVec, aabb, (filter) -> {
            return !filter.isSpectator() && filter.isPickable();
        }, distance);

        if(result != null) {
            LivingEntity target = (LivingEntity)result.getEntity();

            double distToTarget = target.distanceToSqr(player.getX(), player.getY(), player.getZ());
            double attackReachSqr = (player.getBbWidth() * 1.6F * player.getBbWidth() * 1.6F + target.getBbWidth());

            double vecX = target.getX() - player.getX();
            double vecY = player.getDeltaMovement().y;
            double vecZ = target.getZ() - player.getZ();
            double vecM = Math.sqrt((Math.pow(vecX, 2)) + Math.pow(vecY, 2) + Math.pow(vecZ, 2));
            float speedModifier = 2f;

            vecX = (vecX / vecM) * speedModifier;
            vecY = (vecY / vecM) * speedModifier;
            vecZ = (vecZ / vecM) * speedModifier;

            player.setDeltaMovement(vecX, vecY, vecZ);

            if (distToTarget <= attackReachSqr) {
                target.hurt(DamageSource.mobAttack(player), 7.0F);
                ((PlayerEntity) player).getCooldowns().addCooldown(stack.getItem(), 25);
                Hand hand = player.getUsedItemHand();
                player.swing(hand);
                player.stopUsingItem();
            }
        }
    }
}
