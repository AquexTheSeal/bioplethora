package io.github.bioplethora.entity.projectile;

import io.github.bioplethora.blocks.api.world.BlockUtils;
import io.github.bioplethora.blocks.api.world.EffectUtils;
import io.github.bioplethora.registry.BPEntities;
import io.github.bioplethora.registry.BPItems;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class NetheriteObsidianGaidiusEntity extends GaidiusBaseEntity {

    public NetheriteObsidianGaidiusEntity(EntityType<? extends NetheriteObsidianGaidiusEntity> type, World world) {
        super(type, world);
    }

    public NetheriteObsidianGaidiusEntity(World world, LivingEntity entity) {
        super(BPEntities.NETHERITE_OBSIDIAN_GAIDIUS.get(), entity, world);
    }

    public NetheriteObsidianGaidiusEntity(World world, double v, double v1, double v2) {
        super(BPEntities.NETHERITE_OBSIDIAN_GAIDIUS.get(), world, v, v1, v2);
    }

    @Override
    protected void onHit(RayTraceResult pResult) {
        super.onHit(pResult);
        this.playSound(SoundEvents.ANVIL_PLACE, 0.65F, 0.8F);
        EffectUtils.addCircleParticleForm(level, this, ParticleTypes.POOF, 7, 0.55, 0.0001);
    }

    @Override
    protected void onHitEntity(EntityRayTraceResult result) {
        super.onHitEntity(result);
        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01D, -0.1D, -0.01D));
        if (result.getEntity() instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) result.getEntity();
            entity.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 60, 3));
        }
    }

    @Override
    protected void onHitBlock(BlockRayTraceResult pResult) {
        super.onHitBlock(pResult);
        if (level.getBlockState(pResult.getBlockPos()).getMaterial() == Material.GLASS) {
            level.destroyBlock(pResult.getBlockPos(), false);
        } else {
            this.playSound(SoundEvents.WITHER_BREAK_BLOCK, 0.65F, 0.65F);
            BlockUtils.knockUpRandomNearbyBlocks(level, 0.25D, pResult.getBlockPos(), 2, 2, 2, true, true);
            this.remove();
        }
    }

    @Override
    public int getProjectileDamage(EntityRayTraceResult result) {
        return isCrit() ? 24 : 16;
    }

    @Override
    protected Item getDefaultItem() {
        return BPItems.NETHERITE_OBSIDIAN_GAIDIUS.get();
    }
}
