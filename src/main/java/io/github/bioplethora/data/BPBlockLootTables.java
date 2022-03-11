package io.github.bioplethora.data;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.registry.BioplethoraBlocks;
import net.minecraft.block.Block;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;
import java.util.stream.Collectors;

public class BPBlockLootTables extends BlockLootTables {

    @Override
    protected void addTables() {
        dropSelf(BioplethoraBlocks.NANDBRI_SCALE_BLOCK.get());
        dropSelf(BioplethoraBlocks.BELLOPHITE_BLOCK.get());
        dropSelf(BioplethoraBlocks.BELLOPHITE_CORE_BLOCK.get());

        dropSelf(BioplethoraBlocks.MIRESTONE.get());

        add(BioplethoraBlocks.GREEN_GRYLYNEN_CRYSTAL_BLOCK.get(), (sTouch)
                -> createSingleItemTableWithSilkTouch(sTouch, BioplethoraBlocks.GREEN_GRYLYNEN_CRYSTAL_BLOCK.get()));
        add(BioplethoraBlocks.YELLOW_GRYLYNEN_CRYSTAL_BLOCK.get(), (sTouch)
                -> createSingleItemTableWithSilkTouch(sTouch, BioplethoraBlocks.YELLOW_GRYLYNEN_CRYSTAL_BLOCK.get()));
        add(BioplethoraBlocks.RED_GRYLYNEN_CRYSTAL_BLOCK.get(), (sTouch)
                -> createSingleItemTableWithSilkTouch(sTouch, BioplethoraBlocks.RED_GRYLYNEN_CRYSTAL_BLOCK.get()));

        dropSelf(BioplethoraBlocks.REINFORCING_TABLE.get());

        // Alphanum Stone Set
        dropSelf(BioplethoraBlocks.ALPHANUM.get());
        dropSelf(BioplethoraBlocks.ALPHANUM_BRICKS.get());
        dropSelf(BioplethoraBlocks.POLISHED_ALPHANUM.get());
        dropSelf(BioplethoraBlocks.ALPHANUM_PILLAR.get());
        dropSelf(BioplethoraBlocks.ALPHANUM_NUCLEUS.get());

        dropSelf(BioplethoraBlocks.ALPHANUM_STAIRS.get());
        dropSelf(BioplethoraBlocks.ALPHANUM_STAIRS_BRICKS.get());
        dropSelf(BioplethoraBlocks.POLISHED_ALPHANUM_STAIRS.get());
        dropSelf(BioplethoraBlocks.ALPHANUM_WALL.get());
        dropSelf(BioplethoraBlocks.ALPHANUM_WALL_BRICKS.get());
        dropSelf(BioplethoraBlocks.POLISHED_ALPHANUM_WALL.get());
        dropSelf(BioplethoraBlocks.ALPHANUM_SLAB.get());
        dropSelf(BioplethoraBlocks.ALPHANUM_SLAB_BRICKS.get());
        dropSelf(BioplethoraBlocks.POLISHED_ALPHANUM_SLAB.get());

        // Petrawood woodset
        dropSelf(BioplethoraBlocks.PETRAWOOD_LOG.get());
        dropSelf(BioplethoraBlocks.PETRAWOOD_WOOD.get());
        dropSelf(BioplethoraBlocks.STRIPPED_PETRAWOOD_LOG.get());
        dropSelf(BioplethoraBlocks.STRIPPED_PETRAWOOD_WOOD.get());
        dropSelf(BioplethoraBlocks.PETRAWOOD_PLANKS.get());
        add(BioplethoraBlocks.PETRAWOOD_LEAVES.get(), createShearsOnlyDrop(BioplethoraBlocks.PETRAWOOD_LEAVES.get()));

        dropSelf(BioplethoraBlocks.PETRAWOOD_FENCE.get());
        dropSelf(BioplethoraBlocks.PETRAWOOD_FENCE_GATE.get());
        dropSelf(BioplethoraBlocks.PETRAWOOD_SLAB.get());
        dropSelf(BioplethoraBlocks.PETRAWOOD_PRESSURE_PLATE.get());
        dropSelf(BioplethoraBlocks.PETRAWOOD_STAIRS.get());
        dropSelf(BioplethoraBlocks.PETRAWOOD_BUTTON.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ForgeRegistries.BLOCKS.getValues().stream().filter(b -> Objects.requireNonNull(b.getRegistryName()).getNamespace().equals(Bioplethora.MOD_ID)).collect(Collectors.toList());
    }
}
