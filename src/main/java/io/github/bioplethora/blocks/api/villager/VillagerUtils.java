package io.github.bioplethora.blocks.api.villager;

import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraftforge.event.village.VillagerTradesEvent;

public class VillagerUtils {

    public static final int NOVICE = 1, APPRENTICE = 2, JOURNEYMAN = 3, EXPERT = 4, MASTER = 5;

    public static void addVillagerTrades(VillagerTradesEvent event, int level, VillagerTrades.ITrade... trades) {
        for (VillagerTrades.ITrade trade : trades) event.getTrades().get(level).add(trade);
    }

    public static void addVillagerTrades(VillagerTradesEvent event, int level, VillagerProfession profession, VillagerTrades.ITrade... trades) {
        if (event.getType() == profession) addVillagerTrades(event, level, trades);
    }
}
