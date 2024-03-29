package io.github.bioplethora.entity.creatures;

import io.github.bioplethora.config.BPConfig;
import io.github.bioplethora.entity.FloatingCreatureEntity;
import io.github.bioplethora.entity.IBioClassification;
import io.github.bioplethora.enums.BPEntityClasses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;
import java.util.Random;

public class OnofishEntity extends FloatingCreatureEntity implements IAnimatable, IBioClassification {
    private static final DataParameter<Integer> DATA_VARIANT = EntityDataManager.defineId(OnofishEntity.class, DataSerializers.INT);
    private final AnimationFactory factory = new AnimationFactory(this);
    public int particleTime;
    public int jumpTime;

    public OnofishEntity(EntityType<? extends FloatingCreatureEntity> type, World worldIn) {
        super(type, worldIn);
        this.moveControl = new MoveHelperController(this);
    }

    @Override
    public BPEntityClasses getBioplethoraClass() {
        return BPEntityClasses.ECOHARMLESS;
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createLivingAttributes()
                .add(Attributes.ARMOR, 2 * BPConfig.COMMON.mobArmorMultiplier.get())
                .add(Attributes.ATTACK_SPEED, 1.5)
                .add(Attributes.ATTACK_DAMAGE, 2 * BPConfig.COMMON.mobMeeleeDamageMultiplier.get())
                .add(Attributes.ATTACK_KNOCKBACK, 0.5 * BPConfig.COMMON.mobMeeleeDamageMultiplier.get())
                .add(Attributes.MAX_HEALTH, 10 * BPConfig.COMMON.mobHealthMultiplier.get())
                .add(Attributes.MOVEMENT_SPEED, 0.25 * BPConfig.COMMON.mobMovementSpeedMultiplier.get())
                .add(Attributes.FOLLOW_RANGE, 32D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new MoveRandomGoal());
        this.goalSelector.addGoal(2, new LookAtGoal(this, PlayerEntity.class, 24.0F));
        this.goalSelector.addGoal(3, new LookRandomlyGoal(this));
    }

    @Override
    public void aiStep() {
        super.aiStep();
        ++this.particleTime;
        if (this.particleTime == 40) {
            double d0 = this.random.nextGaussian() * 0.02D;
            double d1 = this.random.nextGaussian() * 0.02D;
            double d2 = this.random.nextGaussian() * 0.02D;
            this.level.addParticle(ParticleTypes.FIREWORK, this.getRandomX(1.0D), this.getRandomY(), this.getRandomZ(1.0D), d0, d1, d2);
            particleTime = 0;
        }

        ++this.jumpTime;
        if (this.jumpTime == 200) {
            this.setDeltaMovement(0, this.random.nextBoolean() ? .15 : -.20, 0);
            this.playSound(SoundEvents.BUBBLE_COLUMN_BUBBLE_POP, 1, 1);
            jumpTime = this.random.nextInt(50);
        }
    }

    @Override
    protected void tickDeath() {
        ++this.deathTime;
        if (this.deathTime == 20) {
            this.remove();

            for (int i = 0; i < 20; ++i) {
                double d0 = this.random.nextGaussian() * 0.02D;
                double d1 = this.random.nextGaussian() * 0.02D;
                double d2 = this.random.nextGaussian() * 0.02D;
                this.level.addParticle(ParticleTypes.POOF, this.getRandomX(1.0D), this.getRandomY(), this.getRandomZ(1.0D), d0, d1, d2);
                this.level.addParticle(ParticleTypes.FIREWORK, this.getRandomX(1.0D), this.getRandomY(), this.getRandomZ(1.0D), d0, d1, d2);
            }
        }
    }

    public static boolean checkOnofishSpawnRules(EntityType<OnofishEntity> onofishEntityEntityType, IWorld pLevel, SpawnReason pSpawnType, BlockPos pPos, Random pRandom) {
        return pPos.getY() > 30 && pRandom.nextInt(4) == 1;
    }

    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld pLevel, DifficultyInstance pDifficulty, SpawnReason pReason, @Nullable ILivingEntityData pSpawnData, @Nullable CompoundNBT pDataTag) {
        this.setVariant(MathHelper.clamp(1 + this.random.nextInt(5), 1, 4));

        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 1;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_VARIANT, 1);
    }

    public int getVariant() {
        return this.entityData.get(DATA_VARIANT);
    }

    public void setVariant(int value) {
        this.entityData.set(DATA_VARIANT, value);
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("ono_variant", getVariant());
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT pCompound) {
        super.readAdditionalSaveData(pCompound);
        setVariant(pCompound.getInt("ono_variant"));
    }

    @Override
    protected float getVoicePitch() {
        return 2.0F + this.random.nextFloat();
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.SQUID_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.SLIME_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.SQUID_DEATH;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {

        if (this.isDeadOrDying()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.onofish.death", true));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.onofish.idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "myliothan_controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}
