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

        tag(BPTags.Blocks.GRYLYNEN_UNBREAKABLE)
                .add(BPBlocks.BELLOPHITE_BLOCK.get())
                .add(BPBlocks.BELLOPHITE_CORE_BLOCK.get())
                .add(Blocks.BEACON).add(Blocks.OBSIDIAN).add(Blocks.CRYING_OBSIDIAN)
                .addTag(BlockTags.WITHER_IMMUNE)
        ;

        tag(BPTags.Blocks.WOODEN_GRYLYNEN_SPAWNABLE)
                .addTag(BlockTags.LOGS)
                .addTag(BlockTags.PLANKS)
                .addTag(BlockTags.WOODEN_PRESSURE_PLATES)
                .addTag(BlockTags.WOODEN_BUTTONS)
                .addTag(BlockTags.WOODEN_STAIRS)
                .addTag(BlockTags.WOODEN_SLABS)
                .addTag(BlockTags.WOODEN_FENCES)
                .addTag(BlockTags.FENCE_GATES)
                .addTag(BlockTags.WOODEN_DOORS)
                .addTag(BlockTags.WOODEN_TRAPDOORS)
                .addTag(BlockTags.SIGNS)
                .addTag(BlockTags.STANDING_SIGNS)
                .addTag(BlockTags.WALL_SIGNS)
        ;

        tag(BPTags.Blocks.STONE_GRYLYNEN_SPAWNABLE)
                .addTag(BlockTags.BASE_STONE_OVERWORLD)
                .addTag(BlockTags.STONE_PRESSURE_PLATES)
                .addTag(BlockTags.STONE_BRICKS)
        ;

        tag(BPTags.Blocks.GOLDEN_GRYLYNEN_SPAWNABLE)
                .add(Blocks.GOLD_ORE)
                .add(Blocks.NETHER_GOLD_ORE)
                .add(Blocks.GOLD_BLOCK)
                .add(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE)
        ;

        tag(BPTags.Blocks.IRON_GRYLYNEN_SPAWNABLE)
                .add(Blocks.IRON_ORE)
                .add(Blocks.IRON_BLOCK)
                .add(Blocks.IRON_DOOR)
                .add(Blocks.IRON_BARS)
                .add(Blocks.IRON_TRAPDOOR)
                .add(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE)
        ;

        tag(BPTags.Blocks.DIAMOND_GRYLYNEN_SPAWNABLE)
                .add(Blocks.DIAMOND_ORE)
                .add(Blocks.DIAMOND_BLOCK)
        ;

        tag(BPTags.Blocks.NETHERITE_GRYLYNEN_SPAWNABLE)
                .add(Blocks.ANCIENT_DEBRIS)
                .add(Blocks.NETHERITE_BLOCK)
        ;
    }

    public void forVanillaTags() {
        tag(BlockTags.CLIMBABLE)
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
    }
}
