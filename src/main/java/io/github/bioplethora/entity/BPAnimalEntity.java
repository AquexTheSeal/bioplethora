package io.github.bioplethora.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

/**
 * Credits: WeirdNerd (Permission Granted)
 */
public abstract class BPAnimalEntity extends TameableEntity implements IAnimatable {

    protected static final DataParameter<Boolean> MOVING = EntityDataManager.defineId(BPAnimalEntity.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Boolean> ATTACKING = EntityDataManager.defineId(BPAnimalEntity.class, DataSerializers.BOOLEAN);

    @Override
    public boolean hurt(DamageSource p_70097_1_, float p_70097_2_) {
        return super.hurt(p_70097_1_, p_70097_2_);
    }

    public BPAnimalEntity(EntityType<? extends TameableEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    abstract public void registerControllers(AnimationData data);

    @Override
    abstract public AnimationFactory getFactory();

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(MOVING, false);
        this.entityData.define(ATTACKING, false);
    }

    public boolean getMoving() {
        return this.entityData.get(MOVING);
    }

    public void setMoving(boolean moving) {
        this.entityData.set(MOVING, moving);
    }

    public boolean getAttacking() {
        return this.entityData.get(ATTACKING);
    }

    public void setAttacking(boolean attacking) {
        this.entityData.set(ATTACKING, attacking);
    }
}