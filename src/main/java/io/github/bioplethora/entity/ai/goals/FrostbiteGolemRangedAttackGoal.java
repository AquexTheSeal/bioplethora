package io.github.bioplethora.entity.ai.goals;

import io.github.bioplethora.config.BPConfig;
import io.github.bioplethora.entity.creatures.FrostbiteGolemEntity;
import io.github.bioplethora.entity.projectile.FrostbiteMetalClusterEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class FrostbiteGolemRangedAttackGoal extends Goal {

    private final FrostbiteGolemEntity frostbite_golem;
    public int chargeTime;

    public FrostbiteGolemRangedAttackGoal(FrostbiteGolemEntity frostbite_golemEntity) {
        this.frostbite_golem = frostbite_golemEntity;
    }

    public boolean canUse() {
        return this.frostbite_golem.getTarget() != null;
    }

    public void start() {
        this.chargeTime = 0;
    }

    public void stop() {
        this.frostbite_golem.setCharging(false);
    }

    public void tick() {

        LivingEntity livingentity = this.frostbite_golem.getTarget();

        assert livingentity != null;
        World world = this.frostbite_golem.level;
        Vector3d vector3d = this.frostbite_golem.getViewVector(1.0F);
        double x = this.frostbite_golem.getX(), y = this.frostbite_golem.getY(), z = this.frostbite_golem.getZ();
        double targetX = livingentity.getX(), targetY = livingentity.getY(), targetZ = livingentity.getZ();
        BlockPos blockPos = new BlockPos((int) x, (int) y, (int) z);

        double d2 = targetX - (x + vector3d.x * 4.0D);
        double d3 = livingentity.getY(0.5D) - (0.5D + this.frostbite_golem.getY(0.5D));
        double d4 = targetZ - (z + vector3d.z * 4.0D);

        FrostbiteMetalClusterEntity frostbite_metalClusterEntity = new FrostbiteMetalClusterEntity(world, this.frostbite_golem, d2, d3, d4);
        frostbite_metalClusterEntity.setPos(x + vector3d.x * 4.0D, this.frostbite_golem.getY(0.5D) + 0.5D, frostbite_metalClusterEntity.getZ() + vector3d.z * 4.0D);

        if (livingentity.distanceToSqr(this.frostbite_golem) < 4096.0D && this.frostbite_golem.canSee(livingentity)) {
            ++this.chargeTime;
            if (this.chargeTime == 10) {
                world.playSound(null, blockPos, SoundEvents.BEACON_ACTIVATE, SoundCategory.HOSTILE, (float) 1, (float) 1);
            }

            if (!BPConfig.COMMON.hellMode.get()) {
                if (this.chargeTime == 20) {
                    world.playSound(null, blockPos, SoundEvents.SHULKER_SHOOT, SoundCategory.HOSTILE, (float) 1, (float) 1);

                    world.addFreshEntity(frostbite_metalClusterEntity);
                    this.chargeTime = -40;
                }

                /* IF IN HELLMODE*/
            } else if (BPConfig.COMMON.hellMode.get()) {
                if (this.chargeTime == 20) {
                    world.playSound(null, blockPos, SoundEvents.SHULKER_SHOOT, SoundCategory.HOSTILE, (float) 1, (float) 1);

                   world.addFreshEntity(frostbite_metalClusterEntity);
                }

                if (this.chargeTime == 30) {
                    if (this.frostbite_golem.getHealth() <= 100) {
                        world.addFreshEntity(frostbite_metalClusterEntity);
                    }
                    this.chargeTime = -40;
                }
            }
        } else {
            this.chargeTime = 0;
        }

        this.frostbite_golem.setCharging(this.chargeTime > 10);
    }
}
