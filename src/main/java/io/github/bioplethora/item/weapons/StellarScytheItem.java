package io.github.bioplethora.item.weapons;

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
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StellarScytheItem extends SwordItem {

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
                world.addParticle(ParticleTypes.SWEEP_ATTACK, x, y + 2, z, 0, 0, 0);
            }
            if(world instanceof ServerWorld) {
                List<Entity> nearEntities = world
                        .getEntitiesOfClass(Entity.class, new AxisAlignedBB(x - (10 / 2d), y, z - (10 / 2d), x + (10 / 2d), y + (10 / 2d), z + (10 / 2d)), null)
                        .stream().sorted(new Object() {
                            Comparator<Entity> compareDistOf(double dx, double dy, double dz) {
                                return Comparator.comparing((entCnd -> entCnd.distanceToSqr(dx, dy, dz)));
                            }
                        }.compareDistOf(x, y, z)).collect(Collectors.toList());
                for (Entity entityIterator : nearEntities) {
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

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("item.bioplethora.sacred_level.desc").withStyle(TextFormatting.AQUA));
        tooltip.add(new TranslationTextComponent("item.bioplethora.special_skill.desc").withStyle(TextFormatting.GOLD));
        tooltip.add(new TranslationTextComponent("item.bioplethora.stellar_scythe.desc_0").withStyle(TextFormatting.GRAY));
    }
}
