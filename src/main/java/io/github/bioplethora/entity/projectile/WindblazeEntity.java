package io.github.bioplethora.entity.projectile;

import io.github.bioplethora.config.BioplethoraConfig;
import io.github.bioplethora.entity.creatures.AltyrusEntity;
import io.github.bioplethora.registry.BioplethoraEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.network.IPacket;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class WindblazeEntity extends DamagingProjectileEntity {

    public double lifespan = 0;

    public WindblazeEntity(EntityType<? extends DamagingProjectileEntity> entityType, World world) {
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

        ++lifespan;

        if ((lifespan == 100)) {
            this.remove();
        }
    }

    @Override
    protected void onHitEntity(EntityRayTraceResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        AxisAlignedBB entArea = new AxisAlignedBB(this.getX() - (3 / 2d), this.getY() - (3 / 2d), this.getZ() - (3 / 2d), this.getX() + (3 / 2d), this.getY() + (3 / 2d), this.getZ() + (3 / 2d));

        if (this.level instanceof ServerWorld && this.getOwner() instanceof MobEntity && ((MobEntity) this.getOwner()).getTarget() != null) {
            List<Entity> nearEntities = this.level.getEntitiesOfClass(Entity.class, entArea, null).stream().sorted(new Object() {
                        Comparator<Entity> compareDistOf(double dx, double dy, double dz) {
                            return Comparator.comparing((getEnt -> getEnt.distanceToSqr(dx, dy, dz)));
                        }
                    }.compareDistOf(this.getX(), this.getY(), this.getZ())).collect(Collectors.toList());
            for (Entity entityArea : nearEntities) {
                if (entityArea instanceof LivingEntity && (entityArea == ((MobEntity) this.getOwner()).getTarget() || ((MobEntity) entityArea).getTarget() == this.getEntity())) {

                    if (!(entityArea instanceof AltyrusEntity)) {
                        entityArea.setDeltaMovement(0, 0.75, 0);
                    }

                    if (BioplethoraConfig.COMMON.hellMode.get()) {
                        entityArea.hurt(DamageSource.MAGIC, 3);
                        ((LivingEntity) entityArea).addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 100, 2));
                        ((LivingEntity) entityArea).addEffect(new EffectInstance(Effects.WEAKNESS, 60, 1));
                    } else {
                        ((LivingEntity) entityArea).addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 60, 1));
                    }
                }
            }
        }
        level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, SoundCategory.HOSTILE, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + 0.5F);
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
