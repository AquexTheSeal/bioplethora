package io.github.bioplethora.gui.container;


import io.github.bioplethora.registry.BioplethoraBlocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IWorldPosCallable;

import javax.annotation.Nullable;

public abstract class AbstractReinforcingContainer extends Container {

    protected final CraftResultInventory resultSlots = new CraftResultInventory();
    protected final IInventory inputSlots = new Inventory(3) {
        public void setChanged() {
            super.setChanged();
            AbstractReinforcingContainer.this.slotsChanged(this);
        }
    };
    protected final IWorldPosCallable access;
    protected final PlayerEntity player;

    protected abstract boolean mayPickup(PlayerEntity pPlayer, boolean pHasStack);

    protected abstract ItemStack onTake(PlayerEntity pPlayer, ItemStack pInputItem);

    public AbstractReinforcingContainer(@Nullable ContainerType<?> pType, int pContainerId, PlayerInventory pPlayerInventory, IWorldPosCallable pAccess) {
        super(pType, pContainerId);
        this.access = pAccess;
        this.player = pPlayerInventory.player;

        addSlot(new Slot(this.inputSlots, 0, 44, 85 - 32));
        addSlot(new Slot(this.inputSlots, 1, 44, 67 - 32));

        addSlot(new Slot(this.inputSlots, 2, 44, 49 - 32));

        addSlot(new Slot(this.resultSlots, 3, 127, 37) {
            public boolean mayPlace(ItemStack pStack) {
                return false;
            }

            public boolean mayPickup(PlayerEntity pPlayer) {
                return AbstractReinforcingContainer.this.mayPickup(pPlayer, this.hasItem());
            }

            public ItemStack onTake(PlayerEntity pPlayer, ItemStack pStack) {
                return AbstractReinforcingContainer.this.onTake(pPlayer, pStack);
            }
        });

        for(int row = 0; row < 3; ++row) {
            for(int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(pPlayerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        for(int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(pPlayerInventory, col, 8 + col * 18, 142));
        }
    }

    public abstract void createResult();

    public void slotsChanged(IInventory pInventory) {
        super.slotsChanged(pInventory);
        if (pInventory == this.inputSlots) {
            this.createResult();
        }

    }

    public void removed(PlayerEntity pPlayer) {
        super.removed(pPlayer);
        this.access.execute((world, pos) -> this.clearContainer(pPlayer, world, this.inputSlots));
    }

    public boolean stillValid(PlayerEntity pPlayer) {
        return stillValid(access, player, BioplethoraBlocks.REINFORCING_TABLE.get());
    }

    protected boolean shouldQuickMoveToAdditionalSlot(ItemStack pStack) {
        return false;
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot sourceSlot = slots.get(index);

        if (sourceSlot != null && sourceSlot.hasItem()) {
            ItemStack itemstack1 = sourceSlot.getItem();
            itemstack = itemstack1.copy();
            if (index == 3) {
                if (!this.moveItemStackTo(itemstack1, 4, 39, true)) {
                    return ItemStack.EMPTY;
                }

                sourceSlot.onQuickCraft(itemstack1, itemstack);
            } else if (index != 0 && index != 1 && index != 2) {
                if (index >= 4 && index < 39) {
                    int i = this.shouldQuickMoveToAdditionalSlot(itemstack) ? 1 : 0;
                    if (!this.moveItemStackTo(itemstack1, i, 3, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.moveItemStackTo(itemstack1, 4, 39, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                sourceSlot.set(ItemStack.EMPTY);
            } else {
                sourceSlot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            sourceSlot.onTake(player, itemstack1);
        }

        return itemstack;
    }
}