package io.github.bioplethora.entity.creatures;

import io.github.bioplethora.api.mixin.IPlayerEntityMixin;
import io.github.bioplethora.api.world.BlockUtils;
import io.github.bioplethora.api.world.EntityUtils;
import io.github.bioplethora.config.BPConfig;
import io.github.bioplethora.entity.BPMonsterEntity;
import io.github.bioplethora.entity.IBioClassification;
import io.github.bioplethora.entity.IMobCappedEntity;
import io.github.bioplethora.entity.ai.gecko.GeckoMoveToTargetGoal;
import io.github.bioplethora.entity.ai.goals.*;
import io.github.bioplethora.entity.others.AlphanumShardEntity;
import io.github.bioplethora.entity.others.BPEffectEntity;
import io.github.bioplethora.entity.others.part.BPPartEntity;
import io.github.bioplethora.enums.BPEffectTypes;
import io.github.bioplethora.enums.BPEntityClasses;
import io.github.bioplethora.registry.BPAttributes;
import io.github.bioplethora.registry.BPEntities;
import io.github.bioplethora.registry.BPParticles;
import io.github.bioplethora.registry.BPSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.FireworkParticle;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.*;
import net.minecraft.world.server.ServerBossInfo;
import net.minecraft.world.server.ServerWorld;
import software.bernie.example.client.model.entity.BikeModel;
import software.bernie.example.client.renderer.entity.BikeGeoRenderer;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;

public class AlphemKingEntity extends BPMonsterEntity implements IAnimatable, IBioClassification, IMobCappedEntity {

