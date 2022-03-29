package io.github.bioplethora.item.armor;

import io.github.bioplethora.item.IHurtSkillArmor;
import io.github.bioplethora.item.ItemSettings;
import io.github.bioplethora.registry.BPDamageSources;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class FleignariteArmorItem extends ArmorItem implements IHurtSkillArmor {

    public FleignariteArmorItem(IArmorMaterial material, EquipmentSlotType type, Item.Properties properties) {
        super(material, type, properties);
    }

    @Override
    public void hurtTrigger(LivingEntity user, LivingEntity attacker, ItemStack stack) {
        attacker.playSound(SoundEvents.SLIME_BLOCK_BREAK, 1.0F, 1.0F);
        attacker.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, attacker.getRandom().nextBoolean() ? 40 : 60, 1, false, false, false));
        attacker.addEffect(new EffectInstance(Effects.CONFUSION, attacker.getRandom().nextBoolean() ? 60 : 40, 0, false, false, false));

        attacker.hurt(BPDamageSources.armorPiercingFleignarite(attacker, attacker), (float) 2);
    }

    @Override
    public int getHurtAbilityCooldown() {
        return 120;
    }

    @Override
    public void inventoryTick(ItemStack pStack, World pLevel, Entity pEntity, int pItemSlot, boolean pIsSelected) {
        if (pStack.getDamageValue() < pStack.getMaxDamage()) {
            pStack.getOrCreateTag().putInt("regen_time", pStack.getOrCreateTag().getInt("regen_time") + 1);

            if (pStack.getOrCreateTag().getInt("regen_time") == 120) {
                int i = Math.min((int) (2 * pStack.getXpRepairRatio()), pStack.getDamageValue());
                pStack.setDamageValue(pStack.getDamageValue() - i);
                pStack.getOrCreateTag().putInt("regen_time", 0);
            }
        }
        cdHelper(pStack);
        super.inventoryTick(pStack, pLevel, pEntity, pItemSlot, pIsSelected);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable World pLevel, List<ITextComponent> pTooltip, ITooltipFlag pFlag) {
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);

        ItemSettings.sacredLevelText(pTooltip);

        pTooltip.add(new TranslationTextComponent("item.bioplethora.fleignarite_armor.sticky_piece.skill").withStyle(ItemSettings.SKILL_NAME_COLOR));
        if (Screen.hasShiftDown() || Screen.hasControlDown()) {
            pTooltip.add(new TranslationTextComponent("item.bioplethora.fleignarite_armor.sticky_piece.desc").withStyle(ItemSettings.SKILL_DESC_COLOR));
        }
    }
}
