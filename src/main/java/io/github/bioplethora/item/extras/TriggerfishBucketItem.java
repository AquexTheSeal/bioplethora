package io.github.bioplethora.item.extras;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.FishBucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class TriggerfishBucketItem extends FishBucketItem {

    public TriggerfishBucketItem(Supplier<? extends EntityType<?>> p_i244797_1_, Supplier<? extends Fluid> p_i244797_2_, Properties p_i244797_3_) {
        super(p_i244797_1_, p_i244797_2_, p_i244797_3_);
    }
}
