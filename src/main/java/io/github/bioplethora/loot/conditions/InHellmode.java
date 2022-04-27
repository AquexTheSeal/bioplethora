package io.github.bioplethora.loot.conditions;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import io.github.bioplethora.BPConfig;
import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.registry.BPLootConditions;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameter;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;

import java.util.Set;

public class InHellmode implements ILootCondition {
    public static final InHellmode INSTANCE = new InHellmode();
    public static final LootParameter<Boolean> HELLMODE_PARAM = new LootParameter<>(new ResourceLocation(Bioplethora.MOD_ID, "hellmode"));

    public InHellmode() {
    }

    public LootConditionType getType() {
        return BPLootConditions.IN_HELLMODE;
    }

    public Set<LootParameter<?>> getReferencedContextParams() {
        return ImmutableSet.of(HELLMODE_PARAM);
    }

    @Override
    public boolean test(LootContext lootContext) {
        return BPConfig.IN_HELLMODE;
    }

    public static class Serializer implements ILootSerializer<InHellmode> {

        public void serialize(JsonObject pJson, InHellmode pValue, JsonSerializationContext pSerializationContext) {
        }

        public InHellmode deserialize(JsonObject pJson, JsonDeserializationContext pSerializationContext) {
            return InHellmode.INSTANCE;
        }
    }
}
