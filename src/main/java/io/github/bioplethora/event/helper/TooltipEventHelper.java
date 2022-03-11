package io.github.bioplethora.event.helper;

import io.github.bioplethora.item.ItemSettings;
import io.github.bioplethora.registry.BioplethoraEnchantments;
import io.github.bioplethora.registry.BioplethoraItems;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

public class TooltipEventHelper {

    public static void onTooltip(ItemTooltipEvent event) {
        if(event.getPlayer() == null) {
            return;
        }
        if (EnchantmentHelper.getItemEnchantmentLevel(BioplethoraEnchantments.ANTIBIO_ECOHARMLESS.get(), event.getItemStack()) > 0) {
            event.getToolTip().add(new TranslationTextComponent(ItemSettings.ECOHARMLESS_ENCH,
                    EnchantmentHelper.getItemEnchantmentLevel(BioplethoraEnchantments.ANTIBIO_ECOHARMLESS.get(), event.getItemStack()))
                    .withStyle(ItemSettings.ANTIBIO_BONUS_COLOR));
        }
        if (EnchantmentHelper.getItemEnchantmentLevel(BioplethoraEnchantments.ANTIBIO_PLETHONEUTRAL.get(), event.getItemStack()) > 0) {
            event.getToolTip().add(new TranslationTextComponent(ItemSettings.PLETHONEUTRAL_ENCH,
                    EnchantmentHelper.getItemEnchantmentLevel(BioplethoraEnchantments.ANTIBIO_ECOHARMLESS.get(), event.getItemStack()))
                    .withStyle(ItemSettings.ANTIBIO_BONUS_COLOR));
        }
        if (EnchantmentHelper.getItemEnchantmentLevel(BioplethoraEnchantments.ANTIBIO_DANGERUM.get(), event.getItemStack()) > 0) {
            event.getToolTip().add(new TranslationTextComponent(ItemSettings.DANGERUM_ENCH,
                    EnchantmentHelper.getItemEnchantmentLevel(BioplethoraEnchantments.ANTIBIO_ECOHARMLESS.get(), event.getItemStack()))
                    .withStyle(ItemSettings.ANTIBIO_BONUS_COLOR));
        }
        if (EnchantmentHelper.getItemEnchantmentLevel(BioplethoraEnchantments.ANTIBIO_HELLSENT.get(), event.getItemStack()) > 0) {
            event.getToolTip().add(new TranslationTextComponent(ItemSettings.HELLSENT_ENCH,
                    EnchantmentHelper.getItemEnchantmentLevel(BioplethoraEnchantments.ANTIBIO_ECOHARMLESS.get(), event.getItemStack()))
                    .withStyle(ItemSettings.ANTIBIO_BONUS_COLOR));
        }
        if (EnchantmentHelper.getItemEnchantmentLevel(BioplethoraEnchantments.ANTIBIO_ELDERIA.get(), event.getItemStack()) > 0) {
            event.getToolTip().add(new TranslationTextComponent(ItemSettings.ELDERIA_ENCH,
                    EnchantmentHelper.getItemEnchantmentLevel(BioplethoraEnchantments.ANTIBIO_ECOHARMLESS.get(), event.getItemStack()))
                    .withStyle(ItemSettings.ANTIBIO_BONUS_COLOR));
        }

        if (
                event.getItemStack().getItem() == BioplethoraItems.FLEIGNARITE_SCALES.get() ||
                        event.getItemStack().getItem() == BioplethoraItems.RED_GRYLYNEN_CRYSTAL.get() ||
                        event.getItemStack().getItem() == BioplethoraItems.ALPHANUM_GEM.get() ||
                        event.getItemStack().getItem() == BioplethoraItems.ALPHEM_KING_REMNANT.get()
        ) {
            event.getToolTip().add(new TranslationTextComponent(ItemSettings.NO_USE_YET).withStyle(ItemSettings.NO_USE_COLOR));
        }
    }
}
