package io.github.bioplethora.mixin;

import io.github.bioplethora.world.biomes.provider.helper.BPSeedHolder;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.Dimension;
import net.minecraft.world.gen.settings.DimensionGeneratorSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(DimensionGeneratorSettings.class)
public class DimensionGeneratorSettingsMixin {

    @Inject(at = @At(value = "RETURN"), method = "<init>(JZZLnet/minecraft/util/registry/SimpleRegistry;Ljava/util/Optional;)V")
    private void giveRandomBPSeed(long seed, boolean generateFeatures, boolean bonusChest, SimpleRegistry<Dimension> registry, Optional<String> s, CallbackInfo ci) {
        BPSeedHolder.setSeed(seed);
    }
}