    protected static final DataParameter<Boolean> WAKING = EntityDataManager.defineId(AlphemKingEntity.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Boolean> ATTACKING2 = EntityDataManager.defineId(AlphemKingEntity.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Boolean> SMASHING = EntityDataManager.defineId(AlphemKingEntity.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Boolean> ROARING = EntityDataManager.defineId(AlphemKingEntity.class, DataSerializers.BOOLEAN);
    // TODO: 16/02/2022 Add an animation for shooting/charging. Add a new and stronger projectile to replace the Windblaze projectile.
    protected static final DataParameter<Boolean> CHARGING = EntityDataManager.defineId(AlphemKingEntity.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Boolean> JUMPING = EntityDataManager.defineId(AlphemKingEntity.class, DataSerializers.BOOLEAN);
    // TODO: 16/02/2022 Add a ramming animation and a ramming AI goal.
    protected static final DataParameter<Boolean> RAMMING = EntityDataManager.defineId(AlphemKingEntity.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Boolean> BARRIERED = EntityDataManager.defineId(AlphemKingEntity.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Boolean> BERSERKED = EntityDataManager.defineId(AlphemKingEntity.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Boolean> PURSUIT = EntityDataManager.defineId(AlphemKingEntity.class, DataSerializers.BOOLEAN);

    private final ServerBossInfo bossInfo = (ServerBossInfo) (new ServerBossInfo(this.getDisplayName(), BossInfo.Color.GREEN, BossInfo.Overlay.PROGRESS).setDarkenScreen(true).setPlayBossMusic(true));
    private final AnimationFactory factory = new AnimationFactory(this);
    public boolean explodedOnDeath = false;
    public double healthRegenTimer = 0;
    public int summonShardTimer;
    public int attackPhase;
    public int wakeTimer;
    public int barrierTimer;
    public float vecOfTarget;
    public boolean inWall;
    public int dyingExpTimer;

    // Extras - For modded mobs that picks up mobs.
    public int pickupTimer;

    /*
    public final KingPartEntity[] subEntities;
    public final KingPartEntity core;
    */

    public AlphemKingEntity(EntityType<? extends MonsterEntity> type, World world) {
        super(type, world);
        this.noCulling = true;
        this.xpReward = 20;
        /*
        core = new KingPartEntity<>(this, "core", 1.6f, 1.6f);
        subEntities = new KingPartEntity[]{core};
        */
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createLivingAttributes()
                .add(Attributes.ARMOR, 14.5 * BPConfig.COMMON.mobArmorMultiplier.get())
                .add(Attributes.ATTACK_SPEED, 0.001)
                .add(Attributes.ATTACK_DAMAGE, 30 * BPConfig.COMMON.mobMeeleeDamageMultiplier.get())
                .add(Attributes.ATTACK_KNOCKBACK, 7.0D)
                .add(Attributes.MAX_HEALTH, 395 * BPConfig.COMMON.mobHealthMultiplier.get())
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.5)
                .add(Attributes.MOVEMENT_SPEED, 0.25F * BPConfig.COMMON.mobMovementSpeedMultiplier.get())
                .add(Attributes.FOLLOW_RANGE, 64.0D)
                .add(BPAttributes.TRUE_DEFENSE.get(), 2D);
    }

    @Override
    public BPEntityClasses getBioplethoraClass() {
        return BPEntityClasses.ELDERIA;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 24.0F));
        this.goalSelector.addGoal(4, new RandomWalkingGoal(this, 1.0F));
        this.goalSelector.addGoal(1, new AlphemKingRoarGoal(this));
        this.goalSelector.addGoal(2, new GeckoMoveToTargetGoal<AlphemKingEntity>(this, 1.15F, 8) {
            @Override
            public boolean canUse() {
                if (RANDOM.nextInt(this.checkRate) == 0) return false;
                if (!entity.isBusy()) {
                    return this.isExecutable(this, this.entity, this.entity.getTarget());
                } else {
                    return false;
                }
            }
            @Override
            public boolean canContinueToUse() {
                return this.canUse();
            }
            @Override
            public void moveToTarget() {
                Vector3d vector3d = RandomPositionGenerator.getPosAvoid(entity, 16, 7, entity.getTarget().position());
                Path pathT = null;
                if (vector3d != null) {
                    pathT = entity.getNavigation().createPath(vector3d.x, vector3d.y, vector3d.z, 0);
                }
                if (this.entity.isCharging() && pathT != null) {
                    entity.getNavigation().moveTo(pathT, this.speedMultiplier);
                } else {
                    super.moveToTarget();
                }
            }
        });
        // 1st phase meelee
        this.goalSelector.addGoal(2, new AlphemKingMeeleeGoal(this, 80, 0.5, 0.6));
        // 2nd phase meelee
        this.goalSelector.addGoal(2, new AlphemKingSecondMeeleeGoal(this, 80, 0.5, 0.6));
        // 3rd phase meelee
        this.goalSelector.addGoal(2, new AlphemKingSmashingGoal(this, 120, 0.8, 0.9));
        this.goalSelector.addGoal(3, new AlphemKingPursuitGoal(this));
        this.goalSelector.addGoal(4, new AlphemKingRangedAttackGoal(this));
        this.goalSelector.addGoal(5, new AlphemKingJumpGoal(this));
        this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(6, new SwimGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, FrostbiteGolemEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AltyrusEntity.class, true));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
    }

    @Override
    public void registerControllers(AnimationData data) {
        AnimationController controller = new AnimationController<>(this, "alphem_king_controller", 0, this::predicate);
        data.addAnimationController(controller);
    }

    public boolean isBusy() {
        return this.getAttacking() || this.getAttacking() || this.getAttacking2() || this.getSmashing() || this.isPursuit();
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {

        if (this.isDeadOrDying()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.alphem_king.death", true));
            return PlayState.CONTINUE;
        }

        if (this.getWaking()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.alphem_king.waking", true));
            return PlayState.CONTINUE;
        }

        if (this.getRoaring()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.alphem_king.roar", true));
            return PlayState.CONTINUE;
        }

        if (this.isPursuit()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.alphem_king.pursuit", true));
            return PlayState.CONTINUE;
        }

        if (this.getSmashing()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.alphem_king.attack_2", true));
            return PlayState.CONTINUE;
        }

        if (this.getAttacking2()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.alphem_king.attack_1", true));
            return PlayState.CONTINUE;
        }

        if (this.getAttacking()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.alphem_king.attack_0", true));
            return PlayState.CONTINUE;
        }

        if (event.isMoving() && this.isBerserked()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.alphem_king.walk_berserk", true));
            return PlayState.CONTINUE;
        }

        if (event.isMoving() && !this.isBerserked()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.alphem_king.walk", true));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.alphem_king.idle", true));
        return PlayState.CONTINUE;
    }

    public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        if (worldIn instanceof ServerWorld && BPConfig.COMMON.hellMode.get()) {
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(35 * BPConfig.COMMON.mobMeeleeDamageMultiplier.get());
            this.getAttribute(Attributes.ARMOR).setBaseValue(21.5 * BPConfig.COMMON.mobArmorMultiplier.get());
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(550 * BPConfig.COMMON.mobHealthMultiplier.get());
            this.setHealth(380 * BPConfig.COMMON.mobHealthMultiplier.get());
        }
        if (reason == SpawnReason.MOB_SUMMONED) {
            this.setWaking(true);
            this.setNoAi(true);
        }
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    public void push(double pX, double pY, double pZ) {
        if (!this.isPursuit()) {
            super.push(pX, pY, pZ);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getWaking() || this.isNoAi()) {
            ++wakeTimer;
            if (wakeTimer == 30) {
                this.playSound(SoundEvents.ANVIL_LAND, 1.0F, 1.0F);
            }
            if (wakeTimer == 50) {
                this.playSound(SoundEvents.ANVIL_LAND, 1.0F, 1.0F);
            }
            if (wakeTimer == 90) {
                this.playSound(SoundEvents.ELDER_GUARDIAN_CURSE, 1.0F, 0.5F);
                if (level instanceof ServerWorld) {
                    ((ServerWorld) level).sendParticles(BPParticles.KINGS_ROAR.get(), getX(), getY() + 0.5D, getZ(), 1, 0, 0, 0, 0);
                }
            }
            if (wakeTimer >= 140) {
                this.setNoAi(false);
                this.setWaking(false);
            }
        }

        int areaint = 32;
        AxisAlignedBB aabb = new AxisAlignedBB(
                this.getX() - areaint, this.getY() - areaint, this.getZ() - areaint,
                this.getX() + areaint, this.getY() + areaint, this.getZ() + areaint
        );

        for (AlphemEntity alphem : level.getEntitiesOfClass(AlphemEntity.class, aabb)) {
            if (!(alphem.getOwner() instanceof AlphemKingEntity)) {
                alphem.kill();
            }
        }
        for (CavernFleignarEntity cavernFleignar : level.getEntitiesOfClass(CavernFleignarEntity.class, aabb)) {
            cavernFleignar.kill();
        }
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        float moveVector = (float) Math.toRadians(this.vecOfTarget + 90 + this.getRandom().nextFloat() * 150 - 75);
        Vector3d getVector = this.getDeltaMovement().add(1.75F * Math.cos(moveVector), 0, 1.75F * Math.sin(moveVector));
        if (pSource != DamageSource.OUT_OF_WORLD) {
            if (this.getWaking()) {
                this.playSound(SoundEvents.ANVIL_LAND, 1.0F, 1.5F);
                return false;
            }
            if (this.isBarriered()) {
                this.playSound(SoundEvents.GLASS_BREAK, 1.5F, 1.0F);
                this.setBarriered(false);
                return false;
            }
        }
        if (!this.getAttacking() && !this.getAttacking2() && !this.getSmashing()) {
            this.setDeltaMovement(getVector.x(), 0.5, getVector.z());
        }
        return super.hurt(pSource, pAmount);
    }

    public void aiStep() {
        super.aiStep();

        // Extras - For modded mobs that picks up mobs.
        float moveVector = (float) Math.toRadians(this.vecOfTarget + 90 + this.getRandom().nextFloat() * 150 - 75);
        Vector3d getVector = this.getDeltaMovement().add(2.5F * Math.cos(moveVector), 0, 2.5F * Math.sin(moveVector));
        if (this.isPassenger()) {
            ++pickupTimer;
            if (pickupTimer == 40) {
                this.heal(10.0F);
                this.playSound(SoundEvents.ANVIL_LAND, 1.5F, 0.75F);
                level.explode(this, getX(), getY(), getZ(), 3.0F, Explosion.Mode.NONE);
                this.stopRiding();
                this.setDeltaMovement(getVector.x(), 1.35, getVector.z());
                pickupTimer = 0;
            }
        } else {
            pickupTimer = 0;
        }

        World world = this.level;

        this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());

        if (!this.isBarriered()) {
            ++barrierTimer;
            if (barrierTimer == 150) {
                barrierTimer = 0;
                this.setBarriered(true);
            }

            if (getTarget() != null) {
                vecOfTarget = (float) (Math.atan2(getTarget().getZ() - getZ(), getTarget().getX() - getX()) * (180 / Math.PI) + 90);
            }
        }

        if (this.getTarget() != null) {
            summonShardTimer++;
            if (this.summonShardTimer == 40) {
                summonShard(BPConfig.IN_HELLMODE ? 5 + getRandom().nextInt(6) : 4 + getRandom().nextInt(4));
                this.summonShardTimer = 0;
            }
        }

        this.setBerserked(this.getHealth() <= this.getMaxHealth() / 2);

        if (this.isBerserked()) {
            double d0 = this.random.nextGaussian() * 0.02D;
            double d1 = this.random.nextGaussian() * 0.02D;
            double d2 = this.random.nextGaussian() * 0.02D;
            world.addParticle(ParticleTypes.SOUL_FIRE_FLAME, this.getRandomX(1.0D), this.getRandomY(), this.getRandomZ(1.0D), d0, d1, d2);
            world.addParticle(ParticleTypes.POOF, this.getRandomX(1.0D), this.getRandomY(), this.getRandomZ(1.0D), d0, d1, d2);

            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.35F * BPConfig.COMMON.mobMovementSpeedMultiplier.get());
            if (!BPConfig.IN_HELLMODE) {
                this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(37.0F * BPConfig.COMMON.mobMeeleeDamageMultiplier.get());
            } else {
                this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(45.0F * BPConfig.COMMON.mobMeeleeDamageMultiplier.get());
            }

            if (!(this.getHealth() <= 5)) {
                ++healthRegenTimer;
                if (healthRegenTimer == 20) {
                    this.heal( 2 + this.getRandom().nextInt(2));
                    healthRegenTimer = 0;
                }
            }

        } else {
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.20F * BPConfig.COMMON.mobMovementSpeedMultiplier.get());
            if (!BPConfig.IN_HELLMODE) {
                this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(30.0F * BPConfig.COMMON.mobMeeleeDamageMultiplier.get());
            } else {
                this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(35.0F * BPConfig.COMMON.mobMeeleeDamageMultiplier.get());
            }

            if (!(this.getHealth() <= 5)) {
                ++healthRegenTimer;
                if (healthRegenTimer == 60) {
                    this.heal(2F);
                    healthRegenTimer = 0;
                }
            }
        }

        if (this.hasEffect(Effects.MOVEMENT_SLOWDOWN)) {
            this.removeEffect(Effects.MOVEMENT_SLOWDOWN);
        }
        if (this.hasEffect(Effects.WEAKNESS)) {
            this.removeEffect(Effects.WEAKNESS);
        }
        if (this.hasEffect(Effects.POISON)) {
            this.removeEffect(Effects.POISON);
        }
        if (this.hasEffect(Effects.WITHER)) {
            this.removeEffect(Effects.WITHER);
        }

        if (!this.level.isClientSide) {
            this.inWall = this.checkWalls(this.getBoundingBox());
        }
    }

