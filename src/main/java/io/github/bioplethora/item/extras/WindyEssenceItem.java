package io.github.bioplethora.item.extras;

import io.github.bioplethora.registry.BPBlocks;
import io.github.bioplethora.registry.BPStructures;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.EyeOfEnderEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class WindyEssenceItem extends Item {

    public WindyEssenceItem(Properties properties) {
        super(properties);
    }

    // Eye of ender code copy moment
    public ActionResult<ItemStack> use(World pLevel, PlayerEntity pPlayer, Hand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        BlockRayTraceResult blockResult = getPlayerPOVHitResult(pLevel, pPlayer, RayTraceContext.FluidMode.NONE);
        if (blockResult.getType() == RayTraceResult.Type.BLOCK && pLevel.getBlockState(blockResult.getBlockPos()).is(BPBlocks.ALPHANUM_NUCLEUS.get())) {
            return ActionResult.pass(itemstack);
        } else {
            pPlayer.startUsingItem(pHand);
            if (pLevel instanceof ServerWorld) {
                BlockPos blockpos = ((ServerWorld)pLevel).getChunkSource().getGenerator().findNearestMapFeature((ServerWorld)pLevel, BPStructures.ALPHANUM_MAUSOLEUM.get(), pPlayer.blockPosition(), 100, false);
                if (blockpos != null) {
                    EyeOfEnderEntity eyeofenderentity = new EyeOfEnderEntity(pLevel, pPlayer.getX(), pPlayer.getY(0.5D), pPlayer.getZ());
                    eyeofenderentity.setItem(itemstack);
                    eyeofenderentity.signalTo(blockpos);
                    pLevel.addFreshEntity(eyeofenderentity);
                    if (pPlayer instanceof ServerPlayerEntity) {
                        CriteriaTriggers.USED_ENDER_EYE.trigger((ServerPlayerEntity)pPlayer, blockpos);
                    }

                    pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.ENDER_EYE_LAUNCH, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
                    pLevel.levelEvent(null, 1003, pPlayer.blockPosition(), 0);
                    if (!pPlayer.abilities.instabuild) {
                        itemstack.shrink(1);
                    }

                    pPlayer.awardStat(Stats.ITEM_USED.get(this));
                    pPlayer.swing(pHand, true);
                    return ActionResult.success(itemstack);
                }
            }

            return ActionResult.consume(itemstack);
        }
    }
}
