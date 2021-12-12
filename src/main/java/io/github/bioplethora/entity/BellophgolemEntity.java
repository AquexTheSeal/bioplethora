package io.github.bioplethora.entity;

import io.github.bioplethora.config.BioplethoraConfig;
import io.github.bioplethora.entity.ai.BellophgolemCopyTargetOwnerGoal;
import io.github.bioplethora.entity.ai.BellophiteClusterRangedAttackGoal;
import io.github.bioplethora.entity.ai.monster.MonsterAnimatableMeleeGoal;
import io.github.bioplethora.entity.ai.monster.MonsterAnimatableMoveToTargetGoal;
import io.github.bioplethora.registry.BioplethoraSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.VexEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.Explosion;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;

public class BellophgolemEntity extends AnimatableMonsterEntity implements IAnimatable {

    private static final DataParameter<Boolean> DATA_IS_CHARGING = EntityDataManager.defineId(BellophgolemEntity.class, DataSerializers.BOOLEAN);
    private final AnimationFactory factory = new AnimationFactory(this);
    private boolean hasCracked = false;
    private MobEntity owner;
    private boolean hasLimitedLife;
    private int limitedLifeTicks;

    public BellophgolemEntity(EntityType<? extends BellophgolemEntity> type, World worldIn) {
        super(type, worldIn);
        this.noCulling = true;
        this.xpReward = 20;
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createLivingAttributes()
                .add(Attributes.ARMOR, 10 * BioplethoraConfig.COMMON.mobArmorMultiplier.get())
                .add(Attributes.ATTACK_SPEED, 10)
                .add(Attributes.ATTACK_DAMAGE, 23 * BioplethoraConfig.COMMON.mobMeeleeDamageMultiplier.get())
                .add(Attributes.ATTACK_KNOCKBACK, 7D)
                .add(Attributes.MAX_HEALTH, 220 * BioplethoraConfig.COMMON.mobHealthMultiplier.get())
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.5)
                .add(Attributes.MOVEMENT_SPEED, 0.2 * BioplethoraConfig.COMMON.mobMovementSpeedMultiplier.get())
                .add(Attributes.FOLLOW_RANGE, 64D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 24.0F));
        this.goalSelector.addGoal(3, new LookAtGoal(this, AlphemEntity.class, 24.0F));
        this.goalSelector.addGoal(2, new MonsterAnimatableMoveToTargetGoal(this, 1.6, 8));
        /*this.goalSelector.addGoal(1, new BellophgolemAnimatedSmashingGoal(this, 100, 1, 1.1));*/
        this.goalSelector.addGoal(2, new MonsterAnimatableMeleeGoal(this, 60, 0.6, 0.7));
        this.goalSelector.addGoal(5, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(7, new SwimGoal(this));
        this.goalSelector.addGoal(6, new BellophiteClusterRangedAttackGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AlphemEntity.class, true));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this).setAlertOthers(this.getClass()));
        this.targetSelector.addGoal(1, new BellophgolemCopyTargetOwnerGoal(this, this));
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (this.dead) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.bellophgolem.death", true));
            return PlayState.CONTINUE;
        }

        /*if (this.getSmashing()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.bellophgolem.smashing", true));
            return PlayState.CONTINUE;
        }*/

        if (this.getAttacking()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.bellophgolem.attack", true));
            return PlayState.CONTINUE;
        }

        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.bellophgolem.walking", true));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.bellophgolem.idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "bellophgolemcontroller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public SoundEvent getAmbientSound() {
        return BioplethoraSoundEvents.BELLOPHGOLEM_IDLE.get();
    }

    @Override
    public SoundEvent getHurtSound(DamageSource damageSource) {
        return BioplethoraSoundEvents.BELLOPHGOLEM_HURT.get();
    }

    @Override
    public SoundEvent getDeathSound() {
        return BioplethoraSoundEvents.BELLOPHGOLEM_DEATH.get();
    }

    @Override
    public void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.IRON_GOLEM_STEP, 0.15f, 1);
    }


    public boolean doHurtTarget (Entity entity) {
        boolean flag = super.doHurtTarget(entity);
        /*f (flag && entity instanceof LivingEntity)*/
        if (this.getHealth() <= 100 && BioplethoraConfig.COMMON.hellMode.get()) {
            if (this.level instanceof ServerWorld) {
                ((World) this.level).explode(null, (int) getTarget().getX(), (int) getTarget().getY(), (int) getTarget().getZ(), (float) 2, Explosion.Mode.BREAK);
                ((ServerWorld) this.level).sendParticles(ParticleTypes.POOF, (getTarget().getX()), (getTarget().getY()), (getTarget().getZ()), (int) 40, 0.75, 0.75,
                        0.75, 0.1);
            }
        } else {
            if (this.level instanceof ServerWorld) {
                ((World) this.level).explode(null, (int) getTarget().getX(), (int) getTarget().getY(), (int) getTarget().getZ(), (float) 1.2, Explosion.Mode.NONE);
                ((ServerWorld) this.level).sendParticles(ParticleTypes.POOF, (getTarget().getX()), (getTarget().getY()), (getTarget().getZ()), (int) 20, 0.4, 0.4,
                        0.4, 0.1);
            }
        }
        this.doEnchantDamageEffects(this, entity);
        return flag;
    }

    public void addAdditionalSaveData(CompoundNBT compoundNBT) {
        super.addAdditionalSaveData(compoundNBT);
        if (this.hasLimitedLife) {
            compoundNBT.putInt("LifeTicks", this.limitedLifeTicks);
        }
    }

    public void setLimitedLife(int limitedLife) {
        this.hasLimitedLife = true;
        this.limitedLifeTicks = limitedLife;
    }

    public void aiStep() {
        super.aiStep();
        if (((LivingEntity) this.getEntity()).getHealth() <= 100 && !BioplethoraConfig.COMMON.hellMode.get()) {
            ((LivingEntity) this.getEntity()).addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, 5, 1));
            ((LivingEntity) this.getEntity()).addEffect(new EffectInstance(Effects.REGENERATION, 5, 1));
        }

        if (((LivingEntity) this.getEntity()).getHealth() <= 100 && BioplethoraConfig.COMMON.hellMode.get()) {
            ((LivingEntity) this.getEntity()).addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, 5, 2));
            ((LivingEntity) this.getEntity()).addEffect(new EffectInstance(Effects.REGENERATION, 5, 1));
        }

        if (this.getHealth() <= 100 && !this.hasCracked) {
            this.playSound(SoundEvents.IRON_GOLEM_DAMAGE, 1.0F, 1.0F);
            this.hasCracked = true;
        }

        if (this.hasLimitedLife) {
            ++limitedLifeTicks;

            if (this.limitedLifeTicks >= 200) {
                this.remove();
            }
        }

        if (this.getOwner() != null) {
            if (!this.level.isClientSide && this.getOwner().isDeadOrDying()) {
                this.level.explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 3F, Explosion.Mode.BREAK);
                this.kill();
            }
        }
    }

    public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        if (worldIn instanceof ServerWorld && BioplethoraConfig.COMMON.hellMode.get()) {
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(30 * BioplethoraConfig.COMMON.mobMeeleeDamageMultiplier.get());
            this.getAttribute(Attributes.ARMOR).setBaseValue(12.5 * BioplethoraConfig.COMMON.mobArmorMultiplier.get());
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(245 * BioplethoraConfig.COMMON.mobHealthMultiplier.get());
            this.setHealth(245 * BioplethoraConfig.COMMON.mobHealthMultiplier.get());
        }
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    public MobEntity getOwner() {
        return this.owner;
    }

    public void setOwner(MobEntity mobEntity) {
        this.owner = mobEntity;
    }

    @Override
    public boolean ignoreExplosion() {
        return true;
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isCharging() {
        return this.entityData.get(DATA_IS_CHARGING);
    }

    public void setCharging(boolean charging) {
        this.entityData.set(DATA_IS_CHARGING, charging);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_IS_CHARGING, false);
    }
}
