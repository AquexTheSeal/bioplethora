package io.github.bioplethora.entity.creatures;

import io.github.bioplethora.BPConfig;
import io.github.bioplethora.entity.FloatingMonsterEntity;
import io.github.bioplethora.entity.IBioClassification;
import io.github.bioplethora.entity.IGrylynenTier;
import io.github.bioplethora.entity.ai.monster.BPMonsterMeleeGoal;
import io.github.bioplethora.enums.BPEntityClasses;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class GrylynenEntity extends FloatingMonsterEntity implements IAnimatable, IFlyingAnimal, IBioClassification {

    private final AnimationFactory factory = new AnimationFactory(this);
    private final IGrylynenTier tier;

    public GrylynenEntity(EntityType<? extends MonsterEntity> type, World worldIn, IGrylynenTier IGrylynenTier) {
        super(type, worldIn);
        this.noCulling = true;
        this.xpReward = 15;
        this.moveControl = new MoveHelperController(this);
        this.tier = IGrylynenTier;
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes(IGrylynenTier tier) {
        return MobEntity.createLivingAttributes()
                .add(Attributes.ARMOR, 0)
                .add(Attributes.ATTACK_SPEED, 10)
                .add(Attributes.ATTACK_KNOCKBACK, 0.5D)
                .add(Attributes.ATTACK_DAMAGE, tier.getTierDamage() * BPConfig.COMMON.mobMeeleeDamageMultiplier.get())
                .add(Attributes.MAX_HEALTH, !BPConfig.IN_HELLMODE ? tier.getTierHealth() : tier.getHellTierHP())
                .add(Attributes.MOVEMENT_SPEED, 0.25 * BPConfig.COMMON.mobMovementSpeedMultiplier.get())
                .add(Attributes.FLYING_SPEED, 0.45 * BPConfig.COMMON.mobMovementSpeedMultiplier.get())
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.2D)
                .add(Attributes.FOLLOW_RANGE, 32D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new WaterAvoidingRandomFlyingGoal(this, 1.2));
        this.goalSelector.addGoal(3, new GrylynenEntity.ChargeAttackGoal());
        this.goalSelector.addGoal(3, new BPMonsterMeleeGoal(this, 20, 0.7, 0.8));
        this.goalSelector.addGoal(4, new GrylynenEntity.MoveRandomGoal());
        this.goalSelector.addGoal(5, new SwimGoal(this));

        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 24.0F));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
        
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
    }

    @Override
    public BPEntityClasses getBioplethoraClass() {
        return BPEntityClasses.DANGERUM;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {

        if (this.isDeadOrDying()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.grylynen.death", true));
            return PlayState.CONTINUE;
        }
        if (this.getAttacking()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.grylynen.attack", true));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.grylynen.idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "grylynen_controller", 0, this::predicate));
    }

    public int getMaxSpawnClusterSize() {
        return 3;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source != DamageSource.OUT_OF_WORLD) {
            amount = 1;
        }
        return super.hurt(source, amount);
    }

    public void tick() {
        super.tick();
        double x = this.getX();
        double y = this.getY();
        double z = this.getZ();
        if (this.level instanceof ServerWorld) {
            ((ServerWorld) this.level).sendParticles(ParticleTypes.END_ROD, x, y, z, 5, 0.65, 0.65, 0.65, 0.01);
        }
    }

    @Override
    public boolean doHurtTarget(Entity pEntity) {
        return super.doHurtTarget(pEntity);
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public net.minecraft.util.SoundEvent getHurtSound(DamageSource damageSource) {
        return this.getGrylynenTier().getHurtSound();
    }

    @Override
    protected float getVoicePitch() {
        return 0.95F + (this.getRandom().nextFloat() / 2);
    }

    @Override
    public net.minecraft.util.SoundEvent getAmbientSound() {
        return SoundEvents.AXE_STRIP;
    }

    @Override
    public net.minecraft.util.SoundEvent getDeathSound() {
        return SoundEvents.BAMBOO_BREAK;
    }

    public IGrylynenTier getGrylynenTier() {
        return tier;
    }
}
