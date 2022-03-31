package io.github.bioplethora.entity.others;

import io.github.bioplethora.entity.creatures.AlphemEntity;
import io.github.bioplethora.registry.BPEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class AlphanumShardEntity extends Entity implements IAnimatable {

    private final AnimationFactory factory = new AnimationFactory(this);
    public LivingEntity owner;
    public LivingEntity target;
    public int timeBeforeExpire;

    public AlphanumShardEntity(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    public void tick() {
        super.tick();
        ++timeBeforeExpire;
        if (timeBeforeExpire >= 40) {
            this.level.explode(this, getX(), getY(), getZ(), 1.5F, Explosion.Mode.BREAK);
            if (!this.level.isClientSide()) {
                ((ServerWorld) this.level).sendParticles(ParticleTypes.FIREWORK, getX(), getY(), getZ(), 45, 0.45, 0.45, 0.45, 0.01);

                ServerWorld serverworld = (ServerWorld)this.level;
                BlockPos blockpos = new BlockPos(getX(), getY(), getZ());
                AlphemEntity alphem = BPEntities.ALPHEM.get().create(this.level);
                alphem.finalizeSpawn(serverworld, this.level.getCurrentDifficultyAt(blockpos), SpawnReason.MOB_SUMMONED, null, null);
                alphem.moveTo(blockpos, 0.0F, 0.0F);
                if (this.target != null) {
                    alphem.setTarget(target);
                }
                if (this.owner != null) {
                    alphem.setOwner(owner);
                }

                if (Math.random() < 0.10) {
                    this.level.addFreshEntity(alphem);
                }
            }
            this.remove();
        }
    }

    public void setOwner(LivingEntity owner) {
        this.owner = owner;
    }

    public LivingEntity getOwner() {
        return owner;
    }

    public void setTarget(LivingEntity target) {
        this.target = target;
    }

    public LivingEntity getTarget() {
        return target;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.alphanum_shard.idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "alphanum_shard_controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    protected void readAdditionalSaveData(CompoundNBT p_70037_1_) {
    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT p_213281_1_) {
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
