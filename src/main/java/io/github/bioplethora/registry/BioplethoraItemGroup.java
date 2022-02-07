package io.github.bioplethora.registry;

import io.github.bioplethora.Bioplethora;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BioplethoraItemGroup {
    public static ItemGroup BioplethoraItemItemGroup = new ItemGroup("bioplethora_item_item_group") {

        @Override
        public ItemStack makeIcon() {
            return new ItemStack(BioplethoraItems.ARBITRARY_BALLISTA.get());
        }

        @OnlyIn(Dist.CLIENT)
        @Override
        public ResourceLocation getTabsImage() {
            return new ResourceLocation(Bioplethora.MOD_ID, "textures/gui/container/bpitem_tabs.png");
        }

        @OnlyIn(Dist.CLIENT)
        @Override
        public ResourceLocation getBackgroundImage() {
            return new ResourceLocation(Bioplethora.MOD_ID, "textures/gui/container/bp_creative_tab.png");
        }
    };

    public static ItemGroup BioplethoraSpawnEggsItemGroup = new ItemGroup("bioplethora_spawn_eggs_item_group") {

        @Override
        public ItemStack makeIcon() {
            return new ItemStack(BioplethoraItems.PEAGUIN_SPAWN_EGG.get());
        }

        @OnlyIn(Dist.CLIENT)
        @Override
        public ResourceLocation getTabsImage() {
            return new ResourceLocation(Bioplethora.MOD_ID, "textures/gui/container/bpeggs_tabs.png");
        }

        @OnlyIn(Dist.CLIENT)
        @Override
        public ResourceLocation getBackgroundImage() {
            return new ResourceLocation(Bioplethora.MOD_ID, "textures/gui/container/bp_creative_tab.png");
        }
    };
}
