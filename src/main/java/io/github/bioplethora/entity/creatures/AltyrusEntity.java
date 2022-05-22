package io.github.bioplethora.entity.creatures;

import io.github.bioplethora.api.advancements.AdvancementUtils;
import io.github.bioplethora.api.world.BlockUtils;
import io.github.bioplethora.config.BPConfig;
import io.github.bioplethora.entity.BPMonsterEntity;
import io.github.bioplethora.entity.IBioClassification;
import io.github.bioplethora.entity.IMobCappedEntity;
import io.github.bioplethora.entity.ai.gecko.GeckoDodgeableMeleeGoal;
import io.github.bioplethora.entity.ai.goals.AltyrusRangedAttackGoal;
import io.github.bioplethora.entity.ai.goals.AltyrusSummonGolemGoal;
import io.github.bioplethora.enums.BPEntityClasses;
import io.github.bioplethora.registry.BPAttributes;
import io.github.bioplethora.registry.BPSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.*;
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

public class AltyrusEntity extends BPMonsterEntity implements IAnimatable, IFlyingAnimal, IBioClassification, IMobCappedEntity {

    private static final DataParameter<Boolean> DATA_IS_CHARGING = EntityDataManager.defineId(AltyrusEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> DATA_IS_SUMMONING = EntityDataManager.defineId(AltyrusEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> DATA_IS_DODGING = EntityDataManager.defineId(AltyrusEntity.class, DataSerializers.BOOLEAN);
    private final ServerBossInfo bossInfo = new ServerBossInfo(this.getDisplayName(), BossInfo.Color.PURPLE, BossInfo.Overlay.PROGRESS);
    private final AnimationFactory factory = new AnimationFactory(this);
    public BlockPos boundOrigin;
    public int dodgeTimer;

    public AltyrusEntity(EntityType<? extends BPMonsterEntity> type, World world) {
        super(type, world);
        this.noCulling = true;
        this.moveControl = new AltyrusEntity.MoveHelperController(this);
        this.xpReward = 200;
    }

    @Override
    public BPEntityClasses getBioplethoraClass() {
        return BPEntityClasses.ELDERIA;
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createLivingAttributes()
                .add(Attributes.ARMOR, 22.5 * BPConfig.COMMON.mobArmorMultiplier.get())
                .add(Attributes.ATTACK_SPEED, 10)
                .add(Attributes.ATTACK_DAMAGE, 35 * BPConfig.COMMON.mobMeeleeDamageMultiplier.get())
                .add(Attributes.ATTACK_KNOCKBACK, 7D)
                .add(Attributes.MAX_HEALTH, 495 * BPConfig.COMMON.mobHealthMultiplier.get())
                .add(Attributes.KNOCKBACK_RESISTANCE, 10.0)
                .add(Attributes.MOVEMENT_SPEED, 1.5F * BPConfig.COMMON.mobMovementSpeedMultiplier.get())
                .add(Attributes.FLYING_SPEED, 1.5F * BPConfig.COMMON.mobMovementSpeedMultiplier.get())
                .add(Attributes.FOLLOW_RANGE, 64D)
                .add(BPAttributes.TRUE_DEFENSE.get(), 3D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new WaterAvoidingRandomFlyingGoal(this, 1.2));
        this.goalSelector.addGoal(3, new AltyrusEntity.ChargeAttackGoal());
        this.goalSelector.addGoal(3, new GeckoDodgeableMeleeGoal<>(this, 60, 0.5, 0.6));
        this.goalSelector.addGoal(4, new AltyrusEntity.MoveRandomGoal());
        this.goalSelector.addGoal(4, new AltyrusRangedAttackGoal(this));
        this.goalSelector.addGoal(5, new AltyrusSummonGolemGoal(this));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 24.0F));
        this.goalSelector.addGoal(6, new LookAtGoal(this, AlphemEntity.class, 24.0F));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(8, new SwimGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AlphemEntity.class, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AlphemKingEntity.class, false));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
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

