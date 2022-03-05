package io.github.bioplethora.item.extras;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class AlphanumGemItem extends Item {

    public AlphanumGemItem(Properties properties) {
        super(properties);
    }

    public boolean hasCustomEntity(ItemStack stack) {
        return true;
    }

    @Nullable
    public Entity createEntity(World world, Entity entity, ItemStack itemstack) {
        entity.setNoGravity(true);
        return null;
    }

    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        entity.setDeltaMovement(entity.getDeltaMovement().scale(1));
        if (entity.level.isClientSide) {
            if (entity.getAge() % 20 == 0) {
                entity.level.addParticle(ParticleTypes.POOF,
                        entity.getRandomX(0.2D),
                        entity.getRandomY() + 0.2D,
                        entity.getRandomZ(0.2D),
                        entity.level.getRandom().nextFloat() * 0.02F - 0.01F,
                        -0.01F - entity.level.getRandom().nextFloat() * 0.01F,
                        entity.level.getRandom().nextFloat() * 0.02F - 0.01F);
            }
        }

        return false;
    }
}
