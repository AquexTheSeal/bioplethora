package io.github.bioplethora.entity.ai;

import io.github.bioplethora.entity.AnimatableHostileEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.EnumSet;
import java.util.function.BiFunction;

public class BellophgolemAnimatedSmashingGoal /*extends AnimatableGoal*/ {
    /*private final double animationLength;
    private final BiFunction<Double, Double, Boolean> attackPredicate;
    private boolean hasHit;
    public static double smashingCooldown = 400;*/

    /**
     * @param entity          Attacking entity
     * @param animationLength
     */
   /*public BellophgolemAnimatedSmashingGoal(AnimatableHostileEntity entity, double animationLength, double attackBegin, double attackEnd) {
        this.entity = entity;
        this.animationLength = animationLength;
        this.attackPredicate = (progress, length) -> attackBegin < progress / (length) && progress / (length) < attackEnd;
        this.setFlags(EnumSet.of(Flag.LOOK));
    }

    private static boolean checkIfValid(BellophgolemAnimatedSmashingGoal goal, AnimatableHostileEntity attacker, LivingEntity target) {
        if (smashingCooldown > 0) return false;
        if (target == null) return false;
        if (target.isAlive() && !target.isSpectator()) {
            if (target instanceof PlayerEntity && ((PlayerEntity) target).isCreative()) {
                attacker.setSmashing(false);
                return false;
            }
            double distance = goal.entity.distanceToSqr(target.getX(), target.getY(), target.getZ());
            if (distance <= AnimatableGoal.getAttackReachSq(attacker, target)) return true;
        }
        attacker.setSmashing(false);
        return false;
    }

    @Override
    public boolean canUse() {
        if (Math.random() <= 0.1) return false;
        return BellophgolemAnimatedSmashingGoal.checkIfValid(this, entity, this.entity.getTarget());
    }

    @Override
    public boolean canContinueToUse() {
        if (Math.random() <= 0.1) return true;
        return BellophgolemAnimatedSmashingGoal.checkIfValid(this, entity, this.entity.getTarget());
    }

    @Override
    public void start() {
        this.entity.setSmashing(true);
        this.entity.setAggressive(true);
        smashingCooldown = 400;
        this.animationProgress = 0;
    }

    @Override
    public void stop() {
        LivingEntity target = this.entity.getTarget();
        if (!EntityPredicates.NO_CREATIVE_OR_SPECTATOR.test(target)) {
            this.entity.setTarget(null);
        }
        this.entity.setSmashing(false);
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

            if (smashingCooldown > 0) {
                smashingCooldown = (double) (smashingCooldown - 1);
            }
        }
    }*/
}
