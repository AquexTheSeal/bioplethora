package io.github.bioplethora.enchantments;

import io.github.bioplethora.item.weapons.AlphanumObliteratorItem;
import io.github.bioplethora.item.weapons.GaidiusBaseItem;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.item.AxeItem;
import net.minecraft.item.SwordItem;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class BPEnchantmentHelper {

    public static final EnchantmentType BP_WEAPON_AND_AXE = EnchantmentType.create("bp_weapon_and_axe", (item) -> (item instanceof SwordItem || item instanceof AxeItem));
    public static final EnchantmentType ALPHANUM_OBLITERATOR = EnchantmentType.create("alphanum_obliterator", (item) -> item instanceof AlphanumObliteratorItem);
    public static final EnchantmentType GAIDIUS = EnchantmentType.create("gaidius", (item) -> item instanceof GaidiusBaseItem);
}
