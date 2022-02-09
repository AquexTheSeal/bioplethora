package io.github.bioplethora.mixin;

import io.github.bioplethora.entity.IBioClassification;
import io.github.bioplethora.registry.BioplethoraEntityClasses;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LivingEntity.class)
public class LivingEntityMixin implements IBioClassification {

    @Override
    public BioplethoraEntityClasses getBioplethoraClass() {
        return BioplethoraEntityClasses.NONE;
    }
}
