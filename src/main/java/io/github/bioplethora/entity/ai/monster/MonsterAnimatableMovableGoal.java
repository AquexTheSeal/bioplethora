package io.github.bioplethora.entity.ai.monster;

import io.github.bioplethora.entity.AnimatableMonsterEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.Path;

public abstract class MonsterAnimatableMovableGoal extends MonsterAnimatableGoal {

    protected Path path;

    @Override
    abstract public boolean canUse();

    protected boolean isExecutable(MonsterAnimatableMovableGoal goal, AnimatableMonsterEntity attacker, LivingEntity target) {
        if (target == null) return false;
        if (target.isAlive() && !target.isSpectator()) {
            if (target instanceof PlayerEntity && ((PlayerEntity) target).isCreative()) return false;

            double distance = goal.entity.distanceToSqr(target.getX(), target.getY(), target.getZ());
            goal.path = attacker.getNavigation().createPath(target, 0);

            return attacker.getSensing().canSee(target) && distance >= MonsterAnimatableGoal.getAttackReachSq(attacker, target);
        }
        return false;
    }
}
