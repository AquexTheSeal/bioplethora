package io.github.bioplethora.data;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.registry.BioplethoraBlocks;
import io.github.bioplethora.registry.BioplethoraTags;
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

        tag(BioplethoraTags.Blocks.GRYLYNEN_UNBREAKABLE)
                .add(BioplethoraBlocks.BELLOPHITE_BLOCK.get())
                .add(BioplethoraBlocks.BELLOPHITE_CORE_BLOCK.get())
                .add(Blocks.BEACON).add(Blocks.OBSIDIAN).add(Blocks.CRYING_OBSIDIAN)
                .addTag(BlockTags.WITHER_IMMUNE)
        ;

        tag(BioplethoraTags.Blocks.WOODEN_GRYLYNEN_SPAWNABLE)
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

        tag(BioplethoraTags.Blocks.STONE_GRYLYNEN_SPAWNABLE)
                .addTag(BlockTags.BASE_STONE_OVERWORLD)
                .addTag(BlockTags.STONE_PRESSURE_PLATES)
                .addTag(BlockTags.STONE_BRICKS)
        ;

        tag(BioplethoraTags.Blocks.GOLDEN_GRYLYNEN_SPAWNABLE)
                .add(Blocks.GOLD_ORE)
                .add(Blocks.NETHER_GOLD_ORE)
                .add(Blocks.GOLD_BLOCK)
                .add(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE)
        ;

        tag(BioplethoraTags.Blocks.IRON_GRYLYNEN_SPAWNABLE)
                .add(Blocks.IRON_ORE)
                .add(Blocks.IRON_BLOCK)
                .add(Blocks.IRON_DOOR)
                .add(Blocks.IRON_BARS)
                .add(Blocks.IRON_TRAPDOOR)
                .add(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE)
        ;

        tag(BioplethoraTags.Blocks.DIAMOND_GRYLYNEN_SPAWNABLE)
                .add(Blocks.DIAMOND_ORE)
                .add(Blocks.DIAMOND_BLOCK)
        ;

        tag(BioplethoraTags.Blocks.NETHERITE_GRYLYNEN_SPAWNABLE)
                .add(Blocks.ANCIENT_DEBRIS)
                .add(Blocks.NETHERITE_BLOCK)
        ;
    }

    public void forVanillaTags() {
        tag(BlockTags.LOGS)
                .add(BioplethoraBlocks.PETRAWOOD_LOG.get())
                .add(BioplethoraBlocks.PETRAWOOD_WOOD.get())
                .add(BioplethoraBlocks.STRIPPED_PETRAWOOD_LOG.get())
                .add(BioplethoraBlocks.STRIPPED_PETRAWOOD_WOOD.get())
        ;
        tag(BlockTags.PLANKS)
                .add(BioplethoraBlocks.PETRAWOOD_PLANKS.get())
        ;
        tag(BlockTags.WOODEN_PRESSURE_PLATES)
                .add(BioplethoraBlocks.PETRAWOOD_PRESSURE_PLATE.get())
        ;
        tag(BlockTags.WOODEN_BUTTONS)
                .add(BioplethoraBlocks.PETRAWOOD_BUTTON.get())
        ;
        tag(BlockTags.WOODEN_STAIRS)
                .add(BioplethoraBlocks.PETRAWOOD_STAIRS.get())
        ;
        tag(BlockTags.WOODEN_SLABS)
                .add(BioplethoraBlocks.PETRAWOOD_SLAB.get())
        ;
        tag(BlockTags.WOODEN_FENCES)
                .add(BioplethoraBlocks.PETRAWOOD_FENCE.get())
        ;
        tag(BlockTags.FENCE_GATES)
                .add(BioplethoraBlocks.PETRAWOOD_FENCE_GATE.get())
        ;
    }
}
