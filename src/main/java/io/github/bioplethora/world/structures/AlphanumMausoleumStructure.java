package io.github.bioplethora.world.structures;

import com.mojang.serialization.Codec;
import io.github.bioplethora.Bioplethora;
import net.minecraft.block.BlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.jigsaw.JigsawManager;
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.feature.template.TemplateManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

public class AlphanumMausoleumStructure extends Structure<NoFeatureConfig> {

    public AlphanumMausoleumStructure(Codec<NoFeatureConfig> pCodec) {
        super(pCodec);
    }

    @Override
    public GenerationStage.Decoration step() {
        return GenerationStage.Decoration.SURFACE_STRUCTURES;
    }

    @Override
    protected boolean isFeatureChunk(ChunkGenerator generator, BiomeProvider provider, long seed, SharedSeedRandom seedRandom, int chunkX, int chunkZ, Biome biome, ChunkPos chunkPos, NoFeatureConfig noFeatureConfig) {
        BlockPos centerOfChunk = new BlockPos((chunkX << 4) + 16, 0, (chunkZ << 4) + 16);

        int landHeight = generator.getBaseHeight(centerOfChunk.getX(), centerOfChunk.getZ(), Heightmap.Type.WORLD_SURFACE_WG);

        IBlockReader columnOfBlocks = generator.getBaseColumn(centerOfChunk.getX(), centerOfChunk.getZ());
        BlockState topBlock = columnOfBlocks.getBlockState(centerOfChunk.above(landHeight));

        return topBlock.getFluidState().isEmpty();
    }

    @Override
    public IStartFactory<NoFeatureConfig> getStartFactory() {
        return AlphanumMausoleumStructure.Start::new;
    }

    public static class Start extends StructureStart<NoFeatureConfig> {
        public Start(Structure<NoFeatureConfig> structureIn, int chunkX, int chunkZ,
                     MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
            super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
        }

        @Override
        public void generatePieces(DynamicRegistries dynamicRegistryManager, ChunkGenerator chunkGenerator,
                                   TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn,
                                   NoFeatureConfig config) {
            int x = (chunkX << 4) + 16;
            int z = (chunkZ << 4) + 16;
            BlockPos blockpos = new BlockPos(x, 0, z);

            JigsawManager.addPieces(dynamicRegistryManager, new VillageConfig(() -> dynamicRegistryManager
                            .registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
                            .get(new ResourceLocation(Bioplethora.MOD_ID, "alphanum_mausoleum/start_pool")),
                            10), AbstractVillagePiece::new, chunkGenerator, templateManagerIn,
                    blockpos, this.pieces, this.random,false,true);

            this.pieces.forEach(piece -> piece.move(0, 1, 0));
            this.pieces.forEach(piece -> piece.getBoundingBox().y0 -= 1);

            this.calculateBoundingBox();

            LogManager.getLogger().log(Level.DEBUG, "House at " +
                    this.pieces.get(0).getBoundingBox().x0 + " " + this.pieces.get(0).getBoundingBox().y0 + " " + this.pieces.get(0).getBoundingBox().z0);
        }
    }
}
