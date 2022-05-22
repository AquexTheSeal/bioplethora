package io.github.bioplethora.item.weapons;

import io.github.bioplethora.api.IReachWeapon;
import io.github.bioplethora.api.world.EntityUtils;
import io.github.bioplethora.entity.projectile.CryeanumGaidiusEntity;
import io.github.bioplethora.entity.projectile.GaidiusBaseEntity;
import io.github.bioplethora.registry.BPEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.UseAction;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class GaidiusBaseItem extends SwordItem implements IReachWeapon {

    public ItemProps gaidiusProps;

    public GaidiusBaseItem(IItemTier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, ItemProps gaidiusProps, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
        this.gaidiusProps = gaidiusProps;
    }

    public ActionResult<ItemStack> use(World world, PlayerEntity entity, Hand handIn) {
        ItemStack itemstack = entity.getItemInHand(handIn);
        entity.startUsingItem(handIn);
        return ActionResult.consume(itemstack);
    }

    public UseAction getUseAnimation(ItemStack p_77661_1_) {
        return UseAction.SPEAR;
    }

    public int getUseDuration(ItemStack p_77626_1_) {
        return 72000;
    }

    @Override
    public void releaseUsing(ItemStack stack, World world, LivingEntity entity, int value) {
        super.releaseUsing(stack, world, entity, value);

        int i = this.getUseDuration(stack) - value;
        if (i >= 10 - (EnchantmentHelper.getItemEnchantmentLevel(BPEnchantments.SHEER.get(), stack) * 5)) {

            shootAt(stack, world, entity, i, 0);
            if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.MULTISHOT, stack) > 0) {
                shootAt(stack, world, entity, i, 15);
                shootAt(stack, world, entity, i, -15);
            }

            entity.playSound(SoundEvents.TRIDENT_THROW, 1.2F, 0.5F);

            if (entity instanceof PlayerEntity) {
                PlayerEntity playerentity = (PlayerEntity) entity;

                playerentity.awardStat(Stats.ITEM_USED.get(this));
                if (!playerentity.abilities.instabuild) {
                    stack.hurtAndBreak(
                            50 - (EnchantmentHelper.getItemEnchantmentLevel(BPEnchantments.SOFTSHOOTING.get(), stack) * 8),
                            entity, (entity1) -> entity1.broadcastBreakEvent(EntityUtils.getSlotTypeFromItem(stack, entity))
                    );
                }
            }
        }
    }

    public void shootAt(ItemStack stack, World world, LivingEntity entity, int i, float yRotAddition) {
        float gaidiusCritVelocityMultiplier = i >= 30 ? 2F : 1F;
        GaidiusBaseEntity gaidius = gaidiusProps.projectile.test(world, entity);

        gaidius.setItem(stack);
        gaidius.setOwner(entity);
        gaidius.shootFromRotation(entity, entity.xRot, entity.yRot + yRotAddition, 0.0F, gaidiusProps.velocity * gaidiusCritVelocityMultiplier, gaidiusProps.inaccuracy);
        if (i >= 30 - (EnchantmentHelper.getItemEnchantmentLevel(BPEnchantments.SHEER.get(), stack) * 10)) {
            gaidius.setCrit(true);
        }
        if (!world.isClientSide) {
            world.addFreshEntity(gaidius);
        }
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return super.canApplyAtEnchantingTable(stack, enchantment) || enchantment == Enchantments.MULTISHOT;
    }

    @Override
    public double getReachDistance() {
        return 6.5;
    }

    public interface IGaidiusPredicate {
        GaidiusBaseEntity test(World world, LivingEntity entity);
    }

    public static class ItemProps {
        private IGaidiusPredicate projectile = CryeanumGaidiusEntity::new;
        private float velocity = 2.0F;
        private float inaccuracy = 1.0F;

        public ItemProps projectile(IGaidiusPredicate projectile) {
            this.projectile = projectile;
            return this;
        }

        public ItemProps velocity(float velocity) {
            this.velocity = velocity;
            return this;
        }

        public ItemProps inaccuracy(float inaccuracy) {
            this.inaccuracy = inaccuracy;
            return this;
        }
    }
}
