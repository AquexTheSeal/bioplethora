package io.github.bioplethora.helpers.world;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

public class EntityUtils {

    public static void knockbackAwayFromUser(float force, LivingEntity user, LivingEntity target) {
        target.knockback(force, MathHelper.sin(user.yRot * ((float) Math.PI / 180F)), -MathHelper.cos(user.yRot * ((float) Math.PI / 180F)));
    }
}
