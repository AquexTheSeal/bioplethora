package io.github.bioplethora.item.functionals;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class ActivatableItem extends Item {
    public boolean isActivated;

    public ActivatableItem(Properties pProperties) {
        super(pProperties);
        this.isActivated = false;
    }

    @Override
    public void inventoryTick(ItemStack pStack, World pLevel, Entity pEntity, int pItemSlot, boolean pIsSelected) {
        super.inventoryTick(pStack, pLevel, pEntity, pItemSlot, pIsSelected);

        if (this.isActivated) {
            this.activatedTick(pStack, pLevel, pEntity);
        }
    }

    /**
     * Ticks in inventory if this ActivatableItem is activated.
     */
    public void activatedTick(ItemStack pStack, World pLevel, Entity pEntity) {
    }

    @Override
    public ActionResult<ItemStack> use(World pLevel, PlayerEntity pPlayer, Hand pHand) {
        ItemStack stack = pPlayer.getItemInHand(pHand);

        if (pPlayer.isCrouching()) {
            activate(pLevel, pPlayer, stack);
            return ActionResult.success(stack);
        } else {
            return ActionResult.fail(stack);
        }
    }

    @Override
    public ActionResultType useOn(ItemUseContext pContext) {

        if (pContext.getPlayer().isCrouching()) {
            activate(pContext.getLevel(), pContext.getPlayer(), pContext.getItemInHand());
            return ActionResultType.SUCCESS;
        } else {
            return ActionResultType.FAIL;
        }
    }

    public void activate(World pLevel, PlayerEntity pPlayer, ItemStack stack) {

        pPlayer.getCooldowns().addCooldown(stack.getItem(), 60);

        if (!pPlayer.getCooldowns().isOnCooldown(stack.getItem())) {
            if (!getActivated()) {
                setActivated(true);
                pPlayer.playSound(getActivationSound(), 1.0F, 1.0F);
            } else {
                setActivated(false);
                pPlayer.playSound(getDeactivationSound(), 1.0F, 1.0F);
            }
        }
    }

    public boolean getActivated() {
        return isActivated;
    }

    public void setActivated(boolean activated) {
        isActivated = activated;
    }

    public SoundEvent getActivationSound() {
        return SoundEvents.ANVIL_USE;
    }

    public SoundEvent getDeactivationSound() {
        return SoundEvents.ANVIL_PLACE;
    }

    @Override
    public boolean isFoil(ItemStack pStack) {
        return this.isActivated;
    }
}
