package io.github.bioplethora.entity.creatures;

import io.github.bioplethora.BioplethoraConfig;
import io.github.bioplethora.entity.AnimatableMonsterEntity;
import io.github.bioplethora.entity.IBioClassification;
import io.github.bioplethora.entity.ai.monster.MonsterAnimatableMeleeGoal;
import io.github.bioplethora.entity.ai.monster.MonsterAnimatableMoveToTargetGoal;
import io.github.bioplethora.enums.BPEntityClasses;
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
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
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

public class DwarfMossadileEntity extends AnimatableMonsterEntity implements IAnimatable, IBioClassification {

    private static final DataParameter<Boolean> DATA_IS_NETHER_VARIANT = EntityDataManager.defineId(DwarfMossadileEntity.class, DataSerializers.BOOLEAN);
    private final AnimationFactory factory = new AnimationFactory(this);

    public DwarfMossadileEntity(EntityType<? extends MonsterEntity> type, World world) {
        super(type, world);
        this.xpReward = 10;
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createLivingAttributes()
                .add(Attributes.ARMOR, 2 * BioplethoraConfig.COMMON.mobArmorMultiplier.get())
                .add(Attributes.ATTACK_SPEED, 1.5)
                .add(Attributes.ATTACK_DAMAGE, 5 * BioplethoraConfig.COMMON.mobMeeleeDamageMultiplier.get())
                .add(Attributes.ATTACK_KNOCKBACK, 0.3D)
                .add(Attributes.MAX_HEALTH, 25 * BioplethoraConfig.COMMON.mobHealthMultiplier.get())
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.2)
                .add(Attributes.MOVEMENT_SPEED, 0.25 * BioplethoraConfig.COMMON.mobMovementSpeedMultiplier.get())
                .add(Attributes.FOLLOW_RANGE, 32.0D);
    }

    @Override
    public BPEntityClasses getBioplethoraClass() {
        return BPEntityClasses.DANGERUM;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 24.0F));
        this.goalSelector.addGoal(4, new RandomWalkingGoal(this, 1F));
        this.goalSelector.addGoal(1, new MonsterAnimatableMoveToTargetGoal(this, 1, 8));
        this.goalSelector.addGoal(1, new MonsterAnimatableMeleeGoal(this, 40, 0.5, 0.6));
        this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(5, new SwimGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, NandbriEntity.class, true));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, DwarfMossadileEntity.class)).setAlertOthers());
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "dwarf_mossadile_controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (this.getAttacking()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.dwarf_mossadile.attacking", true));
            return PlayState.CONTINUE;
        }

        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.dwarf_mossadile.walking", true));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.dwarf_mossadile.idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    public net.minecraft.util.SoundEvent getAmbientSound() {
        return SoundEvents.SHULKER_AMBIENT;
    }

    @Override
    public net.minecraft.util.SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.SHULKER_HURT;
    }

    @Override
    public net.minecraft.util.SoundEvent getDeathSound() {
        return SoundEvents.SHULKER_DEATH;
    }

    @Override
    public void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.SALMON_FLOP, 0.15f, 1);
    }

    public boolean doHurtTarget(Entity entity) {
        boolean flag = super.doHurtTarget(entity);

        World world = entity.level;
        BlockPos blockPos = new BlockPos((int) getTarget().getX(), (int) getTarget().getY(), (int) getTarget().getZ());

        if (flag && entity instanceof LivingEntity) {
            if (this.isNetherVariant()) {
                entity.setSecondsOnFire(5);
                ((ServerWorld) this.level).sendParticles(ParticleTypes.FLAME, getTarget().getX(), getTarget().getY(), getTarget().getZ(), 20, 0.4, 0.4, 0.4, 0.1);
                this.level.playSound(null, blockPos, SoundEvents.BLAZE_SHOOT, SoundCategory.HOSTILE, (float) 1, (float) 1);
            } else {
                ((LivingEntity) entity).addEffect(new EffectInstance(Effects.POISON, 40, 1));
            }
        }
        return flag;
    }

    public int getMaxSpawnClusterSize() {
        return 4;
    }

    public ILivingEntityData finalizeSpawn(IServerWorld world, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {

        if ((this.level.dimension().equals(World.NETHER) || (this.level.dimension().equals(World.NETHER)))) {
            this.setNetherVariant(true);
        }

        if (BioplethoraConfig.COMMON.hellMode.get()) {
            this.getAttribute(Attributes.ARMOR).setBaseValue(4 * BioplethoraConfig.COMMON.mobArmorMultiplier.get());
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(37 * BioplethoraConfig.COMMON.mobHealthMultiplier.get());
            this.setHealth(37 * BioplethoraConfig.COMMON.mobHealthMultiplier.get());
        }

        if (this.isNetherVariant()) {
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(7.5 * BioplethoraConfig.COMMON.mobMeeleeDamageMultiplier.get());
        }

        return super.finalizeSpawn(world, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    public boolean fireImmune() {
        return this.isNetherVariant();
    }

    public boolean isNetherVariant() {
        return this.entityData.get(DATA_IS_NETHER_VARIANT);
    }

    public void setNetherVariant(boolean netherVariant) {
        this.entityData.set(DATA_IS_NETHER_VARIANT, netherVariant);
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compoundNBT) {
        super.addAdditionalSaveData(compoundNBT);
        compoundNBT.putBoolean("isNetherVariant", this.isNetherVariant());
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compoundNBT) {
        super.readAdditionalSaveData(compoundNBT);
        this.setNetherVariant(compoundNBT.getBoolean("isNetherVariant"));
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_IS_NETHER_VARIANT, false);
    }
}
