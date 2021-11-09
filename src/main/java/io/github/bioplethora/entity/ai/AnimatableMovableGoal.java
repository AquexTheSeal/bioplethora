package io.github.bioplethora.entity.ai;

import io.github.bioplethora.entity.AnimatableHostileEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.Path;

public abstract class AnimatableMovableGoal extends AnimatableGoal {

    protected Path path;

    @Override
    abstract public boolean canUse();

    protected boolean isExecutable(AnimatableMovableGoal goal, AnimatableHostileEntity attacker, LivingEntity target) {
        if (target == null) return false;
        if (target.isAlive() && !target.isSpectator()) {
            if (target instanceof PlayerEntity && ((PlayerEntity) target).isCreative()) return false;

            double distance = goal.entity.distanceToSqr(target.getX(), target.getY(), target.getZ());
            goal.path = attacker.getNavigation().createPath(target, 0);

            return attacker.getSensing().canSee(target) && distance >= AnimatableGoal.getAttackReachSq(attacker, target);
        }
        return false;
    }
}
