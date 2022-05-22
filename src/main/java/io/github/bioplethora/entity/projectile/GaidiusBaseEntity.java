package io.github.bioplethora.entity.projectile;

import io.github.bioplethora.registry.BPEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public abstract class GaidiusBaseEntity extends ProjectileItemEntity {
    public boolean isCrit;

    public GaidiusBaseEntity(EntityType<? extends ProjectileItemEntity> type, World world) {
        super(type, world);
    }

    public GaidiusBaseEntity(EntityType<? extends ProjectileItemEntity> type, LivingEntity entity, World world) {
        super(type, entity, world);
    }

    public GaidiusBaseEntity(EntityType<? extends ProjectileItemEntity> type, World world, double v, double v1, double v2) {
        super(type, v, v1, v2, world);
    }

    @Override
    protected void onHitEntity(EntityRayTraceResult result) {
        super.onHitEntity(result);
        Entity entity = result.getEntity();
        entity.hurt(DamageSource.thrown(this, this.getOwner()), getProjectileDamage(result)
                + (EnchantmentHelper.getItemEnchantmentLevel(BPEnchantments.RACKING_EDGE.get(), getItem()) * 1.5F)
        );
    }

    @Override
    public void tick() {
        super.tick();

        Vector3d vector3d = this.getDeltaMovement();
        double d3 = vector3d.x;
        double d4 = vector3d.y;
        double d0 = vector3d.z;
        if (this.isCrit()) {
            for(int i = 0; i < 4; ++i) {
                this.level.addParticle(ParticleTypes.CRIT, this.getX() + d3 * (double)i / 4.0D, this.getY() + d4 * (double)i / 4.0D, this.getZ() + d0 * (double)i / 4.0D, -d3, -d4 + 0.2D, -d0);
            }
        }
    }

    public void setCrit(boolean crit) {
        isCrit = crit;
    }

    public boolean isCrit() {
        return isCrit;
    }

    public boolean isOnFire() {
        return false;
    }

    public abstract int getProjectileDamage(EntityRayTraceResult result);

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
