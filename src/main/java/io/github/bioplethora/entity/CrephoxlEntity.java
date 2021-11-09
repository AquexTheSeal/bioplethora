package io.github.bioplethora.entity;

import io.github.bioplethora.entity.ai.AnimatableMeleeGoal;
import io.github.bioplethora.entity.ai.AnimatableMoveToTargetGoal;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class CrephoxlEntity extends AnimatableHostileEntity implements IAnimatable {

    private final AnimationFactory factory = new AnimationFactory(this);

    public CrephoxlEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
        this.noCulling = true;
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createLivingAttributes()
                .add(Attributes.ARMOR, 8)
                .add(Attributes.ATTACK_SPEED, 0.5)
                .add(Attributes.ATTACK_DAMAGE, 15)
                .add(Attributes.ATTACK_KNOCKBACK, 5D)
                .add(Attributes.MAX_HEALTH, 120)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.5)
                .add(Attributes.MOVEMENT_SPEED, 0.15)
                .add(Attributes.FOLLOW_RANGE, 32D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 24.0F));
        this.goalSelector.addGoal(2, new AnimatableMoveToTargetGoal(this, 1.6, 8));
        this.goalSelector.addGoal(2, new AnimatableMeleeGoal(this, 38.3, 0.5, 0.6));
        this.goalSelector.addGoal(5, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(7, new SwimGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this).setAlertOthers(CrephoxlEntity.class));
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
        return (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.hurt"));
    }

    @Override
    public net.minecraft.util.SoundEvent getDeathSound() {
        return (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.death"));
    }

    public boolean doHurtTarget (Entity p_70652_1_) {
        boolean flag = super.doHurtTarget(p_70652_1_);
        if (flag && p_70652_1_ instanceof LivingEntity)
            this.getTarget().setDeltaMovement(getTarget().getDeltaMovement().z+1-1, 2,getTarget().getDeltaMovement().z+1-1);
        if (this.level instanceof ServerWorld) {
            ((ServerWorld) this.level).sendParticles(ParticleTypes.POOF, (getTarget().getX()), (getTarget().getY()), (getTarget().getZ()), (int) 20, 0.4, 0.4,
                    0.4, 0.1);
            ((World) this.level).playSound(null, new BlockPos((int) getTarget().getX(), (int) getTarget().getY(), (int) getTarget().getZ()),
                    (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.explode")),
                    SoundCategory.NEUTRAL, (float) 1, (float) 1);
        }
        return flag;
    }
}
