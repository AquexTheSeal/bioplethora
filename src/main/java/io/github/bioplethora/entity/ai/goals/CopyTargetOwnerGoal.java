package io.github.bioplethora.entity.ai.goals;

import io.github.bioplethora.entity.SummonableMonsterEntity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.TargetGoal;

public class CopyTargetOwnerGoal extends TargetGoal {

    private final EntityPredicate copyOwnerTargeting = (new EntityPredicate()).allowUnseeable().ignoreInvisibilityTesting();
    private final SummonableMonsterEntity summonableMonster;

    public CopyTargetOwnerGoal(SummonableMonsterEntity summonableMonster) {
        super(summonableMonster, false);
        this.summonableMonster = summonableMonster;
    }

    public boolean canUse() {
        return this.summonableMonster.getOwner() != null && this.summonableMonster.getOwner() instanceof MobEntity &&
                ((MobEntity) this.summonableMonster.getOwner()).getTarget() != null &&
                this.canAttack(((MobEntity) this.summonableMonster.getOwner()).getTarget(), this.copyOwnerTargeting);
    }

    public void start() {
        this.summonableMonster.setTarget(((MobEntity) this.summonableMonster.getOwner()).getTarget());
        super.start();
    }
}
