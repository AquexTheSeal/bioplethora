package io.github.bioplethora.entity.others;

import io.github.bioplethora.enums.BPEffectTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class BPEffectEntity extends Entity implements IAnimatable {

    private final AnimationFactory factory = new AnimationFactory(this);
    private LivingEntity owner;
    public final BPEffectTypes effectType;
    public int lifespan;

    public BPEffectEntity(EntityType<?> pType, World pLevel, BPEffectTypes effectType) {
        super(pType, pLevel);
        this.lifespan = 0;
        this.effectType = effectType;
    }

    @Nullable
    public BPEffectTypes getEffectType() {
        return effectType;
    }

    public LivingEntity getOwner() {
        return this.owner;
    }

    public void setOwner(LivingEntity livingEntity) {
        this.owner = livingEntity;
    }

    public void setLifespan(int value) {
        lifespan = value;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {

        event.getController().setAnimation(new AnimationBuilder().addAnimation(effectType.getAnimation().getAnimationString(), true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "bp_effect_controller", 0, this::predicate));
    }

    @Override
    public void tick() {
        super.tick();
        ++lifespan;
        if (lifespan == this.effectType.getAnimation().getLifespan()) {
            this.remove();
            this.setLifespan(0);
        }
        if (this.getOwner() != null) {
            this.yRot = this.getOwner().yRot;
        }
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compoundNBT) {
        this.setLifespan(compoundNBT.getInt("lifespan"));
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compoundNBT) {
        compoundNBT.putInt("lifespan", this.lifespan);
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
