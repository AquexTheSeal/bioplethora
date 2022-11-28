package io.github.bioplethora.entity.creatures;

import io.github.bioplethora.blocks.api.world.EffectUtils;
import io.github.bioplethora.config.BPConfig;
import io.github.bioplethora.entity.BPMonsterEntity;
import io.github.bioplethora.entity.IBioClassification;
import io.github.bioplethora.entity.ai.gecko.GeckoMeleeGoal;
import io.github.bioplethora.entity.ai.gecko.GeckoMoveToTargetGoal;
import io.github.bioplethora.entity.ai.gecko.IGeckoBaseEntity;
import io.github.bioplethora.enums.BPEntityClasses;
import io.github.bioplethora.registry.BPParticles;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.monster.HoglinEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.piglin.PiglinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;

public class MyuthineEntity extends BPMonsterEntity implements IAnimatable, IBioClassification {
    private final AnimationFactory factory = new AnimationFactory(this);

    public MyuthineEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
        this.xpReward = 22;
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createLivingAttributes()
                .add(Attributes.ARMOR, 7 * BPConfig.COMMON.mobArmorMultiplier.get())
                .add(Attributes.ATTACK_SPEED, 10.5)
                .add(Attributes.ATTACK_DAMAGE, 8 * BPConfig.COMMON.mobMeeleeDamageMultiplier.get())
                .add(Attributes.ATTACK_KNOCKBACK, 1.8D)
                .add(Attributes.MAX_HEALTH, 60 * BPConfig.COMMON.mobHealthMultiplier.get())
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.6)
                .add(Attributes.MOVEMENT_SPEED, 0.35 * BPConfig.COMMON.mobMovementSpeedMultiplier.get())
                .add(Attributes.FOLLOW_RANGE, 48.0D);
    }

    @Override
    public BPEntityClasses getBioplethoraClass() {
        return BPEntityClasses.DANGERUM;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new GeckoMoveToTargetGoal<>(this, 1.2, 8));
        this.goalSelector.addGoal(2, new MyuthineMeleeGoal<>(this, 20, 0.6, 0.7));
        this.goalSelector.addGoal(4, new SwimGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PiglinEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, HoglinEntity.class, true));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this).setAlertOthers());
    }

    @Override
    public void aiStep() {
        super.aiStep();
    }

    @Override
    public boolean doHurtTarget(Entity pEntity) {
        SoundEvent hurtEffectSound = SoundEvents.NETHER_SPROUTS_BREAK;
        this.playSound(hurtEffectSound, 1.0F, 1.35F);
        EffectUtils.addCircleParticleForm(level, pEntity, BPParticles.RED_ENIVILE_LEAF.get(), 38, 0.3, 0.01);

        if (pEntity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) pEntity;
            if (random.nextInt(3) == 1) player.disableShield(true);
        }

        return super.doHurtTarget(pEntity);
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        SoundEvent hurtEffectSound = SoundEvents.NETHER_SPROUTS_BREAK;
        this.playSound(hurtEffectSound, 1.2F, 1.0F);
        EffectUtils.addCircleParticleForm(level, this, BPParticles.RED_ENIVILE_LEAF.get(), 45, 0.45, 0.001, 1);
        return super.hurt(pSource, pAmount);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.ZOMBIE_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ZOMBIE_VILLAGER_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ZOMBIE_DEATH;
    }

    @Override
    protected float getVoicePitch() {
        return 0.4F;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "myuthine_controller", 0, this::predicate));
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if(this.getAttacking()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.myuthine.attack", true));
            event.getController().transitionLengthTicks = 0;
            return PlayState.CONTINUE;
        }

        if(event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.myuthine.walk", true));
            event.getController().transitionLengthTicks = 5;
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.myuthine.idle", true));
        event.getController().transitionLengthTicks = 4;
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    public static class MyuthineMeleeGoal<E extends MobEntity> extends GeckoMeleeGoal<E> {

        public MyuthineMeleeGoal(E entity, double animationLength, double attackBegin, double attackEnd) {
            super(entity, animationLength, attackBegin, attackEnd);
        }

        public static boolean checkIfValid(GeckoMeleeGoal goal, MobEntity attacker, LivingEntity target) {
            if (target == null) return false;
            if (target.isAlive() && !target.isSpectator()) {
                if (target instanceof PlayerEntity && ((PlayerEntity) target).isCreative()) {
                    ((IGeckoBaseEntity) attacker).setAttacking(false);
                    return false;
                }
                double distance = goal.entity.distanceToSqr(target.getX(), target.getY(), target.getZ());
                if (distance <= getAttackReachSq(attacker, target)) return true;
            }
            ((IGeckoBaseEntity) attacker).setAttacking(false);
            return false;
        }

        protected static double getAttackReachSq(LivingEntity attacker, LivingEntity target) {
            return attacker.getBbWidth() * 5.5F * attacker.getBbWidth() * 5.5F + target.getBbWidth();
        }

        @Override
        public boolean canUse() {
            if (Math.random() <= 0.1) return false;

            return checkIfValid(this, entity, this.entity.getTarget());
        }

        @Override
        public boolean canContinueToUse() {
            if (Math.random() <= 0.1) return true;

            return checkIfValid(this, entity, this.entity.getTarget());
        }
    }
}
