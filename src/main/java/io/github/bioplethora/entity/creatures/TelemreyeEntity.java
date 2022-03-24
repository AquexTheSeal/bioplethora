package io.github.bioplethora.entity.creatures;

import io.github.bioplethora.BPConfig;
import io.github.bioplethora.entity.BPMonsterEntity;
import io.github.bioplethora.entity.IBioClassification;
import io.github.bioplethora.enums.BPEntityClasses;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.BodyController;
import net.minecraft.entity.ai.controller.LookController;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;


public class TelemreyeEntity extends BPMonsterEntity implements IAnimatable, IBioClassification, IFlyingAnimal {
    
    private final AnimationFactory factory = new AnimationFactory(this);
    private TelemreyeEntity.AttackPhase attackPhase = TelemreyeEntity.AttackPhase.CIRCLE;
    private Vector3d moveTargetPoint = Vector3d.ZERO;
    private BlockPos anchorPoint = BlockPos.ZERO;

    public TelemreyeEntity(EntityType<? extends BPMonsterEntity> type, World worldIn) {
        super(type, worldIn);
        this.noCulling = true;
        this.moveControl = new TelemreyeEntity.MoveHelperController(this);
        this.lookControl = new LookHelperController(this);
    }

    @Override
    public BPEntityClasses getBioplethoraClass() {
        return BPEntityClasses.HELLSENT;
    }

