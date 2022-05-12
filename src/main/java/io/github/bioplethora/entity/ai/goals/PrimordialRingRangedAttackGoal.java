package io.github.bioplethora.entity.ai.goals;

import io.github.bioplethora.entity.others.PrimordialRingEntity;
import io.github.bioplethora.entity.projectile.BellophiteClusterEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class PrimordialRingRangedAttackGoal extends Goal {

    private final PrimordialRingEntity ring;
    public int chargeTime;

    public PrimordialRingRangedAttackGoal(PrimordialRingEntity ringEntity) {
        this.ring = ringEntity;
    }

    public boolean canUse() {
        return this.ring.getTarget() != null;
    }

    public void start() {
        this.chargeTime = 0;
    }

    public void stop() {
        this.ring.setCharging(false);
    }

    public void tick() {
        LivingEntity livingentity = this.ring.getTarget();
        if (livingentity.distanceToSqr(this.ring) < 4096.0D && this.ring.canSee(livingentity)) {
            World world = this.ring.level;

            this.ring.getLookControl().setLookAt(ring.getTarget(), 30.0F, 30.0F);

            ++this.chargeTime;
            if (this.chargeTime == 80 && !this.ring.isSilent()) {
                this.ring.level.playSound(null, this.ring.getX(), this.ring.getY(), this.ring.getZ(), SoundEvents.BEACON_ACTIVATE, this.ring.getSoundSource(), 1.0F, 1.0F + 1 * 0.2F);
            }
            if (this.chargeTime == 100) {

                Vector3d vector3d = this.ring.getViewVector(1.0F);
                double d1 = livingentity.getX() - this.ring.getX();
                double d2 = livingentity.getY(0.5D) - this.ring.getY(0.5D);
                double d3 = livingentity.getZ() - this.ring.getZ();
                if (!this.ring.isSilent()) {
                    this.ring.level.playSound(null, this.ring.getX(), this.ring.getY(), this.ring.getZ(), SoundEvents.SHULKER_SHOOT, this.ring.getSoundSource(), 1.0F, 1.0F + 1 * 0.2F);
                }

                BellophiteClusterEntity cluster = new BellophiteClusterEntity(world, this.ring, d1, d2, d3);
                cluster.setPos(this.ring.getX(), this.ring.getY(0.5D) + 0.5D, cluster.getZ());
                world.addFreshEntity(cluster);
            }
            if (this.chargeTime == 120) {
                this.chargeTime = 0;
            }
        }
        this.ring.setCharging(this.chargeTime >= 100);
    }
}
