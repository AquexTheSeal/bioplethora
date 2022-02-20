package io.github.bioplethora.data;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.registry.BioplethoraBlocks;
import io.github.bioplethora.registry.BioplethoraItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.TieredItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

import javax.annotation.Nonnull;
import java.util.Collection;

public class BioItemModelProvider extends ItemModelProvider {

    public BioItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Bioplethora.MOD_ID, existingFileHelper);
    }

    /**
     * Automatically generates item and block item models without existing model files.
     */
    @Override
    protected void registerModels() {
        addItems(BioplethoraItems.ITEMS.getEntries());
        addBlockItems(BioplethoraBlocks.BLOCK_ITEMS.getEntries());
    }

    @Nonnull
    @Override
    public String getName() {
        return Bioplethora.MOD_NAME + " Item models";
    }

    public ModelFile.ExistingModelFile getMcLoc(String mcModel) {
        return getExistingFile(mcLoc(mcModel));
    }

    private void addItems(final Collection<RegistryObject<Item>> items) {
        ModelFile.ExistingModelFile generated = getMcLoc("item/generated");
        ModelFile.ExistingModelFile handheld = getMcLoc("item/handheld");

        for (RegistryObject<Item> item : items) {
            String name = item.getId().getPath();

            // Checks if item texture exist and model texture doesn't exist
            if (!existingFileHelper.exists(new ResourceLocation(Bioplethora.MOD_ID, "item/" + name), TEXTURE)
                    || existingFileHelper.exists(new ResourceLocation(Bioplethora.MOD_ID, "item/" + name), MODEL))
                continue;

            Bioplethora.LOGGER.info(item.getId());

            getBuilder(item.getId().getPath()).parent(
                    //If Item is TieredItem and if item is NOT ArmorItem, minecraft/handheld model will be generated for that item.
                            // Otherwise, minecraft/generated model will be generated for that item.
                    (item.get() instanceof TieredItem) && !(item.get() instanceof ArmorItem) ? handheld : generated)
                    .texture("layer0", ItemModelProvider.ITEM_FOLDER + "/" + name);
        }
    }

    private void addBlockItems(final Collection<RegistryObject<Item>> blockItems) {
        for (RegistryObject<Item> item : blockItems) {
            String name = item.getId().getPath();

            // Checks if item texture exist and model texture doesn't exist
            if (!existingFileHelper.exists(new ResourceLocation(Bioplethora.MOD_ID, "block/" + name), MODEL)
                    || existingFileHelper.exists(new ResourceLocation(Bioplethora.MOD_ID, "item/" + name), MODEL))
                continue;

            Bioplethora.LOGGER.info(item.getId());

            withExistingParent(name, new ResourceLocation(Bioplethora.MOD_ID, "block/" + name));
        }
    }
}
