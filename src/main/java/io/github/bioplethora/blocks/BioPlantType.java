package io.github.bioplethora.blocks;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public enum BioPlantType {

    SOUL_SAND_VALLEY(ImmutableList.of(Blocks.SOUL_SAND, Blocks.SOUL_SOIL));

    private final ImmutableList<Block> whitelist;

    BioPlantType(ImmutableList<Block> whitelist) {
        this.whitelist = whitelist;
    }

    public ImmutableList<Block> getWhitelist() {
        return whitelist;
    }
}
