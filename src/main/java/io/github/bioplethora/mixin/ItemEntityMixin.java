package io.github.bioplethora.mixin;

import io.github.bioplethora.registry.BPItems;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin {

    @Shadow public abstract ItemStack getItem();

    @Inject(method = "hurt", at = @At(value = "HEAD"), cancellable = true)
    public void hurt(DamageSource pSource, float pAmount, CallbackInfoReturnable cir) {
        if (!this.getItem().isEmpty() && pSource.isExplosion()) {

            if (this.getItem().getItem() == BPItems.ALPHANUM_OBLITERATOR.get()) {
                cir.setReturnValue(false);

            } else if (this.getItem().getItem() == BPItems.ALPHANUM_GEM.get()) {
                cir.setReturnValue(false);
            }
        }
    }
}
