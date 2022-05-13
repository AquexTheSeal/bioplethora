package io.github.bioplethora.entity;

import io.github.bioplethora.entity.ai.gecko.IGeckoBaseEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

/**
 * Credits: WeirdNerd (Permission Granted)
 */
public abstract class BPMonsterEntity extends MonsterEntity implements IAnimatable, IGeckoBaseEntity {

    protected static final DataParameter<Boolean> MOVING = EntityDataManager.defineId(BPMonsterEntity.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Boolean> ATTACKING = EntityDataManager.defineId(BPMonsterEntity.class, DataSerializers.BOOLEAN);

    public int prevHurtTime;
    public boolean hurtRandomizer;

    @OnlyIn(Dist.CLIENT)
    public float ageInTicks;

    public BPMonsterEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    abstract public void registerControllers(AnimationData data);

    @Override
    abstract public AnimationFactory getFactory();

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        this.hurtRandomizer = this.getRandom().nextBoolean();
        return super.hurt(pSource, pAmount);
    }

    @Override
    public void baseTick() {
        this.prevHurtTime = hurtTime;
        super.baseTick();
    }

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