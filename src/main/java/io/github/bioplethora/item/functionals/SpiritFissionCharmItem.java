package io.github.bioplethora.item.functionals;

import io.github.bioplethora.item.ItemSettings;
import io.github.bioplethora.registry.BioplethoraEffects;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class SpiritFissionCharmItem extends Item {

    public SpiritFissionCharmItem(Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(ItemStack pStack, World pLevel, Entity pEntity, int pItemSlot, boolean pIsSelected) {
        super.inventoryTick(pStack, pLevel, pEntity, pItemSlot, pIsSelected);

        if (pEntity instanceof PlayerEntity) {
            ((PlayerEntity) pEntity).addEffect(new EffectInstance(BioplethoraEffects.SPIRIT_FISSION.get(), 5, 1));
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {

        ItemSettings.sacredLevelText(tooltip);

        tooltip.add(new TranslationTextComponent("item.bioplethora.spirit_fission_charm.spirit_fission.skill").withStyle(ItemSettings.SKILL_NAME_COLOR));
        if (Screen.hasShiftDown() || Screen.hasControlDown()) {
            tooltip.add(new TranslationTextComponent("item.bioplethora.spirit_fission_charm.spirit_fission.desc").withStyle(ItemSettings.SKILL_DESC_COLOR));
        }
    }
}
