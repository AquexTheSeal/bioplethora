package io.github.bioplethora.event.helper;

import io.github.bioplethora.BioplethoraConfig;
import io.github.bioplethora.entity.creatures.GrylynenEntity;
import io.github.bioplethora.entity.others.GrylynenCoreBombEntity;
import io.github.bioplethora.registry.BioplethoraEffects;
import io.github.bioplethora.registry.BioplethoraEntities;
import io.github.bioplethora.registry.BioplethoraTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
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
        if ((IWorld instanceof World) && !(event.getPlayer().hasEffect(BioplethoraEffects.SPIRIT_FISSION.get()))) {

            World world = (World) event.getWorld();
            double x = event.getPos().getX(), y = event.getPos().getY(), z = event.getPos().getZ();
            BlockPos pos = new BlockPos(x, y, z);

            if (!event.getPlayer().isCreative()) {

                if (getTaggedBlock(BioplethoraTags.Blocks.WOODEN_GRYLYNEN_SPAWNABLE.getName(), world, pos)) {
                    if (Math.random() < (BioplethoraConfig.getHellMode ? 0.05 : 0.075) && BioplethoraConfig.COMMON.spawnWoodenGrylynen.get()) {
                        summonGrylynenCore(BioplethoraEntities.WOODEN_GRYLYNEN.get().create(world), world, pos);
                    }
                }

                if (getTaggedBlock(BioplethoraTags.Blocks.STONE_GRYLYNEN_SPAWNABLE.getName(), world, pos)) {
                    if (Math.random() < (BioplethoraConfig.getHellMode ? 0.05 : 0.075) && BioplethoraConfig.COMMON.spawnStoneGrylynen.get()) {
                        summonGrylynenCore(BioplethoraEntities.STONE_GRYLYNEN.get().create(world), world, pos);
                    }
                }

                if (getTaggedBlock(BioplethoraTags.Blocks.GOLDEN_GRYLYNEN_SPAWNABLE.getName(), world, pos)) {
                    if (Math.random() < (BioplethoraConfig.getHellMode ? 0.075 : 0.1) && BioplethoraConfig.COMMON.spawnGoldenGrylynen.get()) {
                        summonGrylynenCore(BioplethoraEntities.GOLDEN_GRYLYNEN.get().create(world), world, pos);
                    }
                }

                if (getTaggedBlock(BioplethoraTags.Blocks.IRON_GRYLYNEN_SPAWNABLE.getName(), world, pos)) {
                    if (Math.random() < (BioplethoraConfig.getHellMode ? 0.075 : 0.1) && BioplethoraConfig.COMMON.spawnIronGrylynen.get()) {
                        summonGrylynenCore(BioplethoraEntities.IRON_GRYLYNEN.get().create(world), world, pos);
                    }
                }

                if (getTaggedBlock(BioplethoraTags.Blocks.DIAMOND_GRYLYNEN_SPAWNABLE.getName(), world, pos)) {
                    if (Math.random() < (BioplethoraConfig.getHellMode ? 0.1 : 0.1025) && BioplethoraConfig.COMMON.spawnDiamondGrylynen.get()) {
                        summonGrylynenCore(BioplethoraEntities.DIAMOND_GRYLYNEN.get().create(world), world, pos);
                    }
                }

                if (getTaggedBlock(BioplethoraTags.Blocks.NETHERITE_GRYLYNEN_SPAWNABLE.getName(), world, pos)) {
                    if (Math.random() < (BioplethoraConfig.getHellMode ? 0.1 : 0.1025) && BioplethoraConfig.COMMON.spawnNetheriteGrylynen.get()) {
                        summonGrylynenCore(BioplethoraEntities.NETHERITE_GRYLYNEN.get().create(world), world, pos);
                    }
                }
            }
        }
    }

    public static boolean getTaggedBlock(ResourceLocation tag, World world, BlockPos pos) {
        return BlockTags.getAllTags().getTagOrEmpty(tag).contains(world.getBlockState(pos).getBlock());
    }

    public static void summonGrylynenCore(GrylynenEntity grylynen, World world, BlockPos centerPos) {

        GrylynenCoreBombEntity core = BioplethoraEntities.GRYLYNEN_CORE_BOMB.get().create(world);
        if (!(world.getDifficulty() == Difficulty.PEACEFUL)) {

            /*
            if (world.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
                breakSurroundingBlocks(world, centerPos);
            }
             */

            if (!world.isClientSide()) {
                ServerWorld serverworld = (ServerWorld) world;
                core.moveTo(centerPos, 0.0F, 0.0F);
                core.setTier(grylynen.getGrylynenTier());
                serverworld.addFreshEntity(core);
            }
        }
    }

    public static void breakSurroundingBlocks(World world, BlockPos centerPos) {

        grylynenDestroyBlocks(world, centerPos, 1, 1, 1, true);
    }

    public static void destroyBlockAt(BlockPos pos, World world) {
        if (!getTaggedBlock(BioplethoraTags.Blocks.GRYLYNEN_UNBREAKABLE.getName(), world, pos)) {
            world.destroyBlock(pos, true);
        }
    }

    public static void grylynenDestroyBlocks(World world, BlockPos point, int radiusX, int radiusY, int radiusZ, boolean shouldDrop) {

        for (int radY = point.getY() - radiusX; radY <= point.getY() + radiusY; radY++) {
            for (int radX = point.getX() - radiusX; radX <= point.getX() + radiusX; radX++) {
                for (int radZ = point.getZ() - radiusZ; radZ <= point.getZ() + radiusZ; radZ++) {

                    BlockPos pos = new BlockPos(radX, radY, radZ);
                    BlockState blockState = world.getBlockState(pos);
                    Block block = blockState.getBlock();

                    destroyBlockAt(pos, world);
                }
            }
        }
    }
}
