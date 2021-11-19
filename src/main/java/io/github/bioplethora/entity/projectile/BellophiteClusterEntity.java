package io.github.bioplethora.entity.projectile;

import io.github.bioplethora.entity.BellophgolemEntity;
import io.github.bioplethora.registry.BioplethoraEntities;
import net.minecraft.block.AbstractBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class BellophiteClusterEntity extends DamagingProjectileEntity implements IAnimatable {

    private final AnimationFactory factory = new AnimationFactory(this);
    public int explosionPower = 1;
    private float ProjDamage;

    public BellophiteClusterEntity(EntityType<BellophiteClusterEntity> entityType, World worldIn) {
        super(entityType, worldIn);
    }

    @OnlyIn(Dist.CLIENT)
    public BellophiteClusterEntity(World world, double v, double v1, double v2, double v3, double v4, double v5) {
        super(BioplethoraEntities.BELLOPHITE_CLUSTER.get(), v, v1, v2, v3, v4, v5, world);
    }

    public BellophiteClusterEntity(World world, LivingEntity entity, double v, double v1, double v2) {
        super(BioplethoraEntities.BELLOPHITE_CLUSTER.get(), entity, v, v1, v2, world);
    }

    @Override
    public void tick() {
        super.tick();
        Vector3d vector3d = this.getDeltaMovement();
        RayTraceResult raytraceresult = ProjectileHelper.getHitResult(this, this::canHitEntity);
        if (raytraceresult.getType() != RayTraceResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
            this.onHit(raytraceresult);
        }
        this.setSecondsOnFire(0);
        double d0 = this.getX() + vector3d.x;
        double d1 = this.getY() + vector3d.y;
        double d2 = this.getZ() + vector3d.z;
        this.updateRotation();
        if (this.level.getBlockStates(this.getBoundingBox()).noneMatch(AbstractBlock.AbstractBlockState::isAir)) {
            this.remove();
        } else {
            this.setDeltaMovement(vector3d.scale(0.99F));
            this.setPos(d0, d1, d2);
        }
    }

    @Override
    protected void onHitEntity(EntityRayTraceResult result) {
        super.onHitEntity(result);
        Entity entity = this.getOwner();
        if (entity instanceof BellophgolemEntity)
            result.getEntity().hurt(DamageSource.indirectMobAttack(this, (BellophgolemEntity) entity).setProjectile(), ProjDamage);
        if (!this.level.isClientSide) {
            boolean flag = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this.getOwner());
            this.level.explode((Entity)null, this.getX(), this.getY(), this.getZ(), (float)this.explosionPower, flag, flag ? Explosion.Mode.DESTROY : Explosion.Mode.NONE);
            this.remove();
        }
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {

        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.bellophite_cluster.main", true));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.bellophite_cluster.main", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "bellophiteclustercontroller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}
