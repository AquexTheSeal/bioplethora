package io.github.bioplethora.registry;

import io.github.bioplethora.Bioplethora;
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

    private static <SC extends ISurfaceBuilderConfig>ConfiguredSurfaceBuilder<SC> register(String name, ConfiguredSurfaceBuilder<SC> csb) {
        return WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_SURFACE_BUILDER, new ResourceLocation(Bioplethora.MOD_ID, name), csb);
    }
}
