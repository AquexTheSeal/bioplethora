package io.github.bioplethora.enums;

import com.google.common.collect.ImmutableList;
import io.github.bioplethora.registry.BPBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.function.Supplier;

public enum BioPlantType {

    //----------------- NETHER ----------------
    ALL_NETHER(() -> ImmutableList.of(
            Blocks.NETHERRACK, Blocks.WARPED_NYLIUM, Blocks.CRIMSON_NYLIUM, Blocks.BASALT,
            Blocks.SOUL_SAND, Blocks.SOUL_SOIL
    )),
    SOUL_SAND_VALLEY(() -> ImmutableList.of(
            Blocks.SOUL_SAND, Blocks.SOUL_SOIL
    )),
    WARPED_FOREST(() -> ImmutableList.of(
            Blocks.NETHERRACK, Blocks.WARPED_NYLIUM, Blocks.WARPED_WART_BLOCK
    )),
    CRYEANUM(() -> ImmutableList.of(
            Blocks.NETHERRACK, BPBlocks.CRYEA.get()
    )),

    //----------------- END ----------------
    END_STONE(() -> ImmutableList.of(
            Blocks.END_STONE
    )),
    CAERI(() -> ImmutableList.of(
            BPBlocks.CRYOSOIL.get(), BPBlocks.IRION.get(), BPBlocks.CYRA.get(), Blocks.END_STONE
    )),
    CHORUS(() -> ImmutableList.of(
            Blocks.END_STONE, BPBlocks.ENDURION.get()
    )),
    END_ISLANDS_ICE(() -> ImmutableList.of(
            Blocks.ICE,  Blocks.BLUE_ICE,  Blocks.PACKED_ICE,  Blocks.FROSTED_ICE
    )),
    CAERULWOOD_TREE(() -> ImmutableList.of(
            BPBlocks.CAERULWOOD_LEAVES.get(), BPBlocks.CAERULWOOD_LOG.get(), BPBlocks.CAERULWOOD_WOOD.get(), BPBlocks.STRIPPED_CAERULWOOD_LOG.get(),
            BPBlocks.CAERULWOOD_WOOD.get(), Blocks.END_STONE
    ));

    private final Supplier<ImmutableList<Block>> whitelist;

    BioPlantType(Supplier<ImmutableList<Block>> whitelist) {
        this.whitelist = whitelist;
    }

    public Supplier<ImmutableList<Block>> getWhitelist() {
        return whitelist;
    }
}
