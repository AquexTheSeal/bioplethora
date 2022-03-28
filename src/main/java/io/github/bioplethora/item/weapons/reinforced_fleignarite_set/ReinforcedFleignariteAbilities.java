package io.github.bioplethora.item.weapons.reinforced_fleignarite_set;

import io.github.bioplethora.item.ItemSettings;
import io.github.bioplethora.registry.BPDamageSources;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;

import java.util.List;

public class ReinforcedFleignariteAbilities {

    public static void hitSkill(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof PlayerEntity) {
            if (!((PlayerEntity) attacker).getCooldowns().isOnCooldown(stack.getItem())) {
                defaultHitSkill(target, attacker);
                ((PlayerEntity) attacker).getCooldowns().addCooldown(stack.getItem(), 100);
            }
        } else {
            defaultHitSkill(target, attacker);
        }
    }

    public static void defaultHitSkill(LivingEntity target, LivingEntity attacker) {
        target.playSound(SoundEvents.ANVIL_PLACE, 1.0F, 0.75F);
        if (!target.level.isClientSide()) {
            ((ServerWorld) target.level).sendParticles(ParticleTypes.FIREWORK, target.getX(), target.getY(), target.getZ(), 75, 0.75, 0.75, 0.75, 0.01);
            ((ServerWorld) target.level).sendParticles(ParticleTypes.CRIT, target.getX(), target.getY(), target.getZ(), 75, 0.75, 0.75, 0.75, 0.01);
        }
        target.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, target.getRandom().nextBoolean() ? 70 : 90, 2, false, false, false));
        target.addEffect(new EffectInstance(Effects.DIG_SLOWDOWN, target.getRandom().nextBoolean() ? 60 : 80, 0, false, false, false));
        target.addEffect(new EffectInstance(Effects.CONFUSION, target.getRandom().nextBoolean() ? 80 : 60, 1, false, false, false));

        target.knockback(2, MathHelper.sin(attacker.yRot * ((float) Math.PI / 180F)), -MathHelper.cos(attacker.yRot * ((float) Math.PI / 180F)));
        target.hurt(BPDamageSources.armorPiercingFleignarite(attacker, attacker), (float) 5);
    }

    public static void regenerateItem(ItemStack stack, Entity entity) {

        if (entity instanceof LivingEntity) {
            if (((LivingEntity) entity).getMainHandItem() != stack && ((LivingEntity) entity).getOffhandItem() != stack) {
                if (stack.getDamageValue() < stack.getMaxDamage()) {
                    stack.getOrCreateTag().putInt("regen_time", stack.getOrCreateTag().getInt("regen_time") + 1);

                    if (stack.getOrCreateTag().getInt("regen_time") == 200) {
                        stack.setDamageValue(stack.getDamageValue() - 2);
                        stack.getOrCreateTag().putInt("regen_time", 0);
                    }
                }
            }
        }
    }

    public static void abilityTooltip(List<ITextComponent> tooltip) {
        ItemSettings.sacredLevelText(tooltip);

        tooltip.add(new TranslationTextComponent("item.bioplethora.reinforced_fleignarite_weapon.deadly_blow.skill").withStyle(ItemSettings.SKILL_NAME_COLOR));
        if (Screen.hasShiftDown() || Screen.hasControlDown()) {
            tooltip.add(new TranslationTextComponent("item.bioplethora.reinforced_fleignarite_weapon.deadly_blow.desc").withStyle(ItemSettings.SKILL_DESC_COLOR));
        }
    }
}
