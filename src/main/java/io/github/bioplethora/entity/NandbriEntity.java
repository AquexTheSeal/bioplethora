package io.github.bioplethora.entity;

import io.github.bioplethora.config.BioplethoraConfig;
import io.github.bioplethora.entity.ai.AnimatableMeleeGoal;
import io.github.bioplethora.entity.ai.AnimatableMoveToTargetGoal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class NandbriEntity extends AnimatableHostileEntity implements IAnimatable {
    private final AnimationFactory factory = new AnimationFactory(this);

    public NandbriEntity(EntityType<? extends MonsterEntity> type, World world) {
        super(type, world);
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createLivingAttributes()
                .add(Attributes.ARMOR, 2 * BioplethoraConfig.COMMON.mobArmorMultiplier.get())
                .add(Attributes.ATTACK_SPEED, 2)
                .add(Attributes.ATTACK_DAMAGE, 2 * BioplethoraConfig.COMMON.mobMeeleeDamageMultiplier.get())
                .add(Attributes.ATTACK_KNOCKBACK, 0.5D)
                .add(Attributes.MAX_HEALTH, 2 * BioplethoraConfig.COMMON.mobHealthMultiplier.get())
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.5)
                .add(Attributes.MOVEMENT_SPEED, 2 * BioplethoraConfig.COMMON.mobMovementSpeedMultiplier.get())
                .add(Attributes.FOLLOW_RANGE, 1D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 24.0F));
        this.goalSelector.addGoal(2, new AnimatableMoveToTargetGoal(this, 1.6, 8));
        this.goalSelector.addGoal(2, new AnimatableMeleeGoal(this, 40, 0.5, 0.6));
        this.goalSelector.addGoal(1, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(7, new SwimGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers(this.getClass()));
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "nandbri_controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    private <E extends IAnimatable>PlayState predicate(AnimationEvent<E> event) {
        if(this.getAttacking()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.nandbri.attack", true));
            return PlayState.CONTINUE;
        }

        if(event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.nandbri.walk", true));
            return PlayState.CONTINUE;
        }

        return PlayState.CONTINUE;
    }

    public boolean doHurtTarget(Entity entity) {
        boolean flag = super.doHurtTarget(entity);
        if(flag && entity instanceof LivingEntity) {
            ((LivingEntity) entity).addEffect(new EffectInstance(Effects.POISON, 200));
        }
        return flag;
    }
}
