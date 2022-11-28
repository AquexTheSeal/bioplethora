package io.github.bioplethora.event.helper;

import com.google.common.collect.ImmutableList;
import io.github.bioplethora.blocks.api.world.EntityUtils;
import io.github.bioplethora.blocks.SmallMushroomBlock;
import io.github.bioplethora.registry.BPBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.eventbus.api.Event;

import java.util.Random;

public class BonemealBlocksHelper {

    public static void performBonemealAction(BonemealEvent event) {
        World world = event.getWorld();

        doBoneMeal(event,
                soulSandValleyPlants(world.getRandom()),
                ImmutableList.of(Blocks.SOUL_SOIL, Blocks.SOUL_SAND)
        );
    }

    public static BlockState soulSandValleyPlants(Random random) {
        switch (random.nextInt(3)) {
            default: return BPBlocks.SOUL_TALL_GRASS.get().defaultBlockState();
            case 1: return BPBlocks.SOUL_MINISHROOM.get().defaultBlockState().setValue(SmallMushroomBlock.MINISHROOMS, 1 + random.nextInt(3));
            case 2: return BPBlocks.SOUL_BIGSHROOM.get().defaultBlockState();
        }
    }

    public static void doBoneMeal(BonemealEvent event, BlockState blockSelector, ImmutableList<Block> blockList) {

        World pLevel = event.getWorld(); BlockState state = event.getBlock();
        BlockPos pPos = event.getPos(); ItemStack stack = event.getStack();
        PlayerEntity player = event.getPlayer();

        if (blockList.contains(pLevel.getBlockState(pPos).getBlock())) {
            EntityUtils.swingAHand(stack, player);
            stack.shrink(1);

            Random pRand = pLevel.random;
            BlockPos targetPos = pPos.above();

            for (int i = 0; i < 128; i++) {
                targetPos = targetPos.offset(pRand.nextInt(5) - 1, (pRand.nextInt(4) - 1) * pRand.nextInt(4) / 2, pRand.nextInt(5) - 1);

                if (pLevel.getBlockState(targetPos).isAir()) {
                    if (blockList.contains(pLevel.getBlockState(targetPos.below()).getBlock())) {
                        if (blockSelector.getBlock() instanceof DoublePlantBlock) {
                            ((DoublePlantBlock) blockSelector.getBlock()).placeAt(pLevel, targetPos, 2);

                        } else pLevel.setBlock(targetPos, blockSelector, 2);
                    }
                }
            }

            for(int i = 0; i < 15; ++i) {
                double d2 = pRand.nextGaussian() * 0.02D;
                double d3 = pRand.nextGaussian() * 0.02D;
                double d4 = pRand.nextGaussian() * 0.02D;
                double d6 = (double)pPos.getX() + pRand.nextDouble() * 2.0D;
                double d7 = (double)pPos.getY() + pRand.nextDouble() * 3.0D;
                double d8 = (double)pPos.getZ() + pRand.nextDouble() * 2.0D;
                if (!pLevel.getBlockState((new BlockPos(d6, d7, d8)).below()).isAir()) {
                    pLevel.addParticle(ParticleTypes.SOUL, d6, d7, d8, d2, d3, d4);
                }
            }

            event.setResult(Event.Result.ALLOW);
        }
    }
}
