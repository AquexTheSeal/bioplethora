package io.github.bioplethora.world;

import com.google.common.collect.ImmutableList;
import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.registry.BPConfiguredFeatures;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = Bioplethora.MOD_ID)
public class BPFeatureGeneration {

    @SubscribeEvent
    public static void generateFeatures(final BiomeLoadingEvent event) {
        RegistryKey<Biome> key = RegistryKey.create(Registry.BIOME_REGISTRY, event.getName());
        Set<BiomeDictionary.Type> types = BiomeDictionary.getTypes(key);

        List<Supplier<ConfiguredFeature<?, ?>>> undergroundDeco = event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_DECORATION);
        List<Supplier<ConfiguredFeature<?, ?>>> locMod = event.getGeneration().getFeatures(GenerationStage.Decoration.LOCAL_MODIFICATIONS);

        if (types.contains(BiomeDictionary.Type.OVERWORLD)) {
            undergroundDeco.add(() -> BPConfiguredFeatures.FLEIGNARITE_REMAINS_CONFIG);
            undergroundDeco.add(() -> BPConfiguredFeatures.FLEIGNARITE_VINES_CONFIG);

            locMod.add(() -> BPConfiguredFeatures.FLEIGNARITE_REMAINS_CONFIG);
            locMod.add(() -> BPConfiguredFeatures.FLEIGNARITE_VINES_CONFIG);
        }
    }

    public static ImmutableList<Block> stoneBlocks() {
        return ImmutableList.of(Blocks.STONE, Blocks.ANDESITE, Blocks.DIORITE, Blocks.GRANITE);
    }

    public static ImmutableList<BlockState> stoneBlockstates() {
        return ImmutableList.of(Blocks.STONE.defaultBlockState(), Blocks.ANDESITE.defaultBlockState(), Blocks.DIORITE.defaultBlockState(), Blocks.GRANITE.defaultBlockState());
    }

    public static Random seedFleignarChunk(int pChunkX, int pChunkZ, long pSeed, long l) {
        return new Random(pSeed + ((long) pChunkX * pChunkX * 4987142) + (pChunkX * 5947611L) + (long) pChunkZ * pChunkZ * 4392871L + (pChunkZ * 389711L) ^ l);
    }

    public static boolean isFleignariteChunk(BlockPos pos, ISeedReader seedReader) {
        ChunkPos chunkpos = new ChunkPos(pos);
        return seedFleignarChunk(chunkpos.x, chunkpos.z, seedReader.getSeed(), 987234911L).nextInt(20) == 0;
    }
}
