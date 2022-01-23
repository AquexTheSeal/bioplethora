package io.github.bioplethora.blocks;

import io.github.bioplethora.entity.others.AltyrusSummoningEntity;
import io.github.bioplethora.registry.BioplethoraBlocks;
import io.github.bioplethora.registry.BioplethoraEntities;
import io.github.bioplethora.registry.BioplethoraItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class BellophiteCoreBlock extends Block {


    public BellophiteCoreBlock(Properties properties) {
        super(properties);
    }

    @Override
    public int getLightBlock(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return 15;
    }

    @Override
    public ActionResultType use(BlockState blockstate, World world, BlockPos pos, PlayerEntity entity, Hand hand, BlockRayTraceResult hit) {

        int x = pos.getX(), y = pos.getY(), z = pos.getZ();
        //center blocks
        BlockPos c1 = new BlockPos(x, y + 1, z);
        BlockPos c2 = new BlockPos(x, y - 1, z);
        //top layer
        BlockPos t1 = new BlockPos(x + 1, y + 1, z);
        BlockPos t2 = new BlockPos(x - 1, y + 1, z);
        BlockPos t3 = new BlockPos(x, y + 1, z + 1);
        BlockPos t4 = new BlockPos(x, y + 1, z - 1);
        BlockPos t5 = new BlockPos(x + 2, y + 1, z);
        BlockPos t6 = new BlockPos(x - 2, y + 1, z);
        BlockPos t7 = new BlockPos(x, y + 1, z + 2);
        BlockPos t8 = new BlockPos(x, y + 1, z - 2);
        BlockPos t9 = new BlockPos(x + 3, y + 1, z);
        BlockPos t10 = new BlockPos(x - 3, y + 1, z);
        BlockPos t11 = new BlockPos(x, y + 1, z + 3);
        BlockPos t12 = new BlockPos(x, y + 1, z - 3);
        //mid layer
        BlockPos m1 = new BlockPos(x + 3, y, z);
        BlockPos m2 = new BlockPos(x - 3, y, z);
        BlockPos m3 = new BlockPos(x, y, z + 3);
        BlockPos m4 = new BlockPos(x, y, z - 3);
        //bottom layer
        BlockPos b1 = new BlockPos(x + 3, y - 1, z);
        BlockPos b2 = new BlockPos(x - 3, y - 1, z);
        BlockPos b3 = new BlockPos(x, y - 1, z + 3);
        BlockPos b4 = new BlockPos(x, y - 1, z - 3);

        if (entity.getMainHandItem().getItem() == BioplethoraItems.BELLOPHITE.get()) {

            if (((world.getBlockState(c1)).getBlock() == BioplethoraBlocks.BELLOPHITE_BLOCK.get()) &&
                    ((world.getBlockState(c2)).getBlock() == BioplethoraBlocks.BELLOPHITE_BLOCK.get()) &&
                    //top layer
                    ((world.getBlockState(t1)).getBlock() == Blocks.CHAIN) &&
                    ((world.getBlockState(t2)).getBlock() == Blocks.CHAIN) &&
                    ((world.getBlockState(t3)).getBlock() == Blocks.CHAIN) &&
                    ((world.getBlockState(t4)).getBlock() == Blocks.CHAIN) &&
                    ((world.getBlockState(t5)).getBlock() == Blocks.CHAIN) &&
                    ((world.getBlockState(t6)).getBlock() == Blocks.CHAIN) &&
                    ((world.getBlockState(t7)).getBlock() == Blocks.CHAIN) &&
                    ((world.getBlockState(t8)).getBlock() == Blocks.CHAIN) &&
                    ((world.getBlockState(t9)).getBlock() == BioplethoraBlocks.BELLOPHITE_BLOCK.get()) &&
                    ((world.getBlockState(t10)).getBlock() == BioplethoraBlocks.BELLOPHITE_BLOCK.get()) &&
                    ((world.getBlockState(t11)).getBlock() == BioplethoraBlocks.BELLOPHITE_BLOCK.get()) &&
                    ((world.getBlockState(t12)).getBlock() == BioplethoraBlocks.BELLOPHITE_BLOCK.get()) &&
                    //mid layer
                    ((world.getBlockState(m1)).getBlock() == Blocks.CHAIN) &&
                    ((world.getBlockState(m2)).getBlock() == Blocks.CHAIN) &&
                    ((world.getBlockState(m3)).getBlock() == Blocks.CHAIN) &&
                    ((world.getBlockState(m4)).getBlock() == Blocks.CHAIN) &&
                    //bottom layer
                    ((world.getBlockState(b1)).getBlock() == BioplethoraBlocks.BELLOPHITE_BLOCK.get()) &&
                    ((world.getBlockState(b2)).getBlock() == BioplethoraBlocks.BELLOPHITE_BLOCK.get()) &&
                    ((world.getBlockState(b3)).getBlock() == BioplethoraBlocks.BELLOPHITE_BLOCK.get()) &&
                    ((world.getBlockState(b4)).getBlock() == BioplethoraBlocks.BELLOPHITE_BLOCK.get())) {

                if (!entity.isCreative()) {
                    ItemStack ItemToRemove = new ItemStack(BioplethoraItems.BELLOPHITE.get());
                    entity.inventory.clearOrCountMatchingItems(p -> ItemToRemove.getItem() == p.getItem(), 1, entity.inventoryMenu.getCraftSlots());
                }

                if (!world.isClientSide()) {
                    world.playSound(null, new BlockPos(x, y, z), SoundEvents.END_PORTAL_FRAME_FILL, SoundCategory.NEUTRAL, (float) 1, (float) 1);
                    world.playSound(null, new BlockPos(x, y, z), SoundEvents.ELDER_GUARDIAN_CURSE, SoundCategory.NEUTRAL, (float) 1, (float) 1);
                    //main center
                    world.destroyBlock(new BlockPos(x, y, z), false);
                    world.destroyBlock(c1, false);
                    world.destroyBlock(c2, false);
                    //top layer
                    world.destroyBlock(t1, false);
                    world.destroyBlock(t2, false);
                    world.destroyBlock(t3, false);
                    world.destroyBlock(t4, false);
                    world.destroyBlock(t5, false);
                    world.destroyBlock(t6, false);
                    world.destroyBlock(t7, false);
                    world.destroyBlock(t8, false);
                    world.destroyBlock(t9, false);
                    world.destroyBlock(t10, false);
                    world.destroyBlock(t11, false);
                    world.destroyBlock(t12, false);
                    //mid layer
                    world.destroyBlock(m1, false);
                    world.destroyBlock(m2, false);
                    world.destroyBlock(m3, false);
                    world.destroyBlock(m4, false);
                    //bottom layer
                    world.destroyBlock(b1, false);
                    world.destroyBlock(b2, false);
                    world.destroyBlock(b3, false);
                    world.destroyBlock(b4, false);

                    ((ServerWorld) world).sendParticles(ParticleTypes.POOF, x, y, z, 40, 0.4, 0.4, 0.4, 0.1);
                    ServerWorld serverworld = (ServerWorld) world;
                    BlockPos blockpos = (new BlockPos(x, y, z));
                    AltyrusSummoningEntity altyrusSummoningEntity = BioplethoraEntities.ALTYRUS_SUMMONING.get().create(world);
                    altyrusSummoningEntity.moveTo(blockpos, 0.0F, 0.0F);

                    serverworld.addFreshEntity(altyrusSummoningEntity);

                    if (!entity.level.isClientSide()) {
                        entity.displayClientMessage(new StringTextComponent("Summon successful!"), (false));
                    }
                }

            } else if (!entity.level.isClientSide()) {
                entity.displayClientMessage(new StringTextComponent("Invalid structure, use the Biopedia to find the correct structure."), (false));
            }
        } else {
            if (!entity.level.isClientSide()) {
                entity.displayClientMessage(new StringTextComponent("Invalid item for the altar, requires: Bellophite."), (false));
            }
        }

        return ActionResultType.SUCCESS;
    }
}
