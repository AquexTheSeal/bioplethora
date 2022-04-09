package io.github.bioplethora.data;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.registry.BPBlocks;
import io.github.bioplethora.registry.BPItems;
import net.minecraft.block.Block;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;
import java.util.stream.Collectors;

public class BPBlockLootTables extends BlockLootTables {

    @Override
    protected void addTables() {
        dropSelf(BPBlocks.NANDBRI_SCALE_BLOCK.get());
        dropSelf(BPBlocks.BELLOPHITE_BLOCK.get());
        dropSelf(BPBlocks.BELLOPHITE_CORE_BLOCK.get());

        dropSelf(BPBlocks.MIRESTONE.get());

        add(BPBlocks.GREEN_GRYLYNEN_CRYSTAL_BLOCK.get(), (sTouch)
                -> createSingleItemTableWithSilkTouch(sTouch, BPBlocks.GREEN_GRYLYNEN_CRYSTAL_BLOCK.get()));
        add(BPBlocks.YELLOW_GRYLYNEN_CRYSTAL_BLOCK.get(), (sTouch)
                -> createSingleItemTableWithSilkTouch(sTouch, BPBlocks.YELLOW_GRYLYNEN_CRYSTAL_BLOCK.get()));
        add(BPBlocks.RED_GRYLYNEN_CRYSTAL_BLOCK.get(), (sTouch)
                -> createSingleItemTableWithSilkTouch(sTouch, BPBlocks.RED_GRYLYNEN_CRYSTAL_BLOCK.get()));

        dropSelf(BPBlocks.REINFORCING_TABLE.get());

        dropOther(BPBlocks.FLEIGNARITE_REMAINS.get(), BPItems.FLEIGNARITE_SCALES.get());
        dropOther(BPBlocks.FLEIGNARITE_SPLOTCH.get(), BPItems.FLEIGNARITE_SCALES.get());
        dropOther(BPBlocks.FLEIGNARITE_VINES.get(), BPItems.FLEIGNARITE_SCALES.get());
        dropOther(BPBlocks.FLEIGNARITE_VINES_PLANT.get(), BPItems.FLEIGNARITE_SCALES.get());

        // Nether Plants
        dropSelf(BPBlocks.BASALT_SPELEOTHERM.get());
        dropOther(BPBlocks.BASALT_SPELEOTHERM_PLANT.get(), BPBlocks.BASALT_SPELEOTHERM.get());
        dropOther(BPBlocks.FIERY_BASALT_SPELEOTHERM.get(), BPBlocks.BASALT_SPELEOTHERM.get());

        // Alphanum Stone Set
        dropSelf(BPBlocks.ALPHANUM.get());
        dropSelf(BPBlocks.ALPHANUM_BRICKS.get());
        dropSelf(BPBlocks.POLISHED_ALPHANUM.get());
        dropSelf(BPBlocks.ALPHANUM_PILLAR.get());
        dropSelf(BPBlocks.ALPHANUM_NUCLEUS.get());

        dropSelf(BPBlocks.ALPHANUM_STAIRS.get());
        dropSelf(BPBlocks.ALPHANUM_STAIRS_BRICKS.get());
        dropSelf(BPBlocks.POLISHED_ALPHANUM_STAIRS.get());
        dropSelf(BPBlocks.ALPHANUM_WALL.get());
        dropSelf(BPBlocks.ALPHANUM_WALL_BRICKS.get());
        dropSelf(BPBlocks.POLISHED_ALPHANUM_WALL.get());
        dropSelf(BPBlocks.ALPHANUM_SLAB.get());
        dropSelf(BPBlocks.ALPHANUM_SLAB_BRICKS.get());
        dropSelf(BPBlocks.POLISHED_ALPHANUM_SLAB.get());

        // Petrawood woodset
        dropSelf(BPBlocks.PETRAWOOD_LOG.get());
        dropSelf(BPBlocks.PETRAWOOD_WOOD.get());
        dropSelf(BPBlocks.STRIPPED_PETRAWOOD_LOG.get());
        dropSelf(BPBlocks.STRIPPED_PETRAWOOD_WOOD.get());
        dropSelf(BPBlocks.PETRAWOOD_PLANKS.get());
        add(BPBlocks.PETRAWOOD_LEAVES.get(), createShearsOnlyDrop(BPBlocks.PETRAWOOD_LEAVES.get()));
        //dropSelf(BPBlocks.PETRAWOOD_SAPLING.get());

        dropSelf(BPBlocks.PETRAWOOD_FENCE.get());
        dropSelf(BPBlocks.PETRAWOOD_FENCE_GATE.get());
        dropSelf(BPBlocks.PETRAWOOD_SLAB.get());
        dropSelf(BPBlocks.PETRAWOOD_PRESSURE_PLATE.get());
        dropSelf(BPBlocks.PETRAWOOD_STAIRS.get());
        dropSelf(BPBlocks.PETRAWOOD_BUTTON.get());
        //dropSelf(BPBlocks.PETRAWOOD_SIGN.get());
        //dropSelf(BPBlocks.PETRAWOOD_WALL_SIGN.get());
        //dropSelf(BPBlocks.PETRAWOOD_DOOR.get());
        //dropSelf(BPBlocks.PETRAWOOD_TRAPDOOR.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ForgeRegistries.BLOCKS.getValues().stream().filter(b -> Objects.requireNonNull(b.getRegistryName()).getNamespace().equals(Bioplethora.MOD_ID)).collect(Collectors.toList());
    }
}
