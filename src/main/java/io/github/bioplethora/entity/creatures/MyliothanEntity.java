package io.github.bioplethora.entity.creatures;

import io.github.bioplethora.BioplethoraConfig;
import io.github.bioplethora.entity.IBioplethoraEntityClass;
import io.github.bioplethora.entity.ai.MyliothanChargeAttackGoal;
import io.github.bioplethora.registry.BioplethoraEntityClasses;
import io.github.bioplethora.registry.BioplethoraSoundEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.DolphinLookController;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Objects;
import java.util.Optional;

public class MyliothanEntity extends WaterMobEntity implements IAnimatable, IBioplethoraEntityClass {

    private static final DataParameter<Boolean> DATA_IS_CHARGING = EntityDataManager.defineId(MyliothanEntity.class, DataSerializers.BOOLEAN);
    private final AnimationFactory factory = new AnimationFactory(this);
    public Vector3d moveTargetPoint = Vector3d.ZERO;

    public MyliothanEntity(EntityType<? extends WaterMobEntity> type, World worldIn) {
        super(type, worldIn);
        this.moveControl = new MyliothanMoveController(this);
        this.lookControl = new DolphinLookController(this, 10);
        this.setPathfindingMalus(PathNodeType.WATER, 0.0F);
        this.noCulling = true;
    }

