package io.github.bioplethora.blocks.specific;

import io.github.bioplethora.registry.BPBlocks;
import net.minecraft.block.*;

public class EnredeKelpTopBlock extends KelpTopBlock {

    public EnredeKelpTopBlock(Properties properties) {
        super(properties);
    }

    protected Block getBodyBlock() {
        return BPBlocks.ENREDE_KELP_PLANT.get();
    }
}
