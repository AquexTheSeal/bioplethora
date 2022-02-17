package io.github.bioplethora.event.helper;

import io.github.bioplethora.BioplethoraConfig;
import io.github.bioplethora.entity.creatures.GrylynenEntity;
import io.github.bioplethora.registry.BioplethoraEntities;
import io.github.bioplethora.registry.BioplethoraTags;
import net.minecraft.entity.SpawnReason;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.world.BlockEvent;

public class GrylynenSpawnHelper {

    public static void onBlockBreak(BlockEvent.BreakEvent event) {

        IWorld IWorld = event.getWorld();
        if (IWorld instanceof World) {

            World world = (World) event.getWorld();
            double x = event.getPos().getX(), y = event.getPos().getY(), z = event.getPos().getZ();
            BlockPos pos = new BlockPos(x, y, z);

            if (getTaggedBlock(BioplethoraTags.Blocks.WOODEN_GRYLYNEN_SPAWNABLE.getName(), world, pos)) {
                if (Math.random() < (BioplethoraConfig.getHellMode ? 0.05 : 0.075) && BioplethoraConfig.COMMON.spawnWoodenGrylynen.get()) {
                    summonGrylynen(BioplethoraEntities.WOODEN_GRYLYNEN.get().create(world), world, pos);
                }
            }

            if (getTaggedBlock(BioplethoraTags.Blocks.STONE_GRYLYNEN_SPAWNABLE.getName(), world, pos)) {
                if (Math.random() < (BioplethoraConfig.getHellMode ? 0.05 : 0.075) && BioplethoraConfig.COMMON.spawnStoneGrylynen.get()) {
                    summonGrylynen(BioplethoraEntities.STONE_GRYLYNEN.get().create(world), world, pos);
                }
            }

            if (getTaggedBlock(BioplethoraTags.Blocks.GOLDEN_GRYLYNEN_SPAWNABLE.getName(), world, pos)) {
                if (Math.random() < (BioplethoraConfig.getHellMode ? 0.075 : 0.1) && BioplethoraConfig.COMMON.spawnGoldenGrylynen.get()) {
                    summonGrylynen(BioplethoraEntities.GOLDEN_GRYLYNEN.get().create(world), world, pos);
                }
            }

            if (getTaggedBlock(BioplethoraTags.Blocks.IRON_GRYLYNEN_SPAWNABLE.getName(), world, pos)) {
                if (Math.random() < (BioplethoraConfig.getHellMode ? 0.075 : 0.1) && BioplethoraConfig.COMMON.spawnIronGrylynen.get()) {
                    summonGrylynen(BioplethoraEntities.IRON_GRYLYNEN.get().create(world), world, pos);
                }
            }

            if (getTaggedBlock(BioplethoraTags.Blocks.DIAMOND_GRYLYNEN_SPAWNABLE.getName(), world, pos)) {
                if (Math.random() < (BioplethoraConfig.getHellMode ? 0.1 : 0.1025) && BioplethoraConfig.COMMON.spawnDiamondGrylynen.get()) {
                    summonGrylynen(BioplethoraEntities.DIAMOND_GRYLYNEN.get().create(world), world, pos);
                }
            }

            if (getTaggedBlock(BioplethoraTags.Blocks.NETHERITE_GRYLYNEN_SPAWNABLE.getName(), world, pos)) {
                if (Math.random() < (BioplethoraConfig.getHellMode ? 0.1 : 0.1025) && BioplethoraConfig.COMMON.spawnNetheriteGrylynen.get()) {
                    summonGrylynen(BioplethoraEntities.NETHERITE_GRYLYNEN.get().create(world), world, pos);
                }
            }
        }
    }

    public static boolean getTaggedBlock(ResourceLocation tag, World world, BlockPos pos) {
        return BlockTags.getAllTags().getTagOrEmpty(tag).contains(world.getBlockState(pos).getBlock());
    }

