package io.github.bioplethora.entity.creatures;

import io.github.bioplethora.config.BPConfig;
import io.github.bioplethora.entity.IBioClassification;
import io.github.bioplethora.entity.ai.goals.MyliothanChargeAttackGoal;
import io.github.bioplethora.entity.ai.goals.MyliothanShakeGoal;
import io.github.bioplethora.entity.others.part.BPPartEntity;
import io.github.bioplethora.enums.BPEntityClasses;
import io.github.bioplethora.registry.BPSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.*;
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
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.entity.PartEntity;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Random;

public class MyliothanEntity extends WaterMobEntity implements IAnimatable, IBioClassification {

    public final BPPartEntity[] subEntities;
    public final BPPartEntity head;
    public final BPPartEntity tail;
    public final BPPartEntity leftWing;
    public final BPPartEntity rightWing;
    public final BPPartEntity leftWingTip;
    public final BPPartEntity rightWingTip;

    public Vector3d moveTargetPoint = Vector3d.ZERO;
    private final double[][] positions = new double[64][3];
    private int posPointer = -1;
    private float yRotA;
    public boolean inWall;

    private static final DataParameter<Boolean> SHAKING = EntityDataManager.defineId(MyliothanEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> CHARGING = EntityDataManager.defineId(MyliothanEntity.class, DataSerializers.BOOLEAN);
    private final AnimationFactory factory = new AnimationFactory(this);

    public MyliothanEntity(EntityType<? extends WaterMobEntity> type, World worldIn) {
        super(type, worldIn);
        this.moveControl = new MyliothanMoveController(this);
        this.lookControl = new DolphinLookController(this, 10);
        this.setPathfindingMalus(PathNodeType.WATER, 0.0F);
        this.noCulling = true;

        head = new BPPartEntity<>(this, "head", 9.5f, 3.2f);
        tail = new BPPartEntity<>(this, "tail", 9f, 3.0f);
        leftWing = new BPPartEntity<>(this, "wing", 12.5f, 2.5f);
        rightWing = new BPPartEntity<>(this, "wing", 12.5f, 2.5f);
        leftWingTip = new BPPartEntity<>(this, "wing", 9.5f, 2.3f);
        rightWingTip = new BPPartEntity<>(this, "wing", 9.5f, 2.3f);
        subEntities = new BPPartEntity[]{head, tail, leftWing, rightWing, leftWingTip, rightWingTip};

    }

    @Nullable
    @Override
    public PartEntity<?>[] getParts() {
        return subEntities;
    }

    @Override
    public boolean isMultipartEntity() {
        return true;
    }

    @Override
    public BPEntityClasses getBioplethoraClass() {
        return BPEntityClasses.ELDERIA;
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createLivingAttributes()
                .add(Attributes.ARMOR, (BPConfig.IN_HELLMODE ? 16 : 14) * BPConfig.COMMON.mobArmorMultiplier.get())
                .add(Attributes.ATTACK_SPEED, 10)
                .add(Attributes.ATTACK_KNOCKBACK, 10D)
                .add(Attributes.ATTACK_DAMAGE, (BPConfig.IN_HELLMODE ? 32 : 27) * BPConfig.COMMON.mobMeeleeDamageMultiplier.get())
                .add(Attributes.MAX_HEALTH, (BPConfig.IN_HELLMODE ? 385 : 335) * BPConfig.COMMON.mobHealthMultiplier.get())
                .add(Attributes.MOVEMENT_SPEED, 1.2 * BPConfig.COMMON.mobMovementSpeedMultiplier.get())
                .add(Attributes.KNOCKBACK_RESISTANCE, 10D)
                .add(Attributes.FOLLOW_RANGE, 64D);
    }

    @Override
    public void tick() {
        super.tick();

        if (posPointer < 0) {
            for (int i = 0; i < positions.length; ++i) {
                positions[i][0] = getViewYRot(1.0F);
                positions[i][1] = getY();
            }
        }

        if (++posPointer == positions.length) {
            posPointer = 0;
        }

        // Target Point Deltas
        double tpdx = moveTargetPoint.x - getX(), tpdz = moveTargetPoint.z - getZ();

        if (Math.abs(tpdx) > (double) 1.0E-5F || Math.abs(tpdz) > (double) 1.0E-5F) {
            float yRotAModifier = MathHelper.clamp(
                    MathHelper.wrapDegrees(180.0F - (float) MathHelper.atan2(tpdx, tpdz) *
                            (180F / (float) Math.PI) - getViewYRot(1.0F)),
                    -10.0F, 10.0F);

            yRotA *= 0.8F;
            yRotA += yRotAModifier * getTurnSpeed();
        }

        float posLatency = (float) (getLatencyPos(5, 1.0F)[1]
                - getLatencyPos(10, 1.0F)[1]) * 10.0F * ((float) Math.PI / 180F);
        float cosLatency = MathHelper.cos(posLatency);
        float sinLatency = MathHelper.sin(posLatency);
        float sinRotMod = MathHelper.sin(getViewYRot(1.0F) * ((float) Math.PI / 180F) - yRotA * 0.01F);
        float cosRotMod = MathHelper.cos(getViewYRot(1.0F) * ((float) Math.PI / 180F) - yRotA * 0.01F);
        float yHeadOffset = getHeadYOffset();

        float yRadians = getViewYRot(1.0F) * ((float) Math.PI / 180F);
        float wingXOffset = MathHelper.cos(yRadians);
        float wingZOffset = MathHelper.sin(yRadians);

        tickPart(head, (-sinRotMod * (8f * getScale() * 0.8f) * cosLatency),
                (yHeadOffset + sinLatency * 6.5f),
                (cosRotMod * (8f * getScale() * 0.8f) * cosLatency));

        tickPart(tail, (sinRotMod * (10f * getScale() * 0.8f) * cosLatency),
                (yHeadOffset + sinLatency * 6.5f),
                (-cosRotMod * (10f * getScale() * 0.8f) * cosLatency));

        tickPart(leftWing, wingXOffset * 6f, 0.5f, wingZOffset * 6f);
        tickPart(rightWing, wingXOffset * -6f, 0.5f, wingZOffset * -6f);
        tickPart(leftWingTip, wingXOffset * 16f, 0.0f, wingZOffset * 16f);
        tickPart(rightWingTip, wingXOffset * -16f, 0.0f, wingZOffset * -16f);

        Vector3d[] subVec = new Vector3d[subEntities.length];

        for (int i = 0; i < subEntities.length; ++i) {
            subVec[i] = new Vector3d(subEntities[i].getX(), subEntities[i].getY(), subEntities[i].getZ());
        }

        for (int i = 0; i < subEntities.length; ++i) {
            subEntities[i].xo = subVec[i].x;
            subEntities[i].yo = subVec[i].y;
            subEntities[i].zo = subVec[i].z;
            subEntities[i].xOld = subVec[i].x;
            subEntities[i].yOld = subVec[i].y;
            subEntities[i].zOld = subVec[i].z;
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.inWall) {
            this.move(MoverType.SELF, this.getDeltaMovement().scale(0.8F));
        } else {
            this.move(MoverType.SELF, this.getDeltaMovement());
        }

        if (!this.level.isClientSide) {
            this.inWall = this.checkWalls(this.getBoundingBox()) |
                    this.checkWalls(this.head.getBoundingBox()) | this.checkWalls(this.leftWing.getBoundingBox()) |
                            this.checkWalls(this.rightWing.getBoundingBox()) | this.checkWalls(this.rightWingTip.getBoundingBox()) |
                                    this.checkWalls(this.leftWingTip.getBoundingBox()) | this.checkWalls(this.tail.getBoundingBox());
        }

        if (this.getTarget() != null) {
            if (this.getTarget().isInWater()) {
                this.getTarget().setDeltaMovement(0, 0.5, 0);
            }
        }
    }

    @Override
    public boolean doHurtTarget(Entity pEntity) {
        if (pEntity instanceof PlayerEntity) {
            PlayerEntity entity = (PlayerEntity) pEntity;
            if (entity.getUseItem().isShield(entity)) {
                entity.getCooldowns().addCooldown(entity.getUseItem().getItem(), 300);
            }
        }
        return super.doHurtTarget(pEntity);
    }

    @Override
    protected void handleAirSupply(int pAirSupply) {
        this.setAirSupply(300);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FindWaterGoal(this));
        this.goalSelector.addGoal(1, new MyliothanChargeAttackGoal(this));
        this.goalSelector.addGoal(2, new MyliothanShakeGoal(this));
        this.goalSelector.addGoal(2, new MoveTowardsTargetGoal(this, 1.6, 8));
        this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 24.0F));
        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.0D, 10));
        this.goalSelector.addGoal(5, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
    }

    private boolean checkWalls(AxisAlignedBB pArea) {
        int i = MathHelper.floor(pArea.minX);
        int j = MathHelper.floor(pArea.minY);
        int k = MathHelper.floor(pArea.minZ);
        int l = MathHelper.floor(pArea.maxX);
        int i1 = MathHelper.floor(pArea.maxY);
        int j1 = MathHelper.floor(pArea.maxZ);
        boolean flag = false;
        boolean flag1 = false;

        for(int k1 = i; k1 <= l; ++k1) {
            for(int l1 = j; l1 <= i1; ++l1) {
                for(int i2 = k; i2 <= j1; ++i2) {
                    BlockPos blockpos = new BlockPos(k1, l1, i2);
                    BlockState blockstate = this.level.getBlockState(blockpos);
                    Block block = blockstate.getBlock();
                    if (!blockstate.isAir(this.level, blockpos) && blockstate.getMaterial() != Material.FIRE) {
                        if (net.minecraftforge.common.ForgeHooks.canEntityDestroy(this.level, blockpos, this) && !BlockTags.DRAGON_IMMUNE.contains(block)) {
                            flag1 = this.level.removeBlock(blockpos, false) || flag1;
                        } else {
                            flag = true;
                        }
                    }
                }
            }
        }

        if (flag1) {
            BlockPos blockpos1 = new BlockPos(i + this.random.nextInt(l - i + 1), j + this.random.nextInt(i1 - j + 1), k + this.random.nextInt(j1 - k + 1));
            this.level.levelEvent(2008, blockpos1, 0);
        }

        return flag;
    }

    // TODO: 26/01/2022 - Better Myliothan charging animation.
    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {

        if (this.isCharging()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.myliothan.charge", true));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.myliothan.idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "myliothan_controller", 5, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    public static boolean checkMyliothanSpawnRules(EntityType<MyliothanEntity> myliothan, IWorld world, SpawnReason pSpawnType, BlockPos pos, Random pRandom) {
        if (pos.getY() > 45 && pos.getY() < world.getSeaLevel()) {
            Optional<RegistryKey<Biome>> optional = world.getBiomeName(pos);
            return BiomeDictionary.hasType(optional.get(), BiomeDictionary.Type.OCEAN) && world.getFluidState(pos).is(FluidTags.WATER);
        } else {
            return false;
        }
    }

    @Override
    public void checkDespawn() {
        if (this.level.getDifficulty() == Difficulty.PEACEFUL && this.shouldDespawnInPeaceful()) {
            this.remove();
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
        return BPSoundEvents.MYLIOTHAN_IDLE.get();
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
                double d0 = MathHelper.sqrt(f * f + f2 * f2);
                double d1 = 1.0D - (double) MathHelper.abs(f1 * 0.7F) / d0;
                f = (float) ((double) f * d1);
                f2 = (float) ((double) f2 * d1);
                d0 = MathHelper.sqrt(f * f + f2 * f2);
                double d2 = MathHelper.sqrt(f * f + f2 * f2 + f1 * f1);
                float f3 = this.myliothan.yRot;
                float f4 = (float) MathHelper.atan2(f2, f);
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
                        float f2 = -((float)(MathHelper.atan2(d1, MathHelper.sqrt(d0 * d0 + d2 * d2)) * (double)(180F / (float)Math.PI)));
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
        this.entityData.define(SHAKING, false);
        this.entityData.define(CHARGING, false);
    }

    public boolean isShaking() {
        return this.entityData.get(SHAKING);
    }

    public void setShaking(boolean shaking) {
        this.entityData.set(SHAKING, shaking);
    }

    public boolean isCharging() {
        return this.entityData.get(CHARGING);
    }

    public void setCharging(boolean charging) {
        this.entityData.set(CHARGING, charging);
    }

    private void tickPart(BPPartEntity pPart, double offsetX, double offsetY, double offsetZ) {
        pPart.setPos(getX() + offsetX, getY() + offsetY, getZ() + offsetZ);
    }

    private float getHeadYOffset() {
        double[] latencyPos = getLatencyPos(5, 1.0F);
        double[] latencyPos1 = getLatencyPos(0, 1.0F);
        return (float) (latencyPos[1] - latencyPos1[1]);
    }

    public double[] getLatencyPos(int pointer, float multiplier) {
        if (isDeadOrDying()) {
            multiplier = 0.0F;
        }

        multiplier = 1.0F - multiplier;
        int item = posPointer - pointer & 63;
        int previousItem = posPointer - pointer - 1 & 63;
        double[] latencyPos = new double[3];
        double yawOffset = positions[item][0];
        double yOffsetMinusYawOffset = MathHelper.wrapDegrees(positions[previousItem][0] - yawOffset);
        latencyPos[0] = yawOffset + yOffsetMinusYawOffset * (double) multiplier;
        yawOffset = positions[item][1];
        yOffsetMinusYawOffset = positions[previousItem][1] - yawOffset;
        latencyPos[1] = yawOffset + yOffsetMinusYawOffset * (double) multiplier;
        latencyPos[2] = MathHelper.lerp(multiplier, positions[item][2], positions[previousItem][2]);
        return latencyPos;
    }

    public float getTurnSpeed() {
        float deltaDistance = (float) (getDeltaMovement().length() + 1.0F);
        float min = Math.min(deltaDistance, 40.0F);
        return 0.7F / min / deltaDistance;
    }
}
