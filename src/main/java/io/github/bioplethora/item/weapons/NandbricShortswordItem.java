package io.github.bioplethora.item.weapons;

import io.github.bioplethora.api.BPItemSettings;
import io.github.bioplethora.config.BPConfig;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
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
    private boolean using;

    public NandbricShortswordItem(IItemTier tier, int attackDamageModifier, float attackSpeedModifier, Properties properties) {
        super(tier, attackDamageModifier, attackSpeedModifier, properties);
        this.target = null;
        this.using = false;
    }

    public void inventoryTick(ItemStack itemstack, World world, Entity entity, int slot, boolean selected) {
        LivingEntity attacker = (LivingEntity)entity;
        if(selected) {
            if(using) {
                Hand hand = attacker.getUsedItemHand();
                BlockPos attackerPos = new BlockPos(attacker.getX(), attacker.getY(), attacker.getZ());

                if (this.target != null) {
                    AxisAlignedBB hitrange = attacker.getBoundingBox().inflate(2);

                    if (hitrange.intersects(this.target.getBoundingBox())) {
                        this.target.hurt(DamageSource.mobAttack(attacker), 7);
                        this.target.addEffect(new EffectInstance(Effects.POISON, 200, 1));
                        if (attacker instanceof PlayerEntity) {
                            ((PlayerEntity) attacker).getCooldowns().addCooldown(itemstack.getItem(), 22);
                            ((PlayerEntity) attacker).awardStat(Stats.ITEM_USED.get(this));
                            if (!((PlayerEntity) attacker).abilities.instabuild) {
                                itemstack.hurtAndBreak(1, attacker, (user) -> user.broadcastBreakEvent(hand));
                            }
                        }
                        world.playSound(null, attackerPos, SoundEvents.ZOMBIE_INFECT, SoundCategory.PLAYERS, 1, 1);
                        world.playSound(null, attackerPos, SoundEvents.PLAYER_ATTACK_SWEEP, SoundCategory.PLAYERS, 1, 1);
                        if (world instanceof ServerWorld) {
                            ((ServerWorld) world).sendParticles(ParticleTypes.SNEEZE, this.target.getX(), this.target.getY() - (this.target.getBbHeight() / 2), this.target.getZ(), 30, 0.8, 1.2, 0.8, 0);
                        }
                        using = false;
                    } else {
                        double yDifference = this.target.getY() - attacker.getY();
                        boolean flag = yDifference <= 3 && yDifference > -1;
                        boolean canUseInNonCreative = attacker.getY() > this.target.getY() || flag;
                        double vecX = this.target.getX() - attacker.getX(),
                                vecY = this.target.getY() - attacker.getY(),
                                vecZ = this.target.getZ() - attacker.getZ();
                        double vecM = Math.sqrt(Math.pow(vecX, 2) + Math.pow(vecY, 2) + Math.pow(vecZ, 2));
                        final double speedModifier = BPConfig.COMMON.toxinRushSpeedModifier.get();
                        vecX = (vecX / vecM) * speedModifier;
                        vecY = (vecY / vecM) * speedModifier;
                        vecZ = (vecZ / vecM) * speedModifier;

                        if (((PlayerEntity) attacker).abilities.instabuild || canUseInNonCreative) {
                            attacker.setDeltaMovement(vecX, vecY, vecZ);
                        } else using = false;
                    }
                } else using = false;
            }
        } else using = false;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        BPItemSettings.sacredLevelText(tooltip);

        tooltip.add(new TranslationTextComponent("item.bioplethora.nandbric_shortsword.fast_strike.skill").withStyle(BPItemSettings.SKILL_NAME_COLOR));
        if(Screen.hasShiftDown() || Screen.hasControlDown()) {
            tooltip.add(new TranslationTextComponent("item.bioplethora.nandbric_shortsword.fast_strike.desc").withStyle(BPItemSettings.SKILL_DESC_COLOR));
        }

        tooltip.add(new TranslationTextComponent("item.bioplethora.nandbric_shortsword.toxin_rush.skill").withStyle(BPItemSettings.SKILL_NAME_COLOR));
        if(Screen.hasShiftDown() || Screen.hasControlDown()) {
            tooltip.add(new TranslationTextComponent("item.bioplethora.nandbric_shortsword.toxin_rush.desc").withStyle(BPItemSettings.SKILL_DESC_COLOR));
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

    @Override
    public boolean onEntitySwing(ItemStack itemstack, LivingEntity entity) {
        double range = 24.0D;
        double distance = range * range;
        Vector3d vec = entity.getEyePosition(1);
        Vector3d vec1 = entity.getViewVector(1);
        Vector3d targetVec = vec.add(vec1.x * range, vec1.y * range, vec1.z * range);
        AxisAlignedBB aabb = entity.getBoundingBox().expandTowards(vec1.scale(range)).inflate(4.0D, 4.0D, 4.0D);
        EntityRayTraceResult result = ProjectileHelper.getEntityHitResult(entity, vec, targetVec, aabb, (filter) -> !filter.isSpectator() && filter != entity, distance);
        boolean flag = result != null && result.getEntity() instanceof LivingEntity && result.getEntity().isAlive();

        if(flag && !((PlayerEntity)entity).getCooldowns().isOnCooldown(itemstack.getItem())) {
            this.target = (LivingEntity)result.getEntity();
            this.using = true;
        }
        return super.onEntitySwing(itemstack, entity);
    }
}
