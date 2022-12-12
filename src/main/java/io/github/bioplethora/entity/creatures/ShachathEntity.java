package io.github.bioplethora.entity.creatures;

import io.github.bioplethora.config.BPConfig;
import io.github.bioplethora.entity.IBioClassification;
import io.github.bioplethora.entity.IMobCappedEntity;
import io.github.bioplethora.entity.SummonableMonsterEntity;
import io.github.bioplethora.entity.ai.controller.WaterMoveController;
import io.github.bioplethora.entity.ai.gecko.GeckoMeleeGoal;
import io.github.bioplethora.entity.ai.gecko.GeckoMoveToTargetGoal;
import io.github.bioplethora.entity.ai.goals.*;
import io.github.bioplethora.entity.ai.navigator.WaterAndLandPathNavigator;
import io.github.bioplethora.entity.others.BPEffectEntity;
import io.github.bioplethora.enums.BPEffectTypes;
import io.github.bioplethora.enums.BPEntityClasses;
import io.github.bioplethora.registry.*;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
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
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
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
import java.util.EnumSet;

public class ShachathEntity extends SummonableMonsterEntity implements IAnimatable, IBioClassification, IMobCappedEntity {
    protected static final DataParameter<Boolean> ATTACKING2 = EntityDataManager.defineId(ShachathEntity.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Boolean> STRIKING = EntityDataManager.defineId(ShachathEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> DATA_IS_QUICKSHOOTING = EntityDataManager.defineId(ShachathEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> DATA_IS_CLONE = EntityDataManager.defineId(ShachathEntity.class, DataSerializers.BOOLEAN);

    private final TranslationTextComponent cloneProgText = new TranslationTextComponent("bossbar.bioplethora.shachath.clone_progress");

    private final ServerBossInfo bossInfo = new ServerBossInfo(this.getDisplayName(), BossInfo.Color.RED, BossInfo.Overlay.PROGRESS);
    private final ServerBossInfo cloneProgress = new ServerBossInfo(cloneProgText, BossInfo.Color.WHITE, BossInfo.Overlay.PROGRESS);
    private final AnimationFactory factory = new AnimationFactory(this);
    public BlockPos boundOrigin;
    protected int tpParticleAmount = 30;
    protected double tpParticleRadius = 0.3;
    public int cloneChargeTime;
    public int attackPhase;
    public int tpTimer;
    public int jumpTimer;

    public ShachathEntity(EntityType<? extends MonsterEntity> type, World world) {
        super(type, world);
        this.moveControl = new ShachathEntity.NonGroundController(this);
        this.noCulling = true;
        this.tpTimer = 0;
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createLivingAttributes()
                .add(Attributes.ARMOR, 15 * BPConfig.COMMON.mobArmorMultiplier.get())
                .add(Attributes.ATTACK_SPEED, 1.5)
                .add(Attributes.ATTACK_DAMAGE, 15 * BPConfig.COMMON.mobMeeleeDamageMultiplier.get())
                .add(Attributes.ATTACK_KNOCKBACK, 0.5D)
                .add(Attributes.MAX_HEALTH, 100 * BPConfig.COMMON.mobHealthMultiplier.get())
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5)
                .add(Attributes.MOVEMENT_SPEED, 0.65 * BPConfig.COMMON.mobMovementSpeedMultiplier.get())
                .add(Attributes.FOLLOW_RANGE, 64D)
                .add(BPAttributes.TRUE_DEFENSE.get(), 1D);
    }

    @Override
    public BPEntityClasses getBioplethoraClass() {
        return BPEntityClasses.HELLSENT;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 24.0F));
        this.goalSelector.addGoal(1, new ShachathEntity.NonGroundMoveRandomGoal());
        this.goalSelector.addGoal(2, new ShachathEntity.NonGroundMoveToTargetGoal());
        this.goalSelector.addGoal(2, new ShachathAttackWaveGoal.Wave1(this, 30, 0.5, 0.6));
        this.goalSelector.addGoal(2, new ShachathAttackWaveGoal.Wave2(this, 35, 0.6, 0.7));
        this.goalSelector.addGoal(2, new ShachathEntityStrikeGoal(this));
        this.goalSelector.addGoal(3, new ShachathQuickShootingGoal(this));
        this.goalSelector.addGoal(3, new ShachathCloningGoal(this));
        this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this, ShachathEntity.class).setAlertOthers());
        this.targetSelector.addGoal(1, new CopyTargetOwnerGoal(this));

        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AltyrusEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, FrostbiteGolemEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AlphemEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AlphemKingEntity.class, true));
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "shachath_controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    private <E extends IAnimatable>PlayState predicate(AnimationEvent<E> event) {

        if(this.isQuickShooting()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.shachath.quick_shooting", true));
            return PlayState.CONTINUE;
        }

        if(this.getStriking()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.shachath.striking", true));
            return PlayState.CONTINUE;
        }

        if(this.getAttacking2()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.shachath.attacking2", true));
            return PlayState.CONTINUE;
        }

        if(this.getAttacking()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.shachath.attacking", true));
            return PlayState.CONTINUE;
        }

        if (isInWater() || level.isEmptyBlock(blockPosition().below())) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.shachath.swim", true));
            return PlayState.CONTINUE;
        }

        if(event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.shachath.running", true));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.shachath.idle", true));
        return PlayState.CONTINUE;
    }

    @Nullable
    public ILivingEntityData finalizeSpawn(IServerWorld iServerWorld, DifficultyInstance difficultyInstance, SpawnReason spawnReason, @Nullable ILivingEntityData iLivingEntityData, @Nullable CompoundNBT compoundNBT) {
        iLivingEntityData = super.finalizeSpawn(iServerWorld, difficultyInstance, spawnReason, iLivingEntityData, compoundNBT);

        if (!this.level.isClientSide()) {
            this.setItemSlot(EquipmentSlotType.MAINHAND, new ItemStack(BPItems.VERMILION_BLADE.get()));
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
                    //entityIterator.hurt(BPDamageSources.helioSlashed(this, this), this.isClone() ? 1F : 1.5F);
                    ++this.tpTimer;
                    if (this.tpTimer == 40) {
                        this.teleportRandomly();
                        this.tpTimer = 0;
                    }
                }
                if ((entityIterator instanceof MobEntity)) {
                    if (((MobEntity) entityIterator).getTarget() == this) {
                        //entityIterator.hurt(BPDamageSources.helioSlashed(this, this), this.isClone() ? 1F : 1.5F);
                    }
                }
            }
        }
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public void swing(Hand pHand) {
        super.swing(pHand);
        if (attackPhase == 0) {
            addSHEffect(BPEffectTypes.SHACHATH_SLASH_FLAT);
            this.playSound(BPSoundEvents.SHACHATH_SLASH.get(), 0.75F, 0.75F + random.nextFloat());
            for (LivingEntity entities : level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(2.4, 0, 2.4))) {
                if (entities != this) {
                    double xa = entities.getX(), ya = entities.getY() + 1, za = entities.getZ();
                    entities.hurt(DamageSource.mobAttack(this), this.isClone() ? 7F : 10F);
                    level.addParticle(BPParticles.SHACHATH_CLASH_INNER.get(), xa, ya, za, 0, 0, 0);
                    level.addParticle(BPParticles.SHACHATH_CLASH_OUTER.get(), xa, ya, za, 0, 0, 0);
                }
            }
        }
        double d0 = -MathHelper.sin(this.yRot * ((float)Math.PI / 180F)) * 6;
        double d1 = MathHelper.cos(this.yRot * ((float)Math.PI / 180F)) * 6;
        if (attackPhase == 1) {
            addSHEffect(BPEffectTypes.SHACHATH_SLASH_FRONT);
            this.playSound(BPSoundEvents.SHACHATH_SLASH.get(), 0.75F, 0.75F + random.nextFloat());
            for (LivingEntity entities : level.getEntitiesOfClass(LivingEntity.class, new AxisAlignedBB(getX() - d0, getY() - 2.5, getZ() - d1, getX() + d0, getY() + 2.5, getZ() + d1))) {
                if (entities != this) {
                    double xa = entities.getX(), ya = entities.getY() + 1, za = entities.getZ();
                    entities.hurt(DamageSource.mobAttack(this), this.isClone() ? 8F : 12F);
                    level.addParticle(BPParticles.SHACHATH_CLASH_INNER.get(), xa, ya, za, 0, 0, 0);
                    level.addParticle(BPParticles.SHACHATH_CLASH_OUTER.get(), xa, ya, za, 0, 0, 0);
                }
            }
        }
    }

    public void addSHEffect(BPEffectTypes effectTypes) {
        BPEffectEntity slash = BPEntities.BP_EFFECT.get().create(this.level);
        slash.setEffectType(effectTypes);
        slash.setOwner(this);
        slash.moveTo(this.blockPosition(), 0.0F, 0.0F);
        slash.yRot = this.yRot;
        slash.yRotO = this.yRot;
        this.level.addFreshEntity(slash);
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
        this.entityData.define(STRIKING, false);
        this.entityData.define(ATTACKING2, false);
        this.entityData.define(DATA_IS_QUICKSHOOTING, false);
        this.entityData.define(DATA_IS_CLONE, false);
    }

    public boolean getStriking() {
        return this.entityData.get(STRIKING);
    }

    public void setStriking(boolean striking) {
        this.entityData.set(STRIKING, striking);
    }

    public boolean getAttacking2() {
        return this.entityData.get(ATTACKING2);
    }

    public void setAttacking2(boolean attacking2) {
        this.entityData.set(ATTACKING2, attacking2);
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

        if (this.boundOrigin != null) {
            compoundNBT.putInt("BoundX", this.boundOrigin.getX());
            compoundNBT.putInt("BoundY", this.boundOrigin.getY());
            compoundNBT.putInt("BoundZ", this.boundOrigin.getZ());
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compoundNBT) {
        super.readAdditionalSaveData(compoundNBT);
        this.setClone(compoundNBT.getBoolean("isClone"));
        if (compoundNBT.contains("BoundX")) {
            this.boundOrigin = new BlockPos(compoundNBT.getInt("BoundX"), compoundNBT.getInt("BoundY"), compoundNBT.getInt("BoundZ"));
        }
    }

    @Override
    public net.minecraft.util.SoundEvent getAmbientSound() {
        return BPSoundEvents.SHACHATH_IDLE.get();
    }

    @Override
    public net.minecraft.util.SoundEvent getHurtSound(DamageSource damageSource) {
        return BPSoundEvents.SHACHATH_HURT.get();
    }

    @Override
    public net.minecraft.util.SoundEvent getDeathSound() {
        return BPSoundEvents.SHACHATH_DEATH.get();
    }

    @Override
    public int getMaxDamageCap() {
        return BPConfig.COMMON.shachathMobCap.get();
    }

    public void setBoundOrigin(@Nullable BlockPos pBoundOrigin) {
        this.boundOrigin = pBoundOrigin;
    }

    @Nullable
    public BlockPos getBoundOrigin() {
        return this.boundOrigin;
    }

    public class NonGroundController extends MovementController {
        public NonGroundController(ShachathEntity floatingMob) {
            super(floatingMob);
        }

        public void tick() {
            ShachathEntity shachath = ShachathEntity.this;
            if (this.operation == MovementController.Action.MOVE_TO && !shachath.isVehicle()) {
                Vector3d vector3d = new Vector3d(this.wantedX - shachath.getX(), this.wantedY - shachath.getY(), this.wantedZ - shachath.getZ());
                double d0 = vector3d.length();
                if (d0 < shachath.getBoundingBox().getSize()) {
                    this.operation = MovementController.Action.WAIT;
                    shachath.setDeltaMovement(shachath.getDeltaMovement().scale(0.5D));
                } else {
                    shachath.setDeltaMovement(shachath.getDeltaMovement().add(vector3d.scale((this.speedModifier * 0.05D / d0) * (shachath.isInWater() ? 2.2 : 1))));
                    if (shachath.getTarget() == null) {
                        Vector3d vector3d1 = shachath.getDeltaMovement();
                        shachath.yRot = -((float) MathHelper.atan2(vector3d1.x, vector3d1.z)) * (180F / (float) Math.PI);
                    } else {
                        double d2 = shachath.getTarget().getX() - shachath.getX();
                        double d1 = shachath.getTarget().getZ() - shachath.getZ();
                        shachath.yRot = -((float) MathHelper.atan2(d2, d1)) * (180F / (float) Math.PI);
                    }
                    shachath.yBodyRot = shachath.yRot;
                }
            }
        }
    }
    
    public class NonGroundMoveToTargetGoal extends Goal {
        public NonGroundMoveToTargetGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        public boolean canUse() {

            if (ShachathEntity.this.getTarget() != null) {
                return ShachathEntity.this.distanceToSqr(ShachathEntity.this.getTarget()) > 2.0D;
            } else {
                return false;
            }
        }

        public boolean canContinueToUse() {
            if (ShachathEntity.this.getOwner() != null) {
                if (!(ShachathEntity.this.distanceToSqr(ShachathEntity.this.getOwner()) <= 1024D)) {
                    return false;
                }
            }
            return ShachathEntity.this.getMoveControl().hasWanted() && ShachathEntity.this.getTarget() != null && ShachathEntity.this.getTarget().isAlive();
        }

        public void start() {
            LivingEntity livingentity = ShachathEntity.this.getTarget();
            Vector3d vector3d = livingentity.getEyePosition(1.0F);
            ShachathEntity.this.moveControl.setWantedPosition(vector3d.x, vector3d.y, vector3d.z, 1.75D);
        }

        public void stop() {
        }

        public void tick() {
            LivingEntity livingentity = ShachathEntity.this.getTarget();

            if (!ShachathEntity.this.getBoundingBox().intersects(livingentity.getBoundingBox())) {
                double d0 = ShachathEntity.this.distanceToSqr(livingentity);
                if (d0 < 9.0D) {
                    Vector3d vector3d = livingentity.getEyePosition(1.0F);
                    ShachathEntity.this.moveControl.setWantedPosition(vector3d.x, vector3d.y, vector3d.z, 1.0D);
                }
            }
        }
    }

    public class NonGroundMoveRandomGoal extends Goal {
        public NonGroundMoveRandomGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        public boolean canUse() {
            return !ShachathEntity.this.getMoveControl().hasWanted() && ShachathEntity.this.getOwner() == null && ShachathEntity.this.random.nextInt(5) == 0;
        }

        public boolean canContinueToUse() {
            return false;
        }

        public void tick() {
            BlockPos blockpos = ShachathEntity.this.getBoundOrigin();
            if (blockpos == null) {
                blockpos = ShachathEntity.this.blockPosition();
            }

            for(int i = 0; i < 3; ++i) {
                BlockPos blockpos1 = blockpos.offset(ShachathEntity.this.random.nextInt(15) - 7, ShachathEntity.this.random.nextInt(11) - 5, ShachathEntity.this.random.nextInt(15) - 7);
                if (ShachathEntity.this.level.isEmptyBlock(blockpos1)) {
                    ShachathEntity.this.moveControl.setWantedPosition((double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.5D, (double)blockpos1.getZ() + 0.5D, 0.25D);
                    if (ShachathEntity.this.getTarget() == null) {
                        ShachathEntity.this.getLookControl().setLookAt((double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.5D, (double)blockpos1.getZ() + 0.5D, 180.0F, 20.0F);
                    }
                    break;
                }
            }
        }
    }
}
