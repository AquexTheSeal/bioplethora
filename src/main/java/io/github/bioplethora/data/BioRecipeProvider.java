package io.github.bioplethora.data;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.gui.recipe.ReinforcingRecipeBuilder;
import io.github.bioplethora.registry.BPBlocks;
import io.github.bioplethora.registry.BPItems;
import io.github.bioplethora.registry.BPTags;
import net.minecraft.block.Blocks;
import net.minecraft.data.*;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

import java.util.function.Consumer;

public class BioRecipeProvider extends RecipeProvider {

    public BioRecipeProvider(DataGenerator gen) {
        super(gen);
    }

    @Override
    protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {

        /** @Param consumer, providedItem, requiredItem **/
        shortsword(consumer, BPItems.NANDBRIC_SHORTSWORD.get(), BPItems.NANDBRI_SCALES.get(), BPItems.NANDBRI_FANG.get());

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
        foodCooking(consumer, BPItems.RAW_JAWFLESH.get(), BPItems.COOKED_JAWFLESH.get(), 0.40F, 300);

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

        // WOODSET
        this.woodset(consumer, BPTags.Items.ENIVILE_LOGS,
                BPBlocks.ENIVILE_PLANKS.get(), BPBlocks.ENIVILE_LOG.get(), BPBlocks.STRIPPED_ENIVILE_LOG.get(),
                BPBlocks.ENIVILE_WOOD.get(), BPBlocks.STRIPPED_ENIVILE_WOOD.get(), BPBlocks.ENIVILE_FENCE.get(), 
                BPBlocks.ENIVILE_FENCE_GATE.get(), BPBlocks.ENIVILE_PRESSURE_PLATE.get(), BPBlocks.ENIVILE_DOOR.get(),
                BPBlocks.ENIVILE_TRAPDOOR.get(), BPBlocks.ENIVILE_BUTTON.get(), BPBlocks.ENIVILE_STAIRS.get(), 
                BPBlocks.ENIVILE_SLAB.get(), BPBlocks.ENIVILE_SIGN_ITEM.get(), BPBlocks.ENIVILE_BOAT.get()
        );
        this.woodset(consumer, BPTags.Items.CAERULWOOD_LOGS,
                BPBlocks.CAERULWOOD_PLANKS.get(), BPBlocks.CAERULWOOD_LOG.get(), BPBlocks.STRIPPED_CAERULWOOD_LOG.get(),
                BPBlocks.CAERULWOOD_WOOD.get(), BPBlocks.STRIPPED_CAERULWOOD_WOOD.get(), BPBlocks.CAERULWOOD_FENCE.get(),
                BPBlocks.CAERULWOOD_FENCE_GATE.get(), BPBlocks.CAERULWOOD_PRESSURE_PLATE.get(), BPBlocks.CAERULWOOD_DOOR.get(),
                BPBlocks.CAERULWOOD_TRAPDOOR.get(), BPBlocks.CAERULWOOD_BUTTON.get(), BPBlocks.CAERULWOOD_STAIRS.get(),
                BPBlocks.CAERULWOOD_SLAB.get(), BPBlocks.CAERULWOOD_SIGN_ITEM.get(), BPBlocks.CAERULWOOD_BOAT.get()
        );

        // STONE
        stoneSetHelper(consumer, BPBlocks.ALPHANUM.get(), BPBlocks.ALPHANUM_BRICKS.get(), BPBlocks.POLISHED_ALPHANUM.get(),
                BPBlocks.ALPHANUM_STAIRS.get(), BPBlocks.ALPHANUM_WALL.get(), BPBlocks.ALPHANUM_SLAB.get(),
                BPBlocks.ALPHANUM_STAIRS_BRICKS.get(), BPBlocks.ALPHANUM_WALL_BRICKS.get(), BPBlocks.ALPHANUM_SLAB_BRICKS.get(),
                BPBlocks.POLISHED_ALPHANUM_STAIRS.get(), BPBlocks.POLISHED_ALPHANUM_WALL.get(), BPBlocks.POLISHED_ALPHANUM_SLAB.get()
        );
        pillarCrafting(consumer, BPBlocks.ALPHANUM_PILLAR.get(), BPBlocks.ALPHANUM.get());

        // ETC.
        reinforcing(consumer, BPItems.ARBITRARY_BALLISTA.get(), BPItems.RED_GRYLYNEN_CRYSTAL.get(), BPItems.BELLOPHITE.get(), Items.CROSSBOW, 0);
        reinforcing(consumer, BPItems.INFERNAL_QUARTERSTAFF_DEACTIVATED.get(), BPItems.INFERNAL_QUARTERSTAFF_BLADE.get(), BPItems.INFERNAL_QUARTERSTAFF_BASE.get(), BPItems.INFERNAL_QUARTERSTAFF_BLADE.get(), 0);
        reinforcing(consumer, BPItems.INFERNAL_QUARTERSTAFF.get(), BPItems.FIERY_CUBE.get(), BPItems.INFERNAL_QUARTERSTAFF_DEACTIVATED.get(), BPItems.SOUL_CUBE.get(), 0);
    }

