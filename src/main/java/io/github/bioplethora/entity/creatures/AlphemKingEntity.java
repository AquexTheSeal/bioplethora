package io.github.bioplethora.entity.creatures;

import io.github.bioplethora.BioplethoraConfig;
import io.github.bioplethora.entity.BPMonsterEntity;
import io.github.bioplethora.entity.IBioClassification;
import io.github.bioplethora.entity.ai.*;
import io.github.bioplethora.entity.ai.monster.BPMonsterMoveToTargetGoal;
import io.github.bioplethora.enums.BPEntityClasses;
import io.github.bioplethora.registry.BioplethoraSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.Explosion;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;

public class AlphemKingEntity extends BPMonsterEntity implements IAnimatable, IBioClassification {

    protected static final DataParameter<Boolean> ATTACKING2 = EntityDataManager.defineId(AlphemKingEntity.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Boolean> SMASHING = EntityDataManager.defineId(AlphemKingEntity.class, DataSerializers.BOOLEAN);
    // TODO: 16/02/2022 Give a summoning effect to the Alphems.
    protected static final DataParameter<Boolean> ROARING = EntityDataManager.defineId(AlphemKingEntity.class, DataSerializers.BOOLEAN);
    // TODO: 16/02/2022 Add an animation for shooting/charging. Add a new and stronger projectile to replace the Windblaze projectile.
    protected static final DataParameter<Boolean> CHARGING = EntityDataManager.defineId(AlphemKingEntity.class, DataSerializers.BOOLEAN);
    // TODO: 16/02/2022 Add a jumping animation and a jumping AI goal.
    protected static final DataParameter<Boolean> JUMPING = EntityDataManager.defineId(AlphemKingEntity.class, DataSerializers.BOOLEAN);
    // TODO: 16/02/2022 Add a ramming animation and a ramming AI goal.
    protected static final DataParameter<Boolean> RAMMING = EntityDataManager.defineId(AlphemKingEntity.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Boolean> BARRIERED = EntityDataManager.defineId(AlphemKingEntity.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Boolean> BERSERKED = EntityDataManager.defineId(AlphemKingEntity.class, DataSerializers.BOOLEAN);
    private final AnimationFactory factory = new AnimationFactory(this);
    public boolean explodedOnDeath = false;
    public double healthRegenTimer = 0;
    public int attackPhase;
    public int barrierTimer;

    public AlphemKingEntity(EntityType<? extends MonsterEntity> type, World world) {
        super(type, world);
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createLivingAttributes()
                .add(Attributes.ARMOR, 15 * BioplethoraConfig.COMMON.mobArmorMultiplier.get())
                .add(Attributes.ATTACK_SPEED, 10.5)
                .add(Attributes.ATTACK_DAMAGE, 30 * BioplethoraConfig.COMMON.mobMeeleeDamageMultiplier.get())
                .add(Attributes.ATTACK_KNOCKBACK, 8.0D)
                .add(Attributes.MAX_HEALTH, 320 * BioplethoraConfig.COMMON.mobHealthMultiplier.get())
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.5)
                .add(Attributes.MOVEMENT_SPEED, 0.20F * BioplethoraConfig.COMMON.mobMovementSpeedMultiplier.get())
                .add(Attributes.FOLLOW_RANGE, 64.0D);
    }

    @Override
    public BPEntityClasses getBioplethoraClass() {
        return BPEntityClasses.ELDERIA;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 24.0F));
        this.goalSelector.addGoal(4, new RandomWalkingGoal(this, 0.5F));
        this.goalSelector.addGoal(1, new BPMonsterMoveToTargetGoal(this, 1.0F, 8));
        this.goalSelector.addGoal(1, new AlphemKingMeeleeGoal(this, 80, 0.5, 0.6));
        this.goalSelector.addGoal(1, new AlphemKingSecondMeeleeGoal(this, 80, 0.5, 0.6));
        this.goalSelector.addGoal(1, new AlphemKingSmashingGoal(this, 120, 0.8, 0.9));
        this.goalSelector.addGoal(2, new AlphemKingRangedAttackGoal(this));
        this.goalSelector.addGoal(3, new AlphemKingRoarGoal(this));
        this.goalSelector.addGoal(4, new AlphemKingJumpGoal(this));
        this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(5, new SwimGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AltyrusEntity.class, true));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, AlphemKingEntity.class)).setAlertOthers());
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "alphem_king_controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    private <E extends IAnimatable>PlayState predicate(AnimationEvent<E> event) {

        if (this.isDeadOrDying()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.alphem_king.death", true));
            return PlayState.CONTINUE;
        }
        if (this.isJumping()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.alphem_king.jump", true));
            return PlayState.CONTINUE;
        }
        if (this.getRoaring()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.alphem_king.roar", true));
            return PlayState.CONTINUE;
        }
        if (this.getSmashing()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.alphem_king.attack_2", true));
            return PlayState.CONTINUE;
        }
        if (this.getAttacking2()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.alphem_king.attack_1", true));
            return PlayState.CONTINUE;
        }
        if (this.getAttacking()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.alphem_king.attack_0", true));
            return PlayState.CONTINUE;
        }
        if (event.isMoving() && this.isBerserked()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.alphem_king.walk_berserk", true));
            return PlayState.CONTINUE;
        }
        if (event.isMoving() && !this.isBerserked()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.alphem_king.walk", true));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.alphem_king.idle", true));
        return PlayState.CONTINUE;
    }

    public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        if (worldIn instanceof ServerWorld && BioplethoraConfig.COMMON.hellMode.get()) {
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(35 * BioplethoraConfig.COMMON.mobMeeleeDamageMultiplier.get());
            this.getAttribute(Attributes.ARMOR).setBaseValue(17.5 * BioplethoraConfig.COMMON.mobArmorMultiplier.get());
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(380 * BioplethoraConfig.COMMON.mobHealthMultiplier.get());
            this.setHealth(380 * BioplethoraConfig.COMMON.mobHealthMultiplier.get());
        }
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    public void aiStep() {
        super.aiStep();
        World world = this.level;

        if (!this.isBarriered()) {
            ++barrierTimer;
             if (barrierTimer == 100 + this.getRandom().nextInt(100)) {
                 this.setBarriered(true);
                 barrierTimer = 0;
             }
        }

        this.setBerserked(this.getHealth() <= this.getMaxHealth() / 2);

        if (this.isBerserked()) {
            double d0 = this.random.nextGaussian() * 0.02D;
            double d1 = this.random.nextGaussian() * 0.02D;
            double d2 = this.random.nextGaussian() * 0.02D;
            world.addParticle(ParticleTypes.SOUL_FIRE_FLAME, this.getRandomX(1.0D), this.getRandomY(), this.getRandomZ(1.0D), d0, d1, d2);
            world.addParticle(ParticleTypes.POOF, this.getRandomX(1.0D), this.getRandomY(), this.getRandomZ(1.0D), d0, d1, d2);

            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.35F * BioplethoraConfig.COMMON.mobMovementSpeedMultiplier.get());
            if (!BioplethoraConfig.getHellMode) {
                this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(37.0F * BioplethoraConfig.COMMON.mobMeeleeDamageMultiplier.get());
            } else {
                this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(45.0F * BioplethoraConfig.COMMON.mobMeeleeDamageMultiplier.get());
            }

            if (!(this.getHealth() <= 5)) {
                ++healthRegenTimer;
                if (healthRegenTimer == 10) {
                    this.setHealth(this.getHealth() + 1 + this.getRandom().nextInt(2));
                    healthRegenTimer = 0;
                }
            }

        } else {
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.20F * BioplethoraConfig.COMMON.mobMovementSpeedMultiplier.get());
            if (!BioplethoraConfig.getHellMode) {
                this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(30.0F * BioplethoraConfig.COMMON.mobMeeleeDamageMultiplier.get());
            } else {
                this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(35.0F * BioplethoraConfig.COMMON.mobMeeleeDamageMultiplier.get());
            }
        }
    }

    public void phaseAttack(LivingEntity entity, World world) {

        float f1 = ((float)this.getAttributeValue(Attributes.ATTACK_KNOCKBACK) / 2);

        for (LivingEntity areaEnt : world.getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(5, 0.5, 5))) {

            if (areaEnt != this) {
                areaEnt.knockback(f1 * 0.5F, MathHelper.sin(this.yRot * ((float) Math.PI / 180F)), -MathHelper.cos(this.yRot * ((float) Math.PI / 180F)));
                areaEnt.setDeltaMovement(this.getDeltaMovement().add(0, 1.5 - entity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE), 0));
                areaEnt.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 100, 2));
            }
        }

        if (world instanceof ServerWorld) {
            ((ServerWorld) world).sendParticles(ParticleTypes.POOF, entity.getX(), entity.getY(), entity.getZ(),
                    25, 0.45, 0.45, 0.45, 0.01);
        }
    }

    public void phaseAttack2(LivingEntity entity, World world) {

        float f1 = (float)this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);

        for (LivingEntity areaEnt : world.getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(10, 0.5, 10))) {

            if (areaEnt != this) {
                areaEnt.knockback(f1 * 0.5F, MathHelper.sin(this.yRot * ((float) Math.PI / 180F)), -MathHelper.cos(this.yRot * ((float) Math.PI / 180F)));
                areaEnt.setDeltaMovement(this.getDeltaMovement().add(0, 0.5 - entity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE), 0));
                areaEnt.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 100, 2));
            }
        }

        if (world instanceof ServerWorld) {
            ((ServerWorld) world).sendParticles(ParticleTypes.CLOUD, entity.getX(), entity.getY(), entity.getZ(),
                    55, 0.75, 0.75, 0.75, 0.01);
        }
    }

    public void phaseSmashing(LivingEntity entity, World world) {

        float f1 = (float)this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);

        for (LivingEntity areaEnt : world.getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(15, 0.5, 15))) {

            if (areaEnt != this) {
                areaEnt.knockback(f1 * 0.5F, MathHelper.sin(this.yRot * ((float) Math.PI / 180F)), -MathHelper.cos(this.yRot * ((float) Math.PI / 180F)));
                areaEnt.setDeltaMovement(this.getDeltaMovement().add(0, 1.5 - entity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE), 0));
                areaEnt.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 100, 2));
            }
        }

        world.explode(this, entity.getX(), entity.getY(), entity.getZ(), 3.0F, Explosion.Mode.NONE);

        if (world instanceof ServerWorld) {
            ((ServerWorld) world).sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, entity.getX(), entity.getY(), entity.getZ(),
                    75, 0.4, 1.5, 0.4, 0.001);
        }
    }

    @Override
    protected void tickDeath() {

        ++this.deathTime;

        if (this.deathTime == 60) {
            this.level.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ANVIL_PLACE, SoundCategory.HOSTILE, 1.0F, 1.0F);
        }

        if (this.deathTime == 100) {

            if (!explodedOnDeath) {
                this.level.explode(this, this.getX(), this.getY(), this.getZ(), 3.0F, Explosion.Mode.NONE);
                explodedOnDeath = true;
            }
            this.remove();

            for (int i = 0; i < 100; ++i) {
                double d0 = this.random.nextGaussian() * 0.02D;
                double d1 = this.random.nextGaussian() * 0.02D;
                double d2 = this.random.nextGaussian() * 0.02D;

                this.level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, this.getRandomX(1.0D), this.getRandomY(), this.getRandomZ(1.0D), d0, d1, d2);
                this.level.addParticle(ParticleTypes.POOF, this.getRandomX(1.0D), this.getRandomY(), this.getRandomZ(1.0D), d0, d1, d2);
            }
        }
    }

    public boolean doHurtTarget(Entity entity) {
        boolean flag = super.doHurtTarget(entity);
        World world = entity.level;

        if (entity instanceof LivingEntity) {

            entity.invulnerableTime = 0;

            if (this.attackPhase == 0) {
                this.phaseAttack((LivingEntity) entity, world);
            }

            if (this.attackPhase == 1) {
                this.phaseAttack2((LivingEntity) entity, world);
            }

            if (this.attackPhase == 2) {
                this.phaseSmashing((LivingEntity) entity, world);
            }
        }

        return flag;
    }

    @Override
    public SoundEvent getAmbientSound() {
        return BioplethoraSoundEvents.BELLOPHGOLEM_IDLE.get();
    }

    @Override
    public SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.IRON_GOLEM_HURT;
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundEvents.IRON_GOLEM_DEATH;
    }

    @Override
    public void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.IRON_GOLEM_STEP, 0.15f, 1);
    }

    public SoundEvent getRoarSound() {
        return BioplethoraSoundEvents.ALPHEM_KING_ROAR.get();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATTACKING2, false);
        this.entityData.define(SMASHING, false);
        this.entityData.define(ROARING, false);
        this.entityData.define(CHARGING, false);
        this.entityData.define(JUMPING, false);
        this.entityData.define(RAMMING, false);
        this.entityData.define(BARRIERED, false);
        this.entityData.define(BERSERKED, false);
    }

    public boolean getAttacking2() {
        return this.entityData.get(ATTACKING2);
    }

    public void setAttacking2(boolean attacking2) {
        this.entityData.set(ATTACKING2, attacking2);
    }

    public boolean getSmashing() {
        return this.entityData.get(SMASHING);
    }

    public void setSmashing(boolean smashing) {
        this.entityData.set(SMASHING, smashing);
    }

    public boolean getRoaring() {
        return this.entityData.get(ROARING);
    }

    public void setRoaring(boolean smashing) {
        this.entityData.set(ROARING, smashing);
    }

    public boolean isCharging() {
        return this.entityData.get(CHARGING);
    }

    public void setCharging(boolean charging) {
        this.entityData.set(CHARGING, charging);
    }

    public boolean isJumping() {
        return this.entityData.get(JUMPING);
    }

    public void setJumping(boolean jumping) {
        this.entityData.set(JUMPING, jumping);
    }

    public boolean isRamming() {
        return this.entityData.get(RAMMING);
    }

    public void setRamming(boolean ramming) {
        this.entityData.set(RAMMING, ramming);
    }

    public boolean isBarriered() {
        return this.entityData.get(BARRIERED);
    }

    public void setBarriered(boolean barriered) {
        this.entityData.set(BARRIERED, barriered);
    }

    public boolean isBerserked() {
        return this.entityData.get(BERSERKED);
    }

    public void setBerserked(boolean berserked) {
        this.entityData.set(BERSERKED, berserked);
    }

}