package io.github.bioplethora.item.functionals;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class ActivatableItem extends Item implements ICurioItem {

    public String getFullActivatedText = "is_activated";
    public boolean isCurios;

    public ActivatableItem(Properties pProperties, boolean isCurios) {
        super(pProperties);
        this.isCurios = isCurios;
    }

    @Override
    public void inventoryTick(ItemStack pStack, World pLevel, Entity pEntity, int pItemSlot, boolean pIsSelected) {
        super.inventoryTick(pStack, pLevel, pEntity, pItemSlot, pIsSelected);

        if (this.getActivated(pStack)) {
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

    public void activate(World pLevel, PlayerEntity pPlayer, ItemStack stack) {

        if (!pPlayer.getCooldowns().isOnCooldown(stack.getItem())) {
            if (!getActivated(stack)) {
                setActivated(true, stack);
                pPlayer.playSound(getActivationSound(), 1.0F, 1.0F);
            } else if (getActivated(stack)) {
                setActivated(false, stack);
                pPlayer.playSound(getDeactivationSound(), 1.0F, 1.0F);
            }

            pPlayer.getCooldowns().addCooldown(stack.getItem(), 60);
        }
    }

    public boolean getActivated(ItemStack stack) {
        return stack.getOrCreateTag().getBoolean(getFullActivatedText);
    }

    public void setActivated(boolean activated, ItemStack stack) {
        stack.getOrCreateTag().putBoolean(getFullActivatedText, activated);
    }

    public SoundEvent getActivationSound() {
        return SoundEvents.ANVIL_USE;
    }

    public SoundEvent getDeactivationSound() {
        return SoundEvents.ANVIL_PLACE;
    }

    @Override
    public boolean isFoil(ItemStack pStack) {
        return this.getActivated(pStack);
    }

    @Override
    public boolean hasCurioCapability(ItemStack stack) {
        return this.isCurios;
    }

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {

        if (this.getActivated(stack)) {
            this.activatedTick(stack, livingEntity.level, livingEntity);
        }
    }
}
