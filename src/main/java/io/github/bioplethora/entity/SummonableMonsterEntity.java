package io.github.bioplethora.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public abstract class SummonableMonsterEntity extends BPMonsterEntity implements IAnimatable {

    private LivingEntity owner;
    private boolean hasLimitedLife;
    private boolean explodeOnExpiry;
    private int lifeLimitBeforeDeath;
    private int limitedLifeTicks = 0;

    public SummonableMonsterEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    abstract public void registerControllers(AnimationData data);

    @Override
    abstract public AnimationFactory getFactory();

    public LivingEntity getOwner() {
        return this.owner;
    }

    public void setOwner(LivingEntity mobEntity) {
        this.owner = mobEntity;
    }

    public void addAdditionalSaveData(CompoundNBT compoundNBT) {
        super.addAdditionalSaveData(compoundNBT);
        compoundNBT.putBoolean("HasLifeLimit", this.hasLimitedLife);
        if (this.hasLimitedLife) {
            compoundNBT.putInt("LifeTicks", this.limitedLifeTicks);
            compoundNBT.putInt("LifeLimit", this.lifeLimitBeforeDeath);
            compoundNBT.putBoolean("ExplodeOnExpiry", this.explodeOnExpiry);
        }
    }

    public void readAdditionalSaveData(CompoundNBT compoundNBT) {
        super.readAdditionalSaveData(compoundNBT);
        this.setHasLimitedLife(compoundNBT.getBoolean("HasLifeLimit"));
        this.setLimitedLifeTicks(compoundNBT.getInt("LifeTicks"));
        this.setLifeLimitBeforeDeath(compoundNBT.getInt("LifeLimit"));
        this.setExplodeOnExpiry(compoundNBT.getBoolean("ExplodeOnExpiry"));
    }

    public void setLimitedLifeTicks(int limitedLifeTicks) {
        this.limitedLifeTicks = limitedLifeTicks;
    }

    public void setHasLimitedLife(boolean hasLimitedLife) {
        this.hasLimitedLife = hasLimitedLife;
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
