package io.github.bioplethora.entity.projectile;

import io.github.bioplethora.BPConfig;
import io.github.bioplethora.registry.BPEntities;
import io.github.bioplethora.registry.BPItems;
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
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;

public class BellophiteArrowEntity extends AbstractArrowEntity {

    /*private double baseDamage = 12.0D;*/
    private int duration = 200;

    public BellophiteArrowEntity(EntityType<? extends BellophiteArrowEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public BellophiteArrowEntity(World worldIn, LivingEntity shooter) {
        super(BPEntities.BELLOPHITE_ARROW.get(), shooter, worldIn);
    }

    public BellophiteArrowEntity(World worldIn, double x, double y, double z) {
        super(BPEntities.BELLOPHITE_ARROW.get(), x, y, z, worldIn);
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }


    public void tick() {
        super.tick();
        if (this.level instanceof ServerWorld) {
            if (!this.inGround) {
                ((ServerWorld) this.level).sendParticles(ParticleTypes.CLOUD, this.getX(), this.getY(), this.getZ(), (int) 2, 0.2, 0.2, 0.2, 0.01);
            }
        }
    }

    public void onHitEntity(EntityRayTraceResult entityRayTraceResult) {
        super.onHitEntity(entityRayTraceResult);

        this.projectileHit();
    }

    @Override
    protected void onHitBlock(BlockRayTraceResult blockRayTraceResult) {
        super.onHitBlock(blockRayTraceResult);

        this.projectileHit();
    }

    public void projectileHit() {
        double x = this.getX(), y = this.getY(), z = this.getZ();
        BlockPos pos = new BlockPos((int) this.getX(), (int) this.getY(), (int) this.getZ());
        AxisAlignedBB area = new AxisAlignedBB(this.getX() - (5 / 2d), this.getY() - (5 / 2d), this.getZ() - (5 / 2d), this.getX() + (5 / 2d), this.getY() + (5 / 2d), this.getZ() + (5 / 2d));

        if (this.level instanceof ServerWorld) {
            ((ServerWorld) this.level).sendParticles(ParticleTypes.CLOUD, x, y, z, 20, 0.4, 0.4, 0.4, 0.1);
        }

        this.level.playSound(null, pos, SoundEvents.GLASS_BREAK, SoundCategory.NEUTRAL, (float) 1, (float) 1);

        if(this.level instanceof ServerWorld) {
            for (Entity entityIterator : this.level.getEntitiesOfClass(Entity.class, area)) {
                if (entityIterator instanceof LivingEntity && entityIterator != this.getOwner()) {
                    if (this.getOwner() != null) {
                        entityIterator.hurt(DamageSource.indirectMagic(this.getOwner(), this.getOwner()), (float) 10.5);
                    } else {
                        entityIterator.hurt(DamageSource.MAGIC, (float) 10.5);
                    }
                    ((LivingEntity) entityIterator).addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 60, 2));
                    ((LivingEntity) entityIterator).addEffect(new EffectInstance(Effects.DIG_SLOWDOWN, 60, 2));
                    ((LivingEntity) entityIterator).addEffect(new EffectInstance(Effects.WEAKNESS, 60, 1));
                }
            }
        }
    }

    @Override
    public double getBaseDamage() {
        return BPConfig.getHellMode ? 7.0D : 9.5D;
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(BPItems.BELLOPHITE_ARROW.get());
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
}
