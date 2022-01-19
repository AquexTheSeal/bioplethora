package io.github.bioplethora.entity.ai;

import io.github.bioplethora.entity.creatures.MyliothanEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class MyliothanChargeAttackGoal extends Goal {

    public final MyliothanEntity myliothan;
    public int chargeTime;

    public MyliothanChargeAttackGoal(MyliothanEntity myliothanEntity) {
        this.myliothan = myliothanEntity;
    }

    public boolean canUse() {
        return this.myliothan.getTarget() != null;
    }

    public void start() {
        this.chargeTime = 0;
    }

    public void stop() {
        this.myliothan.setCharging(false);
    }

    public void tick() {
        LivingEntity target = this.myliothan.getTarget();
        if (target.distanceToSqr(this.myliothan) < 4096.0D && this.myliothan.canSee(target)) {
            World world = this.myliothan.level;

            ++this.chargeTime;

            if (this.chargeTime >= 200) {

                this.myliothan.lookAt(target, 30.0F, 30.0F);

                this.myliothan.moveTargetPoint = new Vector3d(target.getX(), target.getY(0.5D), target.getZ());

                if (this.myliothan.getBoundingBox().inflate(2F).intersects(target.getBoundingBox())) {
                    if (!this.myliothan.isSilent()) {
                        this.myliothan.level.levelEvent(1039, this.myliothan.blockPosition(), 0);
                    }
                    this.myliothan.doHurtTarget(target);
                    world.explode(this.myliothan, target.getX(), target.getY(), target.getZ(), 3F, Explosion.Mode.BREAK);

                    this.chargeTime = 0;
                }
            } else if (this.chargeTime > 0) {
                --this.chargeTime;
            }
            if (this.chargeTime == 400) {
                this.chargeTime = 0;
            }
        }

        this.myliothan.setCharging(this.chargeTime > 200);
    }
}
