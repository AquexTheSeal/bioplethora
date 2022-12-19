package io.github.bioplethora.entity.projectile;

import io.github.bioplethora.registry.BPDamageSources;
import io.github.bioplethora.registry.BPEntities;
import io.github.bioplethora.registry.BPItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class VermilionBladeProjectileEntity extends DamagingProjectileEntity implements IAnimatable, IRendersAsItem {

    private final AnimationFactory factory = new AnimationFactory(this);
    public double lifespan = 0;
    public int bladeSize = 1;

    public VermilionBladeProjectileEntity(EntityType<? extends DamagingProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public VermilionBladeProjectileEntity(World world, LivingEntity entity, double v, double v1, double v2) {
        super(BPEntities.VERMILION_BLADE_PROJECTILE.get(), entity, v, v1, v2, world);
    }

    @Override
    public void registerControllers(AnimationData data) {

    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

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

        entity.hurt(BPDamageSources.helioSlashed(this.getOwner(), this.getOwner()), 5 * ((float) this.bladeSize * 0.75F));
        this.remove();
    }

    @Override
    protected void onHit(RayTraceResult result) {
        double x = this.getX(), y = this.getY(), z = this.getZ();
        Entity owner = this.getOwner();
        super.onHit(result);

        if (result.getType() != RayTraceResult.Type.ENTITY || !((EntityRayTraceResult) result).getEntity().is(owner)) {
            this.level.explode(this, x, y, z, 1.5F * ((float) this.bladeSize * 0.5F), Explosion.Mode.BREAK);
            this.remove();
        }
    }

    public void setBladeSize(int value) {
        this.bladeSize = value;
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected IParticleData getTrailParticle() {
        return ParticleTypes.SMOKE;
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

    @Override
    public ItemStack getItem() {
        return new ItemStack(BPItems.VERMILION_BLADE.get());
    }
}
