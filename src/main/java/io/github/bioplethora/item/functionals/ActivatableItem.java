package io.github.bioplethora.item.functionals;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class ActivatableItem extends Item {

    public String getFullActivatedText = "is_" + getItemName() + "_activated";
    public boolean isShining = false;

    public ActivatableItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void inventoryTick(ItemStack pStack, World pLevel, Entity pEntity, int pItemSlot, boolean pIsSelected) {

        CompoundNBT playerData = pEntity.getPersistentData();
        CompoundNBT data = playerData.getCompound(PlayerEntity.PERSISTED_NBT_TAG);

        if (data.getBoolean(getFullActivatedText)) {
            this.activatedTick(pStack, pLevel, pEntity);
            this.isShining = true;
        } else {
            this.isShining = false;
        }

        super.inventoryTick(pStack, pLevel, pEntity, pItemSlot, pIsSelected);
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

    public void activate(World pLevel, PlayerEntity pPlayer, ItemStack stack) {

        if (!pPlayer.getCooldowns().isOnCooldown(stack.getItem())) {
            if (!getActivated(pPlayer)) {
                setActivated(true, pPlayer);
                pPlayer.playSound(getActivationSound(), 1.0F, 1.0F);
            } else if (getActivated(pPlayer)) {
                setActivated(false, pPlayer);
                pPlayer.playSound(getDeactivationSound(), 1.0F, 1.0F);
            }

            pPlayer.getCooldowns().addCooldown(stack.getItem(), 60);
        }
    }

    public String getItemName() {
        return "activatable";
    }

    public boolean getActivated(LivingEntity entity) {
        CompoundNBT playerData = entity.getPersistentData();
        CompoundNBT data = playerData.getCompound(PlayerEntity.PERSISTED_NBT_TAG);

        return data.getBoolean(getFullActivatedText);
    }

    public void setActivated(boolean activated, LivingEntity entity) {
        CompoundNBT playerData = entity.getPersistentData();
        CompoundNBT data = playerData.getCompound(PlayerEntity.PERSISTED_NBT_TAG);

        data.putBoolean(getFullActivatedText, activated);
    }

    public SoundEvent getActivationSound() {
        return SoundEvents.ANVIL_USE;
    }

    public SoundEvent getDeactivationSound() {
        return SoundEvents.ANVIL_PLACE;
    }

    @Override
    public boolean isFoil(ItemStack pStack) {
        return this.isShining;
    }
}
