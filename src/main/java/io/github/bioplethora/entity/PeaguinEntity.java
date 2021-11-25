package io.github.bioplethora.entity;

import io.github.bioplethora.config.BioplethoraConfig;
import io.github.bioplethora.entity.ai.AnimalAnimatableMeleeGoal;
import io.github.bioplethora.entity.ai.AnimalAnimatableMoveToTargetGoal;
import io.github.bioplethora.registry.BioplethoraEntities;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
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
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createLivingAttributes()
                .add(Attributes.ARMOR, 2 * BioplethoraConfig.COMMON.mobArmorMultiplier.get())
                .add(Attributes.ATTACK_SPEED, 1.5)
                .add(Attributes.ATTACK_DAMAGE, 2 * BioplethoraConfig.COMMON.mobMeeleeDamageMultiplier.get())
                .add(Attributes.MAX_HEALTH, 40 * BioplethoraConfig.COMMON.mobHealthMultiplier.get())
                .add(Attributes.MOVEMENT_SPEED, 2.5 * BioplethoraConfig.COMMON.mobMovementSpeedMultiplier.get());
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new AnimalAnimatableMoveToTargetGoal(this, 1.6, 8));
        this.goalSelector.addGoal(2, new AnimalAnimatableMeleeGoal(this, 60, 0.6, 0.7));
        this.goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 24.0F));
        this.goalSelector.addGoal(4, new RandomWalkingGoal(this, 0.5F));
        this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(5, new SwimGoal(this));
        this.goalSelector.addGoal(1, new BreedGoal(this, 1));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this).setAlertOthers(this.getClass()));
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

        if(event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.peaguin.walking", true));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.nandbri.idle", true));
        return PlayState.CONTINUE;
    }

    public PeaguinEntity getBreedOffspring(ServerWorld serverWorld, AgeableEntity ageableEntity) {
        return BioplethoraEntities.PEAGUIN.get().create(serverWorld);
    }

    protected float getStandingEyeHeight(Pose pose, EntitySize entitySize) {
        return this.isBaby() ? entitySize.height * 0.8F : 1.0F;
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
}
