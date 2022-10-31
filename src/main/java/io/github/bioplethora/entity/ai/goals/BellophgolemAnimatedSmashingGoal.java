package io.github.bioplethora.entity.ai.goals;

import io.github.bioplethora.entity.creatures.BellophgolemEntity;
import net.minecraft.entity.ai.goal.Goal;

public class BellophgolemAnimatedSmashingGoal extends Goal {

    private final BellophgolemEntity shachath;
    public int chargeTime;

    public BellophgolemAnimatedSmashingGoal(BellophgolemEntity shachathEntity) {
        this.shachath = shachathEntity;
    }

    public boolean canUse() {
        return this.shachath.getTarget() != null;
    }

    public void start() {
        this.chargeTime = 0;
    }

    public void stop() {
        this.shachath.setNoGravity(false);
    }

    /*public void tick() {
        LivingEntity target = this.shachath.getTarget();

        if (target.distanceToSqr(this.shachath) < 4096.0D && this.shachath.canSee(target)) {

            this.shachath.getLookControl().setLookAt(target, 30.0F, 30.0F);

            ++this.chargeTime;
            if (this.chargeTime == 160) {
                this.shachath.teleportWithEffect(this.shachath.getX(), this.shachath.getY() + 5, this.shachath.getZ());
                this.shachath.setNoGravity(true);
            }
            if (this.chargeTime == 200) {

                BlockPos blockpos = new BlockPos(this.shachath.getX(), this.shachath.getY() - 3, this.shachath.getZ());

                if (!this.shachath.level.getBlockState(blockpos).getMaterial().blocksMotion()) {
                    this.shachath.teleportWithEffect(this.shachath.getX(), this.shachath.getY() - 3, this.shachath.getZ());
                } else {
                    this.shachath.setDeltaMovement(0, -3, 0);
                }

                this.shachath.setNoGravity(false);
                this.chargeTime = 0;
            }
        } else {
            this.chargeTime = 0;
        }
        this.shachath.setQuickShooting(this.chargeTime > 160);
    }*/
}
