package io.github.bioplethora.entity.ai.goals;

import io.github.bioplethora.entity.creatures.GaugalemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.player.PlayerEntity;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class GaugalemTeleportToTargetGoal extends NearestAttackableTargetGoal<PlayerEntity> {
    private final GaugalemEntity gaugalem;
    private PlayerEntity pendingTarget;
    private int teleportTime;

    public GaugalemTeleportToTargetGoal(GaugalemEntity gaugalemEntity, @Nullable Predicate<LivingEntity> livingEntityPredicate) {
        super(gaugalemEntity, PlayerEntity.class, 10, false, false, livingEntityPredicate);
        this.gaugalem = gaugalemEntity;
    }

    public boolean canUse() {
        return this.pendingTarget != null;
    }

    public void start() {
        this.teleportTime = 0;
    }

    public void stop() {
        this.pendingTarget = null;
        super.stop();
    }

    public boolean canContinueToUse() {
        return this.pendingTarget == null;
    }

    public void tick() {
        if (this.gaugalem.getTarget() == null) {
            super.setTarget((LivingEntity)null);
        }

        if (this.pendingTarget != null) {
            this.target = this.pendingTarget;
            this.pendingTarget = null;
            super.start();
        } else {
            if (this.target != null && !this.gaugalem.isPassenger()) {
                if (this.target.distanceToSqr(this.gaugalem) < 16.0D) {
                    this.gaugalem.teleport();
                }

                this.teleportTime = 0;
            } else if (this.target.distanceToSqr(this.gaugalem) > 256.0D && this.teleportTime++ >= 30 && this.gaugalem.teleportTowards(this.target)) {
                this.teleportTime = 0;
            }

            super.tick();
        }

    }
}
