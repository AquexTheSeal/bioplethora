package io.github.bioplethora.entity.projectile;

import io.github.bioplethora.registry.BioplethoraDamageSources;
import io.github.bioplethora.registry.BioplethoraEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
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

public class VermilionBladeProjectileEntity extends DamagingProjectileEntity implements IAnimatable {

    private final AnimationFactory factory = new AnimationFactory(this);
    public double lifespan = 0;

    public VermilionBladeProjectileEntity(EntityType<? extends DamagingProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public VermilionBladeProjectileEntity(World world, LivingEntity entity, double v, double v1, double v2) {
        super(BioplethoraEntities.VERMILION_BLADE_PROJECTILE.get(), entity, v, v1, v2, world);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.vermilion_blade_projectile.idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "vermilion_blade_projectile_controller", 0, this::predicate));
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

        entity.hurt(BioplethoraDamageSources.helioSlashed(this.getOwner(), this.getOwner()), 5);
        this.remove();
    }

    @Override
    protected void onHit(RayTraceResult result) {
        double x = this.getX(), y = this.getY(), z = this.getZ();
        Entity owner = this.getOwner();
        super.onHit(result);

        if (result.getType() != RayTraceResult.Type.ENTITY || !((EntityRayTraceResult) result).getEntity().is(owner)) {
            this.level.explode(this, x, y, z, 1.5F, Explosion.Mode.BREAK);
            this.remove();
        }
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
}
