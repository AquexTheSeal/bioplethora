package io.github.bioplethora.data;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.blocks.tile_entities.ReinforcingTableBlock;
import io.github.bioplethora.registry.BPBlocks;
import net.minecraft.block.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
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
        this.simpleBlock(BPBlocks.BELLOPHITE_BLOCK.get());
        this.simpleBlock(BPBlocks.BELLOPHITE_CORE_BLOCK.get());
        this.simpleBlock(BPBlocks.NANDBRI_SCALE_BLOCK.get());

        this.simpleBlock(BPBlocks.MIRESTONE.get());

        this.simpleBlock(BPBlocks.GREEN_GRYLYNEN_CRYSTAL_BLOCK.get());
        this.simpleBlock(BPBlocks.YELLOW_GRYLYNEN_CRYSTAL_BLOCK.get());
        this.simpleBlock(BPBlocks.RED_GRYLYNEN_CRYSTAL_BLOCK.get());

        this.reinforcingTableBlock(BPBlocks.REINFORCING_TABLE.get());

        this.simpleCarpetBlock(BPBlocks.FLEIGNARITE_REMAINS.get());
        this.simpleCrossBlock(BPBlocks.FLEIGNARITE_VINES.get());
        this.simpleCrossBlock(BPBlocks.FLEIGNARITE_VINES_PLANT.get());

        // Nether Plants
        this.bigMushroomBlock(BPBlocks.SOUL_BIGSHROOM.get());

        this.doubleCropPlantBlock(BPBlocks.SOUL_TALL_GRASS.get());

        this.simpleCrossBlock(BPBlocks.LAVA_SPIRE.get());

        this.simpleCrossBlock(BPBlocks.BASALT_SPELEOTHERM.get());
        this.simpleCrossBlock(BPBlocks.BASALT_SPELEOTHERM_PLANT.get());
        this.simpleCrossBlock(BPBlocks.FIERY_BASALT_SPELEOTHERM.get());

        this.simpleCrossBlock(BPBlocks.SOUL_ETERN.get());
        this.simpleCrossBlock(BPBlocks.SOUL_ETERN_PLANT.get());
        this.simpleCrossBlock(BPBlocks.FLOURISHED_SOUL_ETERN.get());

        // Alphanum stone set
        this.simpleBlock(BPBlocks.ALPHANUM.get());
        this.simpleBlock(BPBlocks.ALPHANUM_BRICKS.get());
        this.simpleBlock(BPBlocks.POLISHED_ALPHANUM.get());
        this.logBlock(BPBlocks.ALPHANUM_PILLAR.get());
        this.differentTopLogBlock(BPBlocks.ALPHANUM_NUCLEUS.get(), BPBlocks.ALPHANUM_PILLAR.get());

        this.stairsBlock(BPBlocks.ALPHANUM_STAIRS.get(), bioResLoc("alphanum"));
        this.stairsBlock(BPBlocks.ALPHANUM_STAIRS_BRICKS.get(), bioResLoc("alphanum_bricks"));
        this.stairsBlock(BPBlocks.POLISHED_ALPHANUM_STAIRS.get(), bioResLoc("polished_alphanum"));
        this.wallBlock(BPBlocks.ALPHANUM_WALL.get(), bioResLoc("alphanum"));
        this.wallBlock(BPBlocks.ALPHANUM_WALL_BRICKS.get(), bioResLoc("alphanum_bricks"));
        this.wallBlock(BPBlocks.POLISHED_ALPHANUM_WALL.get(), bioResLoc("polished_alphanum"));
        this.slabBlock(BPBlocks.ALPHANUM_SLAB.get(), bioResLoc("alphanum"), bioResLoc("alphanum"));
        this.slabBlock(BPBlocks.ALPHANUM_SLAB_BRICKS.get(), bioResLoc("alphanum_bricks"), bioResLoc("alphanum_bricks"));
        this.slabBlock(BPBlocks.POLISHED_ALPHANUM_SLAB.get(), bioResLoc("polished_alphanum"), bioResLoc("polished_alphanum"));

        // Petrawood woodset
        this.fixedLogBlock(BPBlocks.PETRAWOOD_LOG.get());
        this.woodBlock(BPBlocks.PETRAWOOD_WOOD.get(), bioResLoc("petrawood_log_side"));
        this.fixedLogBlock(BPBlocks.STRIPPED_PETRAWOOD_LOG.get());
        this.woodBlock(BPBlocks.STRIPPED_PETRAWOOD_WOOD.get(), bioResLoc("stripped_petrawood_log_side"));
        this.simpleBlock(BPBlocks.PETRAWOOD_PLANKS.get());
        this.simpleBlock(BPBlocks.PETRAWOOD_LEAVES.get());
        //this.simpleCrossBlock(BPBlocks.PETRAWOOD_SAPLING.get());

        this.fenceBlock(BPBlocks.PETRAWOOD_FENCE.get(), bioResLoc("petrawood_planks"));
        this.fenceGateBlock(BPBlocks.PETRAWOOD_FENCE_GATE.get(), bioResLoc("petrawood_planks"));
        this.slabBlock(BPBlocks.PETRAWOOD_SLAB.get(), bioResLoc("petrawood_planks"), bioResLoc("petrawood_planks"));
        this.pressurePlateBlock(BPBlocks.PETRAWOOD_PRESSURE_PLATE.get(), bioResLoc("petrawood_planks"));
        this.stairsBlock(BPBlocks.PETRAWOOD_STAIRS.get(), bioResLoc("petrawood_planks"));
        this.buttonBlock(BPBlocks.PETRAWOOD_BUTTON.get(), bioResLoc("petrawood_planks"));

        /*
        this.doorBlock(BPBlocks.PETRAWOOD_DOOR.get(), bioResLoc("petrawood_door_lower"), bioResLoc("petrawood_door_upper"));
        this.trapdoorBlock(BPBlocks.PETRAWOOD_TRAPDOOR.get(), bioResLoc("petrawood_trapdoor"), true);

         */
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
    public void bigMushroomBlock(Block block) {
        BlockModelBuilder mushroom = models().singleTexture(block.getRegistryName().getPath(), bioResLoc("big_mushroom"), "0", blockTexture(block)).texture("particle", blockTexture(block));
        getVariantBuilder(block).partialState().setModels(new ConfiguredModel(mushroom));
    }

    public void reinforcingTableBlock(ReinforcingTableBlock block) {
        ModelFile all = models().orientableWithBottom(block.getRegistryName().getPath(),
                bioResLoc(block.getRegistryName().getPath() + "_side"),
                bioResLoc(block.getRegistryName().getPath() + "_side"),
                bioResLoc(block.getRegistryName().getPath() + "_bottom"),
                bioResLoc(block.getRegistryName().getPath() + "_top")
        );

        getVariantBuilder(block)
                .partialState().with(ReinforcingTableBlock.FACING_DIRECTION, Direction.SOUTH)
                .modelForState().modelFile(all).addModel()
                .partialState().with(ReinforcingTableBlock.FACING_DIRECTION, Direction.WEST)
                .modelForState().modelFile(all).rotationY(90).addModel()
                .partialState().with(ReinforcingTableBlock.FACING_DIRECTION, Direction.NORTH)
                .modelForState().modelFile(all).rotationY(180).addModel()
                .partialState().with(ReinforcingTableBlock.FACING_DIRECTION, Direction.EAST)
                .modelForState().modelFile(all).rotationY(270).addModel();
    }

    public void doubleCrossPlantBlock(Block block) {
        ModelFile lower = models().cross(block.getRegistryName().getPath() + "_bottom", bioResLoc(block.getRegistryName().getPath() + "_bottom"));
        ModelFile upper = models().cross(block.getRegistryName().getPath() + "_upper", bioResLoc(block.getRegistryName().getPath() + "_upper"));

        getVariantBuilder(block)
                .partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER)
                .modelForState().modelFile(lower).addModel()
                .partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER)
                .modelForState().modelFile(upper).addModel();
    }

    public void doubleCropPlantBlock(Block block) {
        ModelFile lower = models().crop(block.getRegistryName().getPath() + "_lower", bioResLoc(block.getRegistryName().getPath() + "_bottom"));
        ModelFile upper = models().crop(block.getRegistryName().getPath() + "_upper", bioResLoc(block.getRegistryName().getPath() + "_top"));

        getVariantBuilder(block)
                .partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER)
                .modelForState().modelFile(lower).addModel()
                .partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER)
                .modelForState().modelFile(upper).addModel();
    }

    public void simpleCarpetBlock(Block block) {
        getVariantBuilder(block).partialState().setModels(new ConfiguredModel(models().carpet(block.getRegistryName().getPath(), blockTexture(block))));
    }

    public void simpleCrossBlock(Block block) {
        getVariantBuilder(block).partialState().setModels(new ConfiguredModel(models().cross(block.getRegistryName().getPath(), blockTexture(block))));
    }

    public void pressurePlateBlock(PressurePlateBlock block, ResourceLocation all) {
        pressurePlateBlockInternal(block, block.getRegistryName().getPath(), all);
    }

    public void fixedLogBlock(RotatedPillarBlock block) {
        axisBlock(block, extend(blockTexture(block), "_side"), extend(blockTexture(block), "_top"));
    }

    public void differentTopLogBlock(RotatedPillarBlock block, RotatedPillarBlock topBlock) {
        axisBlock(block, blockTexture(block), extend(blockTexture(topBlock), "_top"));
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
        ModelFile button = models().singleTexture(baseName, mcResLoc("button"), all);
        ModelFile buttonPressed = models().singleTexture(baseName + "_pressed", mcResLoc("button_pressed"), all);
        ModelFile buttonInventory = models().singleTexture(baseName + "_inventory", mcResLoc("button_inventory"), all);

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
