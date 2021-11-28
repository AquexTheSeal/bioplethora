package io.github.bioplethora.item.weapons;

import io.github.bioplethora.entity.projectile.BellophiteArrowEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.SpectralArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BellophiteArrowItem extends ArrowItem {

    public BellophiteArrowItem(Item.Properties properties) {
        super(properties);
    }

    public AbstractArrowEntity createArrow(World world, ItemStack itemStack, LivingEntity entity) {
        return new BellophiteArrowEntity(world, entity);
    }
}
