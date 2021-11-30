package io.github.bioplethora.entity.projectile;

import io.github.bioplethora.config.BioplethoraConfig;
import io.github.bioplethora.registry.BioplethoraEntities;
import net.minecraft.block.AbstractBlock;
import net.minecraft.client.renderer.entity.DragonFireballRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
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

    public void tick() {
        super.tick();
        if(this.level instanceof ServerWorld) {
            List<Entity> nearEntities = this.level
                    .getEntitiesOfClass(Entity.class, new AxisAlignedBB(this.getX() - (3 / 2d), this.getY(), this.getZ() - (3 / 2d), this.getX() + (3 / 2d), this.getY() + (3 / 2d), this.getZ() + (3 / 2d)), null)
                    .stream().sorted(new Object() {
                        Comparator<Entity> compareDistOf(double dx, double dy, double dz) {
                            return Comparator.comparing((entCnd -> entCnd.distanceToSqr(dx, dy, dz)));
                        }
                    }.compareDistOf(this.getX(), this.getY(), this.getZ())).collect(Collectors.toList());
            for (Entity entityIterator : nearEntities) {
                if (entityIterator instanceof LivingEntity && entityIterator != this.getOwner()) {

                    entityIterator.setDeltaMovement(entityIterator.getDeltaMovement().x, 0.5, entityIterator.getDeltaMovement().z);
                }
            }
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
                }
            }
        }
        level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + 0.5F);
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
