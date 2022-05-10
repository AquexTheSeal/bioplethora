package io.github.bioplethora.entity.ai;

import io.github.bioplethora.entity.BPMonsterEntity;
import io.github.bioplethora.entity.ai.gecko.GeckoMeleeGoal;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

public class CavernFleignarMeleeGoal extends GeckoMeleeGoal {

    public CavernFleignarMeleeGoal(BPMonsterEntity entity, double animationLength, double attackBegin, double attackEnd) {
        super(entity, animationLength, attackBegin, attackEnd);
    }

    public static boolean checkIfValid(CavernFleignarMeleeGoal goal, BPMonsterEntity attacker, LivingEntity target) {
        if (target == null) return false;
        if (target.isAlive() && !target.isSpectator()) {
            if (target instanceof PlayerEntity && ((PlayerEntity) target).isCreative()) {
                attacker.setAttacking(false);
                return false;
            }
            double distance = goal.entity.distanceToSqr(target);
            if (distance <= 16D) return true;
        }
        attacker.setAttacking(false);
        return false;
    }

    @Override
    public boolean canUse() {
        if (Math.random() <= 0.1) return false;

        return CavernFleignarMeleeGoal.checkIfValid(this, entity, this.entity.getTarget());
    }

    @Override
    public boolean canContinueToUse() {
        if (Math.random() <= 0.1) return true;

        return CavernFleignarMeleeGoal.checkIfValid(this, entity, this.entity.getTarget());
    }
}
