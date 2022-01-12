package io.github.bioplethora.registry;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.enchantments.AntibioEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BioplethoraEnchantments {

    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, Bioplethora.MOD_ID);

    public static final RegistryObject<Enchantment> ECOHARMLESS = ENCHANTMENTS.register("antibio_ecoharmless", () -> new AntibioEnchantment(Enchantment.Rarity.UNCOMMON, EquipmentSlotType.MAINHAND));
    public static final RegistryObject<Enchantment> PLETHONEUTRAL = ENCHANTMENTS.register("antibio_plethoneutral", () -> new AntibioEnchantment(Enchantment.Rarity.UNCOMMON, EquipmentSlotType.MAINHAND));
    public static final RegistryObject<Enchantment> DANGERUM = ENCHANTMENTS.register("antibio_dangerum", () -> new AntibioEnchantment(Enchantment.Rarity.RARE, EquipmentSlotType.MAINHAND));
    public static final RegistryObject<Enchantment> HELLSENT = ENCHANTMENTS.register("antibio_hellsent", () -> new AntibioEnchantment(Enchantment.Rarity.RARE, EquipmentSlotType.MAINHAND));
    public static final RegistryObject<Enchantment> ELDERIA = ENCHANTMENTS.register("antibio_elderia", () -> new AntibioEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlotType.MAINHAND));
}
