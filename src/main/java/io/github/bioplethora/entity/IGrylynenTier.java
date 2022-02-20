package io.github.bioplethora.entity;

import net.minecraft.util.SoundEvent;

public interface IGrylynenTier {

    /**
    * Gets the Grylynen Tier name
     */
    String getTierName();

    /**
     * Gets the Grylynen Crystal Color. Only use "green", "yellow", or "red", otherwise, it will cause a texture missing error.
     */
    String getCrystalColor();

    /**
     * Gets the Grylynen's Attack Damage.
     */
    int getTierDamage();

    /**
     * Gets the Grylynen's Health, note that all Grylynens only take 1 damage at a time under any circumstances, so it is best to keep the health very low.
     */
    int getTierHealth();

    /**
     * Gets the Grylynen's Health, but with Bioplethora's Hell mode config enabled.
     */
    int getHellTierHP();

    /**
     * Gets the Grylynen's Hurt Sound.
     */
    SoundEvent getHurtSound();
}
