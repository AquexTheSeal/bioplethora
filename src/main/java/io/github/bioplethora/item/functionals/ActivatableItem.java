package io.github.bioplethora.item.functionals;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
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
        if (!this.isActivated) {
            this.isActivated = true;
            pPlayer.playSound(getActivationSound(), 1.0F, 1.0F);
        } else {
            this.isActivated = false;
            pPlayer.playSound(getDeactivationSound(), 1.0F, 1.0F);
        }
        return super.use(pLevel, pPlayer, pHand);
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
