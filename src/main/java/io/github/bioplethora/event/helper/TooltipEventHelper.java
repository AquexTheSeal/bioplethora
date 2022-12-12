package io.github.bioplethora.event.helper;

import io.github.bioplethora.api.BPItemSettings;
import io.github.bioplethora.api.IReachWeapon;
import io.github.bioplethora.registry.BPEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

public class TooltipEventHelper {

    public static void onTooltip(ItemTooltipEvent event) {
        if (event.getPlayer() == null) return;

        /*
        if (event.getItemStack().getItem() instanceof IReachWeapon) {
            IReachWeapon reachWeapon = (IReachWeapon) event.getItemStack().getItem();
            event.getToolTip().add(new TranslationTextComponent(ItemSettings.REACH_BONUS, (int) reachWeapon.getReachNBT(event.getItemStack()) - 4).withStyle(ItemSettings.REACH_BONUS_COLOR));
        }
         */

        if (event.getItemStack().getItem() instanceof IReachWeapon) {
            IReachWeapon reachWeapon = (IReachWeapon) event.getItemStack().getItem();
            event.getToolTip().add(new TranslationTextComponent(BPItemSettings.REACH_BONUS, reachWeapon.getReachDistance() - 5).withStyle(BPItemSettings.REACH_BONUS_COLOR));
        }

        if (EnchantmentHelper.getItemEnchantmentLevel(BPEnchantments.ANTIBIO_ECOHARMLESS.get(), event.getItemStack()) > 0) {
            event.getToolTip().add(new TranslationTextComponent(BPItemSettings.ECOHARMLESS_ENCH,
                    EnchantmentHelper.getItemEnchantmentLevel(BPEnchantments.ANTIBIO_ECOHARMLESS.get(), event.getItemStack()))
                    .withStyle(BPItemSettings.ANTIBIO_BONUS_COLOR));
        }
        if (EnchantmentHelper.getItemEnchantmentLevel(BPEnchantments.ANTIBIO_PLETHONEUTRAL.get(), event.getItemStack()) > 0) {
            event.getToolTip().add(new TranslationTextComponent(BPItemSettings.PLETHONEUTRAL_ENCH,
                    EnchantmentHelper.getItemEnchantmentLevel(BPEnchantments.ANTIBIO_ECOHARMLESS.get(), event.getItemStack()))
                    .withStyle(BPItemSettings.ANTIBIO_BONUS_COLOR));
        }
        if (EnchantmentHelper.getItemEnchantmentLevel(BPEnchantments.ANTIBIO_DANGERUM.get(), event.getItemStack()) > 0) {
            event.getToolTip().add(new TranslationTextComponent(BPItemSettings.DANGERUM_ENCH,
                    EnchantmentHelper.getItemEnchantmentLevel(BPEnchantments.ANTIBIO_ECOHARMLESS.get(), event.getItemStack()))
                    .withStyle(BPItemSettings.ANTIBIO_BONUS_COLOR));
        }
        if (EnchantmentHelper.getItemEnchantmentLevel(BPEnchantments.ANTIBIO_HELLSENT.get(), event.getItemStack()) > 0) {
            event.getToolTip().add(new TranslationTextComponent(BPItemSettings.HELLSENT_ENCH,
                    EnchantmentHelper.getItemEnchantmentLevel(BPEnchantments.ANTIBIO_ECOHARMLESS.get(), event.getItemStack()))
                    .withStyle(BPItemSettings.ANTIBIO_BONUS_COLOR));
        }
        if (EnchantmentHelper.getItemEnchantmentLevel(BPEnchantments.ANTIBIO_ELDERIA.get(), event.getItemStack()) > 0) {
            event.getToolTip().add(new TranslationTextComponent(BPItemSettings.ELDERIA_ENCH,
                    EnchantmentHelper.getItemEnchantmentLevel(BPEnchantments.ANTIBIO_ECOHARMLESS.get(), event.getItemStack()))
                    .withStyle(BPItemSettings.ANTIBIO_BONUS_COLOR));
        }

        /*
        if (
                false
        ) {
            event.getToolTip().add(new TranslationTextComponent(ItemSettings.NO_USE_YET).withStyle(ItemSettings.NO_USE_COLOR));
        }*/
    }
}
