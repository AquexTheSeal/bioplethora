package io.github.bioplethora.blocks;

import io.github.bioplethora.registry.BPBlocks;
import io.github.bioplethora.registry.BPParticles;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BPLeavesBlock extends LeavesBlock {

    public BPLeavesBlock( Properties properties) {
        super(properties);
    }

    public IParticleData getLeafParticle() {
        if (this == BPBlocks.CAERULWOOD_LEAVES.get()) {
            return BPParticles.CAERULWOOD_LEAF.get();
        } else {
            throw new IllegalStateException("Invalid leaf block, make sure to add " + this.getRegistryName().getPath() + " on the getLeafParticle() method.");
        }
    }

    @Override
    public void animateTick(BlockState pState, World pLevel, BlockPos pPos, Random pRandom) {
        super.animateTick(pState, pLevel, pPos, pRandom);

        if (pRandom.nextInt(15) == 1) {
            if (pLevel.getBlockState(pPos.below()).isAir()) {
                double speedX = pRandom.nextGaussian() * 0.02D;
                double speedY = pRandom.nextGaussian() * 0.03D;
                double speedZ = pRandom.nextGaussian() * 0.02D;
                pLevel.addParticle(getLeafParticle(), pPos.getX(), pPos.getY() - 1, pPos.getZ(), speedX, speedY, speedZ);
            }
        }
    }
}
