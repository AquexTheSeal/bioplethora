package io.github.bioplethora.entity.projectile;

import io.github.bioplethora.entity.SummonableMonsterEntity;
import io.github.bioplethora.registry.BPDamageSources;
import io.github.bioplethora.registry.BPEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.*;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class UltimateFrostbiteMetalClusterEntity extends DamagingProjectileEntity implements IAnimatable {

    public double xPower;
    public double yPower;
    public double zPower;
    public int lifespan = 0;
    private final AnimationFactory factory = new AnimationFactory(this);

    public UltimateFrostbiteMetalClusterEntity(EntityType<? extends DamagingProjectileEntity> type, World world) {
        super(type, world);
    }

    @OnlyIn(Dist.CLIENT)
    public UltimateFrostbiteMetalClusterEntity(World world, double v, double v1, double v2, double v3, double v4, double v5) {
        super(BPEntities.ULTIMATE_BELLOPHITE_CLUSTER.get(), v, v1, v2, v3, v4, v5, world);
    }

    public UltimateFrostbiteMetalClusterEntity(World world, LivingEntity entity, double v, double v1, double v2) {
        super(BPEntities.ULTIMATE_BELLOPHITE_CLUSTER.get(), entity, v, v1, v2, world);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.frostbite_metal_cluster.main", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "ultimate_frostbite_metal_cluster_controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    protected void onHit(RayTraceResult result) {
        super.onHit(result);
    }

    @Override
    protected void onHitBlock(BlockRayTraceResult p_230299_1_) {
        super.onHitBlock(p_230299_1_);
        this.hitAndExplode();
    }

    @Override
    protected void onHitEntity(EntityRayTraceResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        if (entity instanceof ProjectileEntity) {
            if (((ProjectileEntity) entity).getOwner() != this.getOwner()) {
                this.hitAndExplode();
            }
        } else if (entity instanceof TameableEntity) {
            if (((TameableEntity) entity).getOwner() != this.getOwner()) {
                this.hitAndExplode();
            }
        } else if (entity instanceof SummonableMonsterEntity) {
            if (((SummonableMonsterEntity) entity).getOwner() != this.getOwner()) {
                this.hitAndExplode();
            }
        } else {
            this.hitAndExplode();
        }
    }

    public void tick() {
        super.tick();

        ++lifespan;
        if (lifespan == 100) {
            this.remove();
        }
    }

    public void hitAndExplode() {
        double x = this.getX(), y = this.getY(), z = this.getZ();
        BlockPos blockPos = new BlockPos(x, y, z);
        AxisAlignedBB area = new AxisAlignedBB(x - (7 / 2d), y, z - (7 / 2d), x + (7 / 2d), y + (7 / 2d), z + (7 / 2d));
        DamageSource castration = BPDamageSources.indirectCastration(this.getOwner(), this.getOwner());

        if (this.level instanceof ServerWorld) {
            ((ServerWorld) this.level).sendParticles(ParticleTypes.CLOUD, this.getX(), this.getY(), this.getZ(), 40, 0.6, 0.6, 0.6, 0.1);
        }

        this.level.playSound(null, blockPos, SoundEvents.GLASS_BREAK, SoundCategory.NEUTRAL, (float) 1, (float) 1);

        if (this.level instanceof ServerWorld) {
            List<Entity> nearEntities = this.level
                    .getEntitiesOfClass(Entity.class, area, null)
                    .stream().sorted(new Object() {
                        Comparator<Entity> compareDistOf(double dx, double dy, double dz) {
                            return Comparator.comparing((getEnt -> getEnt.distanceToSqr(dx, dy, dz)));
                        }
                    }.compareDistOf(this.getX(), this.getY(), this.getZ())).collect(Collectors.toList());
            for (Entity entityArea : nearEntities) {
                if (entityArea instanceof LivingEntity && entityArea != this.getOwner()) {

                    entityArea.hurt(castration, (float) 15);

                    ((LivingEntity) entityArea).addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 100, 3));
                    ((LivingEntity) entityArea).addEffect(new EffectInstance(Effects.DIG_SLOWDOWN, 100, 2));
                    ((LivingEntity) entityArea).addEffect(new EffectInstance(Effects.WEAKNESS, 100, 1));
                }
            }
        }

        if (!this.level.isClientSide) {
            this.level.explode(this, x, this.getY(0.0625D), z, 3F, Explosion.Mode.BREAK);
            this.remove();
        }
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public boolean shouldBurn() {
        return false;
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }
}
