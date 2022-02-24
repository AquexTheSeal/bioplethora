package io.github.bioplethora.entity.creatures;

import io.github.bioplethora.BioplethoraConfig;
import io.github.bioplethora.entity.BPMonsterEntity;
import io.github.bioplethora.entity.IBioClassification;
import io.github.bioplethora.entity.IGrylynenTier;
import io.github.bioplethora.entity.ai.monster.BPMonsterMeleeGoal;
import io.github.bioplethora.entity.ai.monster.BPMonsterMoveToTargetGoal;
import io.github.bioplethora.enums.BPEntityClasses;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
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
import java.util.EnumSet;

public class GrylynenEntity extends BPMonsterEntity implements IAnimatable, IFlyingAnimal, IBioClassification {

    private final AnimationFactory factory = new AnimationFactory(this);
    private final IGrylynenTier tier;
    public BlockPos boundOrigin;
    public int col;

    public GrylynenEntity(EntityType<? extends MonsterEntity> type, World worldIn, IGrylynenTier IGrylynenTier) {
        super(type, worldIn);
        this.moveControl = new FlyingMovementController(this, 20, true);
        this.noCulling = true;
        this.xpReward = 15;
        this.moveControl = new GrylynenEntity.MoveHelperController(this);
        this.tier = IGrylynenTier;
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createLivingAttributes()
                .add(Attributes.ARMOR, 0)
                .add(Attributes.ATTACK_SPEED, 10)
                .add(Attributes.ATTACK_KNOCKBACK, 0.5D)
                .add(Attributes.ATTACK_DAMAGE, 2 * BioplethoraConfig.COMMON.mobMeeleeDamageMultiplier.get())
                .add(Attributes.MAX_HEALTH, 1)
                .add(Attributes.MOVEMENT_SPEED, 0.25 * BioplethoraConfig.COMMON.mobMovementSpeedMultiplier.get())
                .add(Attributes.FLYING_SPEED, 0.45 * BioplethoraConfig.COMMON.mobMovementSpeedMultiplier.get())
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.2D)
                .add(Attributes.FOLLOW_RANGE, 32D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new BPMonsterMoveToTargetGoal(this, 1.2, 8));
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

