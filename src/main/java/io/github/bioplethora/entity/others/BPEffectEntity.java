package io.github.bioplethora.entity.others;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.client.entity.render.others.BPEffectRender;
import io.github.bioplethora.entity.creatures.AlphemEntity;
import io.github.bioplethora.enums.BPEffectTypes;
import io.github.bioplethora.item.weapons.InfernalQuarterstaffItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
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
    private static final DataParameter<String> TYPE_ID = EntityDataManager.defineId(BPEffectEntity.class, DataSerializers.STRING);
    private static final DataParameter<Integer> FRAME_LEVEL = EntityDataManager.defineId(BPEffectEntity.class, DataSerializers.INT);
    private final AnimationFactory factory = new AnimationFactory(this);
    private LivingEntity owner;
    public int lifespan;
    public int frameTimer;

    public BPEffectEntity(EntityType<?> pType, World pLevel) {
        super(pType, pLevel);
        this.noCulling = true;
    }

    public BPEffectTypes getEffectType() {
        for (BPEffectTypes candids : BPEffectTypes.values()) {
            if (candids.getName().equals(this.getTypeID())) {
                return candids;
            }
        }
        return getDefaultEffect();
    }

    public void setEffectType(BPEffectTypes getEffectType) {
        this.setTypeID(getEffectType.getName());
    }

    public String getTypeID() {
        return this.entityData.get(TYPE_ID);
    }

    public void setTypeID(String value) {
        this.entityData.set(TYPE_ID, value);
    }

    public int getFrameLevel() {
        return this.entityData.get(FRAME_LEVEL);
    }

    public void setFrameLevel(int value) {
        this.entityData.set(FRAME_LEVEL, value);
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
        if (getEffectType() != null) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(getEffectType().getAnimation().getAnimationString(), true));
        } else {
            Bioplethora.LOGGER.info("EffectType for BPEffectEntity is null!");
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.bp_effect.spin", true));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "bp_effect_controller", 0, this::predicate));
    }

    @Override
    public void tick() {
        ++lifespan;
        if (lifespan >= this.getEffectType().getAnimation().getLifespan()) {
            this.setLifespan(0);
            this.remove();
        }
        ++frameTimer;
        if (frameTimer >= this.getEffectType().getFramesSpeed()) {
            this.setFrameLevel(this.getFrameLevel() == this.getEffectType().getFrames() ? 1 : this.getFrameLevel() + 1);
            frameTimer = 0;
        }
        super.tick();
        //this.moveTo(getOwner().getX(), getOwner().getY(), getOwner().getZ());
        if (this.getOwner() != null) {
            this.yRot = this.getOwner().yRot;
        }
    }

    protected void defineSynchedData() {
        this.entityData.define(TYPE_ID, "none");
        this.entityData.define(FRAME_LEVEL, 1);
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compoundNBT) {
        this.setLifespan(compoundNBT.getInt("lifespan"));
        this.setTypeID(compoundNBT.getString("typeId"));
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compoundNBT) {
        compoundNBT.putInt("lifespan", this.lifespan);
        compoundNBT.putString("typeId", this.getTypeID());
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public BPEffectTypes getDefaultEffect() {
        return BPEffectTypes.AERIAL_SHOCKWAVE;
    }
}
