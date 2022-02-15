package io.github.bioplethora.entity.creatures;

import io.github.bioplethora.BioplethoraConfig;
import io.github.bioplethora.entity.BPMonsterEntity;
import io.github.bioplethora.entity.IBioClassification;
import io.github.bioplethora.entity.ai.AlphemKingMeeleeGoal;
import io.github.bioplethora.entity.ai.AlphemKingSecondMeeleeGoal;
import io.github.bioplethora.entity.ai.AlphemKingSmashingGoal;
import io.github.bioplethora.entity.ai.monster.BPMonsterMoveToTargetGoal;
import io.github.bioplethora.enums.BPEntityClasses;
import io.github.bioplethora.registry.BioplethoraSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
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
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class AlphemKingEntity extends BPMonsterEntity implements IAnimatable, IBioClassification {

    protected static final DataParameter<Boolean> ATTACKING2 = EntityDataManager.defineId(BPMonsterEntity.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Boolean> SMASHING = EntityDataManager.defineId(BPMonsterEntity.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Boolean> ROARING = EntityDataManager.defineId(BPMonsterEntity.class, DataSerializers.BOOLEAN);
    private final AnimationFactory factory = new AnimationFactory(this);
    public boolean explodedOnDeath = false;
    public int attackPhase;

    public AlphemKingEntity(EntityType<? extends MonsterEntity> type, World world) {
        super(type, world);
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createLivingAttributes()
                .add(Attributes.ARMOR, 15 * BioplethoraConfig.COMMON.mobArmorMultiplier.get())
                .add(Attributes.ATTACK_SPEED, 10.5)
                .add(Attributes.ATTACK_DAMAGE, 15 * BioplethoraConfig.COMMON.mobMeeleeDamageMultiplier.get())
                .add(Attributes.ATTACK_KNOCKBACK, 8.0D)
                .add(Attributes.MAX_HEALTH, 270 * BioplethoraConfig.COMMON.mobHealthMultiplier.get())
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
        this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(5, new SwimGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
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

        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.alphem_king.walk", true));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.alphem_king.idle", true));
        return PlayState.CONTINUE;
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

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATTACKING2, false);
        this.entityData.define(SMASHING, false);
        this.entityData.define(ROARING, false);
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
}
