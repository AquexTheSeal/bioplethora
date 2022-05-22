package io.github.bioplethora.enchantments.gaidius;

import io.github.bioplethora.enchantments.BPEnchantmentHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EquipmentSlotType;

public class RackingEdgeEnchantment extends Enchantment {

    public RackingEdgeEnchantment(Enchantment.Rarity rarity, EquipmentSlotType... slotTypes) {
        super(rarity, BPEnchantmentHelper.GAIDIUS, slotTypes);
    }

    public int getMinCost(int Int) {
        return 1 + (Int - 1) * 8;
    }

    public int getMaxCost(int Int) {
        return this.getMinCost(Int) + 15;
    }

    public int getMaxLevel() {
        return 3;
    }
}
