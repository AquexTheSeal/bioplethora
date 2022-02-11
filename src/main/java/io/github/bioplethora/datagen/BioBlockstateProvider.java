package io.github.bioplethora.datagen;

import io.github.bioplethora.registry.BioplethoraBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BioBlockstateProvider extends BlockStateProvider {

    public BioBlockstateProvider(DataGenerator gen, String modid, ExistingFileHelper exFileHelper) {
        super(gen, modid, exFileHelper);
    }

    /**
     * Add blockstates for blocks that needs data generating.
     */
    @Override
    protected void registerStatesAndModels() {
        this.simpleBlock(BioplethoraBlocks.BELLOPHITE_BLOCK.get());
        this.simpleBlock(BioplethoraBlocks.BELLOPHITE_CORE_BLOCK.get());
        this.simpleBlock(BioplethoraBlocks.NANDBRI_SCALE_BLOCK.get());

        this.simpleBlock(BioplethoraBlocks.MIRESTONE.get());

        this.simpleBlock(BioplethoraBlocks.GREEN_GRYLYNEN_CRYSTAL_BLOCK.get());
        this.simpleBlock(BioplethoraBlocks.YELLOW_GRYLYNEN_CRYSTAL_BLOCK.get());
        this.simpleBlock(BioplethoraBlocks.RED_GRYLYNEN_CRYSTAL_BLOCK.get());
    }
}
