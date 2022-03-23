package io.github.bioplethora.entity.others;

import io.github.bioplethora.BioplethoraConfig;
import io.github.bioplethora.entity.IGrylynenTier;
import io.github.bioplethora.entity.creatures.GrylynenEntity;
import io.github.bioplethora.enums.BPGrylynenTier;
import io.github.bioplethora.event.helper.GrylynenSpawnHelper;
import io.github.bioplethora.registry.BPEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
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

public class GrylynenCoreBombEntity extends Entity implements IAnimatable {

    private final AnimationFactory factory = new AnimationFactory(this);
    public IGrylynenTier tier;
    public int birthTime = 0;
    public boolean hasSound;

    public GrylynenCoreBombEntity(EntityType<?> entityType, World world) {
        super(entityType, world);
        this.hasSound = false;
    }

    @Override
    protected void defineSynchedData() {
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.grylynen_core_bomb.idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "grylynen_core_bomb_controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    public void tick() {
        super.tick();

        ++birthTime;
        this.level.addParticle(ParticleTypes.FLAME,
                getX() + (Math.random() / 2), getY() + (Math.random() / 2), getZ() + (Math.random() / 2),
                0.01D, 0.01D, 0.01D);

        if (!hasSound) {
            this.playSound(SoundEvents.BEACON_ACTIVATE, 1.0F, 1.5F);
            hasSound = true;
        }

        if (this.birthTime >= (BioplethoraConfig.getHellMode ? 40 : 60)) {

            if (!this.level.isClientSide) {
                ServerWorld serverworld = (ServerWorld) this.level;
                BlockPos blockpos = (new BlockPos(this.getX(), this.getY(), this.getZ()));

                GrylynenEntity grylynen = BPEntities.WOODEN_GRYLYNEN.get().create(this.level);

                if (this.tier == BPGrylynenTier.WOODEN) {
                    grylynen = BPEntities.WOODEN_GRYLYNEN.get().create(this.level);
                } else if (this.tier == BPGrylynenTier.STONE) {
                    grylynen = BPEntities.STONE_GRYLYNEN.get().create(this.level);
                } else if (this.tier == BPGrylynenTier.GOLDEN) {
                    grylynen = BPEntities.GOLDEN_GRYLYNEN.get().create(this.level);
                } else if (this.tier == BPGrylynenTier.IRON) {
                    grylynen = BPEntities.IRON_GRYLYNEN.get().create(this.level);
                } else if (this.tier == BPGrylynenTier.DIAMOND) {
                    grylynen = BPEntities.DIAMOND_GRYLYNEN.get().create(this.level);
                } else if (this.tier == BPGrylynenTier.NETHERITE) {
                    grylynen = BPEntities.NETHERITE_GRYLYNEN.get().create(this.level);
                }

                grylynen.moveTo(blockpos, 0.0F, 0.0F);
                grylynen.finalizeSpawn(serverworld, level.getCurrentDifficultyAt(blockpos), SpawnReason.MOB_SUMMONED, null, null);

                if (!level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
                    GrylynenSpawnHelper.breakSurroundingBlocks(serverworld, blockpos);
                }
                this.playSound(SoundEvents.SLIME_BLOCK_BREAK, 1.0F, 0.5F);
                if (!this.level.isClientSide()) {
                    ((ServerWorld) level).sendParticles(ParticleTypes.FLAME, getX(), getY(), getZ(), 30, 0.75, 0.75, 0.75, 0.01);
                }
                serverworld.addFreshEntity(grylynen);

                this.remove();
            }
        }
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        this.remove();
        return super.hurt(pSource, pAmount);
    }

    public void setTier(IGrylynenTier tier) {
        this.tier = tier;
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
