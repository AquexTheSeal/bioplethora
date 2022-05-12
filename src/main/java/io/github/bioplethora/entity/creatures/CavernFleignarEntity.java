package io.github.bioplethora.entity.creatures;

import io.github.bioplethora.config.BPConfig;
import io.github.bioplethora.entity.BPMonsterEntity;
import io.github.bioplethora.entity.IBioClassification;
import io.github.bioplethora.entity.ai.goals.CavernFleignarMeleeGoal;
import io.github.bioplethora.enums.BPEntityClasses;
import io.github.bioplethora.registry.BPEffects;
import io.github.bioplethora.registry.BPTags;
import io.github.bioplethora.world.BPVanillaBiomeFeatureGeneration;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.*;
import net.minecraft.world.server.ServerWorld;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;

public class CavernFleignarEntity extends BPMonsterEntity implements IAnimatable, IBioClassification {

    private final AnimationFactory factory = new AnimationFactory(this);
    public boolean isHuge;
    public boolean finalize = false;

    public CavernFleignarEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
        this.noCulling = true;
    }

    @Override
    public BPEntityClasses getBioplethoraClass() {
        return BPEntityClasses.PLETHONEUTRAL;
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createLivingAttributes()
                .add(Attributes.ARMOR, 4 * BPConfig.COMMON.mobArmorMultiplier.get())
                .add(Attributes.ATTACK_SPEED, 10)
                .add(Attributes.ATTACK_DAMAGE, 8 * BPConfig.COMMON.mobMeeleeDamageMultiplier.get())
                .add(Attributes.ATTACK_KNOCKBACK, 1.5D)
                .add(Attributes.MAX_HEALTH, 40 * BPConfig.COMMON.mobHealthMultiplier.get())
                .add(Attributes.KNOCKBACK_RESISTANCE, 10)
                .add(Attributes.MOVEMENT_SPEED, 0.1 * BPConfig.COMMON.mobMovementSpeedMultiplier.get())
                .add(Attributes.FOLLOW_RANGE, 24D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 24.0F));
        this.goalSelector.addGoal(2, new CavernFleignarMeleeGoal(this, 20, 0.8, 0.9));
        this.goalSelector.addGoal(5, new LookRandomlyGoal(this));

        //this.targetSelector.addGoal(2, new CavernFleignarTargetGoal(this, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, false, false, this::validTarget));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers(this.getClass()));
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (this.dead) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.cavern_fleignar.death", true));
            return PlayState.CONTINUE;
        }

        if (this.getAttacking()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.cavern_fleignar.attack", true));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.cavern_fleignar.idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "cavern_fleignar_controller", 0, this::predicate));
    }

    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        if (worldIn instanceof ServerWorld && BPConfig.COMMON.hellMode.get()) {
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(10 * BPConfig.COMMON.mobMeeleeDamageMultiplier.get());
            this.getAttribute(Attributes.ARMOR).setBaseValue(8 * BPConfig.COMMON.mobArmorMultiplier.get());
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(80 * BPConfig.COMMON.mobHealthMultiplier.get());
            this.setHealth(80 * BPConfig.COMMON.mobHealthMultiplier.get());
        }

        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public float getScale() {
        return this.isHuge ? 1.5F : 1.0F;
    }

    @Override
    public net.minecraft.util.SoundEvent getAmbientSound() {
        return SoundEvents.SQUID_AMBIENT;
    }

    @Override
    public net.minecraft.util.SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.SQUID_HURT;
    }

    @Override
    public net.minecraft.util.SoundEvent getDeathSound() {
        return SoundEvents.SQUID_DEATH;
    }

    @Override
    protected float getVoicePitch() {
        return 0.5F;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.hasEffect(Effects.POISON)) {
            this.removeEffect(Effects.POISON);
        }

        if (!finalize) {
            if (Math.random() <= 0.5) {
                this.isHuge = true;
            }
            finalize = true;
        }
    }

    public boolean doHurtTarget(Entity entity) {
        boolean flag = super.doHurtTarget(entity);

        for (LivingEntity targetArea : this.level.getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(4, 2, 4))) {

            if (targetArea != this) {
                float knockbackValue = ((float) this.getAttributeValue(Attributes.ATTACK_KNOCKBACK) / 2);
                int poisonDuration = BPConfig.IN_HELLMODE ? 60 : 100;
                int poisonAmplification = BPConfig.IN_HELLMODE ? 0 : 1;

                if (this.level instanceof ServerWorld) {
                    ((ServerWorld) this.level).sendParticles(ParticleTypes.POOF, entity.getX(), entity.getY(), entity.getZ(), 15, 1.2, 0.2, 1.2, 0.01);
                }

                targetArea.knockback(knockbackValue * 0.5F, MathHelper.sin(this.yRot * ((float) Math.PI / 180F)), -MathHelper.cos(this.yRot * ((float) Math.PI / 180F)));
                targetArea.hurt(DamageSource.mobAttack(this), (float) (this.getAttributeValue(Attributes.ATTACK_KNOCKBACK) * 0.75));
                if (targetArea instanceof CavernFleignarEntity) {
                    targetArea.addEffect(new EffectInstance(Effects.POISON, poisonDuration, poisonAmplification));
                }
            }
        }
        return flag;
    }

    @Override
    protected void tickDeath() {

        ++this.deathTime;

        if (this.deathTime == 40) {
            this.remove();

            for (int i = 0; i < 100; ++i) {
                double d0 = this.random.nextGaussian() * 0.02D;
                double d1 = this.random.nextGaussian() * 0.02D;
                double d2 = this.random.nextGaussian() * 0.02D;

                this.level.addParticle(ParticleTypes.POOF, this.getRandomX(1.0D), this.getRandomY(), this.getRandomZ(1.0D), d0, d1, d2);
            }
        }
    }

    @Override
    public void checkDespawn() {
        BlockPos posBelow = new BlockPos(this.getX(), this.getY(), this.getZ()).below();
        if (!this.level.getBlockState(posBelow).is(BPTags.Blocks.ALPHANIA)) {
            super.checkDespawn();
        } else {
            if (this.level.getDifficulty() == Difficulty.PEACEFUL && this.shouldDespawnInPeaceful()) {
                this.remove();
            }
        }
    }

    @Override
    public boolean checkSpawnRules(IWorld world, SpawnReason reason) {
        return super.checkSpawnRules(world, reason) && CavernFleignarEntity.checkFleignarSpawnRules(level, blockPosition());
    }

    public static boolean checkFleignarSpawnRules(IWorld world, BlockPos pos) {
        if (world instanceof ISeedReader) {
            return pos.getY() <= 50 && BPVanillaBiomeFeatureGeneration.isFleignariteChunk(pos, (ISeedReader) world);
        } else {
            return  false;
        }
    }

    public boolean isPushable() {
        return false;
    }

    public boolean validTarget(LivingEntity target) {
        boolean getTag = EntityTypeTags.getAllTags().getTagOrEmpty(BPTags.Entities.FLEIGNAR_TARGETS.getName()).contains(target.getType());

        if (EntityPredicates.ATTACK_ALLOWED.test(target) && (getTag || target instanceof PlayerEntity)) {
            return !target.hasEffect(BPEffects.SPIRIT_MANIPULATION.get());
        } else {
            return false;
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("fleignar_huge", this.isHuge);
        pCompound.putBoolean("hasFinalized", this.finalize);
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT pCompound) {
        this.isHuge = pCompound.getBoolean("fleignar_huge");
        this.finalize = pCompound.getBoolean("hasFinalized");
    }
}