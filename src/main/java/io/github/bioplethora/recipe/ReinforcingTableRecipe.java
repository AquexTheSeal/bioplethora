package io.github.bioplethora.recipe;

import com.google.gson.JsonObject;
import io.github.bioplethora.registry.BioplethoraBlocks;
import io.github.bioplethora.registry.BioplethoraRecipes;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public class ReinforcingTableRecipe implements IRecipe<IInventory> {

    protected final ResourceLocation recipeId;
    protected final Ingredient topIngredient, midIngredient, botIngredient;
    protected final ItemStack resultItem;
    protected final float experience;

    public ReinforcingTableRecipe(ResourceLocation recipeId, Ingredient topIngredient, Ingredient midIngredient, Ingredient botIngredient, 
                                  ItemStack resultItem, float experience) {
        this.recipeId = recipeId;
        this.topIngredient = topIngredient;
        this.midIngredient = midIngredient;
        this.botIngredient = botIngredient;
        this.resultItem = resultItem;
        this.experience = experience;
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
        CompoundNBT compoundnbt = pInv.getItem(0).getTag();
        if (compoundnbt != null) {
            itemstack.setTag(compoundnbt.copy());
        }

        return itemstack;
    }

    public ItemStack getIcon() {
        return new ItemStack(BioplethoraBlocks.REINFORCING_TABLE.get());
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

    @Override
    public ResourceLocation getId() {
        return recipeId;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return BioplethoraRecipes.REINFORCING_TABLE_RECIPE_SERIALIZER.get();
    }

    public IRecipeType<?> getType() {
        return BioplethoraRecipes.REINFORCING_TABLE_RECIPE;
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<ReinforcingTableRecipe> {

        @Override
        public ReinforcingTableRecipe fromJson(ResourceLocation recipeId, JsonObject json) {

            Ingredient hammer = Ingredient.fromJson(JSONUtils.getAsJsonObject(json, "hammer"));
            Ingredient material = Ingredient.fromJson(JSONUtils.getAsJsonObject(json, "material"));
            Ingredient weapon = Ingredient.fromJson(JSONUtils.getAsJsonObject(json, "weapon"));

            ItemStack result = ShapedRecipe.itemFromJson(JSONUtils.getAsJsonObject(json, "result"));
            float experience = JSONUtils.getAsFloat(json, "experience", 0);

            return new ReinforcingTableRecipe(recipeId, hammer, material, weapon, result, experience);
        }

        @Nullable
        @Override
        public ReinforcingTableRecipe fromNetwork(ResourceLocation recipeId, PacketBuffer buffer) {
            Ingredient hammer = Ingredient.fromNetwork(buffer);
            Ingredient material = Ingredient.fromNetwork(buffer);
            Ingredient weapon = Ingredient.fromNetwork(buffer);

            ItemStack result = buffer.readItem();
            float experience = buffer.readFloat();

            ItemStack output = buffer.readItem();
            return new ReinforcingTableRecipe(recipeId, hammer, material, weapon, result, experience);
        }

        @Override
        public void toNetwork(PacketBuffer buffer, ReinforcingTableRecipe recipe) {
            buffer.writeInt(recipe.getIngredients().size());
            for (Ingredient ing : recipe.getIngredients()) {
                ing.toNetwork(buffer);
            }
            buffer.writeItemStack(recipe.getResult(), false);
        }
    }
}
