package io.github.bioplethora.item.weapons;

import io.github.bioplethora.item.ItemSettings;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.*;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.List;

public class NandbricShortswordItem extends SwordItem {
    private LivingEntity target;

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

        entity.addEffect(new EffectInstance(Effects.POISON, 60, 5));

        entity.invulnerableTime = 5;

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
            double range = 24.0D;
            double distance = range * range;
            Vector3d vec = entity.getEyePosition(1);
            Vector3d vec1 = entity.getViewVector(1);
            Vector3d finalVec = vec.add(vec1.x * range, vec1.y * range, vec1.z * range);
            AxisAlignedBB aabb = entity.getBoundingBox().expandTowards(vec1.scale(range)).inflate(4.0D, 4.0D, 4.0D);
            EntityRayTraceResult result = ProjectileHelper.getEntityHitResult(entity, vec, finalVec, aabb, (filter) -> !filter.isSpectator() && filter.isPickable(), distance);

            if(result != null) {
                target = result.getEntity() instanceof LivingEntity ? (LivingEntity) result.getEntity() : null;
                entity.startUsingItem(hand);
                return ActionResult.success(itemstack);
            }
        }
        return ActionResult.fail(itemstack);
    }

    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        super.onUsingTick(stack, player, count);

        if(target != null && target.getEntity() instanceof LivingEntity && target.getEntity() != player) {
            World world = player.level;

            double vecX = target.getX() - player.getX();
            double vecY = player.getDeltaMovement().y;
            double vecZ = target.getZ() - player.getZ();
            double vecM = Math.sqrt((Math.pow(vecX, 2)) + Math.pow(vecY, 2) + Math.pow(vecZ, 2));
            float speedModifier = 2.5f;

            vecX = (vecX / vecM) * speedModifier;
            vecY = (vecY / vecM) * speedModifier;
            vecZ = (vecZ / vecM) * speedModifier;

            player.setDeltaMovement(vecX, vecY, vecZ);

            AxisAlignedBB hitrange = player.getBoundingBox().inflate(2.2D, 2.2D, 2.2D);

            if (hitrange.intersects(target.getBoundingBox())) {
                boolean flag = target.hurt(DamageSource.mobAttack(player), 7.0F);
                if(flag) {
                    target.addEffect(new EffectInstance(Effects.POISON, 60, 5));
                    player.doEnchantDamageEffects(player, target);
                    ((PlayerEntity)player).getCooldowns().addCooldown(stack.getItem(), 22);
                }

                if(world instanceof ServerWorld) {
                    double x = target.getX(), y = target.getY(), z = target.getZ();
                    BlockPos pos = new BlockPos(x, y, z);

                    ((ServerWorld) world).sendParticles(ParticleTypes.SNEEZE, x, y + (target.getBbHeight() / 2), z, 30, 1.2, 1.2, 1.2, 0);
                    world.playSound(null, pos, SoundEvents.ZOMBIE_INFECT, SoundCategory.PLAYERS, 1, 1);
                    world.playSound(null, pos, SoundEvents.PLAYER_ATTACK_SWEEP, SoundCategory.PLAYERS, 1, 1);
                }

                Hand hand = player.getUsedItemHand();
                player.swing(hand);
                player.stopUsingItem();
                if(player instanceof PlayerEntity) {
                    PlayerEntity wielder = (PlayerEntity)player;
                    wielder.awardStat(Stats.ITEM_USED.get(this));
                    if(!wielder.isCreative()) {
                        stack.hurtAndBreak(1, player, (user) -> user.broadcastBreakEvent(hand));
                    }
                }
            }

            if(player.getDeltaMovement().y > 0.1D) {
                player.getDeltaMovement().multiply(1.0D, 0.2D, 1.0D);
            }
        }
    }

    public void releaseUsing(ItemStack stack, World worldIn, LivingEntity entity, int value) {
        target = null;
    }
}
