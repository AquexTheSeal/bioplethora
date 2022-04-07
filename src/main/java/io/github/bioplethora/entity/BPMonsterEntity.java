package io.github.bioplethora.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.MonsterEntity;
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
public abstract class BPMonsterEntity extends MonsterEntity implements IAnimatable {

    @Override
    public boolean hurt(DamageSource p_70097_1_, float p_70097_2_) {
        return super.hurt(p_70097_1_, p_70097_2_);
    }

    protected static final DataParameter<Boolean> MOVING = EntityDataManager.defineId(BPMonsterEntity.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Boolean> ATTACKING = EntityDataManager.defineId(BPMonsterEntity.class, DataSerializers.BOOLEAN);
    /*protected static final DataParameter<Boolean> SMASHING = EntityDataManager.defineId(AnimatableHostileEntity.class, DataSerializers.BOOLEAN);*/

    protected boolean isAnimationFinished = false;

    public BPMonsterEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
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
        /*this.entityData.define(SMASHING, false);*/
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

    /*public boolean getSmashing() {
        return this.entityData.get(SMASHING);
    }

    /*public void setSmashing(boolean smashing) {
        this.entityData.set(SMASHING, smashing);
    }*/

    //protected abstract boolean hurt();
}