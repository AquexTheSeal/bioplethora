package io.github.bioplethora.generators;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.registry.BioplethoraBlocks;
import io.github.bioplethora.registry.BioplethoraTags;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BioBlockTagsProvider extends BlockTagsProvider {

    public BioBlockTagsProvider(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
        super(generatorIn, Bioplethora.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(BioplethoraTags.Blocks.FENCES)
                .add(BioplethoraBlocks.PETRAWOOD_FENCE.get())
        ;
        tag(BioplethoraTags.Blocks.FENCE_GATES)
                .add(BioplethoraBlocks.PETRAWOOD_FENCE_GATE.get())
        ;
    }
}
