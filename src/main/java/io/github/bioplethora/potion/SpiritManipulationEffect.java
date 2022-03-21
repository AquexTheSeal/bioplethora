package io.github.bioplethora.potion;

import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class SpiritManipulationEffect extends Effect {

    int armorRegenTimer;

    public SpiritManipulationEffect(EffectType effectType, int potionParticleColor) {
        super(effectType, potionParticleColor);
        armorRegenTimer = 0;
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        ++armorRegenTimer;
        if (armorRegenTimer == 20) {
            pLivingEntity.getItemBySlot(EquipmentSlotType.HEAD).setDamageValue(pLivingEntity.getItemBySlot(EquipmentSlotType.HEAD).getDamageValue() + 3);
            pLivingEntity.getItemBySlot(EquipmentSlotType.CHEST).setDamageValue(pLivingEntity.getItemBySlot(EquipmentSlotType.CHEST).getDamageValue() + 3);
            pLivingEntity.getItemBySlot(EquipmentSlotType.LEGS).setDamageValue(pLivingEntity.getItemBySlot(EquipmentSlotType.LEGS).getDamageValue() + 3);
            pLivingEntity.getItemBySlot(EquipmentSlotType.FEET).setDamageValue(pLivingEntity.getItemBySlot(EquipmentSlotType.FEET).getDamageValue() + 3);

            System.out.println("regen test working! Test helmet durability: " + pLivingEntity.getItemBySlot(EquipmentSlotType.HEAD).getDamageValue());

            armorRegenTimer = 0;
        }
        super.applyEffectTick(pLivingEntity, pAmplifier);
    }
}
