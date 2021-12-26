package io.github.bioplethora.registry;

import net.minecraft.item.Food;

public class BioplethoraFoods {
    public static final Food RAW_CUTTLEFISH_MEAT = new Food.Builder().nutrition(4).saturationMod(0.3F).meat().fast().build();
    public static final Food COOKED_CUTTLEFISH_MEAT = new Food.Builder().nutrition(8).saturationMod(0.8F).meat().fast().build();
}