package io.github.bioplethora.entity.creatures;

import io.github.bioplethora.BioplethoraConfig;
import io.github.bioplethora.entity.BPAnimalEntity;
import io.github.bioplethora.entity.IBioClassification;
import io.github.bioplethora.entity.WaterAndLandAnimalEntity;
import io.github.bioplethora.entity.ai.HostileTamableNearestAttackableGoal;
import io.github.bioplethora.entity.ai.PeaguinFollowOwnerGoal;
import io.github.bioplethora.entity.ai.tameable.BPAnimalMeleeGoal;
import io.github.bioplethora.entity.ai.tameable.BPAnimalMoveToTargetGoal;
import io.github.bioplethora.enums.BPEntityClasses;
import io.github.bioplethora.registry.BioplethoraEntities;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.GhastEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
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

public class TrapjawEntity extends WaterAndLandAnimalEntity implements IAnimatable, IBioClassification {

    private final AnimationFactory factory = new AnimationFactory(this);

    public TrapjawEntity(EntityType<? extends BPAnimalEntity> type, World worldIn) {
        super(type, worldIn);
        this.setTame(false);
        this.noCulling = true;
    }

    @Override
    public BPEntityClasses getBioplethoraClass() {
        return BPEntityClasses.DANGERUM;
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createLivingAttributes()
                .add(Attributes.ARMOR, 4 * BioplethoraConfig.COMMON.mobArmorMultiplier.get())
                .add(Attributes.ATTACK_SPEED, 1.5)
                .add(Attributes.ATTACK_DAMAGE, 8 * BioplethoraConfig.COMMON.mobMeeleeDamageMultiplier.get())
                .add(Attributes.ATTACK_KNOCKBACK, 0.5 * BioplethoraConfig.COMMON.mobMeeleeDamageMultiplier.get())
                .add(Attributes.MAX_HEALTH, 80 * BioplethoraConfig.COMMON.mobHealthMultiplier.get())
                .add(Attributes.MOVEMENT_SPEED, 0.35D * BioplethoraConfig.COMMON.mobMovementSpeedMultiplier.get())
                .add(Attributes.FOLLOW_RANGE, 32D);
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
        this.goalSelector.addGoal(5, new PeaguinFollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(6, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new RandomWalkingGoal(this, 1.2, 8));

        this.goalSelector.addGoal(10, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(11, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(2, (new HurtByTargetGoal(this)).setAlertOthers());
        this.targetSelector.addGoal(1, new HostileTamableNearestAttackableGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(2, new HostileTamableNearestAttackableGoal<>(this, AnimalEntity.class, true));
    }

    @Nullable
    @Override
    public AgeableEntity getBreedOffspring(ServerWorld serverWorld, AgeableEntity entity) {
        TrapjawEntity trapjaw = BioplethoraEntities.TRAPJAW.get().create(serverWorld);
        UUID uuid = this.getOwnerUUID();
        if (uuid != null) {
            trapjaw.setOwnerUUID(uuid);
            trapjaw.setTame(true);
        }

        return trapjaw;
    }

    protected float getStandingEyeHeight(Pose pose, EntitySize entitySize) {
        return this.isBaby() ? entitySize.height * 0.8F : 1.0F;
    }

    public boolean isFood(ItemStack pStack) {
        Item item = pStack.getItem();
        return item.getFoodProperties().isMeat();
    }

    public ActionResultType mobInteract(PlayerEntity entity, Hand resultType) {
        ItemStack itemstack = entity.getItemInHand(resultType);
        Item item = itemstack.getItem();
        if (this.level.isClientSide) {
            boolean flag = this.isOwnedBy(entity) || this.isTame() || this.isFood(itemstack) && !this.isTame();
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

            } else if (this.isFood(itemstack)) {
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

    public boolean hurt(DamageSource damageSource, float pAmount) {
        if (this.isInvulnerableTo(damageSource)) {
            return false;
        } else {
            Entity entity = damageSource.getEntity();
            this.setOrderedToSit(false);
            if (entity != null && !(entity instanceof PlayerEntity) && !(entity instanceof AbstractArrowEntity)) {
                pAmount = (pAmount + 1.0F) / 2.0F;
            }

            return super.hurt(damageSource, pAmount);
        }
    }

    public boolean canMate(AnimalEntity entity) {
        if (entity == this) {
            return false;
        } else if (!this.isTame()) {
            return false;
        } else if (!(entity instanceof TrapjawEntity)) {
            return false;
        } else {
            TrapjawEntity trapjaw = (TrapjawEntity) entity;
            if (!trapjaw.isTame()) {
                return false;
            } else if (trapjaw.isInSittingPose()) {
                return false;
            } else {
                return this.isInLove() && trapjaw.isInLove();
            }
        }
    }

    public boolean wantsToAttack(LivingEntity pTarget, LivingEntity pOwner) {
        if (!(pTarget instanceof CreeperEntity) && !(pTarget instanceof GhastEntity)) {
            if (pTarget instanceof TrapjawEntity) {
                TrapjawEntity trapjaw = (TrapjawEntity) pTarget;
                return !trapjaw.isTame() || trapjaw.getOwner() != pOwner;
            } else if (pTarget instanceof PlayerEntity && pOwner instanceof PlayerEntity && !((PlayerEntity) pOwner).canHarmPlayer((PlayerEntity) pTarget)) {
                return false;
            } else if (pTarget instanceof AbstractHorseEntity && ((AbstractHorseEntity) pTarget).isTamed()) {
                return false;
            } else {
                return !(pTarget instanceof TameableEntity) || !((TameableEntity) pTarget).isTame();
            }
        } else {
            return false;
        }
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
        return this.isTame() && super.canBeLeashed(p_184652_1_);
    }

    @OnlyIn(Dist.CLIENT)
    public Vector3d getLeashOffset() {
        return new Vector3d(0.0D, this.getEyeHeight(), this.getBbWidth() * 0.4F);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "trapjaw_controller", 0, this::predicate));
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.trapjaw.idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}