    @Override
    public BioplethoraEntityClasses getBioplethoraClass() {
        return BioplethoraEntityClasses.ELDERIA;
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createLivingAttributes()
                .add(Attributes.ARMOR, 15 * BioplethoraConfig.COMMON.mobArmorMultiplier.get())
                .add(Attributes.ATTACK_SPEED, 10)
                .add(Attributes.ATTACK_KNOCKBACK, 10D)
                .add(Attributes.ATTACK_DAMAGE, 34 * BioplethoraConfig.COMMON.mobMeeleeDamageMultiplier.get())
                .add(Attributes.MAX_HEALTH, 385 * BioplethoraConfig.COMMON.mobHealthMultiplier.get())
                .add(Attributes.MOVEMENT_SPEED, 1.2 * BioplethoraConfig.COMMON.mobMovementSpeedMultiplier.get())
                .add(Attributes.FOLLOW_RANGE, 64D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FindWaterGoal(this));
        this.goalSelector.addGoal(1, new MyliothanChargeAttackGoal(this));
        this.goalSelector.addGoal(2, new MoveTowardsTargetGoal(this, 1.6, 8));
        this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 24.0F));
        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.0D, 10));
        this.goalSelector.addGoal(5, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {

        if (this.isCharging()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.myliothan.charging", true));
            return PlayState.CONTINUE;
        }

        if (event.isMoving() && this.isInWaterOrBubble()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.myliothan.swimming", true));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.myliothan.idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "myliothan_controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public boolean checkSpawnRules(IWorld world, SpawnReason reason) {
        return super.checkSpawnRules(world, reason) && MyliothanEntity.checkMyliothanSpawnRules(level, blockPosition());
    }

    public static boolean checkMyliothanSpawnRules(IWorld world, BlockPos pos) {
        if (pos.getY() > 45 && pos.getY() < world.getSeaLevel()) {
            Optional<RegistryKey<Biome>> optional = world.getBiomeName(pos);
            return (Objects.equals(optional, Optional.of(Biomes.OCEAN)) || !Objects.equals(optional, Optional.of(Biomes.DEEP_OCEAN))) && world.getFluidState(pos).is(FluidTags.WATER);
        } else {
            return false;
        }
    }

    protected PathNavigator createNavigation(World world) {
        return new SwimmerPathNavigator(this, world);
    }

    public void travel(Vector3d vector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(this.getSpeed(), vector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
            if (this.getTarget() == null) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.005D, 0.0D));
            }
        } else {
            super.travel(vector);
        }
    }

    @Override
    public net.minecraft.util.SoundEvent getAmbientSound() {
        return BioplethoraSoundEvents.MYLIOTHAN_IDLE.get();
    }

    @Override
    public net.minecraft.util.SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.GENERIC_HURT;
    }

    @Override
    public net.minecraft.util.SoundEvent getDeathSound() {
        return SoundEvents.GENERIC_DEATH;
    }

    @Override
    public boolean canBeLeashed(PlayerEntity player) {
        return false;
    }

    static class MyliothanMoveController extends MovementController {
        private final MyliothanEntity myliothan;
        private float speed = 0.1F;

        public MyliothanMoveController(MyliothanEntity entity) {
            super(entity);
            this.myliothan = entity;
        }

        public void tick() {
            if (this.myliothan.isInWater()) {
                this.myliothan.setDeltaMovement(this.myliothan.getDeltaMovement().add(0.0D, 0.005D, 0.0D));
            }

            if (this.myliothan.isCharging()) {
                float f = (float) (this.myliothan.moveTargetPoint.x - this.myliothan.getX());
                float f1 = (float) (this.myliothan.moveTargetPoint.y - this.myliothan.getY());
                float f2 = (float) (this.myliothan.moveTargetPoint.z - this.myliothan.getZ());
                double d0 = (double) MathHelper.sqrt(f * f + f2 * f2);
                double d1 = 1.0D - (double) MathHelper.abs(f1 * 0.7F) / d0;
                f = (float) ((double) f * d1);
                f2 = (float) ((double) f2 * d1);
                d0 = (double) MathHelper.sqrt(f * f + f2 * f2);
                double d2 = (double) MathHelper.sqrt(f * f + f2 * f2 + f1 * f1);
                float f3 = this.myliothan.yRot;
                float f4 = (float) MathHelper.atan2((double) f2, (double) f);
                float f5 = MathHelper.wrapDegrees(this.myliothan.yRot + 90.0F);
                float f6 = MathHelper.wrapDegrees(f4 * (180F / (float) Math.PI));
                this.myliothan.yRot = MathHelper.approachDegrees(f5, f6, 4.0F) - 90.0F;
                this.myliothan.yBodyRot = this.myliothan.yRot;
                if (MathHelper.degreesDifferenceAbs(f3, this.myliothan.yRot) < 3.0F) {
                    this.speed = MathHelper.approach(this.speed, 1.8F, 0.005F * (1.8F / this.speed));
                } else {
                    this.speed = MathHelper.approach(this.speed, 0.2F, 0.025F);
                }

                float f7 = (float) (-(MathHelper.atan2(-f1, d0) * (double) (180F / (float) Math.PI)));
                this.myliothan.xRot = f7;
                float f8 = this.myliothan.yRot + 90.0F;
                double d3 = (double) (this.speed * MathHelper.cos(f8 * ((float) Math.PI / 180F))) * Math.abs((double) f / d2);
                double d4 = (double) (this.speed * MathHelper.sin(f8 * ((float) Math.PI / 180F))) * Math.abs((double) f2 / d2);
                double d5 = (double) (this.speed * MathHelper.sin(f7 * ((float) Math.PI / 180F))) * Math.abs((double) f1 / d2);
                Vector3d vector3d = this.myliothan.getDeltaMovement();
                this.myliothan.setDeltaMovement(vector3d.add((new Vector3d(d3, d5, d4)).subtract(vector3d).scale(0.2D)));
            }

            if (this.operation == MovementController.Action.MOVE_TO && !this.myliothan.getNavigation().isDone()) {
                double d0 = this.wantedX - this.myliothan.getX();
                double d1 = this.wantedY - this.myliothan.getY();
                double d2 = this.wantedZ - this.myliothan.getZ();
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                if (d3 < (double)2.5000003E-7F) {
                    this.mob.setZza(0.0F);
                } else {
                    float f = (float)(MathHelper.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
                    this.myliothan.yRot = this.rotlerp(this.myliothan.yRot, f, 10.0F);
                    this.myliothan.yBodyRot = this.myliothan.yRot;
                    this.myliothan.yHeadRot = this.myliothan.yRot;
                    float f1 = (float)(this.speedModifier * this.myliothan.getAttributeValue(Attributes.MOVEMENT_SPEED));
                    if (this.myliothan.isInWater()) {
                        this.myliothan.setSpeed(f1 * 0.02F);
                        float f2 = -((float)(MathHelper.atan2(d1, (double)MathHelper.sqrt(d0 * d0 + d2 * d2)) * (double)(180F / (float)Math.PI)));
                        f2 = MathHelper.clamp(MathHelper.wrapDegrees(f2), -85.0F, 85.0F);
                        this.myliothan.xRot = this.rotlerp(this.myliothan.xRot, f2, 5.0F);
                        float f3 = MathHelper.cos(this.myliothan.xRot * ((float)Math.PI / 180F));
                        float f4 = MathHelper.sin(this.myliothan.xRot * ((float)Math.PI / 180F));
                        this.myliothan.zza = f3 * f1;
                        this.myliothan.yya = -f4 * f1;
                    } else {
                        this.myliothan.setSpeed(f1 * 0.1F);
                    }
                }
            } else {
                this.myliothan.setSpeed(0.0F);
                this.myliothan.setXxa(0.0F);
                this.myliothan.setYya(0.0F);
                this.myliothan.setZza(0.0F);
            }
        }
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_IS_CHARGING, false);
    }

    public boolean isCharging() {
        return this.entityData.get(DATA_IS_CHARGING);
    }

    public void setCharging(boolean charging) {
        this.entityData.set(DATA_IS_CHARGING, charging);
    }
}
