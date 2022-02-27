package io.github.bioplethora.container;

import io.github.bioplethora.recipe.ReinforcingRecipe;
import io.github.bioplethora.registry.BioplethoraContainerTypes;
import io.github.bioplethora.registry.BioplethoraRecipes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ReinforcingTableContainer extends AbstractReinforcingContainer {

    private final World level;
    @Nullable
    private ReinforcingRecipe selectedRecipe;

    public ReinforcingTableContainer(int pContainerId, PlayerInventory pPlayerInventory) {
        this(pContainerId, pPlayerInventory, IWorldPosCallable.NULL);
    }

    public ReinforcingTableContainer(int pContainerId, PlayerInventory pPlayerInventory, IWorldPosCallable pAccess) {
        super(BioplethoraContainerTypes.REINFORCING_TABLE_CONTAINER.get(), pContainerId, pPlayerInventory, pAccess);
        this.level = pPlayerInventory.player.level;
    }

    protected boolean mayPickup(PlayerEntity pPlayer, boolean pHasStack) {
        return this.selectedRecipe != null && this.selectedRecipe.matches(this.inputSlots, this.level);
    }

    protected ItemStack onTake(PlayerEntity pPlayer, ItemStack pInputItem) {
        pInputItem.onCraftedBy(pPlayer.level, pPlayer, pInputItem.getCount());
        this.resultSlots.awardUsedRecipes(pPlayer);
        this.shrinkStackInSlot(0);
        this.shrinkStackInSlot(1);
        this.shrinkStackInSlot(2);
        this.access.execute((world, pos) -> world.levelEvent(1044, pos, 0));
        return pInputItem;
    }

    private void shrinkStackInSlot(int pIndex) {
        ItemStack itemstack = this.inputSlots.getItem(pIndex);
        itemstack.shrink(1);
        this.inputSlots.setItem(pIndex, itemstack);
    }

    public void createResult() {
        List<ReinforcingRecipe> list = this.level.getRecipeManager().getRecipesFor(BioplethoraRecipes.REINFORCING, this.inputSlots, this.level);
        if (list.isEmpty()) {
            this.resultSlots.setItem(3, ItemStack.EMPTY);
        } else {
            this.selectedRecipe = list.get(0);
            ItemStack itemstack = this.selectedRecipe.assemble(this.inputSlots);
            this.resultSlots.setRecipeUsed(this.selectedRecipe);
            this.resultSlots.setItem(3, itemstack);
        }
    }

    public boolean canTakeItemForPickAll(ItemStack pStack, Slot pSlot) {
        return pSlot.container != this.resultSlots && super.canTakeItemForPickAll(pStack, pSlot);
    }
}
