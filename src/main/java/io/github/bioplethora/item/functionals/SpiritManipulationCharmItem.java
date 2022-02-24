package io.github.bioplethora.item.functionals;

import io.github.bioplethora.item.ItemSettings;
import io.github.bioplethora.registry.BioplethoraEffects;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class SpiritManipulationCharmItem extends ActivatableItem {

    public SpiritManipulationCharmItem(Properties properties) {
        super(properties);
    }

    @Override
    public void activatedTick(ItemStack pStack, World pLevel, Entity pEntity) {
        super.activatedTick(pStack, pLevel, pEntity);

        if (pEntity instanceof PlayerEntity) {
            ((PlayerEntity) pEntity).addEffect(new EffectInstance(BioplethoraEffects.SPIRIT_MANIPULATION.get(), 10, 0));
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {

        ItemSettings.sacredLevelText(tooltip);

        tooltip.add(new TranslationTextComponent("item.bioplethora.spirit_manipulation_charm.spirit_manipulation.skill").withStyle(ItemSettings.SKILL_NAME_COLOR));
        if (Screen.hasShiftDown() || Screen.hasControlDown()) {
            tooltip.add(new TranslationTextComponent("item.bioplethora.spirit_manipulation_charm.spirit_manipulation.desc").withStyle(ItemSettings.SKILL_DESC_COLOR));
        }
    }
}
