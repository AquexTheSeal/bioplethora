package io.github.bioplethora.entity.creatures;

import io.github.bioplethora.entity.BPAirWaterLandEntity;
import io.github.bioplethora.entity.IBioClassification;
import io.github.bioplethora.entity.ISaddleable;
import io.github.bioplethora.entity.IVerticalMount;
import io.github.bioplethora.enums.BPEntityClasses;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.Direction;
import net.minecraft.util.HandSide;
import net.minecraft.util.TransportationHelper;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;

public class AquamaerEntity extends BPAirWaterLandEntity implements IAnimatable, IBioClassification, IRideable, IVerticalMount, ISaddleable {

    private static final DataParameter<Boolean> HAS_SADDLE = EntityDataManager.defineId(TrapjawEntity.class, DataSerializers.BOOLEAN);
    private final AnimationFactory factory = new AnimationFactory(this);

    public AquamaerEntity(EntityType<? extends TameableEntity> type, World worldIn) {
        super(type, worldIn);
        this.setTame(false);
        this.noCulling = true;
    }

    @Override
    public BPEntityClasses getBioplethoraClass() {
        return BPEntityClasses.ECOHARMLESS;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
    }

    @Nullable
    @Override
    public AgeableEntity getBreedOffspring(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        return null;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(HAS_SADDLE, false);
    }

    public boolean isSaddled() {
        return this.entityData.get(HAS_SADDLE);
    }

    public void setSaddled(boolean saddled) {
        this.entityData.set(HAS_SADDLE, saddled);
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    public boolean rideableUnderWater() {
        return true;
    }

    @Override
    public boolean shouldVerticalMove() {
        return true;
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public float getSteeringSpeed() {
        return 0.5F;
    }

    public boolean canBeLeashed(PlayerEntity entity) {
        return this.isTame() && super.canBeLeashed(entity);
    }

    @OnlyIn(Dist.CLIENT)
    public Vector3d getLeashOffset() {
        return new Vector3d(0.0D, 0.6D * this.getEyeHeight(), this.getBbWidth() * 0.4F);
    }

    public boolean canBreatheUnderwater() {
        return true;
    }

    public CreatureAttribute getMobType() {
        return CreatureAttribute.WATER;
    }

    @Override
    public double getPassengersRidingOffset() {
        return super.getPassengersRidingOffset() * 1.75;
    }

    @Override
    protected boolean canRide(Entity entity) {
        return super.canRide(entity);
    }

    public boolean canBeControlledByRider() {
        return this.getControllingPassenger() == this.getOwner();
    }

    @Nullable
    public Entity getControllingPassenger() {
        return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
    }

    public int getAmbientSoundInterval() {
        return 120;
    }

    protected int getExperienceReward(PlayerEntity playerEntity) {
        return 2 + this.level.random.nextInt(3);
    }

    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    protected float getWaterSlowDown() {
        if (this.isVehicle()) {
            return 0.95F;
        } else {
            return super.getWaterSlowDown();
        }
    }

    @Override
    public void travel(Vector3d dir) {
        Entity entity = this.getControllingPassenger();
        if (this.isVehicle()) {
            assert entity != null;
            this.yRot = entity.yRot;
            this.yRotO = this.yRot;
            this.xRot = entity.xRot * 0.5F;
            this.setRot(this.yRot, this.xRot);
            this.flyingSpeed = this.getSpeed() * 0.15F;
            this.yBodyRot = entity.yRot;
            this.yHeadRot = entity.yRot;
            this.maxUpStep = 1.0F;
            if (entity instanceof LivingEntity) {
                this.setSpeed((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED));
                float forward = ((LivingEntity) entity).zza;
                float strafe = ((LivingEntity) entity).xxa;
                super.travel(new Vector3d(strafe, 0, forward));
            }
            this.animationSpeedOld = this.animationSpeed;
            double d1 = this.getX() - this.xo;
            double d0 = this.getZ() - this.zo;
            float f1 = MathHelper.sqrt(d1 * d1 + d0 * d0) * 4.0F;
            if (f1 > 1.0F)
                f1 = 1.0F;
            this.animationSpeed += (f1 - this.animationSpeed) * 0.4F;
            this.animationPosition += this.animationSpeed;
            return;
        }
        this.maxUpStep = 0.5F;
        this.flyingSpeed = 0.02F;
        super.travel(dir);
    }

    public Vector3d getDismountLocationForPassenger(LivingEntity pLivingEntity) {
        Vector3d vector3d = getCollisionHorizontalEscapeVector(this.getBbWidth(), pLivingEntity.getBbWidth(), this.yRot + (pLivingEntity.getMainArm() == HandSide.RIGHT ? 90.0F : -90.0F));
        Vector3d vector3d1 = this.getDismountLocationInDirection(vector3d, pLivingEntity);
        if (vector3d1 != null) {
            return vector3d1;
        } else {
            Vector3d vector3d2 = getCollisionHorizontalEscapeVector(this.getBbWidth(), pLivingEntity.getBbWidth(), this.yRot + (pLivingEntity.getMainArm() == HandSide.LEFT ? 90.0F : -90.0F));
            Vector3d vector3d3 = this.getDismountLocationInDirection(vector3d2, pLivingEntity);
            return vector3d3 != null ? vector3d3 : this.position();
        }
    }

    @Nullable
    private Vector3d getDismountLocationInDirection(Vector3d vector3d1, LivingEntity entity) {
        double d0 = this.getX() + vector3d1.x;
        double d1 = this.getBoundingBox().minY;
        double d2 = this.getZ() + vector3d1.z;
        BlockPos.Mutable mutable = new BlockPos.Mutable();

        for(Pose pose : entity.getDismountPoses()) {
            mutable.set(d0, d1, d2);
            double d3 = this.getBoundingBox().maxY + 0.75D;

            while(true) {
                double d4 = this.level.getBlockFloorHeight(mutable);
                if ((double)mutable.getY() + d4 > d3) {
                    break;
                }

                if (TransportationHelper.isBlockFloorValid(d4)) {
                    AxisAlignedBB axisalignedbb = entity.getLocalBoundsForPose(pose);
                    Vector3d vector3d = new Vector3d(d0, (double)mutable.getY() + d4, d2);
                    if (TransportationHelper.canDismountTo(this.level, entity, axisalignedbb.move(vector3d))) {
                        entity.setPose(pose);
                        return vector3d;
                    }
                }

                mutable.move(Direction.UP);
                if (!((double)mutable.getY() < d3)) {
                    break;
                }
            }
        }

        return null;
    }

    @Override
    public void travelWithInput(Vector3d pTravelVec) {
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving() && this.getTarget() == null) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.aquamaer.walk", true));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.aquamaer.idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "aquamaer_controller", 0, this::predicate));
    }

    @Override
    public boolean boost() {
        return false;
    }
}
