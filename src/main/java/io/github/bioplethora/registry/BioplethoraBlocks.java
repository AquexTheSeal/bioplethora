package io.github.bioplethora.registry;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.blocks.BellophiteCoreBlock;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BioplethoraBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Bioplethora.MOD_ID);

    public static final RegistryObject<Block> NANDBRI_SCALE_BLOCK = BLOCKS.register("nandbri_scale_block", () -> new Block(AbstractBlock.Properties.of(Material.WOOL, MaterialColor.COLOR_LIGHT_GRAY).strength(0.8F, 0.8F).harvestTool(ToolType.AXE).sound(SoundType.BONE_BLOCK)));
    public static final RegistryObject<Block> BELLOPHITE_BLOCK = BLOCKS.register("bellophite_block", () -> new Block(AbstractBlock.Properties.of(Material.METAL, MaterialColor.COLOR_YELLOW).requiresCorrectToolForDrops().strength(50.0F, 1200.0F).harvestTool(ToolType.PICKAXE).sound(SoundType.NETHERITE_BLOCK)));
    public static final RegistryObject<Block> BELLOPHITE_CORE_BLOCK = BLOCKS.register("bellophite_core_block", () -> new BellophiteCoreBlock(AbstractBlock.Properties.of(Material.GLASS).strength(0.3F).sound(SoundType.GLASS).noOcclusion()));
}
