package io.github.bioplethora.entity.ai;

import io.github.bioplethora.entity.SummonableMonsterEntity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.ai.goal.TargetGoal;

public class CopyTargetOwnerGoal extends TargetGoal {

    private final EntityPredicate copyOwnerTargeting = (new EntityPredicate()).allowUnseeable().ignoreInvisibilityTesting();
    private final SummonableMonsterEntity summonableMonster;

    public CopyTargetOwnerGoal(SummonableMonsterEntity summonableMonster) {
        super(summonableMonster, false);
        this.summonableMonster = summonableMonster;
    }

    public boolean canUse() {
        return this.summonableMonster.getOwner() != null && this.summonableMonster.getOwner().getTarget() != null && this.canAttack(this.summonableMonster.getOwner().getTarget(), this.copyOwnerTargeting);
    }

    public void start() {
        this.summonableMonster.setTarget(this.summonableMonster.getOwner().getTarget());
        super.start();
    }
}
