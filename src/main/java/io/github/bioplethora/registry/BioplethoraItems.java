package io.github.bioplethora.registry;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.items.BioplethoraSpawnEggItem;
import io.github.bioplethora.items.weapons.BellophgolemShieldItem;
import io.github.bioplethora.items.weapons.CrephoxlHammerItem;
import io.github.bioplethora.util.BioplethoraItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
import net.minecraft.item.Rarity;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BioplethoraItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Bioplethora.MOD_ID);

    public static final RegistryObject<Item> CREPHOXL_FEATHER = ITEMS.register("crephoxl_feather", () -> new Item(new Item.Properties().stacksTo(64).tab(BioplethoraItemGroup.BioplethoraItemItemGroup)));
    public static final RegistryObject<Item> CREPHOXL_STICK = ITEMS.register("crephoxl_stick", () -> new Item(new Item.Properties().stacksTo(64).tab(BioplethoraItemGroup.BioplethoraItemItemGroup)));
    public static final RegistryObject<Item> NANDBRI_SCALES = ITEMS.register("nandbri_scales", () -> new Item(new Item.Properties().stacksTo(64).tab(BioplethoraItemGroup.BioplethoraItemItemGroup)));

    public static final RegistryObject<Item> CREPHOXL_HAMMER = ITEMS.register("crephoxl_hammer", () -> new CrephoxlHammerItem(ItemTier.NETHERITE, 12-5, -3.30f,
            new Item.Properties().rarity(Rarity.RARE).durability(4508).tab(BioplethoraItemGroup.BioplethoraItemItemGroup)));
    public static final RegistryObject<Item> BELLOPHGOLEM_SHIELD = ITEMS.register("bellophgolem_shield", () -> new BellophgolemShieldItem(new Item.Properties().rarity(Rarity.RARE).durability(125000).tab(BioplethoraItemGroup.BioplethoraItemItemGroup)));

    public static final RegistryObject<Item> CREPHOXL_SPAWN_EGG = ITEMS.register("crephoxl_spawn_egg", () -> new BioplethoraSpawnEggItem(BioplethoraEntities.CREPHOXL, 0xFFFFFF, 0xFF0000, new Item.Properties().tab(BioplethoraItemGroup.BioplethoraSpawnEggsItemGroup)));
    public static final RegistryObject<Item> ALPHEM_SPAWN_EGG = ITEMS.register("alphem_spawn_egg", () -> new BioplethoraSpawnEggItem(BioplethoraEntities.ALPHEM, 0xFFFFFF, 0xFF0000, new Item.Properties().tab(BioplethoraItemGroup.BioplethoraSpawnEggsItemGroup)));
    public static final RegistryObject<Item> NANDBRI_SPAWN_EGG = ITEMS.register("nandbri_spawn_egg", () -> new BioplethoraSpawnEggItem(BioplethoraEntities.NANDBRI, 0xFFFFFF, 0xFF0000, new Item.Properties().tab(BioplethoraItemGroup.BioplethoraSpawnEggsItemGroup)));
    public static final RegistryObject<Item> BELLOPHGOLEM_SPAWN_EGG = ITEMS.register("bellophgolem_spawn_egg", () -> new BioplethoraSpawnEggItem(BioplethoraEntities.BELLOPHGOLEM, 0xFFFFFF, 0xFF0000, new Item.Properties().tab(BioplethoraItemGroup.BioplethoraSpawnEggsItemGroup)));
}