    public String getName() {
        return "Bioplethora Recipes";
    }

    public static void shortsword(Consumer<IFinishedRecipe> consumer, IItemProvider providedItem, IItemProvider handle, IItemProvider material) {
        ShapedRecipeBuilder.shaped(providedItem).define('I', handle).define('#', material).pattern(" I ").pattern(" # ")
                .unlockedBy("has_item", has(material)).save(consumer);
    }

    public static void ingotToBlock(Consumer<IFinishedRecipe> consumer, IItemProvider providedItem, IItemProvider requiredItem) {
        ShapedRecipeBuilder.shaped(providedItem, 1).define('#', requiredItem).pattern("###").pattern("###").pattern("###")
                .unlockedBy("has_item", has(requiredItem)).save(consumer);
    }

    public static void blockToIngot(Consumer<IFinishedRecipe> consumer, IItemProvider providedItem, IItemProvider requiredItem) {
        ShapelessRecipeBuilder.shapeless(providedItem, 9).requires(requiredItem)
                .unlockedBy("has_item", has(requiredItem)).save(consumer);
    }

    public void foodCooking(Consumer<IFinishedRecipe> consumer, Item input, Item output, float exp, int defaultTime) {
        String inputItemString = input.getRegistryName().getPath();
        
        CookingRecipeBuilder.smelting(Ingredient.of(input), output, exp, defaultTime).unlockedBy("has_" + input.getRegistryName().getPath(), has(input)).save(consumer);
        
        CookingRecipeBuilder.cooking(Ingredient.of(input), output, exp, defaultTime / 2, IRecipeSerializer.SMOKING_RECIPE).unlockedBy("has_" + inputItemString, has(input))
                .save(consumer, new ResourceLocation(Bioplethora.MOD_ID, output.getRegistryName().getPath() + "_smoking"));
        
        CookingRecipeBuilder.cooking(Ingredient.of(input), output, exp, defaultTime * 3, IRecipeSerializer.CAMPFIRE_COOKING_RECIPE).unlockedBy("has_" + inputItemString, has(input))
                .save(consumer, new ResourceLocation(Bioplethora.MOD_ID, output.getRegistryName().getPath() + "_campfire_cooking"));
    }

    public static void reinforcing(Consumer<IFinishedRecipe> consumer, Item providedItem, IItemProvider top, IItemProvider mid, IItemProvider bot, int dupeRecipe) {
        ReinforcingRecipeBuilder.reinforcing(Ingredient.of(top), Ingredient.of(mid), Ingredient.of(bot), providedItem).unlocks("has_item", has(bot))
                .save(consumer, new ResourceLocation(Bioplethora.MOD_ID, providedItem.getRegistryName().getPath() + "_reinforcing" + (dupeRecipe <= 0 ? "" : "_" + dupeRecipe)));
    }

