package io.github.bioplethora.entity.projectile;

import io.github.bioplethora.registry.BPItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.projectile.EyeOfEnderEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(value = Dist.CLIENT, _interface = IRendersAsItem.class)
public class WindyEssenceEntity extends EyeOfEnderEntity {

    public WindyEssenceEntity(EntityType<? extends EyeOfEnderEntity> type, World world) {
        super(type, world);
    }

    public ItemStack getItem() {
        ItemStack itemstack = this.getItemRaw();
        return itemstack.isEmpty() ? new ItemStack(BPItems.WINDY_ESSENCE.get()) : itemstack;
    }
}
