package io.github.bioplethora.enchantments;

import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.item.AxeItem;
import net.minecraft.item.SwordItem;

public class BPEnchantmentHelper {

    public static EnchantmentType BP_WEAPON_AND_AXE = EnchantmentType.create("bp_weapon_and_axe", (item) -> (item instanceof SwordItem || item instanceof AxeItem));
}
