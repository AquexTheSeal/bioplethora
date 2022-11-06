package io.github.bioplethora.data;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.registry.BPBlocks;
import io.github.bioplethora.registry.BPTags;
import net.minecraft.block.Blocks;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BioBlockTagsProvider extends BlockTagsProvider {

    public BioBlockTagsProvider(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
        super(generatorIn, Bioplethora.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {

        this.forVanillaTags();
        tag(BPTags.Blocks.GROUND_END_BLOCKS)
                .add(Blocks.END_STONE)
                .add(BPBlocks.CYRA.get())
                .add(BPBlocks.CRYOSOIL.get())
                .add(BPBlocks.IRION.get())
        ;

        tag(BPTags.Blocks.CAERULWOOD_LOGS)
                .add(BPBlocks.CAERULWOOD_LOG.get())
                .add(BPBlocks.CAERULWOOD_WOOD.get())
                .add(BPBlocks.STRIPPED_CAERULWOOD_LOG.get())
                .add(BPBlocks.STRIPPED_CAERULWOOD_WOOD.get())
        ;

        tag(BPTags.Blocks.ALPHANIA)
                .add(BPBlocks.ALPHANUM.get())
                .add(BPBlocks.ALPHANUM_BRICKS.get())
                .add(BPBlocks.POLISHED_ALPHANUM.get())
                .add(BPBlocks.ALPHANUM_STAIRS.get())
                .add(BPBlocks.ALPHANUM_STAIRS_BRICKS.get())
                .add(BPBlocks.POLISHED_ALPHANUM_STAIRS.get())
                .add(BPBlocks.ALPHANUM_WALL.get())
                .add(BPBlocks.ALPHANUM_WALL_BRICKS.get())
                .add(BPBlocks.POLISHED_ALPHANUM_WALL.get())
                .add(BPBlocks.ALPHANUM_SLAB.get())
                .add(BPBlocks.ALPHANUM_SLAB_BRICKS.get())
                .add(BPBlocks.POLISHED_ALPHANUM_SLAB.get())
        ;

        tag(BPTags.Blocks.CHORUS_GROWABLE)
                .add(BPBlocks.ENDURION.get())
                .add(Blocks.END_STONE)
        ;

        tag(BPTags.Blocks.GRYLYNEN_UNBREAKABLE)
                .add(BPBlocks.BELLOPHITE_BLOCK.get())
                .add(BPBlocks.BELLOPHITE_CORE_BLOCK.get())
                .add(Blocks.BEACON).add(Blocks.OBSIDIAN).add(Blocks.CRYING_OBSIDIAN).add(Blocks.BEDROCK)
                .addTag(BlockTags.WITHER_IMMUNE)
        ;

        tag(BPTags.Blocks.WOODEN_GRYLYNEN_SPAWNABLE)
                .addTag(BlockTags.LOGS)
        ;

        tag(BPTags.Blocks.STONE_GRYLYNEN_SPAWNABLE)
                .addTag(BlockTags.BASE_STONE_OVERWORLD)
        ;

        tag(BPTags.Blocks.GOLDEN_GRYLYNEN_SPAWNABLE)
                .add(Blocks.GOLD_ORE)
                .add(Blocks.GOLD_BLOCK)
        ;

        tag(BPTags.Blocks.IRON_GRYLYNEN_SPAWNABLE)
                .add(Blocks.IRON_ORE)
        ;

        tag(BPTags.Blocks.DIAMOND_GRYLYNEN_SPAWNABLE)
                .add(Blocks.DIAMOND_ORE)
        ;

        tag(BPTags.Blocks.NETHERITE_GRYLYNEN_SPAWNABLE)
                .add(Blocks.ANCIENT_DEBRIS)
        ;
    }

    public void forVanillaTags() {
        tag(BlockTags.CLIMBABLE)
                .add(BPBlocks.SPIRIT_DANGLER.get())
                .add(BPBlocks.SPIRIT_DANGLER_PLANT.get())

                .add(BPBlocks.FLEIGNARITE_VINES.get())
                .add(BPBlocks.FLEIGNARITE_VINES_PLANT.get())

                .add(BPBlocks.THONTUS_THISTLE.get())
                .add(BPBlocks.THONTUS_THISTLE_PLANT.get())
                .add(BPBlocks.BERRIED_THONTUS_THISTLE.get())

                .add(BPBlocks.TURQUOISE_PENDENT.get())
                .add(BPBlocks.TURQUOISE_PENDENT_PLANT.get())
                .add(BPBlocks.BLOSSOMING_TURQUOISE_PENDENT.get())

                .add(BPBlocks.SOUL_ETERN.get())
                .add(BPBlocks.SOUL_ETERN_PLANT.get())
                .add(BPBlocks.FLOURISHED_SOUL_ETERN.get())
        ;

        tag(BlockTags.BASE_STONE_OVERWORLD)
                .add(BPBlocks.ALPHANUM.get())
        ;

        tag(BlockTags.WALLS)
                .add(BPBlocks.ALPHANUM_WALL.get())
                .add(BPBlocks.ALPHANUM_WALL_BRICKS.get())
                .add(BPBlocks.POLISHED_ALPHANUM_WALL.get())
        ;

        // woodset
        tag(BlockTags.LOGS)
                .add(BPBlocks.ENIVILE_LOG.get())
                .add(BPBlocks.ENIVILE_WOOD.get())
                .add(BPBlocks.STRIPPED_ENIVILE_LOG.get())
                .add(BPBlocks.STRIPPED_ENIVILE_WOOD.get())
                
                .add(BPBlocks.CAERULWOOD_LOG.get())
                .add(BPBlocks.CAERULWOOD_WOOD.get())
                .add(BPBlocks.STRIPPED_CAERULWOOD_LOG.get())
                .add(BPBlocks.STRIPPED_CAERULWOOD_WOOD.get())
        ;
        tag(BlockTags.PLANKS)
                .add(BPBlocks.ENIVILE_PLANKS.get())
                .add(BPBlocks.CAERULWOOD_PLANKS.get())
        ;
        tag(BlockTags.SAPLINGS)
                .add(BPBlocks.ENIVILE_SAPLING.get())
                .add(BPBlocks.CAERULWOOD_SAPLING.get())
        ;
        tag(BlockTags.LEAVES)
                .add(BPBlocks.ENIVILE_LEAVES_PINK.get())
                .add(BPBlocks.ENIVILE_LEAVES_RED.get())
                .add(BPBlocks.CAERULWOOD_LEAVES.get())
        ;
        tag(BlockTags.DOORS)
                .add(BPBlocks.ENIVILE_DOOR.get())
                .add(BPBlocks.CAERULWOOD_DOOR.get())
        ;
        tag(BlockTags.WOODEN_PRESSURE_PLATES)
                .add(BPBlocks.ENIVILE_PRESSURE_PLATE.get())
                .add(BPBlocks.CAERULWOOD_PRESSURE_PLATE.get())
        ;
        tag(BlockTags.WOODEN_BUTTONS)
                .add(BPBlocks.ENIVILE_BUTTON.get())
                .add(BPBlocks.CAERULWOOD_BUTTON.get())
        ;
        tag(BlockTags.WOODEN_STAIRS)
                .add(BPBlocks.ENIVILE_STAIRS.get())
                .add(BPBlocks.CAERULWOOD_STAIRS.get())
        ;
        tag(BlockTags.WOODEN_SLABS)
                .add(BPBlocks.ENIVILE_SLAB.get())
                .add(BPBlocks.CAERULWOOD_SLAB.get())
        ;
        tag(BlockTags.WOODEN_FENCES)
                .add(BPBlocks.ENIVILE_FENCE.get())
                .add(BPBlocks.CAERULWOOD_FENCE.get())
        ;
        tag(BlockTags.FENCE_GATES)
                .add(BPBlocks.ENIVILE_FENCE_GATE.get())
                .add(BPBlocks.CAERULWOOD_FENCE_GATE.get())
        ;
        
        /*
        tag(BlockTags.LOGS)
                .add(BPBlocks.PETRAWOOD_LOG.get())
                .add(BPBlocks.PETRAWOOD_WOOD.get())
                .add(BPBlocks.STRIPPED_PETRAWOOD_LOG.get())
                .add(BPBlocks.STRIPPED_PETRAWOOD_WOOD.get())
        ;
        tag(BlockTags.PLANKS)
                .add(BPBlocks.PETRAWOOD_PLANKS.get())
        ;
        tag(BlockTags.WOODEN_PRESSURE_PLATES)
                .add(BPBlocks.PETRAWOOD_PRESSURE_PLATE.get())
        ;
        tag(BlockTags.WOODEN_BUTTONS)
                .add(BPBlocks.PETRAWOOD_BUTTON.get())
        ;
        tag(BlockTags.WOODEN_STAIRS)
                .add(BPBlocks.PETRAWOOD_STAIRS.get())
        ;
        tag(BlockTags.WOODEN_SLABS)
                .add(BPBlocks.PETRAWOOD_SLAB.get())
        ;
        tag(BlockTags.WOODEN_FENCES)
                .add(BPBlocks.PETRAWOOD_FENCE.get())
        ;
        tag(BlockTags.FENCE_GATES)
                .add(BPBlocks.PETRAWOOD_FENCE_GATE.get())
        ;
         */
    }
}
