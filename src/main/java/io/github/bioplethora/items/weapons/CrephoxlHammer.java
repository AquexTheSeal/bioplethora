package io.github.bioplethora.items.weapons;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.enums.BioplethoraRarity;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import java.util.List;

public class CrephoxlHammer extends AxeItem {

    public CrephoxlHammer(IItemTier tier, int attackDamageIn, float attackSpeedIn, Item.Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity entity, LivingEntity source) {
        boolean retval = super.hurtEnemy(stack, entity, source);

        //Adds debuffs to target after hit.
        entity.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 5, 2));
        entity.addEffect(new EffectInstance(Effects.WEAKNESS, 4, 1));
        entity.addEffect(new EffectInstance(Effects.DIG_SLOWDOWN, 3, 1));
        entity.addEffect(new EffectInstance(Effects.CONFUSION, 5, 1));

        //Deals more damage to Entities over 50 max health.
        if (((LivingEntity) entity).getMaxHealth() >= 50) {
            entity.hurt(DamageSource.GENERIC, getAttackDamage() * 2);
        }
        return retval;
    }

    @Override
    public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        list.add(new StringTextComponent("\u00A77Debuffs targets. Deals more damage to mobs with more than 50 maximum health."));
    }
}
