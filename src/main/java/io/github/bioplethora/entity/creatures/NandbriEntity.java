package io.github.bioplethora.entity.creatures;

import io.github.bioplethora.BioplethoraConfig;
import io.github.bioplethora.entity.BPMonsterEntity;
import io.github.bioplethora.entity.IBioClassification;
import io.github.bioplethora.entity.ai.NandbriScratchAttackGoal;
import io.github.bioplethora.entity.ai.monster.BPMonsterMoveToTargetGoal;
import io.github.bioplethora.entity.ai.NandbriBiteAttackGoal;
import io.github.bioplethora.enums.BPEntityClasses;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class NandbriEntity extends BPMonsterEntity implements IAnimatable, IBioClassification {
    public int attackPhase;
    protected static final DataParameter<Boolean> SCRATCHING = EntityDataManager.defineId(NandbriEntity.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Boolean> SPITTING = EntityDataManager.defineId(NandbriEntity.class, DataSerializers.BOOLEAN);

    // TODO: Toxic Spit Attack
    // public int timeBeforeSpit = 60;

    private final AnimationFactory factory = new AnimationFactory(this);

    public NandbriEntity(EntityType<? extends MonsterEntity> type, World world) {
        super(type, world);
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createLivingAttributes()
                .add(Attributes.ARMOR, 6 * BioplethoraConfig.COMMON.mobArmorMultiplier.get())
                .add(Attributes.ATTACK_SPEED, 10.5)
                .add(Attributes.ATTACK_DAMAGE, 4 * BioplethoraConfig.COMMON.mobMeeleeDamageMultiplier.get())
                .add(Attributes.ATTACK_KNOCKBACK, 0.25D)
                .add(Attributes.MAX_HEALTH, 50 * BioplethoraConfig.COMMON.mobHealthMultiplier.get())
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.4)
                .add(Attributes.MOVEMENT_SPEED, 0.6 * BioplethoraConfig.COMMON.mobMovementSpeedMultiplier.get())
                .add(Attributes.FOLLOW_RANGE, 32.0D);
    }

    @Override
    public BPEntityClasses getBioplethoraClass() {
        return BPEntityClasses.PLETHONEUTRAL;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 24.0F));
        this.goalSelector.addGoal(4, new RandomWalkingGoal(this, 0.5F));
        this.goalSelector.addGoal(1, new BPMonsterMoveToTargetGoal(this, 0.75, 8));
        this.goalSelector.addGoal(1, new NandbriBiteAttackGoal(this, 16, 0.45, 0.75));
        this.goalSelector.addGoal(1, new NandbriScratchAttackGoal(this, 16.8, 0.23, 0.38));
        this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(5, new SwimGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AlphemEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, GolemEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, DwarfMossadileEntity.class, true));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, NandbriEntity.class)).setAlertOthers());
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

        if(this.getScratching()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.nandbri.scratch", true));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.nandbri.idle", true));
        return PlayState.CONTINUE;
    }

    public boolean doHurtTarget(Entity entity) {
        boolean flag = super.doHurtTarget(entity);
        World world = entity.level;
        if(flag && entity instanceof LivingEntity) {
            ((LivingEntity) entity).addEffect(new EffectInstance(Effects.POISON, 200, 2));
            if(!world.isClientSide()) {
                if(this.attackPhase == 0) {
                    world.playSound(null, this, SoundEvents.ZOMBIE_INFECT, SoundCategory.HOSTILE, 1, 1);
                }

                if(this.attackPhase == 1) {
                    world.playSound(null, this, SoundEvents.PLAYER_ATTACK_SWEEP, SoundCategory.HOSTILE, 1, 1);
                }
            }
        }
        return flag;
    }

    @Override
    protected void doPush(Entity entity) {
        boolean flag = !entity.isCrouching() && (entity instanceof PlayerEntity || entity instanceof VillagerEntity || ((LivingEntity)entity).getMobType() == CreatureAttribute.ILLAGER);
        if(flag) {
            this.setTarget((LivingEntity) entity);
        }
    }

    /*
    @Override
    public void baseTick() {
        --timeBeforeSpit;
        if(timeBeforeSpit <= 0) {
            timeBeforeSpit = 60;
            setSpitting(true);
        }
    } */

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SCRATCHING, false);
        this.entityData.define(SPITTING, false);
    }

    public boolean getScratching() {
        return this.entityData.get(SCRATCHING);
    }

    public void setScratching(boolean scratching) {
        this.entityData.set(SCRATCHING, scratching);
    }

    public boolean getSpitting() {
        return this.entityData.get(SPITTING);
    }

    public void setSpitting(boolean spitting) {
        this.entityData.set(SPITTING, spitting);
    }
}
