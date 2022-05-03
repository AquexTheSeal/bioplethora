package io.github.bioplethora.registry;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.blocks.*;
import io.github.bioplethora.blocks.specific.*;
import io.github.bioplethora.blocks.tile_entities.FleignariteSplotchBlock;
import io.github.bioplethora.blocks.tile_entities.ReinforcingTableBlock;
import io.github.bioplethora.enums.BioPlantShape;
import io.github.bioplethora.enums.BioPlantType;
import io.github.bioplethora.registry.worldgen.BPNBTTrees;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class BPBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Bioplethora.MOD_ID);
    public static final DeferredRegister<Item> BLOCK_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Bioplethora.MOD_ID);

    public static final RegistryObject<ReinforcingTableBlock> REINFORCING_TABLE = registerBlock("reinforcing_table", () -> new ReinforcingTableBlock(AbstractBlock.Properties.of(Material.METAL)
            .strength(20.0F, 35).sound(SoundType.NETHERITE_BLOCK).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()), BPItemGroup.BioplethoraItemItemGroup);

    public static final RegistryObject<Block> NANDBRI_SCALE_BLOCK = registerBlock("nandbri_scale_block", () -> new Block(AbstractBlock.Properties.of(Material.WOOL, MaterialColor.COLOR_LIGHT_GRAY)
            .strength(0.8F, 0.8F).harvestTool(ToolType.AXE).harvestLevel(3).sound(SoundType.BONE_BLOCK)), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<Block> BELLOPHITE_BLOCK = registerFireResBlock("bellophite_block", () -> new Block(AbstractBlock.Properties.of(Material.METAL).requiresCorrectToolForDrops()
            .strength(50.0F, 1200.0F).harvestTool(ToolType.PICKAXE).sound(SoundType.NETHERITE_BLOCK)), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<Block> BELLOPHITE_CORE_BLOCK = registerFireResBlock("bellophite_core_block", () -> new BellophiteCoreBlock(AbstractBlock.Properties.of(Material.GLASS)
            .strength(0.3F).sound(SoundType.GLASS).noOcclusion()), BPItemGroup.BioplethoraItemItemGroup);

    public static final RegistryObject<Block> GREEN_GRYLYNEN_CRYSTAL_BLOCK = registerFireResBlock("green_grylynen_crystal_block", () -> new Block(AbstractBlock.Properties.of(Material.GLASS)
            .strength(0.3F).sound(SoundType.NETHER_GOLD_ORE).noOcclusion().lightLevel((block) -> 7)), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<Block> YELLOW_GRYLYNEN_CRYSTAL_BLOCK = registerFireResBlock("yellow_grylynen_crystal_block", () -> new Block(AbstractBlock.Properties.of(Material.GLASS)
            .strength(0.4F).sound(SoundType.NETHER_GOLD_ORE).noOcclusion().lightLevel((block) -> 9)), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<Block> RED_GRYLYNEN_CRYSTAL_BLOCK = registerFireResBlock("red_grylynen_crystal_block", () -> new Block(AbstractBlock.Properties.of(Material.GLASS)
            .strength(0.5F).sound(SoundType.NETHER_GOLD_ORE).noOcclusion().lightLevel((block) -> 12)), BPItemGroup.BioplethoraItemItemGroup);

    public static final RegistryObject<Block> MIRESTONE = registerBlock("mirestone", () -> new Block(AbstractBlock.Properties.of(Material.STONE)
            .strength(1.2F, 4.8F).harvestTool(ToolType.SHOVEL).sound(SoundType.GRAVEL).noOcclusion()), null);

    // Fleignarite Blocks
    public static final RegistryObject<Block> FLEIGNARITE_REMAINS = registerBlock("fleignarite_remains", () -> new FleignariteRemainsBlock(AbstractBlock.Properties.of(Material.PLANT)
            .strength(0.3F).friction(0.8F).harvestTool(ToolType.SHOVEL).sound(SoundType.SLIME_BLOCK).noOcclusion()
            .lightLevel((block) -> 5)), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<Block> FLEIGNARITE_SPLOTCH = registerBlock("fleignarite_splotch", () -> new FleignariteSplotchBlock(AbstractBlock.Properties.of(Material.PLANT)
            .strength(0.3F).friction(0.8F).harvestTool(ToolType.SHOVEL).sound(SoundType.SLIME_BLOCK).noOcclusion()
            .lightLevel((block) -> 5)), BPItemGroup.BioplethoraItemItemGroup);

    public static final RegistryObject<Block> FLEIGNARITE_VINES = registerBlock("fleignarite_vines", () -> new FleignariteVinesTopBlock(AbstractBlock.Properties.of(Material.PLANT)
            .strength(0.3F).instabreak().sound(SoundType.SLIME_BLOCK).noCollission()
            .lightLevel((block) -> 5)), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<Block> FLEIGNARITE_VINES_PLANT = registerBlock("fleignarite_vines_plant", () -> new FleignariteVinesBlock(AbstractBlock.Properties.of(Material.PLANT)
            .strength(0.3F).instabreak().sound(SoundType.SLIME_BLOCK).noCollission()
            .lightLevel((block) -> 5)), null, false);

    // Nether Plants

    // Mushrooms
    public static final RegistryObject<Block> SOUL_MINISHROOM = registerBlock("soul_minishroom", () -> new SmallMushroomBlock(BioPlantType.SOUL_SAND_VALLEY,
            AbstractBlock.Properties.of(Material.PLANT).sound(SoundType.SOUL_SOIL).strength(0.5F).noCollission().lightLevel((block) -> 4 * block.getValue(SmallMushroomBlock.MINISHROOMS))), BPItemGroup.BioplethoraItemItemGroup);

    public static final RegistryObject<Block> SOUL_BIGSHROOM = registerBlock("soul_bigshroom", () -> new BPPlantBlock(BioPlantType.SOUL_SAND_VALLEY, BioPlantShape.BIG_MUSHROOM,
            AbstractBlock.Properties.of(Material.PLANT).sound(SoundType.SOUL_SOIL).strength(0.5F).noOcclusion().lightLevel((block) -> 7)), BPItemGroup.BioplethoraItemItemGroup);

    // Extras
    public static final RegistryObject<Block> LAVA_SPIRE = registerBlock("lava_spire", () -> new LavaSpireBlock(AbstractBlock.Properties.of(Material.REPLACEABLE_FIREPROOF_PLANT)
            .sound(SoundType.NETHER_SPROUTS).strength(0.3F).instabreak().noCollission().lightLevel((block) -> 13)), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<Block> WARPED_DANCER = registerBlock("warped_dancer", () -> new BPPlantBlock(BioPlantType.WARPED_FOREST, BioPlantShape.SIMPLE_PLANT,
            AbstractBlock.Properties.of(Material.REPLACEABLE_FIREPROOF_PLANT).sound(SoundType.NETHER_SPROUTS).strength(0.3F).instabreak().noCollission().lightLevel((block) -> 3)), BPItemGroup.BioplethoraItemItemGroup);

    // Minor Plants (e.g. Grass, Tallgrass)
    public static final RegistryObject<Block> SOUL_SPROUTS = registerBlock("soul_sprouts", () -> new BPPlantBlock(BioPlantType.SOUL_SAND_VALLEY, BioPlantShape.SIMPLE_PLANT,
            AbstractBlock.Properties.of(Material.REPLACEABLE_FIREPROOF_PLANT).sound(SoundType.SOUL_SOIL).strength(0.3F).instabreak().noCollission()), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<Block> SOUL_TALL_GRASS = registerBlock("soul_tall_grass", () -> new BPDoublePlantBlock(BioPlantType.SOUL_SAND_VALLEY, BioPlantShape.SIMPLE_PLANT,
            AbstractBlock.Properties.of(Material.REPLACEABLE_FIREPROOF_PLANT).sound(SoundType.SOUL_SOIL).strength(0.3F).instabreak().noCollission()), BPItemGroup.BioplethoraItemItemGroup);

    // Nether Vines
    public static final RegistryObject<Block> SPIRIT_DANGLER = registerBlock("spirit_dangler", () -> new BPVinesTopBlock.SpiritDanglerTopBlock(
            AbstractBlock.Properties.of(Material.REPLACEABLE_FIREPROOF_PLANT).sound(SoundType.TWISTING_VINES).instabreak().noCollission().lightLevel((block) -> 6)), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<Block> SPIRIT_DANGLER_PLANT = registerBlock("spirit_dangler_plant", () -> new BPVinesBlock.SpiritDanglerBlock(
            AbstractBlock.Properties.of(Material.REPLACEABLE_FIREPROOF_PLANT).sound(SoundType.TWISTING_VINES).instabreak().noCollission().lightLevel((block) -> 8)), null, false);

    // Fruitable Nether Vines
    public static final RegistryObject<Block> BASALT_SPELEOTHERM = registerBlock("basalt_speleotherm", () -> new BPFruitableVinesTopBlock.BasaltSpeleothermTopBlock(
            AbstractBlock.Properties.of(Material.PLANT).sound(SoundType.BASALT).strength(0.3F).noOcclusion()), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<Block> BASALT_SPELEOTHERM_PLANT = registerBlock("basalt_speleotherm_plant", () -> new BPFruitableVinesBlock.BasaltSpeleothermBlock(
            AbstractBlock.Properties.of(Material.PLANT).sound(SoundType.BASALT).strength(0.3F).noOcclusion()), null, false);
    public static final RegistryObject<Block> FIERY_BASALT_SPELEOTHERM = registerBlock("fiery_basalt_speleotherm", () -> new BPFruitableVinesBlock.FieryBasaltSpeleothermBlock(
            AbstractBlock.Properties.of(Material.PLANT).sound(SoundType.BASALT).strength(0.3F).noOcclusion().lightLevel((block) -> 7)), null, false);

    public static final RegistryObject<Block> THONTUS_THISTLE = registerBlock("thontus_thistle", () -> new BPFruitableVinesTopBlock.ThontusThistleTopBlock(
            AbstractBlock.Properties.of(Material.PLANT).sound(SoundType.VINE).strength(0.3F).instabreak().noCollission()), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<Block> THONTUS_THISTLE_PLANT = registerBlock("thontus_thistle_plant", () -> new BPFruitableVinesBlock.ThontusThistleBlock(
            AbstractBlock.Properties.of(Material.PLANT).sound(SoundType.VINE).strength(0.3F).instabreak().noCollission()), null, false);
    public static final RegistryObject<Block> BERRIED_THONTUS_THISTLE = registerBlock("berried_thontus_thistle", () -> new BPFruitableVinesBlock.BerriedThontusThistleBlock(
            AbstractBlock.Properties.of(Material.PLANT).sound(SoundType.VINE).strength(0.3F).instabreak().noCollission().lightLevel((block) -> 7)), null, false);

    public static final RegistryObject<Block> TURQUOISE_PENDENT = registerBlock("turquoise_pendent", () -> new BPFruitableVinesTopBlock.TurquoisePendentTopBlock(
            AbstractBlock.Properties.of(Material.PLANT).sound(SoundType.TWISTING_VINES).strength(0.3F).instabreak().noCollission()), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<Block> TURQUOISE_PENDENT_PLANT = registerBlock("turquoise_pendent_plant", () -> new BPFruitableVinesBlock.TurquoisePendentBlock(
            AbstractBlock.Properties.of(Material.PLANT).sound(SoundType.TWISTING_VINES).strength(0.3F).instabreak().noCollission()), null, false);
    public static final RegistryObject<Block> BLOSSOMING_TURQUOISE_PENDENT = registerBlock("blossoming_turquoise_pendent", () -> new BPFruitableVinesBlock.BlossomingTurquoisePendentBlock(
            AbstractBlock.Properties.of(Material.PLANT).sound(SoundType.TWISTING_VINES).strength(0.3F).instabreak().noCollission().lightLevel((block) -> 7)), null, false);

    public static final RegistryObject<Block> CERISE_IVY = registerBlock("cerise_ivy", () -> new BPFruitableVinesTopBlock.CeriseIvyTopBlock(
            AbstractBlock.Properties.of(Material.PLANT).sound(SoundType.WEEPING_VINES).strength(0.3F).instabreak().noCollission()), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<Block> CERISE_IVY_PLANT = registerBlock("cerise_ivy_plant", () -> new BPFruitableVinesBlock.CeriseIvyBlock(
            AbstractBlock.Properties.of(Material.PLANT).sound(SoundType.WEEPING_VINES).strength(0.3F).instabreak().noCollission()), null, false);
    public static final RegistryObject<Block> SEEDED_CERISE_IVY = registerBlock("seeded_cerise_ivy", () -> new BPFruitableVinesBlock.SeededCeriseIvyBlock(
            AbstractBlock.Properties.of(Material.PLANT).sound(SoundType.WEEPING_VINES).strength(0.3F).instabreak().noCollission().lightLevel((block) -> 7)), null, false);

    public static final RegistryObject<Block> SOUL_ETERN = registerBlock("soul_etern", () -> new BPFruitableVinesTopBlock.SoulEternTopBlock(
            AbstractBlock.Properties.of(Material.PLANT).sound(SoundType.SOUL_SOIL).strength(0.3F).instabreak().noCollission()), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<Block> SOUL_ETERN_PLANT = registerBlock("soul_etern_plant", () -> new BPFruitableVinesBlock.SoulEternBlock(
            AbstractBlock.Properties.of(Material.PLANT).sound(SoundType.SOUL_SOIL).strength(0.3F).instabreak().noCollission()), null, false);
    public static final RegistryObject<Block> FLOURISHED_SOUL_ETERN = registerBlock("flourished_soul_etern", () -> new BPFruitableVinesBlock.FlourishedSoulEternBlock(
            AbstractBlock.Properties.of(Material.PLANT).sound(SoundType.SOUL_SOIL).strength(0.3F).instabreak().noCollission().lightLevel((block) -> 7)), null, false);

    // Potted Plants
    public static final RegistryObject<FlowerPotBlock> POTTED_LAVA_SPIRE = registerBlock("potted_lava_spire", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, LAVA_SPIRE, AbstractBlock.Properties.copy(Blocks.FLOWER_POT)), null, false);

    // Caerulwood Woodset
    public static final RegistryObject<Block> CAERULWOOD_PLANKS = registerBlock("caerulwood_planks", () -> new Block(AbstractBlock.Properties.copy(Blocks.CRIMSON_PLANKS).strength(2.4F).sound(SoundType.WOOD).noOcclusion()), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<RotatedPillarBlock> CAERULWOOD_LOG = registerBlock("caerulwood_log", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.CRIMSON_HYPHAE).strength(2.4F).sound(SoundType.WOOD).noOcclusion()), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_CAERULWOOD_LOG = registerBlock("stripped_caerulwood_log", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(BPBlocks.CAERULWOOD_LOG.get())), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<RotatedPillarBlock> CAERULWOOD_WOOD = registerBlock("caerulwood_wood", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.CRIMSON_HYPHAE).strength(2.4F).sound(SoundType.WOOD).noOcclusion()), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_CAERULWOOD_WOOD = registerBlock("stripped_caerulwood_wood", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(BPBlocks.CAERULWOOD_WOOD.get())), BPItemGroup.BioplethoraItemItemGroup);

    public static final RegistryObject<LeavesBlock> CAERULWOOD_LEAVES = registerBlock("caerulwood_leaves", () -> new LeavesBlock(AbstractBlock.Properties.copy(Blocks.OAK_LEAVES).sound(SoundType.GRASS).noOcclusion()), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<SaplingBlock> CAERULWOOD_SAPLING = registerBlock("caerulwood_sapling", () -> new BPSaplingBlock(BioPlantType.CAERULWOOD_TREE, new BPNBTTrees.CaerulwoodNBTTree(), AbstractBlock.Properties.copy(Blocks.OAK_SAPLING).noCollission()), BPItemGroup.BioplethoraItemItemGroup);

    public static final RegistryObject<FenceBlock> CAERULWOOD_FENCE = registerBlock("caerulwood_fence", () -> new FenceBlock(AbstractBlock.Properties.copy(BPBlocks.CAERULWOOD_PLANKS.get())), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<FenceGateBlock> CAERULWOOD_FENCE_GATE = registerBlock("caerulwood_fence_gate", () -> new FenceGateBlock(AbstractBlock.Properties.copy(BPBlocks.CAERULWOOD_PLANKS.get())), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<StandingSignBlock> CAERULWOOD_SIGN = registerBlock("caerulwood_sign", () -> new BPStandingSignBlock(AbstractBlock.Properties.copy(BPBlocks.CAERULWOOD_PLANKS.get()), BPWoodTypes.CAERULWOOD), null, false);
    public static final RegistryObject<WallSignBlock> CAERULWOOD_WALL_SIGN = registerBlock("caerulwood_wall_sign", () -> new BPWallSignBlock(AbstractBlock.Properties.copy(BPBlocks.CAERULWOOD_PLANKS.get()), BPWoodTypes.CAERULWOOD), null, false);
    public static final RegistryObject<PressurePlateBlock> CAERULWOOD_PRESSURE_PLATE = registerBlock("caerulwood_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, AbstractBlock.Properties.copy(BPBlocks.CAERULWOOD_PLANKS.get())), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<DoorBlock> CAERULWOOD_DOOR = registerBlock("caerulwood_door", () -> new DoorBlock(AbstractBlock.Properties.copy(BPBlocks.CAERULWOOD_PLANKS.get())), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<TrapDoorBlock> CAERULWOOD_TRAPDOOR = registerBlock("caerulwood_trapdoor", () -> new TrapDoorBlock(AbstractBlock.Properties.copy(BPBlocks.CAERULWOOD_PLANKS.get())), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<WoodButtonBlock> CAERULWOOD_BUTTON = registerBlock("caerulwood_button", () -> new WoodButtonBlock(AbstractBlock.Properties.copy(BPBlocks.CAERULWOOD_PLANKS.get())), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<StairsBlock> CAERULWOOD_STAIRS = registerBlock("caerulwood_stairs", () -> new StairsBlock(() -> BPBlocks.CAERULWOOD_PLANKS.get().defaultBlockState(), AbstractBlock.Properties.copy(BPBlocks.CAERULWOOD_PLANKS.get())), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<SlabBlock> CAERULWOOD_SLAB = registerBlock("caerulwood_slab", () -> new SlabBlock(AbstractBlock.Properties.copy(BPBlocks.CAERULWOOD_PLANKS.get())), BPItemGroup.BioplethoraItemItemGroup);

    // End Plants
    public static final RegistryObject<Block> AZURLIA = registerBlock("azurlia", () -> new BPReversePlantBlock(BioPlantType.CAERULWOOD_TREE, BioPlantShape.SIMPLE_PLANT,
            AbstractBlock.Properties.copy(BPBlocks.CAERULWOOD_LEAVES.get()).lightLevel((block) -> 13).noCollission()), BPItemGroup.BioplethoraItemItemGroup);

    // End Blocks
    public static final RegistryObject<Block> CYRA = registerBlock("cyra", () -> new Block(AbstractBlock.Properties.of(Material.GRASS)
            .strength(1.0F).harvestTool(ToolType.SHOVEL).sound(SoundType.NETHER_SPROUTS).noOcclusion()), BPItemGroup.BioplethoraItemItemGroup);

    // Alphanum Set
    public static final RegistryObject<Block> ALPHANUM = registerBlock("alphanum", () -> new Block(AbstractBlock.Properties.of(Material.STONE)
            .strength(50.0F, 1200.0F).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.NETHER_GOLD_ORE).noOcclusion()), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<Block> ALPHANUM_BRICKS = registerBlock("alphanum_bricks", () ->
            new Block(AbstractBlock.Properties.copy(BPBlocks.ALPHANUM.get())), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<Block> POLISHED_ALPHANUM = registerBlock("polished_alphanum", () ->
            new Block(AbstractBlock.Properties.copy(BPBlocks.ALPHANUM.get())), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<RotatedPillarBlock> ALPHANUM_PILLAR = registerBlock("alphanum_pillar", () ->
            new RotatedPillarBlock(AbstractBlock.Properties.copy(BPBlocks.ALPHANUM.get())), BPItemGroup.BioplethoraItemItemGroup);

    public static final RegistryObject<RotatedPillarBlock> ALPHANUM_NUCLEUS = registerBlock("alphanum_nucleus", () ->
            new RotatedPillarBlock(AbstractBlock.Properties.copy(BPBlocks.ALPHANUM.get()).lightLevel((block) -> 8)), BPItemGroup.BioplethoraItemItemGroup);

    public static final RegistryObject<StairsBlock> ALPHANUM_STAIRS = registerBlock("alphanum_stairs", () ->
            new StairsBlock(BPBlocks.ALPHANUM.get().defaultBlockState(), AbstractBlock.Properties.copy(BPBlocks.ALPHANUM.get())), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<WallBlock> ALPHANUM_WALL = registerBlock("alphanum_wall", () ->
            new WallBlock(AbstractBlock.Properties.copy(BPBlocks.ALPHANUM.get())), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<SlabBlock> ALPHANUM_SLAB = registerBlock("alphanum_slab", () ->
            new SlabBlock(AbstractBlock.Properties.copy(BPBlocks.ALPHANUM.get())), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<StairsBlock> ALPHANUM_STAIRS_BRICKS = registerBlock("alphanum_brick_stairs", () ->
            new StairsBlock((BPBlocks.ALPHANUM_BRICKS.get().defaultBlockState()), AbstractBlock.Properties.copy(BPBlocks.ALPHANUM.get())), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<WallBlock> ALPHANUM_WALL_BRICKS = registerBlock("alphanum_brick_wall", () ->
            new WallBlock(AbstractBlock.Properties.copy(BPBlocks.ALPHANUM.get())), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<SlabBlock> ALPHANUM_SLAB_BRICKS = registerBlock("alphanum_brick_slab", () ->
            new SlabBlock(AbstractBlock.Properties.copy(BPBlocks.ALPHANUM.get())), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<StairsBlock> POLISHED_ALPHANUM_STAIRS = registerBlock("polished_alphanum_stairs", () ->
            new StairsBlock(BPBlocks.POLISHED_ALPHANUM.get().defaultBlockState(), AbstractBlock.Properties.copy(BPBlocks.ALPHANUM.get())), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<WallBlock> POLISHED_ALPHANUM_WALL = registerBlock("polished_alphanum_wall", () ->
            new WallBlock(AbstractBlock.Properties.copy(BPBlocks.ALPHANUM.get())), BPItemGroup.BioplethoraItemItemGroup);
    public static final RegistryObject<SlabBlock> POLISHED_ALPHANUM_SLAB = registerBlock("polished_alphanum_slab", () ->
            new SlabBlock(AbstractBlock.Properties.copy(BPBlocks.ALPHANUM.get())), BPItemGroup.BioplethoraItemItemGroup);

    // todo: Petrawood set for Rocky Woodlands biome
    /*
    public static final RegistryObject<RotatedPillarBlock> PETRAWOOD_LOG = registerBlock("petrawood_log", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.CRIMSON_HYPHAE).strength(2.4F).sound(SoundType.WOOD).noOcclusion()), null);
    public static final RegistryObject<RotatedPillarBlock> PETRAWOOD_WOOD = registerBlock("petrawood_wood", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.CRIMSON_HYPHAE).strength(2.4F).sound(SoundType.WOOD).noOcclusion()), null);
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_PETRAWOOD_LOG = registerBlock("stripped_petrawood_log", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(BPBlocks.PETRAWOOD_LOG.get()).strength(2.4F).sound(SoundType.WOOD).noOcclusion()), null);
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_PETRAWOOD_WOOD = registerBlock("stripped_petrawood_wood", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(BPBlocks.PETRAWOOD_WOOD.get()).strength(2.4F).sound(SoundType.WOOD).noOcclusion()), null);
    public static final RegistryObject<Block> PETRAWOOD_PLANKS = registerBlock("petrawood_planks", () -> new Block(AbstractBlock.Properties.copy(Blocks.CRIMSON_PLANKS).strength(2.4F).sound(SoundType.WOOD).noOcclusion()), null);
    public static final RegistryObject<LeavesBlock> PETRAWOOD_LEAVES = registerBlock("petrawood_leaves", () -> new LeavesBlock(AbstractBlock.Properties.copy(Blocks.OAK_LEAVES).strength(2.4F).sound(SoundType.GRASS).noOcclusion()), null);
    public static final RegistryObject<SaplingBlock> PETRAWOOD_SAPLING = registerBlock("petrawood_sapling", () -> new SaplingBlock(new OakTree(), AbstractBlock.Properties.of(Material.PLANT).strength(2.4F).sound(SoundType.GRASS).noOcclusion()), null);

    public static final RegistryObject<FenceBlock> PETRAWOOD_FENCE = registerBlock("petrawood_fence", () -> new FenceBlock(AbstractBlock.Properties.copy(BPBlocks.PETRAWOOD_PLANKS.get())), null);
    public static final RegistryObject<FenceGateBlock> PETRAWOOD_FENCE_GATE = registerBlock("petrawood_fence_gate", () -> new FenceGateBlock(AbstractBlock.Properties.copy(BPBlocks.PETRAWOOD_PLANKS.get())), null);
    public static final RegistryObject<SlabBlock> PETRAWOOD_SLAB = registerBlock("petrawood_slab", () -> new SlabBlock(AbstractBlock.Properties.copy(BPBlocks.PETRAWOOD_PLANKS.get())), null);
    public static final RegistryObject<PressurePlateBlock> PETRAWOOD_PRESSURE_PLATE = registerBlock("petrawood_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, AbstractBlock.Properties.copy(BPBlocks.PETRAWOOD_PLANKS.get())), null);
    public static final RegistryObject<StairsBlock> PETRAWOOD_STAIRS = registerBlock("petrawood_stairs", () -> new StairsBlock(BPBlocks.PETRAWOOD_PLANKS.get().defaultBlockState(), AbstractBlock.Properties.copy(BPBlocks.PETRAWOOD_PLANKS.get())), null);
    public static final RegistryObject<WoodButtonBlock> PETRAWOOD_BUTTON = registerBlock("petrawood_button", () -> new WoodButtonBlock(AbstractBlock.Properties.copy(BPBlocks.PETRAWOOD_PLANKS.get())), null);
    public static final RegistryObject<StandingSignBlock> PETRAWOOD_SIGN = registerBlock("petrawood_sign", () -> new StandingSignBlock(AbstractBlock.Properties.copy(BPBlocks.PETRAWOOD_PLANKS.get()), BPWoodTypes.PETRAWOOD), null);
    public static final RegistryObject<WallSignBlock> PETRAWOOD_WALL_SIGN = registerBlock("petrawood_wall_sign", () -> new WallSignBlock(AbstractBlock.Properties.copy(BPBlocks.PETRAWOOD_PLANKS.get()), BPWoodTypes.PETRAWOOD), null);
    public static final RegistryObject<DoorBlock> PETRAWOOD_DOOR = registerBlock("petrawood_door", () -> new DoorBlock(AbstractBlock.Properties.copy(BPBlocks.PETRAWOOD_PLANKS.get())), null);
    public static final RegistryObject<TrapDoorBlock> PETRAWOOD_TRAPDOOR = registerBlock("petrawood_trapdoor", () -> new TrapDoorBlock(AbstractBlock.Properties.copy(BPBlocks.PETRAWOOD_PLANKS.get())), null);
    */

    //=================================================================================
    //                       REGULAR BLOCK CONSTRUCTORS
    //=================================================================================
    public static <B extends Block> RegistryObject<B> registerBlock(String name, Supplier<? extends B> supplier, ItemGroup itemGroup) {
        return registerBlock(name, supplier, itemGroup, true);
    }

    public static <B extends Block> RegistryObject<B> registerBlock(String name, Supplier<? extends B> supplier, ItemGroup itemGroup, boolean generateItem) {
        return registerBlock(name, supplier, itemGroup, 64, generateItem);
    }

    public static <B extends Block> RegistryObject<B> registerBlock(String name, Supplier<? extends B> supplier, ItemGroup itemGroup, int stackSize, boolean generateItem) {
        RegistryObject<B> block = BPBlocks.BLOCKS.register(name, supplier);
        if (generateItem)
            BLOCK_ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(itemGroup).stacksTo(stackSize)));
        return block;
    }

    //=================================================================================
    //                      FIRE RESISTANT BLOCK CONSTRUCTORS
    //=================================================================================
    public static <B extends Block> RegistryObject<B> registerFireResBlock(String name, Supplier<? extends B> supplier, ItemGroup itemGroup) {
        return registerFireResBlock(name, supplier, itemGroup, true);
    }

    public static <B extends Block> RegistryObject<B> registerFireResBlock(String name, Supplier<? extends B> supplier, ItemGroup itemGroup, boolean generateItem) {
        return registerFireResBlock(name, supplier, itemGroup, 64, generateItem);
    }

    public static <B extends Block> RegistryObject<B> registerFireResBlock(String name, Supplier<? extends B> supplier, ItemGroup itemGroup, int stackSize, boolean generateItem) {
        RegistryObject<B> block = BPBlocks.BLOCKS.register(name, supplier);
        if (generateItem)
            BLOCK_ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(itemGroup).stacksTo(stackSize).fireResistant()));
        return block;
    }
}