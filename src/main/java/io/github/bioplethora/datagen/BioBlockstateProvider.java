package io.github.bioplethora.datagen;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.registry.BioplethoraBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BioBlockstateProvider extends BlockStateProvider {

    public BioBlockstateProvider(DataGenerator gen, String modid, ExistingFileHelper exFileHelper) {
        super(gen, modid, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        this.simpleBlock(BioplethoraBlocks.BELLOPHITE_BLOCK.get());
        this.simpleBlock(BioplethoraBlocks.BELLOPHITE_CORE_BLOCK.get());
        this.simpleBlock(BioplethoraBlocks.NANDBRI_SCALE_BLOCK.get());
    }

    private ResourceLocation bioResLoc(String texture) {
        return new ResourceLocation(Bioplethora.MOD_ID, "block/" + texture);
    }
}
