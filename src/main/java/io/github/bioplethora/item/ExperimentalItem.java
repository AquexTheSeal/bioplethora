package io.github.bioplethora.item;

import io.github.bioplethora.api.IReachWeapon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class ExperimentalItem extends Item implements IReachWeapon {

    public ExperimentalItem(Properties properties) {
        super(properties);
    }

    @Override
    public double getReachDistance() {
        return 128.0D;
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        return true;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int pItemSlot, boolean pIsSelected) {
        super.inventoryTick(stack, world, entity, pItemSlot, pIsSelected);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity entity, LivingEntity source) {
        for (int i = entity.level.getMaxBuildHeight(); i > entity.getY(); i--) {
            for (int c = 0; c < 90; c++) {
                entity.level.addParticle(ParticleTypes.FLAME, entity.getX(), i, entity.getZ(), Math.sin(c), 0.01, Math.cos(c));
            }
        }
        return super.hurtEnemy(stack, entity, source);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent("item.bioplethora.boss_level.desc").withStyle(TextFormatting.AQUA));
        tooltip.add(new TranslationTextComponent("item.bioplethora.test_item.desc").withStyle(TextFormatting.GRAY));
    }
}