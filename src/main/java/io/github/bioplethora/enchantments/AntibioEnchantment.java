package io.github.bioplethora.enchantments;

import io.github.bioplethora.entity.IBioClassification;
import io.github.bioplethora.enums.BPEntityClasses;
import io.github.bioplethora.registry.BioplethoraDamageSources;
import io.github.bioplethora.registry.BioplethoraParticles;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.server.ServerWorld;

public class AntibioEnchantment extends Enchantment {

    public final BPEntityClasses classTarget;

    public AntibioEnchantment(Enchantment.Rarity rarity, BPEntityClasses classTarget, EquipmentSlotType... slotTypes) {
        super(rarity, BPEnchantmentHelper.BP_WEAPON_AND_AXE, slotTypes);
        this.classTarget = classTarget;
    }

    public int getMinCost(int Int) {
        return 1 + (Int - 1) * 10;
    }

    public int getMaxCost(int Int) {
        return this.getMinCost(Int) + 15;
    }

    public int getMaxLevel() {
        return 5;
    }

    public boolean canEnchant(ItemStack stack) {
        return stack.getItem() instanceof AxeItem || super.canEnchant(stack);
    }

    @Override
    public void doPostAttack(LivingEntity pUser, Entity pTarget, int pLevel) {
        super.doPostAttack(pUser, pTarget, pLevel);
        if (pTarget instanceof IBioClassification) {
            if (((IBioClassification) pTarget).getBioplethoraClass() == classTarget) {

                if (pTarget.level instanceof ServerWorld) {
                    ((ServerWorld) pTarget.level).sendParticles(BioplethoraParticles.ANTIBIO_SPELL.get(),
                            pTarget.getX(), pTarget.getY() + 1.0, pTarget.getZ(),
                            10, 0.4, 1, 0.4, 0.05);
                }
                pUser.playSound(SoundEvents.ZOMBIE_INFECT, 1.0F, 1.0F);

                pTarget.invulnerableTime = 0;
                pTarget.hurt(BioplethoraDamageSources.antibio(pUser, pUser), EnchantmentHelper.getEnchantmentLevel(this, pUser));
            }
        }
    }
}
