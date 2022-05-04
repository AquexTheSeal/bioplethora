package io.github.bioplethora.mixin;

import io.github.bioplethora.item.weapons.BellophiteShieldItem;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin {

    @Shadow public abstract boolean requiresCustomPersistence();

    @Inject(at = @At("HEAD"), method = ("maybeDisableShield"), cancellable = true)
    private void maybeDisableShield(PlayerEntity player, ItemStack attackerAxe, ItemStack defendantShield, CallbackInfo ci) {

        if (defendantShield.getItem() instanceof BellophiteShieldItem) {
            ci.cancel();
        }
    }
}
