package io.github.bioplethora.registry;

import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.ComposterBlock;
import net.minecraft.block.FireBlock;
import net.minecraft.item.AxeItem;
import net.minecraft.util.IItemProvider;

public class BPExtras {

    /**
     * Note:
     * - Petrawood is NOT flammable.
     */
    public static void addExtras() {
        // Flammables
        addFlammableBlock(BPBlocks.FLEIGNARITE_REMAINS.get(), 5, 20);
        addFlammableBlock(BPBlocks.FLEIGNARITE_SPLOTCH.get(), 5, 20);
        addFlammableBlock(BPBlocks.FLEIGNARITE_VINES.get(), 5, 20);
        addFlammableBlock(BPBlocks.FLEIGNARITE_VINES_PLANT.get(), 5, 20);

        // Compostables
        addCompostableBlock(BPBlocks.FLEIGNARITE_VINES.get(), 0.4F);

        addCompostableBlock(BPBlocks.ENIVILE_LEAVES_PINK.get(), 0.3F);
        addCompostableBlock(BPBlocks.ENIVILE_LEAVES_RED.get(), 0.3F);
        addCompostableBlock(BPBlocks.CAERULWOOD_LEAVES.get(), 0.45F);
        //addCompostableBlock(BPBlocks.PETRAWOOD_LEAVES.get(), 0.3F);

        addCompostableBlock(BPBlocks.SOUL_SPROUTS.get(), 0.45F);
        addCompostableBlock(BPBlocks.SOUL_TALL_GRASS.get(), 0.6F);

        addCompostableBlock(BPBlocks.SOUL_MINISHROOM.get(), 0.3F);
        addCompostableBlock(BPBlocks.SOUL_BIGSHROOM.get(), 0.75F);

        // Stripables

        addStripableBlock(BPBlocks.ENIVILE_LOG.get(), BPBlocks.STRIPPED_ENIVILE_LOG.get());
        addStripableBlock(BPBlocks.ENIVILE_WOOD.get(), BPBlocks.STRIPPED_ENIVILE_WOOD.get());
        
        addStripableBlock(BPBlocks.CAERULWOOD_LOG.get(), BPBlocks.STRIPPED_CAERULWOOD_LOG.get());
        addStripableBlock(BPBlocks.CAERULWOOD_WOOD.get(), BPBlocks.STRIPPED_CAERULWOOD_WOOD.get());

        //addStripableBlock(BPBlocks.PETRAWOOD_LOG.get(), BPBlocks.STRIPPED_PETRAWOOD_LOG.get());
        //addStripableBlock(BPBlocks.PETRAWOOD_WOOD.get(), BPBlocks.STRIPPED_PETRAWOOD_WOOD.get());
    }

    public static void addFlammableBlock(Block block, int encouragement, int flammability) {
        FireBlock fireblock = (FireBlock) Blocks.FIRE;
        fireblock.setFlammable(block, encouragement, flammability);
    }

    public static void addCompostableBlock(IItemProvider item, float chance) {
        ComposterBlock.COMPOSTABLES.put(item.asItem(), chance);
    }

    public static void addStripableBlock(Block beforeResult, Block afterResult) {
        AxeItem.STRIPABLES = Maps.newHashMap(AxeItem.STRIPABLES);
        AxeItem.STRIPABLES.put(beforeResult, afterResult);
    }
}
