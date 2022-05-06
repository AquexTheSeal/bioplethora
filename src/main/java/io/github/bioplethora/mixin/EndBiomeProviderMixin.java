package io.github.bioplethora.mixin;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.EndBiomeProvider;
import net.minecraft.world.gen.SimplexNoiseGenerator;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Mixin(EndBiomeProvider.class)
public class EndBiomeProviderMixin {

    @Shadow @Final private SimplexNoiseGenerator islandNoise;

    @Shadow @Final private Biome highlands;
    @Shadow @Final private Biome end;
    @Shadow @Final private Biome midlands;
    @Shadow @Final private Biome islands;
    @Shadow @Final private Biome barrens;

    @Shadow @Final private Registry<Biome> biomes;

    /**
     * @author Mojang Studios
     */
    @Overwrite
    public Biome getNoiseBiome(int pX, int pY, int pZ) {
        int i = pX >> 2;
        int j = pZ >> 2;
        if ((long)i * (long)i + (long)j * (long)j <= 4096L) {
            return this.end;
        } else {
            float f = EndBiomeProvider.getHeightValue(this.islandNoise, i * 2 + 1, j * 2 + 1);
            if (f > 40.0F) {
                return getRandomBiomeOverworld();
            } else if (f >= 0.0F) {
                return getRandomBiomeNether();
            } else {
                return f < -20.0F ? this.islands : this.barrens;
            }
        }
    }

    public Biome getRandomBiomeOverworld() {
        List<Biome> biomeList = new ArrayList<>();

        List<Map.Entry<RegistryKey<Biome>, Biome>> lookupForgeRegistries =
                ForgeRegistries.BIOMES.getEntries().stream()
                        .filter(biome -> (biome.getValue().getBiomeCategory() != Biome.Category.NETHER) && (biome.getValue().getBiomeCategory() != Biome.Category.THEEND))
                        .collect(Collectors.toList()
                        );

        for (Map.Entry<RegistryKey<Biome>, Biome> biome : lookupForgeRegistries) {
            biomeList.add(biome.getValue());
        }

        return biomeList.get(new Random().nextInt(biomeList.size()));
    }

    public Biome getRandomBiomeNether() {
        List<Biome> biomeList = new ArrayList<>();

        List<Map.Entry<RegistryKey<Biome>, Biome>> lookupForgeRegistries =
                ForgeRegistries.BIOMES.getEntries().stream()
                        .filter(biome -> biome.getValue().getBiomeCategory() == Biome.Category.NETHER)
                        .collect(Collectors.toList()
                        );

        for (Map.Entry<RegistryKey<Biome>, Biome> biome : lookupForgeRegistries) {
            biomeList.add(biome.getValue());
        }

        return biomeList.get(new Random().nextInt(biomeList.size()));
    }
}
