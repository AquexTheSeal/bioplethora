package io.github.bioplethora.registry;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class BioplethoraItemGroup {
    public static ItemGroup BioplethoraItemItemGroup = new ItemGroup("bioplethora_item_item_group") {

        @Override
        public ItemStack makeIcon() {
            return new ItemStack(BioplethoraItems.NANDBRI_SCALES.get());
        }
    };

    public static ItemGroup BioplethoraSpawnEggsItemGroup = new ItemGroup("bioplethora_spawn_eggs_item_group") {

        @Override
        public ItemStack makeIcon() {
            return new ItemStack(BioplethoraItems.NANDBRI_SPAWN_EGG.get());
        }
    };
}
