package io.github.bioplethora.item.weapons;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GrylynenShieldYellowItem extends GrylynenShieldBaseItem {

    public GrylynenShieldYellowItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getCooldownReduction() {
        return 40;
    }

    @Override
    public int getArmorBonus() {
        return 4;
    }

    @Override
    public void blockingSkill(ItemStack stack, LivingEntity user, Entity attacker, World world) {
        // Checks if it is a 50% chance
        if (Math.random() <= 0.5) {
            // Regenerates the user's health by 2 hearts
            user.setHealth(user.getHealth() + 2);
        }
    }
}
