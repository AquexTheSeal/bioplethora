package io.github.bioplethora.enchantments;

import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.item.AxeItem;
import net.minecraft.item.SwordItem;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class BPEnchantmentHelper {

    public static EnchantmentType BP_WEAPON_AND_AXE = EnchantmentType.create("bp_weapon_and_axe", (item) -> (item instanceof SwordItem || item instanceof AxeItem));

    /*@SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {

        LivingEntity pUser = event.player;

        double var1;
        if (EnchantmentHelper.getItemEnchantmentLevel(BioplethoraEnchantments.HONED.get(), pUser.getOffhandItem()) <= EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SHARPNESS, pUser.getMainHandItem())) {
            var1 = EnchantmentHelper.getItemEnchantmentLevel(BioplethoraEnchantments.HONED.get(), pUser.getMainHandItem());
        } else {
            var1 = EnchantmentHelper.getItemEnchantmentLevel(BioplethoraEnchantments.HONED.get(), pUser.getOffhandItem());
        }

        if (pUser.level instanceof ServerWorld) {
            pUser.level.getServer().getCommands().performCommand((new CommandSource(ICommandSource.NULL, new Vector3d(pUser.getX(), pUser.getY(), pUser.getZ()), Vector2f.ZERO, (ServerWorld)
                            pUser.level, 4, "", new StringTextComponent(""), pUser.level.getServer(), null)).withSuppressedOutput(),
                    "attribute @p minecraft:generic.attack_speed base set " + (4.0D + var1 * 0.75));
        }
    }*/
}
