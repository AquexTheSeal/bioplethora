package io.github.bioplethora.entity.creatures;

import io.github.bioplethora.BioplethoraConfig;
import io.github.bioplethora.entity.AnimatableMonsterEntity;
import io.github.bioplethora.entity.IBioplethoraEntityClass;
import io.github.bioplethora.registry.BioplethoraEntityClasses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.BodyController;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;


public class TelemreyeEntity extends AnimatableMonsterEntity implements IAnimatable, IBioplethoraEntityClass, IFlyingAnimal {

    private final AnimationFactory factory = new AnimationFactory(this);

    public TelemreyeEntity(EntityType<? extends AnimatableMonsterEntity> type, World worldIn) {
        super(type, worldIn);
        this.noCulling = true;
    }

    @Override
    public BioplethoraEntityClasses getBioplethoraClass() {
        return BioplethoraEntityClasses.HELLSENT;
    }

    protected BodyController createBodyControl() {
        return new TelemreyeEntity.BodyHelperController(this);
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createLivingAttributes()
                .add(Attributes.ARMOR, 12 * BioplethoraConfig.COMMON.mobArmorMultiplier.get())
                .add(Attributes.ATTACK_SPEED, 10)
                .add(Attributes.ATTACK_KNOCKBACK, 1.8D)
                .add(Attributes.ATTACK_DAMAGE, 15 * BioplethoraConfig.COMMON.mobMeeleeDamageMultiplier.get())
                .add(Attributes.MAX_HEALTH, 75 * BioplethoraConfig.COMMON.mobHealthMultiplier.get())
                .add(Attributes.MOVEMENT_SPEED, 1.2 * BioplethoraConfig.COMMON.mobMovementSpeedMultiplier.get())
                .add(Attributes.FOLLOW_RANGE, 64D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 24.0F));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.telemreye.idle_air", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "telemreye_controller", 0, this::predicate));
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
            this.level.addParticle(ParticleTypes.MYCELIUM, this.getX() + (double)f2, this.getY() + (double)f4, this.getZ() + (double)f3, 0.0D, 0.0D, 0.0D);
            this.level.addParticle(ParticleTypes.MYCELIUM, this.getX() - (double)f2, this.getY() + (double)f4, this.getZ() - (double)f3, 0.0D, 0.0D, 0.0D);
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
}
