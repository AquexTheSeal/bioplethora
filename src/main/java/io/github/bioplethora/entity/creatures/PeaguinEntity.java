package io.github.bioplethora.entity.creatures;

import io.github.bioplethora.BioplethoraConfig;
import io.github.bioplethora.entity.BPAnimalEntity;
import io.github.bioplethora.entity.IBioClassification;
import io.github.bioplethora.entity.WaterAndLandAnimalEntity;
import io.github.bioplethora.entity.ai.WaterAndLandFollowOwnerGoal;
import io.github.bioplethora.entity.ai.tameable.BPAnimalMeleeGoal;
import io.github.bioplethora.entity.ai.tameable.BPAnimalMoveToTargetGoal;
import io.github.bioplethora.enums.BPEntityClasses;
import io.github.bioplethora.registry.BioplethoraEntities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.GhastEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorldReader;
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
import java.util.UUID;
import java.util.function.Predicate;

public class PeaguinEntity extends WaterAndLandAnimalEntity implements IAnimatable, IAngerable, IBioClassification {

    private static final DataParameter<Integer> DATA_REMAINING_ANGER_TIME = EntityDataManager.defineId(PeaguinEntity.class, DataSerializers.INT);
    private static final RangedInteger PERSISTENT_ANGER_TIME = TickRangeConverter.rangeOfSeconds(20, 39);

    public static final Predicate<LivingEntity> PREY_SELECTOR = (entity) -> entity instanceof AbstractFishEntity;

    private UUID persistentAngerTarget;

    private final AnimationFactory factory = new AnimationFactory(this);

    public PeaguinEntity(EntityType<? extends BPAnimalEntity> type, World worldIn) {
        super(type, worldIn);
        this.setTame(false);
        this.noCulling = true;
    }

    @Override
    public BPEntityClasses getBioplethoraClass() {
        return BPEntityClasses.PLETHONEUTRAL;
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
        this.goalSelector.addGoal(2, new SitGoal(this));
        this.goalSelector.addGoal(4, new BPAnimalMoveToTargetGoal(this, 1.6, 8) {
            @Override
            public boolean canUse() {
                if (!entity.isInWater()) {
                    return super.canUse();
                } else {
                    return false;
                }
            }

            @Override
            public boolean canContinueToUse() {
                if (!entity.isInWater()) {
                    return super.canContinueToUse();
                } else {
                    return false;
                }
            }
        });
        this.goalSelector.addGoal(4, new BPAnimalMeleeGoal(this, 20, 0.5, 0.75));
        this.goalSelector.addGoal(4, new BPAnimalMoveToTargetGoal(this, 1.6, 8) {
            @Override
            public boolean canUse() {
                if (!entity.isInWater()) {
                    return super.canUse();
                } else {
                    return false;
                }
            }

            @Override
            public boolean canContinueToUse() {
                if (!entity.isInWater()) {
                    return super.canContinueToUse();
                } else {
                    return false;
                }
            }
        });
        this.goalSelector.addGoal(5, new WaterAndLandFollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(6, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new RandomWalkingGoal(this, 1.2, 8));

        this.goalSelector.addGoal(10, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(11, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(2, (new HurtByTargetGoal(this)).setAlertOthers());
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 10, true, false, this::isAngryAt));
        this.targetSelector.addGoal(3, new NonTamedTargetGoal<>(this, AbstractFishEntity.class, false, PREY_SELECTOR));
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "peaguin_controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public net.minecraft.util.SoundEvent getAmbientSound() {
        if (this.isInWater()) {
            return SoundEvents.DOLPHIN_AMBIENT_WATER;
        } else {
            return SoundEvents.DOLPHIN_AMBIENT;
        }
    }

    @Override
    public net.minecraft.util.SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.DOLPHIN_HURT;
    }

    @Override
    public net.minecraft.util.SoundEvent getDeathSound() {
        return SoundEvents.DOLPHIN_DEATH;
    }

    @Override
    public void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.SALMON_FLOP, 0.15f, 1);
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

    public boolean isFood(ItemStack pStack) {
        Item item = pStack.getItem();
        return ItemTags.getAllTags().getTagOrEmpty(ItemTags.FISHES.getName()).contains(item);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_REMAINING_ANGER_TIME, 0);
    }

    public ActionResultType mobInteract(PlayerEntity entity, Hand resultType) {
        ItemStack itemstack = entity.getItemInHand(resultType);
        Item item = itemstack.getItem();
        if (this.level.isClientSide) {
            boolean flag = this.isOwnedBy(entity) || this.isTame() || this.isFood(itemstack) && !this.isTame() && !this.isAngry();
            return flag ? ActionResultType.CONSUME : ActionResultType.PASS;
        } else {
            if (this.isTame()) {
                if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
                    if (!entity.abilities.instabuild) {
                        itemstack.shrink(1);
                    }

                    this.heal((float) item.getFoodProperties().getNutrition());
                    return ActionResultType.SUCCESS;
                }

                ActionResultType actionresulttype = super.mobInteract(entity, resultType);
                if ((!actionresulttype.consumesAction() || this.isBaby()) && this.isOwnedBy(entity)) {
                    this.setOrderedToSit(!this.isOrderedToSit());
                    this.jumping = false;
                    this.navigation.stop();
                    this.setTarget(null);
                    return ActionResultType.SUCCESS;
                }

                return actionresulttype;

            } else if (this.isFood(itemstack) && !this.isAngry()) {
                if (!entity.abilities.instabuild) {
                    itemstack.shrink(1);
                }

                if (this.random.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, entity)) {
                    this.tame(entity);
                    this.navigation.stop();
                    this.setTarget(null);
                    this.setOrderedToSit(true);
                    this.level.broadcastEntityEvent(this, (byte) 7);
                } else {
                    this.level.broadcastEntityEvent(this, (byte) 6);
                }

                return ActionResultType.SUCCESS;
            }

            return super.mobInteract(entity, resultType);
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
        return new Vector3d(0.0D, this.getEyeHeight(), this.getBbWidth() * 0.4F);
    }
}
