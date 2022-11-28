package io.github.bioplethora.blocks.api;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.List;

public class BPItemSettings {
    public static String SACRED_LEVEL = "item.bioplethora.sacred_level.desc";
    public static String BOSS_LEVEL = "item.bioplethora.boss_level.desc";
    public static String SHIFT_REMINDER = "item.bioplethora.shift_reminder.desc";

    public static TextFormatting TIER_LEVEL_COLOR = TextFormatting.AQUA;
    public static TextFormatting SHIFT_REMINDER_COLOR = TextFormatting.GRAY;

    public static TextFormatting SKILL_NAME_COLOR = TextFormatting.GREEN;
    public static TextFormatting SKILL_DESC_COLOR = TextFormatting.GRAY;

    public static void sacredLevelText(List<ITextComponent> tooltip) {
        tooltip.add(new TranslationTextComponent(BPItemSettings.SACRED_LEVEL).withStyle(BPItemSettings.TIER_LEVEL_COLOR));
        tooltip.add(new TranslationTextComponent(BPItemSettings.SHIFT_REMINDER).withStyle(BPItemSettings.SHIFT_REMINDER_COLOR));
    }

    public static void bossLevelText(List<ITextComponent> tooltip) {
        tooltip.add(new TranslationTextComponent(BPItemSettings.BOSS_LEVEL).withStyle(BPItemSettings.TIER_LEVEL_COLOR));
        tooltip.add(new TranslationTextComponent(BPItemSettings.SHIFT_REMINDER).withStyle(BPItemSettings.SHIFT_REMINDER_COLOR));
    }

    public static TextFormatting REACH_BONUS_COLOR = TextFormatting.AQUA;

    public static String REACH_BONUS = "tooltip.bioplethora.reach_bonus.desc";

    public static TextFormatting ANTIBIO_BONUS_COLOR = TextFormatting.YELLOW;

    public static String ECOHARMLESS_ENCH = "tooltip.bioplethora.antibio_ecoharmless.desc";
    public static String PLETHONEUTRAL_ENCH = "tooltip.bioplethora.antibio_plethoneutral.desc";
    public static String DANGERUM_ENCH = "tooltip.bioplethora.antibio_dangerum.desc";
    public static String HELLSENT_ENCH = "tooltip.bioplethora.antibio_hellsent.desc";
    public static String ELDERIA_ENCH = "tooltip.bioplethora.antibio_elderia.desc";

    public static TextFormatting NO_USE_COLOR = TextFormatting.RED;

    public static String NO_USE_YET = "tooltip.bioplethora.no_use.desc";
}
