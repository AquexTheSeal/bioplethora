package io.github.bioplethora.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;

public class HonedEnchantment extends Enchantment {

    public HonedEnchantment(Enchantment.Rarity rarity, EquipmentSlotType... slotTypes) {
        super(rarity, BPEnchantmentHelper.BP_WEAPON_AND_AXE, slotTypes);
    }

    public int getMinCost(int Int) {
        return 1 + (Int - 1) * 10;
    }

    public int getMaxCost(int Int) {
        return this.getMinCost(Int) + 15;
    }

    public int getMaxLevel() {
        return 3;
    }

    public boolean canEnchant(ItemStack stack) {
        return stack.getItem() instanceof AxeItem || super.canEnchant(stack);
    }

    @Override
    public void doPostAttack(LivingEntity pUser, Entity pTarget, int pLevel) {
        super.doPostAttack(pUser, pTarget, pLevel);

        if (pTarget instanceof LivingEntity) {
            pTarget.invulnerableTime = 5;
        }
        /*double var1;
        if (EnchantmentHelper.getItemEnchantmentLevel(BioplethoraEnchantments.HONED.get(), pUser.getOffhandItem()) <= EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SHARPNESS, pUser.getMainHandItem())) {
            var1 = EnchantmentHelper.getItemEnchantmentLevel(BioplethoraEnchantments.HONED.get(), pTarget instanceof LivingEntity ? ((LivingEntity) pTarget).getMainHandItem() : ItemStack.EMPTY);
        } else {
            var1 = EnchantmentHelper.getItemEnchantmentLevel(BioplethoraEnchantments.HONED.get(), pTarget instanceof LivingEntity ? ((LivingEntity) pTarget).getOffhandItem() : ItemStack.EMPTY);
        }

        if (pUser.level instanceof ServerWorld) {
            pUser.level.getServer().getCommands().performCommand((new CommandSource(ICommandSource.NULL, new Vector3d(pUser.getX(), pUser.getY(), pUser.getZ()), Vector2f.ZERO, (ServerWorld) pUser.level, 4, "", new StringTextComponent(""), pUser.level.getServer(), null)).withSuppressedOutput(),
                    "attribute @p minecraft:generic.attack_speed base set " + (4.0D + var1 * 0.75D));
        }*/
    }
}
