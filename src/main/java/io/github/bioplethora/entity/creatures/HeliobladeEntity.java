package io.github.bioplethora.entity.creatures;

import io.github.bioplethora.BioplethoraConfig;
import io.github.bioplethora.entity.IBioClassification;
import io.github.bioplethora.entity.SummonableMonsterEntity;
import io.github.bioplethora.entity.ai.CopyTargetOwnerGoal;
import io.github.bioplethora.entity.ai.HeliobladeCloningGoal;
import io.github.bioplethora.entity.ai.HeliobladeQuickShootingGoal;
import io.github.bioplethora.entity.ai.monster.MonsterAnimatableMeleeGoal;
import io.github.bioplethora.entity.ai.monster.MonsterAnimatableMoveToTargetGoal;
import io.github.bioplethora.enums.BPEntityClasses;
import io.github.bioplethora.registry.BioplethoraDamageSources;
import io.github.bioplethora.registry.BioplethoraItems;
import io.github.bioplethora.registry.BioplethoraSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.BossInfo;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerBossInfo;
import net.minecraft.world.server.ServerWorld;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;

public class HeliobladeEntity extends SummonableMonsterEntity implements IAnimatable, IBioClassification {
    private static final DataParameter<Boolean> DATA_IS_QUICKSHOOTING = EntityDataManager.defineId(HeliobladeEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> DATA_IS_CLONE = EntityDataManager.defineId(HeliobladeEntity.class, DataSerializers.BOOLEAN);

    private final TranslationTextComponent cloneProgText = new TranslationTextComponent("bossbar.bioplethora.helioblade.clone_progress");

    private final ServerBossInfo bossInfo = new ServerBossInfo(this.getDisplayName(), BossInfo.Color.RED, BossInfo.Overlay.PROGRESS);
    private final ServerBossInfo cloneProgress = new ServerBossInfo(cloneProgText, BossInfo.Color.WHITE, BossInfo.Overlay.PROGRESS);
    private final AnimationFactory factory = new AnimationFactory(this);
    protected int tpParticleAmount = 30;
    protected double tpParticleRadius = 0.3;
    public int cloneChargeTime;
    public int tpTimer;

    public HeliobladeEntity(EntityType<? extends MonsterEntity> type, World world) {
        super(type, world);
        this.noCulling = true;
        this.tpTimer = 0;
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createLivingAttributes()
                .add(Attributes.ARMOR, 15 * BioplethoraConfig.COMMON.mobArmorMultiplier.get())
                .add(Attributes.ATTACK_SPEED, 1.5)
                .add(Attributes.ATTACK_DAMAGE, 15 * BioplethoraConfig.COMMON.mobMeeleeDamageMultiplier.get())
                .add(Attributes.ATTACK_KNOCKBACK, 0.5D)
                .add(Attributes.MAX_HEALTH, 100 * BioplethoraConfig.COMMON.mobHealthMultiplier.get())
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5)
                .add(Attributes.MOVEMENT_SPEED, 0.65 * BioplethoraConfig.COMMON.mobMovementSpeedMultiplier.get())
                .add(Attributes.FOLLOW_RANGE, 64D);
    }

