package io.github.bioplethora.entity.others;

import io.github.bioplethora.entity.creatures.AltyrusEntity;
import io.github.bioplethora.registry.BioplethoraEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
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

public class AltyrusSummoningEntity extends Entity implements IAnimatable {

    private final AnimationFactory factory = new AnimationFactory(this);
    public int birthTime = 0;

    public AltyrusSummoningEntity(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void defineSynchedData() {
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.altyrus_summoning.summoning", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "altyrussummoningcontroller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    public void tick() {
        super.tick();

        ++birthTime;

        this.setDeltaMovement(this.getDeltaMovement().x(), this.getDeltaMovement().y() + 0.1, this.getDeltaMovement().z());

        if (this.level instanceof ServerWorld) {
            ((ServerWorld) this.level).sendParticles(ParticleTypes.POOF, (this.getX()), (this.getY()), (this.getZ()), (int) 5, 1, 1, 1, 0.1);
        }

        if (this.birthTime >= 100) {

            if (!this.level.isClientSide) {
                this.level.explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 5F, Explosion.Mode.BREAK);

                ServerWorld serverworld = (ServerWorld)this.level;
                BlockPos blockpos = (new BlockPos(this.getX(), this.getY(), this.getZ()));

                AltyrusEntity altyrusEntity = BioplethoraEntities.ALTYRUS.get().create(this.level);
                altyrusEntity.moveTo(blockpos, 0.0F, 0.0F);
                altyrusEntity.finalizeSpawn(serverworld, this.level.getCurrentDifficultyAt(blockpos), SpawnReason.MOB_SUMMONED, (ILivingEntityData)null, (CompoundNBT)null);

                serverworld.addFreshEntity(altyrusEntity);

                this.remove();
            }
        }
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
