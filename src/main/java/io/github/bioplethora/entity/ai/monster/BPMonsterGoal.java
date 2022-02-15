package io.github.bioplethora.entity.ai.monster;

import io.github.bioplethora.entity.BPMonsterEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

import java.util.Random;

public abstract class BPMonsterGoal extends Goal {

    protected static final Random RANDOM = new Random();
    public BPMonsterEntity entity;
    protected long tickDelta;
    protected double animationProgress;
    private long lastGameTime;
    private boolean isFirsLoop = true;

    protected static double getAttackReachSq(BPMonsterEntity attacker, LivingEntity target) {
        return attacker.getBbWidth() * 2F * attacker.getBbWidth() * 2F + target.getBbWidth();
    }

    /**
     * Basic tick functionality that most AnimatableGoals will use
     */
    public void baseTick() {
        if (this.isFirsLoop) {
            this.isFirsLoop = false;
            this.animationProgress += 1;
            this.lastGameTime = this.entity.level.getGameTime();
            return;
        }
        this.tickDelta = this.entity.level.getGameTime() - this.lastGameTime;
        this.animationProgress += 1 + this.tickDelta / 100000.0;
        this.lastGameTime = this.entity.level.getGameTime();
    }

    @Override
    abstract public boolean canUse();
}
