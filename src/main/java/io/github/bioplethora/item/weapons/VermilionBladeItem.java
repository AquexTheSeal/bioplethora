package io.github.bioplethora.item.weapons;

import io.github.bioplethora.api.BPItemSettings;
import io.github.bioplethora.api.IReachWeapon;
import io.github.bioplethora.api.world.ItemUtils;
import io.github.bioplethora.entity.projectile.VermilionBladeProjectileEntity;
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

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity entity, LivingEntity source) {
        boolean retval = super.hurtEnemy(stack, entity, source);

        World world = entity.level;
        double areaint = 3 * ((double) EnchantmentHelper.getEnchantmentLevel(Enchantments.SWEEPING_EDGE, source) * 1.5);
        double sx = entity.getX(), sy = entity.getY(), sz = entity.getZ();
        AxisAlignedBB area = new AxisAlignedBB(sx - (areaint / 2d), sy, sz - (areaint / 2d), sx + (areaint / 2d), sy + (areaint / 2d), sz + (areaint / 2d));

        world.playSound((PlayerEntity) source, sx, sy, sz, SoundEvents.ZOMBIE_BREAK_WOODEN_DOOR, SoundCategory.PLAYERS, 1, 1);

        for (LivingEntity entityIterator : world.getEntitiesOfClass(LivingEntity.class, area)) {
            if (entityIterator != source) {
                double eix = entityIterator.getX(), eiy = entityIterator.getY(), eiz = entityIterator.getZ();
                if (!(world.isClientSide())) {
                    ((ServerWorld) world).sendParticles(ParticleTypes.SMOKE, eix, eiy, eiz, 100, 0.5, 0.5, 0.5, 0);
                }
                entityIterator.hurt(DamageSource.playerAttack((PlayerEntity) source), this.getDamage());
            }
        }

        return retval;
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
            ItemUtils.shootWithItemBreakable(entity, projectile, world, stack, 1);

            entity.playSound(SoundEvents.BLAZE_SHOOT, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + 0.5F);
            if (++this.bladeSize >= 5) bladeSize = 1;

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
