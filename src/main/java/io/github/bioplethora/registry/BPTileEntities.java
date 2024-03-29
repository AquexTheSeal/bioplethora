package io.github.bioplethora.registry;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.blocks.tile_entities.BPSignTileEntity;
import io.github.bioplethora.blocks.tile_entities.FleignariteSplotchTileEntity;
import io.github.bioplethora.blocks.tile_entities.ReinforcingTableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BPTileEntities {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Bioplethora.MOD_ID);

    public static final RegistryObject<TileEntityType<BPSignTileEntity>> BP_SIGN = TILE_ENTITIES.register("bp_sign",
            () -> TileEntityType.Builder.of(BPSignTileEntity::new,
                    BPBlocks.ENIVILE_SIGN.get(), BPBlocks.ENIVILE_WALL_SIGN.get(),
                    BPBlocks.CAERULWOOD_SIGN.get(), BPBlocks.CAERULWOOD_WALL_SIGN.get()
            ).build(null));

    public static final RegistryObject<TileEntityType<ReinforcingTableTileEntity>> REINFORCING_TABLE = TILE_ENTITIES.register("reinforcing_block",
            () -> TileEntityType.Builder.of(ReinforcingTableTileEntity::new, BPBlocks.REINFORCING_TABLE.get()).build(null));

    public static final RegistryObject<TileEntityType<FleignariteSplotchTileEntity>> FLEIGNARITE_SPLOTCH = TILE_ENTITIES.register("fleignarite_splotch",
            () -> TileEntityType.Builder.of(FleignariteSplotchTileEntity::new, BPBlocks.FLEIGNARITE_SPLOTCH.get()).build(null));

    public static final RegistryObject<TileEntityType<FleignariteSplotchTileEntity>> ALPHANUM_NUCLEUS = TILE_ENTITIES.register("alphanum_nucleus",
            () -> TileEntityType.Builder.of(FleignariteSplotchTileEntity::new, BPBlocks.ALPHANUM_NUCLEUS.get()).build(null));
}
