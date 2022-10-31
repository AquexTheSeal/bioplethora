package io.github.bioplethora.entity.creatures;

import io.github.bioplethora.api.world.EffectUtils;
import io.github.bioplethora.config.BPConfig;
import io.github.bioplethora.entity.BPAnimalEntity;
import io.github.bioplethora.entity.FloatingMonsterEntity;
import io.github.bioplethora.entity.ai.controller.WaterMoveController;
import io.github.bioplethora.entity.ai.gecko.GeckoGoal;
import io.github.bioplethora.entity.ai.gecko.GeckoMeleeGoal;
import io.github.bioplethora.entity.ai.gecko.GeckoMoveToTargetGoal;
import io.github.bioplethora.entity.ai.gecko.IGeckoBaseEntity;
import io.github.bioplethora.entity.ai.goals.BPCustomSwimmingGoal;
import io.github.bioplethora.entity.ai.goals.BPWaterChargingCoal;
import io.github.bioplethora.entity.ai.goals.WaterFollowOwnerGoal;
import io.github.bioplethora.entity.ai.navigator.WaterAndLandPathNavigator;
import io.github.bioplethora.enums.BPEntityClasses;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.PathType;
import net.minecraft.pathfinding.WalkNodeProcessor;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import org.lwjgl.system.CallbackI;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class VoidjawEntity extends TrapjawEntity {

    private static final DataParameter<Boolean> IS_SAVING = EntityDataManager.defineId(VoidjawEntity.class, DataSerializers.BOOLEAN);
    public boolean inWall;
    public int particleTime;

    public VoidjawEntity(EntityType<? extends BPAnimalEntity> type, World worldIn) {
        super(type, worldIn);
        this.moveControl = new VoidjawEntity.MoveHelperController(this);
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createLivingAttributes()
                .add(Attributes.ARMOR, 6 * BPConfig.COMMON.mobArmorMultiplier.get())
                .add(Attributes.ATTACK_SPEED, 1.5)
                .add(Attributes.ATTACK_DAMAGE, 17 * BPConfig.COMMON.mobMeeleeDamageMultiplier.get())
                .add(Attributes.ATTACK_KNOCKBACK, 1.0 * BPConfig.COMMON.mobMeeleeDamageMultiplier.get())
                .add(Attributes.MAX_HEALTH, 135 * BPConfig.COMMON.mobHealthMultiplier.get())
                .add(Attributes.MOVEMENT_SPEED, 0.30D * BPConfig.COMMON.mobMovementSpeedMultiplier.get())
                .add(Attributes.FOLLOW_RANGE, 64D);
    }

    @Override
    public BPEntityClasses getBioplethoraClass() {
        return BPEntityClasses.HELLSENT;
    }

    @Override
    public boolean shouldVerticalMove() {
        return true;
    }

    public boolean isNoGravity() {
        return true;
    }

    public boolean causeFallDamage(float distance, float damageMultiplier) {
        return false;
    }

    protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
    }

    @Override
    protected void registerGoals() {
        //this.goalSelector.addGoal(2, new SitGoal(this));
        this.goalSelector.addGoal(3, new VoidjawEntity.ChargeAttackGoal());
        this.goalSelector.addGoal(4, new VoidjawEntity.MoveRandomGoal());
        this.goalSelector.addGoal(4, new VoidjawMeleeGoal<>(this, 30, 0.5, 0.6));
        this.goalSelector.addGoal(5, new WaterFollowOwnerGoal(this, 1.2D, 10.0F, 2.0F, true));
        this.goalSelector.addGoal(5, new VoidjawEntity.FollowOwnerGoal());
        this.goalSelector.addGoal(6, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new RandomWalkingGoal(this, 1.2, 8));
        this.goalSelector.addGoal(10, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(11, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(2, (new HurtByTargetGoal(this)).setAlertOthers());
        this.targetSelector.addGoal(1, new NonTamedTargetGoal<>(this, LivingEntity.class, false, PREY_SELECTOR));
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.inWall) {
            this.move(MoverType.SELF, this.getDeltaMovement().scale(0.8F));
        } else {
            this.move(MoverType.SELF, this.getDeltaMovement());
        }

        if (!this.level.isClientSide) {
            this.inWall = this.checkWalls(this.getBoundingBox());
        }

        if (this.getDeltaMovement().x() > 0.2D || this.getDeltaMovement().y() > 0.2D || this.getDeltaMovement().z() > 0.2D) {
            ++this.particleTime;
            if (this.particleTime == 10) {
                double d0 = this.random.nextGaussian() * 0.02D;
                double d1 = this.random.nextGaussian() * 0.02D;
                double d2 = this.random.nextGaussian() * 0.02D;
                this.level.addParticle(ParticleTypes.FIREWORK, this.getRandomX(1.0D), this.getRandomY(), this.getRandomZ(1.0D), d0, d1, d2);
                particleTime = 0;
            }
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_SAVING, false);
    }

    public boolean isSaving() {
        return this.entityData.get(IS_SAVING);
    }

    public void setSaving(boolean saving) {
        this.entityData.set(IS_SAVING, saving);
    }

    private boolean checkWalls(AxisAlignedBB pArea) {
        int i = MathHelper.floor(pArea.minX);
        int j = MathHelper.floor(pArea.minY);
        int k = MathHelper.floor(pArea.minZ);
        int l = MathHelper.floor(pArea.maxX);
        int i1 = MathHelper.floor(pArea.maxY);
        int j1 = MathHelper.floor(pArea.maxZ);
        boolean flag = false;
        boolean flag1 = false;

        for(int k1 = i; k1 <= l; ++k1) {
            for(int l1 = j; l1 <= i1; ++l1) {
                for(int i2 = k; i2 <= j1; ++i2) {
                    BlockPos blockpos = new BlockPos(k1, l1, i2);
                    BlockState blockstate = this.level.getBlockState(blockpos);
                    Block block = blockstate.getBlock();
                    if (!blockstate.isAir(this.level, blockpos) && blockstate.getMaterial() != Material.FIRE) {
                        if (net.minecraftforge.common.ForgeHooks.canEntityDestroy(this.level, blockpos, this) && !BlockTags.DRAGON_IMMUNE.contains(block) &&
                                (BlockTags.LEAVES.contains(block) || BlockTags.CORAL_PLANTS.contains(block))) {
                            flag1 = this.level.removeBlock(blockpos, false) || flag1;
                        } else {
                            flag = true;
                        }
                    }
                }
            }
        }

        if (flag1) {
            BlockPos blockpos1 = new BlockPos(i + this.random.nextInt(l - i + 1), j + this.random.nextInt(i1 - j + 1), k + this.random.nextInt(j1 - k + 1));
            this.level.levelEvent(2008, blockpos1, 0);
        }

        return flag;
    }

    @Override
    public void switchNavigator(boolean onLand) {
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
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "voidjaw_controller", 0, this::predicate));
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (this.getAttacking()) {
            event.getController().setAnimation(new AnimationBuilder().loop("animation.voidjaw.attack"));
            return PlayState.CONTINUE;
        } else if (this.isInSittingPose()) {
            event.getController().setAnimation(new AnimationBuilder().loop("animation.voidjaw.sit"));
            return PlayState.CONTINUE;
        } else if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().loop("animation.voidjaw.float"));
            return PlayState.CONTINUE;
        } else {
            event.getController().setAnimation(new AnimationBuilder().loop("animation.voidjaw.idle"));
            return PlayState.CONTINUE;
        }
    }

    public static class VoidjawMeleeGoal<E extends MobEntity> extends GeckoMeleeGoal<E> {

        public VoidjawMeleeGoal(E entity, double animationLength, double attackBegin, double attackEnd) {
            super(entity, animationLength, attackBegin, attackEnd);
        }

        public boolean checkIfValid2(GeckoMeleeGoal goal, MobEntity attacker, LivingEntity target) {
            if (target == null) return false;
            if (target.isAlive() && !target.isSpectator()) {
                if (target instanceof PlayerEntity && ((PlayerEntity) target).isCreative()) {
                    ((IGeckoBaseEntity) attacker).setAttacking(false);
                    return false;
                }
                double distance = goal.entity.distanceToSqr(target.getX(), target.getY(), target.getZ());
                if (distance <= getAttackReachSq2(attacker, target)) return true;
            }
            ((IGeckoBaseEntity) attacker).setAttacking(false);
            return false;
        }

        protected double getAttackReachSq2(LivingEntity attacker, LivingEntity target) {
            return attacker.getBbWidth() * 4.5F * attacker.getBbWidth() * 4.5F + target.getBbWidth();
        }

        @Override
        public boolean canUse() {
            if (Math.random() <= 0.1) return false;

            return checkIfValid2(this, entity, this.entity.getTarget());
        }

        @Override
        public boolean canContinueToUse() {
            if (Math.random() <= 0.1) return true;

            return checkIfValid2(this, entity, this.entity.getTarget());
        }


    }

    public class MoveHelperController extends MovementController {
        public MoveHelperController(VoidjawEntity floatingMob) {
            super(floatingMob);
        }

        public void tick() {
            VoidjawEntity voidjaw = VoidjawEntity.this;
            if (voidjaw.isOrderedToSit() && !voidjaw.isInSittingPose()) {
                VoidjawEntity.this.setDeltaMovement(VoidjawEntity.this.getDeltaMovement().add(0, -0.25, 0));
                VoidjawEntity.this.setInSittingPose(true);
            } else if (!voidjaw.isOrderedToSit() && voidjaw.isInSittingPose()) {
                VoidjawEntity.this.setDeltaMovement(VoidjawEntity.this.getDeltaMovement().add(0, 0.25, 0));
                VoidjawEntity.this.setInSittingPose(false);
            }
            if (this.operation == MovementController.Action.MOVE_TO && !voidjaw.isVehicle() && !voidjaw.isInSittingPose()) {
                Vector3d vector3d = new Vector3d(this.wantedX - voidjaw.getX(), this.wantedY - voidjaw.getY(), this.wantedZ - voidjaw.getZ());
                double d0 = vector3d.length();
                if (d0 < voidjaw.getBoundingBox().getSize()) {
                    this.operation = MovementController.Action.WAIT;
                    voidjaw.setDeltaMovement(voidjaw.getDeltaMovement().scale(0.5D));
                } else {
                    voidjaw.setDeltaMovement(voidjaw.getDeltaMovement().add(vector3d.scale((this.speedModifier * 0.05D / d0) * (voidjaw.isInWater() ? 2.2 : 1))));
                    if (voidjaw.getTarget() == null) {
                        Vector3d vector3d1 = voidjaw.getDeltaMovement();
                        voidjaw.yRot = -((float) MathHelper.atan2(vector3d1.x, vector3d1.z)) * (180F / (float) Math.PI);
                    } else {
                        double d2 = voidjaw.getTarget().getX() - voidjaw.getX();
                        double d1 = voidjaw.getTarget().getZ() - voidjaw.getZ();
                        voidjaw.yRot = -((float) MathHelper.atan2(d2, d1)) * (180F / (float) Math.PI);
                    }
                    voidjaw.yBodyRot = voidjaw.yRot;
                }
            }
        }
    }

    @Override
    public void move(MoverType pType, Vector3d pPos) {
        super.move(pType, pPos);
        if (this.isSaving() || !this.level.isEmptyBlock(this.blockPosition())) {
            this.checkInsideBlocks();
        }
    }

    public class ChargeAttackGoal extends Goal {
        public ChargeAttackGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        public boolean canUse() {
            if (VoidjawEntity.this.getOwner() != null) {
                if (!(VoidjawEntity.this.distanceToSqr(VoidjawEntity.this.getOwner()) <= 1024D)) {
                    return false;
                }
            }
            if (VoidjawEntity.this.getTarget() != null && !VoidjawEntity.this.getMoveControl().hasWanted() && VoidjawEntity.this.random.nextInt(3) == 0) {

                return VoidjawEntity.this.distanceToSqr(VoidjawEntity.this.getTarget()) > 2.0D;
            } else {
                return false;
            }
        }

        public boolean canContinueToUse() {
            if (VoidjawEntity.this.getOwner() != null) {
                if (!(VoidjawEntity.this.distanceToSqr(VoidjawEntity.this.getOwner()) <= 1024D)) {
                    return false;
                }
            }
            return VoidjawEntity.this.getMoveControl().hasWanted() && VoidjawEntity.this.getTarget() != null && VoidjawEntity.this.getTarget().isAlive();
        }

        public void start() {
            LivingEntity livingentity = VoidjawEntity.this.getTarget();
            Vector3d vector3d = livingentity.getEyePosition(1.0F);
            VoidjawEntity.this.moveControl.setWantedPosition(vector3d.x, vector3d.y, vector3d.z, 1.0D);
        }

        public void stop() {
        }

        public void tick() {
            LivingEntity livingentity = VoidjawEntity.this.getTarget();

            if (!VoidjawEntity.this.getBoundingBox().intersects(livingentity.getBoundingBox())) {
                double d0 = VoidjawEntity.this.distanceToSqr(livingentity);
                if (d0 < 9.0D) {
                    Vector3d vector3d = livingentity.getEyePosition(1.0F);
                    VoidjawEntity.this.moveControl.setWantedPosition(vector3d.x, vector3d.y, vector3d.z, 1.0D);
                }
            }
        }
    }

    public class FollowOwnerGoal extends Goal {
        private int timeToRecalcPath;

        public FollowOwnerGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        public boolean canUse() {
            if (VoidjawEntity.this.isInSittingPose()) {
                return false;
            } else if (VoidjawEntity.this.isVehicle()) {
                return false;
            } else if (VoidjawEntity.this.getOwner() == null) {
                return false;
            } else return VoidjawEntity.this.getTarget() == null;
        }

        public boolean canContinueToUse() {
            return canUse();
        }

        public void start() {
            this.timeToRecalcPath = 0;
        }

        public void stop() {
        }

        public void tick() {
            int rand = VoidjawEntity.this.getRandom().nextBoolean() ? -5 : 5;
            int rand2 = VoidjawEntity.this.getRandom().nextBoolean() ? 5 : -5;
            Vector3d vector3d = VoidjawEntity.this.getOwner().getEyePosition(1.0F);
            VoidjawEntity.this.getLookControl().setLookAt(VoidjawEntity.this.getOwner(), 10.0F, (float)VoidjawEntity.this.getMaxHeadXRot());

            if (VoidjawEntity.this.getOwner().fallDistance > 5.0F && VoidjawEntity.this.isSaddled() && !VoidjawEntity.this.isVehicle() && !VoidjawEntity.this.getOwner().isPassenger()) {
                VoidjawEntity.this.setSaving(true);
                VoidjawEntity.this.getOwner().playSound(SoundEvents.ELDER_GUARDIAN_HURT, 1.0F, 1.75F);
                VoidjawEntity.this.playSound(VoidjawEntity.this.getHurtSound(DamageSource.GENERIC), 1.5F, 1.3F);
                VoidjawEntity.this.moveControl.setWantedPosition(vector3d.x, vector3d.y - 1, vector3d.z, 6D);
                EffectUtils.addCircleParticleForm(VoidjawEntity.this.level, VoidjawEntity.this, ParticleTypes.CAMPFIRE_COSY_SMOKE, 15, 0.5, 0.01);
                if (VoidjawEntity.this.getBoundingBox().intersects(VoidjawEntity.this.getOwner().getBoundingBox())) {
                    VoidjawEntity.this.getOwner().startRiding(VoidjawEntity.this);
                    VoidjawEntity.this.getOwner().playSound(SoundEvents.ANVIL_PLACE, 1.0F, 1.0F);
                    EffectUtils.addCircleParticleForm(VoidjawEntity.this.level, VoidjawEntity.this, ParticleTypes.FIREWORK, 30, 0.75, 0.105);
                    VoidjawEntity.this.setSaving(false);
                }
            } else {
                VoidjawEntity.this.setSaving(false);
            }

            if (--this.timeToRecalcPath <= 0) {
                this.timeToRecalcPath = 10;
                if (!VoidjawEntity.this.isLeashed() && !VoidjawEntity.this.isPassenger()) {
                    if (VoidjawEntity.this.distanceToSqr(VoidjawEntity.this.getOwner()) >= 144.0D) {
                        this.teleportToOwner();
                    } else {
                        if (VoidjawEntity.this.isFood(VoidjawEntity.this.getOwner().getMainHandItem()) || VoidjawEntity.this.getOwner().getMainHandItem().getItem() == Items.SADDLE) {
                            VoidjawEntity.this.moveControl.setWantedPosition(vector3d.x, vector3d.y, vector3d.z, 1.0D);
                        } else {
                            VoidjawEntity.this.moveControl.setWantedPosition(vector3d.x + rand, vector3d.y, vector3d.z + rand2, 1.0D);
                        }
                    }
                }
            }
        }

        private void teleportToOwner() {
            BlockPos blockpos = VoidjawEntity.this.getOwner().blockPosition();

            for(int i = 0; i < 10; ++i) {
                int j = this.randomIntInclusive(-3, 3);
                int k = this.randomIntInclusive(-1, 1);
                int l = this.randomIntInclusive(-3, 3);
                boolean flag = this.maybeTeleportTo(blockpos.getX() + j, blockpos.getY() + k, blockpos.getZ() + l);
                if (flag) {
                    return;
                }
            }
        }

        private boolean maybeTeleportTo(int pX, int pY, int pZ) {
            LivingEntity livingentity = VoidjawEntity.this.getOwner();
            if (Math.abs((double)pX - VoidjawEntity.this.getOwner().getX()) < 2.0D && Math.abs((double)pZ - VoidjawEntity.this.getOwner().getZ()) < 2.0D) {
                return false;
            } else if (!this.canTeleportTo(new BlockPos(pX, pY, pZ))) {
                return false;
            } else {
                Vector3d vector3d = livingentity.getEyePosition(1.0F);
                VoidjawEntity.this.moveControl.setWantedPosition(vector3d.x, vector3d.y, vector3d.z, 1.0D);
                return true;
            }
        }

        private boolean canTeleportTo(BlockPos pPos) {
            if (!VoidjawEntity.this.level.isEmptyBlock(pPos)) {
                return false;
            } else {
                BlockPos blockpos = pPos.subtract(VoidjawEntity.this.blockPosition());
                return VoidjawEntity.this.level.noCollision(VoidjawEntity.this, VoidjawEntity.this.getBoundingBox().move(blockpos));
            }
        }

        private int randomIntInclusive(int pMin, int pMax) {
            return VoidjawEntity.this.getRandom().nextInt(pMax - pMin + 1) + pMin;
        }
    }

    public class MoveRandomGoal extends Goal {
        public MoveRandomGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        public boolean canUse() {
            return !VoidjawEntity.this.getMoveControl().hasWanted() && VoidjawEntity.this.getOwner() == null && VoidjawEntity.this.random.nextInt(5) == 0;
        }

        public boolean canContinueToUse() {
            return false;
        }

        public void tick() {
            BlockPos blockpos = VoidjawEntity.this.getBoundOrigin();
            if (blockpos == null) {
                blockpos = VoidjawEntity.this.blockPosition();
            }

            for(int i = 0; i < 3; ++i) {
                BlockPos blockpos1 = blockpos.offset(VoidjawEntity.this.random.nextInt(15) - 7, VoidjawEntity.this.random.nextInt(11) - 5, VoidjawEntity.this.random.nextInt(15) - 7);
                if (VoidjawEntity.this.level.isEmptyBlock(blockpos1)) {
                    VoidjawEntity.this.moveControl.setWantedPosition((double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.5D, (double)blockpos1.getZ() + 0.5D, 0.25D);
                    if (VoidjawEntity.this.getTarget() == null) {
                        VoidjawEntity.this.getLookControl().setLookAt((double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.5D, (double)blockpos1.getZ() + 0.5D, 180.0F, 20.0F);
                    }
                    break;
                }
            }
        }
    }
}
