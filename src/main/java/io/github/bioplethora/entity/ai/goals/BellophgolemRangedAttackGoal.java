package io.github.bioplethora.entity.ai.goals;

import io.github.bioplethora.config.BPConfig;
import io.github.bioplethora.entity.creatures.BellophgolemEntity;
import io.github.bioplethora.entity.projectile.BellophiteClusterEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class BellophgolemRangedAttackGoal extends Goal {

    private final BellophgolemEntity bellophgolem;
    public int chargeTime;

    public BellophgolemRangedAttackGoal(BellophgolemEntity bellophgolemEntity) {
        this.bellophgolem = bellophgolemEntity;
    }

    public boolean canUse() {
        return this.bellophgolem.getTarget() != null;
    }

    public void start() {
        this.chargeTime = 0;
    }

    public void stop() {
        this.bellophgolem.setCharging(false);
    }

    public void tick() {

        LivingEntity livingentity = this.bellophgolem.getTarget();

        assert livingentity != null;
        World world = this.bellophgolem.level;
        Vector3d vector3d = this.bellophgolem.getViewVector(1.0F);
        double x = this.bellophgolem.getX(), y = this.bellophgolem.getY(), z = this.bellophgolem.getZ();
        double targetX = livingentity.getX(), targetY = livingentity.getY(), targetZ = livingentity.getZ();
        BlockPos blockPos = new BlockPos((int) x, (int) y, (int) z);

        double d2 = targetX - (x + vector3d.x * 4.0D);
        double d3 = livingentity.getY(0.5D) - (0.5D + this.bellophgolem.getY(0.5D));
        double d4 = targetZ - (z + vector3d.z * 4.0D);

        BellophiteClusterEntity bellophiteClusterEntity = new BellophiteClusterEntity(world, this.bellophgolem, d2, d3, d4);
        bellophiteClusterEntity.setPos(x + vector3d.x * 4.0D, this.bellophgolem.getY(0.5D) + 0.5D, bellophiteClusterEntity.getZ() + vector3d.z * 4.0D);

        if (livingentity.distanceToSqr(this.bellophgolem) < 4096.0D && this.bellophgolem.canSee(livingentity)) {
            ++this.chargeTime;
            if (this.chargeTime == 10) {
                world.playSound(null, blockPos, SoundEvents.BEACON_ACTIVATE, SoundCategory.HOSTILE, (float) 1, (float) 1);
            }

            if (!BPConfig.COMMON.hellMode.get()) {
                if (this.chargeTime == 20) {
                    world.playSound(null, blockPos, SoundEvents.SHULKER_SHOOT, SoundCategory.HOSTILE, (float) 1, (float) 1);

                    world.addFreshEntity(bellophiteClusterEntity);
                    this.chargeTime = -40;
                }

                /* IF IN HELLMODE*/
            } else if (BPConfig.COMMON.hellMode.get()) {
                if (this.chargeTime == 20) {
                    world.playSound(null, blockPos, SoundEvents.SHULKER_SHOOT, SoundCategory.HOSTILE, (float) 1, (float) 1);

                   world.addFreshEntity(bellophiteClusterEntity);
                }

                if (this.chargeTime == 30) {
                    if (this.bellophgolem.getHealth() <= 100) {
                        world.addFreshEntity(bellophiteClusterEntity);
                    }
                    this.chargeTime = -40;
                }
            }
        } else {
            this.chargeTime = 0;
        }

        this.bellophgolem.setCharging(this.chargeTime > 10);
    }
}
