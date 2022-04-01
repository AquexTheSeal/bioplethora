package io.github.bioplethora.data;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.gui.recipe.ReinforcingRecipeBuilder;
import io.github.bioplethora.registry.BPBlocks;
import io.github.bioplethora.registry.BPItems;
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
        ingotToBlock(consumer, BPBlocks.BELLOPHITE_BLOCK.get(), BPItems.BELLOPHITE.get());
        blockToIngot(consumer, BPItems.BELLOPHITE.get(), BPBlocks.BELLOPHITE_BLOCK.get());
        ingotToBlock(consumer, BPBlocks.NANDBRI_SCALE_BLOCK.get(), BPItems.NANDBRI_SCALES.get());
        blockToIngot(consumer, BPItems.NANDBRI_SCALES.get(), BPBlocks.NANDBRI_SCALE_BLOCK.get());

        ingotToBlock(consumer, BPBlocks.GREEN_GRYLYNEN_CRYSTAL_BLOCK.get(), BPItems.GREEN_GRYLYNEN_CRYSTAL.get());
        blockToIngot(consumer, BPItems.GREEN_GRYLYNEN_CRYSTAL.get(), BPBlocks.GREEN_GRYLYNEN_CRYSTAL_BLOCK.get());
        ingotToBlock(consumer, BPBlocks.YELLOW_GRYLYNEN_CRYSTAL_BLOCK.get(), BPItems.YELLOW_GRYLYNEN_CRYSTAL.get());
        blockToIngot(consumer, BPItems.YELLOW_GRYLYNEN_CRYSTAL.get(), BPBlocks.YELLOW_GRYLYNEN_CRYSTAL_BLOCK.get());
        ingotToBlock(consumer, BPBlocks.RED_GRYLYNEN_CRYSTAL_BLOCK.get(), BPItems.RED_GRYLYNEN_CRYSTAL.get());
        blockToIngot(consumer, BPItems.RED_GRYLYNEN_CRYSTAL.get(), BPBlocks.RED_GRYLYNEN_CRYSTAL_BLOCK.get());

        foodCooking(consumer, BPItems.RAW_CUTTLEFISH_MEAT.get(), BPItems.COOKED_CUTTLEFISH_MEAT.get(), 0.30F, 200);
        foodCooking(consumer, BPItems.RAW_FLENTAIR.get(), BPItems.COOKED_FLENTAIR.get(), 0.40F, 300);
        foodCooking(consumer, BPItems.RAW_MOSILE.get(), BPItems.COOKED_MOSILE.get(), 0.30F, 200);

        // TOOLS
        toolSetHelper(consumer,  BPItems.FLEIGNARITE_SCALES.get(), BPItems.FLEIGNARITE_SCALES.get(),
                BPItems.FLEIGNARITE_SWORD.get(), BPItems.FLEIGNARITE_SHOVEL.get(),
                BPItems.FLEIGNARITE_PICKAXE.get(), BPItems.FLEIGNARITE_AXE.get(), BPItems.FLEIGNARITE_HOE.get());

        reinforcing(consumer, BPItems.REINFORCED_FLEIGNARITE_SWORD.get(), Items.NETHERITE_INGOT, BPItems.BELLOPHITE.get(), BPItems.FLEIGNARITE_SWORD.get(), 0);
        reinforcing(consumer, BPItems.REINFORCED_FLEIGNARITE_SHOVEL.get(), Items.NETHERITE_INGOT, BPItems.BELLOPHITE.get(), BPItems.FLEIGNARITE_SHOVEL.get(), 0);
        reinforcing(consumer, BPItems.REINFORCED_FLEIGNARITE_PICKAXE.get(), Items.NETHERITE_INGOT, BPItems.BELLOPHITE.get(), BPItems.FLEIGNARITE_PICKAXE.get(), 0);
        reinforcing(consumer, BPItems.REINFORCED_FLEIGNARITE_AXE.get(), Items.NETHERITE_INGOT, BPItems.BELLOPHITE.get(), BPItems.FLEIGNARITE_AXE.get(), 0);
        reinforcing(consumer, BPItems.REINFORCED_FLEIGNARITE_HOE.get(), Items.NETHERITE_INGOT, BPItems.BELLOPHITE.get(), BPItems.FLEIGNARITE_HOE.get(), 0);

        reinforcing(consumer, BPItems.REINFORCED_FLEIGNARITE_SWORD.get(), Items.NETHERITE_SWORD, BPItems.BELLOPHITE.get(), BPItems.FLEIGNARITE_SWORD.get(), 1);
        reinforcing(consumer, BPItems.REINFORCED_FLEIGNARITE_SHOVEL.get(), Items.NETHERITE_SHOVEL, BPItems.BELLOPHITE.get(), BPItems.FLEIGNARITE_SHOVEL.get(), 1);
        reinforcing(consumer, BPItems.REINFORCED_FLEIGNARITE_PICKAXE.get(), Items.NETHERITE_PICKAXE, BPItems.BELLOPHITE.get(), BPItems.FLEIGNARITE_PICKAXE.get(), 1);
        reinforcing(consumer, BPItems.REINFORCED_FLEIGNARITE_AXE.get(), Items.NETHERITE_AXE, BPItems.BELLOPHITE.get(), BPItems.FLEIGNARITE_AXE.get(), 1);
        reinforcing(consumer, BPItems.REINFORCED_FLEIGNARITE_HOE.get(), Items.NETHERITE_HOE, BPItems.BELLOPHITE.get(), BPItems.FLEIGNARITE_HOE.get(), 1);

        reinforcing(consumer, BPItems.REINFORCED_FLEIGNARITE_SWORD.get(), BPItems.FLEIGNARITE_SWORD.get(), BPItems.BELLOPHITE.get(), Items.NETHERITE_SWORD, 2);
        reinforcing(consumer, BPItems.REINFORCED_FLEIGNARITE_SHOVEL.get(), BPItems.FLEIGNARITE_SHOVEL.get(), BPItems.BELLOPHITE.get(), Items.NETHERITE_SHOVEL, 2);
        reinforcing(consumer, BPItems.REINFORCED_FLEIGNARITE_PICKAXE.get(), BPItems.FLEIGNARITE_PICKAXE.get(), BPItems.BELLOPHITE.get(), Items.NETHERITE_PICKAXE, 2);
        reinforcing(consumer, BPItems.REINFORCED_FLEIGNARITE_AXE.get(), BPItems.FLEIGNARITE_AXE.get(), BPItems.BELLOPHITE.get(), Items.NETHERITE_AXE, 2);
        reinforcing(consumer, BPItems.REINFORCED_FLEIGNARITE_HOE.get(), BPItems.FLEIGNARITE_HOE.get(), BPItems.BELLOPHITE.get(), Items.NETHERITE_HOE, 2);

        // ARMOR
        armorSetHelper(consumer,  BPItems.FLEIGNARITE_SCALES.get(),
                BPItems.FLEIGNARITE_HELMET.get(), BPItems.FLEIGNARITE_CHESTPLATE.get(),
                BPItems.FLEIGNARITE_LEGGINGS.get(), BPItems.FLEIGNARITE_BOOTS.get());
        armorSetHelper(consumer,  BPItems.NANDBRI_SCALES.get(),
                BPItems.NANDBRIC_HELMET.get(), BPItems.NANDBRIC_CHESTPLATE.get(),
                BPItems.NANDBRIC_LEGGINGS.get(), BPItems.NANDBRIC_BOOTS.get());

        reinforcing(consumer, BPItems.REINFORCED_FLEIGNARITE_HELMET.get(), Items.NETHERITE_INGOT, BPItems.BELLOPHITE.get(), BPItems.FLEIGNARITE_HELMET.get(), 0);
        reinforcing(consumer, BPItems.REINFORCED_FLEIGNARITE_CHESTPLATE.get(), Items.NETHERITE_INGOT, BPItems.BELLOPHITE.get(), BPItems.FLEIGNARITE_CHESTPLATE.get(), 0);
        reinforcing(consumer, BPItems.REINFORCED_FLEIGNARITE_LEGGINGS.get(), Items.NETHERITE_INGOT, BPItems.BELLOPHITE.get(), BPItems.FLEIGNARITE_LEGGINGS.get(), 0);
        reinforcing(consumer, BPItems.REINFORCED_FLEIGNARITE_BOOTS.get(), Items.NETHERITE_INGOT, BPItems.BELLOPHITE.get(), BPItems.FLEIGNARITE_BOOTS.get(), 0);

        reinforcing(consumer, BPItems.REINFORCED_FLEIGNARITE_HELMET.get(), Items.NETHERITE_HELMET, BPItems.BELLOPHITE.get(), BPItems.FLEIGNARITE_HELMET.get(), 1);
        reinforcing(consumer, BPItems.REINFORCED_FLEIGNARITE_CHESTPLATE.get(), Items.NETHERITE_CHESTPLATE, BPItems.BELLOPHITE.get(), BPItems.FLEIGNARITE_CHESTPLATE.get(), 1);
        reinforcing(consumer, BPItems.REINFORCED_FLEIGNARITE_LEGGINGS.get(), Items.NETHERITE_LEGGINGS, BPItems.BELLOPHITE.get(), BPItems.FLEIGNARITE_LEGGINGS.get(), 1);
        reinforcing(consumer, BPItems.REINFORCED_FLEIGNARITE_BOOTS.get(), Items.NETHERITE_BOOTS, BPItems.BELLOPHITE.get(), BPItems.FLEIGNARITE_BOOTS.get(), 1);

        reinforcing(consumer, BPItems.REINFORCED_FLEIGNARITE_HELMET.get(), BPItems.FLEIGNARITE_HELMET.get(), BPItems.BELLOPHITE.get(), Items.NETHERITE_HELMET, 2);
        reinforcing(consumer, BPItems.REINFORCED_FLEIGNARITE_CHESTPLATE.get(), BPItems.FLEIGNARITE_CHESTPLATE.get(), BPItems.BELLOPHITE.get(), Items.NETHERITE_CHESTPLATE, 2);
        reinforcing(consumer, BPItems.REINFORCED_FLEIGNARITE_LEGGINGS.get(), BPItems.FLEIGNARITE_LEGGINGS.get(), BPItems.BELLOPHITE.get(), Items.NETHERITE_LEGGINGS, 2);
        reinforcing(consumer, BPItems.REINFORCED_FLEIGNARITE_BOOTS.get(), BPItems.FLEIGNARITE_BOOTS.get(), BPItems.BELLOPHITE.get(), Items.NETHERITE_BOOTS, 2);

        // STONE
        stoneSetHelper(consumer, BPBlocks.ALPHANUM.get(), BPBlocks.ALPHANUM_BRICKS.get(), BPBlocks.POLISHED_ALPHANUM.get(),
                BPBlocks.ALPHANUM_STAIRS.get(), BPBlocks.ALPHANUM_WALL.get(), BPBlocks.ALPHANUM_SLAB.get(),
                BPBlocks.ALPHANUM_STAIRS_BRICKS.get(), BPBlocks.ALPHANUM_WALL_BRICKS.get(), BPBlocks.ALPHANUM_SLAB_BRICKS.get(),
                BPBlocks.POLISHED_ALPHANUM_STAIRS.get(), BPBlocks.POLISHED_ALPHANUM_WALL.get(), BPBlocks.POLISHED_ALPHANUM_SLAB.get()
        );
        pillarCrafting(consumer, BPBlocks.ALPHANUM_PILLAR.get(), BPBlocks.ALPHANUM.get());

        // ETC.
        reinforcing(consumer, BPItems.ARBITRARY_BALLISTA.get(), BPItems.RED_GRYLYNEN_CRYSTAL.get(), BPItems.BELLOPHITE.get(), Items.CROSSBOW, 0);
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

    private static void reinforcing(Consumer<IFinishedRecipe> consumer, Item providedItem, IItemProvider top, IItemProvider mid, IItemProvider bot, int dupeRecipe) {
        ReinforcingRecipeBuilder.reinforcing(Ingredient.of(top), Ingredient.of(mid), Ingredient.of(bot), providedItem).unlocks("has_item", has(bot))
                .save(consumer, new ResourceLocation(Bioplethora.MOD_ID, providedItem.getRegistryName().getPath() + "_reinforcing" + (dupeRecipe <= 0 ? "" : "_" + dupeRecipe)));
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

    private void armorSetHelper(Consumer<IFinishedRecipe> consumer, IItemProvider material, IItemProvider helmet, IItemProvider chestplate, IItemProvider leggings, IItemProvider boots) {
        ShapedRecipeBuilder.shaped(helmet, 2).define('S', material).pattern("SSS").pattern("S S")
                .unlockedBy("has_" + material.asItem().getRegistryName().getPath(), has(material)).save(consumer);
        ShapedRecipeBuilder.shaped(chestplate, 2).define('S', material).pattern("S S").pattern("SSS").pattern("SSS")
                .unlockedBy("has_" + material.asItem().getRegistryName().getPath(), has(material)).save(consumer);
        ShapedRecipeBuilder.shaped(leggings, 2).define('S', material).pattern("SSS").pattern("S S").pattern("S S")
                .unlockedBy("has_" + material.asItem().getRegistryName().getPath(), has(material)).save(consumer);
        ShapedRecipeBuilder.shaped(boots, 2).define('S', material).pattern("S S").pattern("S S")
                .unlockedBy("has_" + material.asItem().getRegistryName().getPath(), has(material)).save(consumer);
    }

    private void toolSetHelper(Consumer<IFinishedRecipe> consumer, IItemProvider material, IItemProvider stick, IItemProvider sword, IItemProvider shovel, IItemProvider pickaxe, IItemProvider axe, IItemProvider hoe) {
        ShapedRecipeBuilder.shaped(sword, 2).define('S', stick).define('M', material).pattern(" M ").pattern(" M ").pattern(" S ")
                .unlockedBy("has_" + material.asItem().getRegistryName().getPath(), has(material)).save(consumer);
        ShapedRecipeBuilder.shaped(shovel, 2).define('S', stick).define('M', material).pattern(" M ").pattern(" S ").pattern(" S ")
                .unlockedBy("has_" + material.asItem().getRegistryName().getPath(), has(material)).save(consumer);
        ShapedRecipeBuilder.shaped(pickaxe, 2).define('S', stick).define('M', material).pattern("MMM").pattern(" S ").pattern(" S ")
                .unlockedBy("has_" + material.asItem().getRegistryName().getPath(), has(material)).save(consumer);
        ShapedRecipeBuilder.shaped(axe, 2).define('S', stick).define('M', material).pattern("MM ").pattern("MS ").pattern(" S ")
                .unlockedBy("has_" + material.asItem().getRegistryName().getPath(), has(material)).save(consumer);
        ShapedRecipeBuilder.shaped(hoe, 2).define('S', stick).define('M', material).pattern("MM ").pattern(" S ").pattern(" S ")
                .unlockedBy("has_" + material.asItem().getRegistryName().getPath(), has(material)).save(consumer);
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
