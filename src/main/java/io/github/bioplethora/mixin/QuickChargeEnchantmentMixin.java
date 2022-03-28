package io.github.bioplethora.mixin;

import io.github.bioplethora.item.weapons.AlphanumObliteratorItem;
import io.github.bioplethora.item.weapons.VermilionBladeItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.QuickChargeEnchantment;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(QuickChargeEnchantment.class)
public class QuickChargeEnchantmentMixin extends Enchantment {

    protected QuickChargeEnchantmentMixin(Rarity p_i46731_1_, EnchantmentType p_i46731_2_, EquipmentSlotType[] p_i46731_3_) {
        super(p_i46731_1_, p_i46731_2_, p_i46731_3_);
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return stack.getItem() instanceof VermilionBladeItem || stack.getItem() instanceof AlphanumObliteratorItem || super.canEnchant(stack);
    }
}
