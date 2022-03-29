package io.github.bioplethora.entity.others;

import io.github.bioplethora.entity.creatures.AltyrusEntity;
import io.github.bioplethora.helpers.advancements.AdvancementUtils;
import io.github.bioplethora.registry.BPEntities;
import net.minecraft.entity.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
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

        this.setDeltaMovement(this.getDeltaMovement().add(0, 0.5, 0));

        if (this.level instanceof ServerWorld) {
            ((ServerWorld) this.level).sendParticles(ParticleTypes.POOF, (this.getX()), (this.getY()), (this.getZ()), (int) 5, 1, 1, 1, 0.1);
            //this.summonParticleBarrier((ServerWorld) this.level);
        }

        if (this.birthTime >= 100) {

            if (!this.level.isClientSide) {
                this.level.explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 5F, Explosion.Mode.BREAK);

                ServerWorld serverworld = (ServerWorld)this.level;
                BlockPos blockpos = (new BlockPos(this.getX(), this.getY(), this.getZ()));

                this.grantBirthAdvancement(32);

                AltyrusEntity altyrusEntity = BPEntities.ALTYRUS.get().create(this.level);
                altyrusEntity.moveTo(blockpos, 0.0F, 0.0F);
                altyrusEntity.finalizeSpawn(serverworld, this.level.getCurrentDifficultyAt(blockpos), SpawnReason.MOB_SUMMONED, (ILivingEntityData)null, (CompoundNBT)null);

                serverworld.addFreshEntity(altyrusEntity);

                this.remove();
            }
        }
    }

    public void summonParticleBarrier(ServerWorld serverWorld) {

        int loop = 0; int particleAmount = 10; int xRad = 3; int zRad = 3;

        if (loop < particleAmount) {
            serverWorld.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                    this.getX() + 0.5 + Math.cos(((Math.PI * 2) / particleAmount) * loop) * xRad,
                    this.getY(),
                    this.getZ() + 0.5 + Math.sin(((Math.PI * 2) / particleAmount) * loop) * zRad,
                    0.0, 1.0, 0.0);
            ++loop;
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

    public void grantBirthAdvancement(int radius) {
        Entity entity = this.getEntity();
        World world = entity.level;
        double x = entity.getX(), y = entity.getY(), z = entity.getZ();
        AxisAlignedBB area = new AxisAlignedBB(x - (radius / 2d), y, z - (radius / 2d), x + (radius / 2d), y + (radius / 2d), z + (radius / 2d));

        //Grant Advancement to all nearby players
        for (LivingEntity entityIterator : world.getEntitiesOfClass(LivingEntity.class, area)) {
            if (entityIterator != null) {
                AdvancementUtils.grantBioAdvancement(entityIterator, "bioplethora:altyrus_summoning");
            }
        }
    }
}
