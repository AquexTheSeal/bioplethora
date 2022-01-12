package io.github.bioplethora.mixin;

import io.github.bioplethora.entity.IBioplethoraEntityClass;
import io.github.bioplethora.util.BioplethoraEntityClasses;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LivingEntity.class)
public class LivingEntityMixin implements IBioplethoraEntityClass {

    @Override
    public BioplethoraEntityClasses getBioplethoraClass() {
        return BioplethoraEntityClasses.NONE;
    }
}
