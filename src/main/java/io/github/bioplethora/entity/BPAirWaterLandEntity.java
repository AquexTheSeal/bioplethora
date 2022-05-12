package io.github.bioplethora.entity;

import io.github.bioplethora.entity.ai.controller.WaterMoveController;
import io.github.bioplethora.entity.ai.goals.BPCustomSwimmingGoal;
import io.github.bioplethora.entity.ai.goals.BPWaterChargingCoal;
import io.github.bioplethora.entity.ai.navigator.WaterAndLandPathNavigator;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.EnumSet;

public abstract class BPAirWaterLandEntity extends WaterAndLandAnimalEntity {
    
    public BPAirWaterLandEntity(EntityType<? extends TameableEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new BPWaterChargingCoal(this));
        this.goalSelector.addGoal(2, new BPCustomSwimmingGoal(this));
    }

    @Override
    public void switchNavigator(boolean onLand) {
        if (onLand) {
            this.moveControl = new BPAirWaterLandEntity.MoveHelperController(this);
            this.navigation = new GroundPathNavigator(this, level);
            this.isLandNavigator = true;
        } else {
            this.moveControl = new WaterMoveController(this, 1.2F);
            this.navigation = new WaterAndLandPathNavigator(this, level);
            this.isLandNavigator = false;
        }
    }

    public boolean isNoGravity() {
        return true;
    }

    public class ChargeAttackGoal extends Goal {
        public ChargeAttackGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        public boolean canUse() {
            if (BPAirWaterLandEntity.this.getTarget() != null &&
                    !BPAirWaterLandEntity.this.getMoveControl().hasWanted() &&
                    BPAirWaterLandEntity.this.random.nextInt(3) == 0) {

                return BPAirWaterLandEntity.this.distanceToSqr(BPAirWaterLandEntity.this.getTarget()) > 2.0D;
            } else {
                return false;
            }
        }

        public boolean canContinueToUse() {
            return BPAirWaterLandEntity.this.getMoveControl().hasWanted() &&
                    BPAirWaterLandEntity.this.getTarget() != null &&
                    BPAirWaterLandEntity.this.getTarget().isAlive();
        }

        public void start() {
            LivingEntity livingentity = BPAirWaterLandEntity.this.getTarget();
            Vector3d vector3d = livingentity.getEyePosition(1.0F);
            BPAirWaterLandEntity.this.moveControl.setWantedPosition(vector3d.x, vector3d.y, vector3d.z, 1.0D);
        }

        public void stop() {
        }

        public void tick() {
            LivingEntity livingentity = BPAirWaterLandEntity.this.getTarget();

            if (!BPAirWaterLandEntity.this.getBoundingBox().intersects(livingentity.getBoundingBox())) {
                double d0 = BPAirWaterLandEntity.this.distanceToSqr(livingentity);
                if (d0 < 9.0D) {
                    Vector3d vector3d = livingentity.getEyePosition(1.0F);
                    BPAirWaterLandEntity.this.moveControl.setWantedPosition(vector3d.x, vector3d.y, vector3d.z, 1.0D);
                }
            }
        }
    }

    public class MoveHelperController extends MovementController {
        public MoveHelperController(BPAirWaterLandEntity floatingMob) {
            super(floatingMob);
        }

        public void tick() {
            if (this.operation == MovementController.Action.MOVE_TO) {
                Vector3d vector3d = new Vector3d(this.wantedX - BPAirWaterLandEntity.this.getX(), this.wantedY - BPAirWaterLandEntity.this.getY(), this.wantedZ - BPAirWaterLandEntity.this.getZ());
                double d0 = vector3d.length();
                if (d0 < BPAirWaterLandEntity.this.getBoundingBox().getSize()) {
                    this.operation = MovementController.Action.WAIT;
                    BPAirWaterLandEntity.this.setDeltaMovement(BPAirWaterLandEntity.this.getDeltaMovement().scale(0.5D));
                } else {
                    BPAirWaterLandEntity.this.setDeltaMovement(BPAirWaterLandEntity.this.getDeltaMovement().add(vector3d.scale(this.speedModifier * 0.05D / d0)));
                    if (BPAirWaterLandEntity.this.getTarget() == null) {
                        Vector3d vector3d1 = BPAirWaterLandEntity.this.getDeltaMovement();
                        BPAirWaterLandEntity.this.yRot = -((float) MathHelper.atan2(vector3d1.x, vector3d1.z)) * (180F / (float)Math.PI);
                        BPAirWaterLandEntity.this.yBodyRot = BPAirWaterLandEntity.this.yRot;
                    } else {
                        double d2 = BPAirWaterLandEntity.this.getTarget().getX() - BPAirWaterLandEntity.this.getX();
                        double d1 = BPAirWaterLandEntity.this.getTarget().getZ() - BPAirWaterLandEntity.this.getZ();
                        BPAirWaterLandEntity.this.yRot = -((float) MathHelper.atan2(d2, d1)) * (180F / (float)Math.PI);
                        BPAirWaterLandEntity.this.yBodyRot = BPAirWaterLandEntity.this.yRot;
                    }
                }
            }
        }
    }

    public class MoveRandomGoal extends Goal {
        public MoveRandomGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        public boolean canUse() {
            return !BPAirWaterLandEntity.this.getMoveControl().hasWanted() && BPAirWaterLandEntity.this.random.nextInt(5) == 0;
        }

        public boolean canContinueToUse() {
            return false;
        }

        public void tick() {
            BlockPos blockpos = BPAirWaterLandEntity.this.getBoundOrigin();
            if (blockpos == null) {
                blockpos = BPAirWaterLandEntity.this.blockPosition();
            }

            for(int i = 0; i < 3; ++i) {
                BlockPos blockpos1 = blockpos.offset(BPAirWaterLandEntity.this.random.nextInt(15) - 7, BPAirWaterLandEntity.this.random.nextInt(11) - 5, BPAirWaterLandEntity.this.random.nextInt(15) - 7);
                if (BPAirWaterLandEntity.this.level.isEmptyBlock(blockpos1)) {
                    BPAirWaterLandEntity.this.moveControl.setWantedPosition((double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.5D, (double)blockpos1.getZ() + 0.5D, 0.25D);
                    if (BPAirWaterLandEntity.this.getTarget() == null) {
                        BPAirWaterLandEntity.this.getLookControl().setLookAt((double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.5D, (double)blockpos1.getZ() + 0.5D, 180.0F, 20.0F);
                    }
                    break;
                }
            }
        }
    }
}
