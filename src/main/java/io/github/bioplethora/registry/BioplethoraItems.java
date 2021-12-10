package io.github.bioplethora.registry;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.item.BiopediaItem;
import io.github.bioplethora.item.BioplethoraSpawnEggItem;
import io.github.bioplethora.item.weapons.*;
import io.github.bioplethora.util.BioplethoraItemGroup;
import io.github.bioplethora.util.CustomRarityTypes;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
import net.minecraft.item.Rarity;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BioplethoraItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Bioplethora.MOD_ID);

    public static final RegistryObject<Item> BIOPEDIA = ITEMS.register("biopedia", () -> new BiopediaItem(new Item.Properties().fireResistant().rarity(Rarity.RARE).stacksTo(1).tab(BioplethoraItemGroup.BioplethoraItemItemGroup)));
    public static final RegistryObject<Item> TEST_ITEM = ITEMS.register("test_item", () -> new ExperimentalItem(new Item.Properties().fireResistant().rarity(CustomRarityTypes.BOSS_WEAPON).stacksTo(1).tab(BioplethoraItemGroup.BioplethoraItemItemGroup)));

    // Non-boss drops/items
    public static final RegistryObject<Item> CREPHOXL_FEATHER = ITEMS.register("crephoxl_feather", () -> new Item(new Item.Properties().stacksTo(64).tab(BioplethoraItemGroup.BioplethoraItemItemGroup)));
    public static final RegistryObject<Item> CREPHOXL_STICK = ITEMS.register("crephoxl_stick", () -> new Item(new Item.Properties().stacksTo(64).tab(BioplethoraItemGroup.BioplethoraItemItemGroup)));
    public static final RegistryObject<Item> NANDBRI_SCALES = ITEMS.register("nandbri_scales", () -> new Item(new Item.Properties().stacksTo(64).tab(BioplethoraItemGroup.BioplethoraItemItemGroup)));
    public static final RegistryObject<Item> BELLOPHITE = ITEMS.register("bellophite", () -> new Item(new Item.Properties().stacksTo(64).tab(BioplethoraItemGroup.BioplethoraItemItemGroup)));

    // Non-boss Weapons
    public static final RegistryObject<Item> CREPHOXL_HAMMER = ITEMS.register("crephoxl_hammer", () -> new CrephoxlHammerItem(ItemTier.NETHERITE, 12-5, -3.30f,
            new Item.Properties().fireResistant().rarity(CustomRarityTypes.SACRED).durability(4508).tab(BioplethoraItemGroup.BioplethoraItemItemGroup)));
    public static final RegistryObject<Item> BELLOPHGOLEM_SHIELD = ITEMS.register("bellophgolem_shield", () -> new BellophgolemShieldItem(new Item.Properties().fireResistant().rarity(CustomRarityTypes.SACRED).durability(5000).tab(BioplethoraItemGroup.BioplethoraItemItemGroup)));
    public static final RegistryObject<Item> BELLOPHITE_ARROW = ITEMS.register("bellophite_arrow", () -> new BellophiteArrowItem(new Item.Properties().fireResistant().rarity(CustomRarityTypes.SACRED).tab(BioplethoraItemGroup.BioplethoraItemItemGroup)));
    public static final RegistryObject<Item> STELLAR_SCYTHE = ITEMS.register("stellar_scythe", () -> new StellarScytheItem(ItemTier.NETHERITE, 11-5, -2.5f,
            new Item.Properties().fireResistant().rarity(CustomRarityTypes.SACRED).durability(4508).tab(BioplethoraItemGroup.BioplethoraItemItemGroup)));

    // Spawn Eggs
    public static final RegistryObject<Item> CREPHOXL_SPAWN_EGG = ITEMS.register("crephoxl_spawn_egg", () -> new BioplethoraSpawnEggItem(BioplethoraEntities.CREPHOXL, 0xFFFFFF, 0xFFFFFF, new Item.Properties().tab(BioplethoraItemGroup.BioplethoraSpawnEggsItemGroup)));
    public static final RegistryObject<Item> ALPHEM_SPAWN_EGG = ITEMS.register("alphem_spawn_egg", () -> new BioplethoraSpawnEggItem(BioplethoraEntities.ALPHEM, 0xFFFFFF, 0xFFFFFF, new Item.Properties().tab(BioplethoraItemGroup.BioplethoraSpawnEggsItemGroup)));
    public static final RegistryObject<Item> NANDBRI_SPAWN_EGG = ITEMS.register("nandbri_spawn_egg", () -> new BioplethoraSpawnEggItem(BioplethoraEntities.NANDBRI, 0xFFFFFF, 0xFFFFFF, new Item.Properties().tab(BioplethoraItemGroup.BioplethoraSpawnEggsItemGroup)));
    public static final RegistryObject<Item> BELLOPHGOLEM_SPAWN_EGG = ITEMS.register("bellophgolem_spawn_egg", () -> new BioplethoraSpawnEggItem(BioplethoraEntities.BELLOPHGOLEM, 0xFFFFFF, 0xFFFFFF, new Item.Properties().tab(BioplethoraItemGroup.BioplethoraSpawnEggsItemGroup)));
    public static final RegistryObject<Item> PEAGUIN_SPAWN_EGG = ITEMS.register("peaguin_spawn_egg", () -> new BioplethoraSpawnEggItem(BioplethoraEntities.PEAGUIN, 0xFFFFFF, 0xFFFFFF, new Item.Properties().tab(BioplethoraItemGroup.BioplethoraSpawnEggsItemGroup)));
    public static final RegistryObject<Item> CUTTLEFISH_SPAWN_EGG = ITEMS.register("cuttlefish_spawn_egg", () -> new BioplethoraSpawnEggItem(BioplethoraEntities.CUTTLEFISH, 0xFFFFFF, 0xFFFFFF, new Item.Properties().tab(BioplethoraItemGroup.BioplethoraSpawnEggsItemGroup)));

    // Block Items
    public static final RegistryObject<Item> NANDBRI_SCALE_BLOCK = ITEMS.register("nandbri_scale_block", () -> new BlockItem(BioplethoraBlocks.NANDBRI_SCALE_BLOCK.get(), new Item.Properties().tab(BioplethoraItemGroup.BioplethoraItemItemGroup)));
}
