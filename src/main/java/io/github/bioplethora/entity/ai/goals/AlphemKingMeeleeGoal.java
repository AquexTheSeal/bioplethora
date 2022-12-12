package io.github.bioplethora.entity.ai.goals;

import io.github.bioplethora.entity.BPMonsterEntity;
import io.github.bioplethora.entity.ai.gecko.GeckoDodgeableMeleeGoal;
import io.github.bioplethora.entity.ai.gecko.GeckoGoal;
import io.github.bioplethora.entity.ai.gecko.IGeckoBaseEntity;
import io.github.bioplethora.entity.creatures.AlphemKingEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;

public class AlphemKingMeeleeGoal extends GeckoDodgeableMeleeGoal<AlphemKingEntity> {
    
    public AlphemKingEntity king = entity;

    public AlphemKingMeeleeGoal(AlphemKingEntity entity, double animationLength, double attackBegin, double attackEnd) {
        super(entity, animationLength, attackBegin, attackEnd);
    }

    public int attackPhaseReq() {
        return 0;
    }

    public void doCIV(AlphemKingEntity attacker) {
        attacker.setAttacking(false);
    }

    public double reachSq(AlphemKingEntity attacker, LivingEntity target) {
        return getAttackReachSq(attacker, target);
    }

    public static boolean checkIfValid(AlphemKingMeeleeGoal goal, AlphemKingEntity attacker, LivingEntity target) {
        if (target == null) return false;
        if (target.isAlive() && !target.isSpectator()) {
            if (target instanceof PlayerEntity && ((PlayerEntity) target).isCreative()) {
                goal.doCIV(attacker);
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
        goal.doCIV(attacker);
        return false;
    }

    protected static double getAttackReachSq(BPMonsterEntity attacker, LivingEntity target) {
        return attacker.getBbWidth() * 3F * attacker.getBbWidth() * 3F + target.getBbWidth();
    }

    @Override
    public boolean canUse() {
        return checkIfValid(this, king, this.king.getTarget());
    }

    @Override
    public boolean canContinueToUse() {
        return isInAttackState && this.entity.getTarget() != null && !this.entity.isPursuit();
    }

    @Override
    public void start() {
        isInAttackState = true;
        this.king.setAttacking(true);
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
        king.setAttacking(false);
        this.entity.setAggressive(false);
    }

    public void switchPhase() {
        this.king.attackPhase = 1;
    }

    @Override
    public void tick() {
        this.baseTick();
        LivingEntity target = this.king.getTarget();
        if (target != null) {
            if (this.attackPredicate.apply(this.animationProgress, this.animationLength) && !this.hasHit) {
                this.king.lookAt(target, 30.0F, 30.0F);
                this.king.swing(Hand.MAIN_HAND);
                if (canReachTarget()) {
                    this.king.doHurtTarget(target);
                }
                this.hasHit = true;
            }

            if (this.animationProgress > this.animationLength) {
                switchPhase();
                this.animationProgress = 0;
                this.hasHit = false;
                this.isInAttackState = false;
            }
        }
    }

    @Override
    public boolean canReachTarget() {
        LivingEntity target = entity.getTarget();
        if (target == null) return false;

        if (target.isAlive() && !target.isSpectator()) {
            double distance = entity.distanceToSqr(target.getX(), target.getY(), target.getZ());
            return distance <= reachSq(entity, target);
        }
        return false;
    }
}
