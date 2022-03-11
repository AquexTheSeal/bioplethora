package io.github.bioplethora.entity.projectile;

import io.github.bioplethora.BioplethoraConfig;
import io.github.bioplethora.registry.BioplethoraEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.Attributes;
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

public class WindblazeEntity extends DamagingProjectileEntity {

    public double lifespan = 0;

    public WindblazeEntity(EntityType<? extends DamagingProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @OnlyIn(Dist.CLIENT)
    public WindblazeEntity(World world, double p_i1768_2_, double p_i1768_4_, double p_i1768_6_, double p_i1768_8_, double p_i1768_10_, double p_i1768_12_) {
        super(BioplethoraEntities.WINDBLAZE.get(), p_i1768_2_, p_i1768_4_, p_i1768_6_, p_i1768_8_, p_i1768_10_, p_i1768_12_, world);
    }

    public WindblazeEntity(World world, LivingEntity entity, double v, double v1, double v2) {
        super(BioplethoraEntities.WINDBLAZE.get(), entity, v, v1, v2, world);
    }


    @Override
    public void tick() {
        super.tick();

        ++lifespan;
        if (lifespan == 100) {
            this.remove();
        }
    }

    @Override
    protected void onHitEntity(EntityRayTraceResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        AxisAlignedBB entArea = new AxisAlignedBB(this.getX() - (3 / 2d), this.getY() - (3 / 2d), this.getZ() - (3 / 2d), this.getX() + (3 / 2d), this.getY() + (3 / 2d), this.getZ() + (3 / 2d));

        if (this.level instanceof ServerWorld && this.getOwner() instanceof MobEntity) {
            if (((MobEntity) this.getOwner()).getTarget() != null) {

                for (LivingEntity entityArea : this.level.getEntitiesOfClass(LivingEntity.class, entArea)) {

                    if ((entityArea == ((MobEntity) this.getOwner()).getTarget())) {
                        hurtEntityArea(entityArea);
                    }

                    if (entityArea instanceof MobEntity) {
                        if (((MobEntity) entityArea).getTarget() == this.getOwner()) {
                            hurtEntityArea(entityArea);
                        }
                    }
                }
            }
        }
        level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, SoundCategory.HOSTILE, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + 0.5F);
    }

    public void hurtEntityArea(LivingEntity entityArea) {

        entityArea.setDeltaMovement(entityArea.getDeltaMovement().add(0, 0.3 - entityArea.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE), 0));

        if (this.getOwner() != null) {
            if (BioplethoraConfig.COMMON.hellMode.get()) {
                entityArea.hurt(DamageSource.indirectMagic(this.getOwner(), this.getOwner()), 3);
                entityArea.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 100, 1));
                entityArea.addEffect(new EffectInstance(Effects.WEAKNESS, 60));
            } else {
                entityArea.hurt(DamageSource.indirectMagic(this.getOwner(), this.getOwner()), 1);
                entityArea.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 60));
            }
        }
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
