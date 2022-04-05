package io.github.bioplethora.enums;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.registry.BPItems;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

public enum BPArmorMaterials implements IArmorMaterial {

    FLEIGNARITE("fleignarite", 18, new int[]{2, 5, 6, 2}, 12, SoundEvents.ARMOR_EQUIP_ELYTRA, 1.0F, 0.0F, () -> {
        return Ingredient.of(BPItems.FLEIGNARITE_SCALES.get());
    }),
    REINFORCED_FLEIGNARITE("reinforced_fleignarite", 30, new int[]{5, 9, 8, 4}, 22, SoundEvents.ARMOR_EQUIP_NETHERITE, 4.0F, 0.12F, () -> {
        return Ingredient.of(BPItems.REINFORCED_FLEIGNARITE.get());
    }),
    NANDBRIC("nandbric", 27, new int[]{3, 5, 6, 3}, 18, SoundEvents.ARMOR_EQUIP_ELYTRA, 1.0F, 0.03F, () -> {
        return Ingredient.of(BPItems.NANDBRI_SCALES.get());
    }),
    PEAGUIN_SCALES("peaguin_scales", 17, new int[]{2, 5, 6, 2}, 15, SoundEvents.ARMOR_EQUIP_TURTLE, 0.0F, 0.0F, () -> {
        return Ingredient.of(BPItems.PEAGUIN_SCALES.get());
    }),

    // Aqu's personal chestplate. Don't mind it.
    AQU("aqu", 78, new int[]{12, 20, 17, 12}, 79, SoundEvents.END_PORTAL_FRAME_FILL, 10.0F, 2.0F, () -> {
        return Ingredient.of(Items.CHAIN_COMMAND_BLOCK);
    });

    private final int[] MAX_DAMAGE_ARRAY = new int[] { 13, 15, 16, 11 };
    private final String name;
    private final int durability;
    private final int[] damageReductionAmountArray;
    private final int enchantability;
    private final SoundEvent soundOnEquip;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairMaterial;

    BPArmorMaterials(String nameIn, int durabilityIn, int[] damageReductionAmountArrayIn, int enchantabilityIn, SoundEvent soundOnEquip, float toughnessIn, float knockbackResistanceIn, Supplier<Ingredient> repairMaterialIn) {
        this.name = Bioplethora.MOD_ID + ":" + nameIn;
        this.durability = durabilityIn;
        this.damageReductionAmountArray = damageReductionAmountArrayIn;
        this.enchantability = enchantabilityIn;
        this.soundOnEquip = soundOnEquip;
        this.toughness = toughnessIn;
        this.knockbackResistance = knockbackResistanceIn;
        this.repairMaterial = repairMaterialIn;
    }

    @OnlyIn(Dist.CLIENT)
    public String getName() {
        return this.name;
    }

    @Override
    public int getDurabilityForSlot(EquipmentSlotType slotIn) {
        return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * this.durability;
    }

    @Override
    public int getDefenseForSlot(EquipmentSlotType slotIn) {
        return this.damageReductionAmountArray[slotIn.getIndex()];
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantability;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.soundOnEquip;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairMaterial.get();
    }
}
