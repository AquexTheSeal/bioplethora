package io.github.bioplethora.data;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.recipe.ReinforcingRecipeBuilder;
import io.github.bioplethora.registry.BioplethoraBlocks;
import io.github.bioplethora.registry.BioplethoraItems;
import net.minecraft.data.*;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

import java.util.function.Consumer;

public class BioRecipeProvider extends RecipeProvider {

    public BioRecipeProvider(DataGenerator gen) {
        super(gen);
    }

    @Override
    protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {

        /** @Param consumer, providedItem, requiredItem **/
        ingotToBlock(consumer, BioplethoraBlocks.BELLOPHITE_BLOCK.get(), BioplethoraItems.BELLOPHITE.get());
        blockToIngot(consumer, BioplethoraItems.BELLOPHITE.get(), BioplethoraBlocks.BELLOPHITE_BLOCK.get());
        ingotToBlock(consumer, BioplethoraBlocks.NANDBRI_SCALE_BLOCK.get(), BioplethoraItems.NANDBRI_SCALES.get());
        blockToIngot(consumer, BioplethoraItems.NANDBRI_SCALES.get(), BioplethoraBlocks.NANDBRI_SCALE_BLOCK.get());

        ingotToBlock(consumer, BioplethoraBlocks.GREEN_GRYLYNEN_CRYSTAL_BLOCK.get(), BioplethoraItems.GREEN_GRYLYNEN_CRYSTAL.get());
        blockToIngot(consumer, BioplethoraItems.GREEN_GRYLYNEN_CRYSTAL.get(), BioplethoraBlocks.GREEN_GRYLYNEN_CRYSTAL_BLOCK.get());
        ingotToBlock(consumer, BioplethoraBlocks.YELLOW_GRYLYNEN_CRYSTAL_BLOCK.get(), BioplethoraItems.YELLOW_GRYLYNEN_CRYSTAL.get());
        blockToIngot(consumer, BioplethoraItems.YELLOW_GRYLYNEN_CRYSTAL.get(), BioplethoraBlocks.YELLOW_GRYLYNEN_CRYSTAL_BLOCK.get());
        ingotToBlock(consumer, BioplethoraBlocks.RED_GRYLYNEN_CRYSTAL_BLOCK.get(), BioplethoraItems.RED_GRYLYNEN_CRYSTAL.get());
        blockToIngot(consumer, BioplethoraItems.RED_GRYLYNEN_CRYSTAL.get(), BioplethoraBlocks.RED_GRYLYNEN_CRYSTAL_BLOCK.get());

        foodCooking(consumer, BioplethoraItems.RAW_CUTTLEFISH_MEAT.get(), BioplethoraItems.COOKED_CUTTLEFISH_MEAT.get(), 0.30F, 200);
        foodCooking(consumer, BioplethoraItems.RAW_FLENTAIR.get(), BioplethoraItems.COOKED_FLENTAIR.get(), 0.35F, 300);

        reinforcing(consumer, BioplethoraItems.ARBITRARY_BALLISTA.get(), BioplethoraItems.RED_GRYLYNEN_CRYSTAL.get(),
                BioplethoraItems.BELLOPHITE.get(), Items.CROSSBOW);

    }

    public String getName() {
        return "Bioplethora Recipes";
    }

    private static void ingotToBlock(Consumer<IFinishedRecipe> consumer, IItemProvider providedItem, IItemProvider requiredItem) {
        ShapedRecipeBuilder.shaped(providedItem, 1).define('#', requiredItem).pattern("###").pattern("###").pattern("###")
                .unlockedBy("has_item", has(requiredItem)).save(consumer);
    }

    private static void blockToIngot(Consumer<IFinishedRecipe> consumer, IItemProvider providedItem, IItemProvider requiredItem) {
        ShapelessRecipeBuilder.shapeless(providedItem, 9).requires(requiredItem)
                .unlockedBy("has_item", has(requiredItem)).save(consumer);
    }

    private static void reinforcing(Consumer<IFinishedRecipe> consumer, Item providedItem, IItemProvider top, IItemProvider mid, IItemProvider bot) {
        ReinforcingRecipeBuilder.reinforcing(Ingredient.of(top), Ingredient.of(mid), Ingredient.of(bot), providedItem).unlocks("has_item", has(bot)).save(consumer, new ResourceLocation(Bioplethora.MOD_ID, providedItem.getRegistryName().getPath() + "_reinforcing"));
    }

    private void foodCooking(Consumer<IFinishedRecipe> consumer, Item input, Item output, float exp, int defaultTime) {
        String inputItemString = input.getRegistryName().getPath();
        
        CookingRecipeBuilder.smelting(Ingredient.of(input), output, exp, defaultTime).unlockedBy("has_" + input.getRegistryName().getPath(), has(input)).save(consumer);
        
        CookingRecipeBuilder.cooking(Ingredient.of(input), output, exp, defaultTime / 2, IRecipeSerializer.SMOKING_RECIPE).unlockedBy("has_" + inputItemString, has(input))
                .save(consumer, new ResourceLocation(Bioplethora.MOD_ID, output.getRegistryName().getPath() + "_smoking"));
        
        CookingRecipeBuilder.cooking(Ingredient.of(input), output, exp, defaultTime * 3, IRecipeSerializer.CAMPFIRE_COOKING_RECIPE).unlockedBy("has_" + inputItemString, has(input))
                .save(consumer, new ResourceLocation(Bioplethora.MOD_ID, output.getRegistryName().getPath() + "_campfire_cooking"));
    }

    private static void genericHelmet(Consumer<IFinishedRecipe> consumer, IItemProvider providedItem, IItemProvider requiredItem) {
        ShapedRecipeBuilder.shaped(providedItem, 1).define('#', requiredItem).pattern("###").pattern("# #").pattern("   ")
                .unlockedBy("has_item", has(requiredItem)).save(consumer);
    }

    private static void genericChestplate(Consumer<IFinishedRecipe> consumer, IItemProvider providedItem, IItemProvider requiredItem) {
        ShapedRecipeBuilder.shaped(providedItem, 1).define('#', requiredItem).pattern("# #").pattern("###").pattern("###")
                .unlockedBy("has_item", has(requiredItem)).save(consumer);
    }

    private static void genericLeggings(Consumer<IFinishedRecipe> consumer, IItemProvider providedItem, IItemProvider requiredItem) {
        ShapedRecipeBuilder.shaped(providedItem, 1).define('#', requiredItem).pattern("###").pattern("# #").pattern("# #")
                .unlockedBy("has_item", has(requiredItem)).save(consumer);
    }

    private static void genericBoots(Consumer<IFinishedRecipe> consumer, IItemProvider providedItem, IItemProvider requiredItem) {
        ShapedRecipeBuilder.shaped(providedItem, 1).define('#', requiredItem).pattern("   ").pattern("# #").pattern("# #")
                .unlockedBy("has_item", has(requiredItem)).save(consumer);
    }
}
