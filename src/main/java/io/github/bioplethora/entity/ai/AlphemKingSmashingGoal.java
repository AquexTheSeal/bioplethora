package io.github.bioplethora.entity.ai;

import io.github.bioplethora.entity.BPMonsterEntity;
import io.github.bioplethora.entity.creatures.AlphemKingEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.EntityPredicates;

public class AlphemKingSmashingGoal extends AlphemKingMeeleeGoal {

    public AlphemKingSmashingGoal(BPMonsterEntity entity, double animationLength, double attackBegin, double attackEnd) {
        super(entity, animationLength, attackBegin, attackEnd);
    }

    public static boolean checkIfValid(AlphemKingSmashingGoal goal, AlphemKingEntity attacker, LivingEntity target) {
        if (target == null) return false;
        if (target.isAlive() && !target.isSpectator()) {

            if (target instanceof PlayerEntity && ((PlayerEntity) target).isCreative()) {
                attacker.setSmashing(false);
                return false;
            }

            if (attacker.attackPhase != 2) {
                return false;
            }

            double distance = goal.king.distanceToSqr(target.getX(), target.getY(), target.getZ());
            if (distance <= AlphemKingSmashingGoal.getAttackReachSq(attacker, target)) return true;
        }
        attacker.setSmashing(false);
        return false;
    }

    protected static double getAttackReachSq(BPMonsterEntity attacker, LivingEntity target) {
        return attacker.getBbWidth() * 3F * attacker.getBbWidth() * 3F + target.getBbWidth();
    }

    @Override
    public boolean canUse() {
        if (Math.random() <= 0.1) return false;

        return AlphemKingSmashingGoal.checkIfValid(this, king, this.king.getTarget());
    }

    @Override
    public boolean canContinueToUse() {
        if (Math.random() <= 0.1) return true;

        return AlphemKingSmashingGoal.checkIfValid(this, king, this.king.getTarget());
    }

    @Override
    public void start() {
        this.king.setSmashing(true);
        this.king.setAggressive(true);
        this.animationProgress = 0;
    }

    @Override
    public void stop() {
        LivingEntity target = this.king.getTarget();
        if (!EntityPredicates.NO_CREATIVE_OR_SPECTATOR.test(target)) {
            this.king.setTarget(null);
        }
        this.king.setSmashing(false);
        this.king.setAggressive(false);

        if (this.hasHit) {
            switchPhase();
        }

        this.hasHit = false;
        this.animationProgress = 0;
    }

    @Override
    public void switchPhase() {
        if (this.king.attackPhase == 2) {
            this.king.attackPhase = 0;
        }
    }

    @Override
    public void tick() {
        super.tick();
    }
}
