package io.github.bioplethora.registry;

import io.github.bioplethora.Bioplethora;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BioplethoraBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Bioplethora.MOD_ID);

    public static final RegistryObject<Block> NANDBRI_SCALE_BLOCK = BLOCKS.register("nandbri_scale_block", () -> new Block(AbstractBlock.Properties.of(Material.WOOL, MaterialColor.COLOR_LIGHT_GRAY).strength(0.8F, 0.8F).harvestTool(ToolType.AXE).sound(SoundType.BONE_BLOCK)));
}
