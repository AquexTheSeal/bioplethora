package io.github.bioplethora.registry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nullable;

public class BioplethoraDamageSources {

    public static DamageSource indirectCastration(Entity entity, @Nullable Entity source) {
        return (new BioplethoraIndirectDamageSource("indirectCastration", entity, source)).bypassArmor().setMagic().setExplosion();
    }

    public static DamageSource helioSlashed(Entity entity, @Nullable Entity source) {
        return (new BioplethoraIndirectDamageSource("helioSlashed", entity, source)).bypassArmor().setMagic();
    }

    public static DamageSource antibio(Entity entity, @Nullable Entity source) {
        return (new BioplethoraIndirectDamageSource("antibio", entity, source)).setMagic();
    }

    public static DamageSource armorPiercingFleignarite(Entity entity, @Nullable Entity source) {
        return (new BioplethoraIndirectDamageSource("armorPiercingFleignarite", entity, source)).bypassArmor();
    }

    static class BioplethoraIndirectDamageSource extends EntityDamageSource {

        Entity indirectOwner;
        public BioplethoraIndirectDamageSource(String name, Entity entity, @Nullable Entity source) {
            super(name, entity);
            indirectOwner = entity;
        }

        @Nullable
        public Entity getDirectEntity() {
            return this.entity;
        }

        public ITextComponent getLocalizedDeathMessage(LivingEntity livingEntity) {
            LivingEntity killCredit = (LivingEntity) indirectOwner;
            String s = "death.attack." + this.msgId;
            int variant = livingEntity.getRandom().nextInt(2);
            String s1 = s + "." + variant;
            String s2 = s + ".indirect_" + variant;
            return killCredit != null ? new TranslationTextComponent(s2, livingEntity.getDisplayName(), killCredit.getDisplayName()) : new TranslationTextComponent(s1, livingEntity.getDisplayName());
        }
    }
}
