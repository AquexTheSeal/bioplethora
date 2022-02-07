package io.github.bioplethora.entity.creatures;

import io.github.bioplethora.BioplethoraConfig;
import io.github.bioplethora.entity.AnimatableMonsterEntity;
import io.github.bioplethora.entity.IBioplethoraEntityClass;
import io.github.bioplethora.entity.ai.monster.MonsterAnimatableMeleeGoal;
import io.github.bioplethora.entity.ai.monster.MonsterAnimatableMoveToTargetGoal;
import io.github.bioplethora.registry.BioplethoraEntityClasses;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemTier;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
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

import javax.annotation.Nullable;

public class GrylynenEntity extends AnimatableMonsterEntity implements IAnimatable, IFlyingAnimal, IBioplethoraEntityClass {

    private final AnimationFactory factory = new AnimationFactory(this);
    private final Tier grylynenTier;
    public int col;

    public GrylynenEntity(EntityType<? extends MonsterEntity> type, World worldIn, Tier tier) {
        super(type, worldIn);
        this.moveControl = new FlyingMovementController(this, 20, true);
        this.noCulling = true;
        this.xpReward = 15;
        this.grylynenTier = tier;
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
                .add(Attributes.FOLLOW_RANGE, 64D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 24.0F));
        this.goalSelector.addGoal(4, new RandomWalkingGoal(this, 0.5F));
        this.goalSelector.addGoal(1, new MonsterAnimatableMoveToTargetGoal(this, 1.2, 8));
        this.goalSelector.addGoal(1, new MonsterAnimatableMeleeGoal(this, 40, 0.6, 0.7));
        this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(5, new SwimGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, GrylynenEntity.class).setAlertOthers());
    }

    @Override
    public BioplethoraEntityClasses getBioplethoraClass() {
        return BioplethoraEntityClasses.DANGERUM;
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

    public void tick() {
        super.tick();

        double x = this.getX();
        double y = this.getY();
        double z = this.getZ();

        if (this.level instanceof ServerWorld) {
            ((ServerWorld) this.level).sendParticles(ParticleTypes.CRIT, x, y, z, 10, 0.65, 0.65, 0.65, 0.01);
        }
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
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(this.getGrylynenTier().getTierHeath());
            this.setHealth(this.getGrylynenTier().getTierHeath());

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
        if (this.getGrylynenTier() == Tier.WOODEN) {
            return SoundEvents.WOOD_BREAK;
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
            throw new IllegalArgumentException("Invalid Grylynen variant- such Grylynen variant does not exist.");
        }
    }

    @Override
    public net.minecraft.util.SoundEvent getAmbientSound() {
        return SoundEvents.BLAZE_AMBIENT;
    }

    @Override
    public net.minecraft.util.SoundEvent getDeathSound() {
        return SoundEvents.BLAZE_DEATH;
    }

    protected PathNavigator createNavigation(World worldIn) {
        return new FlyingPathNavigator(GrylynenEntity.this, worldIn) {
            public boolean isStableDestination(BlockPos pos) {
                return !this.level.getBlockState(pos.below()).isAir();
            }
        };
    }

    public float getWalkTargetValue(BlockPos pos, IWorldReader worldIn) {
        return worldIn.getBlockState(pos).isAir() ? 10.0F : 0.0F;
    }

    public boolean isNoGravity() {
        return true;
    }

    public boolean causeFallDamage(float distance, float damageMultiplier) {
        return false;
    }

    protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
    }

    public GrylynenEntity.Tier getGrylynenTier() {
        return grylynenTier;
    }

    public enum Tier {

        /**
         * @param name - Name of the type
         * @param crystalColor - Color of the Crystal under the Grylynen (Only allowed: green, yellow, red)
         * @param tier - Corresponding Item Tier
         * @param tierDmg - Grylynen Tier Base Attack Damage
         * @param tierHP - Grylynen Tier HP on Default
         * @param hellTierHP - Grylynen Tier HP on Hellmode Config
         */

        WOODEN("wooden", "green", ItemTier.WOOD, 5, 3, 4),
        STONE("stone", "green", ItemTier.STONE, 6, 4, 6),
        GOLDEN("golden", "yellow", ItemTier.GOLD, 6, 4, 6),
        IRON("iron", "yellow", ItemTier.IRON, 8, 6, 8),
        DIAMOND("diamond", "red", ItemTier.DIAMOND, 10, 8, 10),
        NETHERITE("netherite", "red", ItemTier.NETHERITE, 14, 10, 13);

        private final String name;
        private final String crystalColor;
        private final IItemTier tier;
        private final int tierDmg;
        private final int tierHP;
        private final int hellTierHP;

        Tier(String name, String crystalColor, IItemTier tier, int tierDmg, int tierHP, int hellTierHP) {
            this.name = name;
            this.crystalColor = crystalColor;
            this.tier = tier;
            this.tierDmg = tierDmg;
            this.tierHP = tierHP;
            this.hellTierHP = hellTierHP;
        }

        public String getTierName() {
            return this.name;
        }

        public String getCrystalColor() {
            return this.crystalColor;
        }

        public IItemTier getTierType() {
            return this.tier;
        }

        public int getTierDamage() {
            return this.tierDmg;
        }

        public int getTierHeath() {
            return this.tierHP;
        }

        public int getHellTierHP() {
            return this.hellTierHP;
        }
    }
}
