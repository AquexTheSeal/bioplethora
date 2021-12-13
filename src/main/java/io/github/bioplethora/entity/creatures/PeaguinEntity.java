package io.github.bioplethora.entity.creatures;

import io.github.bioplethora.config.BioplethoraConfig;
import io.github.bioplethora.entity.AnimatableAnimalEntity;
import io.github.bioplethora.entity.ai.tameable.AnimalAnimatableMeleeGoal;
import io.github.bioplethora.entity.ai.tameable.AnimalAnimatableMoveToTargetGoal;
import io.github.bioplethora.entity.ai.PeaguinFollowOwnerGoal;
import io.github.bioplethora.entity.ai.controller.WaterMoveController;
import io.github.bioplethora.entity.ai.navigator.WaterAndLandPathNavigator;
import io.github.bioplethora.registry.BioplethoraEntities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.GhastEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.*;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;
import java.util.UUID;

public class PeaguinEntity extends AnimatableAnimalEntity implements IAnimatable, IAngerable {

    private static final DataParameter<Integer> DATA_REMAINING_ANGER_TIME = EntityDataManager.defineId(PeaguinEntity.class, DataSerializers.INT);
    private static final RangedInteger PERSISTENT_ANGER_TIME = TickRangeConverter.rangeOfSeconds(20, 39);
    private UUID persistentAngerTarget;
    private boolean isLandNavigator;

    private final AnimationFactory factory = new AnimationFactory(this);

    public PeaguinEntity(EntityType<? extends AnimatableAnimalEntity> type, World worldIn) {
        super(type, worldIn);
        this.setTame(false);
        this.setPathfindingMalus(PathNodeType.WATER, 0.0F);
        this.setPathfindingMalus(PathNodeType.WATER_BORDER, 0.0F);
        /*this.moveControl = new PeaguinEntity.MoveHelperController(this);*/
        this.noCulling = true;
        switchNavigator(false);
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createLivingAttributes()
                .add(Attributes.ARMOR, 2 * BioplethoraConfig.COMMON.mobArmorMultiplier.get())
                .add(Attributes.ATTACK_SPEED, 1.5)
                .add(Attributes.ATTACK_DAMAGE, 2 * BioplethoraConfig.COMMON.mobMeeleeDamageMultiplier.get())
                .add(Attributes.ATTACK_KNOCKBACK, 0.5 * BioplethoraConfig.COMMON.mobMeeleeDamageMultiplier.get())
                .add(Attributes.MAX_HEALTH, 20 * BioplethoraConfig.COMMON.mobHealthMultiplier.get())
                .add(Attributes.MOVEMENT_SPEED, 0.25 * BioplethoraConfig.COMMON.mobMovementSpeedMultiplier.get())
                .add(Attributes.FOLLOW_RANGE, 16D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(2, new SitGoal(this));
        this.goalSelector.addGoal(4, new AnimalAnimatableMoveToTargetGoal(this, 1.6, 8));
        this.goalSelector.addGoal(4, new AnimalAnimatableMeleeGoal(this, 20, 0.5, 0.75));
        this.goalSelector.addGoal(5, new PeaguinFollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(6, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new RandomWalkingGoal(this, 1.2, 8));
        this.goalSelector.addGoal(8, new PeaguinEntity.SwimGoal(this));
        this.goalSelector.addGoal(10, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(11, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(2, (new HurtByTargetGoal(this)).setAlertOthers());
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 10, true, false, this::isAngryAt));
        this.targetSelector.addGoal(8, new ResetAngerGoal<>(this, true));
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "peaguincontroller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public net.minecraft.util.SoundEvent getAmbientSound() {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.dolphin.ambient"));
    }

    @Override
    public net.minecraft.util.SoundEvent getHurtSound(DamageSource damageSource) {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.dolphin.hurt"));
    }

    @Override
    public net.minecraft.util.SoundEvent getDeathSound() {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.dolphin.death"));
    }

