package io.github.bioplethora.data;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.blocks.BPIdeFanBlock;
import io.github.bioplethora.blocks.SmallMushroomBlock;
import io.github.bioplethora.blocks.tile_entities.ReinforcingTableBlock;
import io.github.bioplethora.registry.BPBlocks;
import net.minecraft.block.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.*;
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

        this.reinforcingTableBlock(BPBlocks.REINFORCING_TABLE.get());

        this.simpleBlock(BPBlocks.GREEN_GRYLYNEN_CRYSTAL_BLOCK.get());
        this.simpleBlock(BPBlocks.YELLOW_GRYLYNEN_CRYSTAL_BLOCK.get());
        this.simpleBlock(BPBlocks.RED_GRYLYNEN_CRYSTAL_BLOCK.get());

        this.reinforcingTableBlock(BPBlocks.REINFORCING_TABLE.get());

        this.simpleCarpetBlock(BPBlocks.FLEIGNARITE_REMAINS.get());
        this.simpleCrossBlock(BPBlocks.FLEIGNARITE_VINES.get());
        this.simpleCrossBlock(BPBlocks.FLEIGNARITE_VINES_PLANT.get());

        // Nether Plants
        this.wsabbSoilMCResLoc(BPBlocks.CRYEA.get(), Blocks.NETHERRACK);
        this.simpleCarpetBlock(BPBlocks.CRYEA_CARPET.get(), bioResLoc("cryea_top"));

        this.simpleCrossBlock(BPBlocks.KYRIA.get());
        this.doubleCropPlantBlock(BPBlocks.KYRIA_BELINE.get());
        this.ideFanBlock(BPBlocks.KYRIA_IDE_FAN.get());

        this.smallMushroomBlock(BPBlocks.SOUL_MINISHROOM.get());
        this.bigMushroomBlock(BPBlocks.SOUL_BIGSHROOM.get());

        this.simpleCrossBlock(BPBlocks.LAVA_SPIRE.get());
        this.simpleCrossBlock(BPBlocks.WARPED_DANCER.get());

        this.simpleCrossBlock(BPBlocks.SOUL_SPROUTS.get());
        this.doubleCropPlantBlock(BPBlocks.SOUL_TALL_GRASS.get());

        this.simpleCrossBlock(BPBlocks.PINK_TWI.get());
        this.simpleCrossBlock(BPBlocks.PINK_TWI_PLANT.get());
        this.simpleCrossBlock(BPBlocks.RED_TWI.get());
        this.simpleCrossBlock(BPBlocks.RED_TWI_PLANT.get());

        this.simpleCrossBlock(BPBlocks.SPIRIT_DANGLER.get());
        this.simpleCrossBlock(BPBlocks.SPIRIT_DANGLER_PLANT.get());

        this.simpleCrossBlock(BPBlocks.TURQUOISE_PENDENT.get());
        this.simpleCrossBlock(BPBlocks.TURQUOISE_PENDENT_PLANT.get());
        this.simpleCrossBlock(BPBlocks.BLOSSOMING_TURQUOISE_PENDENT.get());

        this.simpleCrossBlock(BPBlocks.BASALT_SPELEOTHERM.get());
        this.simpleCrossBlock(BPBlocks.BASALT_SPELEOTHERM_PLANT.get());
        this.simpleCrossBlock(BPBlocks.FIERY_BASALT_SPELEOTHERM.get());

        this.simpleCrossBlock(BPBlocks.SOUL_ETERN.get());
        this.simpleCrossBlock(BPBlocks.SOUL_ETERN_PLANT.get());
        this.simpleCrossBlock(BPBlocks.FLOURISHED_SOUL_ETERN.get());

        // End Plants
        this.simpleBlock(BPBlocks.ENDURION.get());
        this.withSidesAndBottomBlock(BPBlocks.IRION.get(), BPBlocks.CRYOSOIL.get());
        this.simpleBlock(BPBlocks.CRYOSOIL.get());

        this.simpleBlock(BPBlocks.CYRA.get());

        this.simpleCrossBlock(BPBlocks.IRION_GRASS.get());
        this.doubleCrossPlantBlock(BPBlocks.IRION_TALL_GRASS.get());

        this.simpleCrossBlock(BPBlocks.CHORUS_IDON.get());
        this.ideFanBlock(BPBlocks.CHORUS_IDE_FAN.get());

        this.simpleCrossBlock(BPBlocks.AZURLIA.get());

        this.doubleCrossPlantBlock(BPBlocks.ARTAIRIUS.get());

        this.simpleBlock(BPBlocks.BYRSS_FRUIT_BLOCK.get());
        this.lanternPlantBlock(BPBlocks.BYRSS_LANTERN_PLANT.get());

        this.withSidesBlock(BPBlocks.CHORUS_CITRUS_BLOCK.get());
        this.lanternPlantBlock(BPBlocks.CHORUS_LANTERN_PLANT.get());

        this.simpleCrossBlock(BPBlocks.FROSTEM.get());

        this.simpleCrossBlock(BPBlocks.SPINXELTHORN.get());
        this.simpleCrossBlock(BPBlocks.SPINXELTHORN_PLANT.get());

        this.simpleCrossBlock(BPBlocks.GLACYNTH.get());
        this.simpleCrossBlock(BPBlocks.GLACYNTH_PLANT.get());

        this.multiLeafWoodset("enivile",
                BPBlocks.ENIVILE_PLANKS.get(), BPBlocks.ENIVILE_LOG.get(), BPBlocks.STRIPPED_ENIVILE_LOG.get(),
                BPBlocks.ENIVILE_WOOD.get(), BPBlocks.STRIPPED_ENIVILE_WOOD.get(), BPBlocks.ENIVILE_LEAVES_PINK.get(), BPBlocks.ENIVILE_LEAVES_RED.get(),
                BPBlocks.ENIVILE_SAPLING.get(), BPBlocks.ENIVILE_FENCE.get(), BPBlocks.ENIVILE_FENCE_GATE.get(),
                BPBlocks.ENIVILE_PRESSURE_PLATE.get(), BPBlocks.ENIVILE_DOOR.get(), BPBlocks.ENIVILE_TRAPDOOR.get(),
                BPBlocks.ENIVILE_BUTTON.get(), BPBlocks.ENIVILE_STAIRS.get(), BPBlocks.ENIVILE_SLAB.get(),
                BPBlocks.ENIVILE_SIGN.get(), BPBlocks.ENIVILE_WALL_SIGN.get()
        );
        this.woodset("caerulwood",
                BPBlocks.CAERULWOOD_PLANKS.get(), BPBlocks.CAERULWOOD_LOG.get(), BPBlocks.STRIPPED_CAERULWOOD_LOG.get(),
                BPBlocks.CAERULWOOD_WOOD.get(), BPBlocks.STRIPPED_CAERULWOOD_WOOD.get(), BPBlocks.CAERULWOOD_LEAVES.get(),
                BPBlocks.CAERULWOOD_SAPLING.get(), BPBlocks.CAERULWOOD_FENCE.get(), BPBlocks.CAERULWOOD_FENCE_GATE.get(),
                BPBlocks.CAERULWOOD_PRESSURE_PLATE.get(), BPBlocks.CAERULWOOD_DOOR.get(), BPBlocks.CAERULWOOD_TRAPDOOR.get(),
                BPBlocks.CAERULWOOD_BUTTON.get(), BPBlocks.CAERULWOOD_STAIRS.get(), BPBlocks.CAERULWOOD_SLAB.get(),
                BPBlocks.CAERULWOOD_SIGN.get(), BPBlocks.CAERULWOOD_WALL_SIGN.get()
        );

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

        /*
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

    public void woodset(String woodType,
            Block planks, Block log, Block strippedLog, Block wood, Block strippedWood, Block leaves, Block sapling,
            Block fence, Block fenceGate, Block pressurePlate, Block door, Block trapdoor,
                        Block button, Block stairs, Block slab, Block sign, Block wallSign) {
        this.fixedLogBlock((RotatedPillarBlock) log);
        this.woodBlock((RotatedPillarBlock) wood, bioResLoc(woodType + "_log_side"));
        this.fixedLogBlock((RotatedPillarBlock) strippedLog);
        this.woodBlock((RotatedPillarBlock) strippedWood, bioResLoc("stripped_" + woodType + "_log_side"));
        this.simpleBlock(planks);
        this.simpleBlock(leaves);
        this.simpleCrossBlock(sapling);

        this.fenceBlock((FenceBlock) fence, bioResLoc(woodType + "_planks"));
        this.fenceGateBlock((FenceGateBlock) fenceGate, bioResLoc(woodType + "_planks"));
        this.slabBlock((SlabBlock) slab, bioResLoc(woodType + "_planks"), bioResLoc(woodType + "_planks"));
        this.pressurePlateBlock((PressurePlateBlock) pressurePlate, bioResLoc(woodType + "_planks"));
        this.stairsBlock((StairsBlock) stairs, bioResLoc(woodType + "_planks"));
        this.buttonBlock((AbstractButtonBlock) button, bioResLoc(woodType + "_planks"));
        this.doorBlock((DoorBlock) door, bioResLoc(woodType + "_door_lower"), bioResLoc(woodType + "_door_upper"));
        this.trapdoorBlock((TrapDoorBlock) trapdoor, bioResLoc(woodType + "_trapdoor"), false);
        this.signBlock(sign, wallSign, bioResLoc(woodType + "_planks"));
    }

    public void multiLeafWoodset(String woodType,
                        Block planks, Block log, Block strippedLog, Block wood, Block strippedWood, Block leaves, Block leaves2, Block sapling,
                        Block fence, Block fenceGate, Block pressurePlate, Block door, Block trapdoor,
                        Block button, Block stairs, Block slab, Block sign, Block wallSign) {
        woodset(woodType, planks, log, strippedLog, wood, strippedWood, leaves, sapling, fence, fenceGate, pressurePlate, door, trapdoor, button, stairs, slab, sign, wallSign);
        this.simpleBlock(leaves2);
    }

    public void signBlock(Block sign, Block wallSign, ResourceLocation texture) {
        BlockModelBuilder pSign = models().getBuilder(sign.getRegistryName().getPath()).texture("particle", texture);
        BlockModelBuilder pWallSign = models().getBuilder(wallSign.getRegistryName().getPath()).texture("particle", texture);

        getVariantBuilder(sign).partialState().setModels(new ConfiguredModel(pSign));
        getVariantBuilder(wallSign).partialState().setModels(new ConfiguredModel(pWallSign));
    }

    public void bigMushroomBlock(Block block) {
        BlockModelBuilder mushroom = models().singleTexture(block.getRegistryName().getPath(), bioResLoc("big_mushroom"), "0", blockTexture(block)).texture("particle", blockTexture(block));
        getVariantBuilder(block).partialState().setModels(new ConfiguredModel(mushroom));
    }

    public void smallMushroomBlock(Block block) {
        getVariantBuilder(block)
                .forAllStates(state -> ConfiguredModel.builder()
                        .modelFile(getMinishroomModel(block, state))
                        .rotationY(minishroomRotation(state))
                        .build());
    }

    public BlockModelBuilder getMinishroomModel(Block block, BlockState state) {
        BlockModelBuilder mushroom1 = models().singleTexture(block.getRegistryName().getPath() + "_one", bioResLoc("small_mushroom_one"), "0", blockTexture(block)).texture("particle", blockTexture(block));
        BlockModelBuilder mushroom2 = models().singleTexture(block.getRegistryName().getPath() + "_two", bioResLoc("small_mushroom_two"), "0", blockTexture(block)).texture("particle", blockTexture(block));
        BlockModelBuilder mushroom3 = models().singleTexture(block.getRegistryName().getPath() + "_three", bioResLoc("small_mushroom_three"), "0", blockTexture(block)).texture("particle", blockTexture(block));

        IntegerProperty amount = SmallMushroomBlock.MINISHROOMS;

        switch (state.getValue(amount)) {
            default: return mushroom1;
            case 2: return mushroom2;
            case 3: return mushroom3;
        }
    }

    public int minishroomRotation(BlockState state) {
        DirectionProperty facing = SmallMushroomBlock.FACING_DIRECTION;

        switch (state.getValue(facing)) {
            default: return 0;
            case WEST: return 90;
            case NORTH: return 180;
            case EAST: return 270;
        }
    }

    public void ideFanBlock(Block block) {
        String defTexture = block.getRegistryName().getPath();
        BlockModelBuilder def = models().singleTexture(defTexture, bioResLoc("ide_fan"), "0", blockTexture(block)).texture("particle", blockTexture(block));
        String buddedTexture = block.getRegistryName().getPath() + "_top";
        BlockModelBuilder budded = models().singleTexture(buddedTexture, bioResLoc("ide_fan_budded"), "0", blockTexture(block)).texture("particle", blockTexture(block));

        getVariantBuilder(block)
                .partialState().with(BPIdeFanBlock.BUDDED, false)
                .modelForState().modelFile(def).addModel()
                .partialState().with(BPIdeFanBlock.BUDDED, true)
                .modelForState().modelFile(budded).addModel();
    }

    public void lanternPlantBlock(Block block) {
        String bottomTexture = block.getRegistryName().getPath() + "_bottom";
        BlockModelBuilder bottom = models().singleTexture(bottomTexture, bioResLoc("lantern_plant_bottom"), "0", extend(blockTexture(block), "_bottom")).texture("particle", extend(blockTexture(block), "_bottom"));
        String topTexture = block.getRegistryName().getPath() + "_top";
        BlockModelBuilder top = models().singleTexture(topTexture, bioResLoc("lantern_plant_top"), "0", extend(blockTexture(block), "_top")).texture("particle", extend(blockTexture(block), "_top"));

        getVariantBuilder(block)
                .partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER)
                .modelForState().modelFile(bottom).addModel()
                .partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER)
                .modelForState().modelFile(top).addModel();
    }

    public void doubleCrossPlantBlock(Block block) {
        ModelFile lower = models().cross(block.getRegistryName().getPath() + "_lower", bioResLoc(block.getRegistryName().getPath() + "_bottom"));
        ModelFile upper = models().cross(block.getRegistryName().getPath() + "_upper", bioResLoc(block.getRegistryName().getPath() + "_top"));

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

    public void wsabbSoilMCResLoc(Block topAndSides, Block soil) {
        getVariantBuilder(topAndSides).partialState().setModels(
                new ConfiguredModel(models()
                        .cube(topAndSides.getRegistryName().getPath(),
                                mcResLoc(soil.getRegistryName().getPath()),
                                bioResLoc(topAndSides.getRegistryName().getPath() + "_top"),
                                bioResLoc(topAndSides.getRegistryName().getPath() + "_side"),
                                bioResLoc(topAndSides.getRegistryName().getPath() + "_side"),
                                bioResLoc(topAndSides.getRegistryName().getPath() + "_side"),
                                bioResLoc(topAndSides.getRegistryName().getPath() + "_side")
                        ).texture("particle", bioResLoc(topAndSides.getRegistryName().getPath() + "_top"))
                )
        );
    }

    public void withSidesAndBottomBlock(Block topAndSides, Block soil) {
        getVariantBuilder(topAndSides).partialState().setModels(
                new ConfiguredModel(models()
                        .cube(topAndSides.getRegistryName().getPath(),
                                bioResLoc(soil.getRegistryName().getPath()),
                                bioResLoc(topAndSides.getRegistryName().getPath() + "_top"),
                                bioResLoc(topAndSides.getRegistryName().getPath() + "_side"),
                                bioResLoc(topAndSides.getRegistryName().getPath() + "_side"),
                                bioResLoc(topAndSides.getRegistryName().getPath() + "_side"),
                                bioResLoc(topAndSides.getRegistryName().getPath() + "_side")
                        ).texture("particle", bioResLoc(topAndSides.getRegistryName().getPath() + "_top"))
                )
        );
    }

    public void mcWithSidesAndBottomBlock(Block topAndSides, Block soil) {
        getVariantBuilder(topAndSides).partialState().setModels(
                new ConfiguredModel(models()
                        .cube(topAndSides.getRegistryName().getPath(),
                                mcResLoc(soil.getRegistryName().getPath()),
                                bioResLoc(topAndSides.getRegistryName().getPath() + "_top"),
                                bioResLoc(topAndSides.getRegistryName().getPath() + "_side"),
                                bioResLoc(topAndSides.getRegistryName().getPath() + "_side"),
                                bioResLoc(topAndSides.getRegistryName().getPath() + "_side"),
                                bioResLoc(topAndSides.getRegistryName().getPath() + "_side")
                        ).texture("particle", bioResLoc(topAndSides.getRegistryName().getPath() + "_top"))
                )
        );
    }

    public void withSidesBlock(Block block) {
        getVariantBuilder(block).partialState().setModels(
                new ConfiguredModel(models()
                        .cube(block.getRegistryName().getPath(),
                                bioResLoc(block.getRegistryName().getPath() + "_top"),
                                bioResLoc(block.getRegistryName().getPath() + "_top"),
                                bioResLoc(block.getRegistryName().getPath() + "_side"),
                                bioResLoc(block.getRegistryName().getPath() + "_side"),
                                bioResLoc(block.getRegistryName().getPath() + "_side"),
                                bioResLoc(block.getRegistryName().getPath() + "_side")
                        ).texture("particle", bioResLoc(block.getRegistryName().getPath() + "_top"))
                )
        );
    }

    public void reinforcingTableBlock(Block block) {
        getVariantBuilder(block).partialState().addModels(
                new ConfiguredModel(models()
                        .singleTexture(block.getRegistryName().getPath(), bioResLoc("reinforcing_table_base"), "1", blockTexture(block))
                        .texture("particle", blockTexture(block))
                )
        );
    }

    public void simpleCarpetBlock(Block block) {
        getVariantBuilder(block).partialState().setModels(new ConfiguredModel(models().carpet(block.getRegistryName().getPath(), blockTexture(block))));
    }

    public void simpleCarpetBlock(Block block, ResourceLocation textureLocation) {
        getVariantBuilder(block).partialState().setModels(new ConfiguredModel(models().carpet(block.getRegistryName().getPath(), textureLocation)));
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
