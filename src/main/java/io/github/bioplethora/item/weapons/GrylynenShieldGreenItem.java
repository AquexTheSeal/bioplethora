package io.github.bioplethora.item.weapons;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GrylynenShieldGreenItem extends GrylynenShieldBaseItem {

    public GrylynenShieldGreenItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getCooldownReduction() {
        return 60;
    }

    @Override
    public int getArmorBonus() {
        return 2;
    }

    @Override
    public void blockingSkill(ItemStack stack, LivingEntity user, Entity attacker, World world) {
        // This area is still empty at the moment because I am not planning to give the green shield a skill yet.
    }
}
