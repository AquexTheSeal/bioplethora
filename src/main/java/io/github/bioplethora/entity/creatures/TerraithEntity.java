package io.github.bioplethora.entity.creatures;

import io.github.bioplethora.entity.BPMonsterEntity;
import io.github.bioplethora.entity.FloatingMonsterEntity;
import io.github.bioplethora.entity.IBioClassification;
import io.github.bioplethora.entity.ai.monster.BPMonsterMeleeGoal;
import io.github.bioplethora.entity.ai.monster.BPMonsterMoveToTargetGoal;
import io.github.bioplethora.enums.BPEntityClasses;
import me.shedaniel.architectury.event.events.EntityEvent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.VexEntity;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.EnumSet;

public class TerraithEntity extends FloatingMonsterEntity implements IAnimatable, IFlyingAnimal, IBioClassification {
    protected static final DataParameter<Boolean> CHARGING = EntityDataManager.defineId(TerraithEntity.class, DataSerializers.BOOLEAN);

    public boolean getCharging() {
        return this.entityData.get(CHARGING);
    }

    public void setCharging(boolean charging) {
        this.entityData.set(CHARGING, charging);
    }

    private final AnimationFactory factory = new AnimationFactory(this);

    public TerraithEntity(EntityType<? extends MonsterEntity> type, World world) {
        super(type, world);
        this.moveControl = new TerraithEntity.MoveHelperController(this);
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createLivingAttributes()
                .add(Attributes.ATTACK_DAMAGE, 3)
                .add(Attributes.ATTACK_SPEED, 10.5)
                .add(Attributes.MAX_HEALTH, 20)
                .add(Attributes.ATTACK_KNOCKBACK, 0.4)
                .add(Attributes.FLYING_SPEED, 5)
                .add(Attributes.FOLLOW_RANGE, 16);
    }

    @Override
    public BPEntityClasses getBioplethoraClass() {
        return BPEntityClasses.DANGERUM;
    }

    @Override
    public void tick() {
        this.noPhysics = true;
        super.tick();
        this.noPhysics = false;
        this.setNoGravity(true);
    }

    @Override
    public void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(4, new TerraithEntity.MoveRandomGoal());
        this.goalSelector.addGoal(1, new BPMonsterMeleeGoal(this, 14, 0.5, 0.7));
        this.goalSelector.addGoal(1, new BPMonsterMoveToTargetGoal(this, 1.0F, 10));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, VillagerEntity.class, true));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, TerraithEntity.class)).setAlertOthers());
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "terraith_controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    private <E extends IAnimatable>PlayState predicate(AnimationEvent<E> event) {
        if(this.getAttacking()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.terraith.attack", true));
            return PlayState.CONTINUE;
        }

        if(event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.terraith.move", true));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.terraith.idle"));
        return PlayState.CONTINUE;
    }

    class MoveHelperController extends MovementController {
        public MoveHelperController(TerraithEntity terraith) {
            super(terraith);
        }

        public void tick() {
            if (this.operation == MovementController.Action.MOVE_TO) {
                Vector3d vector3d = new Vector3d(this.wantedX - TerraithEntity.this.getX(), this.wantedY - TerraithEntity.this.getY(), this.wantedZ - TerraithEntity.this.getZ());
                double d0 = vector3d.length();
                if (d0 < TerraithEntity.this.getBoundingBox().getSize()) {
                    this.operation = MovementController.Action.WAIT;
                    TerraithEntity.this.setDeltaMovement(TerraithEntity.this.getDeltaMovement().scale(0.5D));
                } else {
                    TerraithEntity.this.setDeltaMovement(TerraithEntity.this.getDeltaMovement().add(vector3d.scale(this.speedModifier * 0.05D / d0)));
                    if (TerraithEntity.this.getTarget() == null) {
                        Vector3d vector3d1 = TerraithEntity.this.getDeltaMovement();
                        TerraithEntity.this.yRot = -((float) MathHelper.atan2(vector3d1.x, vector3d1.z)) * (180F / (float)Math.PI);
                        TerraithEntity.this.yBodyRot = TerraithEntity.this.yRot;
                    } else {
                        double targetX = TerraithEntity.this.getTarget().getX() - TerraithEntity.this.getX();
                        double targetZ = TerraithEntity.this.getTarget().getZ() - TerraithEntity.this.getZ();
                        TerraithEntity.this.yRot = -((float)MathHelper.atan2(targetX, targetZ)) * (180F / (float)Math.PI);
                        TerraithEntity.this.yBodyRot = TerraithEntity.this.yRot;
                    }
                }
            }
        }
    }

    class MoveRandomGoal extends Goal {
        public MoveRandomGoal() {
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        public boolean canUse() {
            return !TerraithEntity.this.getMoveControl().hasWanted() && TerraithEntity.this.random.nextInt(7) == 0;
        }

        public boolean canContinueToUse() {
            return false;
        }

        public void tick() {
            BlockPos blockpos = TerraithEntity.this.blockPosition();

            for(int i = 0; i < 3; ++i) {
                BlockPos blockpos1 = blockpos.offset(TerraithEntity.this.random.nextInt(15) - 7, TerraithEntity.this.random.nextInt(11) - 5, TerraithEntity.this.random.nextInt(15) - 7);
                if (TerraithEntity.this.level.isEmptyBlock(blockpos1)) {
                    TerraithEntity.this.moveControl.setWantedPosition((double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.5D, (double)blockpos1.getZ() + 0.5D, 0.25D);
                    if (TerraithEntity.this.getTarget() == null) {
                        TerraithEntity.this.getLookControl().setLookAt((double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.5D, (double)blockpos1.getZ() + 0.5D, 180.0F, 20.0F);
                    }
                    break;
                }
            }

        }
    }

    // TODO: Charge attack similar to vex
    /*
    class ChargeGoal extends Goal {
        public ChargeGoal() {
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        public boolean canUse() {
            if(TerraithEntity.this.getTarget() != null && !TerraithEntity.this.getMoveControl().hasWanted() && TerraithEntity.this.random.nextInt(3) == 0) {
                return TerraithEntity.this.distanceToSqr(TerraithEntity.this.getTarget()) > 3.0D;
            } else {
                return false;
            }
        }

        public boolean canContinueToUse() {
            return TerraithEntity.this.getMoveControl().hasWanted() && TerraithEntity.this
        }

        public void start() {
            LivingEntity target = TerraithEntity.this.getTarget();
            TerraithEntity.this.setCharging(true);

        }
    }
     */
}
