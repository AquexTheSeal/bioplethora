package io.github.bioplethora.item.weapons;

import io.github.bioplethora.entity.others.BellophiteShieldWaveEntity;
import io.github.bioplethora.item.ItemSettings;
import io.github.bioplethora.registry.BPEntities;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.item.UseAction;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class BellophiteShieldItem extends ShieldItem {

    public boolean isCharged;
    public int corePoints;

    public BellophiteShieldItem(Properties properties) {
        super(properties);
        this.corePoints = 0;
        this.isCharged = false;
    }

    @Override
    public boolean isShield(ItemStack stack, LivingEntity entity) {
        return true;
    }

    @Override
    public int getUseDuration(ItemStack itemStack) {
        return 125000;
    }

    @Override
    public UseAction getUseAnimation(ItemStack itemStack) {
        return UseAction.BLOCK;
    }

    public void executeSkill(ItemStack stack, LivingEntity player, World world) {

        double x = player.getX(), y = player.getY(), z = player.getZ();
        BlockPos pos = new BlockPos(x, y + 1, z);

        if (!((PlayerEntity) player).getCooldowns().isOnCooldown(stack.getItem())) {

            if (!this.getIsCharged()) {
                if (!world.isClientSide) {
                    ((ServerWorld) world).sendParticles(new ItemParticleData(ParticleTypes.ITEM, stack), x, y + 1.3, z, 25, 1, 1, 1, 0.1);
                }
                world.playSound(null, pos, SoundEvents.GLASS_BREAK, SoundCategory.PLAYERS, 1, 1);
                this.addCorePoints(1);
            }

            if (this.getCorePoints() == 4) {
                this.addCorePoints(1);
                this.setIsCharged(true);
            }

            if (this.getIsCharged()) {
                world.playSound(null, pos, SoundEvents.BEACON_ACTIVATE, SoundCategory.PLAYERS, 1, 1);

                BellophiteShieldWaveEntity shieldWave = BPEntities.BELLOPHITE_SHIELD_WAVE.get().create(player.level);
                shieldWave.setOwner(player);
                shieldWave.moveTo(pos, 0.0F, 0.0F);

                player.level.addFreshEntity(shieldWave);

                this.clearCorePoints();
                this.setIsCharged(false);

            }
        }
    }

    public void addCorePoints(int value) {
        this.corePoints = this.corePoints + value;
    }

    public void clearCorePoints() {
        this.corePoints = 0;
    }

    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        super.onUsingTick(stack, player, count);
        player.addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, 5, 2));
        player.addEffect(new EffectInstance(Effects.REGENERATION, 5, 1));
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        ItemSettings.sacredLevelText(tooltip);

        tooltip.add(new TranslationTextComponent("item.bioplethora.bellophite_shield.recovery_bulwark.skill").withStyle(ItemSettings.SKILL_NAME_COLOR));
        if (Screen.hasShiftDown() || Screen.hasControlDown()) {
            tooltip.add(new TranslationTextComponent("item.bioplethora.bellophite_shield.recovery_bulwark.desc").withStyle(ItemSettings.SKILL_DESC_COLOR));
        }

        tooltip.add(new TranslationTextComponent("item.bioplethora.bellophite_shield.core_impulse.skill").withStyle(ItemSettings.SKILL_NAME_COLOR));
        if (Screen.hasShiftDown() || Screen.hasControlDown()) {
            tooltip.add(new TranslationTextComponent("item.bioplethora.bellophite_shield.core_impulse.desc").withStyle(ItemSettings.SKILL_DESC_COLOR));
        }
    }

    public boolean getIsCharged() {
        return this.isCharged;
    }

    public void setIsCharged(boolean value) {
        this.isCharged = value;
    }

    public int getCorePoints() {
        return this.corePoints;
    }

    public void setCorePoints(int value) {
        this.corePoints = value;
    }
}