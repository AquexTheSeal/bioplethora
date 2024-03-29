package io.github.bioplethora.api.world;

import com.google.common.collect.AbstractIterator;
import io.github.bioplethora.registry.BPTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class BlockUtils {

    public static boolean checkBlockstate(IWorld world, BlockPos pos, BlockState requiredState) {
        return world.getBlockState(pos) == requiredState;
    }

    public static boolean checkBlock(IWorld world, BlockPos pos, Block requiredBlock) {
        return checkBlockstate(world, pos, requiredBlock.defaultBlockState());
    }

    public static double blockDistance(BlockPos pos1, BlockPos pos2) {
        double d0 = pos2.getX() - pos1.getX();
        double d1 = pos2.getY() - pos1.getY();
        double d2 = pos2.getZ() - pos1.getZ();
        return MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
    }

    public static boolean checkNearestTaggedFluid(AxisAlignedBB pBb, IWorldReader level, ITag<Fluid> tag) {
        int i = MathHelper.floor(pBb.minX), j = MathHelper.ceil(pBb.maxX);
        int k = MathHelper.floor(pBb.minY), l = MathHelper.ceil(pBb.maxY);
        int i1 = MathHelper.floor(pBb.minZ), j1 = MathHelper.ceil(pBb.maxZ);
        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

        for(int k1 = i; k1 < j; ++k1) {
            for(int l1 = k; l1 < l; ++l1) {
                for(int i2 = i1; i2 < j1; ++i2) {
                    BlockState blockstate = level.getBlockState(blockpos$mutable.set(k1, l1, i2));
                    if (!blockstate.getFluidState().is(tag)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public static void knockUpRandomNearbyBlocks(World world, double yDelta, BlockPos point, int radiusX, int radiusY, int radiusZ, boolean sendParticles, boolean randomYDelta) {

        for (int radY = point.getY(); radY <= point.getY() + radiusY; radY++) {
            for (int radX = point.getX() - radiusX; radX <= point.getX() + radiusX; radX++) {
                for (int radZ = point.getZ() - radiusZ; radZ <= point.getZ() + radiusZ; radZ++) {

                    BlockPos pos = new BlockPos(radX, radY, radZ);
                    BlockState blockState = world.getBlockState(pos);
                    Block block = blockState.getBlock();

                    if (!BlockTags.DRAGON_IMMUNE.contains(block) && !BPTags.Blocks.ALPHANIA.contains(block)) {
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
