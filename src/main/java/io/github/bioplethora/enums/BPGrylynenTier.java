package io.github.bioplethora.enums;

import io.github.bioplethora.entity.IGrylynenTier;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

public enum BPGrylynenTier implements IGrylynenTier {

    /**
     * @param name - Name of the type
     * @param crystalColor - Color of the Crystal under the Grylynen (Only allowed: green, yellow, red)
     * @param tier - Corresponding Item Tier
     * @param tierDmg - Grylynen Tier Base Attack Damage
     * @param tierHP - Grylynen Tier HP on Default
     * @param hellTierHP - Grylynen Tier HP on Hellmode Config
     */

    WOODEN("wooden", "green", 5, 3, 4, SoundEvents.WOOD_BREAK),
    STONE("stone", "green", 6, 4, 6, SoundEvents.STONE_BREAK),
    GOLDEN("golden", "yellow", 6, 4, 6, SoundEvents.NETHER_GOLD_ORE_BREAK),
    IRON("iron", "yellow", 8, 6, 8, SoundEvents.BONE_BLOCK_BREAK),
    DIAMOND("diamond", "red", 10, 8, 10, SoundEvents.IRON_GOLEM_DAMAGE),
    NETHERITE("netherite", "red", 14, 10, 13, SoundEvents.NETHERITE_BLOCK_BREAK);

    private final String name;
    private final String crystalColor;
    private final int tierDmg;
    private final int tierHP;
    private final int hellTierHP;
    private final SoundEvent hurtSound;

    BPGrylynenTier(String name, String crystalColor, int tierDmg, int tierHP, int hellTierHP, SoundEvent hurtSound) {
        this.name = name;
        this.crystalColor = crystalColor;
        this.tierDmg = tierDmg;
        this.tierHP = tierHP;
        this.hellTierHP = hellTierHP;
        this.hurtSound = hurtSound;
    }

    public String getTierName() {
        return this.name;
    }

    public String getCrystalColor() {
        return this.crystalColor;
    }

    public int getTierDamage() {
        return this.tierDmg;
    }

    public int getTierHealth() {
        return this.tierHP;
    }

    public int getHellTierHP() {
        return this.hellTierHP;
    }

    public SoundEvent getHurtSound() {
        return this.hurtSound;
    }
}
