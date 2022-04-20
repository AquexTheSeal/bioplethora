package io.github.bioplethora.item.weapons;

import io.github.bioplethora.api.world.EffectUtils;
import io.github.bioplethora.api.world.EntityUtils;
import io.github.bioplethora.api.world.ItemUtils;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class InfernalQuarterstaffItem extends SwordItem {

    LivingEntity markedEntity;

    public InfernalQuarterstaffItem(IItemTier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {

        pTarget.playSound(SoundEvents.GENERIC_BURN, 1.0F, 1.0F);
        EffectUtils.addCircleParticleForm(pTarget.level, pTarget, ParticleTypes.FLAME, 15, 1.5, 0);
        pTarget.setSecondsOnFire(7);
        setMarkedEntity(pTarget);
        setReversed(pStack, true);

        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    public void reverseAttack(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {

        int shr = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SHARPNESS, pStack);
        List<LivingEntity> aabb = pTarget.level.getEntitiesOfClass(
                LivingEntity.class, pAttacker.getBoundingBox().inflate(6, 5, 6), EntityPredicates.NO_CREATIVE_OR_SPECTATOR
        );

        pTarget.playSound(SoundEvents.GENERIC_BURN, 2.0F, 0.8F);
        pTarget.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 2.0F, 1.2F);
        EntityUtils.swingAHand(pStack, pAttacker);

        for (LivingEntity itr : aabb) {
            if (itr != pAttacker) {
                itr.invulnerableTime = 0;
                itr.hurt(DamageSource.indirectMagic(pAttacker, pAttacker), 4 + shr);
                EntityUtils.knockbackAwayFromUser(2, pAttacker, itr);

                if (itr != pTarget) {
                    pTarget.setSecondsOnFire(4);
                }

                if (!itr.level.isClientSide()) {
                    EffectUtils.addCircleParticleForm(itr.level, itr, ParticleTypes.SOUL_FIRE_FLAME, 15, 1.5, 0);
                }
            }
        }
    }

    @Override
    public ActionResultType useOn(ItemUseContext pContext) {
        World world = pContext.getLevel(); PlayerEntity entity = pContext.getPlayer(); Hand hand = pContext.getHand();
        BlockPos pos = pContext.getClickedPos(); ItemStack stack = pContext.getItemInHand();

        if (ItemUtils.checkCooldownUsable(entity, stack)) {
            if (entity != null) {
                entity.setDeltaMovement(entity.getDeltaMovement().add(0, 1.0, 0));
                entity.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, 20, 1));
                entity.swing(hand);
            }
            world.playSound(entity, pos, SoundEvents.SHULKER_SHOOT, SoundCategory.PLAYERS, 1, 1);
            world.playSound(entity, pos, SoundEvents.PLAYER_ATTACK_SWEEP, SoundCategory.PLAYERS, 1, 1);
            ItemUtils.setStackOnCooldown(entity, stack, 20, true);
            return ActionResultType.SUCCESS;

        } else return super.useOn(pContext);
    }

    @Override
    public void inventoryTick(ItemStack pStack, World pLevel, Entity pEntity, int pItemSlot, boolean pIsSelected) {
        super.inventoryTick(pStack, pLevel, pEntity, pItemSlot, pIsSelected);
        CompoundNBT getTag = pStack.getOrCreateTag();

        if (isReversed(pStack)) {
            int reverseCombo = getTag.getInt("reverse_combo");
            getTag.putInt("reverse_combo", reverseCombo + 1);

            if (getTag.getInt("reverse_combo") >= 10) {
                if (getMarkedEntity() != null) {
                    if (!(pEntity instanceof LivingEntity)) return;
                    reverseAttack(pStack, getMarkedEntity(), (LivingEntity) pEntity);

                    this.setMarkedEntity(null);
                }

                getTag.putInt("reverse_combo", 0);
                setReversed(pStack, false);
            }
        }
    }

    public static boolean isReversed(ItemStack stack) {
        CompoundNBT compoundnbt = stack.getTag();
        return compoundnbt != null && compoundnbt.getBoolean("reverse");
    }

    public static void setReversed(ItemStack stack, boolean value) {
        CompoundNBT compoundnbt = stack.getOrCreateTag();
        compoundnbt.putBoolean("reverse", value);
    }

    public LivingEntity getMarkedEntity() {
        return markedEntity;
    }

    public void setMarkedEntity(LivingEntity markedEntity) {
        this.markedEntity = markedEntity;
    }
}
