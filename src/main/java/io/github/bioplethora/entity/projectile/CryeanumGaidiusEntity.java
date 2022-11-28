package io.github.bioplethora.entity.projectile;

import io.github.bioplethora.blocks.api.world.EffectUtils;
import io.github.bioplethora.registry.BPEntities;
import io.github.bioplethora.registry.BPItems;
import io.github.bioplethora.registry.BPParticles;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class CryeanumGaidiusEntity extends GaidiusBaseEntity {

    public CryeanumGaidiusEntity(EntityType<? extends CryeanumGaidiusEntity> type, World world) {
        super(type, world);
    }

    public CryeanumGaidiusEntity(World world, LivingEntity entity) {
        super(BPEntities.CRYEANUM_GAIDIUS.get(), entity, world);
    }

    public CryeanumGaidiusEntity(World world, double v, double v1, double v2) {
        super(BPEntities.CRYEANUM_GAIDIUS.get(), world, v, v1, v2);
    }

    @Override
    protected void onHit(RayTraceResult pResult) {
        super.onHit(pResult);
        this.playSound(SoundEvents.GRASS_BREAK, 1.2F, 0.6F);
        EffectUtils.addCircleParticleForm(level, this, BPParticles.RED_ENIVILE_LEAF.get(), 7, 0.55, 0.0001);
    }

    @Override
    protected void onHitEntity(EntityRayTraceResult result) {
        super.onHitEntity(result);
        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01D, -0.1D, -0.01D));
    }

    @Override
    public int getProjectileDamage(EntityRayTraceResult result) {
        return isCrit() ? 12 : 7;
    }

    @Override
    protected Item getDefaultItem() {
        return BPItems.CRYEANUM_GAIDIUS.get();
    }
}
