package io.github.bioplethora.item.weapons;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.item.UseAction;
import net.minecraft.world.World;

public abstract class GrylynenShieldBaseItem extends ShieldItem {
    public boolean canBeCooldown = false;

    /**
     * @return Default cooldown it will take from Axes.
     */
    public abstract int getCooldownReduction();

    /**
     * @return How much durability will the armor used by the user recover everytime the shield gets damaged?
     */
    public abstract int getArmorBonus();

    public GrylynenShieldBaseItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isShield(ItemStack stack, LivingEntity entity) {
        return true;
    }

    @Override
    public int getUseDuration(ItemStack itemStack) {
        return 75000;
    }

    @Override
    public UseAction getUseAnimation(ItemStack itemStack) {
        return UseAction.BLOCK;
    }

    @Override
    public void inventoryTick(ItemStack pStack, World pLevel, Entity pEntity, int pItemSlot, boolean pIsSelected) {
        super.inventoryTick(pStack, pLevel, pEntity, pItemSlot, pIsSelected);

        if (pEntity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) pEntity;

            if (!player.getCooldowns().isOnCooldown(pStack.getItem())) {
                canBeCooldown = false;
            }

            if (!canBeCooldown) {
                if (player.getCooldowns().getCooldownPercent(pStack.getItem(), 0.0F) >= 0.9F) {
                    player.getCooldowns().removeCooldown(pStack.getItem());
                    player.getCooldowns().addCooldown(pStack.getItem(), getCooldownReduction());
                    canBeCooldown = true;
                }
            }
        }
    }
}
