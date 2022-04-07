package io.github.bioplethora.item.weapons;

import io.github.bioplethora.api.world.EffectUtils;
import io.github.bioplethora.api.world.EntityUtils;
import io.github.bioplethora.api.world.ItemUtils;
import io.github.bioplethora.item.ItemSettings;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class CrephoxlHammerItem extends AxeItem {

    public CrephoxlHammerItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Item.Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
    }

    /** <h2>Special Ability 1 of 2: Deathsweep</h2>
     *
     * Hitting an entity while crouching will deal 80% of this tool's base damage to nearby entities within
     a 2-block radius. 1.5 second cooldown.
     */
    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity entity, LivingEntity source) {
        boolean retval = super.hurtEnemy(stack, entity, source);

        World world = entity.level;
        double x = entity.getX(), y = entity.getY(), z = entity.getZ();
        BlockPos pos = new BlockPos(x, y, z);
        PlayerEntity player = (PlayerEntity) source;

        if(player.isCrouching() && ItemUtils.checkCooldownUsable(entity, stack)) {
            ItemUtils.setStackOnCooldown(entity, stack, 30, true);

            entity.playSound(SoundEvents.ANVIL_PLACE, 1, 1);
            entity.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 1, 1);

            if(!world.isClientSide) {
                world.addParticle(ParticleTypes.SWEEP_ATTACK, x, y, z, 0, 0, 0);
                for (LivingEntity entityIterator : EntityUtils.getEntitiesInArea(LivingEntity.class, world, pos, 5, 2)) {
                    if(Math.random() < 0.33) {
                        EffectUtils.addEffectNoIcon(entityIterator, Effects.MOVEMENT_SLOWDOWN, 60, 4);
                    }
                    if(entityIterator != entity) {
                        entityIterator.hurt(DamageSource.mobAttack(player), this.getAttackDamage() * 0.8F);
                    }
                }
            }
        }

        //Adds debuffs to target after hit.
        EffectUtils.addEffectNoIcon(entity, Effects.MOVEMENT_SLOWDOWN, 5, 2);
        EffectUtils.addEffectNoIcon(entity, Effects.WEAKNESS, 4, 1);
        EffectUtils.addEffectNoIcon(entity, Effects.DIG_SLOWDOWN, 3, 1);
        EffectUtils.addEffectNoIcon(entity, Effects.CONFUSION, 5, 1);

        //Deals more damage to Entities over 50 max health.
        if (entity.getMaxHealth() >= 50) {
            entity.hurt(DamageSource.mobAttack(entity), getAttackDamage() * 2);
        }

        return retval;
    }

    /** <h2>Special Ability 2 of 2: Aerial Shockwave</h2>
     *
     * Create a damaging shockwave on block right-click position, dealing 9 damage to
     nearby entities & sending them flying into the air. 3-second cooldown.
     */
    @Override
    public ActionResultType useOn(ItemUseContext context) {
        World world = context.getLevel();
        PlayerEntity entity = context.getPlayer();
        Hand hand = context.getHand();
        BlockPos pos = context.getClickedPos();
        ItemStack stack = context.getItemInHand();

        double x = pos.getX(), y = pos.getY(), z = pos.getZ();
        if(!entity.isInWater()) {
            ItemUtils.setStackOnCooldown(entity, stack, 60, true);
            world.playSound(entity, pos, SoundEvents.WITHER_BREAK_BLOCK, SoundCategory.PLAYERS, 1, 1);
            entity.swing(hand);

            if (!world.isClientSide()) {
                ((ServerWorld)world).sendParticles(ParticleTypes.CLOUD, x, y + 1.2, z, 50, 3, 0.2, 3, 0);
            }

            for (Entity entityIterator : world.getEntitiesOfClass(Entity.class, new AxisAlignedBB(x - (7 / 2d), y - (3 / 2d), z - (7 / 2d), x + (4 / 2d), y + (4 / 2d), z + (4 / 2d)), null)) {
                if (entityIterator instanceof LivingEntity && entityIterator != entity) {
                    entityIterator.hurt(DamageSource.mobAttack(entity), 9.0F);
                    entityIterator.setDeltaMovement((entity.getDeltaMovement().x()), 1, (entity.getDeltaMovement().z()));
                }
            }

            return ActionResultType.SUCCESS;
        } else {
            return ActionResultType.FAIL;
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        ItemSettings.sacredLevelText(tooltip);

        tooltip.add(new TranslationTextComponent("item.bioplethora.crephoxl_hammer.dysfunction.skill").withStyle(ItemSettings.SKILL_NAME_COLOR));
        if (Screen.hasShiftDown() || Screen.hasControlDown()) {
            tooltip.add(new TranslationTextComponent("item.bioplethora.crephoxl_hammer.dysfunction.desc").withStyle(ItemSettings.SKILL_DESC_COLOR));
        }

        tooltip.add(new TranslationTextComponent("item.bioplethora.crephoxl_hammer.deathsweep.skill").withStyle(ItemSettings.SKILL_NAME_COLOR));
        if (Screen.hasShiftDown() || Screen.hasControlDown()) {
            tooltip.add(new TranslationTextComponent("item.bioplethora.crephoxl_hammer.deathsweep.desc").withStyle(ItemSettings.SKILL_DESC_COLOR));
        }

        tooltip.add(new TranslationTextComponent("item.bioplethora.crephoxl_hammer.aerial_shockwave.skill").withStyle(ItemSettings.SKILL_NAME_COLOR));
        if (Screen.hasShiftDown() || Screen.hasControlDown()) {
            tooltip.add(new TranslationTextComponent("item.bioplethora.crephoxl_hammer.aerial_shockwave.desc").withStyle(ItemSettings.SKILL_DESC_COLOR));
        }
    }
}
