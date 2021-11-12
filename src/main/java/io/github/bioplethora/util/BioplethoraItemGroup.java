package io.github.bioplethora.util;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.registry.BioplethoraItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class BioplethoraItemGroup {
    public static ItemGroup BioplethoraItemItemGroup = new ItemGroup("bioplethora_item_item_group") {

        @Override
        public ItemStack makeIcon() {
            return new ItemStack(BioplethoraItems.CREPHOXL_FEATHER.get());
        }
    };

    public static ItemGroup BioplethoraSpawnEggsItemGroup = new ItemGroup("bioplethora_spawn_eggs_item_group") {

        @Override
        public ItemStack makeIcon() {
            return new ItemStack(BioplethoraItems.CREPHOXL_SPAWN_EGG.get());
        }
    };
}
