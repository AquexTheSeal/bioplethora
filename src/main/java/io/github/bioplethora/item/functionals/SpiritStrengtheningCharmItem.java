package io.github.bioplethora.item.functionals;

import io.github.bioplethora.blocks.api.BPItemSettings;
import io.github.bioplethora.registry.BPEffects;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class SpiritStrengtheningCharmItem extends ActivatableItem {

    public SpiritStrengtheningCharmItem(Properties properties) {
        super(properties, true);
    }

    @Override
    public void activatedTick(ItemStack pStack, World pLevel, Entity pEntity) {
        super.activatedTick(pStack, pLevel, pEntity);

        if (pEntity instanceof PlayerEntity) {
            ((PlayerEntity) pEntity).addEffect(new EffectInstance(BPEffects.SPIRIT_STRENGTHENING.get(), 10, 0, false, false));
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        BPItemSettings.sacredLevelText(tooltip);

        tooltip.add(new TranslationTextComponent("item.bioplethora.spirit_strengthening_charm.spirit_strengthening.skill").withStyle(BPItemSettings.SKILL_NAME_COLOR));
        if (Screen.hasShiftDown() || Screen.hasControlDown()) {
            tooltip.add(new TranslationTextComponent("item.bioplethora.spirit_strengthening_charm.spirit_strengthening.desc").withStyle(BPItemSettings.SKILL_DESC_COLOR));
        }
    }
}