    private boolean checkWalls(AxisAlignedBB pArea) {
        if (this.isBerserked()) {
            int i = MathHelper.floor(pArea.minX);
            int j = MathHelper.floor(pArea.minY);
            int k = MathHelper.floor(pArea.minZ);
            int l = MathHelper.floor(pArea.maxX);
            int i1 = MathHelper.floor(pArea.maxY);
            int j1 = MathHelper.floor(pArea.maxZ);
            boolean flag = false;
            boolean flag1 = false;

            for (int k1 = i; k1 <= l; ++k1) {
                for (int l1 = j; l1 <= i1; ++l1) {
                    for (int i2 = k; i2 <= j1; ++i2) {
                        BlockPos blockpos = new BlockPos(k1, l1, i2);
                        BlockState blockstate = this.level.getBlockState(blockpos);
                        Block block = blockstate.getBlock();
                        if (!blockstate.isAir(this.level, blockpos) && blockstate.getMaterial() != Material.FIRE) {
                            if (net.minecraftforge.common.ForgeHooks.canEntityDestroy(this.level, blockpos, this) && !BlockTags.DRAGON_IMMUNE.contains(block)) {
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
                this.playSound(SoundEvents.WITHER_BREAK_BLOCK, 1.0F, 1.0F);
                this.level.levelEvent(2008, blockpos1, 0);
            }

            return flag;
        } else {
            return false;
        }
    }

    public void summonShard(int amount) {
        for (int i = 0; i < amount; i++) {

            double xPos = getRandom().nextBoolean() ? getX() + getRandom().nextInt(24) : getX() - getRandom().nextInt(24);
            double zPos = getRandom().nextBoolean() ? getZ() + getRandom().nextInt(24) : getZ() - getRandom().nextInt(24);

            AlphanumShardEntity shard = BPEntities.ALPHANUM_SHARD.get().create(this.level);
            if (this.getTarget() != null) {
                shard.setTarget(this.getTarget());
            }
            shard.setOwner(this);

            shard.setPos(xPos, getGroundPos(level, (int) xPos, (int) zPos).getY(), zPos);

            this.level.addFreshEntity(shard);
        }
    }

    public static BlockPos getGroundPos(IWorldReader pLevel, int pX, int pZ) {
        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable(pX, pLevel.getMaxBuildHeight(), pZ);
        do {
            blockpos$mutable.move(Direction.DOWN);
        } while(pLevel.getBlockState(blockpos$mutable).isAir() && blockpos$mutable.getY() > 0);

        BlockPos blockpos = blockpos$mutable.below();
        if (pLevel.getBlockState(blockpos).isPathfindable(pLevel, blockpos, PathType.LAND)) {
            return blockpos;
        }

        return blockpos$mutable.immutable().above();
    }

    @Override
    public void startSeenByPlayer(ServerPlayerEntity player) {
        super.startSeenByPlayer(player);
        this.bossInfo.addPlayer(player);
        ((IPlayerEntityMixin) player).setAlphanumCurse(true);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayerEntity player) {
        super.stopSeenByPlayer(player);
        this.bossInfo.removePlayer(player);
        ((IPlayerEntityMixin) player).setAlphanumCurse(false);
    }

    public float hurtScalable(LivingEntity ent, float val, float hmVal) {
        return (float) ((BPConfig.IN_HELLMODE ? hmVal : val) + (Math.sqrt(ent.getMaxHealth() * 1.25)));
    }

    public void createAttackParticleEffect(BlockPos pos, int flag) {
        for (int i = 0; i < 90; i++) {
            if (flag == 0) {
                level.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, pos.getX(), pos.getY() + 0.5, pos.getZ(), Math.sin(i) / 8, 0, Math.cos(i) / 8);
                level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, pos.getX(), pos.getY() + 0.75, pos.getZ(), Math.sin(i) / 4, 0, Math.cos(i) / 4);
            }
            if (flag == 1) {
                level.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, pos.getX(), pos.getY() + 0.5, pos.getZ(), Math.sin(i) / 4, 0, Math.cos(i) / 4);
                level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, pos.getX(), pos.getY() + 0.75, pos.getZ(), Math.sin(i) / 2, 0, Math.cos(i) / 2);
            }
        }
    }

