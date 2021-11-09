package io.github.bioplethora.util;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.registry.BioplethoraItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class BioplethoraItemGroup {
    public static ItemGroup BioplethoraItemGroup = new ItemGroup("bioplethora_item_group") {

        @Override
        public ItemStack makeIcon() {
            return new ItemStack(BioplethoraItems.CREPHOXL_FEATHER.get());
        }
    };
}
