package io.github.bioplethora.registry;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.enchantments.AntibioEnchantment;
import io.github.bioplethora.enchantments.DevastatingBlastEnchantment;
import io.github.bioplethora.enums.BPEntityClasses;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = Bioplethora.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BPEnchantments {

    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, Bioplethora.MOD_ID);

    public static final RegistryObject<Enchantment> ANTIBIO_ECOHARMLESS = ENCHANTMENTS.register("antibio_ecoharmless", () ->
            new AntibioEnchantment(Enchantment.Rarity.UNCOMMON, BPEntityClasses.ECOHARMLESS, EquipmentSlotType.MAINHAND));
    public static final RegistryObject<Enchantment> ANTIBIO_PLETHONEUTRAL = ENCHANTMENTS.register("antibio_plethoneutral", () ->
            new AntibioEnchantment(Enchantment.Rarity.UNCOMMON, BPEntityClasses.PLETHONEUTRAL, EquipmentSlotType.MAINHAND));
    public static final RegistryObject<Enchantment> ANTIBIO_DANGERUM = ENCHANTMENTS.register("antibio_dangerum", () ->
            new AntibioEnchantment(Enchantment.Rarity.RARE, BPEntityClasses.DANGERUM, EquipmentSlotType.MAINHAND));
    public static final RegistryObject<Enchantment> ANTIBIO_HELLSENT = ENCHANTMENTS.register("antibio_hellsent", () ->
            new AntibioEnchantment(Enchantment.Rarity.RARE, BPEntityClasses.HELLSENT, EquipmentSlotType.MAINHAND));
    public static final RegistryObject<Enchantment> ANTIBIO_ELDERIA = ENCHANTMENTS.register("antibio_elderia", () ->
            new AntibioEnchantment(Enchantment.Rarity.VERY_RARE, BPEntityClasses.ELDERIA, EquipmentSlotType.MAINHAND));

    public static final RegistryObject<Enchantment> DEVASTATING_BLAST = ENCHANTMENTS.register("devastating_blast", () ->
            new DevastatingBlastEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlotType.MAINHAND, EquipmentSlotType.OFFHAND));

    //public static final RegistryObject<Enchantment> HONED = ENCHANTMENTS.register("honed", () -> new HonedEnchantment(Enchantment.Rarity.UNCOMMON, EquipmentSlotType.MAINHAND));

}
