package io.github.bioplethora.entity.ai.goals;

import io.github.bioplethora.entity.BPMonsterEntity;
import io.github.bioplethora.entity.ai.gecko.GeckoDodgeableMeleeGoal;
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
        return attacker.getBbWidth() * 2F * attacker.getBbWidth() * 2F + target.getBbWidth();
    }

    @Override
    public boolean canUse() {
        if (Math.random() <= 0.1) return false;

        return checkIfValid(this, king, this.king.getTarget());
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
        isInAttackState = false;
        LivingEntity target = this.king.getTarget();
        if (!EntityPredicates.NO_CREATIVE_OR_SPECTATOR.test(target)) {
            this.king.setTarget(null);
        }
        this.king.setAttacking(false);
        this.king.setAggressive(false);

        if (this.hasHit) {
            switchPhase();
        }

        this.hasHit = false;
        this.animationProgress = 0;
    }

    public void switchPhase() {
        if (this.king.attackPhase == 0) {
            this.king.attackPhase = 1;
        }
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
                this.animationProgress = 0;
                this.hasHit = false;
                switchPhase();

                this.isInAttackState = false;
            }
        }
    }
}