    public void phaseAttack(World world) {
        double d0 = -MathHelper.sin(this.yRot * ((float)Math.PI / 180F)) * 4;
        double d1 = MathHelper.cos(this.yRot * ((float)Math.PI / 180F)) * 4;
        BlockPos areaPos = new BlockPos(getX() + d0, getY(), getZ() + d1);

        for (LivingEntity areaEnt : world.getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(5, 0.5, 5).move(areaPos))) {
            if (areaEnt != this) {
                areaEnt.hurt(DamageSource.mobAttack(this), hurtScalable(areaEnt, 9, 11));
                areaEnt.hurt(DamageSource.explosion(this), hurtScalable(areaEnt, 9, 11));
                this.knockbackNoRes(areaEnt, 1.0F, MathHelper.sin(this.yRot * ((float) Math.PI / 180F)), -MathHelper.cos(this.yRot * ((float) Math.PI / 180F)));
                areaEnt.setDeltaMovement(this.getDeltaMovement().add(0, 1.5 - areaEnt.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE), 0));
                areaEnt.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 100, 2));
            }
        }
        this.playSound(SoundEvents.WITHER_BREAK_BLOCK, 1.0F, 1.0F);
        this.createAttackParticleEffect(areaPos, 0);
        BlockUtils.knockUpRandomNearbyBlocks(world, 0.3D, areaPos.below(), 3, 1, 3, false, true);
        if (world instanceof ServerWorld) {
            ((ServerWorld) world).sendParticles(ParticleTypes.POOF,areaPos.getX(), areaPos.getY(), areaPos.getZ(),
                    25, 0.45, 0.45, 0.45, 0.01);
        }
        EntityUtils.shakeNearbyPlayersScreen(this, 32, 5);
    }

    public void phaseAttack2(World world) {
        double d0 = -MathHelper.sin(this.yRot * ((float)Math.PI / 180F)) * 3.25;
        double d1 = MathHelper.cos(this.yRot * ((float)Math.PI / 180F)) * 3.25;
        BlockPos areaPos = new BlockPos(getX() + d0, getY(), getZ() + d1);
        for (LivingEntity areaEnt : world.getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(10, 0.5, 10).move(areaPos))) {
            if (areaEnt != this) {
                areaEnt.hurt(DamageSource.mobAttack(this), hurtScalable(areaEnt, 7, 10));
                areaEnt.hurt(DamageSource.explosion(this), hurtScalable(areaEnt, 7, 10));
                this.knockbackNoRes(areaEnt, 2.5F, MathHelper.sin(this.yRot * ((float) Math.PI / 180F)), -MathHelper.cos(this.yRot * ((float) Math.PI / 180F)));
                areaEnt.setDeltaMovement(this.getDeltaMovement().add(0, 0.75, 0));
                areaEnt.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 100, 2));
            }
        }
        if (world instanceof ServerWorld) {
            ((ServerWorld) world).sendParticles(ParticleTypes.CLOUD, areaPos.getX(), areaPos.getY(), areaPos.getZ(), 55, 0.75, 0.75, 0.75, 0.01);
            ((ServerWorld) world).sendParticles(ParticleTypes.SWEEP_ATTACK, areaPos.getX(), areaPos.getY(), areaPos.getZ(), 55, 1.25, 1.25, 1.25, 0.01);
        }
    }

    public void phaseSmashing(World world) {

        float f1 = (float) this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
        double d0 = -MathHelper.sin(this.yRot * ((float)Math.PI / 180F)) * 2.5;
        double d1 = MathHelper.cos(this.yRot * ((float)Math.PI / 180F)) * 2.5;
        BlockPos areaPos = new BlockPos(getX() + d0, getY(), getZ() + d1);

        for (LivingEntity areaEnt : world.getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(15, 0.5, 15).move(areaPos))) {
            if (areaEnt != this) {
                areaEnt.hurt(DamageSource.mobAttack(this), hurtScalable(areaEnt, 13, 17));
                this.knockbackNoRes(areaEnt, f1 * 0.5F, MathHelper.sin(this.yRot * ((float) Math.PI / 180F)), -MathHelper.cos(this.yRot * ((float) Math.PI / 180F)));
                areaEnt.setDeltaMovement(this.getDeltaMovement().add(0, 1.5, 0));
                areaEnt.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 100, 2));
            }
        }

        world.explode(this, areaPos.getX(), areaPos.getY(), areaPos.getZ(), 3.0F, Explosion.Mode.NONE);
        this.playSound(SoundEvents.WITHER_BREAK_BLOCK, 1.0F, 1.0F);
        this.createAttackParticleEffect(areaPos, 1);
        BlockUtils.knockUpRandomNearbyBlocks(world, 0.5D, areaPos.below(), 6, 2, 6, false, true);

        if (world instanceof ServerWorld) {
            ((ServerWorld) world).sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, areaPos.getX(), areaPos.getY(), areaPos.getZ(),
                    75, 0.4, 1.5, 0.4, 0.001);
        }

        EntityUtils.shakeNearbyPlayersScreen(this, 32, 10);
    }

    public void knockbackNoRes(LivingEntity target, float pStrength, double pRatioX, double pRatioZ) {
        net.minecraftforge.event.entity.living.LivingKnockBackEvent event = net.minecraftforge.common.ForgeHooks.onLivingKnockBack(this, pStrength, pRatioX, pRatioZ);
        if(event.isCanceled()) return;
        pStrength = event.getStrength();
        pRatioX = event.getRatioX();
        pRatioZ = event.getRatioZ();
        if (!(pStrength <= 0.0F)) {
            target.hasImpulse = true;
            Vector3d vector3d = target.getDeltaMovement();
            Vector3d vector3d1 = (new Vector3d(pRatioX, 0.0D, pRatioZ)).normalize().scale(pStrength);
            target.setDeltaMovement(vector3d.x / 2.0D - vector3d1.x, target.isOnGround() ? Math.min(0.4D, vector3d.y / 2.0D + (double)pStrength) : vector3d.y, vector3d.z / 2.0D - vector3d1.z);
        }
    }

    @Override
    protected void tickDeath() {

        ++this.deathTime;

        int areaint = 256;
        AxisAlignedBB aabb = new AxisAlignedBB(
                this.getX() - areaint, this.getY() - areaint, this.getZ() - areaint,
                this.getX() + areaint, this.getY() + areaint, this.getZ() + areaint
        );

        if (this.dyingExpTimer++ == 6) {
            this.dropExperience(MathHelper.floor((float)12000 * 0.08F));
            dyingExpTimer = 0;
        }

        if (this.deathTime == 50) {
            this.level.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ANVIL_PLACE, SoundCategory.HOSTILE, 1.0F, 1.0F);
        }

        if (this.deathTime == 100) {

            if (!explodedOnDeath) {
                this.level.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.WITHER_BREAK_BLOCK, SoundCategory.HOSTILE, 1.0F, 1.0F);
                this.level.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.GENERIC_EXPLODE, SoundCategory.HOSTILE, 1.0F, 1.0F);

                explodedOnDeath = true;
            }
            for (BPEffectEntity effect : level.getLoadedEntitiesOfClass(BPEffectEntity.class, aabb)) {
                if (effect.getEffectType() == BPEffectTypes.ALPHANUM_FIRE && effect.getOwner() == this) {
                    effect.remove();
                }
            }
            this.remove();

            for (int i = 0; i < 100; ++i) {
                double d0 = this.random.nextGaussian() * 0.02D;
                double d1 = this.random.nextGaussian() * 0.02D;
                double d2 = this.random.nextGaussian() * 0.02D;

                this.level.addParticle(ParticleTypes.EXPLOSION, this.getRandomX(1.0D), this.getRandomY(), this.getRandomZ(1.0D), d0, d1, d2);
                this.level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, this.getRandomX(1.0D), this.getRandomY(), this.getRandomZ(1.0D), d0, d1, d2);
                this.level.addParticle(ParticleTypes.POOF, this.getRandomX(1.0D), this.getRandomY(), this.getRandomZ(1.0D), d0, d1, d2);
            }
        }
    }

    private void dropExperience(int pAmount) {
        while(pAmount > 0) {
            int i = ExperienceOrbEntity.getExperienceValue(pAmount);
            pAmount -= i;
            this.level.addFreshEntity(new ExperienceOrbEntity(this.level, this.getX(), this.getY(), this.getZ(), i));
        }
    }

    @Override
    public void swing(Hand pHand, boolean pUpdateSelf) {
        super.swing(pHand, pUpdateSelf);
        World world = level;

        if (this.attackPhase == 0) {
            this.phaseAttack(world);
        }

        if (this.attackPhase == 1) {
            this.phaseAttack2(world);
        }

        if (this.attackPhase == 2) {
            this.phaseSmashing( world);
        }
    }

    public boolean doHurtTarget(Entity entity) {
        if (entity instanceof LivingEntity) {
            float healValue = isBerserked() ? hurtScalable(((LivingEntity) entity), 17, 20) : hurtScalable(((LivingEntity) entity), 10, 14);
            this.heal(healValue);
        }
        return super.doHurtTarget(entity);
    }

    @Override
    public SoundEvent getAmbientSound() {
        return BPSoundEvents.FROSTBITE_GOLEM_IDLE.get();
    }

    @Override
    public SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.IRON_GOLEM_HURT;
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundEvents.IRON_GOLEM_DEATH;
    }

    @Override
    public void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.IRON_GOLEM_STEP, 0.15f, 1);
    }

    public SoundEvent getRoarSound() {
        return BPSoundEvents.ALPHEM_KING_ROAR.get();
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean ignoreExplosion() {
        return true;
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    public void checkDespawn() {
    }

    @Override
    public boolean causeFallDamage(float p_225503_1_, float p_225503_2_) {
        return false;
    }

    protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(WAKING, true);
        this.entityData.define(ATTACKING2, false);
        this.entityData.define(SMASHING, false);
        this.entityData.define(ROARING, false);
        this.entityData.define(CHARGING, false);
        this.entityData.define(JUMPING, false);
        this.entityData.define(RAMMING, false);
        this.entityData.define(BARRIERED, false);
        this.entityData.define(BERSERKED, false);
        this.entityData.define(PURSUIT, false);
    }

    public boolean getWaking() {
        return this.entityData.get(WAKING);
    }

    public void setWaking(boolean sleeping) {
        this.entityData.set(WAKING, sleeping);
    }

    public boolean getAttacking2() {
        return this.entityData.get(ATTACKING2);
    }

    public void setAttacking2(boolean attacking2) {
        this.entityData.set(ATTACKING2, attacking2);
    }

    public boolean getSmashing() {
        return this.entityData.get(SMASHING);
    }

    public void setSmashing(boolean smashing) {
        this.entityData.set(SMASHING, smashing);
    }

    public boolean getRoaring() {
        return this.entityData.get(ROARING);
    }

    public void setRoaring(boolean smashing) {
        this.entityData.set(ROARING, smashing);
    }

    public boolean isCharging() {
        return this.entityData.get(CHARGING);
    }

    public void setCharging(boolean charging) {
        this.entityData.set(CHARGING, charging);
    }

    public boolean isJumping() {
        return this.entityData.get(JUMPING);
    }

    public void setJumping(boolean jumping) {
        this.entityData.set(JUMPING, jumping);
    }

    public boolean isRamming() {
        return this.entityData.get(RAMMING);
    }

    public void setRamming(boolean ramming) {
        this.entityData.set(RAMMING, ramming);
    }

    public boolean isBarriered() {
        return this.entityData.get(BARRIERED);
    }

    public void setBarriered(boolean barriered) {
        this.entityData.set(BARRIERED, barriered);
    }

    public boolean isBerserked() {
        return this.entityData.get(BERSERKED);
    }

    public void setBerserked(boolean berserked) {
        this.entityData.set(BERSERKED, berserked);
    }

    public boolean isPursuit() {
        return this.entityData.get(PURSUIT);
    }

    public void setPursuit(boolean pursuit) {
        this.entityData.set(PURSUIT, pursuit);
    }
    @Override
    public int getMaxDamageCap() {
        return BPConfig.COMMON.alphemKingMobCap.get();
    }

    public static class KingPartEntity<T extends MobEntity> extends BPPartEntity<T> {

        public KingPartEntity(T parent, String name, float width, float height) {
            super(parent, name, width, height);
        }

        @Override
        public boolean hurt(DamageSource pSource, float pAmount) {
            return super.hurt(pSource, pAmount * 2.0F);
        }
    }
}