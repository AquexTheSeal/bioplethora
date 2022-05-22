package io.github.bioplethora.entity.projectile;

import io.github.bioplethora.registry.BPEntities;
import io.github.bioplethora.registry.BPItems;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class AbyssalScalesEntity extends ProjectileItemEntity {

    public AbyssalScalesEntity(EntityType<? extends AbyssalScalesEntity> type, World world) {
        super(type, world);
    }

    public AbyssalScalesEntity(World world, LivingEntity entity) {
        super(BPEntities.ABYSSAL_SCALES.get(), entity, world);
    }

    public AbyssalScalesEntity(World world, double v, double v1, double v2) {
        super(BPEntities.ABYSSAL_SCALES.get(), v, v1, v2, world);
    }

    @Override
    protected Item getDefaultItem() {
        return BPItems.ABYSSAL_SCALES.get();
    }

    @Override
    protected void onHitEntity(EntityRayTraceResult result) {
        super.onHitEntity(result);
        Entity entity = result.getEntity();
        entity.hurt(DamageSource.thrown(this, this.getOwner()), 6);
        this.playSound(SoundEvents.GLASS_BREAK, 1.2F, 0.8F);
        if (entity instanceof LivingEntity && entity != this.getOwner()) {
            LivingEntity living = (LivingEntity) entity;
            if (!(this.getOwner() instanceof PlayerEntity)) {
                living.invulnerableTime = 0;
            }
            living.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 200, 6));
            living.addEffect(new EffectInstance(Effects.DIG_SLOWDOWN, 200, 3));
        }
        if (!this.level.isClientSide) {
            this.level.broadcastEntityEvent(this, (byte) 3);
            this.remove();
        }
    }

    @Override
    protected void onHitBlock(BlockRayTraceResult pResult) {
        super.onHitBlock(pResult);
        Block block = level.getBlockState(pResult.getBlockPos()).getBlock();

        this.playSound(SoundEvents.GLASS_BREAK, 1.2F, 0.8F);
        if (!BlockTags.DRAGON_IMMUNE.contains(block)) {
            if (this.random.nextBoolean()) {
                level.setBlock(pResult.getBlockPos(), Blocks.ICE.defaultBlockState(), 2);
            } else {
                level.setBlock(pResult.getBlockPos(), Blocks.BLUE_ICE.defaultBlockState(), 2);
            }
        }
        if (!this.level.isClientSide) {
            this.level.broadcastEntityEvent(this, (byte)3);
            this.remove();
        }
    }

    public boolean isOnFire() {
        return false;
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
