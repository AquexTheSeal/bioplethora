package io.github.bioplethora.integration.jei;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.gui.recipe.ReinforcingRecipe;
import io.github.bioplethora.registry.BPBlocks;
import io.github.bioplethora.registry.BPRecipes;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReinforcingTableCategory implements IRecipeCategory<ReinforcingRecipe> {

    public static final ResourceLocation CATEGORY_ID = new ResourceLocation(BPRecipes.REINFORCING.toString());
    private IDrawable categoryIcon;
    private IDrawable categoryBackground;

    public ReinforcingTableCategory(IGuiHelper helper) {
        categoryIcon = helper.createDrawableIngredient(new ItemStack(BPBlocks.REINFORCING_TABLE.get()));
        categoryBackground = helper.drawableBuilder(new ResourceLocation(Bioplethora.MOD_ID, "textures/gui/container/jei/reinforcing_table.png"),
                0, 0, 170, 80).setTextureSize(170, 80).build();
    }

    @Override
    public ResourceLocation getUid() {
        return CATEGORY_ID;
    }

    @Override
    public Class<? extends ReinforcingRecipe> getRecipeClass() {
        return ReinforcingRecipe.class;
    }

    @Override
    public String getTitle() {
        return "Reinforce";
    }

    @Override
    public IDrawable getIcon() {
        return categoryIcon;
    }

    @Override
    public IDrawable getBackground() {
        return categoryBackground;
    }

    @Override
    public void setIngredients(ReinforcingRecipe recipe, IIngredients ingredients) {

        List<List<ItemStack>> collectionArray = new ArrayList<>();

        List<ItemStack> recipe0 = new ArrayList<>();
        Collections.addAll(recipe0, recipe.addIngredient(0));
        collectionArray.add(recipe0);

        List<ItemStack> recipe1 = new ArrayList<>();
        Collections.addAll(recipe1, recipe.addIngredient(1));
        collectionArray.add(recipe1);

        List<ItemStack> recipe2 = new ArrayList<>();
        Collections.addAll(recipe2, recipe.addIngredient(2));
        collectionArray.add(recipe2);

        ingredients.setInputLists(VanillaTypes.ITEM, collectionArray);

        ingredients.setOutput(VanillaTypes.ITEM, recipe.getResult());
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, ReinforcingRecipe recipe, IIngredients ingredients) {

        IGuiItemStackGroup recipeStacks = recipeLayout.getItemStacks();

        recipeStacks.init(0, true, 43, 13);
        recipeStacks.init(1, true, 43, 33);
        recipeStacks.init(2, true, 43, 52);

        recipeStacks.init(3, false, 126, 33);
        recipeStacks.set(3, recipe.getResult());

        List<List<ItemStack>> inputs = ingredients.getInputs(VanillaTypes.ITEM);
        List<ItemStack> input;
        for (int i = 0; i <= 2; i++) {
            input = inputs.get(i);
            if (input != null && !input.isEmpty()) {
                recipeStacks.set(i, input);
            }
        }
    }
}
