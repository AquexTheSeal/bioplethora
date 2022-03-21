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
        public static final ITag.INamedTag<Block> GRYLYNEN_UNBREAKABLE = forgeLoc("grylynen_unbreakable");

        public static final ITag.INamedTag<Block> WOODEN_GRYLYNEN_SPAWNABLE = forgeLoc("wooden_grylynen_spawnable");
        public static final ITag.INamedTag<Block> STONE_GRYLYNEN_SPAWNABLE = forgeLoc("stone_grylynen_spawnable");
        public static final ITag.INamedTag<Block> GOLDEN_GRYLYNEN_SPAWNABLE = forgeLoc("golden_grylynen_spawnable");
        public static final ITag.INamedTag<Block> IRON_GRYLYNEN_SPAWNABLE = forgeLoc("iron_grylynen_spawnable");
        public static final ITag.INamedTag<Block> DIAMOND_GRYLYNEN_SPAWNABLE = forgeLoc("diamond_grylynen_spawnable");
        public static final ITag.INamedTag<Block> NETHERITE_GRYLYNEN_SPAWNABLE = forgeLoc("netherite_grylynen_spawnable");

        public static final ITag.INamedTag<Block> ALPHANIA = forgeLoc("alphania");

        private static ITag.INamedTag<Block> bioLoc(String path) {
            return BlockTags.bind(new ResourceLocation(Bioplethora.MOD_ID, path).toString());
        }

        private static ITag.INamedTag<Block> mcLoc(String path) {
            return BlockTags.bind(new ResourceLocation("minecraft", path).toString());
        }

        private static ITag.INamedTag<Block> forgeLoc(String path) {
            return BlockTags.bind(new ResourceLocation("forge", path).toString());
        }
    }

    public static final class Items {

        public static final ITag.INamedTag<Item> LOGS = mcLoc("logs");
        public static final ITag.INamedTag<Item> PLANKS = mcLoc("planks");
        public static final ITag.INamedTag<Item> WOODEN_PRESSURE_PLATES = mcLoc("wooden_pressure_plates");
        public static final ITag.INamedTag<Item> WOODEN_BUTTONS = mcLoc("wooden_buttons");
        public static final ITag.INamedTag<Item> WOODEN_STAIRS = mcLoc("wooden_stairs");
        public static final ITag.INamedTag<Item> WOODEN_SLABS = mcLoc("wooden_slabs");
        public static final ITag.INamedTag<Item> WOODEN_FENCES = mcLoc("wooden_fences");
        public static final ITag.INamedTag<Item> FENCE_GATES = mcLoc("fence_gates");

        public static final ITag.INamedTag<Item> WOODEN_DOORS = mcLoc("wooden_doors");
        public static final ITag.INamedTag<Item> WOODEN_TRAPDOORS = mcLoc("wooden_trapdoors");
        public static final ITag.INamedTag<Item> STANDING_SIGNS = mcLoc("standing_signs");
        public static final ITag.INamedTag<Item> WALL_SIGNS = mcLoc("wall_signs");

        // Curios Integration
        public static final ITag.INamedTag<Item> CHARM = curiosLoc("charm");
        public static final ITag.INamedTag<Item> NECKLACE = curiosLoc("necklace");

        // Wastelands of Baedoor Integration
        public static final ITag.INamedTag<Item> WOBR_SABRE = forgeLoc("wobr_sabre");

        private static ITag.INamedTag<Item> bioLoc(String path) {
            return ItemTags.bind(new ResourceLocation(Bioplethora.MOD_ID, path).toString());
        }

        private static ITag.INamedTag<Item> mcLoc(String path) {
            return ItemTags.bind(new ResourceLocation("minecraft", path).toString());
        }

        private static ITag.INamedTag<Item> forgeLoc(String path) {
            return ItemTags.bind(new ResourceLocation("forge", path).toString());
        }

        private static ITag.INamedTag<Item> curiosLoc(String path) {
            return ItemTags.bind(new ResourceLocation("curios", path).toString());
        }
    }

    public static final class Entities {

        public static final ITag.INamedTag<EntityType<?>> FLEIGNAR_TARGETS = forgeLoc("cavern_fleignar_targets");

        // Wastelands of Baedoor Integration
        public static final ITag.INamedTag<EntityType<?>> AVOIDER_KILLABLE = forgeLoc("avoider_killable");
        public static final ITag.INamedTag<EntityType<?>> AUTOMATON_TYPE = forgeLoc("automaton_type");

        private static ITag.INamedTag<EntityType<?>> bioLoc(String path) {
            return EntityTypeTags.bind(new ResourceLocation(Bioplethora.MOD_ID, path).toString());
        }

        private static ITag.INamedTag<EntityType<?>> mcLoc(String path) {
            return EntityTypeTags.bind(new ResourceLocation("minecraft", path).toString());
        }

        private static ITag.INamedTag<EntityType<?>> forgeLoc(String path) {
            return EntityTypeTags.bind(new ResourceLocation("forge", path).toString());
        }
    }
}
