package io.github.bioplethora.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public abstract class SummonableMonsterEntity extends AnimatableMonsterEntity implements IAnimatable {

    private MobEntity owner;
    private boolean hasLimitedLife;
    private boolean explodeOnExpiry;
    private int limitedLifeTicks;
    private int lifeLimitBeforeDeath;

    public SummonableMonsterEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    abstract public void registerControllers(AnimationData data);

    @Override
    abstract public AnimationFactory getFactory();

    public MobEntity getOwner() {
        return this.owner;
    }

    public void setOwner(MobEntity mobEntity) {
        this.owner = mobEntity;
    }

    public void addAdditionalSaveData(CompoundNBT compoundNBT) {
        super.addAdditionalSaveData(compoundNBT);
        if (this.hasLimitedLife) {
            compoundNBT.putInt("LifeTicks", this.limitedLifeTicks);
        }
    }

    public void setLimitedLife(int limitedLife) {
        this.hasLimitedLife = true;
        this.limitedLifeTicks = limitedLife;
    }

    public void setExplodeOnExpiry(boolean explodeOnExpiry) {
        this.explodeOnExpiry = explodeOnExpiry;
    }

    public void setLifeLimitBeforeDeath(int lifeLimitBeforeDeath) {
        this.lifeLimitBeforeDeath = lifeLimitBeforeDeath;
    }

    public void aiStep() {
        super.aiStep();
        if (this.hasLimitedLife) {
            ++limitedLifeTicks;

            if (this.limitedLifeTicks >= this.lifeLimitBeforeDeath) {
                if (this.explodeOnExpiry) {
                    this.level.explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 3F, Explosion.Mode.BREAK);
                }
                this.remove();
            }

            if (this.getOwner() != null) {
                if (!this.level.isClientSide && this.getOwner().isDeadOrDying() && this.explodeOnExpiry) {
                    this.level.explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 2F, Explosion.Mode.BREAK);
                    this.kill();
                }
            }
        }
    }
}
