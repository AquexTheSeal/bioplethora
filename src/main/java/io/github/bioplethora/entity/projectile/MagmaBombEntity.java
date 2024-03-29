package io.github.bioplethora.entity.projectile;

import io.github.bioplethora.registry.BPEntities;
import io.github.bioplethora.registry.BPItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class MagmaBombEntity extends ProjectileItemEntity {

    public float explosionPower;

    public MagmaBombEntity(EntityType<? extends MagmaBombEntity> type, World world) {
        super(type, world);
    }

    public MagmaBombEntity(World world, LivingEntity entity) {
        super(BPEntities.MAGMA_BOMB.get(), entity, world);
    }

    public MagmaBombEntity(World world, double v, double v1, double v2) {
        super(BPEntities.MAGMA_BOMB.get(), v, v1, v2, world);
    }

    protected Item getDefaultItem() {
        return BPItems.MAGMA_BOMB.get();
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public void setExplosionPower(float explosionPower) {
        this.explosionPower = explosionPower;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level instanceof ServerWorld) {
            ((ServerWorld) this.level).sendParticles(ParticleTypes.LARGE_SMOKE, this.getX(), this.getY(), this.getZ(), 15, 0.4, 0.4, 0.4, 0);
        }
    }

    @OnlyIn(Dist.CLIENT)
    private IParticleData getParticle() {
        ItemStack itemstack = this.getItemRaw();
        return (itemstack.isEmpty() ? ParticleTypes.SMOKE : new ItemParticleData(ParticleTypes.ITEM, itemstack));
    }

    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte b) {
        if (b == 3) {
            IParticleData iparticledata = this.getParticle();

            for(int i = 0; i < 8; ++i) {
                this.level.addParticle(iparticledata, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }
    }

    protected void onHitEntity(EntityRayTraceResult result) {
        super.onHitEntity(result);
        Entity entity = result.getEntity();
        entity.hurt(DamageSource.thrown(this, this.getOwner()), 3);
        this.level.explode(this, this.getX(), this.getY(0.0625D), this.getZ(), this.explosionPower, Explosion.Mode.BREAK);
        if (!this.level.isClientSide) {
            this.level.broadcastEntityEvent(this, (byte)3);
        }
        this.remove();
    }

    @Override
    protected void onHitBlock(BlockRayTraceResult pResult) {
        super.onHitBlock(pResult);
        this.level.explode(this, this.getX(), this.getY(0.0625D), this.getZ(), this.explosionPower, Explosion.Mode.BREAK);
        if (!this.level.isClientSide) {
            this.level.broadcastEntityEvent(this, (byte)3);
            this.remove();
        }
    }

    public boolean isOnFire() {
        return true;
    }
}
