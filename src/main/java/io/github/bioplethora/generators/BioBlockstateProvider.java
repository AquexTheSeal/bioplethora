package io.github.bioplethora.generators;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.registry.BioplethoraBlocks;
import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.WoodButtonBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
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

        // Petrawood woodset
        this.fixedLogBlock(BioplethoraBlocks.PETRAWOOD_LOG.get());
        this.woodBlock(BioplethoraBlocks.PETRAWOOD_WOOD.get(), bioResLoc("petrawood_log_side"));
        this.simpleBlock(BioplethoraBlocks.PETRAWOOD_PLANKS.get());
        this.simpleBlock(BioplethoraBlocks.PETRAWOOD_LEAVES.get());

        this.fenceBlock(BioplethoraBlocks.PETRAWOOD_FENCE.get(), bioResLoc("petrawood_planks"));
        this.fenceGateBlock(BioplethoraBlocks.PETRAWOOD_FENCE_GATE.get(), bioResLoc("petrawood_planks"));
        this.slabBlock(BioplethoraBlocks.PETRAWOOD_SLAB.get(), bioResLoc("petrawood_planks"), bioResLoc("petrawood_planks"));
        this.pressurePlateBlock(BioplethoraBlocks.PETRAWOOD_PRESSURE_PLATE.get(), bioResLoc("petrawood_planks"));
        this.stairsBlock(BioplethoraBlocks.PETRAWOOD_STAIRS.get(), bioResLoc("petrawood_planks"));
        this.buttonBlock(BioplethoraBlocks.PETRAWOOD_BUTTON.get(), bioResLoc("petrawood_planks"));
    }

    private ResourceLocation bioResLoc(String texture) {
        return new ResourceLocation(Bioplethora.MOD_ID, "block/" + texture);
    }

    private ResourceLocation mcResLoc(String texture) {
        return new ResourceLocation("minecraft", "block/" + texture);
    }

    public ResourceLocation extend(ResourceLocation rl, String suffix) {
        return new ResourceLocation(rl.getNamespace(), rl.getPath() + suffix);
    }

    // Custom Generators
    public void pressurePlateBlock(PressurePlateBlock block, ResourceLocation all) {
        pressurePlateBlockInternal(block, block.getRegistryName().getPath(), all);
    }

    public void fixedLogBlock(RotatedPillarBlock block) {
        axisBlock(block, extend(blockTexture(block), "_side"), extend(blockTexture(block), "_top"));
    }

    public void pressurePlateBlock(PressurePlateBlock block, ModelFile pressurePlateUp, ModelFile pressurePlateDown) {
        getVariantBuilder(block).forAllStates(state -> {
            Boolean powered = state.getValue(PressurePlateBlock.POWERED);
            return ConfiguredModel.builder().modelFile(powered ? pressurePlateDown : pressurePlateUp).build();
        });
    }

    private void pressurePlateBlockInternal(PressurePlateBlock block, String baseName, ResourceLocation all) {
        ModelFile pressurePlateUp = models().withExistingParent(baseName,
                mcResLoc("pressure_plate_up")).texture("texture", all);
        ModelFile pressurePlateDown = models().withExistingParent(baseName + "_down",
                mcResLoc("pressure_plate_down")).texture("texture", all);
        pressurePlateBlock(block, pressurePlateUp, pressurePlateDown);
    }

    public void woodBlock(RotatedPillarBlock block, ResourceLocation texture) {
        axisBlock(block, texture, texture);
    }

    private void buttonBlock(AbstractButtonBlock block, ResourceLocation all) {

        String baseName = block.getRegistryName().getPath();

        ModelFile button = models().withExistingParent(baseName, mcResLoc("button")).texture("texture", all);
        ModelFile buttonPressed = models().withExistingParent(baseName, mcResLoc("button_pressed")).texture("texture", all);
        int angleOffset = 180;
        getVariantBuilder(block)
                .forAllStates(state -> {
                    boolean pushed = state.getValue(WoodButtonBlock.POWERED);
                    return ConfiguredModel.builder().modelFile(pushed ? buttonPressed : button)
                            .rotationX(state.getValue(BlockStateProperties.ATTACH_FACE).ordinal() * 90)
                            .rotationY((((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + angleOffset) +
                                    (state.getValue(BlockStateProperties.ATTACH_FACE) == AttachFace.CEILING ? 180 : 0)) % 360)
                            .build();
                });
    }
}
