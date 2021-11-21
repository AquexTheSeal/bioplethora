package io.github.bioplethora.entity;

import io.github.bioplethora.config.BioplethoraConfig;
import io.github.bioplethora.entity.ai.AnimatableMeleeGoal;
import io.github.bioplethora.entity.ai.AnimatableMoveToTargetGoal;
import io.github.bioplethora.entity.ai.BellophiteClusterRangedAttackGoal;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
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
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
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

public class BellophgolemEntity extends AnimatableHostileEntity implements IAnimatable {

    private static final DataParameter<Boolean> DATA_IS_CHARGING = EntityDataManager.defineId(BellophgolemEntity.class, DataSerializers.BOOLEAN);
    private final AnimationFactory factory = new AnimationFactory(this);

    public BellophgolemEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
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
        this.goalSelector.addGoal(2, new AnimatableMoveToTargetGoal(this, 1.6, 8));
        this.goalSelector.addGoal(2, new AnimatableMeleeGoal(this, 60, 0.6, 0.7));
        this.goalSelector.addGoal(5, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(7, new SwimGoal(this));
        this.goalSelector.addGoal(6, new BellophiteClusterRangedAttackGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this).setAlertOthers(this.getClass()));
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
    public net.minecraft.util.SoundEvent getHurtSound(DamageSource damageSource) {
        return (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.iron_golem.hurt"));
    }

    @Override
    public net.minecraft.util.SoundEvent getDeathSound() {
        return (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.iron_golem.death"));
    }

    @Override
    public void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound((net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.iron_golem.step")),
                0.15f, 1);
    }


    public boolean doHurtTarget (Entity entity) {
        boolean flag = super.doHurtTarget(entity);
        /*f (flag && entity instanceof LivingEntity)*/
        if (this.level instanceof ServerWorld) {
            ((World) this.level).explode(null, (int) getTarget().getX(), (int) getTarget().getY(), (int) getTarget().getZ(), (float) 1.2, Explosion.Mode.NONE);
            ((ServerWorld) this.level).sendParticles(ParticleTypes.POOF, (getTarget().getX()), (getTarget().getY()), (getTarget().getZ()), (int) 20, 0.4, 0.4,
                    0.4, 0.1);
            }
        return flag;
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
