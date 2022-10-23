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
        if (armorRegenTimer == 60) {
            for (EquipmentSlotType type : EquipmentSlotType.values()) {
                if (type != EquipmentSlotType.MAINHAND && type != EquipmentSlotType.OFFHAND) {
                    pLivingEntity.getItemBySlot(type).setDamageValue(pLivingEntity.getItemBySlot(type).getDamageValue() + 2);
                }
            }

            armorRegenTimer = 0;
        }
        super.applyEffectTick(pLivingEntity, pAmplifier);
    }
}
