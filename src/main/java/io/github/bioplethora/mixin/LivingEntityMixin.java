package io.github.bioplethora.mixin;

import io.github.bioplethora.entity.IBioClassification;
import io.github.bioplethora.enums.BPEntityClasses;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LivingEntity.class)
public class LivingEntityMixin implements IBioClassification {

    @Override
    public BPEntityClasses getBioplethoraClass() {
        return BPEntityClasses.NONE;
    }
}
