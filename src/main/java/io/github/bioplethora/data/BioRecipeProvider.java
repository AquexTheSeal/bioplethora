package io.github.bioplethora.data;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.gui.recipe.ReinforcingRecipeBuilder;
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

        stoneSetHelper(consumer, BioplethoraBlocks.ALPHANUM.get(), BioplethoraBlocks.ALPHANUM_BRICKS.get(), BioplethoraBlocks.POLISHED_ALPHANUM.get(),
                BioplethoraBlocks.ALPHANUM_STAIRS.get(), BioplethoraBlocks.ALPHANUM_WALL.get(), BioplethoraBlocks.ALPHANUM_SLAB.get(),
                BioplethoraBlocks.ALPHANUM_STAIRS_BRICKS.get(), BioplethoraBlocks.ALPHANUM_WALL_BRICKS.get(), BioplethoraBlocks.ALPHANUM_SLAB_BRICKS.get(),
                BioplethoraBlocks.POLISHED_ALPHANUM_STAIRS.get(), BioplethoraBlocks.POLISHED_ALPHANUM_WALL.get(), BioplethoraBlocks.POLISHED_ALPHANUM_SLAB.get()
        );
        pillarCrafting(consumer, BioplethoraBlocks.ALPHANUM_PILLAR.get(), BioplethoraBlocks.ALPHANUM.get());

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

    private void stoneSetHelper(Consumer<IFinishedRecipe> consumer, IItemProvider baseItem, IItemProvider bricksBase, IItemProvider polishedBase,
                                 IItemProvider stairs, IItemProvider wall, IItemProvider slab,
                                 IItemProvider brickStairs, IItemProvider brickWall, IItemProvider brickSlab,
                                 IItemProvider polishedStairs, IItemProvider polishedWall, IItemProvider polishedSlab) {

        brickCrafting(consumer, bricksBase, baseItem);
        polishedCrafting(consumer, polishedBase, bricksBase);

        stairsCrafting(consumer, stairs, baseItem);
        wallsCrafting(consumer, wall, baseItem);
        slabsCrafting(consumer, slab, baseItem);
        stairsCrafting(consumer, brickStairs, bricksBase);
        wallsCrafting(consumer, brickWall, bricksBase);
        slabsCrafting(consumer, brickSlab, bricksBase);
        stairsCrafting(consumer, polishedStairs, polishedBase);
        wallsCrafting(consumer, polishedWall, polishedBase);
        slabsCrafting(consumer, polishedSlab, polishedBase);

        stoneCutting(consumer, baseItem, bricksBase);
        stoneCutting(consumer, baseItem, polishedBase);
        stoneCutting(consumer, baseItem, stairs);
        stoneCutting(consumer, baseItem, wall);
        stoneCutting(consumer, baseItem, slab, 2);
        stoneCutting(consumer, bricksBase, brickStairs);
        stoneCutting(consumer, bricksBase, brickWall);
        stoneCutting(consumer, bricksBase, brickSlab, 2);
        stoneCutting(consumer, polishedBase, polishedStairs);
        stoneCutting(consumer, polishedBase, polishedWall);
        stoneCutting(consumer, polishedBase, polishedSlab, 2);
    }

    private void stoneCutting(Consumer<IFinishedRecipe> consumer, IItemProvider baseItem, IItemProvider resultItem, int amount) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(baseItem), resultItem, amount).unlocks("has_stone", has(baseItem)).save(consumer, new ResourceLocation(Bioplethora.MOD_ID, resultItem.asItem().getRegistryName().getPath() + "_from_stone_stonecutting"));
    }

    private void stoneCutting(Consumer<IFinishedRecipe> consumer, IItemProvider baseItem, IItemProvider resultItem) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(baseItem), resultItem).unlocks("has_stone", has(baseItem)).save(consumer, new ResourceLocation(Bioplethora.MOD_ID, resultItem.asItem().getRegistryName().getPath() + "_from_stone_stonecutting"));
    }

    private static void pillarCrafting(Consumer<IFinishedRecipe> consumer, IItemProvider providedItem, IItemProvider requiredItem) {
        ShapedRecipeBuilder.shaped(providedItem, 2).define('S', requiredItem).pattern("S").pattern("S")
                .unlockedBy("has_" + requiredItem.asItem().getRegistryName().getPath(), has(requiredItem)).save(consumer);
    }


    private static void brickCrafting(Consumer<IFinishedRecipe> consumer, IItemProvider providedItem, IItemProvider requiredItem) {
        ShapedRecipeBuilder.shaped(providedItem).define('#', requiredItem).pattern("##").pattern("##")
                .unlockedBy("has_" + requiredItem.asItem().getRegistryName().getPath(), has(requiredItem)).save(consumer);
    }

    private static void polishedCrafting(Consumer<IFinishedRecipe> consumer, IItemProvider providedItem, IItemProvider requiredItem) {
        ShapedRecipeBuilder.shaped(providedItem, 4).define('S', requiredItem).pattern("SS").pattern("SS")
                .unlockedBy("has_" + requiredItem.asItem().getRegistryName().getPath(), has(requiredItem)).save(consumer);
    }
    
    private static void stairsCrafting(Consumer<IFinishedRecipe> consumer, IItemProvider providedItem, IItemProvider requiredItem) {
        ShapedRecipeBuilder.shaped(providedItem, 4).define('#', requiredItem).pattern("#  ").pattern("## ").pattern("###")
                .unlockedBy("has_" + requiredItem.asItem().getRegistryName().getPath(), has(requiredItem)).save(consumer);
    }

    private static void wallsCrafting(Consumer<IFinishedRecipe> consumer, IItemProvider providedItem, IItemProvider requiredItem) {
        ShapedRecipeBuilder.shaped(providedItem, 6).define('#', requiredItem).pattern("###").pattern("###")
                .unlockedBy("has_" + requiredItem.asItem().getRegistryName().getPath(), has(requiredItem)).save(consumer);
    }

    private static void slabsCrafting(Consumer<IFinishedRecipe> consumer, IItemProvider providedItem, IItemProvider requiredItem) {
        ShapedRecipeBuilder.shaped(providedItem, 6).define('#', requiredItem).pattern("###")
                .unlockedBy("has_" + requiredItem.asItem().getRegistryName().getPath(), has(requiredItem)).save(consumer);

    }
}
