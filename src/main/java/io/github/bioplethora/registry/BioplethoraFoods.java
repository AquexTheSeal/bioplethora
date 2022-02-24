package io.github.bioplethora.registry;

import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class BioplethoraFoods {
    public static final Food RAW_CUTTLEFISH_MEAT = new Food.Builder().nutrition(3).saturationMod(0.2F).meat().fast().build();
    public static final Food COOKED_CUTTLEFISH_MEAT = new Food.Builder().nutrition(6).saturationMod(0.5F).meat().fast().build();
    public static final Food RAW_FLENTAIR = new Food.Builder().nutrition(4).saturationMod(0.4F).effect(new EffectInstance(Effects.POISON, 200, 0), 0.55F).meat().build();
    public static final Food COOKED_FLENTAIR = new Food.Builder().nutrition(7).saturationMod(0.7F).meat().build();
}