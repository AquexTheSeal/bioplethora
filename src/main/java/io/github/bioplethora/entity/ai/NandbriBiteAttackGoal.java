package io.github.bioplethora.entity.ai;

import io.github.bioplethora.entity.BPMonsterEntity;
import io.github.bioplethora.entity.ai.monster.BPMonsterMeleeGoal;
import io.github.bioplethora.entity.creatures.NandbriEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.EntityPredicates;

public class NandbriBiteAttackGoal extends BPMonsterMeleeGoal {
    public NandbriEntity nandbri = (NandbriEntity) entity;
    public NandbriBiteAttackGoal(BPMonsterEntity entity, double animationLength, double attackBegin, double attackEnd) {
        super(entity, animationLength, attackBegin, attackEnd);
    }

    public static boolean checkIfValid(NandbriBiteAttackGoal goal, NandbriEntity attacker, LivingEntity target) {
        if(target == null) return false;
        if(target.isAlive() && !target.isSpectator()){
            if (target instanceof PlayerEntity && ((PlayerEntity) target).isCreative()) {
                attacker.setAttacking(false);
                return false;
            }

            if (attacker.attackPhase != 0) {
                return false;
            }

            if (attacker.getScratching()) {
                return false;
            }

            if (attacker.getSpitting()) {
                return false;
            }

            double distance = goal.nandbri.distanceToSqr(target.getX(), target.getY(), target.getZ());
            if (distance <= NandbriBiteAttackGoal.getAttackReachSqr(attacker, target)) return true;
        }
        attacker.setAttacking(false);
        return false;
    }

    protected static double getAttackReachSqr(BPMonsterEntity attacker, LivingEntity target) {
        return attacker.getBbWidth() * 2F * attacker.getBbWidth() * 2F + target.getBbWidth();
    }

    @Override
    public boolean canUse() {
        if(Math.random() < 0.1 && this.nandbri.getTarget() != null) return true;
        return NandbriBiteAttackGoal.checkIfValid(this, nandbri, this.nandbri.getTarget());
    }

    @Override
    public boolean canContinueToUse() {
        if(Math.random() < 0.1 && this.nandbri.getTarget() != null) return true;
        return NandbriBiteAttackGoal.checkIfValid(this, nandbri, this.nandbri.getTarget());
    }

    @Override
    public void start() {
        this.nandbri.setAttacking(true);
        this.nandbri.setAggressive(true);
        this.animationProgress = 0;
    }

    @Override
    public void stop() {
        LivingEntity target = this.nandbri.getTarget();
        if(!EntityPredicates.NO_CREATIVE_OR_SPECTATOR.test(target)) {
            this.nandbri.setTarget(null);
        }
        this.nandbri.setAttacking(false);
        this.nandbri.setAggressive(false);

        if(this.hasHit) {
            switchPhase();
        }

        this.hasHit = false;
        this.animationProgress = 0;
    }

    public void switchPhase() {
        if(this.nandbri.attackPhase == 0 && Math.random() < 0.2) {
            this.nandbri.attackPhase = 1;
        }
    }

    @Override
    public void tick() {
        this.baseTick();
        LivingEntity target = this.nandbri.getTarget();
        if (target != null) {
            if (this.attackPredicate.apply(this.animationProgress, this.animationLength) && !this.hasHit) {
                this.nandbri.lookAt(target, 30.0F, 30.0F);
                this.nandbri.doHurtTarget(target);
                this.hasHit = true;
            }

            if (this.animationProgress > this.animationLength) {
                this.animationProgress = 0;
                this.hasHit = false;

                switchPhase();
            }
        }
    }
}
