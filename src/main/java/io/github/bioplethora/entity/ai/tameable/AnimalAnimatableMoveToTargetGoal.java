package io.github.bioplethora.entity.ai.tameable;

import io.github.bioplethora.entity.AnimatableTameableEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.EntityPredicates;

import java.util.EnumSet;

public class AnimalAnimatableMoveToTargetGoal extends AnimalAnimatableMovableGoal {

    private final double speedMultiplier;
    private final int checkRate;

    /**
     * Move an AnimatableAnimalEntity to a target entity
     *
     * @param entity          AnimatableMonsterEntity instance
     * @param speedMultiplier Entity will move by base speed * this
     * @param checkRate       Check rate with formula: {@code if(RANDOM.nextInt(rate) == 0)}, so bigger = less often
     */
    public AnimalAnimatableMoveToTargetGoal(AnimatableTameableEntity entity, double speedMultiplier, int checkRate) {
        this.entity = entity;
        this.speedMultiplier = speedMultiplier;
        this.checkRate = checkRate;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (RANDOM.nextInt(this.checkRate) == 0) return false;

        return this.isExecutable(this, this.entity, this.entity.getTarget());
    }

    @Override
    public boolean canContinueToUse() {
        if (RANDOM.nextInt(this.checkRate) == 0) return true;

        return this.isExecutable(this, this.entity, this.entity.getTarget());
    }

    @Override
    public void start() {
        this.entity.setAggressive(true);
        this.entity.setMoving(true);
        this.entity.getNavigation().moveTo(this.path, this.speedMultiplier);
    }

    @Override
    public void stop() {
        LivingEntity target = this.entity.getTarget();
        if (!EntityPredicates.NO_CREATIVE_OR_SPECTATOR.test(target)) {
            this.entity.setTarget(null);
        }
        this.entity.setAggressive(false);
        this.entity.setMoving(false);
        this.entity.getNavigation().stop();
    }

    @Override
    public void tick() {
        LivingEntity target = this.entity.getTarget();
        if (target == null) return;

        this.entity.getLookControl().setLookAt(target, 30F, 30F);
        this.entity.getNavigation().moveTo(target, this.speedMultiplier);
    }
}
