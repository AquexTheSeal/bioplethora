package io.github.bioplethora.entity;

import io.github.bioplethora.config.BioplethoraConfig;
import io.github.bioplethora.entity.ai.AnimalAnimatableMeleeGoal;
import io.github.bioplethora.entity.ai.AnimalAnimatableMoveToTargetGoal;
import io.github.bioplethora.registry.BioplethoraEntities;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.DolphinLookController;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class PeaguinEntity extends AnimatableAnimalEntity implements IAnimatable {
    private final AnimationFactory factory = new AnimationFactory(this);

    public PeaguinEntity(EntityType<? extends AnimatableAnimalEntity> type, World world) {
        super(type, world);
        this.setPathfindingMalus(PathNodeType.WATER, 0.0F);
        this.noCulling = true;
        /*this.moveControl = new MoveHelperController(this);*/
        this.lookControl = new DolphinLookController(this, 10);
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
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.2));
        this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 24.0F));
        this.goalSelector.addGoal(4, new RandomWalkingGoal(this, 0.5F));
        this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 5, 40));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1, Ingredient.of(Items.SALMON), false));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1, Ingredient.of(Items.COOKED_SALMON), false));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1, Ingredient.of(Items.COD), false));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1, Ingredient.of(Items.COOKED_COD), false));
        this.goalSelector.addGoal(3, new FollowParentGoal(this, 1));
        this.goalSelector.addGoal(3, new FollowOwnerGoal(this, 1, 10, 2, false));
        this.goalSelector.addGoal(6, new FollowMobGoal(this, (float) 1, 10, 5));

        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new OwnerHurtTargetGoal(this));
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "peaguincontroller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    private <E extends IAnimatable>PlayState predicate(AnimationEvent<E> event) {

        if(this.isDeadOrDying() || this.dead) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.peaguin.death", true));
            return PlayState.CONTINUE;
        }

        if(this.getAttacking()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.peaguin.death", true));
            return PlayState.CONTINUE;
        }

        if(event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.peaguin.walking", true));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.peaguin.idle", true));
        return PlayState.CONTINUE;
    }

    public PeaguinEntity getBreedOffspring(ServerWorld serverWorld, AgeableEntity ageableEntity) {
        return BioplethoraEntities.PEAGUIN.get().create(serverWorld);
    }

    protected float getStandingEyeHeight(Pose pose, EntitySize entitySize) {
        return this.isBaby() ? entitySize.height * 0.8F : 1.0F;
    }

    public void aiStep() {
        super.aiStep();
        if (!this.isBaby()) {
            this.goalSelector.addGoal(1, new BreedGoal(this, 1));
            this.goalSelector.addGoal(2, new AnimalAnimatableMoveToTargetGoal(this, 1.6, 8));
            this.goalSelector.addGoal(2, new AnimalAnimatableMeleeGoal(this, 40, 0.2, 0.3));
        }

        if (this.isTame()) {
            this.targetSelector.removeGoal(new HurtByTargetGoal(this).setAlertOthers(this.getClass()));
        }
    }

    @Override
    public boolean isFood(ItemStack stack) {
        if (stack == null)
            return false;
        if (Items.SALMON == stack.getItem())
            return true;
        if (Items.COOKED_SALMON == stack.getItem())
            return true;
        if (Items.COD == stack.getItem())
            return true;
        if (Items.COOKED_COD == stack.getItem())
            return true;
        return false;
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

    public boolean canBeLeashed(PlayerEntity playerEntity) {
        return true;
    }

    /*@Override
    protected PathNavigator createNavigation(World worldIn) {
        return new SwimmerPathNavigator(this, worldIn);
    }

    static class MoveHelperController extends MovementController {
        private final PeaguinEntity whale;

        MoveHelperController(PeaguinEntity whale) {
            super(whale);
            this.whale = whale;
        }

        @Override
        public void tick() {
            if (this.whale.isEyeInFluid(FluidTags.WATER)) {
                this.whale.setDeltaMovement(this.whale.getDeltaMovement().add(0.0D, 0.005D, 0.0D));
            }

            if (this.operation == MovementController.Action.MOVE_TO && !this.whale.getNavigation().isDone()) {
                double d0 = this.wantedX - this.whale.getX();
                double d1 = this.wantedY - this.whale.getY();
                double d2 = this.wantedZ - this.whale.getZ();
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;

                if (d3 < (double)2.5000003E-7F) {
                    this.mob.setZza(0.0F);
                } else {
                    float f = (float)(MathHelper.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
                    this.whale.yRot = this.rotlerp(this.whale.yRot, f, 10.0F);
                    this.whale.yBodyRot = this.whale.yRot;
                    this.whale.yHeadRot = this.whale.yRot;
                    float f1 = (float)(this.speedModifier * this.whale.getAttributeValue(Attributes.MOVEMENT_SPEED));

                    if (this.whale.isInWater()) {
                        this.whale.setSpeed(f1 * 0.02F);
                        float f2 = -((float)(MathHelper.atan2(d1, (double)MathHelper.sqrt(d0 * d0 + d2 * d2)) * (double)(180F / (float)Math.PI)));
                        f2 = MathHelper.clamp(MathHelper.wrapDegrees(f2), -85.0F, 85.0F);
                        this.whale.xRot = this.rotlerp(this.whale.xRot, f2, 5.0F);
                        float f3 = MathHelper.cos(this.whale.xRot * ((float)Math.PI / 180F));
                        float f4 = MathHelper.sin(this.whale.xRot * ((float)Math.PI / 180F));
                        this.whale.zza = f3 * f1;
                        this.whale.yya = -f4 * f1;
                    } else {
                        this.whale.setSpeed(f1 * 0.1F);
                    }

                }
            } else {
                this.whale.setSpeed(0.0F);
                this.whale.setXxa(0.0F);
                this.whale.setYya(0.0F);
                this.whale.setZza(0.0F);
            }
        }
    }*/
}
