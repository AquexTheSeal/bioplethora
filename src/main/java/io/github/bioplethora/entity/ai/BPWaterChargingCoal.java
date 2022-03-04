package io.github.bioplethora.entity.ai;

import io.github.bioplethora.entity.WaterAndLandAnimalEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.vector.Vector3d;

import java.util.EnumSet;

public class BPWaterChargingCoal extends Goal {

    public WaterAndLandAnimalEntity entity;

    public BPWaterChargingCoal(WaterAndLandAnimalEntity entity) {
        this.entity = entity;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    public boolean canUse() {
        if (entity.isInWater()) {
            if (entity.getTarget() != null &&
                    !entity.getMoveControl().hasWanted() &&
                    entity.getRandom().nextInt(3) == 0) {

                return entity.distanceToSqr(entity.getTarget()) > 2.0D;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean canContinueToUse() {
        if (entity.isInWater()) {
            return entity.getMoveControl().hasWanted() && entity.getTarget() != null && entity.getTarget().isAlive();
        } else {
            return false;
        }
    }

    public void start() {
        LivingEntity livingentity = entity.getTarget();
        Vector3d vector3d = livingentity.getEyePosition(1.0F);
        entity.getMoveControl().setWantedPosition(vector3d.x, vector3d.y, vector3d.z, 1.0D);
    }

    public void stop() {
    }

    public void tick() {
        LivingEntity livingentity = entity.getTarget();

        if (!entity.getBoundingBox().intersects(livingentity.getBoundingBox())) {
            double d0 = entity.distanceToSqr(livingentity);
            if (d0 < 9.0D) {
                Vector3d vector3d = livingentity.getEyePosition(1.0F);
                entity.getMoveControl().setWantedPosition(vector3d.x, vector3d.y, vector3d.z, 1.0D);
            }
        }
    }
}
