package io.github.bioplethora.item;

import io.github.bioplethora.api.IReachWeapon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biomes;
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
        Minecraft mc = Minecraft.getInstance();
        BlockPos blockpos = Minecraft.getInstance().getCameraEntity().blockPosition();
        entity.sendMessage(new StringTextComponent(mc.level.registryAccess().registryOrThrow(Registry.BIOME_REGISTRY).getKey(mc.level.getBiome(blockpos)).toString()), mc.player.getUUID());
        return true;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int pItemSlot, boolean pIsSelected) {
        super.inventoryTick(stack, world, entity, pItemSlot, pIsSelected);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity entity, LivingEntity source) {

        World world = entity.level;
        double x = entity.getX(), y = entity.getY(), z = entity.getZ();
        BlockPos pos = new BlockPos(x, y, z);
        PlayerEntity player = (PlayerEntity) source;

        boolean retval = super.hurtEnemy(stack, entity, source);

        player.displayClientMessage(new StringTextComponent("Hit Successful"), (false));
        /*
        if (entity instanceof IBioClassification) {
            switch (((IBioClassification) entity).getBioplethoraClass()) {
                case NONE: player.displayClientMessage(new StringTextComponent("Entity Class: None"), (false));
                case ECOHARMLESS: player.displayClientMessage(new StringTextComponent("Entity Class: Ecoharmless"), (false));
                case PLETHONEUTRAL: player.displayClientMessage(new StringTextComponent("Entity Class: Plethoneutral"), (false));
                case DANGERUM: player.displayClientMessage(new StringTextComponent("Entity Class: Dangerum"), (false));
                case HELLSENT: player.displayClientMessage(new StringTextComponent("Entity Class: Hellsent"), (false));
                case ELDERIA: player.displayClientMessage(new StringTextComponent("Entity Class: Elderia"), (false));
            }
        }*/
        return retval;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent("item.bioplethora.boss_level.desc").withStyle(TextFormatting.AQUA));
        tooltip.add(new TranslationTextComponent("item.bioplethora.test_item.desc").withStyle(TextFormatting.GRAY));
    }
}