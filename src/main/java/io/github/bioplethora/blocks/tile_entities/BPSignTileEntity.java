package io.github.bioplethora.blocks.tile_entities;

import io.github.bioplethora.registry.BPTileEntities;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.TileEntityType;

public class BPSignTileEntity extends SignTileEntity {

    public BPSignTileEntity() {
        super();
    }

    @Override
    public TileEntityType<?> getType() {
        return BPTileEntities.BP_SIGN.get();
    }
}
