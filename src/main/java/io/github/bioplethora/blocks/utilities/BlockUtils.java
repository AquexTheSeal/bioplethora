package io.github.bioplethora.blocks.utilities;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class BlockUtils {

    public static void knockUpRandomNearbyBlocks(World world, double yDelta, BlockPos point, int radiusX, int radiusY, int radiusZ, boolean sendParticles, boolean randomYDelta) {

        for (int radY = point.getY(); radY <= point.getY() + radiusY; radY++) {
            for (int radX = point.getX() - radiusX; radX <= point.getX() + radiusX; radX++) {
                for (int radZ = point.getZ() - radiusZ; radZ <= point.getZ() + radiusZ; radZ++) {

                    BlockPos pos = new BlockPos(radX, radY, radZ);
                    BlockState blockState = world.getBlockState(pos);
                    Block block = blockState.getBlock();

                    if ((blockState.getMaterial().isSolid()) && (blockState.getBlock() != Blocks.BEDROCK)) {
                        if (Math.random() <= 0.3) {
                            FallingBlockEntity blockEntity = new FallingBlockEntity(world, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, blockState);
                            if (randomYDelta) {
                                blockEntity.setDeltaMovement(blockEntity.getDeltaMovement().add(0, yDelta + (Math.random() / 4), 0));
                            } else {
                                blockEntity.setDeltaMovement(blockEntity.getDeltaMovement().add(0, yDelta, 0));
                            }
                            world.addFreshEntity(blockEntity);

                            if (sendParticles) {
                                if (world instanceof ServerWorld) {
                                    ((ServerWorld) world).sendParticles(new BlockParticleData(ParticleTypes.BLOCK, blockState), pos.getX(), pos.getY() + 1, pos.getZ(),
                                            5, 0.6, 0.8, 0.6, 0.1);
                                }
                            }
                        }
                    }
                }
            }
        }

        BlockState pointstate = world.getBlockState(point);
        world.playSound(null, point, pointstate.getSoundType().getBreakSound(), SoundCategory.PLAYERS, 1.0F, 1.0F);
    }

    public static void destroyNearbySpecificBlocks(World world, BlockPos point, int radiusX, int radiusY, int radiusZ, boolean shouldDrop, Block... specifiedBlocks) {

        for (int radY = point.getY() - radiusX; radY <= point.getY() + radiusY; radY++) {
            for (int radX = point.getX() - radiusX; radX <= point.getX() + radiusX; radX++) {
                for (int radZ = point.getZ() - radiusZ; radZ <= point.getZ() + radiusZ; radZ++) {

                    BlockPos pos = new BlockPos(radX, radY, radZ);
                    BlockState blockState = world.getBlockState(pos);
                    Block block = blockState.getBlock();

                    for (Block targetBlocks : specifiedBlocks) {
                        if (world.getBlockState(pos).getBlock() == targetBlocks) {
                            world.destroyBlock(pos, shouldDrop);
                        }
                    }
                }
            }
        }
    }

    public static void destroyAllNearbyBlocks(World world, BlockPos point, int radiusX, int radiusY, int radiusZ, boolean shouldDrop) {

        for (int radY = point.getY() - radiusX; radY <= point.getY() + radiusY; radY++) {
            for (int radX = point.getX() - radiusX; radX <= point.getX() + radiusX; radX++) {
                for (int radZ = point.getZ() - radiusZ; radZ <= point.getZ() + radiusZ; radZ++) {

                    BlockPos pos = new BlockPos(radX, radY, radZ);
                    BlockState blockState = world.getBlockState(pos);
                    Block block = blockState.getBlock();

                    world.destroyBlock(pos, shouldDrop);
                }
            }
        }
    }

    private BlockUtils(){}
}
