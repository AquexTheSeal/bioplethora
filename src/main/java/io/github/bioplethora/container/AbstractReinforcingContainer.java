package io.github.bioplethora.container;


import net.minecraft.block.BlockState;
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

    protected abstract boolean isValidBlock(BlockState pState);

    public AbstractReinforcingContainer(@Nullable ContainerType<?> pType, int pContainerId, PlayerInventory pPlayerInventory, IWorldPosCallable pAccess) {
        super(pType, pContainerId);
        this.access = pAccess;
        this.player = pPlayerInventory.player;

        addSlot(new Slot(this.inputSlots, 0, 27, 47));
        addSlot(new Slot(this.inputSlots, 1, 76, 47));
        addSlot(new Slot(this.inputSlots, 2, 134, 47));

        addSlot(new Slot(this.resultSlots, 2, 175, 47) {
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

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(pPlayerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(pPlayerInventory, k, 8 + k * 18, 142));
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
        this.access.execute((p_234647_2_, p_234647_3_) -> {
            this.clearContainer(pPlayer, p_234647_2_, this.inputSlots);
        });
    }

    public boolean stillValid(PlayerEntity pPlayer) {
        return this.access.evaluate((p_234646_2_, p_234646_3_) ->
                this.isValidBlock(p_234646_2_.getBlockState(p_234646_3_)) && pPlayer.distanceToSqr((double) p_234646_3_.getX() + 0.5D, (double) p_234646_3_.getY() + 0.5D, (double) p_234646_3_.getZ() + 0.5D) <= 64.0D, true);
    }

    // must assign a slot number to each of the slots used by the GUI.
    // For this container, we can see both the tile inventory's slots as well as the player inventory slots and the hotbar.
    // Each time we add a Slot to the container, it automatically increases the slotIndex, which means
    //  0 - 8 = hotbar slots (which will map to the InventoryPlayer slot numbers 0 - 8)
    //  9 - 35 = player inventory slots (which map to the InventoryPlayer slot numbers 9 - 35)
    //  36 - 44 = TileInventory slots, which map to our TileEntity slot numbers 0 - 8)
    private static final int hotbar_slots = 9;
    private static final int player_inventory_rows = 3;
    private static final int player_inventory_columns = 9;
    private static final int player_inventory_slots = player_inventory_columns * player_inventory_rows;
    private static final int total_player_slots = hotbar_slots + player_inventory_slots;
    private static final int first_slot_index = 0;
    private static final int tile_entity_first_slot_index = first_slot_index + total_player_slots;

    private static final int tile_entity_inventory_slots = 3;

    @Override
    public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
        Slot sourceSlot = slots.get(index);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        if (index < first_slot_index + total_player_slots) {
            if (!moveItemStackTo(sourceStack, tile_entity_first_slot_index, tile_entity_first_slot_index
                    + tile_entity_inventory_slots, false)) {
                return ItemStack.EMPTY;
            }
        } else if (index < tile_entity_first_slot_index + tile_entity_inventory_slots) {
            if (!moveItemStackTo(sourceStack, first_slot_index, first_slot_index + total_player_slots, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + index);
            return ItemStack.EMPTY;
        }

        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(player, sourceStack);
        return copyOfSourceStack;
    }
}