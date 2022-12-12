package io.github.bioplethora.mixin;

import io.github.bioplethora.entity.creatures.AlphemEntity;
import io.github.bioplethora.entity.creatures.AlphemKingEntity;
import io.github.bioplethora.registry.BPEntities;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.tileentity.MobSpawnerTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.spawner.AbstractSpawner;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobSpawnerTileEntity.class)
public abstract class MobSpawnerTileEntityMixin extends TileEntity {

    @Shadow @Final private AbstractSpawner spawner;

    public MobSpawnerTileEntityMixin(TileEntityType<?> p_i48289_1_) {
        super(p_i48289_1_);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        if (this.spawner.getSpawnerEntity() instanceof AlphemEntity) {
            int areaint = 32;
            AxisAlignedBB aabb = new AxisAlignedBB(
                    getBlockPos().getX() - areaint, getBlockPos().getY() - areaint, getBlockPos().getZ() - areaint,
                    getBlockPos().getX() + areaint, getBlockPos().getY() + areaint, getBlockPos().getZ() + areaint
            );

            if (level.getEntitiesOfClass(AlphemKingEntity.class, aabb).size() > 0) {
                level.destroyBlock(getBlockPos(), false);
            }
        }
    }
}
