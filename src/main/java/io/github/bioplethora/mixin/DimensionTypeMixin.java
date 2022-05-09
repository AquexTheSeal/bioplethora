package io.github.bioplethora.mixin;

import io.github.bioplethora.world.biomes.provider.BPNetherBiomeProvider;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.NoiseChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DimensionType.class)
public class DimensionTypeMixin {

    @Inject(at = @At("HEAD"), method = "defaultNetherGenerator", cancellable = true)
    private static void IE_netherDimensionInfernalExpansion(Registry<Biome> registry, Registry<DimensionSettings> settings, long seed, CallbackInfoReturnable<ChunkGenerator> cir) {
        cir.setReturnValue(new NoiseChunkGenerator(new BPNetherBiomeProvider(seed, registry, 6), seed, () -> settings.getOrThrow(DimensionSettings.NETHER)));
    }
}
