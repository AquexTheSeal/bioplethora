package io.github.bioplethora.registry;

import io.github.bioplethora.Bioplethora;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

public class BioplethoraTags {
    public static final class Blocks {
        public static final ITag.INamedTag<Block> FENCES = mcLoc("fences");
        public static final ITag.INamedTag<Block> FENCE_GATES = mcLoc("fence_gates");

        private static ITag.INamedTag<Block> mcLoc(String path) {
            return BlockTags.bind(new ResourceLocation("minecraft", path).toString());
        }

        private static ITag.INamedTag<Block> forgeLoc(String path) {
            return BlockTags.bind(new ResourceLocation("forge", path).toString());
        }

        private static ITag.INamedTag<Block> bioLoc(String path) {
            return BlockTags.bind(new ResourceLocation(Bioplethora.MOD_ID, path).toString());
        }
    }

    public static final class Items {
        private static ITag.INamedTag<Item> mcLoc(String path) {
            return ItemTags.bind(new ResourceLocation("minecraft", path).toString());
        }

        private static ITag.INamedTag<Item> forgeLoc(String path) {
            return ItemTags.bind(new ResourceLocation("forge", path).toString());
        }

        private static ITag.INamedTag<Item> bioLoc(String path) {
            return ItemTags.bind(new ResourceLocation(Bioplethora.MOD_ID, path).toString());
        }
    }

    public static final class Entities {

        // Wastelands of Baedoor Integration
        public static final ITag.INamedTag<EntityType<?>> AVOIDER_KILLABLE = forgeLoc("avoider_killable");
        public static final ITag.INamedTag<EntityType<?>> AUTOMATON_TYPE = forgeLoc("automaton_type");

        private static ITag.INamedTag<EntityType<?>> mcLoc(String path) {
            return EntityTypeTags.bind(new ResourceLocation("minecraft", path).toString());
        }

        private static ITag.INamedTag<EntityType<?>> forgeLoc(String path) {
            return EntityTypeTags.bind(new ResourceLocation("forge", path).toString());
        }

        private static ITag.INamedTag<EntityType<?>> bioLoc(String path) {
            return EntityTypeTags.bind(new ResourceLocation(Bioplethora.MOD_ID, path).toString());
        }
    }
}
