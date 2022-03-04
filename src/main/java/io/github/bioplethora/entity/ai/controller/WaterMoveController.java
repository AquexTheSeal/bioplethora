package io.github.bioplethora.entity.ai.controller;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

public class WaterMoveController extends MovementController {

    private final CreatureEntity entity;

    public WaterMoveController(CreatureEntity entity, double pSpeed) {
        super(entity);
        this.entity = entity;
        this.speedModifier = pSpeed;
    }

    public void tick() {
        if (this.operation == MovementController.Action.MOVE_TO) {
            Vector3d vector3d = new Vector3d(this.wantedX - entity.getX(), this.wantedY - entity.getY(), this.wantedZ - entity.getZ());
            double d0 = vector3d.length();
            if (d0 < entity.getBoundingBox().getSize()) {
                this.operation = MovementController.Action.WAIT;
                entity.setDeltaMovement(entity.getDeltaMovement().scale(0.5D));
            } else {
                entity.setDeltaMovement(entity.getDeltaMovement().add(vector3d.scale(this.speedModifier * 0.05D / d0)));
                if (entity.getTarget() == null) {
                    Vector3d vector3d1 = entity.getDeltaMovement();
                    entity.yRot = -((float) MathHelper.atan2(vector3d1.x, vector3d1.z)) * (180F / (float)Math.PI);
                    entity.yBodyRot = entity.yRot;
                } else {
                    double d2 = entity.getTarget().getX() - entity.getX();
                    double d1 = entity.getTarget().getZ() - entity.getZ();
                    entity.yRot = -((float)MathHelper.atan2(d2, d1)) * (180F / (float)Math.PI);
                    entity.yBodyRot = entity.yRot;
                }
            }
        }
    }
}
