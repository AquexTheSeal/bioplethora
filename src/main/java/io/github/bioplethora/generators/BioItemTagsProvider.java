package io.github.bioplethora.generators;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.registry.BioplethoraItems;
import io.github.bioplethora.registry.BioplethoraTags;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BioItemTagsProvider extends ItemTagsProvider {

    public BioItemTagsProvider(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider, ExistingFileHelper existingFileHelper) {
        super(dataGenerator, blockTagProvider, Bioplethora.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        // Wastelands of Baedoor Integration
        tag(BioplethoraTags.Items.WOBR_SABRE)
                .add(BioplethoraItems.ABYSSAL_BLADE.get())
        ;
    }
}
