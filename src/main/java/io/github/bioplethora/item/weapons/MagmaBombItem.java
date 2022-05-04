package io.github.bioplethora.item.weapons;

import io.github.bioplethora.entity.projectile.MagmaBombEntity;
import io.github.bioplethora.api.BPItemSettings;
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
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class MagmaBombItem extends Item {

    public MagmaBombItem(Item.Properties properties) {
        super(properties);
    }

    public ActionResult<ItemStack> use(World world, PlayerEntity entity, Hand handIn) {
        ItemStack itemstack = entity.getItemInHand(handIn);
        entity.startUsingItem(handIn);
        return ActionResult.consume(itemstack);
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

        int i = this.getUseDuration(stack) - value;
        if (i >= 10) {
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
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        BPItemSettings.sacredLevelText(tooltip);

        tooltip.add(new TranslationTextComponent("item.bioplethora.magma_bomb.bombardment.skill").withStyle(BPItemSettings.SKILL_NAME_COLOR));
        if (Screen.hasShiftDown() || Screen.hasControlDown()) {
            tooltip.add(new TranslationTextComponent("item.bioplethora.magma_bomb.bombardment.desc").withStyle(BPItemSettings.SKILL_DESC_COLOR));
        }
    }
}