    @Override
    public void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound((net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.salmon.flop")),
                0.15f, 1);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {

        if (this.isDeadOrDying() || this.dead) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.peaguin.death", true));
            return PlayState.CONTINUE;
        }

        if (this.isInSittingPose()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.peaguin.sitting", true));
            return PlayState.CONTINUE;
        }

        if (this.getAttacking() && this.shouldDropLoot()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.peaguin.attacking", true));
            return PlayState.CONTINUE;
        }

        if (this.isSwimming() || this.isInWaterOrBubble() || this.isInWater()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.peaguin.swimming", true));
            return PlayState.CONTINUE;
        }

        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.peaguin.walking", true));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.peaguin.idle", true));
        return PlayState.CONTINUE;
    }

    public PeaguinEntity getBreedOffspring(ServerWorld serverWorld, AgeableEntity ageableEntity) {
        PeaguinEntity peaguinEntity = BioplethoraEntities.PEAGUIN.get().create(serverWorld);
        UUID uuid = this.getOwnerUUID();
        if (uuid != null) {
            peaguinEntity.setOwnerUUID(uuid);
            peaguinEntity.setTame(true);
        }

        return peaguinEntity;
    }

    protected float getStandingEyeHeight(Pose pose, EntitySize entitySize) {
        return this.isBaby() ? entitySize.height * 0.8F : 1.0F;
    }

    public boolean isFood(ItemStack p_70877_1_) {
        Item item = p_70877_1_.getItem();
        return item.isEdible() && item.getFoodProperties().isMeat();
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_REMAINING_ANGER_TIME, 0);
    }

    public ActionResultType mobInteract(PlayerEntity p_230254_1_, Hand p_230254_2_) {
        ItemStack itemstack = p_230254_1_.getItemInHand(p_230254_2_);
        Item item = itemstack.getItem();
        if (this.level.isClientSide) {
            boolean flag = this.isOwnedBy(p_230254_1_) || this.isTame() || item.isEdible() && item.getFoodProperties().isMeat() && !this.isTame() && !this.isAngry();
            return flag ? ActionResultType.CONSUME : ActionResultType.PASS;
        } else {
            if (this.isTame()) {
                if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
                    if (!p_230254_1_.abilities.instabuild) {
                        itemstack.shrink(1);
                    }

                    this.heal((float) item.getFoodProperties().getNutrition());
                    return ActionResultType.SUCCESS;
                }

                ActionResultType actionresulttype = super.mobInteract(p_230254_1_, p_230254_2_);
                if ((!actionresulttype.consumesAction() || this.isBaby()) && this.isOwnedBy(p_230254_1_)) {
                    this.setOrderedToSit(!this.isOrderedToSit());
                    this.jumping = false;
                    this.navigation.stop();
                    this.setTarget((LivingEntity) null);
                    return ActionResultType.SUCCESS;
                }

                return actionresulttype;

            } else if (item.isEdible() && item.getFoodProperties().isMeat() && !this.isAngry()) {
                if (!p_230254_1_.abilities.instabuild) {
                    itemstack.shrink(1);
                }

                if (this.random.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, p_230254_1_)) {
                    this.tame(p_230254_1_);
                    this.navigation.stop();
                    this.setTarget((LivingEntity) null);
                    this.setOrderedToSit(true);
                    this.level.broadcastEntityEvent(this, (byte) 7);
                } else {
                    this.level.broadcastEntityEvent(this, (byte) 6);
                }

                return ActionResultType.SUCCESS;
            }

            return super.mobInteract(p_230254_1_, p_230254_2_);
        }
    }

    public boolean hurt(DamageSource damageSource, float p_70097_2_) {
        if (this.isInvulnerableTo(damageSource)) {
            return false;
        } else {
            Entity entity = damageSource.getEntity();
            this.setOrderedToSit(false);
            if (entity != null && !(entity instanceof PlayerEntity) && !(entity instanceof AbstractArrowEntity)) {
                p_70097_2_ = (p_70097_2_ + 1.0F) / 2.0F;
            }

            return super.hurt(damageSource, p_70097_2_);
        }
    }

    public boolean canMate(AnimalEntity entity) {
        if (entity == this) {
            return false;
        } else if (!this.isTame()) {
            return false;
        } else if (!(entity instanceof PeaguinEntity)) {
            return false;
        } else {
            PeaguinEntity peaguinEntity = (PeaguinEntity) entity;
            if (!peaguinEntity.isTame()) {
                return false;
            } else if (peaguinEntity.isInSittingPose()) {
                return false;
            } else {
                return this.isInLove() && peaguinEntity.isInLove();
            }
        }
    }

    public boolean wantsToAttack(LivingEntity p_142018_1_, LivingEntity p_142018_2_) {
        if (!(p_142018_1_ instanceof CreeperEntity) && !(p_142018_1_ instanceof GhastEntity)) {
            if (p_142018_1_ instanceof PeaguinEntity) {
                PeaguinEntity peaguinEntity = (PeaguinEntity) p_142018_1_;
                return !peaguinEntity.isTame() || peaguinEntity.getOwner() != p_142018_2_;
            } else if (p_142018_1_ instanceof PlayerEntity && p_142018_2_ instanceof PlayerEntity && !((PlayerEntity) p_142018_2_).canHarmPlayer((PlayerEntity) p_142018_1_)) {
                return false;
            } else if (p_142018_1_ instanceof AbstractHorseEntity && ((AbstractHorseEntity) p_142018_1_).isTamed()) {
                return false;
            } else {
                return !(p_142018_1_ instanceof TameableEntity) || !((TameableEntity) p_142018_1_).isTame();
            }
        } else {
            return false;
        }
    }

    public int getRemainingPersistentAngerTime() {
        return this.entityData.get(DATA_REMAINING_ANGER_TIME);
    }

    public void setRemainingPersistentAngerTime(int p_230260_1_) {
        this.entityData.set(DATA_REMAINING_ANGER_TIME, p_230260_1_);
    }

    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.randomValue(this.random));
    }

    @Nullable
    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    public void setPersistentAngerTarget(@Nullable UUID p_230259_1_) {
        this.persistentAngerTarget = p_230259_1_;
    }

    public void addAdditionalSaveData(CompoundNBT compoundNBT) {
        super.addAdditionalSaveData(compoundNBT);

        this.addPersistentAngerSaveData(compoundNBT);
    }

    public void readAdditionalSaveData(CompoundNBT compoundNBT) {
        super.readAdditionalSaveData(compoundNBT);

        if (!level.isClientSide)
            this.readPersistentAngerSaveData((ServerWorld) this.level, compoundNBT);
    }

    public boolean canBreatheUnderwater() {
        return true;
    }

    public CreatureAttribute getMobType() {
        return CreatureAttribute.WATER;
    }

    public boolean checkSpawnObstruction(IWorldReader p_205019_1_) {
        return p_205019_1_.isUnobstructed(this);
    }

    public int getAmbientSoundInterval() {
        return 120;
    }

    protected int getExperienceReward(PlayerEntity playerEntity) {
        return 1 + this.level.random.nextInt(3);
    }

    public boolean isPushedByFluid() {
        return false;
    }

    public boolean canBeLeashed(PlayerEntity p_184652_1_) {
        return !this.isAngry() && super.canBeLeashed(p_184652_1_);
    }

    @OnlyIn(Dist.CLIENT)
    public Vector3d getLeashOffset() {
        return new Vector3d(0.0D, (double) (0.6F * this.getEyeHeight()), (double) (this.getBbWidth() * 0.4F));
    }

    protected boolean canRandomSwim() {
        return true;
    }

    static class SwimGoal extends RandomSwimmingGoal {
        private final PeaguinEntity fish;

        public SwimGoal(PeaguinEntity p_i48856_1_) {
            super(p_i48856_1_, 1.0D, 40);
            this.fish = p_i48856_1_;
        }

        public boolean canUse() {
            return this.fish.canRandomSwim() && super.canUse();
        }
    }

    /*protected PathNavigator createNavigation(World world) {
        if (this.isInWater()) {
            return new SwimmerPathNavigator(this, world);
        } else {
            return new GroundPathNavigator(this, world);
        }
    }*/

    private void switchNavigator(boolean onLand) {
        if (onLand) {
            this.moveControl = new MovementController(this);
            this.navigation = new GroundPathNavigator(this, level);
            this.isLandNavigator = true;
        } else {
            this.moveControl = new WaterMoveController(this, 1.5F);
            this.navigation = new WaterAndLandPathNavigator(this, level);
            this.isLandNavigator = false;
        }
    }

    public void tick() {
        super.tick();
        if (isInWater() && this.isLandNavigator) {
            switchNavigator(false);
        }
        if (!isInWater() && !this.isLandNavigator) {
            switchNavigator(true);
        }
    }

    public void travel(Vector3d stuckSpeedMultiplier) {
        /*if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(0.01F, stuckSpeedMultiplier);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
            if (this.getTarget() == null) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.005D, 0.0D));
            }
        } else {
            super.travel(stuckSpeedMultiplier);
        }*/
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(this.getSpeed(), stuckSpeedMultiplier);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
            if (this.getTarget() == null) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.005D, 0.0D));
            }
        } else {
            super.travel(stuckSpeedMultiplier);
        }
    }
}

    /*static class MoveHelperController extends MovementController {
        private final PeaguinEntity fish;

        MoveHelperController(PeaguinEntity p_i48857_1_) {
            super(p_i48857_1_);
            this.fish = p_i48857_1_;
        }

        public void tick() {
            if (this.fish.isEyeInFluid(FluidTags.WATER)) {
                this.fish.setDeltaMovement(this.fish.getDeltaMovement().add(0.0D, 0.005D, 0.0D));
            }

            if (this.operation == Action.MOVE_TO && !this.fish.getNavigation().isDone()) {
                float f = (float)(this.speedModifier * this.fish.getAttributeValue(Attributes.MOVEMENT_SPEED));
                this.fish.setSpeed(MathHelper.lerp(0.125F, this.fish.getSpeed(), f));
                double d0 = this.wantedX - this.fish.getX();
                double d1 = this.wantedY - this.fish.getY();
                double d2 = this.wantedZ - this.fish.getZ();
                if (d1 != 0.0D) {
                    double d3 = (double)MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                    this.fish.setDeltaMovement(this.fish.getDeltaMovement().add(0.0D, (double)this.fish.getSpeed() * (d1 / d3) * 0.1D, 0.0D));
                }

                if (d0 != 0.0D || d2 != 0.0D) {
                    float f1 = (float)(MathHelper.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
                    this.fish.yRot = this.rotlerp(this.fish.yRot, f1, 90.0F);
                    this.fish.yBodyRot = this.fish.yRot;
                }

            } else {
                this.fish.setSpeed(0.0F);
            }
        }
    }
}*/
