package io.github.bioplethora.entity.projectile;

import io.github.bioplethora.api.world.EntityUtils;
import io.github.bioplethora.config.BPConfig;
import io.github.bioplethora.particles.WindPoofParticleData;
import io.github.bioplethora.registry.BPEntities;
import io.github.bioplethora.registry.BPItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.awt.*;
import java.util.Comparator;
import java.util.List;

import static io.github.bioplethora.api.extras.MathUtils.*;

public class EchoGaidiusEntity extends GaidiusBaseEntity {

    public static final DataParameter<Integer> TARGET_ID = EntityDataManager.defineId(GaidiusBaseEntity.class, DataSerializers.INT);
    public static final int NULL_TARGET_INT = -1;
    public int relocateCD = 0;

    public EchoGaidiusEntity(EntityType<? extends EchoGaidiusEntity> type, World world) {
        super(type, world);
    }

    public EchoGaidiusEntity(World world, LivingEntity entity) {
        super(BPEntities.ECHO_GAIDIUS.get(), entity, world);
    }

    public EchoGaidiusEntity(World world, double v, double v1, double v2) {
        super(BPEntities.ECHO_GAIDIUS.get(), world, v, v1, v2);
    }

    @Override
    public int getProjectileDamage(EntityRayTraceResult result) {
        return isCrit() ? 7 : 4;
    }

    @Override
    protected Item getDefaultItem() {
        return BPItems.ECHO_GAIDIUS.get();
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.onGround) {
            double px = getDeltaMovement().x() / 10, py = getDeltaMovement().y() / 10, pz = getDeltaMovement().z() / 10;

            for (int i = 0; i < 10; i += 1) {
                Color color = new Color(1.0f, 1.0f, 1.0f);
                WindPoofParticleData wPoof = new WindPoofParticleData(color, 1);
                this.level.addParticle(wPoof, getX() + (px * i), getY(0.35) + (py * i), getZ() + (pz * i), 0d, 0d, 0d);
            }
        }

        if (!level.isClientSide && this.tickCount > 3) {
            if (hasTarget() && (!getTarget().isAlive() || this.onGround)) {
                entityData.set(TARGET_ID, NULL_TARGET_INT);
            }

            if (!hasTarget() && !this.onGround && relocateCD <= 0) {
                locateAnotherTarget();
            } else {
                relocateCD--;
            }
        }

        if (tickCount > 3 && hasTarget() && !this.onGround) {
            double deltaX = getDeltaMovement().x(), deltaY = getDeltaMovement().y(), deltaZ = getDeltaMovement().z();
            Entity target = getTarget();

            Vector3d arrowLoc = new Vector3d(getX(), getY(), getZ());
            Vector3d targetLoc = new Vector3d(target.getX(), target.getY() + target.getBbHeight() / 2, target.getZ());
            Vector3d lookVec = targetLoc.subtract(arrowLoc);
            Vector3d arrowMotion = new Vector3d(deltaX, deltaY, deltaZ);

            double radian = wrap180Radian(angleBetween(arrowMotion, lookVec));
            radian = clampAbs(radian, Math.PI / 2);

            Vector3d crossProduct = arrowMotion.cross(lookVec).normalize();
            Vector3d adjustedLookVec = transform(crossProduct, radian, arrowMotion);
            shoot(adjustedLookVec.x, adjustedLookVec.y, adjustedLookVec.z, 1.0F, 0);
        }
    }

    public void locateAnotherTarget() {
        double targetRadius = BPConfig.COMMON.hellMode.get() ? 13.0D : 10.0D;
        List<MobEntity> candidates = level.getEntitiesOfClass(MobEntity.class, this.getBoundingBox().inflate(targetRadius, targetRadius, targetRadius));

        if (!candidates.isEmpty()) {
            if (isValidTarget(candidates.get(0))) {
                candidates.sort(Comparator.comparing(EchoGaidiusEntity.this::distanceToSqr, Double::compare));
                entityData.set(TARGET_ID, candidates.get(0).getId());
            }
        }

        relocateCD = 5;
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(TARGET_ID, NULL_TARGET_INT);
    }

    private MobEntity getTarget() {
        return (MobEntity) level.getEntity(entityData.get(TARGET_ID));
    }

    private boolean hasTarget() {
        return getTarget() != null;
    }

    public boolean isValidTarget(LivingEntity entity) {
        return EntityUtils.IsNotPet(this.getOwner()).test(entity);
    }

    public void onHitEntity(EntityRayTraceResult entityRayTraceResult) {
        super.onHitEntity(entityRayTraceResult);

        if (entityRayTraceResult.getEntity() instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) entityRayTraceResult.getEntity();
            entity.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 60, 3));
            if (random.nextInt(10) == 1) {
                entity.invulnerableTime = 0;
            }
        }
        this.projectileHit();
    }

    protected void onHitBlock(BlockRayTraceResult result) {
        super.onHitBlock(result);

        this.projectileHit();
    }

    public void projectileHit() {
        this.remove();
    }
}
