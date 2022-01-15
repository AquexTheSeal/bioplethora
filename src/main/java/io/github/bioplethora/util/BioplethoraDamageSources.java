package io.github.bioplethora.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IndirectEntityDamageSource;

import javax.annotation.Nullable;

public class BioplethoraDamageSources {

    public static DamageSource indirectCastration(Entity p_76354_0_, @Nullable Entity p_76354_1_) {
        return (new IndirectEntityDamageSource("indirectCastration", p_76354_0_, p_76354_1_)).bypassArmor().setMagic();
    }
}
