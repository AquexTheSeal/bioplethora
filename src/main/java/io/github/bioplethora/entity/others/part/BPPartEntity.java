package io.github.bioplethora.entity.others.part;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraftforge.entity.PartEntity;

public class BPPartEntity<T extends MobEntity> extends PartEntity<T> {
    public final T parentMob;
    public final String name;
    private final EntitySize size;

    public BPPartEntity(T parent, String name, float width, float height) {
        super(parent);
        size = EntitySize.scalable(width, height);
        refreshDimensions();
        parentMob = parent;
        this.name = name;
    }

    @Override
    public EntitySize getDimensions(Pose pPose) {
        return size;
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    public boolean is(Entity pEntity) {
        return this == pEntity || parentMob == pEntity;
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        return !isInvulnerableTo(pSource) && this.getParent().hurt(pSource, pAmount);
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    protected void readAdditionalSaveData(CompoundNBT pCompound) {
    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT pCompound) {
    }
}
