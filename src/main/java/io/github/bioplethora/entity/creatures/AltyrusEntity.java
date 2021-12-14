package io.github.bioplethora.entity.creatures;

import io.github.bioplethora.config.BioplethoraConfig;
import io.github.bioplethora.entity.AnimatableMonsterEntity;
import io.github.bioplethora.entity.ai.AltyrusRangedAttackGoal;
import io.github.bioplethora.entity.ai.AltyrusSummonGolemGoal;
import io.github.bioplethora.entity.ai.monster.MonsterAnimatableMeleeGoal;
import io.github.bioplethora.entity.ai.monster.MonsterAnimatableMoveToTargetGoal;
import io.github.bioplethora.registry.BioplethoraSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;
import net.minecraft.world.server.ServerBossInfo;
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

public class AltyrusEntity extends AnimatableMonsterEntity implements IAnimatable, IFlyingAnimal {

    private static final DataParameter<Boolean> DATA_IS_CHARGING = EntityDataManager.defineId(AltyrusEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> DATA_IS_SUMMONING = EntityDataManager.defineId(AltyrusEntity.class, DataSerializers.BOOLEAN);
    private final ServerBossInfo bossInfo = (ServerBossInfo) (new ServerBossInfo(this.getDisplayName(), BossInfo.Color.PURPLE, BossInfo.Overlay.PROGRESS));
    private final AnimationFactory factory = new AnimationFactory(this);

    public AltyrusEntity(EntityType<? extends MonsterEntity> type, World world) {
        super(type, world);
        this.noCulling = true;
        this.xpReward = 100;
        this.moveControl = new FlyingMovementController(this, 20, true);
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createLivingAttributes()
                .add(Attributes.ARMOR, 15 * BioplethoraConfig.COMMON.mobArmorMultiplier.get())
                .add(Attributes.ATTACK_SPEED, 10)
                .add(Attributes.ATTACK_DAMAGE, 30 * BioplethoraConfig.COMMON.mobMeeleeDamageMultiplier.get())
                .add(Attributes.ATTACK_KNOCKBACK, 7D)
                .add(Attributes.MAX_HEALTH, 450 * BioplethoraConfig.COMMON.mobHealthMultiplier.get())
                .add(Attributes.KNOCKBACK_RESISTANCE, 10.0)
                .add(Attributes.MOVEMENT_SPEED, 0.25 * BioplethoraConfig.COMMON.mobMovementSpeedMultiplier.get())
                .add(Attributes.FOLLOW_RANGE, 64D)
                .add(Attributes.FLYING_SPEED, 1.5F);
    }

    protected PathNavigator createNavigation(World worldIn) {
        return new FlyingPathNavigator(AltyrusEntity.this, worldIn) {
            public boolean isStableDestination(BlockPos pos) {
                return !this.level.getBlockState(pos.below()).isAir();
            }
        };
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 24.0F));
        this.goalSelector.addGoal(3, new LookAtGoal(this, AlphemEntity.class, 24.0F));
        this.goalSelector.addGoal(2, new MonsterAnimatableMoveToTargetGoal(this, 1.6, 8));
        this.goalSelector.addGoal(2, new MonsterAnimatableMeleeGoal(this, 60, 0.6, 0.7));
        this.goalSelector.addGoal(5, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(6, new AltyrusRangedAttackGoal(this));
        this.goalSelector.addGoal(7, new AltyrusSummonGolemGoal(this));
        this.goalSelector.addGoal(8, new SwimGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AlphemEntity.class, true));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this).setAlertOthers(this.getClass()));
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "altyrus_controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    private <E extends IAnimatable>PlayState predicate(AnimationEvent<E> event) {

        if (this.isDeadOrDying() || this.dead) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.altyrus.death", true));
            return PlayState.CONTINUE;
        }

        if (this.getAttacking()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.altyrus.attacking", true));
            return PlayState.CONTINUE;
        }

        if (this.isCharging()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.altyrus.charging", true));
            return PlayState.CONTINUE;
        }

        if (this.isSummoning()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.altyrus.summoning", true));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.altyrus.idle", true));
        return PlayState.CONTINUE;
    }

    public boolean doHurtTarget (Entity entity) {
        boolean flag = super.doHurtTarget(entity);
        if (this.level instanceof ServerWorld) {
            ((World) this.level).explode(null, (int) getTarget().getX(), (int) getTarget().getY(), (int) getTarget().getZ(), (float) 3, Explosion.Mode.BREAK);
            ((ServerWorld) this.level).sendParticles(ParticleTypes.POOF, (getTarget().getX()), (getTarget().getY()), (getTarget().getZ()), (int) 40, 0.75, 0.75,
                    0.75, 0.1);
        }
        this.doEnchantDamageEffects(this, entity);
        return flag;
    }

    public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        if (worldIn instanceof ServerWorld && BioplethoraConfig.COMMON.hellMode.get()) {
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(35 * BioplethoraConfig.COMMON.mobMeeleeDamageMultiplier.get());
            this.getAttribute(Attributes.ARMOR).setBaseValue(17.5 * BioplethoraConfig.COMMON.mobArmorMultiplier.get());
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(520 * BioplethoraConfig.COMMON.mobHealthMultiplier.get());
            this.setHealth(520 * BioplethoraConfig.COMMON.mobHealthMultiplier.get());
        }
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
    }

    @Override
    public void startSeenByPlayer(ServerPlayerEntity player) {
        super.startSeenByPlayer(player);
        this.bossInfo.addPlayer(player);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayerEntity player) {
        super.stopSeenByPlayer(player);
        this.bossInfo.removePlayer(player);
    }

    @Override
    public SoundEvent getAmbientSound() {
        return SoundEvents.BLAZE_AMBIENT;
    }

    @Override
    public SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.BLAZE_HURT;
    }

    @Override
    public SoundEvent getDeathSound() {
        return BioplethoraSoundEvents.BELLOPHGOLEM_DEATH.get();
    }

    public boolean causeFallDamage(float distance, float damageMultiplier) {
        return false;
    }

    protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
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

    @OnlyIn(Dist.CLIENT)
    public boolean isSummoning() {
        return this.entityData.get(DATA_IS_SUMMONING);
    }

    public void setSummoning(boolean summoning) {
        this.entityData.set(DATA_IS_SUMMONING, summoning);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_IS_CHARGING, false);
        this.entityData.define(DATA_IS_SUMMONING, false);
    }
}