package io.github.bioplethora.entity.creatures;

import io.github.bioplethora.entity.AnimatableMonsterEntity;
import io.github.bioplethora.entity.IBioplethoraEntityClass;
import io.github.bioplethora.registry.BioplethoraEntityClasses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;

public class GrylynenEntity extends AnimatableMonsterEntity implements IAnimatable, IBioplethoraEntityClass {

    private final AnimationFactory factory = new AnimationFactory(this);
    private final Tier grylynenTier;

    public GrylynenEntity(EntityType<? extends MonsterEntity> type, World worldIn, Tier tier) {
        super(type, worldIn);
        this.noCulling = true;
        this.grylynenTier = tier;
    }

    @Override
    public BioplethoraEntityClasses getBioplethoraClass() {
        return BioplethoraEntityClasses.DANGERUM;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.grylynen.idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "grylynen_controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    public int specialDurability() {
        if (this.getGrylynenTier() == Tier.WOODEN) {return 1;}
        else if (this.getGrylynenTier() == Tier.STONE) {return 2;}
        else if (this.getGrylynenTier() == Tier.GOLDEN) {return 2;}
        else if (this.getGrylynenTier() == Tier.IRON) {return 3;}
        else if (this.getGrylynenTier() == Tier.DIAMOND) {return 5;}
        else if (this.getGrylynenTier() == Tier.NETHERITE) {return 7;}
        else {return 1;}
    }

    @Nullable
    public ILivingEntityData finalizeSpawn(IServerWorld iServerWorld, DifficultyInstance difficultyInstance, SpawnReason spawnReason, @Nullable ILivingEntityData iLivingEntityData, @Nullable CompoundNBT compoundNBT) {
        iLivingEntityData = super.finalizeSpawn(iServerWorld, difficultyInstance, spawnReason, iLivingEntityData, compoundNBT);
        return iLivingEntityData;
    }

    @Override
    public net.minecraft.util.SoundEvent getHurtSound(DamageSource damageSource) {
        if (this.getGrylynenTier() == Tier.WOODEN) {
            return SoundEvents.WITHER_BREAK_BLOCK;
        } else if (this.getGrylynenTier() == Tier.STONE) {
            return SoundEvents.STONE_BREAK;
        } else if (this.getGrylynenTier() == Tier.GOLDEN) {
            return SoundEvents.NETHER_GOLD_ORE_BREAK;
        } else if (this.getGrylynenTier() == Tier.IRON) {
            return SoundEvents.BONE_BLOCK_BREAK;
        } else if (this.getGrylynenTier() == Tier.DIAMOND) {
            return SoundEvents.IRON_GOLEM_DAMAGE;
        } else if (this.getGrylynenTier() == Tier.NETHERITE) {
            return SoundEvents.NETHERITE_BLOCK_BREAK;
        } else {
            return SoundEvents.GENERIC_HURT;
        }
    }

    public GrylynenEntity.Tier getGrylynenTier() {
        return grylynenTier;
    }

    @Override
    public net.minecraft.util.SoundEvent getDeathSound() {
        return SoundEvents.GENERIC_DEATH;
    }

    public enum Tier {
        WOODEN("wooden"),
        STONE("stone"),
        GOLDEN("golden"),
        IRON("iron"),
        DIAMOND("diamond"),
        NETHERITE("netherite");

        private final String name;

        Tier(String name) {
            this.name = name;
        }

        public String getNameString() {
            return this.name;
        }
    }
}
