package io.github.bioplethora.integration.jei;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.gui.container.ReinforcingTableContainer;
import io.github.bioplethora.gui.screen.ReinforcingTableScreen;
import io.github.bioplethora.registry.BPBlocks;
import io.github.bioplethora.registry.BPRecipes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@JeiPlugin
public class BPCompatJEI implements IModPlugin {
    public static final ResourceLocation pluginId = new ResourceLocation(Bioplethora.MOD_ID, "jei");
    public static final int columns = 4, rows = 9, inventorySize = columns * rows;

    public ResourceLocation getPluginUid() {
        return pluginId;
    }

    private void addDescription(IRecipeRegistration registry, ItemStack itemStack) {
        registry.addIngredientInfo(itemStack, VanillaTypes.ITEM, itemStack.getDescriptionId() + ".jei_desc");
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registry) {
        registry.addRecipeTransferHandler(ReinforcingTableContainer.class, ReinforcingTableCategory.CATEGORY_ID, 0, 3, 4, inventorySize);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registry) {
        @SuppressWarnings("resource")
        ClientWorld world = Minecraft.getInstance().level;
        registry.addRecipes(world.getRecipeManager().getAllRecipesFor(BPRecipes.REINFORCING), ReinforcingTableCategory.CATEGORY_ID);
        addDescription(registry, new ItemStack(BPBlocks.REINFORCING_TABLE.get()));
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(ReinforcingTableScreen.class, 79, 35, 20, 20, ReinforcingTableCategory.CATEGORY_ID);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registry) {
        registry.addRecipeCatalyst(new ItemStack(BPBlocks.REINFORCING_TABLE.get()), ReinforcingTableCategory.CATEGORY_ID);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(new ReinforcingTableCategory(guiHelper));
    }
}
