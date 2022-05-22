package io.github.bioplethora.entity.ai.gecko;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.Path;

/**
 * Credits: WeirdNerd (Permission Granted)
 */
public abstract class GeckoMovableGoal<E extends MobEntity> extends GeckoGoal<E> {

    protected Path path;

    @Override
    abstract public boolean canUse();

    protected boolean isExecutable(GeckoMovableGoal goal, E attacker, LivingEntity target) {
        if (target == null) return false;
        if (target.isAlive() && !target.isSpectator()) {
            if (target instanceof PlayerEntity && ((PlayerEntity) target).isCreative()) return false;

            double distance = goal.entity.distanceToSqr(target.getX(), target.getY(), target.getZ());
            goal.path = attacker.getNavigation().createPath(target, 0);

            return attacker.getSensing().canSee(target) && distance >= GeckoGoal.getAttackReachSq(attacker, target);
        }
        return false;
    }
}
