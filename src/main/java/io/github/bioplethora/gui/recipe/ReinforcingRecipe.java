package io.github.bioplethora.gui.recipe;

import com.google.gson.JsonObject;
import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.registry.BPBlocks;
import io.github.bioplethora.registry.BPRecipes;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public class ReinforcingRecipe implements IRecipe<IInventory> {

    protected final ResourceLocation recipeId;
    protected final Ingredient topIngredient, midIngredient, botIngredient;
    protected final ItemStack resultItem;

    public ReinforcingRecipe(ResourceLocation recipeId, Ingredient topIngredient, Ingredient midIngredient, Ingredient botIngredient,
                             ItemStack resultItem) {
        this.recipeId = recipeId;
        this.topIngredient = topIngredient;
        this.midIngredient = midIngredient;
        this.botIngredient = botIngredient;
        this.resultItem = resultItem;
    }

    @Override
    public boolean matches(IInventory inv, World world) {
        return this.topIngredient.test(inv.getItem(0))
                && this.midIngredient.test(inv.getItem(1))
                && this.botIngredient.test(inv.getItem(2));
    }

    @Override
    public ItemStack assemble(IInventory pInv) {
        ItemStack itemstack = this.resultItem.copy();
        CompoundNBT compoundnbt = pInv.getItem(2).getTag();
        if (compoundnbt != null) {
            itemstack.setTag(compoundnbt.copy());
        }

        return itemstack;
    }

    public ItemStack[] addIngredient(int slotToPlace) {

        NonNullList<Ingredient> totalIngredients = NonNullList.create();
        totalIngredients.add(topIngredient);
        totalIngredients.add(midIngredient);
        totalIngredients.add(botIngredient);

        Ingredient ing = totalIngredients.get(slotToPlace);
        return ing.getItems();
    }

    public ItemStack getIcon() {
        return new ItemStack(BPBlocks.REINFORCING_TABLE.get());
    }

    public ItemStack getResult() {
        return resultItem;
    }

    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return pWidth * pHeight >= 2;
    }

    public ItemStack getResultItem() {
        return this.resultItem;
    }

    public ItemStack getToastSymbol() {
        return new ItemStack(BPBlocks.REINFORCING_TABLE.get());
    }

    @Override
    public ResourceLocation getId() {
        return recipeId;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return BPRecipes.REINFORCING_SERIALIZER.get();
    }

    public IRecipeType<?> getType() {
        return BPRecipes.REINFORCING;
    }


    public static class ReinforcingRecipeType implements IRecipeType<ReinforcingRecipe> {

        @Override
        public String toString () {
            return new ResourceLocation(Bioplethora.MOD_ID, "reinforcing").toString();
        }
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<ReinforcingRecipe> {

        @Override
        public ReinforcingRecipe fromJson(ResourceLocation recipeId, JsonObject json) {

            Ingredient core = Ingredient.fromJson(JSONUtils.getAsJsonObject(json, "core"));
            Ingredient material = Ingredient.fromJson(JSONUtils.getAsJsonObject(json, "material"));
            Ingredient weapon = Ingredient.fromJson(JSONUtils.getAsJsonObject(json, "weapon"));

            ItemStack result = ShapedRecipe.itemFromJson(JSONUtils.getAsJsonObject(json, "result"));

            return new ReinforcingRecipe(recipeId, core, material, weapon, result);
        }

        @Nullable
        @Override
        public ReinforcingRecipe fromNetwork(ResourceLocation recipeId, PacketBuffer buffer) {
            Ingredient core = Ingredient.fromNetwork(buffer);
            Ingredient material = Ingredient.fromNetwork(buffer);
            Ingredient weapon = Ingredient.fromNetwork(buffer);

            ItemStack result = buffer.readItem();

            return new ReinforcingRecipe(recipeId, core, material, weapon, result);
        }

        @Override
        public void toNetwork(PacketBuffer buffer, ReinforcingRecipe recipe) {
            buffer.writeInt(recipe.getIngredients().size());
            for (Ingredient ing : recipe.getIngredients()) {
                ing.toNetwork(buffer);
            }
            buffer.writeItemStack(recipe.getResult(), false);
        }
    }
}
