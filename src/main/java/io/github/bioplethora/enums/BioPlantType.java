package io.github.bioplethora.enums;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public enum BioPlantType {

    ALL(ImmutableList.of(Blocks.NETHERRACK, Blocks.WARPED_NYLIUM, Blocks.CRIMSON_NYLIUM, Blocks.BASALT, Blocks.SOUL_SAND, Blocks.SOUL_SOIL)),
    SOUL_SAND_VALLEY(ImmutableList.of(Blocks.SOUL_SAND, Blocks.SOUL_SOIL)),
    WARPED_FOREST(ImmutableList.of(Blocks.NETHERRACK, Blocks.WARPED_NYLIUM, Blocks.WARPED_WART_BLOCK))
    ;

    private final ImmutableList<Block> whitelist;

    BioPlantType(ImmutableList<Block> whitelist) {
        this.whitelist = whitelist;
    }

    public ImmutableList<Block> getWhitelist() {
        return whitelist;
    }
}
