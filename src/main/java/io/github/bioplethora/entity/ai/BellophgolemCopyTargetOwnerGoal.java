package io.github.bioplethora.entity.ai;

import io.github.bioplethora.entity.BellophgolemEntity;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.monster.VexEntity;

public class BellophgolemCopyTargetOwnerGoal extends TargetGoal {

    private final EntityPredicate copyOwnerTargeting = (new EntityPredicate()).allowUnseeable().ignoreInvisibilityTesting();
    private final BellophgolemEntity bellophgolem;

    public BellophgolemCopyTargetOwnerGoal(CreatureEntity creatureEntity, BellophgolemEntity bellophgolemEntity) {
        super(creatureEntity, false);
        this.bellophgolem = bellophgolemEntity;
    }

    public boolean canUse() {
        return this.bellophgolem.getOwner() != null && this.bellophgolem.getOwner().getTarget() != null && this.canAttack(this.bellophgolem.getOwner().getTarget(), this.copyOwnerTargeting);
    }

    public void start() {
        this.bellophgolem.setTarget(this.bellophgolem.getOwner().getTarget());
        super.start();
    }
}