    protected BodyController createBodyControl() {
        return new TelemreyeEntity.BodyHelperController(this);
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createLivingAttributes()
                .add(Attributes.ARMOR, 12 * BPConfig.COMMON.mobArmorMultiplier.get())
                .add(Attributes.ATTACK_SPEED, 10)
                .add(Attributes.ATTACK_KNOCKBACK, 1.8D)
                .add(Attributes.ATTACK_DAMAGE, 15 * BPConfig.COMMON.mobMeeleeDamageMultiplier.get())
                .add(Attributes.MAX_HEALTH, 75 * BPConfig.COMMON.mobHealthMultiplier.get())
                .add(Attributes.MOVEMENT_SPEED, 1.2 * BPConfig.COMMON.mobMovementSpeedMultiplier.get())
                .add(Attributes.FOLLOW_RANGE, 64D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 24.0F));
        this.goalSelector.addGoal(1, new TelemreyeEntity.PickAttackGoal());
        this.goalSelector.addGoal(2, new TelemreyeEntity.SweepAttackGoal());
        this.goalSelector.addGoal(3, new TelemreyeEntity.OrbitPointGoal());
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, AlphemEntity.class, true));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {

        if (this.attackPhase == AttackPhase.SWOOP) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.telemreye.charging", true));
            return PlayState.CONTINUE;
        }
        if (event.isMoving() || this.attackPhase == AttackPhase.CIRCLE) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.telemreye.moving_air", true));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.telemreye.idle_air", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "telemreye_controller", 0, this::predicate));
    }

    public ILivingEntityData finalizeSpawn(IServerWorld pLevel, DifficultyInstance pDifficulty, SpawnReason pReason, @Nullable ILivingEntityData pSpawnData, @Nullable CompoundNBT pDataTag) {
        this.anchorPoint = this.blockPosition().above(5);
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    public boolean causeFallDamage(float distance, float damageMultiplier) {
        return false;
    }

    protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    enum AttackPhase {
        CIRCLE,
        SWOOP
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    public void tick() {
        super.tick();
        if (this.level.isClientSide) {
            float f = MathHelper.cos((float)(this.getId() * 3 + this.tickCount) * 0.13F + (float)Math.PI);
            float f1 = MathHelper.cos((float)(this.getId() * 3 + this.tickCount + 1) * 0.13F + (float)Math.PI);
            if (f > 0.0F && f1 <= 0.0F) {
                this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.ENDER_DRAGON_FLAP, this.getSoundSource(), 0.95F + this.random.nextFloat() * 0.05F, 0.95F + this.random.nextFloat() * 0.05F, false);
            }

            int i = 3;
            float f2 = MathHelper.cos(this.yRot * ((float)Math.PI / 180F)) * (1.3F + 0.21F * (float)i);
            float f3 = MathHelper.sin(this.yRot * ((float)Math.PI / 180F)) * (1.3F + 0.21F * (float)i);
            float f4 = (0.3F + f * 0.45F) * ((float)i * 0.2F + 1.0F);
            this.level.addParticle(ParticleTypes.CLOUD, this.getX() + (double)f2, this.getY() + (double)f4, this.getZ() + (double)f3, 0.0D, 0.0D, 0.0D);
            this.level.addParticle(ParticleTypes.CLOUD, this.getX() - (double)f2, this.getY() + (double)f4, this.getZ() - (double)f3, 0.0D, 0.0D, 0.0D);
        }
    }

    static class LookHelperController extends LookController {
        public LookHelperController(MobEntity entity) {
            super(entity);
        }
        
        public void tick() {
        }
    }

    class BodyHelperController extends BodyController {
        public BodyHelperController(MobEntity p_i49925_2_) {
            super(p_i49925_2_);
        }

        public void clientTick() {
            TelemreyeEntity.this.yHeadRot = TelemreyeEntity.this.yBodyRot;
            TelemreyeEntity.this.yBodyRot = TelemreyeEntity.this.yRot;
        }
    }

    abstract class MoveGoal extends Goal {
        public MoveGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        protected boolean touchingTarget() {
            return TelemreyeEntity.this.moveTargetPoint.distanceToSqr(TelemreyeEntity.this.getX(), TelemreyeEntity.this.getY(), TelemreyeEntity.this.getZ()) < 4.0D;
        }
    }

    class MoveHelperController extends MovementController {
        private float speed = 0.1F;

        public MoveHelperController(MobEntity p_i48801_2_) {
            super(p_i48801_2_);
        }

        public void tick() {
            if (TelemreyeEntity.this.horizontalCollision) {
                TelemreyeEntity.this.yRot += 180.0F;
                this.speed = 0.1F;
            }

            float f = (float)(TelemreyeEntity.this.moveTargetPoint.x - TelemreyeEntity.this.getX());
            float f1 = (float)(TelemreyeEntity.this.moveTargetPoint.y - TelemreyeEntity.this.getY());
            float f2 = (float)(TelemreyeEntity.this.moveTargetPoint.z - TelemreyeEntity.this.getZ());
            double d0 = MathHelper.sqrt(f * f + f2 * f2);
            double d1 = 1.0D - (double)MathHelper.abs(f1 * 0.7F) / d0;
            f = (float)((double)f * d1);
            f2 = (float)((double)f2 * d1);
            d0 = MathHelper.sqrt(f * f + f2 * f2);
            double d2 = MathHelper.sqrt(f * f + f2 * f2 + f1 * f1);
            float f3 = TelemreyeEntity.this.yRot;
            float f4 = (float)MathHelper.atan2(f2, f);
            float f5 = MathHelper.wrapDegrees(TelemreyeEntity.this.yRot + 90.0F);
            float f6 = MathHelper.wrapDegrees(f4 * (180F / (float)Math.PI));
            TelemreyeEntity.this.yRot = MathHelper.approachDegrees(f5, f6, 4.0F) - 90.0F;
            TelemreyeEntity.this.yBodyRot = TelemreyeEntity.this.yRot;
            if (MathHelper.degreesDifferenceAbs(f3, TelemreyeEntity.this.yRot) < 3.0F) {
                this.speed = MathHelper.approach(this.speed, 1.8F, 0.005F * (1.8F / this.speed));
            } else {
                this.speed = MathHelper.approach(this.speed, 0.2F, 0.025F);
            }

            float f7 = (float)(-(MathHelper.atan2(-f1, d0) * (double)(180F / (float)Math.PI)));
            TelemreyeEntity.this.xRot = f7;
            float f8 = TelemreyeEntity.this.yRot + 90.0F;
            double d3 = (double)(this.speed * MathHelper.cos(f8 * ((float)Math.PI / 180F))) * Math.abs((double)f / d2);
            double d4 = (double)(this.speed * MathHelper.sin(f8 * ((float)Math.PI / 180F))) * Math.abs((double)f2 / d2);
            double d5 = (double)(this.speed * MathHelper.sin(f7 * ((float)Math.PI / 180F))) * Math.abs((double)f1 / d2);
            Vector3d vector3d = TelemreyeEntity.this.getDeltaMovement();
            TelemreyeEntity.this.setDeltaMovement(vector3d.add((new Vector3d(d3, d5, d4)).subtract(vector3d).scale(0.2D)));
        }
    }

    class OrbitPointGoal extends TelemreyeEntity.MoveGoal {
        private float angle;
        private float distance;
        private float height;
        private float clockwise;

        private OrbitPointGoal() {
        }
        
        public boolean canUse() {
            return TelemreyeEntity.this.getTarget() == null || TelemreyeEntity.this.attackPhase == TelemreyeEntity.AttackPhase.CIRCLE;
        }
        
        public void start() {
            this.distance = 5.0F + TelemreyeEntity.this.random.nextFloat() * 10.0F;
            this.height = -4.0F + TelemreyeEntity.this.random.nextFloat() * 9.0F;
            this.clockwise = TelemreyeEntity.this.random.nextBoolean() ? 1.0F : -1.0F;
            this.selectNext();
        }
        
        public void tick() {
            if (TelemreyeEntity.this.random.nextInt(350) == 0) {
                this.height = -4.0F + TelemreyeEntity.this.random.nextFloat() * 9.0F;
            }

            if (TelemreyeEntity.this.random.nextInt(250) == 0) {
                ++this.distance;
                if (this.distance > 15.0F) {
                    this.distance = 5.0F;
                    this.clockwise = -this.clockwise;
                }
            }

            if (TelemreyeEntity.this.random.nextInt(450) == 0) {
                this.angle = TelemreyeEntity.this.random.nextFloat() * 2.0F * (float)Math.PI;
                this.selectNext();
            }

            if (this.touchingTarget()) {
                this.selectNext();
            }

            if (TelemreyeEntity.this.moveTargetPoint.y < TelemreyeEntity.this.getY() && !TelemreyeEntity.this.level.isEmptyBlock(TelemreyeEntity.this.blockPosition().below(1))) {
                this.height = Math.max(1.0F, this.height);
                this.selectNext();
            }

            if (TelemreyeEntity.this.moveTargetPoint.y > TelemreyeEntity.this.getY() && !TelemreyeEntity.this.level.isEmptyBlock(TelemreyeEntity.this.blockPosition().above(1))) {
                this.height = Math.min(-1.0F, this.height);
                this.selectNext();
            }

        }

        private void selectNext() {
            if (BlockPos.ZERO.equals(TelemreyeEntity.this.anchorPoint)) {
                TelemreyeEntity.this.anchorPoint = TelemreyeEntity.this.blockPosition();
            }

            this.angle += this.clockwise * 15.0F * ((float)Math.PI / 180F);
            TelemreyeEntity.this.moveTargetPoint = Vector3d.atLowerCornerOf(TelemreyeEntity.this.anchorPoint).add(this.distance * MathHelper.cos(this.angle), -4.0F + this.height, this.distance * MathHelper.sin(this.angle));
        }
    }

    class PickAttackGoal extends Goal {
        private int nextSweepTick;

        private PickAttackGoal() {
        }

        public boolean canUse() {
            LivingEntity livingentity = TelemreyeEntity.this.getTarget();
            return livingentity != null && TelemreyeEntity.this.canAttack(TelemreyeEntity.this.getTarget(), EntityPredicate.DEFAULT);
        }

        public void start() {
            this.nextSweepTick = 10;
            TelemreyeEntity.this.attackPhase = TelemreyeEntity.AttackPhase.CIRCLE;
            this.setAnchorAboveTarget();
        }

        public void stop() {
            TelemreyeEntity.this.anchorPoint = TelemreyeEntity.this.level.getHeightmapPos(Heightmap.Type.MOTION_BLOCKING, TelemreyeEntity.this.anchorPoint).above(10 + TelemreyeEntity.this.random.nextInt(20));
        }

        public void tick() {
            if (TelemreyeEntity.this.attackPhase == TelemreyeEntity.AttackPhase.CIRCLE) {
                --this.nextSweepTick;
                if (this.nextSweepTick <= 0) {
                    TelemreyeEntity.this.attackPhase = TelemreyeEntity.AttackPhase.SWOOP;
                    this.setAnchorAboveTarget();
                    this.nextSweepTick = (8 + TelemreyeEntity.this.random.nextInt(4)) * 20;
                    TelemreyeEntity.this.playSound(SoundEvents.PHANTOM_SWOOP, 10.0F, 0.95F + TelemreyeEntity.this.random.nextFloat() * 0.1F);
                }
            }

        }

        private void setAnchorAboveTarget() {
            TelemreyeEntity.this.anchorPoint = TelemreyeEntity.this.getTarget().blockPosition().above(20 + TelemreyeEntity.this.random.nextInt(20));
            if (TelemreyeEntity.this.anchorPoint.getY() < TelemreyeEntity.this.level.getSeaLevel()) {
                TelemreyeEntity.this.anchorPoint = new BlockPos(TelemreyeEntity.this.anchorPoint.getX(), TelemreyeEntity.this.level.getSeaLevel() + 1, TelemreyeEntity.this.anchorPoint.getZ());
            }

        }
    }

    class SweepAttackGoal extends TelemreyeEntity.MoveGoal {
        private SweepAttackGoal() {
        }

        public boolean canUse() {
            return TelemreyeEntity.this.getTarget() != null && TelemreyeEntity.this.attackPhase == TelemreyeEntity.AttackPhase.SWOOP;
        }
        
        public boolean canContinueToUse() {
            LivingEntity livingentity = TelemreyeEntity.this.getTarget();
            if (livingentity == null) {
                return false;
            } else if (!livingentity.isAlive()) {
                return false;
            } else if (!(livingentity instanceof PlayerEntity) || !livingentity.isSpectator() && !((PlayerEntity)livingentity).isCreative()) {
                if (!this.canUse()) {
                    return false;
                } else {
                    if (TelemreyeEntity.this.tickCount % 20 == 0) {
                        List<CatEntity> list = TelemreyeEntity.this.level.getEntitiesOfClass(CatEntity.class, TelemreyeEntity.this.getBoundingBox().inflate(16.0D), EntityPredicates.ENTITY_STILL_ALIVE);
                        if (!list.isEmpty()) {
                            for(CatEntity catentity : list) {
                                catentity.hiss();
                            }

                            return false;
                        }
                    }

                    return true;
                }
            } else {
                return false;
            }
        }

        public void start() {
        }

        public void stop() {
            TelemreyeEntity.this.setTarget(null);
            TelemreyeEntity.this.attackPhase = TelemreyeEntity.AttackPhase.CIRCLE;
        }
        
        public void tick() {
            LivingEntity livingentity = TelemreyeEntity.this.getTarget();
            TelemreyeEntity.this.moveTargetPoint = new Vector3d(livingentity.getX(), livingentity.getY(0.5D), livingentity.getZ());
            if (TelemreyeEntity.this.getBoundingBox().inflate(0.2F).intersects(livingentity.getBoundingBox())) {
                TelemreyeEntity.this.doHurtTarget(livingentity);
                TelemreyeEntity.this.attackPhase = TelemreyeEntity.AttackPhase.CIRCLE;
                if (!TelemreyeEntity.this.isSilent()) {
                    TelemreyeEntity.this.level.levelEvent(1039, TelemreyeEntity.this.blockPosition(), 0);
                }
            } else if (TelemreyeEntity.this.horizontalCollision || TelemreyeEntity.this.hurtTime > 0) {
                TelemreyeEntity.this.attackPhase = TelemreyeEntity.AttackPhase.CIRCLE;
            }

        }
    }
}
