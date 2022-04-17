package io.github.bioplethora.registry.worldgen;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.world.blockplacer.LavaEdgeBlockPlacer;
import io.github.bioplethora.world.blockplacer.MinishroomBlockPlacer;
import net.minecraft.world.gen.blockplacer.BlockPlacerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BPBlockPlacers {
    public static final DeferredRegister<BlockPlacerType<?>> BLOCK_PLACERS = DeferredRegister.create(ForgeRegistries.BLOCK_PLACER_TYPES, Bioplethora.MOD_ID);

    public static final RegistryObject<BlockPlacerType<?>> LAVA_EDGE_PLACER = BLOCK_PLACERS.register("lava_edge_placer", () -> new BlockPlacerType<>(LavaEdgeBlockPlacer.CODEC));
    public static final RegistryObject<BlockPlacerType<?>> MINISHROOM_PLACER = BLOCK_PLACERS.register("minishroom_placer", () -> new BlockPlacerType<>(MinishroomBlockPlacer.CODEC));

}
