package io.github.bioplethora.entity.ai.monster;

import io.github.bioplethora.entity.BPMonsterEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;

import java.util.EnumSet;
import java.util.function.BiFunction;

/**
 * Credits: WeirdNerd (Permission Granted)
 */
public class BPMonsterMeleeGoal extends BPMonsterGoal {
    public double animationLength;
    public BiFunction<Double, Double, Boolean> attackPredicate;
    public boolean hasHit;

    public BPMonsterMeleeGoal(BPMonsterEntity entity, double animationLength, double attackBegin, double attackEnd) {
        this.entity = entity;
        this.animationLength = animationLength;
        this.attackPredicate = (progress, length) -> attackBegin < progress / (length) && progress / (length) < attackEnd;
        this.setFlags(EnumSet.of(Flag.LOOK));
    }

    public static boolean checkIfValid(BPMonsterMeleeGoal goal, BPMonsterEntity attacker, LivingEntity target) {
        if (target == null) return false;
        if (target.isAlive() && !target.isSpectator()) {
            if (target instanceof PlayerEntity && ((PlayerEntity) target).isCreative()) {
                attacker.setAttacking(false);
                return false;
            }
            double distance = goal.entity.distanceToSqr(target.getX(), target.getY(), target.getZ());
            if (distance <= BPMonsterGoal.getAttackReachSq(attacker, target)) return true;
        }
        attacker.setAttacking(false);
        return false;
    }

    @Override
    public boolean canUse() {
        if (Math.random() <= 0.1) return false;

        return BPMonsterMeleeGoal.checkIfValid(this, entity, this.entity.getTarget());
    }

    @Override
    public boolean canContinueToUse() {
        if (Math.random() <= 0.1) return true;

        return BPMonsterMeleeGoal.checkIfValid(this, entity, this.entity.getTarget());
    }

    @Override
    public void start() {
        this.entity.setAttacking(true);
        this.entity.setAggressive(true);
        this.animationProgress = 0;
    }

    @Override
    public void stop() {
        LivingEntity target = this.entity.getTarget();
        if (!EntityPredicates.NO_CREATIVE_OR_SPECTATOR.test(target)) {
            this.entity.setTarget(null);
        }
        this.entity.setAttacking(false);
        this.entity.setAggressive(false);
        this.hasHit = false;
        this.animationProgress = 0;
    }

    @Override
    public void tick() {
        this.baseTick();
        LivingEntity target = this.entity.getTarget();
        if (target != null) {
            if (this.attackPredicate.apply(this.animationProgress, this.animationLength) && !this.hasHit) {
                this.entity.lookAt(target, 30.0F, 30.0F);
                this.entity.swing(Hand.MAIN_HAND);
                this.entity.doHurtTarget(target);
                this.hasHit = true;
            }

            if (this.animationProgress > this.animationLength) {
                this.animationProgress = 0;
                this.hasHit = false;
            }
        }
    }
}
