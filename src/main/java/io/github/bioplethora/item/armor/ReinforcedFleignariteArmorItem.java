package io.github.bioplethora.item.armor;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.client.armor.model.ReinforcedFleignariteArmorModel;
import io.github.bioplethora.item.IHurtSkillArmor;
import io.github.bioplethora.registry.BPDamageSources;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ReinforcedFleignariteArmorItem extends ArmorItem implements IHurtSkillArmor {

    public ReinforcedFleignariteArmorItem(IArmorMaterial material, EquipmentSlotType type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public void hurtTrigger(LivingEntity user, LivingEntity attacker, ItemStack stack) {
        attacker.playSound(SoundEvents.ANVIL_PLACE, 1.0F, 0.75F);
        if (!attacker.level.isClientSide()) {
            ((ServerWorld) attacker.level).sendParticles(ParticleTypes.FIREWORK, attacker.getX(), attacker.getY(), attacker.getZ(), 75, 0.75, 0.75, 0.75, 0.01);
            ((ServerWorld) attacker.level).sendParticles(ParticleTypes.CRIT, attacker.getX(), attacker.getY(), attacker.getZ(), 75, 0.75, 0.75, 0.75, 0.01);
        }
        attacker.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, attacker.getRandom().nextBoolean() ? 70 : 90, 2, false, false, false));
        attacker.addEffect(new EffectInstance(Effects.DIG_SLOWDOWN, attacker.getRandom().nextBoolean() ? 60 : 80, 0, false, false, false));
        attacker.addEffect(new EffectInstance(Effects.CONFUSION, attacker.getRandom().nextBoolean() ? 80 : 60, 1, false, false, false));

        attacker.knockback(0.5F, MathHelper.sin(user.yRot * ((float) Math.PI / 180F)), -MathHelper.cos(user.yRot * ((float) Math.PI / 180F)));
        attacker.hurt(BPDamageSources.armorPiercingFleignarite(attacker, attacker), (float) 2);

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