    public static void summonGrylynen(GrylynenEntity grylynen, World world, BlockPos centerPos) {
        if (!(world.getDifficulty() == Difficulty.PEACEFUL)) {

            breakSurroundingBlocks(world, centerPos);

            if (!world.isClientSide()) {
                ServerWorld serverworld = (ServerWorld) world;
                grylynen.moveTo(centerPos, 0.0F, 0.0F);
                grylynen.finalizeSpawn(serverworld, world.getCurrentDifficultyAt(centerPos), SpawnReason.MOB_SUMMONED, null, null);
                serverworld.addFreshEntity(grylynen);
            }
        }
    }

    public static void breakSurroundingBlocks(World world, BlockPos centerPos) {

        double x = centerPos.getX(), y = centerPos.getY(), z = centerPos.getZ();
        BlockPos b1 = new BlockPos(x, y + 1, z);
        BlockPos b2 = new BlockPos(x, y - 1, z);

        BlockPos b3 = new BlockPos(x + 1, y, z + 1);
        BlockPos b4 = new BlockPos(x + 1, y, z - 1);
        BlockPos b5 = new BlockPos(x - 1, y, z + 1);
        BlockPos b6 = new BlockPos(x - 1, y, z - 1);
        BlockPos b7 = new BlockPos(x + 1, y, z);
        BlockPos b8 = new BlockPos(x, y, z + 1);
        BlockPos b9 = new BlockPos(x - 1, y, z);
        BlockPos b10 = new BlockPos(x, y, z - 1);

        BlockPos b11 = new BlockPos(x + 1, y + 1, z + 1);
        BlockPos b12 = new BlockPos(x + 1, y + 1, z - 1);
        BlockPos b13 = new BlockPos(x - 1, y + 1, z + 1);
        BlockPos b14 = new BlockPos(x - 1, y + 1, z - 1);
        BlockPos b15 = new BlockPos(x + 1, y + 1, z);
        BlockPos b16 = new BlockPos(x, y + 1, z + 1);
        BlockPos b17 = new BlockPos(x - 1, y + 1, z);
        BlockPos b18 = new BlockPos(x, y + 1, z - 1);

        BlockPos b19 = new BlockPos(x + 1, y - 1, z + 1);
        BlockPos b20 = new BlockPos(x + 1, y - 1, z - 1);
        BlockPos b21 = new BlockPos(x - 1, y - 1, z + 1);
        BlockPos b22 = new BlockPos(x - 1, y - 1, z - 1);
        BlockPos b23 = new BlockPos(x + 1, y - 1, z);
        BlockPos b24 = new BlockPos(x, y - 1, z + 1);
        BlockPos b25 = new BlockPos(x - 1, y - 1, z);
        BlockPos b26 = new BlockPos(x, y - 1, z - 1);

        destroyBlockAt(b1, world);
        destroyBlockAt(b2, world);

        destroyBlockAt(b3, world);
        destroyBlockAt(b4, world);
        destroyBlockAt(b5, world);
        destroyBlockAt(b6, world);
        destroyBlockAt(b7, world);
        destroyBlockAt(b8, world);
        destroyBlockAt(b9, world);
        destroyBlockAt(b10, world);

        destroyBlockAt(b11, world);
        destroyBlockAt(b12, world);
        destroyBlockAt(b13, world);
        destroyBlockAt(b14, world);
        destroyBlockAt(b15, world);
        destroyBlockAt(b16, world);
        destroyBlockAt(b17, world);
        destroyBlockAt(b18, world);

        destroyBlockAt(b19, world);
        destroyBlockAt(b20, world);
        destroyBlockAt(b21, world);
        destroyBlockAt(b22, world);
        destroyBlockAt(b23, world);
        destroyBlockAt(b24, world);
        destroyBlockAt(b25, world);
        destroyBlockAt(b26, world);
    }

    public static void destroyBlockAt(BlockPos pos, World world) {
        if (!getTaggedBlock(BioplethoraTags.Blocks.GRYLYNEN_UNBREAKABLE.getName(), world, pos)) {
            world.destroyBlock(pos, true);
        }
    }
}
