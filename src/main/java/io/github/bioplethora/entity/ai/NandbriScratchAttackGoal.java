package io.github.bioplethora.entity.ai;

import io.github.bioplethora.entity.BPMonsterEntity;
import io.github.bioplethora.entity.ai.monster.BPMonsterMeleeGoal;
import io.github.bioplethora.entity.creatures.NandbriEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.WallOrFloorItem;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class NandbriScratchAttackGoal extends BPMonsterMeleeGoal {
    public NandbriEntity nandbri = (NandbriEntity) entity;

    public NandbriScratchAttackGoal(BPMonsterEntity entity, double animationLength, double attackBegin, double attackEnd) {
        super(entity, animationLength, attackBegin, attackEnd);
    }

    public static boolean checkIfValid(NandbriScratchAttackGoal goal, NandbriEntity attacker, LivingEntity target) {
        if(target == null) return false;
        if(target.isAlive() && !target.isSpectator()){
            if (target instanceof PlayerEntity && ((PlayerEntity) target).isCreative()) {
                attacker.setScratching(false);
                return false;
            }

            if (attacker.attackPhase != 1) {
                return false;
            }

            if (attacker.getSpitting()) {
                return false;
            }

            double distance = goal.nandbri.distanceToSqr(target.getX(), target.getY(), target.getZ());
            if (distance <= NandbriScratchAttackGoal.getAttackReachSqr(attacker, target)) return true;
        }
        attacker.setScratching(false);
        return false;
    }

    protected static double getAttackReachSqr(BPMonsterEntity attacker, LivingEntity target) {
        return attacker.getBbWidth() * 2F * attacker.getBbWidth() * 2F + target.getBbWidth();
    }

    @Override
    public boolean canUse() {
        if(Math.random() < 0.1 && this.nandbri.getTarget() != null) return true;
        return NandbriScratchAttackGoal.checkIfValid(this, nandbri, this.nandbri.getTarget());
    }

    @Override
    public boolean canContinueToUse() {
        if(Math.random() < 0.1 && this.nandbri.getTarget() != null) return true;
        return NandbriScratchAttackGoal.checkIfValid(this, nandbri, this.nandbri.getTarget());
    }

    @Override
    public void start() {
        this.nandbri.setScratching(true);
        this.nandbri.setAggressive(true);
        this.animationProgress = 0;
    }

    @Override
    public void stop() {
        LivingEntity target = this.nandbri.getTarget();
        if(!EntityPredicates.NO_CREATIVE_OR_SPECTATOR.test(target)) {
            this.nandbri.setTarget(null);
        }
        this.nandbri.setScratching(false);
        this.nandbri.setAggressive(false);

        if(this.hasHit) {
            switchPhase();
        }

        this.hasHit = false;
        this.animationProgress = 0;
    }

    public void switchPhase() {
        if(this.nandbri.attackPhase == 1) {
            this.nandbri.attackPhase = 0;
        }
    }

    @Override
    public void tick() {
        World world = nandbri.level;
        this.baseTick();
        LivingEntity target = this.nandbri.getTarget();
        if (target != null) {
            if (this.attackPredicate.apply(this.animationProgress, this.animationLength) && !this.hasHit) {
                this.nandbri.lookAt(target, 30.0F, 30.0F);
                target.hurt(DamageSource.mobAttack(this.nandbri), 9.0F);
                target.knockback(2, 2, 2);
                world.playSound(null, this.nandbri, SoundEvents.PLAYER_ATTACK_SWEEP, SoundCategory.HOSTILE, 1, 1);

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
