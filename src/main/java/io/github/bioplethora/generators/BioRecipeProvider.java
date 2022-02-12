package io.github.bioplethora.generators;

import io.github.bioplethora.registry.BioplethoraBlocks;
import io.github.bioplethora.registry.BioplethoraItems;
import net.minecraft.data.*;
import net.minecraft.util.IItemProvider;

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

        ingotToBlock(consumer, BioplethoraBlocks.GREEN_GRYLYNEN_CRYSTAL_BLOCK.get(), BioplethoraItems.GREEN_GRYLYNEN_CRYSTAL.get());
        blockToIngot(consumer, BioplethoraItems.GREEN_GRYLYNEN_CRYSTAL.get(), BioplethoraBlocks.GREEN_GRYLYNEN_CRYSTAL_BLOCK.get());
        ingotToBlock(consumer, BioplethoraBlocks.YELLOW_GRYLYNEN_CRYSTAL_BLOCK.get(), BioplethoraItems.YELLOW_GRYLYNEN_CRYSTAL.get());
        blockToIngot(consumer, BioplethoraItems.YELLOW_GRYLYNEN_CRYSTAL.get(), BioplethoraBlocks.YELLOW_GRYLYNEN_CRYSTAL_BLOCK.get());
        ingotToBlock(consumer, BioplethoraBlocks.RED_GRYLYNEN_CRYSTAL_BLOCK.get(), BioplethoraItems.RED_GRYLYNEN_CRYSTAL.get());
        blockToIngot(consumer, BioplethoraItems.RED_GRYLYNEN_CRYSTAL.get(), BioplethoraBlocks.RED_GRYLYNEN_CRYSTAL_BLOCK.get());
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
