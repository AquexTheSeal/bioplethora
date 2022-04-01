package io.github.bioplethora.item.armor;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.client.armor.model.NandbricArmorModel;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class NandbricArmorItem extends ArmorItem {
    public NandbricArmorItem(IArmorMaterial material, EquipmentSlotType slot, Properties properties) {
        super(material, slot, properties);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entity, ItemStack stack, EquipmentSlotType slot, A defaultModel) {
        return slot == EquipmentSlotType.LEGS ? defaultModel : matchingModel(entity, slot, defaultModel);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slotType, String type) {
        String defaultTexture = Bioplethora.MOD_ID + ":textures/models/armor/nandbric_layer_1.png";
        String legTexture = Bioplethora.MOD_ID + ":textures/models/armor/nandbric_layer_2.png";

        return slot == EquipmentSlotType.LEGS ? legTexture : defaultTexture;
    }

    public static <A extends BipedModel<?>> A matchingModel(LivingEntity entity, EquipmentSlotType slot, A defaultModel) {
        boolean crouching = entity.isCrouching();
        boolean riding = defaultModel.riding;
        boolean young = entity.isBaby();

        switch (slot) {
            case HEAD:
                BipedModel helmet = new BipedModel(1);
                helmet.head = new NandbricArmorModel<>().helmet;
                helmet.crouching = crouching;
                helmet.riding = riding;
                helmet.young = young;
                return (A) helmet;
            case CHEST:
                BipedModel chestplate = new BipedModel(1);
                chestplate.body = new NandbricArmorModel<>().chestplate;
                chestplate.leftArm = new NandbricArmorModel<>().leftarm;
                chestplate.rightArm = new NandbricArmorModel<>().rightarm;
                chestplate.crouching = crouching;
                chestplate.riding = riding;
                chestplate.young = young;
                return (A) chestplate;
            case FEET:
                BipedModel boots = new BipedModel(1);
                boots.leftLeg = new NandbricArmorModel<>().leftboot;
                boots.rightLeg = new NandbricArmorModel<>().rightboot;
                boots.crouching = crouching;
                boots.riding = riding;
                boots.young = young;
                return (A) boots;
        }
        return defaultModel;
    }
}