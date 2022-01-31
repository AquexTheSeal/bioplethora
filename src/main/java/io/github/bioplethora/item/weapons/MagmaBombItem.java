package io.github.bioplethora.item.weapons;

import io.github.bioplethora.entity.projectile.MagmaBombEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class MagmaBombItem extends Item {

    public MagmaBombItem(Item.Properties properties) {
        super(properties);
    }

    public ActionResult<ItemStack> use(World world, PlayerEntity entity, Hand hand) {
        ItemStack itemstack = entity.getItemInHand(hand);

        if (itemstack.getDamageValue() >= itemstack.getMaxDamage() - 1) {
            return ActionResult.fail(itemstack);
        } else {
            entity.startUsingItem(hand);
            return ActionResult.consume(itemstack);
        }
    }

    public UseAction getUseAnimation(ItemStack p_77661_1_) {
        return UseAction.SPEAR;
    }

    public int getUseDuration(ItemStack p_77626_1_) {
        return 72000;
    }

    @Override
    public void releaseUsing(ItemStack stack, World world, LivingEntity entity, int value) {
        super.releaseUsing(stack, world, entity, value);

        world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.BLAZE_SHOOT, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
        if (!world.isClientSide) {
            MagmaBombEntity magmaBombEntity = new MagmaBombEntity(world, entity);
            magmaBombEntity.setItem(stack);
            magmaBombEntity.setExplosionPower(3F);
            magmaBombEntity.shootFromRotation(entity, entity.xRot, entity.yRot, 0.0F, 1.5F, 1.0F);
            world.addFreshEntity(magmaBombEntity);
        }

        if (entity instanceof PlayerEntity) {
            PlayerEntity playerentity = (PlayerEntity) entity;

            playerentity.awardStat(Stats.ITEM_USED.get(this));
            if (!playerentity.abilities.instabuild) {
                stack.shrink(1);
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("item.bioplethora.sacred_level.desc").withStyle(TextFormatting.AQUA));
        tooltip.add(new TranslationTextComponent("item.bioplethora.shift_reminder.desc").withStyle(TextFormatting.GRAY));

        tooltip.add(new TranslationTextComponent("item.bioplethora.magma_bomb.bombardment.skill").withStyle(TextFormatting.GOLD));
        if (Screen.hasShiftDown() || Screen.hasControlDown()) {
            tooltip.add(new TranslationTextComponent("item.bioplethora.magma_bomb.bombardment.desc").withStyle(TextFormatting.GRAY));
        }
    }
}
