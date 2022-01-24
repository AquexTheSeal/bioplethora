package io.github.bioplethora.entity.ai;

import io.github.bioplethora.entity.creatures.BellophgolemEntity;
import net.minecraft.entity.ai.goal.Goal;

public class BellophgolemAnimatedSmashingGoal extends Goal {

    private final BellophgolemEntity helioblade;
    public int chargeTime;

    public BellophgolemAnimatedSmashingGoal(BellophgolemEntity heliobladeEntity) {
        this.helioblade = heliobladeEntity;
    }

    public boolean canUse() {
        return this.helioblade.getTarget() != null;
    }

    public void start() {
        this.chargeTime = 0;
    }

    public void stop() {
        this.helioblade.setNoGravity(false);
    }

    /*public void tick() {
        LivingEntity target = this.helioblade.getTarget();

        if (target.distanceToSqr(this.helioblade) < 4096.0D && this.helioblade.canSee(target)) {

            this.helioblade.getLookControl().setLookAt(target, 30.0F, 30.0F);

            ++this.chargeTime;
            if (this.chargeTime == 160) {
                this.helioblade.teleportWithEffect(this.helioblade.getX(), this.helioblade.getY() + 5, this.helioblade.getZ());
                this.helioblade.setNoGravity(true);
            }
            if (this.chargeTime == 200) {

                BlockPos blockpos = new BlockPos(this.helioblade.getX(), this.helioblade.getY() - 3, this.helioblade.getZ());

                if (!this.helioblade.level.getBlockState(blockpos).getMaterial().blocksMotion()) {
                    this.helioblade.teleportWithEffect(this.helioblade.getX(), this.helioblade.getY() - 3, this.helioblade.getZ());
                } else {
                    this.helioblade.setDeltaMovement(0, -3, 0);
                }

                this.helioblade.setNoGravity(false);
                this.chargeTime = 0;
            }
        } else {
            this.chargeTime = 0;
        }
        this.helioblade.setQuickShooting(this.chargeTime > 160);
    }*/
}