        if (this.isSummoning()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.altyrus.summoning", true));
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

        if (this.isDodging()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.altyrus.dodging", true));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.altyrus.idle", true));
        return PlayState.CONTINUE;
    }

    public boolean doHurtTarget (Entity entity) {
        boolean flag = super.doHurtTarget(entity);
        double x = entity.getX(), y = entity.getY(), z = entity.getZ();

        this.level.explode(null, (int) x, (int) y, (int) z, (float) 3, Explosion.Mode.BREAK);
        if (this.level instanceof ServerWorld) {
            ((ServerWorld) this.level).sendParticles(ParticleTypes.POOF, x, y, z, 40, 0.75, 0.75, 0.75, 0.1);
        }
        BlockUtils.knockUpRandomNearbyBlocks(this.level, 0.5D, entity.blockPosition().below(), 5, 2, 5, false, true);
        return flag;
    }

    public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        if (worldIn instanceof ServerWorld && BPConfig.COMMON.hellMode.get()) {
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(42 * BPConfig.COMMON.mobMeeleeDamageMultiplier.get());
            this.getAttribute(Attributes.ARMOR).setBaseValue(24.5 * BPConfig.COMMON.mobArmorMultiplier.get());
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(520 * BPConfig.COMMON.mobHealthMultiplier.get());
            this.setHealth(520 * BPConfig.COMMON.mobHealthMultiplier.get());
        }

        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    public void aiStep() {
        super.aiStep();

        World world = this.level;

        this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());

        if (this.isDodging()) {
            ++dodgeTimer;
            if (dodgeTimer == 20) {
                this.setDodging(false);
                this.dodgeTimer = 0;
            }
        }

        if (world instanceof ServerWorld) {
            ServerWorld serverWorld = ((ServerWorld) world);

            serverWorld.sendParticles(ParticleTypes.CLOUD, this.getX(), this.getY(), this.getZ(), 10, 1.2, 1.2, 1.2, 0.01);

            //this.summonParticleBarrier(serverWorld);
        }
    }

    public void summonParticleBarrier(ServerWorld serverWorld) {

        int loop = 0; int particleAmount = 10; int xRad = 10; int zRad = 10;

        serverWorld.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                (this.getX() + 0.5) + Math.cos(((Math.PI * 2) / particleAmount) * loop) * xRad,
                this.getY(),
                (this.getZ() + 0.5) + Math.sin(((Math.PI * 2) / particleAmount) * loop) * zRad,
                5, 0.5, 1.5, 0.5, 0.01);
        ++loop;
    }

    /*public void summonParticleBarrier(ServerWorld serverWorld) {

        int loop = 0; int particleAmount = 10; int xRad = 3; int zRad = 3;

        while (loop < particleAmount) {
            serverWorld.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                    this.getX() + 0.5 + Math.cos(((Math.PI * 2) / particleAmount) * loop) * xRad,
                    this.getY(),
                    this.getZ() + 0.5 + Math.sin(((Math.PI * 2) / particleAmount) * loop) * zRad,
                    0.0, 1.0, 0.0);
            ++loop;
        }
    }*/

    public boolean isNoGravity() {
        return true;
    }

    @Override
    public void die(DamageSource source) {
        super.die(source);

        Entity sourceEnt = source.getEntity();

        AdvancementUtils.grantBioAdvancement(sourceEnt, "bioplethora:altyrus_kill");
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
        return BPSoundEvents.ALTYRUS_IDLE.get();
    }

    @Override
    public SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.BLAZE_HURT;
    }

    @Override
    public SoundEvent getDeathSound() {
        return BPSoundEvents.BELLOPHGOLEM_DEATH.get();
    }

    public SoundEvent getDodgeSound() {
        return SoundEvents.SHULKER_SHOOT;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_IS_CHARGING, false);
        this.entityData.define(DATA_IS_SUMMONING, false);
        this.entityData.define(DATA_IS_DODGING, false);
    }

    public boolean isCharging() {
        return this.entityData.get(DATA_IS_CHARGING);
    }

    public void setCharging(boolean charging) {
        this.entityData.set(DATA_IS_CHARGING, charging);
    }

    public boolean isSummoning() {
        return this.entityData.get(DATA_IS_SUMMONING);
    }

    public void setSummoning(boolean summoning) {
        this.entityData.set(DATA_IS_SUMMONING, summoning);
    }

    public boolean isDodging() {
        return this.entityData.get(DATA_IS_DODGING);
    }

    public void setDodging(boolean dodging) {
        this.entityData.set(DATA_IS_DODGING, dodging);
    }


    public boolean causeFallDamage(float distance, float damageMultiplier) {
        return false;
    }

    protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
    }

    public void checkDespawn() {
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

    public void readAdditionalSaveData(CompoundNBT pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.contains("BoundX")) {
            this.boundOrigin = new BlockPos(pCompound.getInt("BoundX"), pCompound.getInt("BoundY"), pCompound.getInt("BoundZ"));
        }
    }

    public void addAdditionalSaveData(CompoundNBT pCompound) {
        super.addAdditionalSaveData(pCompound);
        if (this.boundOrigin != null) {
            pCompound.putInt("BoundX", this.boundOrigin.getX());
            pCompound.putInt("BoundY", this.boundOrigin.getY());
            pCompound.putInt("BoundZ", this.boundOrigin.getZ());
        }
    }

    public void setBoundOrigin(@Nullable BlockPos pBoundOrigin) {
        this.boundOrigin = pBoundOrigin;
    }

    @Nullable
    public BlockPos getBoundOrigin() {
        return this.boundOrigin;
    }

    @Override
    public int getMaxDamageCap() {
        return BPConfig.COMMON.altyrusMobCap.get();
    }

    class ChargeAttackGoal extends Goal {
        public ChargeAttackGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }
        
        public boolean canUse() {
            if (AltyrusEntity.this.getTarget() != null &&
                    !AltyrusEntity.this.getMoveControl().hasWanted() &&
                    AltyrusEntity.this.random.nextInt(3) == 0) {

                return AltyrusEntity.this.distanceToSqr(AltyrusEntity.this.getTarget()) > 2.0D;
            } else {
                return false;
            }
        }
        
        public boolean canContinueToUse() {
            return AltyrusEntity.this.getMoveControl().hasWanted() &&
                    AltyrusEntity.this.getTarget() != null &&
                    AltyrusEntity.this.getTarget().isAlive();
        }
        
        public void start() {
            LivingEntity livingentity = AltyrusEntity.this.getTarget();
            Vector3d vector3d = livingentity.getEyePosition(1.0F);
            AltyrusEntity.this.moveControl.setWantedPosition(vector3d.x, vector3d.y, vector3d.z, 1.0D);
            AltyrusEntity.this.playSound(BPSoundEvents.ALTYRUS_CHARGE.get(), 1.0F, 1.0F);
        }
        
        public void stop() {
        }
        
        public void tick() {
            LivingEntity livingentity = AltyrusEntity.this.getTarget();

            if (!AltyrusEntity.this.getBoundingBox().intersects(livingentity.getBoundingBox())) {
                double d0 = AltyrusEntity.this.distanceToSqr(livingentity);
                if (d0 < 9.0D) {
                    Vector3d vector3d = livingentity.getEyePosition(1.0F);
                    AltyrusEntity.this.moveControl.setWantedPosition(vector3d.x, vector3d.y, vector3d.z, 1.0D);
                }
            }

            /*if (AltyrusEntity.this.getBoundingBox().intersects(livingentity.getBoundingBox())) {
                AltyrusEntity.this.doHurtTarget(livingentity);
            } else {
                double d0 = AltyrusEntity.this.distanceToSqr(livingentity);
                if (d0 < 9.0D) {
                    Vector3d vector3d = livingentity.getEyePosition(1.0F);
                    AltyrusEntity.this.moveControl.setWantedPosition(vector3d.x, vector3d.y, vector3d.z, 1.0D);
                }
            }*/
        }
    }

    class MoveHelperController extends MovementController {
        public MoveHelperController(AltyrusEntity altyrus) {
            super(altyrus);
        }

        public void tick() {
            if (this.operation == MovementController.Action.MOVE_TO) {
                Vector3d vector3d = new Vector3d(this.wantedX - AltyrusEntity.this.getX(), this.wantedY - AltyrusEntity.this.getY(), this.wantedZ - AltyrusEntity.this.getZ());
                double d0 = vector3d.length();
                if (d0 < AltyrusEntity.this.getBoundingBox().getSize()) {
                    this.operation = MovementController.Action.WAIT;
                    AltyrusEntity.this.setDeltaMovement(AltyrusEntity.this.getDeltaMovement().scale(0.5D));
                } else {
                    AltyrusEntity.this.setDeltaMovement(AltyrusEntity.this.getDeltaMovement().add(vector3d.scale(this.speedModifier * 0.05D / d0)));
                    if (AltyrusEntity.this.getTarget() == null) {
                        Vector3d vector3d1 = AltyrusEntity.this.getDeltaMovement();
                        AltyrusEntity.this.yRot = -((float) MathHelper.atan2(vector3d1.x, vector3d1.z)) * (180F / (float)Math.PI);
                        AltyrusEntity.this.yBodyRot = AltyrusEntity.this.yRot;
                    } else {
                        double d2 = AltyrusEntity.this.getTarget().getX() - AltyrusEntity.this.getX();
                        double d1 = AltyrusEntity.this.getTarget().getZ() - AltyrusEntity.this.getZ();
                        AltyrusEntity.this.yRot = -((float)MathHelper.atan2(d2, d1)) * (180F / (float)Math.PI);
                        AltyrusEntity.this.yBodyRot = AltyrusEntity.this.yRot;
                    }
                }

            }
        }
    }

    class MoveRandomGoal extends Goal {
        public MoveRandomGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        public boolean canUse() {
            return !AltyrusEntity.this.getMoveControl().hasWanted() && AltyrusEntity.this.random.nextInt(7) == 0;
        }

        public boolean canContinueToUse() {
            return false;
        }

        public void tick() {
            BlockPos blockpos = AltyrusEntity.this.getBoundOrigin();
            if (blockpos == null) {
                blockpos = AltyrusEntity.this.blockPosition();
            }

            for(int i = 0; i < 3; ++i) {
                BlockPos blockpos1 = blockpos.offset(AltyrusEntity.this.random.nextInt(15) - 7, AltyrusEntity.this.random.nextInt(11) - 7, AltyrusEntity.this.random.nextInt(15) - 7);
                if (AltyrusEntity.this.level.isEmptyBlock(blockpos1)) {
                    AltyrusEntity.this.moveControl.setWantedPosition((double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.5D, (double)blockpos1.getZ() + 0.5D, 0.25D);
                    if (AltyrusEntity.this.getTarget() == null) {
                        AltyrusEntity.this.getLookControl().setLookAt((double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.5D, (double)blockpos1.getZ() + 0.5D, 180.0F, 20.0F);
                    }
                    break;
                }
            }
        }
    }
}
