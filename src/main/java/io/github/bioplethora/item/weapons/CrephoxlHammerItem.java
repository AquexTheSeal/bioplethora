package io.github.bioplethora.item.weapons;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import net.minecraftforge.event.world.NoteBlockEvent;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CrephoxlHammerItem extends AxeItem {

    public CrephoxlHammerItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Item.Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity entity, LivingEntity source) {
        boolean retval = super.hurtEnemy(stack, entity, source);

        World world = entity.level;
        double x = entity.getX(),
                y = entity.getY(),
                z = entity.getZ();
        BlockPos pos = new BlockPos(x, y, z);
        PlayerEntity player = (PlayerEntity) source;

        /**Special Ability 1 of 2: Deathsweep
         *
         * Hitting an entity while crouching will deal 80% of this tool's base damage to nearby entities within
         * a 2-block radius. 1.5 second cooldown.
         */
        if(player.isCrouching() && player.getCurrentItemAttackStrengthDelay() >= 18.0F /* this'll stay until we find a better way to detect player attack cooldown. */) {
            player.getCooldowns().addCooldown(stack.getItem(), 30);
            world.playSound(null, pos, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.anvil.place")), SoundCategory.PLAYERS, 1, 1);
            world.playSound(null, pos, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.player.attack.sweep")), SoundCategory.PLAYERS, 1, 1);
            if(world.isClientSide) {
                world.addParticle(ParticleTypes.SWEEP_ATTACK, x, y, z, 0, 0, 0);
            }
            if(world instanceof ServerWorld) {
                List<Entity> nearEntities = world
                        .getEntitiesOfClass(Entity.class, new AxisAlignedBB(x - (5 / 2d), y, z - (5 / 2d), x + (4 / 2d), y + (4 / 2d), z + (4 / 2d)), null)
                        .stream().sorted(new Object() {
                            Comparator<Entity> compareDistOf(double dx, double dy, double dz) {
                                return Comparator.comparing((entCnd -> entCnd.distanceToSqr(dx, dy, dz)));
                            }
                        }.compareDistOf(x, y, z)).collect(Collectors.toList());
                for (Entity entityIterator : nearEntities) {
                    if (entityIterator instanceof LivingEntity && entityIterator != player) {
                        // 33% chance to apply slowness 4 for 3 seconds to nearby entities.
                        if(Math.random() < 0.33) {
                            ((LivingEntity) entityIterator).addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 60, 4));
                        }
                        /* Hurts all nearby entities EXCEPT for the one attacked (to prevent dealing extra damage
                        to the original entity hit)
                        */
                        if(entityIterator != entity) {
                            entityIterator.hurt(DamageSource.mobAttack(player), this.getAttackDamage() * 0.8F);
                        }
                    }
                }
            }
        }

        //Adds debuffs to target after hit.
        entity.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 5, 2));
        entity.addEffect(new EffectInstance(Effects.WEAKNESS, 4, 1));
        entity.addEffect(new EffectInstance(Effects.DIG_SLOWDOWN, 3, 1));
        entity.addEffect(new EffectInstance(Effects.CONFUSION, 5, 1));

        //Deals more damage to Entities over 50 max health.
        if (entity.getMaxHealth() >= 50) {
            entity.hurt(DamageSource.mobAttack(entity), getAttackDamage() * 2);
        }
        return retval;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("item.bioplethora.sacred_level.desc").withStyle(TextFormatting.AQUA));
        tooltip.add(new TranslationTextComponent("item.bioplethora.additions.desc").withStyle(TextFormatting.GOLD));
        tooltip.add(new TranslationTextComponent("item.bioplethora.crephoxl_hammer.desc_0").withStyle(TextFormatting.GRAY));
        tooltip.add(new TranslationTextComponent("item.bioplethora.special_skill.desc").withStyle(TextFormatting.GOLD));
        tooltip.add(new TranslationTextComponent("item.bioplethora.crephoxl_hammer.desc_1").withStyle(TextFormatting.GRAY));
    }

    /** Special Ability 2 of 2: Aerial Shockwave
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
            entity.getCooldowns().addCooldown(stack.getItem(), 60);
            if (hand != null) {
                entity.swing(hand);
            }
            world.playSound(entity, pos, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.wither.break_block")), SoundCategory.PLAYERS, 1, 1);
            if ((!(world.isClientSide()))) {
                ((ServerWorld)world).sendParticles(ParticleTypes.CLOUD, x, y + 1.2, z, 50, 3, 0.2, 3, 0);
                {
                    List<Entity> nearEntities = world
                            .getEntitiesOfClass(Entity.class, new AxisAlignedBB(x - (7 / 2d), y - (3 / 2d), z - (7 / 2d), x + (4 / 2d), y + (4 / 2d), z + (4 / 2d)), null)
                            .stream().sorted(new Object() {
                                Comparator<Entity> compareDistOf(double dx, double dy, double dz) {
                                    return Comparator.comparing((entCnd -> entCnd.distanceToSqr(dx, dy, dz)));
                                }
                            }.compareDistOf(x, y, z)).collect(Collectors.toList());
                    for (Entity entityIterator : nearEntities) {
                        if (entityIterator instanceof LivingEntity && entityIterator != entity) {
                            entityIterator.hurt(DamageSource.mobAttack(entity), 9.0F);
                            entityIterator.setDeltaMovement((entity.getDeltaMovement().x()), 1, (entity.getDeltaMovement().z()));
                        }
                    }
                }
            }
            return ActionResultType.SUCCESS;
        } else {
            return ActionResultType.FAIL;
        }
    }
}
