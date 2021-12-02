package io.github.bioplethora.entity.projectile;

import io.github.bioplethora.config.BioplethoraConfig;
import io.github.bioplethora.registry.BioplethoraEntities;
import net.minecraft.block.AbstractBlock;
import net.minecraft.client.renderer.entity.DragonFireballRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.entity.projectile.DragonFireballEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.network.IPacket;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class WindblazeEntity extends DamagingProjectileEntity {

    public double lifespan = 0;

    public WindblazeEntity(EntityType<? extends WindblazeEntity> entityType, World world) {
        super(entityType, world);
    }

    @OnlyIn(Dist.CLIENT)
    public WindblazeEntity(World world, double v, double v1, double v2, double v3, double v4, double v5) {
        super(BioplethoraEntities.WINDBLAZE.get(), v, v1, v2, v3, v4, v5, world);
    }

    public WindblazeEntity(World world, LivingEntity entity, double v, double v1, double v2) {
        super(BioplethoraEntities.WINDBLAZE.get(), entity, v, v1, v2, world);
    }

    @Override
    public void tick() {
        super.tick();

        Entity entity = this.getOwner();
        if (this.level.isClientSide || (entity == null || !entity.removed) && this.level.hasChunkAt(this.blockPosition())) {
            super.tick();
            if (this.shouldBurn()) {
                this.setSecondsOnFire(1);
            }

            RayTraceResult raytraceresult = ProjectileHelper.getHitResult(this, this::canHitEntity);
            if (raytraceresult.getType() != RayTraceResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
                this.onHit(raytraceresult);
            }

            this.checkInsideBlocks();
            Vector3d vector3d = this.getDeltaMovement();
            double d0 = this.getX() + vector3d.x;
            double d1 = this.getY() + vector3d.y;
            double d2 = this.getZ() + vector3d.z;
            ProjectileHelper.rotateTowardsMovement(this, 0.2F);
            float f = this.getInertia();
            if (this.isInWater()) {
                for(int i = 0; i < 4; ++i) {
                    float f1 = 0.25F;
                    this.level.addParticle(ParticleTypes.BUBBLE, d0 - vector3d.x * 0.25D, d1 - vector3d.y * 0.25D, d2 - vector3d.z * 0.25D, vector3d.x, vector3d.y, vector3d.z);
                }

                f = 0.8F;
            }

            this.setDeltaMovement(vector3d.add(this.xPower, this.yPower, this.zPower).scale((double)f));
            this.level.addParticle(this.getTrailParticle(), d0, d1 + 0.5D, d2, 0.0D, 0.0D, 0.0D);
            this.setPos(d0, d1, d2);
        } else {
            this.remove();
        }

        lifespan = (double) lifespan + 1;

        if ((lifespan == 100)) {
            this.remove();
        }
    }

    @Override
    protected void onHitEntity(EntityRayTraceResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();

        if (this.level instanceof ServerWorld) {
            List<Entity> nearEntities = this.level
                    .getEntitiesOfClass(Entity.class, new AxisAlignedBB(this.getX() - (3 / 2d), this.getY() - (3 / 2d), this.getZ() - (3 / 2d), this.getX() + (3 / 2d), this.getY() + (3 / 2d), this.getZ() + (3 / 2d)), null)
                    .stream().sorted(new Object() {
                        Comparator<Entity> compareDistOf(double dx, double dy, double dz) {
                            return Comparator.comparing((entCnd -> entCnd.distanceToSqr(dx, dy, dz)));
                        }
                    }.compareDistOf(this.getX(), this.getY(), this.getZ())).collect(Collectors.toList());
            for (Entity entityIterator : nearEntities) {
                if (entityIterator instanceof LivingEntity && entityIterator != this.getOwner()) {
                    if (BioplethoraConfig.COMMON.hellMode.get()) {
                        ((LivingEntity) entityIterator).addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 100, 2));
                        ((LivingEntity) entityIterator).addEffect(new EffectInstance(Effects.WEAKNESS, 60, 1));
                    } else {
                        ((LivingEntity) entityIterator).addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 60, 1));
                    }
                    ((LivingEntity) entityIterator).setDeltaMovement(entityIterator.getDeltaMovement().x, 0.75, entityIterator.getDeltaMovement().z);
                }
            }
        }
        level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, SoundCategory.NEUTRAL, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + 0.5F);
    }

    @Nonnull
    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected IParticleData getTrailParticle() {
        return ParticleTypes.CLOUD;
    }

    public boolean hurt(DamageSource damageSource, float v) {
        return false;
    }

    public boolean isPickable() {
        return false;
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    protected boolean shouldBurn() {
        return false;
    }
}
