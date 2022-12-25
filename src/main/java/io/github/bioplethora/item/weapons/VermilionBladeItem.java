package io.github.bioplethora.item.weapons;

import io.github.bioplethora.api.BPItemSettings;
import io.github.bioplethora.api.IReachWeapon;
import io.github.bioplethora.api.world.ItemUtils;
import io.github.bioplethora.entity.others.BPEffectEntity;
import io.github.bioplethora.entity.projectile.VermilionBladeProjectileEntity;
import io.github.bioplethora.enums.BPEffectTypes;
import io.github.bioplethora.registry.BPParticles;
import io.github.bioplethora.registry.BPSoundEvents;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class VermilionBladeItem extends SwordItem implements IReachWeapon {

    public int bladeSize;

    public VermilionBladeItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Item.Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
        this.bladeSize = 1;
    }

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {

        if (!(worldIn instanceof ServerWorld)) {
            return new ActionResult<>(ActionResultType.PASS, playerIn.getItemInHand(handIn));
        }
        this.shootBlade(playerIn.getItemInHand(handIn), playerIn, worldIn);

        return new ActionResult<>(ActionResultType.SUCCESS, playerIn.getItemInHand(handIn));
    }

    public void emptySwingHandler(ItemStack stack, LivingEntity entity) {
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            if (!player.getCooldowns().isOnCooldown(stack.getItem())) {
                BPEffectEntity.createInstance(entity, BPEffectTypes.SHACHATH_SLASH_FLAT);
                entity.playSound(BPSoundEvents.SHACHATH_SLASH.get(), 0.75F, 0.75F + random.nextFloat());
                if (random.nextBoolean()) {
                    for (LivingEntity entities : entity.level.getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(2.4, 0, 2.4))) {
                        if (entities != entity) {
                            double xa = entities.getX(), ya = entities.getY() + 1, za = entities.getZ();
                            entities.hurt(DamageSource.mobAttack(entity), 6F + (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SWEEPING_EDGE, stack) * 1.5F));
                            entity.level.addParticle(BPParticles.SHACHATH_CLASH_INNER.get(), xa, ya, za, 0, 0, 0);
                            entity.level.addParticle(BPParticles.SHACHATH_CLASH_OUTER.get(), xa, ya, za, 0, 0, 0);
                        }
                    }
                } else {
                    double d0 = -MathHelper.sin(entity.yRot * ((float) Math.PI / 180F)) * 6;
                    double d1 = MathHelper.cos(entity.yRot * ((float) Math.PI / 180F)) * 6;
                    BPEffectEntity.createInstance(entity, BPEffectTypes.SHACHATH_SLASH_FRONT);
                    entity.playSound(BPSoundEvents.SHACHATH_SLASH.get(), 0.75F, 0.75F + random.nextFloat());
                    for (LivingEntity entities : entity.level.getEntitiesOfClass(LivingEntity.class, new AxisAlignedBB(entity.getX() - d0, entity.getY() - 2.5, entity.getZ() - d1, entity.getX() + d0, entity.getY() + 2.5, entity.getZ() + d1))) {
                        if (entities != entity) {
                            double xa = entities.getX(), ya = entities.getY() + 1, za = entities.getZ();
                            entities.hurt(DamageSource.mobAttack(entity), 8F + (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SWEEPING_EDGE, stack) * 1.5F));
                            entity.level.addParticle(BPParticles.SHACHATH_CLASH_INNER.get(), xa, ya, za, 0, 0, 0);
                            entity.level.addParticle(BPParticles.SHACHATH_CLASH_OUTER.get(), xa, ya, za, 0, 0, 0);
                        }
                    }
                }
                player.getCooldowns().addCooldown(stack.getItem(), 10);
            }
        }
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        this.emptySwingHandler(pStack, pAttacker);
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        BPItemSettings.bossLevelText(tooltip);

        tooltip.add(new TranslationTextComponent("item.bioplethora.vermilion_blade.blade_master.skill").withStyle(BPItemSettings.SKILL_NAME_COLOR));
        if (Screen.hasShiftDown() || Screen.hasControlDown()) {
            tooltip.add(new TranslationTextComponent("item.bioplethora.vermilion_blade.blade_master.desc").withStyle(BPItemSettings.SKILL_DESC_COLOR));
        }

        tooltip.add(new TranslationTextComponent("item.bioplethora.vermilion_blade.pure_energy_concentration.skill").withStyle(BPItemSettings.SKILL_NAME_COLOR));
        if (Screen.hasShiftDown() || Screen.hasControlDown()) {
            tooltip.add(new TranslationTextComponent("item.bioplethora.vermilion_blade.pure_energy_concentration.desc").withStyle(BPItemSettings.SKILL_DESC_COLOR));
        }
    }

    public void shootBlade(ItemStack stack, LivingEntity entity, World world) {

        if (ItemUtils.checkCooldownUsable(entity, stack)) {
            VermilionBladeProjectileEntity projectile = new VermilionBladeProjectileEntity(world, entity, ItemUtils.projAngleX(entity), ItemUtils.projAngleY(entity), ItemUtils.projAngleZ(entity));
            projectile.setBladeSize(this.bladeSize);
            ItemUtils.shootWithItemBreakable(entity, projectile, world, stack, 1);

            entity.playSound(SoundEvents.BLAZE_SHOOT, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + 0.5F);
            ++this.bladeSize;
            if (this.bladeSize >= 5) bladeSize = 1;

            ItemUtils.setStackOnCooldown(entity, stack, (5 * (this.bladeSize * 2)) - (EnchantmentHelper.getEnchantmentLevel(Enchantments.QUICK_CHARGE, entity) * 5), true);
        }
    }

    @Override
    public boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker) {
        return true;
    }

    @Override
    public double getReachDistance() {
        return 7;
    }
}
