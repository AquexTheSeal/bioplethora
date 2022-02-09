package io.github.bioplethora.datagen;

import io.github.bioplethora.Bioplethora;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BioBlockModelProvider extends BlockModelProvider {

    public BioBlockModelProvider(DataGenerator gen, String modid, ExistingFileHelper exFileHelper) {
        super(gen, modid, exFileHelper);
    }

    @Override
    protected void registerModels() {
        this.cubeAll("bellophite_block", bioResLoc("bellophite_block"));
        this.cubeAll("bellophite_core_block", bioResLoc("bellophite_core_block"));
        this.cubeAll("nandbri_scale_block", bioResLoc("nandbri_scale_block"));

        this.cubeAll("mirestone", bioResLoc("mirestone"));

        this.cubeAll("green_grylynen_crystal_block", bioResLoc("green_grylynen_crystal_block"));
        this.cubeAll("yellow_grylynen_crystal_block", bioResLoc("yellow_grylynen_crystal_block"));
        this.cubeAll("red_grylynen_crystal_block", bioResLoc("red_grylynen_crystal_block"));
    }

    private ResourceLocation bioResLoc(String texture) {
        return new ResourceLocation(Bioplethora.MOD_ID, BLOCK_FOLDER + "/" + texture);
    }

    @Override
    public BlockModelBuilder cubeAll(String name, ResourceLocation texture) {
        return singleTexture(name, mcLoc(BLOCK_FOLDER + "/cube_all"), "all", texture);
    }
}
