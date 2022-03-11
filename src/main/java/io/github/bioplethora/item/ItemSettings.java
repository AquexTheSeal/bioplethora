package io.github.bioplethora.item;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.List;

public class ItemSettings {
    public static String SACRED_LEVEL = "item.bioplethora.sacred_level.desc";
    public static String BOSS_LEVEL = "item.bioplethora.boss_level.desc";
    public static String SHIFT_REMINDER = "item.bioplethora.shift_reminder.desc";

    public static TextFormatting TIER_LEVEL_COLOR = TextFormatting.AQUA;
    public static TextFormatting SHIFT_REMINDER_COLOR = TextFormatting.GRAY;

    public static TextFormatting SKILL_NAME_COLOR = TextFormatting.GREEN;
    public static TextFormatting SKILL_DESC_COLOR = TextFormatting.GRAY;

    public static void sacredLevelText(List<ITextComponent> tooltip) {
        tooltip.add(new TranslationTextComponent(ItemSettings.SACRED_LEVEL).withStyle(ItemSettings.TIER_LEVEL_COLOR));
        tooltip.add(new TranslationTextComponent(ItemSettings.SHIFT_REMINDER).withStyle(ItemSettings.SHIFT_REMINDER_COLOR));
    }

    public static void bossLevelText(List<ITextComponent> tooltip) {
        tooltip.add(new TranslationTextComponent(ItemSettings.BOSS_LEVEL).withStyle(ItemSettings.TIER_LEVEL_COLOR));
        tooltip.add(new TranslationTextComponent(ItemSettings.SHIFT_REMINDER).withStyle(ItemSettings.SHIFT_REMINDER_COLOR));
    }

    public static TextFormatting ANTIBIO_BONUS_COLOR = TextFormatting.YELLOW;

    public static String ECOHARMLESS_ENCH = "tooltip.bioplethora.antibio_ecoharmless.desc";
    public static String PLETHONEUTRAL_ENCH = "tooltip.bioplethora.antibio_plethoneutral.desc";
    public static String DANGERUM_ENCH = "tooltip.bioplethora.antibio_dangerum.desc";
    public static String HELLSENT_ENCH = "tooltip.bioplethora.antibio_hellsent.desc";
    public static String ELDERIA_ENCH = "tooltip.bioplethora.antibio_elderia.desc";
}
