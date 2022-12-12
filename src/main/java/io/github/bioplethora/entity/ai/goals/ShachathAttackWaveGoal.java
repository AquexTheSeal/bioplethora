package io.github.bioplethora.entity.ai.goals;

import io.github.bioplethora.entity.BPMonsterEntity;
import io.github.bioplethora.entity.ai.gecko.GeckoDodgeableMeleeGoal;
import io.github.bioplethora.entity.ai.gecko.GeckoGoal;
import io.github.bioplethora.entity.ai.gecko.IGeckoBaseEntity;
import io.github.bioplethora.entity.creatures.ShachathEntity;
import io.github.bioplethora.entity.creatures.ShachathEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;

import java.util.EnumSet;

public class ShachathAttackWaveGoal<E extends ShachathEntity> extends GeckoDodgeableMeleeGoal<E> {

    public int requiredWave;

    public ShachathAttackWaveGoal(E entity, double animationLength, double attackBegin, double attackEnd) {
        super(entity, animationLength, attackBegin, attackEnd);
    }

    public int attackPhaseReq() {
        return 0;
    }

    public void doCIV(ShachathEntity attacker) {
        setWhat(entity, false);
    }

    public double reachSq(ShachathEntity attacker, LivingEntity target) {
        return getAttackReachSq(attacker, target);
    }

    public static boolean checkIfValid(ShachathAttackWaveGoal<ShachathEntity> goal, ShachathEntity attacker, LivingEntity target) {
        if (target == null) return false;
        if (target.isAlive() && !target.isSpectator()) {
            if (target instanceof PlayerEntity && ((PlayerEntity) target).isCreative()) {
               goal.setWhat(attacker, false);
                return false;
            }
            if (goal.attackPhaseReq() != attacker.attackPhase) {
                return false;
            }
            if (attacker.isQuickShooting()) {
                return false;
            }
            if (target.getBoundingBox().inflate(4, 4, 4).intersects(attacker.getBoundingBox().inflate(2, 2, 2))) {
                return true;
            }
        }
        goal.setWhat(attacker, false);
        return false;
    }

    protected static double getAttackReachSq(BPMonsterEntity attacker, LivingEntity target) {
        return 0;
    }

    @Override
    public boolean canUse() {
        return ShachathAttackWaveGoal.checkIfValid((ShachathAttackWaveGoal) this, entity, this.entity.getTarget());
    }

    @Override
    public boolean canContinueToUse() {
        return isInAttackState && this.entity.getTarget() != null && !this.entity.isQuickShooting();
    }

    @Override
    public void start() {
        isInAttackState = true;
        setWhat(entity, true);
        this.entity.setAggressive(true);
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
        setWhat(entity, false);
        this.entity.setAggressive(false);
    }

    public void switchPhase() {
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
            return target.getBoundingBox().inflate(4, 4, 4).intersects(entity.getBoundingBox().inflate(2, 2, 2));
        }
        return false;
    }

    public static class Wave1 extends ShachathAttackWaveGoal<ShachathEntity> {

        public Wave1(ShachathEntity entity, double animationLength, double attackBegin, double attackEnd) {
            super(entity, animationLength, attackBegin, attackEnd);
        }

        @Override
        public int attackPhaseReq() {
            return 0;
        }

        @Override
        public void setWhat(IGeckoBaseEntity entity, boolean value) {
            entity.setAttacking(value);
        }

        @Override
        public void switchPhase() {
            this.entity.attackPhase = 1;
        }
    }

    public static class Wave2 extends ShachathAttackWaveGoal<ShachathEntity> {

        public Wave2(ShachathEntity entity, double animationLength, double attackBegin, double attackEnd) {
            super(entity, animationLength, attackBegin, attackEnd);
        }

        @Override
        public int attackPhaseReq() {
            return 1;
        }

        @Override
        public void setWhat(IGeckoBaseEntity entity, boolean value) {
            ((ShachathEntity) entity).setAttacking2(value);
        }

        @Override
        public void switchPhase() {
            this.entity.attackPhase = 0;
        }
    }
}
