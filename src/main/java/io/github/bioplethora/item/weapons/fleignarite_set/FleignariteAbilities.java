package io.github.bioplethora.item.weapons.fleignarite_set;

import io.github.bioplethora.api.BPItemSettings;
import io.github.bioplethora.registry.BPDamageSources;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.List;

public class FleignariteAbilities {

    public static void hitSkill(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof PlayerEntity) {
            if (!((PlayerEntity) attacker).getCooldowns().isOnCooldown(stack.getItem())) {
                defaultHitSkill(target, attacker);
                ((PlayerEntity) attacker).getCooldowns().addCooldown(stack.getItem(), 60);
            }
        } else {
            defaultHitSkill(target, attacker);
        }
    }

    public static void defaultHitSkill(LivingEntity target, LivingEntity attacker) {
        target.playSound(SoundEvents.SLIME_BLOCK_BREAK, 1.0F, 1.0F);
        target.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, target.getRandom().nextBoolean() ? 40 : 60, 1, false, false, false));
        target.addEffect(new EffectInstance(Effects.CONFUSION, target.getRandom().nextBoolean() ? 60 : 40, 0, false, false, false));

        target.hurt(BPDamageSources.armorPiercingFleignarite(attacker, attacker), (float) 2);
    }

    public static void regenerateItem(ItemStack stack, Entity entity) {

        if (entity instanceof LivingEntity) {
            if (((LivingEntity) entity).getMainHandItem() != stack && ((LivingEntity) entity).getOffhandItem() != stack) {
                if (stack.getDamageValue() < stack.getMaxDamage()) {
                    stack.getOrCreateTag().putInt("regen_time", stack.getOrCreateTag().getInt("regen_time") + 1);

                    if (stack.getOrCreateTag().getInt("regen_time") == 400) {
                        int i = Math.min((int) (1 * stack.getXpRepairRatio()), stack.getDamageValue());
                        stack.setDamageValue(stack.getDamageValue() - i);
                        stack.getOrCreateTag().putInt("regen_time", 0);
                    }
                }
            }
        }
    }

    public static void abilityTooltip(List<ITextComponent> tooltip) {
        BPItemSettings.sacredLevelText(tooltip);

        tooltip.add(new TranslationTextComponent("item.bioplethora.fleignarite_weapon.gooey_stun.skill").withStyle(BPItemSettings.SKILL_NAME_COLOR));
        if (Screen.hasShiftDown() || Screen.hasControlDown()) {
            tooltip.add(new TranslationTextComponent("item.bioplethora.fleignarite_weapon.gooey_stun.desc").withStyle(BPItemSettings.SKILL_DESC_COLOR));
        }
    }
}
