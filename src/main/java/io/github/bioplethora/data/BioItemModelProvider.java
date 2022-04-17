package io.github.bioplethora.data;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.registry.BPBlocks;
import io.github.bioplethora.registry.BPItems;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.ShieldItem;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolItem;
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
    public void registerModels() {
        this.defaultItem(BPItems.ITEMS.getEntries());
        this.defaultBlock(BPBlocks.BLOCK_ITEMS.getEntries());

        this.grylynenShield(BPItems.GREEN_CRYSTAL_SHIELD);
        this.grylynenShield(BPItems.YELLOW_CRYSTAL_SHIELD);

        this.specialFlatTexture(BPBlocks.FLEIGNARITE_VINES, BPBlocks.FLEIGNARITE_VINES_PLANT);

        this.specialFlatTexture(BPBlocks.SOUL_TALL_GRASS, "soul_tall_grass_top");

        this.specialFlatTexture(BPBlocks.LAVA_SPIRE, BPBlocks.LAVA_SPIRE);

        this.specialFlatTexture(BPBlocks.BASALT_SPELEOTHERM, BPBlocks.BASALT_SPELEOTHERM_PLANT);
        this.specialFlatTexture(BPBlocks.FIERY_BASALT_SPELEOTHERM, BPBlocks.BASALT_SPELEOTHERM_PLANT);
        this.specialFlatTexture(BPBlocks.SOUL_ETERN, BPBlocks.SOUL_ETERN_PLANT);
    }

    @Nonnull
    @Override
    public String getName() {
        return Bioplethora.MOD_NAME + " Item models";
    }

    /**
     * If Item is ToolItem or SwordItem, minecraft/handheld model will be generated for that item.
     * Otherwise, minecraft/generated model will be generated for that item.
     */
    public void defaultItem(Collection<RegistryObject<Item>> items) {
        for (RegistryObject<Item> item : items) {
            String name = item.getId().getPath();
            Item getItem = item.get();
            ResourceLocation datagenLoc = new ResourceLocation(Bioplethora.MOD_ID, "item/" + name);

            ModelFile.ExistingModelFile modelType = getItem instanceof ToolItem || getItem instanceof SwordItem ?
                    getMcLoc("item/handheld") : getMcLoc("item/generated");

            if (!existingFileHelper.exists(datagenLoc, TEXTURE) || existingFileHelper.exists(datagenLoc, MODEL))
                continue;

            this.getBuilder(name).parent(modelType).texture("layer0", ITEM_FOLDER + "/" + name);
            Bioplethora.LOGGER.info("Generate Item Successful: " + item.getId());
        }
    }

    public void defaultBlock(Collection<RegistryObject<Item>> blockItems) {
        for (RegistryObject<Item> blockItem : blockItems) {
            String name = blockItem.getId().getPath();
            ResourceLocation itemLoc = new ResourceLocation(Bioplethora.MOD_ID, "item/" + name);
            ResourceLocation blockLoc = new ResourceLocation(Bioplethora.MOD_ID, "block/" + name);

            if (!existingFileHelper.exists(blockLoc, MODEL) || existingFileHelper.exists(itemLoc, MODEL))
                continue;

            this.withExistingParent(name, blockLoc);
            Bioplethora.LOGGER.info("Generate Block Item Successful: " + blockItem.getId());
        }
    }

    public void createBow(RegistryObject<Item> item) {
        Item items = item.get();
        String name = items.getRegistryName().getPath();
        ResourceLocation datagenLoc = new ResourceLocation(Bioplethora.MOD_ID, "item/" + name);

        if (items instanceof ShieldItem) {
            if (!existingFileHelper.exists(datagenLoc, TEXTURE) || existingFileHelper.exists(datagenLoc, MODEL)) {

            }
        } else {
            throw new IllegalStateException(name + " is not a Bow!");
        }
    }

    public void grylynenShield(RegistryObject<Item> item) {

        Item items = item.get();
        String name = items.getRegistryName().getPath();
        ResourceLocation datagenLoc = new ResourceLocation(Bioplethora.MOD_ID, "item/" + name);

        ModelFile.ExistingModelFile modelType = getBioLoc("item/grylynen_shield_base");
        ModelFile.ExistingModelFile blockingModelType = getBioLoc("item/grylynen_shield_base_blocking");

        if (items instanceof ShieldItem) {
            if (!existingFileHelper.exists(datagenLoc, TEXTURE) || existingFileHelper.exists(datagenLoc, MODEL)) {

                this.getBuilder(name).parent(modelType).texture("layer0", ITEM_FOLDER + "/" + name).override()
                        .predicate(new ResourceLocation("blocking"), 1).model(getBuilder(name + "_blocking"));

                this.getBuilder(name + "_blocking").parent(blockingModelType).texture("layer0", ITEM_FOLDER + "/" + name);

                Bioplethora.LOGGER.info("Generate Shield Item Successful: " + name);
            }
        } else {
            throw new IllegalStateException(name + " is not a Shield!");
        }
    }

    public void specialFlatTexture(RegistryObject<Block> block, RegistryObject<Block> textureBlock) {
        this.specialFlatTexture(block, textureBlock.get().getRegistryName().getPath());
    }

    public void specialFlatTexture(RegistryObject<Block> block, String texture) {
        String name = block.getId().getPath();
        ResourceLocation datagenLoc = new ResourceLocation(Bioplethora.MOD_ID, "item/" + name);

        ModelFile.ExistingModelFile modelType = getMcLoc("item/generated");

        if (!existingFileHelper.exists(datagenLoc, TEXTURE) || existingFileHelper.exists(datagenLoc, MODEL)) {
            this.getBuilder(name).parent(modelType).texture("layer0", BLOCK_FOLDER + "/" + texture);
            Bioplethora.LOGGER.info("Generate Block Item Successful: " + name);
        }
    }

    public ModelFile.ExistingModelFile getMcLoc(String mcModel) {
        return getExistingFile(mcLoc(mcModel));
    }

    public ModelFile.ExistingModelFile getBioLoc(String mcModel) {
        return getExistingFile(bioLoc(mcModel));
    }

    public ResourceLocation bioLoc(String name) {
        return new ResourceLocation(Bioplethora.MOD_ID, name);
    }
}
