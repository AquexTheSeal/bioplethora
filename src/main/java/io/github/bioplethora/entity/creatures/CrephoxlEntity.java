package io.github.bioplethora.entity.creatures;

import io.github.bioplethora.config.BioplethoraConfig;
import io.github.bioplethora.entity.AnimatableMonsterEntity;
import io.github.bioplethora.entity.IBioplethoraEntityClass;
import io.github.bioplethora.entity.ai.monster.MonsterAnimatableMeleeGoal;
import io.github.bioplethora.entity.ai.monster.MonsterAnimatableMoveToTargetGoal;
import io.github.bioplethora.util.BioplethoraAdvancementHelper;
import io.github.bioplethora.util.BioplethoraEntityClasses;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.registries.ForgeRegistries;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class CrephoxlEntity extends AnimatableMonsterEntity implements IAnimatable, IBioplethoraEntityClass {

    private final AnimationFactory factory = new AnimationFactory(this);

    public CrephoxlEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
        this.noCulling = true;
    }

    @Override
    public BioplethoraEntityClasses getBioplethoraClass() {
        return BioplethoraEntityClasses.HELLSENT;
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createLivingAttributes()
                .add(Attributes.ARMOR, 8 * BioplethoraConfig.COMMON.mobArmorMultiplier.get())
                .add(Attributes.ATTACK_SPEED, 10)
                .add(Attributes.ATTACK_DAMAGE, 15 * BioplethoraConfig.COMMON.mobMeeleeDamageMultiplier.get())
                .add(Attributes.ATTACK_KNOCKBACK, 5D)
                .add(Attributes.MAX_HEALTH, 120 * BioplethoraConfig.COMMON.mobHealthMultiplier.get())
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.5)
                .add(Attributes.MOVEMENT_SPEED, 0.15 * BioplethoraConfig.COMMON.mobMovementSpeedMultiplier.get())
                .add(Attributes.FOLLOW_RANGE, 64D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 24.0F));
        this.goalSelector.addGoal(2, new MonsterAnimatableMoveToTargetGoal(this, 1.6, 8));
        this.goalSelector.addGoal(2, new MonsterAnimatableMeleeGoal(this, 38.3, 0.5, 0.6));
        this.goalSelector.addGoal(5, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(7, new SwimGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this).setAlertOthers(this.getClass()));
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (this.dead) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.crephoxl.death", true));
            return PlayState.CONTINUE;
        }

        if (this.getAttacking()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.crephoxl.attack", true));
            return PlayState.CONTINUE;
        }

        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.crephoxl.walking", true));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.crephoxl.idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "crephoxlcontroller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public net.minecraft.util.SoundEvent getHurtSound(DamageSource damageSource) {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.hurt"));
    }

    @Override
    public net.minecraft.util.SoundEvent getDeathSound() {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.death"));
    }

    public boolean doHurtTarget (Entity entity) {

        BlockPos blockPos = new BlockPos((int) getTarget().getX(), (int) getTarget().getY(), (int) getTarget().getZ());

        boolean flag = super.doHurtTarget(entity);
        if (flag && entity instanceof LivingEntity)
            this.getTarget().setDeltaMovement(getTarget().getDeltaMovement().x, 2 - getTarget().getAttributeValue(Attributes.KNOCKBACK_RESISTANCE), getTarget().getDeltaMovement().z);
        if (this.level instanceof ServerWorld) {
            ((ServerWorld) this.level).sendParticles(ParticleTypes.POOF, (getTarget().getX()), (getTarget().getY()), (getTarget().getZ()), (int) 20, 0.4, 0.4,
                    0.4, 0.1);
            this.level.playSound(null, blockPos, SoundEvents.GENERIC_EXPLODE, SoundCategory.NEUTRAL, (float) 1, (float) 1);
        }
        return flag;
    }

    @Override
    public void die(DamageSource source) {
        super.die(source);

        Entity sourceEnt = source.getEntity();
        BioplethoraAdvancementHelper.grantBioAdvancement(sourceEnt, "bioplethora:crephoxl_kill");
    }
}
