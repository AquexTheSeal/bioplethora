package io.github.bioplethora.data;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.registry.BioplethoraItems;
import io.github.bioplethora.registry.BioplethoraTags;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BioItemTagsProvider extends ItemTagsProvider {

    public BioItemTagsProvider(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider, ExistingFileHelper existingFileHelper) {
        super(dataGenerator, blockTagProvider, Bioplethora.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {

        tag(ItemTags.ARROWS)
                .add(BioplethoraItems.BELLOPHITE_ARROW.get())
                .add(BioplethoraItems.WIND_ARROW.get())
        ;

        // Wastelands of Baedoor Integration
        tag(BioplethoraTags.Items.WOBR_SABRE)
                .add(BioplethoraItems.ABYSSAL_BLADE.get())
        ;
    }
}
