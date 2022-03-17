package io.github.bioplethora.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.RandomPatchFeature;

import java.util.Random;

public class CavePatchFeature extends RandomPatchFeature {

    public CavePatchFeature(Codec<BlockClusterFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(ISeedReader reader, ChunkGenerator chunkGenerator, Random random, BlockPos pos, BlockClusterFeatureConfig clusterFeatureConfig) {
        BlockState blockstate = clusterFeatureConfig.stateProvider.getState(random, pos);
        BlockPos blockpos;
        if (clusterFeatureConfig.project) {
            blockpos = reader.getHeightmapPos(Heightmap.Type.WORLD_SURFACE_WG, pos);
        } else {
            blockpos = pos;
        }
        int i = 0;
        BlockPos.Mutable mutable = new BlockPos.Mutable();

        for(int j = 0; j < clusterFeatureConfig.tries; ++j) {
            mutable.setWithOffset(blockpos, random.nextInt(clusterFeatureConfig.xspread + 1) - random.nextInt(clusterFeatureConfig.xspread + 1), random.nextInt(clusterFeatureConfig.yspread + 1) - random.nextInt(clusterFeatureConfig.yspread + 1), random.nextInt(clusterFeatureConfig.zspread + 1) - random.nextInt(clusterFeatureConfig.zspread + 1));
            BlockPos blockpos1 = mutable.below();
            BlockState blockstate1 = reader.getBlockState(blockpos1);
            if ((reader.isEmptyBlock(mutable) || clusterFeatureConfig.canReplace && reader.getBlockState(mutable).getMaterial().isReplaceable()) && blockstate.canSurvive(reader, mutable) && (clusterFeatureConfig.whitelist.isEmpty() || clusterFeatureConfig.whitelist.contains(blockstate1.getBlock())) && !clusterFeatureConfig.blacklist.contains(blockstate1) && (!clusterFeatureConfig.needWater || reader.getFluidState(blockpos1.west()).is(FluidTags.WATER) || reader.getFluidState(blockpos1.east()).is(FluidTags.WATER) || reader.getFluidState(blockpos1.north()).is(FluidTags.WATER) || reader.getFluidState(blockpos1.south()).is(FluidTags.WATER))) {
                if (!reader.canSeeSky(blockpos1)) {
                    clusterFeatureConfig.blockPlacer.place(reader, mutable, blockstate, random);
                    ++i;
                }
            }
        }

        return i > 0;
    }
}
