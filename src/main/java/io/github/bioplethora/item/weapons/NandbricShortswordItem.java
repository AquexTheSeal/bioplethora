package io.github.bioplethora.item.weapons;

import io.github.bioplethora.item.ItemSettings;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
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
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class NandbricShortswordItem extends SwordItem {
    public NandbricShortswordItem(IItemTier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        ItemSettings.sacredLevelText(tooltip);

        tooltip.add(new TranslationTextComponent("item.bioplethora.nandbric_shortsword.toxin_rush.skill").withStyle(ItemSettings.SKILL_NAME_COLOR));
        if(Screen.hasShiftDown() || Screen.hasControlDown()) {
            tooltip.add(new TranslationTextComponent("item.bioplethora.nandbric_shortsword.toxin_rush.desc").withStyle(ItemSettings.SKILL_DESC_COLOR));
        }
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

        if(result.getEntity() instanceof LivingEntity) {
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
                World world = player.level;
                if(!world.isClientSide) {
                    world.playSound(null, new BlockPos(player.getX(), player.getY(), player.getZ()), SoundEvents.ZOMBIE_INFECT, SoundCategory.PLAYERS, 1, 1);
                    world.playSound(null, new BlockPos(player.getX(), player.getY(), player.getZ()), SoundEvents.PLAYER_ATTACK_SWEEP, SoundCategory.PLAYERS, 1, 1);
                }
                target.hurt(DamageSource.mobAttack(player), 7.0F);
                target.addEffect(new EffectInstance(Effects.POISON, 100, 7));
                ((PlayerEntity) player).getCooldowns().addCooldown(stack.getItem(), 22);
                Hand hand = player.getUsedItemHand();
                player.swing(hand);
                player.stopUsingItem();
            }

            if(player.getDeltaMovement().y > 0.7D) {
                player.setDeltaMovement(player.getDeltaMovement().x, player.getDeltaMovement().y * 0.2, player.getDeltaMovement().z);
            }
        }
    }
}
