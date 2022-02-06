package io.github.bioplethora.entity.projectile;

import io.github.bioplethora.registry.BioplethoraEntities;
import io.github.bioplethora.registry.BioplethoraItems;
import io.github.bioplethora.registry.BioplethoraParticles;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;
import java.util.Comparator;
import java.util.List;

/**
 * @credits
 * Copyright (c) 2020 Sin Tachikawa
 * ProjectE - https://www.curseforge.com/minecraft/mc-mods/projecte
 */
public class WindArrowEntity extends AbstractArrowEntity {

    private static final DataParameter<Integer> DW_TARGET_ID = EntityDataManager.defineId(WindArrowEntity.class, DataSerializers.INT);
    private static final int NO_TARGET = -1;
    private int newTargetCooldown = 0;

    private int duration = 200;

    public WindArrowEntity(EntityType<? extends WindArrowEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public WindArrowEntity(World worldIn, LivingEntity shooter) {
        super(BioplethoraEntities.WIND_ARROW.get(), shooter, worldIn);
    }

    public WindArrowEntity(World worldIn, double x, double y, double z) {
        super(BioplethoraEntities.WIND_ARROW.get(), x, y, z, worldIn);
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void doPostHurtEffects(@Nonnull LivingEntity living) {
        super.doPostHurtEffects(living);
        living.invulnerableTime = 0;
    }

    public void tick() {
        super.tick();

        if (this.level instanceof ServerWorld) {
            if (!this.inGround) {
                ((ServerWorld) this.level).sendParticles(BioplethoraParticles.WIND_POOF.get(), this.getX(), this.getY(), this.getZ(), 1, 0.1, 0.1, 0.1, 0);
            }
        }

        ++duration;
        int maxDuration = 200;
        if (this.duration == maxDuration) {
            this.projectileHit();
            this.remove();
        }

        if (!level.isClientSide && this.tickCount > 3) {
            if (hasTarget() && (!getTarget().isAlive() || this.inGround)) {
                entityData.set(DW_TARGET_ID, NO_TARGET);
            }

            if (!hasTarget() && !this.inGround && newTargetCooldown <= 0) {
                findNewTarget();
            } else {
                newTargetCooldown--;
            }
        }

        if (tickCount > 3 && hasTarget() && !this.inGround) {
            double mX = getDeltaMovement().x();
            double mY = getDeltaMovement().y();
            double mZ = getDeltaMovement().z();
            Entity target = getTarget();

            Vector3d arrowLoc = new Vector3d(getX(), getY(), getZ());
            Vector3d targetLoc = new Vector3d(target.getX(), target.getY() + target.getBbHeight() / 2, target.getZ());

            Vector3d lookVec = targetLoc.subtract(arrowLoc);

            Vector3d arrowMotion = new Vector3d(mX, mY, mZ);

            double theta = wrap180Radian(angleBetween(arrowMotion, lookVec));
            theta = clampAbs(theta, Math.PI / 2);

            Vector3d crossProduct = arrowMotion.cross(lookVec).normalize();
            Vector3d adjustedLookVec = transform(crossProduct, theta, arrowMotion);
            shoot(adjustedLookVec.x, adjustedLookVec.y, adjustedLookVec.z, 1.0F, 0);
        }

    }

    private void findNewTarget() {
        List<MobEntity> candidates = level.getEntitiesOfClass(MobEntity.class, this.getBoundingBox().inflate(8, 8, 8));

        if (!candidates.isEmpty()) {
            candidates.sort(Comparator.comparing(WindArrowEntity.this::distanceToSqr, Double::compare));
            entityData.set(DW_TARGET_ID, candidates.get(0).getId());
        }

        newTargetCooldown = 5;
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(DW_TARGET_ID, NO_TARGET);
    }


    private MobEntity getTarget() {
        return (MobEntity) level.getEntity(entityData.get(DW_TARGET_ID));
    }

    private boolean hasTarget() {
        return getTarget() != null;
    }


    public void onHitEntity(EntityRayTraceResult entityRayTraceResult) {
        super.onHitEntity(entityRayTraceResult);

        this.projectileHit();
    }

    protected void onHit(RayTraceResult entityRayTraceResult) {
        super.onHit(entityRayTraceResult);

        this.projectileHit();
    }

    protected void onHitBlock(BlockRayTraceResult result) {
        super.onHitBlock(result);

        this.projectileHit();
    }

    public void projectileHit() {
        BlockPos pos = new BlockPos((int) this.getX(), (int) this.getY(), (int) this.getZ());
        AxisAlignedBB area = new AxisAlignedBB(this.getX() - (5 / 2d), this.getY() - (5 / 2d), this.getZ() - (5 / 2d), this.getX() + (5 / 2d), this.getY() + (5 / 2d), this.getZ() + (5 / 2d));

        this.level.playSound(null, pos, SoundEvents.PLAYER_ATTACK_SWEEP, SoundCategory.NEUTRAL, (float) 1, (float) 1);

        if (this.level instanceof ServerWorld) {
            for (LivingEntity eI : this.level.getEntitiesOfClass(LivingEntity.class, area, null)) {
                if (eI != null && eI != this.getOwner()) {

                    if (this.getOwner() != null) {
                        eI.hurt(DamageSource.indirectMobAttack(this.getOwner(), (LivingEntity) this.getOwner()), 3);
                    }

                    eI.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 100, 2));
                    eI.invulnerableTime = 0;
                    ((ServerWorld) this.level).sendParticles(ParticleTypes.SWEEP_ATTACK, eI.getX(), eI.getY() + 1.0, eI.getZ(), 1, 0.1, 0.1, 0.1, 0);
                }
            }
        }
    }

