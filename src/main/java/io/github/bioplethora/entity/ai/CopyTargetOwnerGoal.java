package io.github.bioplethora.entity.ai;

import io.github.bioplethora.entity.SummonableMonsterEntity;
import io.github.bioplethora.entity.creatures.BellophgolemEntity;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.ai.goal.TargetGoal;

public class CopyTargetOwnerGoal extends TargetGoal {

    private final EntityPredicate copyOwnerTargeting = (new EntityPredicate()).allowUnseeable().ignoreInvisibilityTesting();
    private final SummonableMonsterEntity summonableMonster;

    public CopyTargetOwnerGoal(CreatureEntity creatureEntity, SummonableMonsterEntity summonableMonster) {
        super(creatureEntity, false);
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
