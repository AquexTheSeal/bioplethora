package io.github.bioplethora.data;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.registry.BPItems;
import io.github.bioplethora.registry.BPTags;
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
                .add(BPItems.BELLOPHITE_ARROW.get())
                .add(BPItems.WIND_ARROW.get())
        ;

        // Curios Integration
        tag(BPTags.Items.CHARM)
                .add(BPItems.SPIRIT_FISSION_CHARM.get())
                .add(BPItems.SPIRIT_MANIPULATION_CHARM.get())
                .add(BPItems.SPIRIT_STRENGTHENING_CHARM.get())
        ;

        tag(BPTags.Items.NECKLACE)
                .add(BPItems.SPIRIT_FISSION_CHARM.get())
                .add(BPItems.SPIRIT_MANIPULATION_CHARM.get())
                .add(BPItems.SPIRIT_STRENGTHENING_CHARM.get())
        ;

        // Wastelands of Baedoor Integration
        tag(BPTags.Items.WOBR_SABRE)
                .add(BPItems.ABYSSAL_BLADE.get())
        ;
    }
}
