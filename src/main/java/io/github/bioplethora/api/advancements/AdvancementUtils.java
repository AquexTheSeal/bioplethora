package io.github.bioplethora.api.advancements;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;

public class AdvancementUtils {

    public static void grantBioAdvancement(Entity target, String advancement) {
        if (target instanceof ServerPlayerEntity) {
            Advancement adv = ((ServerPlayerEntity) target).server.getAdvancements().getAdvancement(new ResourceLocation(advancement));
            assert adv != null;
            AdvancementProgress advProg = ((ServerPlayerEntity) target).getAdvancements().getOrStartProgress(adv);
            if (!advProg.isDone()) {
                for (String s : advProg.getRemainingCriteria()) {
                    ((ServerPlayerEntity) target).getAdvancements().award(adv, s);
                }
            }
        }
    }
}
