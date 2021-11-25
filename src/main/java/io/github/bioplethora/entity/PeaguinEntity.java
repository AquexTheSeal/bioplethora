package io.github.bioplethora.entity;

import io.github.bioplethora.config.BioplethoraConfig;
import io.github.bioplethora.entity.ai.AnimalAnimatableMeleeGoal;
import io.github.bioplethora.entity.ai.AnimalAnimatableMoveToTargetGoal;
import io.github.bioplethora.registry.BioplethoraEntities;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.DolphinLookController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
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
        this.goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 24.0F));
        this.goalSelector.addGoal(4, new RandomWalkingGoal(this, 0.5F));
        this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(5, new RandomSwimmingGoal(this, 5, 40));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1, Ingredient.of(Items.SALMON), false));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1, Ingredient.of(Items.COOKED_SALMON), false));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1, Ingredient.of(Items.COD), false));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1, Ingredient.of(Items.COOKED_COD), false));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1));
        this.goalSelector.addGoal(6, new FollowMobGoal(this, (float) 1, 10, 5));
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
            this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
            this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
            this.goalSelector.addGoal(2, new AnimalAnimatableMoveToTargetGoal(this, 1.6, 8));
            this.goalSelector.addGoal(2, new AnimalAnimatableMeleeGoal(this, 40, 0.2, 0.3));
            this.targetSelector.addGoal(2, new HurtByTargetGoal(this).setAlertOthers(this.getClass()));
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
}
