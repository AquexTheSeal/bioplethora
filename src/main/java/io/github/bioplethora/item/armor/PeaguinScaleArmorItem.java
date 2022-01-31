package io.github.bioplethora.item.armor;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class PeaguinScaleArmorItem extends ArmorItem {

    public PeaguinScaleArmorItem(IArmorMaterial material, EquipmentSlotType slotType, Properties properties) {
        super(material, slotType, properties);
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        super.onArmorTick(stack, world, player);
        if (player.isInWater()) {
            player.addEffect(new EffectInstance(Effects.DOLPHINS_GRACE, 5, 1));
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("item.bioplethora.sacred_level.desc").withStyle(TextFormatting.AQUA));
        tooltip.add(new TranslationTextComponent("item.bioplethora.shift_reminder.desc").withStyle(TextFormatting.GRAY));

        tooltip.add(new TranslationTextComponent("item.bioplethora.peaguin_scale_armor.grace_embrace.skill").withStyle(TextFormatting.GOLD));
        if (Screen.hasShiftDown() || Screen.hasControlDown()) {
            tooltip.add(new TranslationTextComponent("item.bioplethora.peaguin_scale_armor.grace_embrace.desc").withStyle(TextFormatting.GRAY));
        }
    }
}
