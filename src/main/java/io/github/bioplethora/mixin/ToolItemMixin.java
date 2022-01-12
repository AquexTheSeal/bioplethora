package io.github.bioplethora.mixin;

import io.github.bioplethora.entity.IBioplethoraEntityClass;
import io.github.bioplethora.registry.BioplethoraEnchantments;
import io.github.bioplethora.util.BioplethoraEntityClasses;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.StringTextComponent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//@Mixin(ToolItem.class)
public abstract class ToolItemMixin {

    public Item item;

    //@Inject(at = @At("HEAD"), method = "hurtEnemy")
    public void hurtEnemy(ItemStack stack, LivingEntity entity, LivingEntity source, CallbackInfoReturnable<Boolean> cir) {

        if ((EnchantmentHelper.getEnchantmentLevel(BioplethoraEnchantments.ECOHARMLESS.get(), source) != 0) && (entity instanceof IBioplethoraEntityClass)) {
            this.EnchEcoharmless(stack, entity, source);
        }
        if ((EnchantmentHelper.getEnchantmentLevel(BioplethoraEnchantments.PLETHONEUTRAL.get(), source) != 0) && (entity instanceof IBioplethoraEntityClass)) {
            this.EnchPlethoneutral(stack, entity, source);
        }
        if ((EnchantmentHelper.getEnchantmentLevel(BioplethoraEnchantments.DANGERUM.get(), source) != 0) && (entity instanceof IBioplethoraEntityClass)) {
            this.EnchDangerum(stack, entity, source);
        }
        if ((EnchantmentHelper.getEnchantmentLevel(BioplethoraEnchantments.HELLSENT.get(), source) != 0) && (entity instanceof IBioplethoraEntityClass)) {
            this.EnchHellsent(stack, entity, source);
        }
        if ((EnchantmentHelper.getEnchantmentLevel(BioplethoraEnchantments.ELDERIA.get(), source) != 0) && (entity instanceof IBioplethoraEntityClass)) {
            this.EnchElderia(stack, entity, source);
        }
    }

    public void EnchEcoharmless(ItemStack stack, LivingEntity entity, LivingEntity source) {
        if (((IBioplethoraEntityClass) entity).getBioplethoraClass() == BioplethoraEntityClasses.ECOHARMLESS) {
            entity.hurt(DamageSource.playerAttack((PlayerEntity) source), stack.getDamageValue() + EnchantmentHelper.getEnchantmentLevel(BioplethoraEnchantments.ECOHARMLESS.get(), source));

            ((PlayerEntity) source).displayClientMessage(new StringTextComponent("Antibio-Ecoharmless Level: "
                    + EnchantmentHelper.getEnchantmentLevel(BioplethoraEnchantments.ECOHARMLESS.get(), source)), (false));
        }
    }

    public void EnchPlethoneutral(ItemStack stack, LivingEntity entity, LivingEntity source) {
        if (((IBioplethoraEntityClass) entity).getBioplethoraClass() == BioplethoraEntityClasses.PLETHONEUTRAL) {
            entity.hurt(DamageSource.playerAttack((PlayerEntity) source), stack.getDamageValue() + EnchantmentHelper.getEnchantmentLevel(BioplethoraEnchantments.PLETHONEUTRAL.get(), source));

            ((PlayerEntity) source).displayClientMessage(new StringTextComponent("Antibio-Plethoneutral Level: "
                    + EnchantmentHelper.getEnchantmentLevel(BioplethoraEnchantments.PLETHONEUTRAL.get(), source)), (false));
        }
    }

    public void EnchDangerum(ItemStack stack, LivingEntity entity, LivingEntity source) {
        if (((IBioplethoraEntityClass) entity).getBioplethoraClass() == BioplethoraEntityClasses.DANGERUM) {
            entity.hurt(DamageSource.playerAttack((PlayerEntity) source), stack.getDamageValue() + EnchantmentHelper.getEnchantmentLevel(BioplethoraEnchantments.DANGERUM.get(), source));

            ((PlayerEntity) source).displayClientMessage(new StringTextComponent("Antibio-Dangerum Level: "
                    + EnchantmentHelper.getEnchantmentLevel(BioplethoraEnchantments.DANGERUM.get(), source)), (false));
        }
    }

    public void EnchHellsent(ItemStack stack, LivingEntity entity, LivingEntity source) {
        if (((IBioplethoraEntityClass) entity).getBioplethoraClass() == BioplethoraEntityClasses.HELLSENT) {
            entity.hurt(DamageSource.playerAttack((PlayerEntity) source), stack.getDamageValue() + EnchantmentHelper.getEnchantmentLevel(BioplethoraEnchantments.HELLSENT.get(), source));

            ((PlayerEntity) source).displayClientMessage(new StringTextComponent("Antibio-Hellsent Level: "
                    + EnchantmentHelper.getEnchantmentLevel(BioplethoraEnchantments.HELLSENT.get(), source)), (false));
        }
    }

    public void EnchElderia(ItemStack stack, LivingEntity entity, LivingEntity source) {
        if (((IBioplethoraEntityClass) entity).getBioplethoraClass() == BioplethoraEntityClasses.ELDERIA) {
            entity.hurt(DamageSource.playerAttack((PlayerEntity) source), stack.getDamageValue() + EnchantmentHelper.getEnchantmentLevel(BioplethoraEnchantments.ELDERIA.get(), source));

            ((PlayerEntity) source).displayClientMessage(new StringTextComponent("Antibio-Elderia Level: "
                    + EnchantmentHelper.getEnchantmentLevel(BioplethoraEnchantments.ELDERIA.get(), source)), (false));
        }
    }
}
