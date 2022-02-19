package io.github.bioplethora.item.weapons;

import io.github.bioplethora.entity.projectile.BellophiteArrowEntity;
import io.github.bioplethora.item.ItemSettings;
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

import javax.annotation.Nullable;
import java.util.List;

public class BellophiteArrowItem extends ArrowItem {

    public BellophiteArrowItem(Item.Properties properties) {
        super(properties);
    }

    public AbstractArrowEntity createArrow(World world, ItemStack itemStack, LivingEntity entity) {
        return new BellophiteArrowEntity(world, entity);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {

        ItemSettings.sacredLevelText(tooltip);

        tooltip.add(new TranslationTextComponent("item.bioplethora.bellophite_arrow.sharper_tip.skill").withStyle(ItemSettings.SKILL_NAME_COLOR));
        if (Screen.hasShiftDown() || Screen.hasControlDown()) {
            tooltip.add(new TranslationTextComponent("item.bioplethora.bellophite_arrow.sharper_tip.desc").withStyle(ItemSettings.SKILL_DESC_COLOR));
        }

        tooltip.add(new TranslationTextComponent("item.bioplethora.bellophite_arrow.core_energy.skill").withStyle(ItemSettings.SKILL_NAME_COLOR));
        if (Screen.hasShiftDown() || Screen.hasControlDown()) {
            tooltip.add(new TranslationTextComponent("item.bioplethora.bellophite_arrow.core_energy.desc").withStyle(ItemSettings.SKILL_DESC_COLOR));
        }
    }
}
