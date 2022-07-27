package io.github.bioplethora.entity.creatures;

import io.github.bioplethora.config.BPConfig;
import io.github.bioplethora.entity.BPAnimalEntity;
import io.github.bioplethora.entity.ai.controller.WaterMoveController;
import io.github.bioplethora.entity.ai.goals.BPCustomSwimmingGoal;
import io.github.bioplethora.entity.ai.goals.BPWaterChargingCoal;
import io.github.bioplethora.entity.ai.navigator.WaterAndLandPathNavigator;
import io.github.bioplethora.enums.BPEntityClasses;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class VoidjawEntity extends TrapjawEntity {

    public VoidjawEntity(EntityType<? extends BPAnimalEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createLivingAttributes()
                .add(Attributes.ARMOR, 6 * BPConfig.COMMON.mobArmorMultiplier.get())
                .add(Attributes.ATTACK_SPEED, 1.5)
                .add(Attributes.ATTACK_DAMAGE, 17 * BPConfig.COMMON.mobMeeleeDamageMultiplier.get())
                .add(Attributes.ATTACK_KNOCKBACK, 1.0 * BPConfig.COMMON.mobMeeleeDamageMultiplier.get())
                .add(Attributes.MAX_HEALTH, 135 * BPConfig.COMMON.mobHealthMultiplier.get())
                .add(Attributes.MOVEMENT_SPEED, 0.30D * BPConfig.COMMON.mobMovementSpeedMultiplier.get())
                .add(Attributes.FOLLOW_RANGE, 32D);
    }

    @Override
    public BPEntityClasses getBioplethoraClass() {
        return BPEntityClasses.HELLSENT;
    }

    @Override
    public boolean shouldVerticalMove() {
        return true;
    }

    public boolean isNoGravity() {
        return true;
    }

    public boolean causeFallDamage(float distance, float damageMultiplier) {
        return false;
    }

    protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new ChargeAttackGoal());
        this.goalSelector.addGoal(2, new MoveRandomGoal());
    }

    @Override
    public void switchNavigator(boolean onLand) {
        if (onLand) {
            this.moveControl = new VoidjawEntity.MoveHelperController(this);
            this.navigation = new GroundPathNavigator(this, level);
            this.isLandNavigator = true;
        } else {
            this.moveControl = new WaterMoveController(this, 1.2F);
            this.navigation = new WaterAndLandPathNavigator(this, level);
            this.isLandNavigator = false;
        }
    }

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

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "voidjaw_controller", 0, this::predicate));
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (this.getAttacking()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.voidjaw.attack", true));
            return PlayState.CONTINUE;
        }
        if (this.isInSittingPose()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.voidjaw.sit", true));
            return PlayState.CONTINUE;
        }
        if ((event.isMoving() && this.getTarget() != null) || (!this.isInWater() && this.isVehicle() && event.isMoving())) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.voidjaw.run", true));
            return PlayState.CONTINUE;
        }
        if (event.isMoving() && this.getTarget() == null) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.voidjaw.walk", true));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.voidjaw.idle", true));
        return PlayState.CONTINUE;
    }

    public class ChargeAttackGoal extends Goal {
        public ChargeAttackGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        public boolean canUse() {
            if (VoidjawEntity.this.getTarget() != null &&
                    !VoidjawEntity.this.getMoveControl().hasWanted() &&
                    VoidjawEntity.this.random.nextInt(3) == 0) {

                return VoidjawEntity.this.distanceToSqr(VoidjawEntity.this.getTarget()) > 2.0D;
            } else {
                return false;
            }
        }

        public boolean canContinueToUse() {
            return VoidjawEntity.this.getMoveControl().hasWanted() &&
                    VoidjawEntity.this.getTarget() != null &&
                    VoidjawEntity.this.getTarget().isAlive();
        }

        public void start() {
            LivingEntity livingentity = VoidjawEntity.this.getTarget();
            Vector3d vector3d = livingentity.getEyePosition(1.0F);
            VoidjawEntity.this.moveControl.setWantedPosition(vector3d.x, vector3d.y, vector3d.z, 1.0D);
        }

        public void stop() {
        }

        public void tick() {
            LivingEntity livingentity = VoidjawEntity.this.getTarget();

            if (!VoidjawEntity.this.getBoundingBox().intersects(livingentity.getBoundingBox())) {
                double d0 = VoidjawEntity.this.distanceToSqr(livingentity);
                if (d0 < 9.0D) {
                    Vector3d vector3d = livingentity.getEyePosition(1.0F);
                    VoidjawEntity.this.moveControl.setWantedPosition(vector3d.x, vector3d.y, vector3d.z, 1.0D);
                }
            }
        }
    }

    public class MoveHelperController extends MovementController {
        public MoveHelperController(VoidjawEntity floatingMob) {
            super(floatingMob);
        }

        public void tick() {
            if (this.operation == MovementController.Action.MOVE_TO) {
                Vector3d vector3d = new Vector3d(this.wantedX - VoidjawEntity.this.getX(), this.wantedY - VoidjawEntity.this.getY(), this.wantedZ - VoidjawEntity.this.getZ());
                double d0 = vector3d.length();
                if (d0 < VoidjawEntity.this.getBoundingBox().getSize()) {
                    this.operation = MovementController.Action.WAIT;
                    VoidjawEntity.this.setDeltaMovement(VoidjawEntity.this.getDeltaMovement().scale(0.5D));
                } else {
                    VoidjawEntity.this.setDeltaMovement(VoidjawEntity.this.getDeltaMovement().add(vector3d.scale(this.speedModifier * 0.05D / d0)));
                    if (VoidjawEntity.this.getTarget() == null) {
                        Vector3d vector3d1 = VoidjawEntity.this.getDeltaMovement();
                        VoidjawEntity.this.yRot = -((float) MathHelper.atan2(vector3d1.x, vector3d1.z)) * (180F / (float)Math.PI);
                        VoidjawEntity.this.yBodyRot = VoidjawEntity.this.yRot;
                    } else {
                        double d2 = VoidjawEntity.this.getTarget().getX() - VoidjawEntity.this.getX();
                        double d1 = VoidjawEntity.this.getTarget().getZ() - VoidjawEntity.this.getZ();
                        VoidjawEntity.this.yRot = -((float) MathHelper.atan2(d2, d1)) * (180F / (float)Math.PI);
                        VoidjawEntity.this.yBodyRot = VoidjawEntity.this.yRot;
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
            return !VoidjawEntity.this.getMoveControl().hasWanted() && VoidjawEntity.this.random.nextInt(5) == 0;
        }

        public boolean canContinueToUse() {
            return false;
        }

        public void tick() {
            BlockPos blockpos = VoidjawEntity.this.getBoundOrigin();
            if (blockpos == null) {
                blockpos = VoidjawEntity.this.blockPosition();
            }

            for(int i = 0; i < 3; ++i) {
                BlockPos blockpos1 = blockpos.offset(VoidjawEntity.this.random.nextInt(15) - 7, VoidjawEntity.this.random.nextInt(11) - 5, VoidjawEntity.this.random.nextInt(15) - 7);
                if (VoidjawEntity.this.level.isEmptyBlock(blockpos1)) {
                    VoidjawEntity.this.moveControl.setWantedPosition((double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.5D, (double)blockpos1.getZ() + 0.5D, 0.25D);
                    if (VoidjawEntity.this.getTarget() == null) {
                        VoidjawEntity.this.getLookControl().setLookAt((double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.5D, (double)blockpos1.getZ() + 0.5D, 180.0F, 20.0F);
                    }
                    break;
                }
            }
        }
    }
}
