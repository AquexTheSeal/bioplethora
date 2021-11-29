package io.github.bioplethora.entity.projectile;

import io.github.bioplethora.registry.BioplethoraEntities;
import io.github.bioplethora.registry.BioplethoraItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class BellophiteArrowEntity extends AbstractArrowEntity {

    private double baseDamage = 10.0D;
    private int duration = 200;

    public BellophiteArrowEntity(EntityType<? extends BellophiteArrowEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public BellophiteArrowEntity(World worldIn, LivingEntity shooter) {
        super(BioplethoraEntities.BELLOPHITE_ARROW.get(), shooter, worldIn);
    }

    public BellophiteArrowEntity(World worldIn, double x, double y, double z) {
        super(BioplethoraEntities.BELLOPHITE_ARROW.get(), x, y, z, worldIn);
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }


    public void tick() {
        super.tick();
        if (this.level instanceof ServerWorld) {
            if (!this.inGround) {
                ((ServerWorld) this.level).sendParticles(ParticleTypes.CLOUD, this.getX(), this.getY(), this.getZ(), (int) 2, 0.4, 0.4, 0.4, 0.1);
            }
        }
    }

    public void onHitEntity(EntityRayTraceResult entityRayTraceResult) {
        super.onHitEntity(entityRayTraceResult);
        Entity entity = entityRayTraceResult.getEntity();

        if (this.level instanceof ServerWorld) {
            ((ServerWorld) this.level).sendParticles(ParticleTypes.CLOUD, this.getX(), this.getY(), this.getZ(), (int) 20, 0.4, 0.4, 0.4, 0.1);
        }

        this.level.playSound(null, new BlockPos((int) this.getX(), (int) this.getY(), (int) this.getZ()),
                (net.minecraft.util.SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.glass.break"))),
                SoundCategory.NEUTRAL, (float) 1, (float) 1);

        if(this.level instanceof ServerWorld) {
            List<Entity> nearEntities = this.level
                    .getEntitiesOfClass(Entity.class, new AxisAlignedBB(this.getX() - (5 / 2d), this.getY() - (5 / 2d), this.getZ() - (5 / 2d), this.getX() + (5 / 2d), this.getY() + (5 / 2d), this.getZ() + (5 / 2d)), null)
                    .stream().sorted(new Object() {
                        Comparator<Entity> compareDistOf(double dx, double dy, double dz) {
                            return Comparator.comparing((entCnd -> entCnd.distanceToSqr(dx, dy, dz)));
                        }
                    }.compareDistOf(this.getX(), this.getY(), this.getZ())).collect(Collectors.toList());
            for (Entity entityIterator : nearEntities) {
                if (entityIterator instanceof LivingEntity && entityIterator != this.getOwner()) {
                    entityIterator.hurt(DamageSource.MAGIC, (float) 5.5);
                    ((LivingEntity) entityIterator).addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 60, 2));
                    ((LivingEntity) entityIterator).addEffect(new EffectInstance(Effects.DIG_SLOWDOWN, 60, 2));
                    ((LivingEntity) entityIterator).addEffect(new EffectInstance(Effects.WEAKNESS, 60, 1));
                }
            }
        }
    }

    protected void onHit(RayTraceResult entityRayTraceResult) {
        super.onHit(entityRayTraceResult);
        Entity entity = this.getOwner();

        if (this.level instanceof ServerWorld) {
            ((ServerWorld) this.level).sendParticles(ParticleTypes.CLOUD, this.getX(), this.getY(), this.getZ(), (int) 20, 0.4, 0.4, 0.4, 0.1);
        }

        this.level.playSound(null, new BlockPos((int) this.getX(), (int) this.getY(), (int) this.getZ()),
                (net.minecraft.util.SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.glass.break"))),
                SoundCategory.NEUTRAL, (float) 1, (float) 1);

        if(this.level instanceof ServerWorld) {
            List<Entity> nearEntities = this.level
                    .getEntitiesOfClass(Entity.class, new AxisAlignedBB(this.getX() - (5 / 2d), this.getY() - (5 / 2d), this.getZ() - (5 / 2d), this.getX() + (5 / 2d), this.getY() + (5 / 2d), this.getZ() + (5 / 2d)), null)
                    .stream().sorted(new Object() {
                        Comparator<Entity> compareDistOf(double dx, double dy, double dz) {
                            return Comparator.comparing((entCnd -> entCnd.distanceToSqr(dx, dy, dz)));
                        }
                    }.compareDistOf(this.getX(), this.getY(), this.getZ())).collect(Collectors.toList());
            for (Entity entityIterator : nearEntities) {
                if (entityIterator instanceof LivingEntity && entityIterator != this.getOwner()) {
                    entityIterator.hurt(DamageSource.MAGIC, (float) 5.5);
                    ((LivingEntity) entityIterator).addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 60, 2));
                    ((LivingEntity) entityIterator).addEffect(new EffectInstance(Effects.DIG_SLOWDOWN, 60, 2));
                    ((LivingEntity) entityIterator).addEffect(new EffectInstance(Effects.WEAKNESS, 60, 1));
                }
            }
        }
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(BioplethoraItems.BELLOPHITE_ARROW.get());
    }

    /*@Override
    public boolean isNoGravity() {
        return true;
    }*/

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

    @Override
    public double getBaseDamage() {
        return this.baseDamage;
    }
}
