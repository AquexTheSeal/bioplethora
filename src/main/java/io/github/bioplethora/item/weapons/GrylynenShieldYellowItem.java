package io.github.bioplethora.item.weapons;

import io.github.bioplethora.api.BPItemSettings;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class GrylynenShieldYellowItem extends GrylynenShieldBaseItem {

    public GrylynenShieldYellowItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getCooldownReduction() {
        return 40;
    }

    @Override
    public int getArmorBonus() {
        return 4;
    }

    @Override
    public void blockingSkill(ItemStack stack, LivingEntity user, Entity attacker, World world) {
        // Checks if it is a 50% chance
        if (Math.random() <= 0.5) {
            // Regenerates the user's health by 2 hearts
            user.heal(2F);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        BPItemSettings.sacredLevelText(tooltip);

        tooltip.add(new TranslationTextComponent("item.bioplethora.yellow_grylynen_shield.yellow_crystal_energy.skill").withStyle(BPItemSettings.SKILL_NAME_COLOR));
        if (Screen.hasShiftDown() || Screen.hasControlDown()) {
            tooltip.add(new TranslationTextComponent("item.bioplethora.yellow_grylynen_shield.yellow_crystal_energy.desc").withStyle(BPItemSettings.SKILL_DESC_COLOR));
        }
    }
}
