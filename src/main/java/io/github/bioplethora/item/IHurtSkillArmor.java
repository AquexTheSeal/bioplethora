package io.github.bioplethora.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public interface IHurtSkillArmor {

    default void onUserHurtWithArmor(LivingEntity user, LivingEntity attacker, ItemStack stack) {
        if (getHurtAbilityCooldown() > 0) {
            if (stack.getOrCreateTag().getInt("abilityCD") <= 0) {
                hurtTrigger(user, attacker, stack);
                stack.getOrCreateTag().putInt("abilityCD", getHurtAbilityCooldown());
            }
        } else {
            hurtTrigger(user, attacker, stack);
        }
    }

    default void cdHelper(ItemStack stack) {
        if (stack.getOrCreateTag().getInt("abilityCD") > 0) {
            decreaseTagInt("abilityCD", stack);
        }
    }

    default void decreaseTagInt(String id, ItemStack stack) {
        stack.getOrCreateTag().putInt(id, stack.getOrCreateTag().getInt(id) - 1);
    }

    void hurtTrigger(LivingEntity user, LivingEntity attacker, ItemStack stack);

    int getHurtAbilityCooldown();
}
