package io.github.bioplethora.entity.ai.goals;

import io.github.bioplethora.entity.BPMonsterEntity;
import io.github.bioplethora.entity.creatures.AlphemKingEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.EntityPredicates;

public class AlphemKingSecondMeeleeGoal extends AlphemKingMeeleeGoal {

    public AlphemKingSecondMeeleeGoal(AlphemKingEntity entity, double animationLength, double attackBegin, double attackEnd) {
        super(entity, animationLength, attackBegin, attackEnd);
    }

    @Override
    public int attackPhaseReq() {
        return 1;
    }

    @Override
    public void doCIV(AlphemKingEntity attacker) {
        king.setAttacking2(false);
    }

    public double reachSq(AlphemKingEntity attacker, LivingEntity target) {
        return getAttackReachSq(attacker, target);
    }

    protected static double getAttackReachSq(BPMonsterEntity attacker, LivingEntity target) {
        return attacker.getBbWidth() * 2.5F * attacker.getBbWidth() * 2.5F + target.getBbWidth();
    }

    @Override
    public boolean canUse() {
        if (Math.random() <= 0.1) return false;

        return AlphemKingSecondMeeleeGoal.checkIfValid(this, king, this.king.getTarget());
    }

    @Override
    public void start() {
        isInAttackState = true;
        this.king.setAttacking2(true);
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
        this.king.setAttacking2(false);
        this.king.setAggressive(false);

        if (this.hasHit) {
            switchPhase();
        }

        this.hasHit = false;
        this.animationProgress = 0;
    }

    @Override
    public void switchPhase() {
        if (this.king.attackPhase == 1) {
            this.king.attackPhase = 2;
        } else {
            this.king.attackPhase = 0;
        }
    }

    @Override
    public void tick() {
        super.tick();
    }
}
