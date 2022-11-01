package io.github.bioplethora.registry.worldgen;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.registry.BPBlocks;
import io.github.bioplethora.world.surfacebuilderconfigs.CarpetedSurfaceBuilderConfig;
import io.github.bioplethora.world.surfacebuilderconfigs.NoisySurfaceBuilderConfig;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class BPConfiguredSurfaceBuilders {

    public static ConfiguredSurfaceBuilder<?> ROCKY_WOODLANDS_SURFACE = register("rocky_woodlands",
            SurfaceBuilder.DEFAULT.configured(new SurfaceBuilderConfig(
                    Blocks.GRASS_BLOCK.defaultBlockState(), // Surface Block
                    BPBlocks.MIRESTONE.get().defaultBlockState(), // Underground Block
                    Blocks.STONE.defaultBlockState() // Underwater Block
            )));

    public static ConfiguredSurfaceBuilder<?> ENDURION_SURFACE = register("endurion",
            SurfaceBuilder.DEFAULT.configured(new SurfaceBuilderConfig(
                    BPBlocks.ENDURION.get().defaultBlockState(), // Surface Block
                    Blocks.END_STONE.defaultBlockState(), // Underground Block
                    Blocks.END_STONE.defaultBlockState() // Underwater Block
            )));

    public static ConfiguredSurfaceBuilder<?> CRYEANUM_SURFACE = register("cryeanum",
            BPSurfaceBuilders.CARPETED.get().configured(new CarpetedSurfaceBuilderConfig(
                    BPBlocks.CRYEA.get().defaultBlockState(), // Surface Block
                    BPBlocks.CRYEA_CARPET.get().defaultBlockState(), // Carpet Block
                    Blocks.NETHERRACK.defaultBlockState(), // Underground Block
                    Blocks.BLACKSTONE.defaultBlockState() // Underwater Block
            )));

    public static ConfiguredSurfaceBuilder<?> CAERI_SURFACE = register("caeri",
            BPSurfaceBuilders.NOISY.get().configured(new NoisySurfaceBuilderConfig(
                    BPBlocks.IRION.get().defaultBlockState(), // Surface Block
                    BPBlocks.CRYOSOIL.get().defaultBlockState(), // Surface Block Uncommon
                    BPBlocks.CRYOSOIL.get().defaultBlockState(), // Underground Block
                    BPBlocks.CRYOSOIL.get().defaultBlockState() // Underwater Block
            )));

    private static <SC extends ISurfaceBuilderConfig>ConfiguredSurfaceBuilder<SC> register(String name, ConfiguredSurfaceBuilder<SC> csb) {
        return WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_SURFACE_BUILDER, new ResourceLocation(Bioplethora.MOD_ID, name), csb);
    }
}