    public void tick() {
        super.tick();

        double x = this.getX();
        double y = this.getY();
        double z = this.getZ();

        if (this.level instanceof ServerWorld) {
            ((ServerWorld) this.level).sendParticles(ParticleTypes.CRIT, x, y, z, 5, 0.65, 0.65, 0.65, 0.01);
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

    @Nullable
    public ILivingEntityData finalizeSpawn(IServerWorld iServerWorld, DifficultyInstance difficultyInstance, SpawnReason spawnReason, @Nullable ILivingEntityData iLivingEntityData, @Nullable CompoundNBT compoundNBT) {
        iLivingEntityData = super.finalizeSpawn(iServerWorld, difficultyInstance, spawnReason, iLivingEntityData, compoundNBT);

        BioplethoraConfig.Common config = BioplethoraConfig.COMMON;

        if (iServerWorld instanceof ServerWorld && !config.hellMode.get()) {
            // If not Hellmode
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(this.getGrylynenTier().getTierHealth());
            this.setHealth(this.getGrylynenTier().getTierHealth());

        } else if (iServerWorld instanceof ServerWorld && config.hellMode.get()) {
            // If in Hellmode
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(this.getGrylynenTier().getHellTierHP());
            this.setHealth(this.getGrylynenTier().getHellTierHP());
        }

        if (iServerWorld instanceof ServerWorld) {
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(this.getGrylynenTier().getTierDamage() * config.mobMeeleeDamageMultiplier.get());
        }

        return iLivingEntityData;
    }

    @Override
    public net.minecraft.util.SoundEvent getHurtSound(DamageSource damageSource) {
        return this.getGrylynenTier().getHurtSound();
    }

    @Override
    public net.minecraft.util.SoundEvent getAmbientSound() {
        return SoundEvents.BLAZE_AMBIENT;
    }

    @Override
    public net.minecraft.util.SoundEvent getDeathSound() {
        return SoundEvents.BLAZE_DEATH;
    }

    public boolean isNoGravity() {
        return true;
    }

    public boolean causeFallDamage(float distance, float damageMultiplier) {
        return false;
    }

    protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
    }

    public IGrylynenTier getGrylynenTier() {
        return tier;
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

    class ChargeAttackGoal extends Goal {
        public ChargeAttackGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        public boolean canUse() {
            if (GrylynenEntity.this.getTarget() != null &&
                    !GrylynenEntity.this.getMoveControl().hasWanted() &&
                    GrylynenEntity.this.random.nextInt(3) == 0) {

                return GrylynenEntity.this.distanceToSqr(GrylynenEntity.this.getTarget()) > 2.0D;
            } else {
                return false;
            }
        }

        public boolean canContinueToUse() {
            return GrylynenEntity.this.getMoveControl().hasWanted() &&
                    GrylynenEntity.this.getTarget() != null &&
                    GrylynenEntity.this.getTarget().isAlive();
        }

        public void start() {
            LivingEntity livingentity = GrylynenEntity.this.getTarget();
            Vector3d vector3d = livingentity.getEyePosition(1.0F);
            GrylynenEntity.this.moveControl.setWantedPosition(vector3d.x, vector3d.y, vector3d.z, 1.0D);
        }

        public void stop() {
        }

        public void tick() {
            LivingEntity livingentity = GrylynenEntity.this.getTarget();

            if (!GrylynenEntity.this.getBoundingBox().intersects(livingentity.getBoundingBox())) {
                double d0 = GrylynenEntity.this.distanceToSqr(livingentity);
                if (d0 < 9.0D) {
                    Vector3d vector3d = livingentity.getEyePosition(1.0F);
                    GrylynenEntity.this.moveControl.setWantedPosition(vector3d.x, vector3d.y, vector3d.z, 1.0D);
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
        public MoveHelperController(GrylynenEntity grylynen) {
            super(grylynen);
        }

        public void tick() {
            if (this.operation == MovementController.Action.MOVE_TO) {
                Vector3d vector3d = new Vector3d(this.wantedX - GrylynenEntity.this.getX(), this.wantedY - GrylynenEntity.this.getY(), this.wantedZ - GrylynenEntity.this.getZ());
                double d0 = vector3d.length();
                if (d0 < GrylynenEntity.this.getBoundingBox().getSize()) {
                    this.operation = MovementController.Action.WAIT;
                    GrylynenEntity.this.setDeltaMovement(GrylynenEntity.this.getDeltaMovement().scale(0.5D));
                } else {
                    GrylynenEntity.this.setDeltaMovement(GrylynenEntity.this.getDeltaMovement().add(vector3d.scale(this.speedModifier * 0.05D / d0)));
                    if (GrylynenEntity.this.getTarget() == null) {
                        Vector3d vector3d1 = GrylynenEntity.this.getDeltaMovement();
                        GrylynenEntity.this.yRot = -((float) MathHelper.atan2(vector3d1.x, vector3d1.z)) * (180F / (float)Math.PI);
                        GrylynenEntity.this.yBodyRot = GrylynenEntity.this.yRot;
                    } else {
                        double d2 = GrylynenEntity.this.getTarget().getX() - GrylynenEntity.this.getX();
                        double d1 = GrylynenEntity.this.getTarget().getZ() - GrylynenEntity.this.getZ();
                        GrylynenEntity.this.yRot = -((float)MathHelper.atan2(d2, d1)) * (180F / (float)Math.PI);
                        GrylynenEntity.this.yBodyRot = GrylynenEntity.this.yRot;
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
            return !GrylynenEntity.this.getMoveControl().hasWanted() && GrylynenEntity.this.random.nextInt(7) == 0;
        }

        public boolean canContinueToUse() {
            return false;
        }

        public void tick() {
            BlockPos blockpos = GrylynenEntity.this.getBoundOrigin();
            if (blockpos == null) {
                blockpos = GrylynenEntity.this.blockPosition();
            }

            for(int i = 0; i < 3; ++i) {
                BlockPos blockpos1 = blockpos.offset(GrylynenEntity.this.random.nextInt(15) - 7, GrylynenEntity.this.random.nextInt(11) - 3, GrylynenEntity.this.random.nextInt(15) - 7);
                if (GrylynenEntity.this.level.isEmptyBlock(blockpos1)) {
                    GrylynenEntity.this.moveControl.setWantedPosition((double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.5D, (double)blockpos1.getZ() + 0.5D, 0.25D);
                    if (GrylynenEntity.this.getTarget() == null) {
                        GrylynenEntity.this.getLookControl().setLookAt((double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.5D, (double)blockpos1.getZ() + 0.5D, 180.0F, 20.0F);
                    }
                    break;
                }
            }

        }
    }
}
