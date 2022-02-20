package io.github.bioplethora.entity.creatures;

import io.github.bioplethora.BioplethoraConfig;
import io.github.bioplethora.entity.IBioClassification;
import io.github.bioplethora.entity.SummonableMonsterEntity;
import io.github.bioplethora.entity.ai.AlphemRangedAttackGoal;
import io.github.bioplethora.entity.ai.CopyTargetOwnerGoal;
import io.github.bioplethora.entity.ai.monster.BPMonsterMeleeGoal;
import io.github.bioplethora.entity.ai.monster.BPMonsterMoveToTargetGoal;
import io.github.bioplethora.enums.BPEntityClasses;
import io.github.bioplethora.registry.BioplethoraAdvancementHelper;
import io.github.bioplethora.registry.BioplethoraSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
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

public class AlphemEntity extends SummonableMonsterEntity implements IAnimatable, IBioClassification {

    private static final DataParameter<Boolean> DATA_IS_CHARGING = EntityDataManager.defineId(AlphemEntity.class, DataSerializers.BOOLEAN);
    private final AnimationFactory factory = new AnimationFactory(this);

    public AlphemEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
        this.noCulling = true;
    }

    @Override
    public BPEntityClasses getBioplethoraClass() {
        return BPEntityClasses.DANGERUM;
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createLivingAttributes()
                .add(Attributes.ARMOR, 4 * BioplethoraConfig.COMMON.mobArmorMultiplier.get())
                .add(Attributes.ATTACK_SPEED, 10)
                .add(Attributes.ATTACK_KNOCKBACK, 0.5D)
                .add(Attributes.ATTACK_DAMAGE, 7 * BioplethoraConfig.COMMON.mobMeeleeDamageMultiplier.get())
                .add(Attributes.MAX_HEALTH, 20 * BioplethoraConfig.COMMON.mobHealthMultiplier.get())
                .add(Attributes.MOVEMENT_SPEED, 0.25 * BioplethoraConfig.COMMON.mobMovementSpeedMultiplier.get())
                .add(Attributes.FOLLOW_RANGE, 64D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 24.0F));
        this.goalSelector.addGoal(3, new LookAtGoal(this, AnimalEntity.class, 24.0F));
        this.goalSelector.addGoal(3, new LookAtGoal(this, BellophgolemEntity.class, 24.0F));
        this.goalSelector.addGoal(3, new LookAtGoal(this, AltyrusEntity.class, 24.0F));
        this.goalSelector.addGoal(3, new LookAtGoal(this, HeliobladeEntity.class, 24.0F));
        this.goalSelector.addGoal(2, new BPMonsterMoveToTargetGoal(this, 1.6, 8));
        this.goalSelector.addGoal(2, new BPMonsterMeleeGoal(this, 40, 0.5, 0.6));
        this.goalSelector.addGoal(3, new RandomWalkingGoal(this, 1.2));
        this.goalSelector.addGoal(3, new AlphemRangedAttackGoal(this));
        this.goalSelector.addGoal(5, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(7, new SwimGoal(this));
        this.goalSelector.addGoal(6, new FollowMobGoal(this, (float) 1, 10, 5));
        this.targetSelector.addGoal(1, new CopyTargetOwnerGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AnimalEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, BellophgolemEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AltyrusEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, HeliobladeEntity.class, true));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, AlphemKingEntity.class).setAlertOthers(AlphemEntity.class));
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (this.dead) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.alphem.death", true));
            return PlayState.CONTINUE;
        }

        if (this.getAttacking()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.alphem.attack", true));
            return PlayState.CONTINUE;
        }

        if (this.isCharging()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.alphem.attack", true));
            return PlayState.CONTINUE;
        }

        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.alphem.walking", true));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.alphem.idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "alphem_controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Nullable
    public ILivingEntityData finalizeSpawn(IServerWorld iServerWorld, DifficultyInstance difficultyInstance, SpawnReason spawnReason, @Nullable ILivingEntityData iLivingEntityData, @Nullable CompoundNBT compoundNBT) {
        iLivingEntityData = super.finalizeSpawn(iServerWorld, difficultyInstance, spawnReason, iLivingEntityData, compoundNBT);

        if (iServerWorld instanceof ServerWorld && BioplethoraConfig.COMMON.hellMode.get()) {
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(10 * BioplethoraConfig.COMMON.mobMeeleeDamageMultiplier.get());
            this.getAttribute(Attributes.ARMOR).setBaseValue(6.5 * BioplethoraConfig.COMMON.mobArmorMultiplier.get());
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(40 * BioplethoraConfig.COMMON.mobHealthMultiplier.get());
            this.setHealth(40 * BioplethoraConfig.COMMON.mobHealthMultiplier.get());
        }

        if (BioplethoraConfig.COMMON.hellMode.get()) {
            if (Math.random() < 0.5) {
                if (!this.level.isClientSide())
                    if (Math.random() < 0.5) {
                        this.setItemSlot(EquipmentSlotType.OFFHAND, new ItemStack(Items.TOTEM_OF_UNDYING));
                    }
                    this.setItemSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.DIAMOND_SWORD));
            } else {
                if (!this.level.isClientSide())
                    if (Math.random() < 0.5) {
                        this.setItemSlot(EquipmentSlotType.OFFHAND, new ItemStack(Items.TOTEM_OF_UNDYING));
                    }
                    this.setItemSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.IRON_AXE));
            }
        } else {
            if (Math.random() < 0.5) {
                if (!this.level.isClientSide())
                    this.setItemSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.STONE_SWORD));
            } else {
                if (!this.level.isClientSide())
                    this.setItemSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.WOODEN_AXE));
            }
        }

        return iLivingEntityData;
    }

    @Override
    public void die(DamageSource source) {
        super.die(source);
        Entity sourceEnt = source.getEntity();
        BioplethoraAdvancementHelper.grantBioAdvancement(sourceEnt, "bioplethora:alphem_kill");
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 4 + this.level.getRandom().nextInt(4);
    }

    public net.minecraft.util.SoundEvent getAmbientSound() {
        return SoundEvents.VEX_CHARGE;
    }

    @Override
    public net.minecraft.util.SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.RABBIT_HURT;
    }

    @Override
    public net.minecraft.util.SoundEvent getDeathSound() {
        return SoundEvents.RABBIT_DEATH;
    }

    @Override
    public void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(BioplethoraSoundEvents.ALPHEM_STEP.get(), 0.15f, 1);
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

    @Override
    public void addAdditionalSaveData(CompoundNBT compoundNBT) {
        super.addAdditionalSaveData(compoundNBT);
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compoundNBT) {
        super.readAdditionalSaveData(compoundNBT);
    }
}
