package io.github.bioplethora.entity.projectile;

import io.github.bioplethora.BioplethoraConfig;
import io.github.bioplethora.registry.BioplethoraDamageSources;
import io.github.bioplethora.registry.BioplethoraEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
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

public class BellophiteClusterEntity extends DamagingProjectileEntity implements IAnimatable {

    public double xPower;
    public double yPower;
    public double zPower;
    public int lifespan = 0;
    private final AnimationFactory factory = new AnimationFactory(this);

    public BellophiteClusterEntity(EntityType<? extends DamagingProjectileEntity> type, World world) {
        super(type, world);
    }

    @OnlyIn(Dist.CLIENT)
    public BellophiteClusterEntity(World world, double p_i1768_2_, double p_i1768_4_, double p_i1768_6_, double p_i1768_8_, double p_i1768_10_, double p_i1768_12_) {
        super(BioplethoraEntities.BELLOPHITE_CLUSTER.get(), p_i1768_2_, p_i1768_4_, p_i1768_6_, p_i1768_8_, p_i1768_10_, p_i1768_12_, world);
    }

    public BellophiteClusterEntity(World world, LivingEntity p_i1769_2_, double p_i1769_3_, double p_i1769_5_, double p_i1769_7_) {
        super(BioplethoraEntities.BELLOPHITE_CLUSTER.get(), p_i1769_2_, p_i1769_3_, p_i1769_5_, p_i1769_7_, world);
    }


    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
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

    protected void onHit(RayTraceResult result) {
        super.onHit(result);
        Entity entity = this.getOwner();
        if (entity != null && (result.getType() != RayTraceResult.Type.ENTITY || !((EntityRayTraceResult) result).getEntity().is(entity))) {
            this.hitAndExplode();
        }
    }

    @Override
    protected void onHitEntity(EntityRayTraceResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        if (entityHitResult.getType() != RayTraceResult.Type.ENTITY || !entityHitResult.getEntity().is(entity)) {
            this.hitAndExplode();
        }
    }

    @Override
    public void tick() {
        Entity entity = this.getOwner();
        if (this.level.isClientSide || (entity != null) && this.level.hasChunkAt(this.blockPosition())) {
            super.tick();
            if (this.shouldBurn()) {
                this.setSecondsOnFire(1);
            }

            if (this.level instanceof ServerWorld) {
                ((ServerWorld) this.level).sendParticles(ParticleTypes.CLOUD, this.getX(), this.getY(), this.getZ(), (int) 3, 0.4, 0.4, 0.4, 0.1);
            }

            RayTraceResult raytraceresult = ProjectileHelper.getHitResult(this, this::canHitEntity);
            if (raytraceresult.getType() != RayTraceResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
                this.onHit(raytraceresult);
            }

            this.checkInsideBlocks();
            Vector3d vector3d = this.getDeltaMovement();
            double d0 = this.getX() + vector3d.x;
            double d1 = this.getY() + vector3d.y;
            double d2 = this.getZ() + vector3d.z;
            ProjectileHelper.rotateTowardsMovement(this, 0.2F);
            float f = this.getInertia();
            if (this.isInWater()) {
                for(int i = 0; i < 4; ++i) {
                    float f1 = 0.25F;
                    this.level.addParticle(ParticleTypes.BUBBLE, d0 - vector3d.x * 0.25D, d1 - vector3d.y * 0.25D, d2 - vector3d.z * 0.25D, vector3d.x, vector3d.y, vector3d.z);
                }

                f = 0.8F;
            }

            this.setDeltaMovement(vector3d.add(this.xPower, this.yPower, this.zPower).scale((double)f));
            this.level.addParticle(this.getTrailParticle(), d0, d1 + 0.5D, d2, 0.0D, 0.0D, 0.0D);
            this.setPos(d0, d1, d2);
        } else {
            this.remove();
        }

        ++lifespan;

        if (lifespan == 100) {
            this.remove();
        }
    }

    public void hitAndExplode() {
        double x = this.getX(), y = this.getY(), z = this.getZ();
        BlockPos blockpos = new BlockPos((int) this.getX(), (int) this.getY(), (int) this.getZ());
        AxisAlignedBB area = new AxisAlignedBB(this.getX() - (7 / 2d), this.getY() - (7 / 2d), this.getZ() - (7 / 2d), this.getX() + (7 / 2d), this.getY() + (7 / 2d), this.getZ() + (7 / 2d));
        DamageSource castration = BioplethoraDamageSources.indirectCastration(this.getOwner(), this.getOwner());

        if (this.level instanceof ServerWorld) {
            ((ServerWorld) this.level).sendParticles(ParticleTypes.CLOUD, x, y, z, 20, 0.4, 0.4, 0.4, 0.1);
        }

        this.level.playSound(null, blockpos, SoundEvents.GLASS_BREAK, SoundCategory.NEUTRAL, (float) 1, (float) 1);

        if(this.level instanceof ServerWorld && !(this.getOwner() == null)) {
            List<Entity> nearEntities = this.level
                    .getEntitiesOfClass(Entity.class, area, null).stream().sorted(new Object() {
                        Comparator<Entity> compareDistOf(double dx, double dy, double dz) {
                            return Comparator.comparing((getEnt -> getEnt.distanceToSqr(dx, dy, dz)));
                        }
                    }.compareDistOf(x, y, z)).collect(Collectors.toList());
            for (Entity entityArea : nearEntities) {
                if (entityArea instanceof LivingEntity && entityArea != this.getOwner()) {

                    if (this.getOwner() != null) {
                        //hell mode + berserk
                        if (((LivingEntity) this.getOwner()).getHealth() <= 100 && BioplethoraConfig.COMMON.hellMode.get()) {
                            entityArea.hurt(castration, (float) 13.5);
                            //berserk only
                        } else if (((LivingEntity) this.getOwner()).getHealth() <= 100 && !BioplethoraConfig.COMMON.hellMode.get()) {
                            entityArea.hurt(castration, (float) 10);
                            //default
                        } else {
                            entityArea.hurt(castration, (float) 5.5);
                        }
                    }

                    ((LivingEntity) entityArea).addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 60, 2));
                    ((LivingEntity) entityArea).addEffect(new EffectInstance(Effects.DIG_SLOWDOWN, 60, 2));
                    ((LivingEntity) entityArea).addEffect(new EffectInstance(Effects.WEAKNESS, 60, 1));
                }
            }
        }

        if (!this.level.isClientSide && this.getOwner() != null) {
            if (((LivingEntity) this.getOwner()).getHealth() <= 100 && BioplethoraConfig.COMMON.hellMode.get()) {
                this.level.explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 3F, Explosion.Mode.BREAK);
            } else {
                this.level.explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 1.5F, Explosion.Mode.BREAK);
            }
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
