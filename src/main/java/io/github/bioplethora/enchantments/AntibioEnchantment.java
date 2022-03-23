package io.github.bioplethora.enchantments;

import io.github.bioplethora.BioplethoraConfig;
import io.github.bioplethora.entity.IBioClassification;
import io.github.bioplethora.enums.BPEntityClasses;
import io.github.bioplethora.registry.BPDamageSources;
import io.github.bioplethora.registry.BPEnchantments;
import io.github.bioplethora.registry.BPParticles;
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

    public boolean checkCompatibility(Enchantment pEnch) {

        Enchantment eco = BPEnchantments.ANTIBIO_ECOHARMLESS.get();
        Enchantment ple = BPEnchantments.ANTIBIO_PLETHONEUTRAL.get();
        Enchantment dan = BPEnchantments.ANTIBIO_DANGERUM.get();
        Enchantment hel = BPEnchantments.ANTIBIO_HELLSENT.get();
        Enchantment eld = BPEnchantments.ANTIBIO_ELDERIA.get();

        if (!BioplethoraConfig.COMMON.antibioCompatibility.get()) {
            if (this == eco) {
                return getCompatHelper(pEnch, eco);
            } else if (this == ple) {
                return getCompatHelper(pEnch, ple);
            } else if (this == dan) {
                return getCompatHelper(pEnch, dan);
            } else if (this == hel) {
                return getCompatHelper(pEnch, hel);
            } else if (this == eld) {
                return getCompatHelper(pEnch, eld);
            }
        } return super.checkCompatibility(pEnch);
    }

    public boolean getCompatHelper(Enchantment pEnch, Enchantment whitelisted) {
        return super.checkCompatibility(pEnch) && !(pEnch instanceof AntibioEnchantment) && (pEnch != whitelisted);
    }

    @Override
    public void doPostAttack(LivingEntity pUser, Entity pTarget, int pLevel) {
        super.doPostAttack(pUser, pTarget, pLevel);
        if (pTarget instanceof IBioClassification) {
            if (((IBioClassification) pTarget).getBioplethoraClass() == classTarget) {

                if (pTarget.level instanceof ServerWorld) {
                    ((ServerWorld) pTarget.level).sendParticles(BPParticles.ANTIBIO_SPELL.get(),
                            pTarget.getX(), pTarget.getY() + 1.0, pTarget.getZ(),
                            10, 0.4, 1, 0.4, 0.05);
                }
                pUser.playSound(SoundEvents.ZOMBIE_INFECT, 1.0F, 1.0F);

                pTarget.invulnerableTime = 0;
                pTarget.hurt(BPDamageSources.antibio(pUser, pUser), EnchantmentHelper.getEnchantmentLevel(this, pUser));
            }
        }
    }
}
