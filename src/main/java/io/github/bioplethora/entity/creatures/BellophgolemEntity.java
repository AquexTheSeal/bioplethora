package io.github.bioplethora.entity.creatures;

import io.github.bioplethora.api.advancements.AdvancementUtils;
import io.github.bioplethora.config.BPConfig;
import io.github.bioplethora.entity.IBioClassification;
import io.github.bioplethora.entity.SummonableMonsterEntity;
import io.github.bioplethora.entity.ai.gecko.GeckoDodgeableMeleeGoal;
import io.github.bioplethora.entity.ai.gecko.GeckoMoveToTargetGoal;
import io.github.bioplethora.entity.ai.goals.BellophgolemRangedAttackGoal;
import io.github.bioplethora.entity.ai.goals.CopyTargetOwnerGoal;
import io.github.bioplethora.enums.BPEntityClasses;
import io.github.bioplethora.registry.BPSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.Explosion;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;

public class BellophgolemEntity extends SummonableMonsterEntity implements IAnimatable, IBioClassification {

    private static final DataParameter<Boolean> DATA_IS_CHARGING = EntityDataManager.defineId(BellophgolemEntity.class, DataSerializers.BOOLEAN);
    private final AnimationFactory factory = new AnimationFactory(this);
    private boolean hasCracked = false;

    public BellophgolemEntity(EntityType<? extends BellophgolemEntity> type, World worldIn) {
        super(type, worldIn);
        this.noCulling = true;
        this.xpReward = 20;
    }

    @Override
    public BPEntityClasses getBioplethoraClass() {
        return BPEntityClasses.HELLSENT;
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createLivingAttributes()
                .add(Attributes.ARMOR, 10 * BPConfig.COMMON.mobArmorMultiplier.get())
                .add(Attributes.ATTACK_SPEED, 10)
                .add(Attributes.ATTACK_DAMAGE, 18.5 * BPConfig.COMMON.mobMeeleeDamageMultiplier.get())
                .add(Attributes.ATTACK_KNOCKBACK, 6D)
                .add(Attributes.MAX_HEALTH, 205 * BPConfig.COMMON.mobHealthMultiplier.get())
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.5)
                .add(Attributes.MOVEMENT_SPEED, 0.23 * BPConfig.COMMON.mobMovementSpeedMultiplier.get())
                .add(Attributes.FOLLOW_RANGE, 64D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 24.0F));
        this.goalSelector.addGoal(3, new LookAtGoal(this, AlphemEntity.class, 24.0F));
        this.goalSelector.addGoal(2, new GeckoMoveToTargetGoal<>(this, 1.6, 8));
        //this.goalSelector.addGoal(1, new BellophgolemAnimatedSmashingGoal(this));
        this.goalSelector.addGoal(2, new GeckoDodgeableMeleeGoal<>(this, 60, 0.6, 0.7));
        this.goalSelector.addGoal(5, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(7, new SwimGoal(this));
        this.goalSelector.addGoal(6, new BellophgolemRangedAttackGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AlphemEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AlphemKingEntity.class, true));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this, BellophgolemEntity.class, AltyrusEntity.class).setAlertOthers(this.getClass()));
        this.targetSelector.addGoal(1, new CopyTargetOwnerGoal(this));
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
        return BPSoundEvents.BELLOPHGOLEM_IDLE.get();
    }

    @Override
    public SoundEvent getHurtSound(DamageSource damageSource) {
        return BPSoundEvents.BELLOPHGOLEM_HURT.get();
    }

    @Override
    public SoundEvent getDeathSound() {
        return BPSoundEvents.BELLOPHGOLEM_DEATH.get();
    }

    @Override
    public void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.IRON_GOLEM_STEP, 0.15f, 1);
    }

    @Override
    public void swing(Hand pHand, boolean pUpdateSelf) {
        super.swing(pHand, pUpdateSelf);

        double d0 = -MathHelper.sin(this.yRot * ((float)Math.PI / 180F)) * 2.5;
        double d1 = MathHelper.cos(this.yRot * ((float)Math.PI / 180F)) * 2.5;

        if (!level.isClientSide()) {
            if (this.getHealth() <= 100 && BPConfig.IN_HELLMODE) {
                if (this.level instanceof ServerWorld) {
                    this.level.explode(null, getX() + d0, getY(), getZ() + d1, 2F, Explosion.Mode.BREAK);
                    ((ServerWorld) this.level).sendParticles(ParticleTypes.POOF, getX() + d0, getY(), getZ() + d1, 40, 0.75, 0.75,
                            0.75, 0.1);
                }
            } else {
                if (this.level instanceof ServerWorld) {
                    this.level.explode(null, getX() + d0, getY(), getZ() + d1, 1.2F, Explosion.Mode.NONE);
                    ((ServerWorld) this.level).sendParticles(ParticleTypes.POOF, getX() + d0, getY(), getZ() + d1, 20, 0.4, 0.4,
                            0.4, 0.1);
                }
            }
        }
    }

    public boolean doHurtTarget(Entity entity) {
        boolean flag = super.doHurtTarget(entity);
        /*f (flag && entity instanceof LivingEntity)*/

        this.doEnchantDamageEffects(this, entity);
        return flag;
    }

    @Override
    public void die(DamageSource source) {
        super.die(source);
        Entity sourceEnt = source.getEntity();

        AdvancementUtils.grantBioAdvancement(sourceEnt, "bioplethora:bellophgolem_kill");
    }

    public void aiStep() {
        super.aiStep();

        if (((LivingEntity) this.getEntity()).getHealth() <= 100 && BPConfig.COMMON.hellMode.get()) {
            ((LivingEntity) this.getEntity()).addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, 5, 1));
        }

        if (this.getHealth() <= 100 && !this.hasCracked) {
            this.playSound(SoundEvents.IRON_GOLEM_DAMAGE, 1.0F, 1.0F);
            this.hasCracked = true;
        }
    }

    public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        if (worldIn instanceof ServerWorld && BPConfig.COMMON.hellMode.get()) {
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(30 * BPConfig.COMMON.mobMeeleeDamageMultiplier.get());
            this.getAttribute(Attributes.ARMOR).setBaseValue(12.5 * BPConfig.COMMON.mobArmorMultiplier.get());
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(245 * BPConfig.COMMON.mobHealthMultiplier.get());
            this.setHealth(245 * BPConfig.COMMON.mobHealthMultiplier.get());
        }
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
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
