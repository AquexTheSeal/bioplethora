package io.github.bioplethora.item.weapons;

import io.github.bioplethora.blocks.api.BPItemSettings;
import io.github.bioplethora.blocks.api.IReachWeapon;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
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

public class StellarScytheItem extends SwordItem implements IReachWeapon {

    public StellarScytheItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties properties) {
        super(tier, attackDamageIn, attackSpeedIn, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity entity, LivingEntity source) {
        boolean retval = super.hurtEnemy(stack, entity, source);

        World world = entity.level;
        double x = entity.getX(), y = entity.getY(), z = entity.getZ();
        BlockPos pos = new BlockPos(x, y, z);
        PlayerEntity player = (PlayerEntity) source;

        if (!player.getCooldowns().isOnCooldown(stack.getItem())) {
            player.getCooldowns().addCooldown(stack.getItem(), 20);
            world.playSound(null, pos, SoundEvents.PLAYER_ATTACK_SWEEP, SoundCategory.PLAYERS, 1, 1);
            if(!world.isClientSide) {
                double d0 = -MathHelper.sin(player.yRot * ((float)Math.PI / 180F));
                double d1 = MathHelper.cos(player.yRot * ((float)Math.PI / 180F));
                ((ServerWorld)world).sendParticles(ParticleTypes.SWEEP_ATTACK, player.getX() + d0, player.getY(0.5D), player.getZ() + d1, 0, d0, 0.0D, d1, 0.0D);
            }
            if(world instanceof ServerWorld) {
                for (Entity entityIterator : world.getEntitiesOfClass(Entity.class, player.getBoundingBox().inflate(2D, 1D, 2D))) {
                    if (entityIterator instanceof LivingEntity && entityIterator != player) {
                        if(entityIterator != entity) {
                            entityIterator.hurt(DamageSource.mobAttack(player), (this.getDamage() * 0.8F) * EnchantmentHelper.getEnchantmentLevel(Enchantments.SWEEPING_EDGE, source));
                        }
                    }
                }
            }
        }
        return retval;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        BPItemSettings.sacredLevelText(tooltip);

        tooltip.add(new TranslationTextComponent("item.bioplethora.stellar_scythe.radius_slash.skill").withStyle(BPItemSettings.SKILL_NAME_COLOR));
        if (Screen.hasShiftDown() || Screen.hasControlDown()) {
            tooltip.add(new TranslationTextComponent("item.bioplethora.stellar_scythe.radius_slash.desc").withStyle(BPItemSettings.SKILL_DESC_COLOR));
        }
    }

    @Override
    public double getReachDistance() {
        return 7.5;
    }
}
