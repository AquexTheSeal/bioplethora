package io.github.bioplethora.blocks;

import io.github.bioplethora.entity.creatures.AltyrusEntity;
import io.github.bioplethora.entity.creatures.BellophgolemEntity;
import io.github.bioplethora.entity.others.AltyrusSummoningEntity;
import io.github.bioplethora.registry.BioplethoraBlocks;
import io.github.bioplethora.registry.BioplethoraEntities;
import io.github.bioplethora.registry.BioplethoraItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class BellophiteCoreBlock extends Block {

    public BellophiteCoreBlock(Properties properties) {
        super(properties);
    }

    @Override
    public int getLightBlock(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return 0;
    }

    @Override
    public ActionResultType use(BlockState blockstate, World world, BlockPos pos, PlayerEntity entity, Hand hand, BlockRayTraceResult hit) {

        if (((world.getBlockState(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ()))).getBlock() == BioplethoraBlocks.BELLOPHITE_BLOCK.get()) &&
                ((world.getBlockState(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ()))).getBlock() == BioplethoraBlocks.BELLOPHITE_BLOCK.get())) {

            if (entity.getMainHandItem().getItem() == BioplethoraItems.BELLOPHITE.get()) {

                if (!entity.isCreative()) {
                    ItemStack ItemToRemove = new ItemStack(BioplethoraItems.BELLOPHITE.get());
                    entity.inventory.clearOrCountMatchingItems(p -> ItemToRemove.getItem() == p.getItem(), 1, entity.inventoryMenu.getCraftSlots());
                }

                if (!world.isClientSide()) {
                    world.playSound(null, new BlockPos(pos.getX(), pos.getY(), pos.getZ()), SoundEvents.END_PORTAL_FRAME_FILL, SoundCategory.NEUTRAL, (float) 1, (float) 1);
                    world.playSound(null, new BlockPos(pos.getX(), pos.getY(), pos.getZ()), SoundEvents.ELDER_GUARDIAN_CURSE, SoundCategory.NEUTRAL, (float) 1, (float) 1);
                    world.destroyBlock(new BlockPos(pos.getX(), pos.getY(), pos.getZ()), false);
                    world.destroyBlock(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ()), false);
                    world.destroyBlock(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ()), false);

                    ((ServerWorld) world).sendParticles(ParticleTypes.POOF, (pos.getX()), (pos.getY()), (pos.getZ()), (int) 40, 0.4, 0.4,
                            0.4, 0.1);

                    ServerWorld serverworld = (ServerWorld)world;
                    BlockPos blockpos = (new BlockPos(pos.getX(), pos.getY(), pos.getZ()));

                    AltyrusSummoningEntity altyrusSummoningEntity = BioplethoraEntities.ALTYRUS_SUMMONING.get().create(world);
                    altyrusSummoningEntity.moveTo(blockpos, 0.0F, 0.0F);

                    serverworld.addFreshEntity(altyrusSummoningEntity);
                }
            }
        }
        return ActionResultType.SUCCESS;
    }
}
