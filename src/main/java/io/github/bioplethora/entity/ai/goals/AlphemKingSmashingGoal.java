package io.github.bioplethora.entity.ai.goals;

import io.github.bioplethora.entity.BPMonsterEntity;
import io.github.bioplethora.entity.ai.gecko.IGeckoBaseEntity;
import io.github.bioplethora.entity.creatures.AlphemKingEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.EntityPredicates;

public class AlphemKingSmashingGoal extends AlphemKingMeeleeGoal {

    public AlphemKingSmashingGoal(AlphemKingEntity entity, double animationLength, double attackBegin, double attackEnd) {
        super(entity, animationLength, attackBegin, attackEnd);
    }

    @Override
    public int attackPhaseReq() {
        return 2;
    }

    @Override
    public void doCIV(AlphemKingEntity attacker) {
        king.setSmashing(false);
    }

    public double reachSq(AlphemKingEntity attacker, LivingEntity target) {
        return getAttackReachSq(attacker, target);
    }

    public static boolean checkIfValid(AlphemKingSmashingGoal goal, AlphemKingEntity attacker, LivingEntity target) {
        if (target == null) return false;
        if (target.isAlive() && !target.isSpectator()) {
            if (!EntityPredicates.NO_CREATIVE_OR_SPECTATOR.test(target)) {
                attacker.setSmashing(false);
                return false;
            }
            if (attacker.attackPhase != goal.attackPhaseReq()) {
                return false;
            }
            if (attacker.getRoaring()) {
                return false;
            }
            if (attacker.isPursuit()) {
                return false;
            }
            double distance = goal.king.distanceToSqr(target.getX(), target.getY(), target.getZ());
            if (distance <= goal.reachSq(attacker, target)) return true;
        }
        attacker.setSmashing(false);
        return false;
    }

    public static double getAttackReachSq(BPMonsterEntity attacker, LivingEntity target) {
        return attacker.getBbWidth() * 3.5F * attacker.getBbWidth() * 3.5F + target.getBbWidth();
    }

    @Override
    public boolean canUse() {
        return AlphemKingSmashingGoal.checkIfValid(this, king, this.king.getTarget());
    }

    @Override
    public void start() {
        isInAttackState = true;
        this.king.setSmashing(true);
        this.king.setAggressive(true);
        this.animationProgress = 0;
    }

    @Override
    public void stop() {
        if (this.hasHit) {
            switchPhase();
            this.hasHit = false;
        }
        this.animationProgress = 0;
        this.isInAttackState = false;
        LivingEntity target = this.entity.getTarget();
        if (!EntityPredicates.NO_CREATIVE_OR_SPECTATOR.test(target)) {
            this.entity.setTarget(null);
        }
        king.setSmashing(false);
        this.entity.setAggressive(false);
    }

    @Override
    public void switchPhase() {
        this.king.attackPhase = 0;
    }
}
