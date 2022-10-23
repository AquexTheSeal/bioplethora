package io.github.bioplethora.data;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.registry.BPBlocks;
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

        // Woodset
        tag(BPTags.Items.ENIVILE_LOGS)
                .add(BPBlocks.ENIVILE_LOG.get().asItem())
                .add(BPBlocks.ENIVILE_WOOD.get().asItem())
                .add(BPBlocks.STRIPPED_ENIVILE_LOG.get().asItem())
                .add(BPBlocks.STRIPPED_ENIVILE_WOOD.get().asItem())
        ;
        
        tag(BPTags.Items.CAERULWOOD_LOGS)
                .add(BPBlocks.CAERULWOOD_LOG.get().asItem())
                .add(BPBlocks.CAERULWOOD_WOOD.get().asItem())
                .add(BPBlocks.STRIPPED_CAERULWOOD_LOG.get().asItem())
                .add(BPBlocks.STRIPPED_CAERULWOOD_WOOD.get().asItem())
        ;

        tag(ItemTags.LOGS)
                .add(BPBlocks.CAERULWOOD_LOG.get().asItem())
                .add(BPBlocks.CAERULWOOD_WOOD.get().asItem())
                .add(BPBlocks.STRIPPED_CAERULWOOD_LOG.get().asItem())
                .add(BPBlocks.STRIPPED_CAERULWOOD_WOOD.get().asItem())
                .add(BPBlocks.ENIVILE_LOG.get().asItem())
                .add(BPBlocks.ENIVILE_WOOD.get().asItem())
                .add(BPBlocks.STRIPPED_ENIVILE_LOG.get().asItem())
                .add(BPBlocks.STRIPPED_ENIVILE_WOOD.get().asItem())
        ;
        tag(ItemTags.PLANKS)
                .add(BPBlocks.CAERULWOOD_PLANKS.get().asItem())
                .add(BPBlocks.ENIVILE_PLANKS.get().asItem())
        ;
        tag(ItemTags.SAPLINGS)
                .add(BPBlocks.CAERULWOOD_SAPLING.get().asItem())
                .add(BPBlocks.ENIVILE_SAPLING.get().asItem())
        ;
        tag(ItemTags.LEAVES)
                .add(BPBlocks.CAERULWOOD_LEAVES.get().asItem())
                .add(BPBlocks.ENIVILE_LEAVES_RED.get().asItem())
                .add(BPBlocks.ENIVILE_LEAVES_PINK.get().asItem())
        ;
        tag(ItemTags.DOORS)
                .add(BPBlocks.CAERULWOOD_DOOR.get().asItem())
                .add(BPBlocks.ENIVILE_DOOR.get().asItem())
        ;
        tag(ItemTags.WOODEN_PRESSURE_PLATES)
                .add(BPBlocks.CAERULWOOD_PRESSURE_PLATE.get().asItem())
                .add(BPBlocks.ENIVILE_PRESSURE_PLATE.get().asItem())
        ;
        tag(ItemTags.WOODEN_BUTTONS)
                .add(BPBlocks.CAERULWOOD_BUTTON.get().asItem())
                .add(BPBlocks.ENIVILE_BUTTON.get().asItem())
        ;
        tag(ItemTags.WOODEN_STAIRS)
                .add(BPBlocks.CAERULWOOD_STAIRS.get().asItem())
                .add(BPBlocks.ENIVILE_STAIRS.get().asItem())
        ;
        tag(ItemTags.WOODEN_SLABS)
                .add(BPBlocks.CAERULWOOD_SLAB.get().asItem())
                .add(BPBlocks.ENIVILE_SLAB.get().asItem())
        ;
        tag(ItemTags.WOODEN_FENCES)
                .add(BPBlocks.CAERULWOOD_FENCE.get().asItem())
                .add(BPBlocks.ENIVILE_FENCE.get().asItem())
        ;
    }
}
