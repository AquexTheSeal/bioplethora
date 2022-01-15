package io.github.bioplethora.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nullable;

public class BioplethoraDamageSources {

    public static DamageSource indirectCastration(Entity p_76354_0_, @Nullable Entity p_76354_1_) {
        return (new BioplethoraIndirectDamageSource("indirectCastration", p_76354_0_, p_76354_1_)).bypassArmor().setMagic();
    }

    static class BioplethoraIndirectDamageSource extends EntityDamageSource {

        public BioplethoraIndirectDamageSource(String p_i1568_1_, Entity p_i1568_2_, @Nullable Entity p_i1568_3_) {
            super(p_i1568_1_, p_i1568_2_);
        }

        @Nullable
        public Entity getDirectEntity() {
            return this.entity;
        }

        public ITextComponent getLocalizedDeathMessage(LivingEntity livingEntity) {
            LivingEntity killCredit = (LivingEntity) this.getDirectEntity();
            String s = "death.attack." + this.msgId;
            int variant = livingEntity.getRandom().nextInt(2);
            String s1 = s + "." + variant;
            String s2 = s + ".attacker_" + variant;
            return killCredit != null ? new TranslationTextComponent(s2, livingEntity.getDisplayName(), killCredit.getDisplayName()) : new TranslationTextComponent(s1, livingEntity.getDisplayName());
        }
    }
}