    @Override
    public double getBaseDamage() {
        return 3.0D;
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(BioplethoraItems.WIND_ARROW.get());
    }

    public void readAdditionalSaveData(CompoundNBT compoundNBT) {
        super.readAdditionalSaveData(compoundNBT);
        if (compoundNBT.contains("Duration")) {
            this.duration = compoundNBT.getInt("Duration");
        }
    }

    public void addAdditionalSaveData(CompoundNBT compoundNBT) {
        super.addAdditionalSaveData(compoundNBT);
        compoundNBT.putInt("Duration", this.duration);
    }

    @Override
    protected float getWaterInertia() {
        return 1F;
    }

    private Vector3d transform(Vector3d axis, double angle, Vector3d normal) {

        double m00 = 1;
        double m01 = 0;
        double m02 = 0;

        double m10 = 0;
        double m11 = 1;
        double m12 = 0;

        double m20 = 0;
        double m21 = 0;
        double m22 = 1;
        double mag = Math.sqrt(axis.x * axis.x + axis.y * axis.y + axis.z * axis.z);
        if (mag >= 1.0E-10) {
            mag = 1.0 / mag;
            double ax = axis.x * mag;
            double ay = axis.y * mag;
            double az = axis.z * mag;

            double sinTheta = Math.sin(angle);
            double cosTheta = Math.cos(angle);
            double t = 1.0 - cosTheta;

            double xz = ax * az;
            double xy = ax * ay;
            double yz = ay * az;

            m00 = t * ax * ax + cosTheta;
            m01 = t * xy - sinTheta * az;
            m02 = t * xz + sinTheta * ay;

            m10 = t * xy + sinTheta * az;
            m11 = t * ay * ay + cosTheta;
            m12 = t * yz - sinTheta * ax;

            m20 = t * xz - sinTheta * ay;
            m21 = t * yz + sinTheta * ax;
            m22 = t * az * az + cosTheta;
        }
        return new Vector3d(m00 * normal.x + m01 * normal.y + m02 * normal.z,
                m10 * normal.x + m11 * normal.y + m12 * normal.z,
                m20 * normal.x + m21 * normal.y + m22 * normal.z);
    }

    private double angleBetween(Vector3d v1, Vector3d v2) {
        double vDot = v1.dot(v2) / (v1.length() * v2.length());
        if (vDot < -1.0) {
            vDot = -1.0;
        }
        if (vDot > 1.0) {
            vDot = 1.0;
        }
        return Math.acos(vDot);
    }

    private double wrap180Radian(double radian) {
        radian %= 2 * Math.PI;
        while (radian >= Math.PI) {
            radian -= 2 * Math.PI;
        }
        while (radian < -Math.PI) {
            radian += 2 * Math.PI;
        }
        return radian;
    }

    private double clampAbs(double param, double maxMagnitude) {
        if (Math.abs(param) > maxMagnitude) {

            if (param < 0) {
                param = -Math.abs(maxMagnitude);
            } else {
                param = Math.abs(maxMagnitude);
            }
        }
        return param;
    }
}
