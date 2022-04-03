package io.github.bioplethora.item.weapons;

import io.github.bioplethora.item.ItemSettings;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.UseAction;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class NandbricShortswordItem extends SwordItem {
    private LivingEntity target;

    public LivingEntity getTarget() {
        return this.target;
    }

    public void setTarget(LivingEntity candidate) {
        this.target = candidate;
    }

    public NandbricShortswordItem(IItemTier tier, int attackDamageModifier, float attackSpeedModifier, Properties properties) {
        super(tier, attackDamageModifier, attackSpeedModifier, properties);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        ItemSettings.sacredLevelText(tooltip);

        tooltip.add(new TranslationTextComponent("item.bioplethora.nandbric_shortsword.fast_strike.skill").withStyle(ItemSettings.SKILL_NAME_COLOR));
        if(Screen.hasShiftDown() || Screen.hasControlDown()) {
            tooltip.add(new TranslationTextComponent("item.bioplethora.nandbric_shortsword.fast_strike.desc").withStyle(ItemSettings.SKILL_DESC_COLOR));
        }

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

        if(retval) {
            entity.addEffect(new EffectInstance(Effects.POISON, 60, 5));
            entity.invulnerableTime = 5;

            world.playSound(null, pos, SoundEvents.ZOMBIE_INFECT, SoundCategory.HOSTILE, 1, 1);
        }

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
            // Finds a target candidate by raytracing towards where the cursor points and checking for an entity
            double range = 24.0D;
            double distance = range * range;
            Vector3d vec = entity.getEyePosition(1);
            Vector3d vec1 = entity.getViewVector(1);
            Vector3d finalVec = vec.add(vec1.x * range, vec1.y * range, vec1.z * range);
            AxisAlignedBB aabb = entity.getBoundingBox().expandTowards(vec1.scale(range)).inflate(4.0D, 4.0D, 4.0D);
            EntityRayTraceResult result = ProjectileHelper.getEntityHitResult(entity, vec, finalVec, aabb, (filter) -> !filter.isSpectator() && filter != entity, distance);

            if(result != null) {
                setTarget(result.getEntity() instanceof LivingEntity? (LivingEntity)result.getEntity() : null);
                entity.startUsingItem(hand);
                return ActionResult.success(itemstack);
            }
        }
        return ActionResult.fail(itemstack);
    }

    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        super.onUsingTick(stack, player, count);

        Hand hand = player.getUsedItemHand();

        if(target != null) {
            double targetX = target.getX(),
                    targetY = target.getY(),
                    targetZ = target.getZ();
            BlockPos pos = new BlockPos(targetX, targetY, targetZ);
            World world = player.level;

            // Charging
            double vecX = targetX - player.getX();
            double vecY = player.getDeltaMovement().y;
            double vecZ = targetZ - player.getZ();
            double vecM = Math.sqrt((Math.pow(vecX, 2)) + Math.pow(vecY, 2) + Math.pow(vecZ, 2));
            float speedModifier = 2.5f;

            vecX = (vecX / vecM) * speedModifier;
            vecY = (vecY / vecM) * speedModifier;
            vecZ = (vecZ / vecM) * speedModifier;

            player.setDeltaMovement(vecX, vecY, vecZ);

            // For looking toward the target
            double d2 = target.getX() - player.getX();
            double d1 = target.getZ() - player.getZ();
            player.yRot = -((float) MathHelper.atan2(d2, d1)) * (180F / (float)Math.PI);

            // For dealing damage to target
            AxisAlignedBB hitrange = player.getBoundingBox().inflate(2.2D, 2.2D, 2.2D);
            if (hitrange.intersects(target.getBoundingBox())) {
                target.hurt(DamageSource.mobAttack(player), 7.0F);
                if(!world.isClientSide) {
                    EnchantmentHelper.doPostHurtEffects(target, player);
                    EnchantmentHelper.doPostDamageEffects(player, target);
                    target.addEffect(new EffectInstance(Effects.POISON, 300, 1));
                    ((PlayerEntity)player).getCooldowns().addCooldown(stack.getItem(), 22);
                }

                // Special FX
                world.playSound(null, pos, SoundEvents.ZOMBIE_INFECT, SoundCategory.PLAYERS, 1, 1);
                world.playSound(null, pos, SoundEvents.PLAYER_ATTACK_SWEEP, SoundCategory.PLAYERS, 1, 1);
                if(world instanceof ServerWorld) {
                    ((ServerWorld) world).sendParticles(ParticleTypes.SNEEZE, targetX, targetY + (target.getBbHeight() / 2), targetZ, 30, 1.2, 1.2, 1.2, 0);
                }

                // For awarding stats
                if(player instanceof PlayerEntity) {
                    ((PlayerEntity)player).awardStat(Stats.ITEM_USED.get(this));
                    if(!((PlayerEntity)player).abilities.instabuild) {
                        stack.hurtAndBreak(1, player, (user) -> user.broadcastBreakEvent(hand));
                    }
                }

                player.swing(hand);
                player.stopUsingItem();
            }

            // Prevents the user from being flung up into the air while using this weapon
            if(player.getDeltaMovement().y > 0.1D) {
                player.setDeltaMovement(player.getDeltaMovement().x(), 0.2D, player.getDeltaMovement().z());
            }
        } else {
            player.stopUsingItem();
            ((PlayerEntity)player).getCooldowns().addCooldown(stack.getItem(), 20);
        }
    }

    public void releaseUsing(ItemStack stack, World worldIn, LivingEntity entity, int value) {
        target = null;
    }
}
