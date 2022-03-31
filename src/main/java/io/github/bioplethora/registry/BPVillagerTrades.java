package io.github.bioplethora.registry;

import io.github.bioplethora.helpers.villager.TradeConstructorUtils;
import io.github.bioplethora.helpers.villager.VillagerUtils;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.fml.RegistryObject;

public class BPVillagerTrades {

    public static void onVillagerTrades(VillagerTradesEvent event) {
        // All Professions
        VillagerUtils.addVillagerTrades(event, VillagerUtils.NOVICE,
                new TradeConstructorUtils(4, getStack(BPItems.GREEN_GRYLYNEN_CRYSTAL), 10, 2, 1.5F)
        );

        // Cleric
        VillagerUtils.addVillagerTrades(event, VillagerUtils.NOVICE, VillagerProfession.CLERIC,
                new TradeConstructorUtils(3, getStack(BPItems.WINDPIECE), 20, 1, 1.3F)
        );
        VillagerUtils.addVillagerTrades(event, VillagerUtils.APPRENTICE, VillagerProfession.CLERIC,
                new TradeConstructorUtils(5, getStack(BPItems.WINDPIECE, 2), 20, 2, 1.2F)
        );
        VillagerUtils.addVillagerTrades(event, VillagerUtils.APPRENTICE, VillagerProfession.CLERIC,
                new TradeConstructorUtils(28, getStack(BPItems.WINDY_ESSENCE, 2), 3, 4, 1.2F)
        );

        // Mason
        VillagerUtils.addVillagerTrades(event, VillagerUtils.APPRENTICE, VillagerProfession.MASON,
                new TradeConstructorUtils(12, getStack(BPItems.YELLOW_GRYLYNEN_CRYSTAL, 2), 15, 4, 1.4F)
        );
        VillagerUtils.addVillagerTrades(event, VillagerUtils.EXPERT, VillagerProfession.MASON,
                new TradeConstructorUtils(24, getStack(BPItems.RED_GRYLYNEN_CRYSTAL), 10, 6, 1.3F)
        );
    }

    public static ItemStack getStack(RegistryObject<Item> item) {
        return new ItemStack(item.get());
    }

    public static ItemStack getStack(RegistryObject<Item> item, int amount) {
        return new ItemStack(item.get(), amount);
    }
}
