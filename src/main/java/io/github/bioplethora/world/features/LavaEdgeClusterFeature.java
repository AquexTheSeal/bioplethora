package io.github.bioplethora.world.features;

import com.mojang.serialization.Codec;
import io.github.bioplethora.api.world.BlockUtils;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.DefaultFlowersFeature;

public class LavaEdgeClusterFeature extends DefaultFlowersFeature {

    public LavaEdgeClusterFeature(Codec<BlockClusterFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean isValid(IWorld pLevel, BlockPos pPos, BlockClusterFeatureConfig pConfig) {
        return super.isValid(pLevel, pPos, pConfig) && BlockUtils.checkNearestTaggedFluid(checkForLiquid(pPos), pLevel, FluidTags.LAVA);
    }

    public AxisAlignedBB checkForLiquid(BlockPos pPos) {
        int areaint = 14;
        double x = pPos.getX(), y = pPos.getY(), z = pPos.getZ();
        return new AxisAlignedBB(
                x - (areaint / 2d), y - (areaint / 2d), z - (areaint / 2d),
                x + (areaint / 2d), y + (areaint / 2d), z + (areaint / 2d)
        );
    }
}
