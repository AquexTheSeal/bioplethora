package io.github.bioplethora.item.weapons;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import io.github.bioplethora.BPConfig;
import io.github.bioplethora.entity.projectile.AlphanumObliteratorSpearEntity;
import io.github.bioplethora.item.ItemSettings;
import io.github.bioplethora.registry.BPEnchantments;
import io.github.bioplethora.registry.BPItems;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class AlphanumObliteratorItem extends Item implements IVanishable {
    private final Multimap<Attribute, AttributeModifier> defaultModifiers;

    public AlphanumObliteratorItem(Properties properties) {
        super(properties);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", 13.0D, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", -2.6F, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
    }

    public UseAction getUseAnimation(ItemStack pStack) {
        return UseAction.BOW;
    }

    public int getUseDuration(ItemStack pStack) {
        return 72000;
    }

    public void initiateShoot(ItemStack pStack, World pLevel, LivingEntity pEntityLiving) {

        double x = pEntityLiving.getX(), y = pEntityLiving.getY(), z = pEntityLiving.getZ();
        if (!pLevel.isClientSide()) {
            ((ServerWorld) pLevel).sendParticles(ParticleTypes.FIREWORK, x, y + 1.2, z, 50, 0.75, 0.75, 0.75, 0.01);
        }

        if (pEntityLiving.getMainHandItem() == pStack) {
            pStack.hurtAndBreak(1, pEntityLiving, (entity) -> {
                entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
            });
        } else if (pEntityLiving.getOffhandItem() == pStack) {
            pStack.hurtAndBreak(1, pEntityLiving, (entity) -> {
                entity.broadcastBreakEvent(EquipmentSlotType.OFFHAND);
            });
        }
        pLevel.playSound(null, x, y, z, SoundEvents.FIREWORK_ROCKET_LAUNCH, SoundCategory.PLAYERS, 1, 1);
        this.shootProjectile(pStack, pLevel, pEntityLiving);
    }

    public void shootProjectile(ItemStack pStack, World pLevel, LivingEntity pEntityLiving) {
        float xTarget = -MathHelper.sin(pEntityLiving.yHeadRot * ((float) Math.PI / 180F)) * MathHelper.cos(pEntityLiving.xRot * ((float) Math.PI / 180F));
        float yTarget = -MathHelper.sin(pEntityLiving.xRot * ((float) Math.PI / 180F));
        float zTarget = MathHelper.cos(pEntityLiving.yHeadRot * ((float) Math.PI / 180F)) * MathHelper.cos(pEntityLiving.xRot * ((float) Math.PI / 180F));
        float damageAdditions = (float) EnchantmentHelper.getItemEnchantmentLevel(BPEnchantments.DEVASTATING_BLAST.get(), pStack) * 1.5F;

        AlphanumObliteratorSpearEntity projectile = new AlphanumObliteratorSpearEntity(pLevel, pEntityLiving, xTarget, yTarget, zTarget);
        projectile.setPos(pEntityLiving.getX(), pEntityLiving.getY() + 1.5, pEntityLiving.getZ());
        projectile.setBaseDamage((BPConfig.getHellMode ? 15.0F : 12.0F) + damageAdditions);
        projectile.shootFromRotation(projectile,
                pEntityLiving.xRot,
                pEntityLiving.yHeadRot,
                0, 1F, 0);
        pLevel.addFreshEntity(projectile);

        AlphanumObliteratorSpearEntity projectile1 = new AlphanumObliteratorSpearEntity(pLevel, pEntityLiving, xTarget, yTarget, zTarget);
        projectile1.setPos(pEntityLiving.getX(), pEntityLiving.getY() + 1.5, pEntityLiving.getZ());
        projectile1.setBaseDamage((BPConfig.getHellMode ? 15.0F : 12.0F) + damageAdditions);
        projectile1.shootFromRotation(projectile1,
                pEntityLiving.xRot,
                pEntityLiving.yHeadRot,
                0, 1F, 1.0F);
        pLevel.addFreshEntity(projectile1);

        AlphanumObliteratorSpearEntity projectile2 = new AlphanumObliteratorSpearEntity(pLevel, pEntityLiving, xTarget, yTarget, zTarget);
        projectile2.setPos(pEntityLiving.getX(), pEntityLiving.getY() + 1.5, pEntityLiving.getZ());
        projectile2.setBaseDamage((BPConfig.getHellMode ? 15.0F : 12.0F) + damageAdditions);
        projectile2.shootFromRotation(projectile2,
                pEntityLiving.xRot,
                pEntityLiving.yHeadRot,
                0, 1F, 1.0F);
        pLevel.addFreshEntity(projectile2);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int pItemSlot, boolean pIsSelected) {
        super.inventoryTick(stack, world, entity, pItemSlot, pIsSelected);
        CompoundNBT getTag = stack.getOrCreateTag();

        int reloadTime = getTag.getInt("reloadTime");
        int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.QUICK_CHARGE, stack);
        int quickChargeBonus = i <= 3 ? i * 10 : 30;

        if (entity instanceof LivingEntity) {
            if (!isCharged(stack)) {
                getTag.putInt("reloadTime", reloadTime + 1);

                if (reloadTime == 40 - quickChargeBonus) {
                    setCharged(stack, true);
                    getTag.putInt("reloadTime", 0);

                    entity.playSound(SoundEvents.CROSSBOW_LOADING_END, 1.0F,  1.0F);
                    if (((LivingEntity) entity).getMainHandItem() == stack) {
                        ((LivingEntity) entity).swing(Hand.MAIN_HAND);
                    }
                    if (((LivingEntity) entity).getOffhandItem() == stack) {
                        ((LivingEntity) entity).swing(Hand.OFF_HAND);
                    }
                }
            }
        }

        int offHandComboTimer = getTag.getInt("offComboTimer");

        if (entity instanceof LivingEntity) {
            if (getTag.getBoolean("shouldOffCombo")) {
                getTag.putInt("offComboTimer", offHandComboTimer + 1);

                if (offHandComboTimer >= 5) {
                    this.initiateShoot(stack, world, (LivingEntity) entity);
                    ((LivingEntity) entity).swing(Hand.OFF_HAND);
                    setCharged(stack, false);
                    getTag.putBoolean("shouldOffCombo", false);
                    getTag.putInt("offComboTimer", 0);
                }
            }
        }
    }

    public void releaseUsing(ItemStack pStack, World pLevel, LivingEntity pEntityLiving, int pTimeLeft) {
        if (pEntityLiving instanceof PlayerEntity) {

            PlayerEntity player = (PlayerEntity) pEntityLiving;
            CompoundNBT getOffTag = pEntityLiving.getOffhandItem().getOrCreateTag();
            int i = this.getUseDuration(pStack) - pTimeLeft;

            if (i >= 10 && isCharged(pStack)) {

                if (pEntityLiving.getMainHandItem() == pStack) {
                    this.initiateShoot(pStack, pLevel, pEntityLiving);

                    if (pEntityLiving.getOffhandItem().getItem() == BPItems.ALPHANUM_OBLITERATOR.get()) {
                        if (isCharged(pEntityLiving.getOffhandItem())) {
                            getOffTag.putBoolean("shouldOffCombo", true);
                        }
                    }
                } else {
                    this.initiateShoot(pStack, pLevel, pEntityLiving);
                }

                setCharged(pStack, false);
            }
        }
    }

    public static boolean isCharged(ItemStack obStack) {
        CompoundNBT compoundnbt = obStack.getTag();
        return compoundnbt != null && compoundnbt.getBoolean("Charged");
    }

    public static void setCharged(ItemStack obStack, boolean pIsCharged) {
        CompoundNBT compoundnbt = obStack.getOrCreateTag();
        compoundnbt.putBoolean("Charged", pIsCharged);
    }

    public ActionResult<ItemStack> use(World pLevel, PlayerEntity pPlayer, Hand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);

        if (!isCharged(pPlayer.getItemInHand(pHand))) {
            return ActionResult.fail(itemstack);

        } else {
            pPlayer.startUsingItem(pHand);
            return ActionResult.consume(itemstack);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        ItemSettings.sacredLevelText(tooltip);

        tooltip.add(new TranslationTextComponent("item.bioplethora.alphanum_obliterator.skullbreaker.skill").withStyle(ItemSettings.SKILL_NAME_COLOR));
        if (Screen.hasShiftDown() || Screen.hasControlDown()) {
            tooltip.add(new TranslationTextComponent("item.bioplethora.alphanum_obliterator.skullbreaker.desc").withStyle(ItemSettings.SKILL_DESC_COLOR));
        }

        tooltip.add(new TranslationTextComponent("item.bioplethora.alphanum_obliterator.blasting_spears.skill").withStyle(ItemSettings.SKILL_NAME_COLOR));
        if (Screen.hasShiftDown() || Screen.hasControlDown()) {
            tooltip.add(new TranslationTextComponent("item.bioplethora.alphanum_obliterator.blasting_spears.desc").withStyle(ItemSettings.SKILL_DESC_COLOR));
        }
    }

    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        pStack.hurtAndBreak(1, pAttacker, (entity) -> {
            entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
        });
        pTarget.knockback(2, MathHelper.sin(pAttacker.yRot * ((float) Math.PI / 180F)), -MathHelper.cos(pAttacker.yRot * ((float) Math.PI / 180F)));
        pTarget.playSound(SoundEvents.ANVIL_PLACE, 1.0F, 0.75F);
        if (!pTarget.level.isClientSide()) {
            ((ServerWorld) pTarget.level).sendParticles(ParticleTypes.FIREWORK, pTarget.getX(), pTarget.getY(), pTarget.getZ(), 75, 0.75, 0.75, 0.75, 0.01);
            ((ServerWorld) pTarget.level).sendParticles(ParticleTypes.CRIT, pTarget.getX(), pTarget.getY(), pTarget.getZ(), 75, 0.75, 0.75, 0.75, 0.01);
        }
        return true;
    }

    public boolean mineBlock(ItemStack pStack, World pLevel, BlockState pState, BlockPos pPos, LivingEntity pEntityLiving) {
        if ((double)pState.getDestroySpeed(pLevel, pPos) != 0.0D) {
            pStack.hurtAndBreak(2, pEntityLiving, (entity) -> {
                entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
            });
        }

        return true;
    }

    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlotType pEquipmentSlot) {
        return pEquipmentSlot == EquipmentSlotType.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(pEquipmentSlot);
    }

    public int getEnchantmentValue() {
        return 1;
    }
}