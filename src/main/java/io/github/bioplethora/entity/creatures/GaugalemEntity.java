package io.github.bioplethora.entity.creatures;

import io.github.bioplethora.config.BPConfig;
import io.github.bioplethora.entity.FloatingMonsterEntity;
import io.github.bioplethora.entity.IBioClassification;
import io.github.bioplethora.entity.ai.gecko.GeckoMeleeGoal;
import io.github.bioplethora.enums.BPEntityClasses;
import io.github.bioplethora.item.weapons.StellarScytheItem;
import io.github.bioplethora.registry.BPItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
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
import java.util.Random;

public class GaugalemEntity extends FloatingMonsterEntity implements IAnimatable, IFlyingAnimal, IBioClassification {
    private final AnimationFactory factory = new AnimationFactory(this);

    public GaugalemEntity(EntityType<? extends MonsterEntity> type, World world) {
        super(type, world);
        this.setItemSlot(EquipmentSlotType.MAINHAND, new ItemStack(BPItems.STELLAR_SCYTHE.get()));
        this.moveControl = new GaugalemEntity.MoveHelperController(this);
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createLivingAttributes()
                .add(Attributes.ARMOR, 4 * BPConfig.COMMON.mobArmorMultiplier.get())
                .add(Attributes.ATTACK_SPEED, 1.5)
                .add(Attributes.ATTACK_DAMAGE, 7 * BPConfig.COMMON.mobMeeleeDamageMultiplier.get())
                .add(Attributes.ATTACK_KNOCKBACK, 0.5D)
                .add(Attributes.MAX_HEALTH, 40 * BPConfig.COMMON.mobHealthMultiplier.get())
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.1)
                .add(Attributes.MOVEMENT_SPEED, 0.5 * BPConfig.COMMON.mobMovementSpeedMultiplier.get())
                .add(Attributes.FOLLOW_RANGE, 32.0D)
                .add(Attributes.FLYING_SPEED, 1.5F);
    }

    @Override
    public BPEntityClasses getBioplethoraClass() {
        return BPEntityClasses.DANGERUM;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 24.0F));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomFlyingGoal(this, 1.2));
        this.goalSelector.addGoal(3, new GaugalemEntity.ChargeAttackGoal());
        this.goalSelector.addGoal(4, new GaugalemEntity.MoveRandomGoal());
        this.goalSelector.addGoal(1, new GeckoMeleeGoal<>(this, 40, 0.5, 0.6));
        this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(5, new SwimGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        /*this.targetSelector.addGoal(2, new GaugalemTeleportToTargetGoal(this, null));*/
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, GaugalemEntity.class).setAlertOthers());
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "gaugalem_controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    private <E extends IAnimatable>PlayState predicate(AnimationEvent<E> event) {
        if(this.getAttacking()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.gaugalem.attack", true));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.gaugalem.idle", true));
        return PlayState.CONTINUE;
    }

    public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        if (worldIn instanceof ServerWorld && BPConfig.COMMON.hellMode.get()) {
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(10 * BPConfig.COMMON.mobMeeleeDamageMultiplier.get());
            this.getAttribute(Attributes.ARMOR).setBaseValue(6 * BPConfig.COMMON.mobArmorMultiplier.get());
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(33 * BPConfig.COMMON.mobHealthMultiplier.get());
            this.setHealth(33 * BPConfig.COMMON.mobHealthMultiplier.get());
        }
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    public static boolean checkGaugalemSpawnRules(EntityType<GaugalemEntity> gaugalemEntityEntityType, IWorld pLevel, SpawnReason pSpawnType, BlockPos pPos, Random pRandom) {
        return pPos.getY() > 40 && pRandom.nextInt(5) == 1;
    }

    public boolean doHurtTarget(Entity entity) {
        boolean flag = super.doHurtTarget(entity);
        World world = entity.level;
        if(flag && entity instanceof LivingEntity) {
            ((LivingEntity) entity).addEffect(new EffectInstance(Effects.WITHER, 100, 2));
            this.addEffect(new EffectInstance(Effects.INVISIBILITY, 100, 2));
            if (this.level instanceof ServerWorld) {
                ((ServerWorld) this.level).sendParticles(ParticleTypes.LARGE_SMOKE, this.getX(), this.getY(), this.getZ(), 20, 0.4, 0.4, 0.4, 0.1);
            }
        }

        if (!this.level.isClientSide() /*&& !(damageSource.getEntity() instanceof LivingEntity) */) {
            this.teleport();
        }

        if (this.getMainHandItem().getItem() instanceof SwordItem) {
            world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()), SoundEvents.PLAYER_ATTACK_SWEEP, SoundCategory.PLAYERS, 1, 1);
            if(!world.isClientSide) {
                world.addParticle(ParticleTypes.SWEEP_ATTACK, entity.getX(), entity.getY() + 2, entity.getZ(), 1, 1, 0.1);
            }

            if (this.getMainHandItem().getItem() instanceof StellarScytheItem) {

                double x = entity.getX(), y = entity.getY(), z = entity.getZ();

                if(world instanceof ServerWorld) {
                    for (Entity entityIterator : world.getEntitiesOfClass(Entity.class, new AxisAlignedBB(x - (10 / 2d), y, z - (10 / 2d), x + (10 / 2d), y + (10 / 2d), z + (10 / 2d)), null)) {
                        if (entityIterator instanceof LivingEntity && entityIterator != this) {
                            if (entityIterator != entity) {
                                entityIterator.hurt(DamageSource.mobAttack(this), ((StellarScytheItem) this.getMainHandItem().getItem()).getDamage() * 0.8F);
                            }
                        }
                    }
                }
            }
        }

        return flag;
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (this.level instanceof ServerWorld) {
            if (!this.hasEffect(Effects.INVISIBILITY)) {
                ((ServerWorld) this.level).sendParticles(ParticleTypes.LARGE_SMOKE, this.getX(), this.getY(), this.getZ(), 5, 0.4, 0.4, 0.4, 0.1);
            }
        }
    }

    public int getMaxSpawnClusterSize() {
        return 1;
    }

    public CreatureAttribute getMobType() {
        return CreatureAttribute.UNDEAD;
    }

    public boolean hurt(DamageSource damageSource, float p_70097_2_) {
        if (this.isInvulnerableTo(damageSource)) {
            return false;
        } else {
            this.addEffect(new EffectInstance(Effects.INVISIBILITY, 100, 2));
            if (this.level instanceof ServerWorld) {
                ((ServerWorld) this.level).sendParticles(ParticleTypes.LARGE_SMOKE, this.getX(), this.getY(), this.getZ(), 20, 0.4, 0.4, 0.4, 0.1);
            }

            if (!this.level.isClientSide() /*&& !(damageSource.getEntity() instanceof LivingEntity) */) {
                this.teleport();
            }

            return super.hurt(damageSource, p_70097_2_);
        }
    }

    public boolean teleport() {
        if (!this.level.isClientSide() && this.isAlive()) {
            double d0 = this.getX() + (this.random.nextDouble() - 0.5D) * 64.0D;
            double d1 = this.getY() + (double)(this.random.nextInt(64) - 32);
            double d2 = this.getZ() + (this.random.nextDouble() - 0.5D) * 64.0D;
            return this.teleport(d0, d1, d2);
        } else {
            return false;
        }
    }

    public boolean teleport(double p_70825_1_, double p_70825_3_, double p_70825_5_) {
        BlockPos.Mutable mutable = new BlockPos.Mutable(p_70825_1_, p_70825_3_, p_70825_5_);

        while(mutable.getY() > 0 && !this.level.getBlockState(mutable).getMaterial().blocksMotion()) {
            mutable.move(Direction.DOWN);
        }

        BlockState blockstate = this.level.getBlockState(mutable);
        boolean flag = blockstate.getMaterial().blocksMotion();
        boolean flag1 = blockstate.getFluidState().is(FluidTags.WATER);
        if (flag && !flag1) {
            net.minecraftforge.event.entity.living.EntityTeleportEvent.EnderEntity event = net.minecraftforge.event.ForgeEventFactory.onEnderTeleport(this, p_70825_1_, p_70825_3_, p_70825_5_);
            if (event.isCanceled()) return false;
            boolean flag2 = this.randomTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ(), true);
            if (flag2 && !this.isSilent()) {
                this.level.playSound(null, this.xo, this.yo, this.zo, SoundEvents.ENDERMAN_TELEPORT, this.getSoundSource(), 1.0F, 1.0F);
                this.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F);
            }

            return flag2;
        } else {
            return false;
        }
    }

    public boolean teleportTowards(Entity p_70816_1_) {
        Vector3d vector3d = new Vector3d(this.getX() - p_70816_1_.getX(), this.getY(0.5D) - p_70816_1_.getEyeY(), this.getZ() - p_70816_1_.getZ());
        vector3d = vector3d.normalize();
        double d0 = 16.0D;
        double d1 = this.getX() + (this.random.nextDouble() - 0.5D) * 8.0D - vector3d.x * 16.0D;
        double d2 = this.getY() + (double)(this.random.nextInt(16) - 8) - vector3d.y * 16.0D;
        double d3 = this.getZ() + (this.random.nextDouble() - 0.5D) * 8.0D - vector3d.z * 16.0D;
        return this.teleport(d1, d2, d3);
    }
}
