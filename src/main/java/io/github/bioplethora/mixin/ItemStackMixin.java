package io.github.bioplethora.mixin;

import com.google.common.collect.Lists;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.text.ITextComponent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

//@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    /*@Shadow*/ public abstract Item getItem();

    //@Inject(at = @At("HEAD"), method = "getTooltipLines")
    public void getTooltipLines(PlayerEntity player, ITooltipFlag tooltipFlag, CallbackInfoReturnable<List<ITextComponent>> cir) {

        List<ITextComponent> listMX = Lists.newArrayList();

        /*if ((getItem() instanceof ToolItem) && (player != null)) {
            if (EnchantmentHelper.getEnchantmentLevel(BioplethoraEnchantments.ECOHARMLESS.get(), player) != 0) {
                listMX.add(new TranslationTextComponent("+" + (EnchantmentHelper.getEnchantmentLevel(BioplethoraEnchantments.ECOHARMLESS.get(), player) != 0)
                        + " " + "tooltip.bioplethora.enchantment_ecoharmless_bonus.text").withStyle(TextFormatting.YELLOW));
            }
            if (EnchantmentHelper.getEnchantmentLevel(BioplethoraEnchantments.PLETHONEUTRAL.get(), player) != 0) {
                listMX.add(new TranslationTextComponent("+" + (EnchantmentHelper.getEnchantmentLevel(BioplethoraEnchantments.PLETHONEUTRAL.get(), player) != 0)
                        + " " + "tooltip.bioplethora.enchantment_plethoneutral_bonus.text").withStyle(TextFormatting.YELLOW));
            }
            if (EnchantmentHelper.getEnchantmentLevel(BioplethoraEnchantments.DANGERUM.get(), player) != 0) {
                listMX.add(new TranslationTextComponent("+" + (EnchantmentHelper.getEnchantmentLevel(BioplethoraEnchantments.DANGERUM.get(), player) != 0)
                        + " " + "tooltip.bioplethora.enchantment_dangerum_bonus.text").withStyle(TextFormatting.YELLOW));
            }
            if (EnchantmentHelper.getEnchantmentLevel(BioplethoraEnchantments.HELLSENT.get(), player) != 0) {
                listMX.add(new TranslationTextComponent("+" + (EnchantmentHelper.getEnchantmentLevel(BioplethoraEnchantments.HELLSENT.get(),player) != 0)
                        + " " + "tooltip.bioplethora.enchantment_hellsent_bonus.text").withStyle(TextFormatting.YELLOW));
            }
            if (EnchantmentHelper.getEnchantmentLevel(BioplethoraEnchantments.ELDERIA.get(), player) != 0) {
                listMX.add(new TranslationTextComponent("+" + (EnchantmentHelper.getEnchantmentLevel(BioplethoraEnchantments.ELDERIA.get(), player) != 0)
                        + " " + "tooltip.bioplethora.enchantment_elderia_bonus.text").withStyle(TextFormatting.YELLOW));
            }
        }*/
    }
}
