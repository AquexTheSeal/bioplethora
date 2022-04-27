package io.github.bioplethora.registry;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.loot.conditions.InHellmode;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.Map;

public class BPLootConditions {
    private static final Map<ResourceLocation, LootConditionType> LOOT_CONDITIONS = new HashMap<>();

    public static final LootConditionType IN_HELLMODE = addCondition("in_hellmode", new InHellmode.Serializer());

    private static LootConditionType addCondition(String name, ILootSerializer<? extends ILootCondition> serializer) {
        LootConditionType condition = new LootConditionType(serializer);
        LOOT_CONDITIONS.put(new ResourceLocation(Bioplethora.MOD_ID, name), condition);
        return condition;
    }

    public static void registerConditions() {
        LOOT_CONDITIONS.forEach((key, condition) -> Registry.register(Registry.LOOT_CONDITION_TYPE, key, condition));
    }
}
