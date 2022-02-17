package io.github.bioplethora.item.weapons;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class AbyssalBladeItem extends SwordItem {

    public AbyssalBladeItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Item.Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
    }

    public UseAction getUseAnimation(ItemStack p_77661_1_) {
        return UseAction.SPEAR;
    }

    public void inventoryTick(ItemStack stack, World world, Entity entity, int p_77663_4_, boolean p_77663_5_) {
        super.inventoryTick(stack, world, entity, p_77663_4_, p_77663_5_);

        LivingEntity living = (LivingEntity)entity;
        if ((living.getMainHandItem().getItem() == stack.getItem()) || (living.getOffhandItem().getItem() == stack.getItem())) {
            living.addEffect(new EffectInstance(Effects.WATER_BREATHING, 5));
        }

        // Wastelands of Baedoor Integration
        if (!stack.getOrCreateTag().getBoolean("is_NBT_set")) {
            stack.getOrCreateTag().putBoolean("is_NBT_set", true);

            stack.getOrCreateTag().putDouble("sabre_defence", 10);
            stack.getOrCreateTag().putDouble("sabre_cooldown", 15);
            stack.getOrCreateTag().putDouble("sabre_harm", 1);
        }
    }

    public int getUseDuration(ItemStack p_77626_1_) {
        return 72000;
    }

    public ActionResult<ItemStack> use(World world, PlayerEntity entity, Hand handIn) {
        ItemStack itemstack = entity.getItemInHand(handIn);
        if (itemstack.getDamageValue() >= itemstack.getMaxDamage() - 1) {
            return ActionResult.fail(itemstack);
        } else {
            entity.startUsingItem(handIn);
            return ActionResult.consume(itemstack);
        }
    }

    public void releaseUsing(ItemStack stack, World world, LivingEntity entity, int value) {
        super.releaseUsing(stack, world, entity, value);

        if (entity instanceof PlayerEntity) {
            PlayerEntity playerentity = (PlayerEntity)entity;

            int i = this.getUseDuration(stack) - value;
            if (i >= 10) {
                if (!playerentity.getCooldowns().isOnCooldown(stack.getItem())) {
                    float f7 = playerentity.yRot;
                    float f = playerentity.xRot;
                    float f1 = -MathHelper.sin(f7 * ((float) Math.PI / 180F)) * MathHelper.cos(f * ((float) Math.PI / 180F));
                    float f2 = -MathHelper.sin(f * ((float) Math.PI / 180F));
                    float f3 = MathHelper.cos(f7 * ((float) Math.PI / 180F)) * MathHelper.cos(f * ((float) Math.PI / 180F));
                    float f4 = MathHelper.sqrt(f1 * f1 + f2 * f2 + f3 * f3);
                    float f5 = 3.0F; //* ((1.0F + (float) j) / 4.0F);
                    f1 = f1 * (f5 / f4);
                    f2 = f2 * (f5 / f4);
                    f3 = f3 * (f5 / f4);
                    playerentity.push((double) f1, (double) f2, (double) f3);
                    playerentity.startAutoSpinAttack(20);
                    if (playerentity.isOnGround()) {
                        float f6 = 1.1999999F;
                        playerentity.move(MoverType.SELF, new Vector3d(0.0D, (double) f6, 0.0D));
                    }
                    playerentity.awardStat(Stats.ITEM_USED.get(this));

                    world.playSound(null, playerentity, SoundEvents.TRIDENT_RIPTIDE_3, SoundCategory.PLAYERS, 1.0F, 1.0F);

                    playerentity.getCooldowns().addCooldown(stack.getItem(), 20);
                }
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("item.bioplethora.sacred_level.desc").withStyle(TextFormatting.AQUA));
        tooltip.add(new TranslationTextComponent("item.bioplethora.shift_reminder.desc").withStyle(TextFormatting.GRAY));

        tooltip.add(new TranslationTextComponent("item.bioplethora.abyssal_blade.tridented_blade.skill").withStyle(TextFormatting.GOLD));
        if (Screen.hasShiftDown() || Screen.hasControlDown()) {
            tooltip.add(new TranslationTextComponent("item.bioplethora.abyssal_blade.tridented_blade.desc").withStyle(TextFormatting.GRAY));
        }
    }
}
