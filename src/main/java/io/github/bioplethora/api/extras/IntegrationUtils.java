package io.github.bioplethora.api.extras;

import net.minecraft.item.ItemStack;

public class IntegrationUtils {

    // Wastelands of Baedoor Integration
    public static void addWobrSaber(ItemStack stack, double sabreDef, double sabreCol, double sabreHarm) {
        if (!stack.getOrCreateTag().getBoolean("is_NBT_set")) {
            stack.getOrCreateTag().putBoolean("is_NBT_set", true);

            stack.getOrCreateTag().putDouble("sabre_defence", sabreDef);
            stack.getOrCreateTag().putDouble("sabre_cooldown", sabreCol);
            stack.getOrCreateTag().putDouble("sabre_harm", sabreHarm);
        }
    }
}
