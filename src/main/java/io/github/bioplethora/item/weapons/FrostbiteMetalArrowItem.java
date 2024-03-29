package io.github.bioplethora.item.weapons;

import io.github.bioplethora.api.BPItemSettings;
import io.github.bioplethora.entity.projectile.FrostbiteMetalArrowEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class FrostbiteMetalArrowItem extends ArrowItem {

    public FrostbiteMetalArrowItem(Item.Properties properties) {
        super(properties);
    }

    public AbstractArrowEntity createArrow(World world, ItemStack itemStack, LivingEntity entity) {
        return new FrostbiteMetalArrowEntity(world, entity);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        BPItemSettings.sacredLevelText(tooltip);

        tooltip.add(new TranslationTextComponent("item.bioplethora.frostbite_metal_arrow.sharper_tip.skill").withStyle(BPItemSettings.SKILL_NAME_COLOR));
        if (Screen.hasShiftDown() || Screen.hasControlDown()) {
            tooltip.add(new TranslationTextComponent("item.bioplethora.frostbite_metal_arrow.sharper_tip.desc").withStyle(BPItemSettings.SKILL_DESC_COLOR));
        }

        tooltip.add(new TranslationTextComponent("item.bioplethora.frostbite_metal_arrow.core_energy.skill").withStyle(BPItemSettings.SKILL_NAME_COLOR));
        if (Screen.hasShiftDown() || Screen.hasControlDown()) {
            tooltip.add(new TranslationTextComponent("item.bioplethora.frostbite_metal_arrow.core_energy.desc").withStyle(BPItemSettings.SKILL_DESC_COLOR));
        }
    }
}
