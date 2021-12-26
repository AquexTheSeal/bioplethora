package io.github.bioplethora.item.weapons;

import com.google.common.collect.Lists;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.ICrossbowUser;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public class ArbitraryBallistaItem extends CrossbowItem {

    private boolean startSoundPlayed = false;
    private boolean midLoadSoundPlayed = false;

    public ArbitraryBallistaItem(Properties properties) {
        super(properties);
    }

    public Predicate<ItemStack> getSupportedHeldProjectiles() {
        return ARROW_OR_FIREWORK;
    }

    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return ARROW_ONLY;
    }

    public ActionResult<ItemStack> use(World world, PlayerEntity entity, Hand hand) {
        ItemStack itemstack = entity.getItemInHand(hand);
        if (isCharged(itemstack)) {
            performShooting(world, entity, hand, itemstack, getShootingPower(itemstack), 1.0F);
            setCharged(itemstack, false);

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

    public void releaseUsing(ItemStack p_77615_1_, World p_77615_2_, LivingEntity p_77615_3_, int p_77615_4_) {
        int i = this.getUseDuration(p_77615_1_) - p_77615_4_;
        float f = getPowerForTime(i, p_77615_1_);
        if (f >= 1.0F && !isCharged(p_77615_1_) && tryLoadProjectiles(p_77615_3_, p_77615_1_)) {
            setCharged(p_77615_1_, true);
            SoundCategory soundcategory = p_77615_3_ instanceof PlayerEntity ? SoundCategory.PLAYERS : SoundCategory.HOSTILE;
            p_77615_2_.playSound((PlayerEntity)null, p_77615_3_.getX(), p_77615_3_.getY(), p_77615_3_.getZ(), SoundEvents.CROSSBOW_LOADING_END, soundcategory, 1.0F, 1.0F / (random.nextFloat() * 0.5F + 1.0F) + 0.2F);
        }
    }

    private static boolean tryLoadProjectiles(LivingEntity p_220021_0_, ItemStack p_220021_1_) {
        int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.MULTISHOT, p_220021_1_);
        int j = i == 0 ? 1 : 3;
        boolean flag = p_220021_0_ instanceof PlayerEntity && ((PlayerEntity)p_220021_0_).abilities.instabuild;
        ItemStack itemstack = p_220021_0_.getProjectile(p_220021_1_);
        ItemStack itemstack1 = itemstack.copy();

        for(int k = 0; k < j; ++k) {
            if (k > 0) {
                itemstack = itemstack1.copy();
            }

            if (itemstack.isEmpty() && flag) {
                itemstack = new ItemStack(Items.ARROW);
                itemstack1 = itemstack.copy();
            }

            if (!loadProjectile(p_220021_0_, p_220021_1_, itemstack, k > 0, flag)) {
                return false;
            }
        }

        return true;
    }

    private static boolean loadProjectile(LivingEntity p_220023_0_, ItemStack p_220023_1_, ItemStack p_220023_2_, boolean p_220023_3_, boolean p_220023_4_) {
        if (p_220023_2_.isEmpty()) {
            return false;
        } else {
            boolean flag = p_220023_4_ && p_220023_2_.getItem() instanceof ArrowItem;
            ItemStack itemstack;
            if (!flag && !p_220023_4_ && !p_220023_3_) {
                itemstack = p_220023_2_.split(1);
                if (p_220023_2_.isEmpty() && p_220023_0_ instanceof PlayerEntity) {
                    ((PlayerEntity)p_220023_0_).inventory.removeItem(p_220023_2_);
                }
            } else {
                itemstack = p_220023_2_.copy();
            }

            addChargedProjectile(p_220023_1_, itemstack);
            return true;
        }
    }

    public static boolean isCharged(ItemStack p_220012_0_) {
        CompoundNBT compoundnbt = p_220012_0_.getTag();
        return compoundnbt != null && compoundnbt.getBoolean("Charged");
    }

    public static void setCharged(ItemStack p_220011_0_, boolean p_220011_1_) {
        CompoundNBT compoundnbt = p_220011_0_.getOrCreateTag();
        compoundnbt.putBoolean("Charged", p_220011_1_);
    }

    private static void addChargedProjectile(ItemStack p_220029_0_, ItemStack p_220029_1_) {
        CompoundNBT compoundnbt = p_220029_0_.getOrCreateTag();
        ListNBT listnbt;
        if (compoundnbt.contains("ChargedProjectiles", 9)) {
            listnbt = compoundnbt.getList("ChargedProjectiles", 10);
        } else {
            listnbt = new ListNBT();
        }

        CompoundNBT compoundnbt1 = new CompoundNBT();
        p_220029_1_.save(compoundnbt1);
        listnbt.add(compoundnbt1);
        compoundnbt.put("ChargedProjectiles", listnbt);
    }

    private static List<ItemStack> getChargedProjectiles(ItemStack p_220018_0_) {
        List<ItemStack> list = Lists.newArrayList();
        CompoundNBT compoundnbt = p_220018_0_.getTag();
        if (compoundnbt != null && compoundnbt.contains("ChargedProjectiles", 9)) {
            ListNBT listnbt = compoundnbt.getList("ChargedProjectiles", 10);
            if (listnbt != null) {
                for(int i = 0; i < listnbt.size(); ++i) {
                    CompoundNBT compoundnbt1 = listnbt.getCompound(i);
                    list.add(ItemStack.of(compoundnbt1));
                }
            }
        }

        return list;
    }

    private static void clearChargedProjectiles(ItemStack p_220027_0_) {
        CompoundNBT compoundnbt = p_220027_0_.getTag();
        if (compoundnbt != null) {
            ListNBT listnbt = compoundnbt.getList("ChargedProjectiles", 9);
            listnbt.clear();
            compoundnbt.put("ChargedProjectiles", listnbt);
        }

    }

    public static boolean containsChargedProjectile(ItemStack p_220019_0_, Item p_220019_1_) {
        return getChargedProjectiles(p_220019_0_).stream().anyMatch((p_220010_1_) -> {
            return p_220010_1_.getItem() == p_220019_1_;
        });
    }

    private static void shootProjectile(World level, LivingEntity entity, Hand hand, ItemStack p_220016_3_, ItemStack p_220016_4_, float p_220016_5_, boolean p_220016_6_, float p_220016_7_, float p_220016_8_, float p_220016_9_) {

        double x = entity.getX(), y = entity.getY(), z = entity.getZ();
        BlockPos pos = entity.getEntity().blockPosition();

        if (!level.isClientSide) {
            boolean flag = p_220016_4_.getItem() == Items.FIREWORK_ROCKET;
            ProjectileEntity projectileentity;
            if (flag) {
                projectileentity = new FireworkRocketEntity(level, p_220016_4_, entity, entity.getX(), entity.getEyeY() - (double)0.15F, entity.getZ(), true);
            } else {
                projectileentity = getArrow(level, entity, p_220016_3_, p_220016_4_);
                if (p_220016_6_ || p_220016_9_ != 0.0F) {
                    ((AbstractArrowEntity)projectileentity).pickup = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
                    ((AbstractArrowEntity)projectileentity).isNoGravity();
                    ((AbstractArrowEntity)projectileentity).setBaseDamage(((AbstractArrowEntity) projectileentity).getBaseDamage() + 7.5);
                }
            }

            if ((!(level.isClientSide()))) {
                ((ServerWorld) level).sendParticles(ParticleTypes.CLOUD, x, y + 1.2, z, 25, 0.75, 0.75, 0.75, 0);
            }

            level.playSound((PlayerEntity)null, x, y, z, SoundEvents.WITHER_BREAK_BLOCK, SoundCategory.PLAYERS, 1, 1);

            if (entity instanceof ICrossbowUser) {
                ICrossbowUser icrossbowuser = (ICrossbowUser)entity;
                icrossbowuser.shootCrossbowProjectile(icrossbowuser.getTarget(), p_220016_3_, projectileentity, p_220016_9_);
            } else {
                Vector3d vector3d1 = entity.getUpVector(1.0F);
                Quaternion quaternion = new Quaternion(new Vector3f(vector3d1), p_220016_9_, true);
                Vector3d vector3d = entity.getViewVector(1.0F);
                Vector3f vector3f = new Vector3f(vector3d);
                vector3f.transform(quaternion);
                projectileentity.shoot((double)vector3f.x(), (double)vector3f.y(), (double)vector3f.z(), p_220016_7_, p_220016_8_);
            }

            p_220016_3_.hurtAndBreak(flag ? 3 : 1, entity, (p_220017_1_) -> {
                p_220017_1_.broadcastBreakEvent(hand);
            });
            level.addFreshEntity(projectileentity);
            level.playSound((PlayerEntity)null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.CROSSBOW_SHOOT, SoundCategory.PLAYERS, 1.0F, p_220016_5_);
        }
    }

    private static AbstractArrowEntity getArrow(World p_220024_0_, LivingEntity p_220024_1_, ItemStack p_220024_2_, ItemStack p_220024_3_) {
        ArrowItem arrowitem = (ArrowItem)(p_220024_3_.getItem() instanceof ArrowItem ? p_220024_3_.getItem() : Items.ARROW);
        AbstractArrowEntity abstractarrowentity = arrowitem.createArrow(p_220024_0_, p_220024_3_, p_220024_1_);
        if (p_220024_1_ instanceof PlayerEntity) {
            abstractarrowentity.setCritArrow(true);
        }

        abstractarrowentity.setSoundEvent(SoundEvents.CROSSBOW_HIT);
        abstractarrowentity.setShotFromCrossbow(true);
        int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PIERCING, p_220024_2_);
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
                shootProjectile(world, entity, hand, stack, itemstack, afloat[i], flag, projectile, shootProjectile, 0.0F);
            } else if (i == 1) {
                shootProjectile(world, entity, hand, stack, itemstack, afloat[i], flag, projectile, shootProjectile, -10.0F);
            } else if (i == 2) {
                shootProjectile(world, entity, hand, stack, itemstack, afloat[i], flag, projectile, shootProjectile, 10.0F);
            }
        }

        onCrossbowShot(world, entity, stack);
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
                world.playSound((PlayerEntity)null, entity.getX(), entity.getY(), entity.getZ(), soundevent, SoundCategory.PLAYERS, 0.5F, 1.0F);
            }

            if (f >= 0.5F && soundevent1 != null && !this.midLoadSoundPlayed) {
                this.midLoadSoundPlayed = true;
                world.playSound((PlayerEntity)null, entity.getX(), entity.getY(), entity.getZ(), soundevent1, SoundCategory.PLAYERS, 0.5F, 1.0F);
            }
        }
    }

    public int getUseDuration(ItemStack p_77626_1_) {
        return getChargeDuration(p_77626_1_) + 3;
    }

    public static int getChargeDuration(ItemStack p_220026_0_) {
        int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.QUICK_CHARGE, p_220026_0_);
        return i == 0 ? 25 : 25 - 5 * i;
    }

    public UseAction getUseAnimation(ItemStack p_77661_1_) {
        return UseAction.CROSSBOW;
    }

    private SoundEvent getStartSound(int p_220025_1_) {
        switch(p_220025_1_) {
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

    private static float getPowerForTime(int p_220031_0_, ItemStack p_220031_1_) {
        float f = (float)p_220031_0_ / (float)getChargeDuration(p_220031_1_);
        if (f > 1.0F) {
            f = 1.0F;
        }

        return f;
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        List<ItemStack> list = getChargedProjectiles(stack);
        if (isCharged(stack) && !list.isEmpty()) {
            ItemStack itemstack = list.get(0);
            tooltip.add((new TranslationTextComponent("item.minecraft.crossbow.projectile")).append(" ").append(itemstack.getDisplayName()));
            if (flag.isAdvanced() && itemstack.getItem() == Items.FIREWORK_ROCKET) {
                List<ITextComponent> list1 = Lists.newArrayList();
                Items.FIREWORK_ROCKET.appendHoverText(itemstack, world, list1, flag);
                if (!list1.isEmpty()) {
                    for(int i = 0; i < list1.size(); ++i) {
                        list1.set(i, (new StringTextComponent("  ")).append(list1.get(i)).withStyle(TextFormatting.GRAY));
                    }

                    tooltip.addAll(list1);
                }
            }
        }
        tooltip.add(new TranslationTextComponent("item.bioplethora.sacred_level.desc").withStyle(TextFormatting.AQUA));
        tooltip.add(new TranslationTextComponent("item.bioplethora.additions.desc").withStyle(TextFormatting.GOLD));
        tooltip.add(new TranslationTextComponent("item.bioplethora.arbitrary_ballista.desc_0").withStyle(TextFormatting.GRAY));
        tooltip.add(new TranslationTextComponent("item.bioplethora.special_skill.desc").withStyle(TextFormatting.GOLD));
        tooltip.add(new TranslationTextComponent("item.bioplethora.arbitrary_ballista.desc_1").withStyle(TextFormatting.GRAY));
    }

    private static float getShootingPower(ItemStack p_220013_0_) {
        return p_220013_0_.getItem() == Items.CROSSBOW && containsChargedProjectile(p_220013_0_, Items.FIREWORK_ROCKET) ? 1.6F : 3.15F;
    }

    public int getDefaultProjectileRange() {
        return 8;
    }
}
