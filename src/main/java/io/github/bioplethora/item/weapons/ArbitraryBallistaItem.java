package io.github.bioplethora.item.weapons;

import io.github.bioplethora.blocks.api.BPItemSettings;
import io.github.bioplethora.blocks.api.mixin.IAbstractArrowMixin;
import io.github.bioplethora.registry.BPEffects;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ICrossbowUser;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class ArbitraryBallistaItem extends CrossbowItem implements IVanishable {

    public static int drawTime = 3;
    private boolean startSoundPlayed = false;
    private boolean midLoadSoundPlayed = false;

    public ArbitraryBallistaItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity entity, Hand hand) {
        ItemStack itemstack = entity.getItemInHand(hand);
        if (isCharged(itemstack)) {
            performShooting(world, entity, hand, itemstack, getShootingPower(itemstack), 1.0F);
            setCharged(itemstack, false);

            for (LivingEntity area : world.getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(1))) {
                if (area != entity) {
                    area.hurt(DamageSource.explosion(entity), 5);
                }
            }

            entity.hurt(DamageSource.explosion((LivingEntity) null), 1);

            return ActionResult.consume(itemstack);
        } else if (!entity.getProjectile(itemstack).isEmpty()) {
            if (!isCharged(itemstack)) {
                this.startSoundPlayed = false;
                this.midLoadSoundPlayed = false;
                entity.startUsingItem(hand);
            }

            return ActionResult.consume(itemstack);
        } else {
            return ActionResult.fail(itemstack);
        }
    }

    @Override
    public void inventoryTick(ItemStack pStack, World pLevel, Entity pEntity, int pItemSlot, boolean pIsSelected) {
        super.inventoryTick(pStack, pLevel, pEntity, pItemSlot, pIsSelected);

        if (pEntity instanceof LivingEntity) {
            if (((LivingEntity) pEntity).getMainHandItem() == pStack || ((LivingEntity) pEntity).getOffhandItem() == pStack) {
                if (!((LivingEntity) pEntity).hasEffect(BPEffects.SPIRIT_FISSION.get())) {
                    ((LivingEntity) pEntity).addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 5));
                    ((LivingEntity) pEntity).addEffect(new EffectInstance(Effects.DIG_SLOWDOWN, 5));
                    ((LivingEntity) pEntity).addEffect(new EffectInstance(Effects.WEAKNESS, 5));
                }
            }
        }
    }

    private static void shootProjectile(World level, LivingEntity entity, Hand hand, ItemStack stack, ItemStack itemStack, float p_220016_5_, boolean b, float p_220016_7_, float p_220016_8_, float v) {

        double x = entity.getX(), y = entity.getY(), z = entity.getZ();

        if (!level.isClientSide) {
            boolean flag = itemStack.getItem() == Items.FIREWORK_ROCKET;
            ProjectileEntity projectileentity;
            if (flag) {
                projectileentity = new FireworkRocketEntity(level, itemStack, entity, entity.getX(), entity.getEyeY() - (double)0.15F, entity.getZ(), true);
            } else {
                projectileentity = getArrow(level, entity, stack, itemStack);
                if (b || v != 0.0F) {
                    ((AbstractArrowEntity)projectileentity).pickup = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
                }
            }

            if ((!(level.isClientSide()))) {
                ((ServerWorld) level).sendParticles(ParticleTypes.CLOUD, x, y + 1.2, z, 25, 0.75, 0.75, 0.75, 0.01);
                ((ServerWorld) level).sendParticles(ParticleTypes.EXPLOSION, x, y + 1.2, z, 25, 0.75, 0.75, 0.75, 0);
            }

            level.playSound(null, x, y, z, SoundEvents.GENERIC_EXPLODE, SoundCategory.PLAYERS, 1, 1);

            if (entity instanceof ICrossbowUser) {
                ICrossbowUser icrossbowuser = (ICrossbowUser)entity;
                icrossbowuser.shootCrossbowProjectile(icrossbowuser.getTarget(), stack, projectileentity, v);
            } else {
                Vector3d vector3d1 = entity.getUpVector(1.0F);
                Quaternion quaternion = new Quaternion(new Vector3f(vector3d1), v, true);
                Vector3d vector3d = entity.getViewVector(1.0F);
                Vector3f vector3f = new Vector3f(vector3d);
                vector3f.transform(quaternion);
                projectileentity.shoot(vector3f.x(), vector3f.y(), vector3f.z(), p_220016_7_, p_220016_8_);
            }

            stack.hurtAndBreak(flag ? 3 : 1, entity, (living) -> living.broadcastBreakEvent(hand));
            level.addFreshEntity(projectileentity);
            level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.CROSSBOW_SHOOT, SoundCategory.PLAYERS, 1.0F, p_220016_5_);
        }
    }

    private static AbstractArrowEntity getArrow(World world, LivingEntity entity, ItemStack stack, ItemStack stack1) {
        ArrowItem arrowitem = (ArrowItem)(stack1.getItem() instanceof ArrowItem ? stack1.getItem() : Items.ARROW);
        AbstractArrowEntity abstractarrowentity = arrowitem.createArrow(world, stack1, entity);
        if (entity instanceof PlayerEntity) {
            abstractarrowentity.setCritArrow(true);
        }

        abstractarrowentity.setSoundEvent(SoundEvents.CROSSBOW_HIT);
        abstractarrowentity.setShotFromCrossbow(true);
        abstractarrowentity.setNoGravity(true);
        abstractarrowentity.setBaseDamage(abstractarrowentity.getBaseDamage() * 1.75);

        ((IAbstractArrowMixin) abstractarrowentity).setExplosionRadius(3.0F);
        ((IAbstractArrowMixin) abstractarrowentity).setShouldExplode(true);

        int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PIERCING, stack);
        if (i > 0) {
            abstractarrowentity.setPierceLevel((byte)i);
        }

        return abstractarrowentity;
    }

    public static void performShooting(World world, LivingEntity entity, Hand hand, ItemStack stack, float projectile, float shootProjectile) {
        List<ItemStack> list = getChargedProjectiles(stack);
        float[] afloat = getShotPitches(entity.getRandom());

        for(int i = 0; i < list.size(); ++i) {
            ItemStack itemstack = list.get(i);
            boolean flag = entity instanceof PlayerEntity && ((PlayerEntity) entity).abilities.instabuild;
            if (!itemstack.isEmpty()) {
                shootProjectile(world, entity, hand, stack, itemstack, afloat[i] / 2F, flag, projectile, shootProjectile, 0.0F);
            } else if (i == 1) {
                shootProjectile(world, entity, hand, stack, itemstack, afloat[i] / 2F, flag, projectile, shootProjectile, -10.0F);
            } else if (i == 2) {
                shootProjectile(world, entity, hand, stack, itemstack, afloat[i] / 2F, flag, projectile, shootProjectile, 10.0F);
            }
        }

        onCrossbowShot(world, entity, stack);
    }

    @Override
    public void releaseUsing(ItemStack pStack, World pLevel, LivingEntity pEntityLiving, int pTimeLeft) {
        int i = this.getUseDuration(pStack) - pTimeLeft;
        float f = getPowerForTime(i, pStack);
        if (f >= 1.0F && !isCharged(pStack) && tryLoadProjectiles(pEntityLiving, pStack)) {
            setCharged(pStack, true);
            SoundCategory soundcategory = pEntityLiving instanceof PlayerEntity ? SoundCategory.PLAYERS : SoundCategory.HOSTILE;
            pLevel.playSound(null, pEntityLiving.getX(), pEntityLiving.getY(), pEntityLiving.getZ(), SoundEvents.CROSSBOW_LOADING_END, soundcategory, 1.0F, 1.0F / (random.nextFloat() * 0.5F + 1.0F) + 0.2F);
        }
    }

    public static float getPowerForTime(int pUseTime, ItemStack pCrossbowStack) {
        float f = (float)pUseTime / (float)getChargeDuration(pCrossbowStack);
        if (f > 1.0F) {
            f = 1.0F;
        }

        return f;
    }

    private static float[] getShotPitches(Random p_220028_0_) {
        boolean flag = p_220028_0_.nextBoolean();
        return new float[]{1.0F, getRandomShotPitch(flag), getRandomShotPitch(!flag)};
    }

    private static float getRandomShotPitch(boolean p_220032_0_) {
        float f = p_220032_0_ ? 0.63F : 0.43F;
        return 1.0F / (random.nextFloat() * 0.5F + 1.8F) + f;
    }

    private static void onCrossbowShot(World p_220015_0_, LivingEntity p_220015_1_, ItemStack p_220015_2_) {
        if (p_220015_1_ instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverplayerentity = (ServerPlayerEntity)p_220015_1_;
            if (!p_220015_0_.isClientSide) {
                CriteriaTriggers.SHOT_CROSSBOW.trigger(serverplayerentity, p_220015_2_);
            }

            serverplayerentity.awardStat(Stats.ITEM_USED.get(p_220015_2_.getItem()));
        }

        clearChargedProjectiles(p_220015_2_);
    }

    @Override
    public void onUseTick(World world, LivingEntity entity, ItemStack stack, int num) {
        if (!world.isClientSide) {
            int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.QUICK_CHARGE, stack);
            SoundEvent soundevent = this.getStartSound(i);
            SoundEvent soundevent1 = i == 0 ? SoundEvents.CROSSBOW_LOADING_MIDDLE : null;
            float f = (float)(stack.getUseDuration() - num) / (float)getChargeDuration(stack);
            if (f < 0.2F) {
                this.startSoundPlayed = false;
                this.midLoadSoundPlayed = false;
            }

            if (f >= 0.2F && !this.startSoundPlayed) {
                this.startSoundPlayed = true;
                world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), soundevent, SoundCategory.PLAYERS, 0.5F, 0.5F);
            }

            if (f >= 0.5F && soundevent1 != null && !this.midLoadSoundPlayed) {
                this.midLoadSoundPlayed = true;
                world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), soundevent1, SoundCategory.PLAYERS, 0.5F, 0.5F);
            }
        }
    }

    public int getUseDuration(ItemStack stack) {
        return getChargeDuration(stack) + 3;
    }

    public static int getChargeDuration(ItemStack stack) {
        int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.QUICK_CHARGE, stack);
        return i == 0 ? 25 * drawTime : (25 - 5 * i) * drawTime;
    }

    private SoundEvent getStartSound(int i) {
        switch(i) {
            case 1:
                return SoundEvents.CROSSBOW_QUICK_CHARGE_1;
            case 2:
                return SoundEvents.CROSSBOW_QUICK_CHARGE_2;
            case 3:
                return SoundEvents.CROSSBOW_QUICK_CHARGE_3;
            default:
                return SoundEvents.CROSSBOW_LOADING_START;
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);
        BPItemSettings.sacredLevelText(tooltip);

        tooltip.add(new TranslationTextComponent("item.bioplethora.arbitrary_ballista.heavy_duty_ballista.skill").withStyle(BPItemSettings.SKILL_NAME_COLOR));
        if (Screen.hasShiftDown() || Screen.hasControlDown()) {
            tooltip.add(new TranslationTextComponent("item.bioplethora.arbitrary_ballista.heavy_duty_ballista.desc").withStyle(BPItemSettings.SKILL_DESC_COLOR));
        }
    }

    private static float getShootingPower(ItemStack p_220013_0_) {
        return p_220013_0_.getItem() == Items.CROSSBOW && containsChargedProjectile(p_220013_0_, Items.FIREWORK_ROCKET) ? 1.6F : 3.15F;
    }

    @Override
    public int getDefaultProjectileRange() {
        return 8;
    }
}
