package io.github.bioplethora.entity.ai.gecko;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.Hand;

/**
 * Credits: WeirdNerd (Permission Granted)
 */
public class GeckoDodgeableMeleeGoal<E extends MobEntity> extends GeckoMeleeGoal<E> {

     public boolean isInAttackState;

    public GeckoDodgeableMeleeGoal(E entity, double animationLength, double attackBegin, double attackEnd) {
        super(entity, animationLength, attackBegin, attackEnd);
    }

    @Override
    public boolean canContinueToUse() {
        return isInAttackState;
    }

    @Override
    public void start() {
        isInAttackState = true;
        super.start();
    }

    @Override
    public void stop() {
        isInAttackState = false;
        super.stop();
    }

    @Override
    public void tick() {
        this.baseTick();
        LivingEntity target = this.entity.getTarget();
        if (target != null) {
            if (this.attackPredicate.apply(this.animationProgress, this.animationLength) && !this.hasHit) {
                this.entity.lookAt(target, 30.0F, 30.0F);
                this.entity.swing(Hand.MAIN_HAND);
                if (canReachTarget()) {
                    this.entity.doHurtTarget(target);
                }
                this.hasHit = true;
            }

            if (this.animationProgress > this.animationLength) {
                this.animationProgress = 0;
                this.hasHit = false;
                this.isInAttackState = false;
            }
        }
    }

    public boolean canReachTarget() {
        LivingEntity target = entity.getTarget();
        if (target == null) return false;

        if (target.isAlive() && !target.isSpectator()) {
            double distance = entity.distanceToSqr(target.getX(), target.getY(), target.getZ());
            return distance <= GeckoGoal.getAttackReachSq(entity, target);
        }
        return false;
    }
}
