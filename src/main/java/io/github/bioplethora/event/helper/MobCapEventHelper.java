package io.github.bioplethora.event.helper;

import io.github.bioplethora.BioplethoraConfig;
import io.github.bioplethora.entity.IMobCappedEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class MobCapEventHelper {

    public static void onEntityHurt(LivingHurtEvent event) {
        LivingEntity victim = event.getEntityLiving();

        if (BioplethoraConfig.COMMON.allowMobCaps.get()) {
            if (victim instanceof IMobCappedEntity) {
                if (event.getSource() != DamageSource.OUT_OF_WORLD) {
                    if (event.getAmount() > ((IMobCappedEntity) victim).getMaxDamageCap()) {
                        event.setAmount(((IMobCappedEntity) victim).getMaxDamageCap());
                    }
                }
            }
        }
    }
}
