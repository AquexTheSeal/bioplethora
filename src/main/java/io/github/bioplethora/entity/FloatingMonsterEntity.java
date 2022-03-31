package io.github.bioplethora.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;
import java.util.EnumSet;

/**
 * Must implement a.i. goals {@link MoveRandomGoal} and move controller {@link MoveHelperController} for the floating to work.
 * {@link ChargeAttackGoal} is optional.
 */
public abstract class FloatingMonsterEntity extends BPMonsterEntity implements IFlyingAnimal {
    public BlockPos boundOrigin;
    
    public FloatingMonsterEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    public abstract void registerControllers(AnimationData data);

    @Override
    public abstract AnimationFactory getFactory();

    public void readAdditionalSaveData(CompoundNBT pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.contains("BoundX")) {
            this.boundOrigin = new BlockPos(pCompound.getInt("BoundX"), pCompound.getInt("BoundY"), pCompound.getInt("BoundZ"));
        }
    }

    public void addAdditionalSaveData(CompoundNBT pCompound) {
        super.addAdditionalSaveData(pCompound);
        if (this.boundOrigin != null) {
            pCompound.putInt("BoundX", this.boundOrigin.getX());
            pCompound.putInt("BoundY", this.boundOrigin.getY());
            pCompound.putInt("BoundZ", this.boundOrigin.getZ());
        }
    }

    public void setBoundOrigin(@Nullable BlockPos pBoundOrigin) {
        this.boundOrigin = pBoundOrigin;
    }

    @Nullable
    public BlockPos getBoundOrigin() {
        return this.boundOrigin;
    }

    public boolean isNoGravity() {
        return true;
    }

    public boolean causeFallDamage(float distance, float damageMultiplier) {
        return false;
    }

    protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
    }

    public class ChargeAttackGoal extends Goal {
        public ChargeAttackGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        public boolean canUse() {
            if (FloatingMonsterEntity.this.getTarget() != null &&
                    !FloatingMonsterEntity.this.getMoveControl().hasWanted() &&
                    FloatingMonsterEntity.this.random.nextInt(3) == 0) {

                return FloatingMonsterEntity.this.distanceToSqr(FloatingMonsterEntity.this.getTarget()) > 2.0D;
            } else {
                return false;
            }
        }

        public boolean canContinueToUse() {
            return FloatingMonsterEntity.this.getMoveControl().hasWanted() &&
                    FloatingMonsterEntity.this.getTarget() != null &&
                    FloatingMonsterEntity.this.getTarget().isAlive();
        }

        public void start() {
            LivingEntity livingentity = FloatingMonsterEntity.this.getTarget();
            Vector3d vector3d = livingentity.getEyePosition(1.0F);
            FloatingMonsterEntity.this.moveControl.setWantedPosition(vector3d.x, vector3d.y, vector3d.z, 1.0D);
        }

        public void stop() {
        }

        public void tick() {
            LivingEntity livingentity = FloatingMonsterEntity.this.getTarget();

            if (!FloatingMonsterEntity.this.getBoundingBox().intersects(livingentity.getBoundingBox())) {
                double d0 = FloatingMonsterEntity.this.distanceToSqr(livingentity);
                if (d0 < 9.0D) {
                    Vector3d vector3d = livingentity.getEyePosition(1.0F);
                    FloatingMonsterEntity.this.moveControl.setWantedPosition(vector3d.x, vector3d.y, vector3d.z, 1.0D);
                }
            }
        }
    }

    public class MoveHelperController extends MovementController {
        public MoveHelperController(FloatingMonsterEntity floatingMob) {
            super(floatingMob);
        }

        public void tick() {
            if (this.operation == MovementController.Action.MOVE_TO) {
                Vector3d vector3d = new Vector3d(this.wantedX - FloatingMonsterEntity.this.getX(), this.wantedY - FloatingMonsterEntity.this.getY(), this.wantedZ - FloatingMonsterEntity.this.getZ());
                double d0 = vector3d.length();
                if (d0 < FloatingMonsterEntity.this.getBoundingBox().getSize()) {
                    this.operation = MovementController.Action.WAIT;
                    FloatingMonsterEntity.this.setDeltaMovement(FloatingMonsterEntity.this.getDeltaMovement().scale(0.5D));
                } else {
                    FloatingMonsterEntity.this.setDeltaMovement(FloatingMonsterEntity.this.getDeltaMovement().add(vector3d.scale(this.speedModifier * 0.05D / d0)));
                    if (FloatingMonsterEntity.this.getTarget() == null) {
                        Vector3d vector3d1 = FloatingMonsterEntity.this.getDeltaMovement();
                        FloatingMonsterEntity.this.yRot = -((float) MathHelper.atan2(vector3d1.x, vector3d1.z)) * (180F / (float)Math.PI);
                        FloatingMonsterEntity.this.yBodyRot = FloatingMonsterEntity.this.yRot;
                    } else {
                        double d2 = FloatingMonsterEntity.this.getTarget().getX() - FloatingMonsterEntity.this.getX();
                        double d1 = FloatingMonsterEntity.this.getTarget().getZ() - FloatingMonsterEntity.this.getZ();
                        FloatingMonsterEntity.this.yRot = -((float)MathHelper.atan2(d2, d1)) * (180F / (float)Math.PI);
                        FloatingMonsterEntity.this.yBodyRot = FloatingMonsterEntity.this.yRot;
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
            return !FloatingMonsterEntity.this.getMoveControl().hasWanted() && FloatingMonsterEntity.this.random.nextInt(7) == 0;
        }

        public boolean canContinueToUse() {
            return false;
        }

        public void tick() {
            BlockPos blockpos = FloatingMonsterEntity.this.getBoundOrigin();
            if (blockpos == null) {
                blockpos = FloatingMonsterEntity.this.blockPosition();
            }

            for(int i = 0; i < 3; ++i) {
                BlockPos blockpos1 = blockpos.offset(FloatingMonsterEntity.this.random.nextInt(15) - 7, FloatingMonsterEntity.this.random.nextInt(11) - 3, FloatingMonsterEntity.this.random.nextInt(15) - 7);
                if (FloatingMonsterEntity.this.level.isEmptyBlock(blockpos1)) {
                    FloatingMonsterEntity.this.moveControl.setWantedPosition((double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.5D, (double)blockpos1.getZ() + 0.5D, 0.25D);
                    if (FloatingMonsterEntity.this.getTarget() == null) {
                        FloatingMonsterEntity.this.getLookControl().setLookAt((double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.5D, (double)blockpos1.getZ() + 0.5D, 180.0F, 20.0F);
                    }
                    break;
                }
            }
        }
    }
}
