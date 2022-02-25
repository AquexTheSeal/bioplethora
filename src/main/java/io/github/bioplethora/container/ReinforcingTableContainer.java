package io.github.bioplethora.container;

import io.github.bioplethora.registry.BioplethoraContainerTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.SmithingRecipe;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ReinforcingTableContainer extends AbstractReinforcingContainer {

    private final World level;
    @Nullable
    private SmithingRecipe selectedRecipe;
    private final List<SmithingRecipe> recipes;

    public ReinforcingTableContainer(int pContainerId, PlayerInventory pPlayerInventory) {
        this(pContainerId, pPlayerInventory, IWorldPosCallable.NULL);
    }

    public ReinforcingTableContainer(int pContainerId, PlayerInventory pPlayerInventory, IWorldPosCallable pAccess) {
        super(BioplethoraContainerTypes.REINFORCING_TABLE_CONTAINER.get(), pContainerId, pPlayerInventory, pAccess);
        this.level = pPlayerInventory.player.level;
        this.recipes = this.level.getRecipeManager().getAllRecipesFor(IRecipeType.SMITHING);
    }

    protected boolean isValidBlock(BlockState pState) {
        return pState.is(Blocks.SMITHING_TABLE);
    }

    protected boolean mayPickup(PlayerEntity pPlayer, boolean pHasStack) {
        return this.selectedRecipe != null && this.selectedRecipe.matches(this.inputSlots, this.level);
    }

    protected ItemStack onTake(PlayerEntity pPlayer, ItemStack pInputItem) {
        pInputItem.onCraftedBy(pPlayer.level, pPlayer, pInputItem.getCount());
        this.resultSlots.awardUsedRecipes(pPlayer);
        this.shrinkStackInSlot(0);
        this.shrinkStackInSlot(1);
        this.access.execute((p_234653_0_, p_234653_1_) -> {
            p_234653_0_.levelEvent(1044, p_234653_1_, 0);
        });
        return pInputItem;
    }

    private void shrinkStackInSlot(int pIndex) {
        ItemStack itemstack = this.inputSlots.getItem(pIndex);
        itemstack.shrink(1);
        this.inputSlots.setItem(pIndex, itemstack);
    }

    /**
     * called when the Anvil Input Slot changes, calculates the new result and puts it in the output slot
     */
    public void createResult() {
        List<SmithingRecipe> list = this.level.getRecipeManager().getRecipesFor(IRecipeType.SMITHING, this.inputSlots, this.level);
        if (list.isEmpty()) {
            this.resultSlots.setItem(0, ItemStack.EMPTY);
        } else {
            this.selectedRecipe = list.get(0);
            ItemStack itemstack = this.selectedRecipe.assemble(this.inputSlots);
            this.resultSlots.setRecipeUsed(this.selectedRecipe);
            this.resultSlots.setItem(0, itemstack);
        }

    }

    protected boolean shouldQuickMoveToAdditionalSlot(ItemStack pStack) {
        return this.recipes.stream().anyMatch((p_241444_1_) -> {
            return p_241444_1_.isAdditionIngredient(pStack);
        });
    }

    /**
     * Called to determine if the current slot is valid for the stack merging (double-click) code. The stack passed in is
     * null for the initial slot that was double-clicked.
     */
    public boolean canTakeItemForPickAll(ItemStack pStack, Slot pSlot) {
        return pSlot.container != this.resultSlots && super.canTakeItemForPickAll(pStack, pSlot);
    }
}