    @Override
    public BPEntityClasses getBioplethoraClass() {
        return BPEntityClasses.HELLSENT;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 24.0F));
        this.goalSelector.addGoal(4, new RandomWalkingGoal(this, 0.5F));
        this.goalSelector.addGoal(1, new MonsterAnimatableMoveToTargetGoal(this, 0.75, 8));
        this.goalSelector.addGoal(1, new MonsterAnimatableMeleeGoal(this, 20, 0.2, 0.3));
        this.goalSelector.addGoal(2, new HeliobladeQuickShootingGoal(this));
        this.goalSelector.addGoal(3, new HeliobladeCloningGoal(this));
        this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(5, new SwimGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this, HeliobladeEntity.class).setAlertOthers());
        this.targetSelector.addGoal(1, new CopyTargetOwnerGoal(this));

        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AltyrusEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, BellophgolemEntity.class, true));
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "helioblade_controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    private <E extends IAnimatable>PlayState predicate(AnimationEvent<E> event) {

        if(this.isQuickShooting()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.helioblade.quick_shooting", true));
            return PlayState.CONTINUE;
        }

        if(this.getAttacking()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.helioblade.attacking", true));
            return PlayState.CONTINUE;
        }

        if(event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.helioblade.running", true));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.helioblade.idle", true));
        return PlayState.CONTINUE;
    }

    @Nullable
    public ILivingEntityData finalizeSpawn(IServerWorld iServerWorld, DifficultyInstance difficultyInstance, SpawnReason spawnReason, @Nullable ILivingEntityData iLivingEntityData, @Nullable CompoundNBT compoundNBT) {
        iLivingEntityData = super.finalizeSpawn(iServerWorld, difficultyInstance, spawnReason, iLivingEntityData, compoundNBT);

        if (!this.level.isClientSide()) {
            this.setItemSlot(EquipmentSlotType.MAINHAND, new ItemStack(BioplethoraItems.VERMILION_BLADE.get()));
        }

        return iLivingEntityData;
    }

    public void aiStep() {
        super.aiStep();

        if (!this.isClone()) {
            this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
            this.cloneProgress.setPercent((float) this.cloneChargeTime / 300f);
        }

        if (this.getTarget() != null) {
            int areaint = 3;
            double x = this.getX(), y = this.getY(), z = this.getZ();
            AxisAlignedBB area = new AxisAlignedBB(x - (areaint / 2d), y, z - (areaint / 2d), x + (areaint / 2d), y + (areaint / 2d), z + (areaint / 2d));
            World world = this.level;

            for (LivingEntity entityIterator : world.getEntitiesOfClass(LivingEntity.class, area)) {

                if (entityIterator == this.getTarget()) {
                    entityIterator.hurt(BioplethoraDamageSources.helioSlashed(this, this), this.isClone() ? 1F : 3.5F);
                    ++this.tpTimer;
                    if (this.tpTimer == 40) {
                        this.teleportRandomly();
                        this.tpTimer = 0;
                    }
                }
                if ((entityIterator instanceof MobEntity)) {
                    if (((MobEntity) entityIterator).getTarget() == this) {
                        entityIterator.hurt(BioplethoraDamageSources.helioSlashed(this, this), this.isClone() ? 1F : 3.5F);
                    }
                }
            }
        }
    }

    @Override
    public void startSeenByPlayer(ServerPlayerEntity player) {
        super.startSeenByPlayer(player);
        if (!this.isClone()) {
            this.bossInfo.addPlayer(player);
            this.cloneProgress.addPlayer(player);
        }
    }

    @Override
    public void stopSeenByPlayer(ServerPlayerEntity player) {
        super.stopSeenByPlayer(player);
        if (!this.isClone()) {
            this.bossInfo.removePlayer(player);
            this.cloneProgress.removePlayer(player);
        }
    }

    public boolean doHurtTarget (Entity entity) {

        this.teleportRandomly();
        this.tpTimer = 0;

        return super.doHurtTarget(entity);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_IS_QUICKSHOOTING, false);
        this.entityData.define(DATA_IS_CLONE, false);
    }

    public boolean isQuickShooting() {
        return this.entityData.get(DATA_IS_QUICKSHOOTING);
    }

    public void setQuickShooting(boolean quickShooting) {
        this.entityData.set(DATA_IS_QUICKSHOOTING, quickShooting);
    }

    public boolean isClone() {
        return this.entityData.get(DATA_IS_CLONE);
    }

    public void setClone(boolean clone) {
        this.entityData.set(DATA_IS_CLONE, clone);
    }

    public void teleportRandomly() {
        boolean isNegVal = this.getRandom().nextBoolean();
        int tpLoc = this.getRandom().nextInt(15);

        this.level.playSound(null, this.getX(), this.getY(), this.getZ(), this.getTeleportSound(), SoundCategory.HOSTILE, (float) 1, (float) 1);

        if (this.level instanceof ServerWorld) {
            ((ServerWorld) this.level).sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.getX(), this.getY(), this.getZ(), this.tpParticleAmount, this.tpParticleRadius, this.tpParticleRadius, this.tpParticleRadius, 0.01);
        }

        BlockPos blockpos = new BlockPos(this.getX() + (isNegVal ? tpLoc : -tpLoc), this.getY(), this.getZ() + (isNegVal ? tpLoc : -tpLoc));

        if (!this.level.getBlockState(blockpos).getMaterial().blocksMotion()) {
            this.moveTo(blockpos, 0.0F, 0.0F);
        }
    }

    public void teleportWithEffect(double xLoc, double yLoc, double zLoc) {

        this.level.playSound(null, xLoc, yLoc, zLoc, this.getTeleportSound(), SoundCategory.HOSTILE, (float) 1, (float) 1);
        if (this.level instanceof ServerWorld) {
            ((ServerWorld) this.level).sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, xLoc, yLoc, zLoc, this.tpParticleAmount, this.tpParticleRadius, this.tpParticleRadius, this.tpParticleRadius, 0.1);
        }
        this.moveTo(xLoc, yLoc, zLoc);
    }

    public float getSoundVolume() {
        return 0.6F;
    }

    public net.minecraft.util.SoundEvent getTeleportSound() {
        return SoundEvents.BLAZE_SHOOT;
    }

    public boolean causeFallDamage(float distance, float damageMultiplier) {
        return false;
    }

    protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compoundNBT) {
        super.addAdditionalSaveData(compoundNBT);
        compoundNBT.putBoolean("isClone", entityData.get(DATA_IS_CLONE));
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compoundNBT) {
        super.readAdditionalSaveData(compoundNBT);
        this.setClone(compoundNBT.getBoolean("isClone"));
    }

    @Override
    public net.minecraft.util.SoundEvent getAmbientSound() {
        return BioplethoraSoundEvents.HELIOBLADE_IDLE.get();
    }

    @Override
    public net.minecraft.util.SoundEvent getHurtSound(DamageSource damageSource) {
        return BioplethoraSoundEvents.HELIOBLADE_HURT.get();
    }

    @Override
    public net.minecraft.util.SoundEvent getDeathSound() {
        return BioplethoraSoundEvents.HELIOBLADE_DEATH.get();
    }
}
