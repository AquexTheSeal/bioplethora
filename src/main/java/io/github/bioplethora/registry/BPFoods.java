package io.github.bioplethora.registry;

import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class BPFoods {
    public static final Food FIERY_SAP_BOTTLE = new Food.Builder().nutrition(3).saturationMod(0.2F).effect(() -> new EffectInstance(Effects.FIRE_RESISTANCE, 200, 0), 1.0F).fast().build();
    public static final Food SOUL_SAP_BOTTLE = new Food.Builder().nutrition(6).saturationMod(0.4F).effect(() -> new EffectInstance(Effects.MOVEMENT_SPEED, 200, 0), 1.0F).build();
    public static final Food WARPED_GRAPES = new Food.Builder().nutrition(5).saturationMod(0.4F).build();
    public static final Food CRIMSON_BITTERSWEET_SEEDS = new Food.Builder().nutrition(4).saturationMod(0.3F).effect(() -> new EffectInstance(Effects.FIRE_RESISTANCE, 200, 0), 1.0F).fast().build();
    public static final Food THONTUS_BERRIES = new Food.Builder().nutrition(5).saturationMod(0.5F).build();

    public static final Food RAW_CUTTLEFISH_MEAT = new Food.Builder().nutrition(3).saturationMod(0.2F).meat().fast().build();
    public static final Food COOKED_CUTTLEFISH_MEAT = new Food.Builder().nutrition(6).saturationMod(0.5F).meat().fast().build();
    public static final Food RAW_FLENTAIR = new Food.Builder().nutrition(4).saturationMod(0.4F).effect(() -> new EffectInstance(Effects.POISON, 200, 0), 0.45F).meat().build();
    public static final Food COOKED_FLENTAIR = new Food.Builder().nutrition(7).saturationMod(0.7F).meat().build();
    public static final Food RAW_MOSILE = new Food.Builder().nutrition(3).saturationMod(0.3F).meat().fast().build();
    public static final Food COOKED_MOSILE = new Food.Builder().nutrition(5).saturationMod(0.5F).meat().fast().build();
    public static final Food RAW_JAWFLESH = new Food.Builder().nutrition(3).saturationMod(0.4F).meat().build();
    public static final Food COOKED_JAWFLESH = new Food.Builder().nutrition(7).saturationMod(0.9F).meat().build();
}