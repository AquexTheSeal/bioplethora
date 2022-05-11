package io.github.bioplethora.entity;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.registry.BPBlocks;
import io.github.bioplethora.registry.BPEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;

public class BPBoatEntity extends BoatEntity {
    private static final DataParameter<String> WOOD_TYPE = EntityDataManager.defineId(BPBoatEntity.class, DataSerializers.STRING);

    public BPBoatEntity(EntityType<? extends BoatEntity> type, World world) {
        super(type, world);
        this.blocksBuilding = true;
    }

    public BPBoatEntity(World worldIn, double pX, double pY, double pZ) {
        this(BPEntities.CAERULWOOD_BOAT.get(), worldIn);
        this.setPos(pX, pY, pZ);
        this.setDeltaMovement(Vector3d.ZERO);
        this.xo = pX;
        this.yo = pY;
        this.zo = pZ;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(WOOD_TYPE, "caerulwood");
    }

    @Override
    protected void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        setWoodType(compound.getString("Type"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putString("Type", this.getWoodType());
    }

    public String getWoodType() {
        return this.entityData.get(WOOD_TYPE);
    }

    public void setWoodType(String wood) {
        this.entityData.set(WOOD_TYPE, wood);
    }

    @Override
    public Item getDropItem() {
        switch(this.getWoodType()) {
            case "caerulwood":
                return BPBlocks.CAERULWOOD_BOAT.get();
            default:
                return BPBlocks.CAERULWOOD_BOAT.get();
        }
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(Bioplethora.MOD_ID, this.getWoodType() + "_boat")));
    }

    @Nonnull
    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
