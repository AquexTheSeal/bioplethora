package io.github.bioplethora.item.armor;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.api.world.EffectUtils;
import io.github.bioplethora.api.world.EntityUtils;
import io.github.bioplethora.client.armor.model.ReinforcedFleignariteArmorModel;
import io.github.bioplethora.api.IHurtSkillArmor;
import io.github.bioplethora.registry.BPDamageSources;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class ReinforcedFleignariteArmorItem extends ArmorItem implements IHurtSkillArmor {

    public ReinforcedFleignariteArmorItem(IArmorMaterial material, EquipmentSlotType type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public void hurtTrigger(LivingEntity user, LivingEntity target, ItemStack stack) {
        target.playSound(SoundEvents.ANVIL_PLACE, 1.0F, 0.75F);

        EffectUtils.addCircleParticleForm(target.level, target, ParticleTypes.FIREWORK, 75, 0.75, 0.01);
        EffectUtils.addCircleParticleForm(target.level, target, ParticleTypes.CRIT, 75, 0.75, 0.01);

        boolean random = target.getRandom().nextBoolean();
        EffectUtils.addEffectNoIcon(target, Effects.MOVEMENT_SLOWDOWN, random ? 70 : 90, 2);
        EffectUtils.addEffectNoIcon(target, Effects.DIG_SLOWDOWN, random ? 60 : 80, 0);
        EffectUtils.addEffectNoIcon(target, Effects.CONFUSION, random ? 80 : 60, 1);

        EntityUtils.knockbackAwayFromUser(0.5F, user, target);
        target.hurt(BPDamageSources.armorPiercingFleignarite(target, target), (float) 2);

    }

    @Override
    public void inventoryTick(ItemStack pStack, World pLevel, Entity pEntity, int pItemSlot, boolean pIsSelected) {
        if (pStack.getDamageValue() < pStack.getMaxDamage()) {
            pStack.getOrCreateTag().putInt("regen_time", pStack.getOrCreateTag().getInt("regen_time") + 1);

            if (pStack.getOrCreateTag().getInt("regen_time") == 260) {
                int i = Math.min((int) (2 * pStack.getXpRepairRatio()), pStack.getDamageValue());
                pStack.setDamageValue(pStack.getDamageValue() - i);
                pStack.getOrCreateTag().putInt("regen_time", 0);
            }
        }
        cdHelper(pStack);
        super.inventoryTick(pStack, pLevel, pEntity, pItemSlot, pIsSelected);
    }

    @Override
    public int getHurtAbilityCooldown() {
        return 80;
    }

    @Override
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entity, ItemStack stack, EquipmentSlotType slot, A defaultModel) {
        return slot == EquipmentSlotType.LEGS ? defaultModel : matchingModel(entity, slot, defaultModel);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        String defaultTexture = Bioplethora.MOD_ID + ":textures/models/armor/reinforced_fleignarite_layer_1.png";
        String legTexture = Bioplethora.MOD_ID + ":textures/models/armor/reinforced_fleignarite_layer_2.png";

        return slot == EquipmentSlotType.LEGS ? legTexture : defaultTexture;
    }

    public static <A extends BipedModel<?>> A matchingModel(LivingEntity entity, EquipmentSlotType slot, A defaultModel) {
        boolean crouching = entity.isCrouching();
        boolean riding = defaultModel.riding;
        boolean young = entity.isBaby();

        switch (slot) {
            case HEAD:
                BipedModel helmet = new BipedModel(1);
                helmet.head = new ReinforcedFleignariteArmorModel<>().head;
                helmet.crouching = crouching;
                helmet.riding = riding;
                helmet.young = young;
                return (A) helmet;
            case CHEST:
                BipedModel chestplate = new BipedModel(1);
                chestplate.body = new ReinforcedFleignariteArmorModel<>().body;
                chestplate.leftArm = new ReinforcedFleignariteArmorModel<>().left_arm;
                chestplate.rightArm = new ReinforcedFleignariteArmorModel<>().right_arm;
                chestplate.crouching = crouching;
                chestplate.riding = riding;
                chestplate.young = young;
                return (A) chestplate;
            case FEET:
                BipedModel boots = new BipedModel(1);
                boots.leftLeg = new ReinforcedFleignariteArmorModel<>().left_shoe;
                boots.rightLeg = new ReinforcedFleignariteArmorModel<>().right_shoe;
                boots.crouching = crouching;
                boots.riding = riding;
                boots.young = young;
                return (A) boots;
        }
        return defaultModel;
    }
}
