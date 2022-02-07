package io.github.bioplethora.item.weapons;

import io.github.bioplethora.BioplethoraConfig;
import io.github.bioplethora.entity.others.PrimordialRingEntity;
import io.github.bioplethora.registry.BioplethoraEntities;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.List;

public class PrimordialStaffItem extends Item {

    public boolean hellConfig = BioplethoraConfig.COMMON.hellMode.get();
    public int charge = 0;

    public PrimordialStaffItem(Properties properties) {
        super(properties);
    }

    public UseAction getUseAnimation(ItemStack stack) {
        return UseAction.SPEAR;
    }

    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    public ActionResult<ItemStack> use(World world, PlayerEntity entity, Hand handIn) {
        ItemStack itemstack = entity.getItemInHand(handIn);
        if (entity.getCooldowns().isOnCooldown(itemstack.getItem())) {
            return ActionResult.fail(itemstack);
        } else {
            entity.startUsingItem(handIn);
            return ActionResult.consume(itemstack);
        }
    }

    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        super.onUsingTick(stack, player, count);

        if (player instanceof PlayerEntity) {
            World worldIn = player.level;
            BlockPos blockpos = player.blockPosition();

            ++charge;
            if (charge == 20) {
                worldIn.playSound(null, blockpos, SoundEvents.BEACON_ACTIVATE, SoundCategory.PLAYERS, 1, 1);
                if (worldIn instanceof ServerWorld) {
                    ((ServerWorld) worldIn).sendParticles(ParticleTypes.CRIT, (player.getX()), (player.getY()), (player.getZ()), 50, 0.65, 0.65, 0.65, 0.01);
                }

                /*worldIn.playSound(null, blockpos, SoundEvents.BEACON_ACTIVATE, SoundCategory.PLAYERS, 1, 1);

                BellophiteShieldWaveEntity shieldWave = BioplethoraEntities.BELLOPHITE_SHIELD_WAVE.get().create(worldIn);
                shieldWave.setOwner(player);
                shieldWave.moveTo(blockpos, 0.0F, 0.0F);

                worldIn.addFreshEntity(shieldWave);*/

                charge = 0;
            }
        }
    }

    public void releaseUsing(ItemStack stack, World worldIn, LivingEntity entity, int value) {
        super.releaseUsing(stack, worldIn, entity, value);

        if (entity instanceof PlayerEntity) {
            PlayerEntity playerIn = (PlayerEntity) entity;
            BlockPos blockpos = playerIn.blockPosition();

            int i = this.getUseDuration(stack) - value;
            if (i >= 10) {

                if (worldIn instanceof ServerWorld) {
                    PrimordialRingEntity ring = BioplethoraEntities.PRIMORDIAL_RING.get().create(worldIn);
                    ring.moveTo(blockpos, 0.0F, 0.0F);
                    ring.setOwner(playerIn);
                    ring.finalizeSpawn((IServerWorld) worldIn, worldIn.getCurrentDifficultyAt(blockpos), SpawnReason.MOB_SUMMONED, null, null);

                    ring.setHasLimitedLife(true);
                    ring.setLifeLimitBeforeDeath(hellConfig ? 1000 : 850 + playerIn.getRandom().nextInt(200));

                    worldIn.addFreshEntity(ring);

                    PrimordialRingEntity ring2 = BioplethoraEntities.PRIMORDIAL_RING.get().create(worldIn);
                    ring2.moveTo(blockpos, 0.0F, 0.0F);
                    ring2.setOwner(playerIn);
                    ring2.finalizeSpawn((IServerWorld) worldIn, worldIn.getCurrentDifficultyAt(blockpos), SpawnReason.MOB_SUMMONED, null, null);

                    ring2.setHasLimitedLife(true);
                    ring2.setLifeLimitBeforeDeath(hellConfig ? 1000 : 850 + playerIn.getRandom().nextInt(200));

                    worldIn.addFreshEntity(ring2);
                }

                worldIn.playSound(null, blockpos, SoundEvents.GHAST_SHOOT, SoundCategory.PLAYERS, 1, 1);
                if (worldIn instanceof ServerWorld) {
                    ((ServerWorld) worldIn).sendParticles(ParticleTypes.POOF, (playerIn.getX()), (playerIn.getY()), (playerIn.getZ()), 100, 0.65, 0.65, 0.65, 0.01);
                }

                if (!playerIn.isCreative()) {
                    playerIn.getCooldowns().addCooldown(stack.getItem(), hellConfig ? 500 : 450 + playerIn.getRandom().nextInt(200));
                }
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("item.bioplethora.boss_level.desc").withStyle(TextFormatting.AQUA));
        tooltip.add(new TranslationTextComponent("item.bioplethora.shift_reminder.desc").withStyle(TextFormatting.GRAY));

        tooltip.add(new TranslationTextComponent("item.bioplethora.primordial_staff.cores_aid.skill").withStyle(TextFormatting.GOLD));
        if (Screen.hasShiftDown() || Screen.hasControlDown()) {
            tooltip.add(new TranslationTextComponent("item.bioplethora.primordial_staff.cores_aid.desc").withStyle(TextFormatting.GRAY));
        }
    }
}