    public void woodset(Consumer<IFinishedRecipe> consumer, ITag<Item> logsTag,
                        IItemProvider planks, IItemProvider log, IItemProvider strippedLog, IItemProvider wood, IItemProvider strippedWood,
                        IItemProvider fence, IItemProvider fenceGate, IItemProvider pressurePlate, IItemProvider door, IItemProvider trapdoor,
                        IItemProvider button, IItemProvider stairs, IItemProvider slab, IItemProvider sign, IItemProvider boat) {
        
        planksFromLogs(consumer, planks, logsTag);
        woodFromLogs(consumer, wood, log);
        woodFromLogs(consumer, strippedWood, strippedLog);
        
        woodenBoat(consumer, boat, planks);
        woodenButton(consumer, button, planks);
        woodenDoor(consumer, door, planks);
        woodenFence(consumer, fence, planks);
        woodenFenceGate(consumer, fenceGate, planks);
        woodenPressurePlate(consumer, pressurePlate, planks);
        woodenSlab(consumer, slab, planks);
        woodenStairs(consumer, stairs, planks);
        woodenTrapdoor(consumer, trapdoor, planks);
        woodenSign(consumer, sign, planks);
    }

    public void stoneSetHelper(Consumer<IFinishedRecipe> consumer, IItemProvider baseItem, IItemProvider bricksBase, IItemProvider polishedBase,
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

    public void armorSetHelper(Consumer<IFinishedRecipe> consumer, IItemProvider material, IItemProvider helmet, IItemProvider chestplate, IItemProvider leggings, IItemProvider boots) {
        ShapedRecipeBuilder.shaped(helmet).define('S', material).pattern("SSS").pattern("S S")
                .unlockedBy("has_" + material.asItem().getRegistryName().getPath(), has(material)).save(consumer);
        ShapedRecipeBuilder.shaped(chestplate).define('S', material).pattern("S S").pattern("SSS").pattern("SSS")
                .unlockedBy("has_" + material.asItem().getRegistryName().getPath(), has(material)).save(consumer);
        ShapedRecipeBuilder.shaped(leggings).define('S', material).pattern("SSS").pattern("S S").pattern("S S")
                .unlockedBy("has_" + material.asItem().getRegistryName().getPath(), has(material)).save(consumer);
        ShapedRecipeBuilder.shaped(boots).define('S', material).pattern("S S").pattern("S S")
                .unlockedBy("has_" + material.asItem().getRegistryName().getPath(), has(material)).save(consumer);
    }

    public void toolSetHelper(Consumer<IFinishedRecipe> consumer, IItemProvider material, IItemProvider stick, IItemProvider sword, IItemProvider shovel, IItemProvider pickaxe, IItemProvider axe, IItemProvider hoe) {
        ShapedRecipeBuilder.shaped(sword).define('S', stick).define('M', material).pattern(" M ").pattern(" M ").pattern(" S ")
                .unlockedBy("has_" + material.asItem().getRegistryName().getPath(), has(material)).save(consumer);
        ShapedRecipeBuilder.shaped(shovel).define('S', stick).define('M', material).pattern(" M ").pattern(" S ").pattern(" S ")
                .unlockedBy("has_" + material.asItem().getRegistryName().getPath(), has(material)).save(consumer);
        ShapedRecipeBuilder.shaped(pickaxe).define('S', stick).define('M', material).pattern("MMM").pattern(" S ").pattern(" S ")
                .unlockedBy("has_" + material.asItem().getRegistryName().getPath(), has(material)).save(consumer);
        ShapedRecipeBuilder.shaped(axe).define('S', stick).define('M', material).pattern("MM ").pattern("MS ").pattern(" S ")
                .unlockedBy("has_" + material.asItem().getRegistryName().getPath(), has(material)).save(consumer);
        ShapedRecipeBuilder.shaped(hoe).define('S', stick).define('M', material).pattern("MM ").pattern(" S ").pattern(" S ")
                .unlockedBy("has_" + material.asItem().getRegistryName().getPath(), has(material)).save(consumer);
    }

    public void stoneCutting(Consumer<IFinishedRecipe> consumer, IItemProvider baseItem, IItemProvider resultItem, int amount) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(baseItem), resultItem, amount).unlocks("has_stone", has(baseItem)).save(consumer, new ResourceLocation(Bioplethora.MOD_ID, resultItem.asItem().getRegistryName().getPath() + "_from_stone_stonecutting"));
    }

    public void stoneCutting(Consumer<IFinishedRecipe> consumer, IItemProvider baseItem, IItemProvider resultItem) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(baseItem), resultItem).unlocks("has_stone", has(baseItem)).save(consumer, new ResourceLocation(Bioplethora.MOD_ID, resultItem.asItem().getRegistryName().getPath() + "_from_stone_stonecutting"));
    }

    public static void pillarCrafting(Consumer<IFinishedRecipe> consumer, IItemProvider providedItem, IItemProvider requiredItem) {
        ShapedRecipeBuilder.shaped(providedItem, 2).define('S', requiredItem).pattern("S").pattern("S")
                .unlockedBy("has_" + requiredItem.asItem().getRegistryName().getPath(), has(requiredItem)).save(consumer);
    }


    public static void brickCrafting(Consumer<IFinishedRecipe> consumer, IItemProvider providedItem, IItemProvider requiredItem) {
        ShapedRecipeBuilder.shaped(providedItem).define('#', requiredItem).pattern("##").pattern("##")
                .unlockedBy("has_" + requiredItem.asItem().getRegistryName().getPath(), has(requiredItem)).save(consumer);
    }

    public static void polishedCrafting(Consumer<IFinishedRecipe> consumer, IItemProvider providedItem, IItemProvider requiredItem) {
        ShapedRecipeBuilder.shaped(providedItem, 4).define('S', requiredItem).pattern("SS").pattern("SS")
                .unlockedBy("has_" + requiredItem.asItem().getRegistryName().getPath(), has(requiredItem)).save(consumer);
    }
    
    public static void stairsCrafting(Consumer<IFinishedRecipe> consumer, IItemProvider providedItem, IItemProvider requiredItem) {
        ShapedRecipeBuilder.shaped(providedItem, 4).define('#', requiredItem).pattern("#  ").pattern("## ").pattern("###")
                .unlockedBy("has_" + requiredItem.asItem().getRegistryName().getPath(), has(requiredItem)).save(consumer);
    }

    public static void wallsCrafting(Consumer<IFinishedRecipe> consumer, IItemProvider providedItem, IItemProvider requiredItem) {
        ShapedRecipeBuilder.shaped(providedItem, 6).define('#', requiredItem).pattern("###").pattern("###")
                .unlockedBy("has_" + requiredItem.asItem().getRegistryName().getPath(), has(requiredItem)).save(consumer);
    }

    public static void slabsCrafting(Consumer<IFinishedRecipe> consumer, IItemProvider providedItem, IItemProvider requiredItem) {
        ShapedRecipeBuilder.shaped(providedItem, 6).define('#', requiredItem).pattern("###")
                .unlockedBy("has_" + requiredItem.asItem().getRegistryName().getPath(), has(requiredItem)).save(consumer);

    }

    public static void planksFromLog(Consumer<IFinishedRecipe> pFinishedRecipeConsumer, IItemProvider pPlanks, ITag<Item> pLog) {
        ShapelessRecipeBuilder.shapeless(pPlanks, 4).requires(pLog).group("planks").unlockedBy("has_log", has(pLog)).save(pFinishedRecipeConsumer);
    }

    public static void planksFromLogs(Consumer<IFinishedRecipe> pFinishedRecipeConsumer, IItemProvider pPlanks, ITag<Item> pLogs) {
        ShapelessRecipeBuilder.shapeless(pPlanks, 4).requires(pLogs).group("planks").unlockedBy("has_logs", has(pLogs)).save(pFinishedRecipeConsumer);
    }

    public static void woodFromLogs(Consumer<IFinishedRecipe> pFinishedRecipeConsumer, IItemProvider pWood, IItemProvider pLog) {
        ShapedRecipeBuilder.shaped(pWood, 3).define('#', pLog).pattern("##").pattern("##").group("bark").unlockedBy("has_log", has(pLog)).save(pFinishedRecipeConsumer);
    }

    public static void woodenBoat(Consumer<IFinishedRecipe> pFinishedRecipeConsumer, IItemProvider pBoat, IItemProvider pMaterial) {
        ShapedRecipeBuilder.shaped(pBoat).define('#', pMaterial).pattern("# #").pattern("###").group("boat").unlockedBy("in_water", insideOf(Blocks.WATER)).save(pFinishedRecipeConsumer);
    }

    public static void woodenButton(Consumer<IFinishedRecipe> p_240474_0_, IItemProvider p_240474_1_, IItemProvider p_240474_2_) {
        ShapelessRecipeBuilder.shapeless(p_240474_1_).requires(p_240474_2_).group("wooden_button").unlockedBy("has_planks", has(p_240474_2_)).save(p_240474_0_);
    }

    public static void woodenDoor(Consumer<IFinishedRecipe> p_240475_0_, IItemProvider p_240475_1_, IItemProvider p_240475_2_) {
        ShapedRecipeBuilder.shaped(p_240475_1_, 3).define('#', p_240475_2_).pattern("##").pattern("##").pattern("##").group("wooden_door").unlockedBy("has_planks", has(p_240475_2_)).save(p_240475_0_);
    }

    public static void woodenFence(Consumer<IFinishedRecipe> p_240476_0_, IItemProvider p_240476_1_, IItemProvider p_240476_2_) {
        ShapedRecipeBuilder.shaped(p_240476_1_, 3).define('#', Items.STICK).define('W', p_240476_2_).pattern("W#W").pattern("W#W").group("wooden_fence").unlockedBy("has_planks", has(p_240476_2_)).save(p_240476_0_);
    }

    public static void woodenFenceGate(Consumer<IFinishedRecipe> p_240477_0_, IItemProvider p_240477_1_, IItemProvider p_240477_2_) {
        ShapedRecipeBuilder.shaped(p_240477_1_).define('#', Items.STICK).define('W', p_240477_2_).pattern("#W#").pattern("#W#").group("wooden_fence_gate").unlockedBy("has_planks", has(p_240477_2_)).save(p_240477_0_);
    }

    public static void woodenPressurePlate(Consumer<IFinishedRecipe> p_240478_0_, IItemProvider p_240478_1_, IItemProvider p_240478_2_) {
        ShapedRecipeBuilder.shaped(p_240478_1_).define('#', p_240478_2_).pattern("##").group("wooden_pressure_plate").unlockedBy("has_planks", has(p_240478_2_)).save(p_240478_0_);
    }

    public static void woodenSlab(Consumer<IFinishedRecipe> p_240479_0_, IItemProvider p_240479_1_, IItemProvider p_240479_2_) {
        ShapedRecipeBuilder.shaped(p_240479_1_, 6).define('#', p_240479_2_).pattern("###").group("wooden_slab").unlockedBy("has_planks", has(p_240479_2_)).save(p_240479_0_);
    }

    public static void woodenStairs(Consumer<IFinishedRecipe> p_240480_0_, IItemProvider p_240480_1_, IItemProvider p_240480_2_) {
        ShapedRecipeBuilder.shaped(p_240480_1_, 4).define('#', p_240480_2_).pattern("#  ").pattern("## ").pattern("###").group("wooden_stairs").unlockedBy("has_planks", has(p_240480_2_)).save(p_240480_0_);
    }

    public static void woodenTrapdoor(Consumer<IFinishedRecipe> p_240481_0_, IItemProvider p_240481_1_, IItemProvider p_240481_2_) {
        ShapedRecipeBuilder.shaped(p_240481_1_, 2).define('#', p_240481_2_).pattern("###").pattern("###").group("wooden_trapdoor").unlockedBy("has_planks", has(p_240481_2_)).save(p_240481_0_);
    }

    public static void woodenSign(Consumer<IFinishedRecipe> p_240482_0_, IItemProvider p_240482_1_, IItemProvider p_240482_2_) {
        String s = Registry.ITEM.getKey(p_240482_2_.asItem()).getPath();
        ShapedRecipeBuilder.shaped(p_240482_1_, 3).group("sign").define('#', p_240482_2_).define('X', Items.STICK).pattern("###").pattern("###").pattern(" X ").unlockedBy("has_" + s, has(p_240482_2_)).save(p_240482_0_);
    }
}
