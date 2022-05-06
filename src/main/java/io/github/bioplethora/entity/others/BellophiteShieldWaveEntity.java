package io.github.bioplethora.entity.others;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class BellophiteShieldWaveEntity extends Entity implements IAnimatable {

    private final AnimationFactory factory = new AnimationFactory(this);
    private LivingEntity owner;
    public int lifespan;

    public BellophiteShieldWaveEntity(EntityType<?> entityType, World world) {
        super(entityType, world);
        this.lifespan = 0;
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

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.bellophite_shield_wave.default", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "bellophite_shield_wave_controller", 0, this::predicate));
    }

    public void tick() {
        super.tick();

        if (this.getOwner() != null) {
            double x = this.getOwner().getX(), y = this.getOwner().getY(), z = this.getOwner().getZ();
            AxisAlignedBB area = new AxisAlignedBB(x - (10 / 2d), y, z - (10 / 2d), x + (10 / 2d), y + (10 / 2d), z + (10 / 2d));
            BlockPos pos = new BlockPos(x, y + 1, z);
            World world = this.level;

            ++lifespan;
            this.moveTo(pos, 0.0F, 0.0F);
            for (Entity entityIterator : world.getEntitiesOfClass(Entity.class, area, null)) {
                if (entityIterator instanceof LivingEntity && entityIterator != this.getOwner()) {
                    entityIterator.hurt(DamageSource.indirectMagic(this.getOwner(), this.getOwner()), 1F);
                }
            }
        }

        if (this.lifespan == 40) {
            this.remove();
        }
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    protected void readAdditionalSaveData(CompoundNBT compoundNBT) {
        this.setLifespan(compoundNBT.getInt("lifespan"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT compoundNBT) {
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
