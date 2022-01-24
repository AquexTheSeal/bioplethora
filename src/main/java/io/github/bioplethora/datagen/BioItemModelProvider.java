package io.github.bioplethora.datagen;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.registry.BioplethoraBlocks;
import io.github.bioplethora.registry.BioplethoraItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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

    @Override
    protected void registerModels() {
        generate(BioplethoraItems.ITEMS.getEntries());
        generateBlockItems(BioplethoraBlocks.BLOCK_ITEMS.getEntries());
    }

    @Nonnull
    @Override
    public String getName() {
        return Bioplethora.MOD_NAME + " Item models";
    }

    private void generate(final Collection<RegistryObject<Item>> items) {
        final ModelFile parentGenerated = getExistingFile(mcLoc("item/generated"));
        final ModelFile.ExistingModelFile parentHandheld = getExistingFile(mcLoc("item/handheld"));

        for (RegistryObject<Item> item : items) {
            String name = item.getId().getPath();

            if (name.startsWith("enchanted"))
                name = name.substring(name.indexOf("_") + 1);

            if (!existingFileHelper.exists(new ResourceLocation(Bioplethora.MOD_ID, "item/" + name), TEXTURE) || existingFileHelper.exists(new ResourceLocation(Bioplethora.MOD_ID, "item/" + name), MODEL))
                continue;

            Bioplethora.LOGGER.info(item.getId());
            getBuilder(item.getId().getPath()).parent(item.get().getMaxDamage(ItemStack.EMPTY) > 0 && !(item.get() instanceof ArmorItem) ? parentHandheld : parentGenerated).texture("layer0", ItemModelProvider.ITEM_FOLDER + "/" + name);
        }
    }

    private void generateBlockItems(final Collection<RegistryObject<Item>> itemBlocks) {
        for (RegistryObject<Item> item : itemBlocks) {
            String name = item.getId().getPath();
            if (!existingFileHelper.exists(new ResourceLocation(Bioplethora.MOD_ID, "block/" + name), MODEL) || existingFileHelper.exists(new ResourceLocation(Bioplethora.MOD_ID, "item/" + name), MODEL))
                continue;
            Bioplethora.LOGGER.info(item.getId());
            withExistingParent(name, new ResourceLocation(Bioplethora.MOD_ID, "block/" + name));

        }
    }
}
