package io.github.bioplethora.item.weapons;

import io.github.bioplethora.api.BPItemSettings;
import io.github.bioplethora.api.IReachWeapon;
import io.github.bioplethora.api.world.EffectUtils;
import io.github.bioplethora.api.world.EntityUtils;
import io.github.bioplethora.api.world.ItemUtils;
import io.github.bioplethora.entity.others.BPEffectEntity;
import io.github.bioplethora.enums.BPEffectTypes;
import io.github.bioplethora.registry.BPDamageSources;
import io.github.bioplethora.registry.BPEntities;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class InfernalQuarterstaffItem extends SwordItem implements IReachWeapon {

    LivingEntity markedEntity;

    public InfernalQuarterstaffItem(IItemTier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public double getReachDistance() {
        return 6.5;
    }

    public void emptySwingHandler(ItemStack stack, LivingEntity entity) {

        double d0 = -MathHelper.sin(entity.yRot * ((float) Math.PI / 180F));
        double d1 = MathHelper.cos(entity.yRot * ((float) Math.PI / 180F));

        entity.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 1.3F, 0.2F + entity.getRandom().nextFloat());
        entity.playSound(SoundEvents.GENERIC_BURN, 2.0F, 1.2F);

        if (!entity.level.isClientSide) {
            ((ServerWorld) entity.level).sendParticles(ParticleTypes.SWEEP_ATTACK, entity.getX() + d0, entity.getY(0.5D), entity.getZ() + d1, 0, d0, 0.0D, d1, 0.0D);
        }

        if (entity.level instanceof ServerWorld) {
            for (Entity entityIterator : entity.level.getEntitiesOfClass(Entity.class, entity.getBoundingBox().inflate(3D, 1D, 3D))) {

                if (entityIterator instanceof LivingEntity && entityIterator != entity && EntityUtils.IsNotPet(entity).test(entityIterator)) {
                    entityIterator.hurt(BPDamageSources.armorPiercingWeapon(entity), 4 + EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SWEEPING_EDGE, stack));
                }
            }
        }
        BPEffectEntity.createInstance(entity, BPEffectTypes.FLAMING_SLASH);
    }

    @Override
    public void inventoryTick(ItemStack pStack, World pLevel, Entity pEntity, int pItemSlot, boolean pIsSelected) {
        super.inventoryTick(pStack, pLevel, pEntity, pItemSlot, pIsSelected);
        CompoundNBT getTag = pStack.getOrCreateTag();

        if (getQuarterstaffCD(pEntity) > 0) {
            addQuarterstaffCD(pEntity, -1);
        }

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

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {

        pTarget.playSound(SoundEvents.GENERIC_BURN, 1.0F, 1.0F);
        EffectUtils.addCircleParticleForm(pTarget.level, pTarget, ParticleTypes.FLAME, 15, 1.5, 0);
        pTarget.hurt(BPDamageSources.armorPiercingWeapon(pAttacker), 2);
        pTarget.setSecondsOnFire(7);
        setMarkedEntity(pTarget);
        setReversed(pStack, true);
        BPEffectEntity.createInstance(pAttacker, BPEffectTypes.FLAMING_SLASH);

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
            if (itr != pAttacker && EntityUtils.IsNotPet(pAttacker).test(itr)) {
                itr.invulnerableTime = 0;
                itr.hurt(DamageSource.indirectMagic(pAttacker, pAttacker), 4 + shr);
                EntityUtils.knockbackAwayFromUser(0.8F, pAttacker, itr);

                if (itr != pTarget) {
                    pTarget.setSecondsOnFire(4);
                }

                if (!itr.level.isClientSide()) {
                    EffectUtils.addCircleParticleForm(itr.level, itr, ParticleTypes.SOUL_FIRE_FLAME, 15, 1.5, 0);
                }
            }
        }
        BPEffectEntity.createInstance(pAttacker, BPEffectTypes.SOUL_PURGE);
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        double range = 32.0D;
        double distance = range * range;
        Vector3d vec = player.getEyePosition(1);
        Vector3d vec1 = player.getViewVector(1);
        Vector3d targetVec = vec.add(vec1.x * range, vec1.y * range, vec1.z * range);
        AxisAlignedBB aabb = player.getBoundingBox().expandTowards(vec1.scale(range)).inflate(4.0D, 4.0D, 4.0D);
        EntityRayTraceResult result = ProjectileHelper.getEntityHitResult(player, vec, targetVec, aabb, EntityPredicates.NO_CREATIVE_OR_SPECTATOR, distance);

        Hand oppositeHand = hand == Hand.MAIN_HAND ? Hand.OFF_HAND : Hand.MAIN_HAND;
        boolean hasWeaponInOppositeHand = player.getItemInHand(oppositeHand).getItem() == this;

        if (ItemUtils.checkCooldownUsable(player, player.getItemInHand(hand))) {

            if ((result != null ? result.getEntity() : null) != null) {

                if (hasWeaponInOppositeHand) {
                    player.swing(oppositeHand);
                }
                player.swing(hand);
                player.addEffect(new EffectInstance(Effects.GLOWING, 40));
                player.playSound(SoundEvents.GENERIC_BURN, 2.0F, 0.8F);
                result.getEntity().hurt(DamageSource.indirectMagic(player, player), hasWeaponInOppositeHand ? 7.5F : 4F);
                result.getEntity().setSecondsOnFire(hasWeaponInOppositeHand ? 10 : 7);
                BPEffectEntity.createInstance(player, BPEffectTypes.FLAMING_SNIPE);
                ItemUtils.setStackOnCooldown(player, player.getItemInHand(hand), hasWeaponInOppositeHand ? 40 : 100, true);
            }
        }
        
        return super.use(world, player, hand);
    }

    @Override
    public ActionResultType useOn(ItemUseContext pContext) {
        World world = pContext.getLevel(); PlayerEntity entity = pContext.getPlayer(); Hand hand = pContext.getHand();
        BlockPos pos = pContext.getClickedPos(); ItemStack stack = pContext.getItemInHand();

        if (entity != null && ItemUtils.checkCooldownUsable(entity, stack) && entity.isCrouching()) {
            
            entity.setDeltaMovement(entity.getDeltaMovement().add(0, 1.0, 0));
            entity.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, 20, 1));
            entity.swing(hand);
            world.playSound(entity, pos, SoundEvents.SHULKER_SHOOT, SoundCategory.PLAYERS, 1.3F, 1.75F);
            world.playSound(entity, pos, SoundEvents.PLAYER_ATTACK_SWEEP, SoundCategory.PLAYERS, 1, 1);
            BPEffectEntity.createInstance(entity, BPEffectTypes.AIR_JUMP);
            ItemUtils.setStackOnCooldown(entity, stack, 20, true);
            
            return ActionResultType.SUCCESS;

        } else return super.useOn(pContext);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        BPItemSettings.sacredLevelText(tooltip);

        tooltip.add(new TranslationTextComponent("item.bioplethora.infernal_quarterstaff.fierce_slashing.skill").withStyle(BPItemSettings.SKILL_NAME_COLOR));
        if (Screen.hasShiftDown() || Screen.hasControlDown()) {
            tooltip.add(new TranslationTextComponent("item.bioplethora.infernal_quarterstaff.fierce_slashing.desc").withStyle(BPItemSettings.SKILL_DESC_COLOR));
        }

        tooltip.add(new TranslationTextComponent("item.bioplethora.infernal_quarterstaff.flaming_art.skill").withStyle(BPItemSettings.SKILL_NAME_COLOR));
        if (Screen.hasShiftDown() || Screen.hasControlDown()) {
            tooltip.add(new TranslationTextComponent("item.bioplethora.infernal_quarterstaff.flaming_art.desc").withStyle(BPItemSettings.SKILL_DESC_COLOR));
        }

        tooltip.add(new TranslationTextComponent("item.bioplethora.infernal_quarterstaff.snipe_afar.skill").withStyle(BPItemSettings.SKILL_NAME_COLOR));
        if (Screen.hasShiftDown() || Screen.hasControlDown()) {
            tooltip.add(new TranslationTextComponent("item.bioplethora.infernal_quarterstaff.snipe_afar.desc").withStyle(BPItemSettings.SKILL_DESC_COLOR));
        }
    }

    public static int getQuarterstaffCD(Entity entity) {
        CompoundNBT compoundnbt = entity.getPersistentData();
        return compoundnbt.getInt("infQuarterstaffCD");
    }

    public static void setQuarterstaffCD(Entity entity, int value) {
        CompoundNBT compoundnbt = entity.getPersistentData();
        compoundnbt.putInt("infQuarterstaffCD", value);
    }

    public static void addQuarterstaffCD(Entity entity, int value) {
        setQuarterstaffCD(entity, getQuarterstaffCD(entity) + value);
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
