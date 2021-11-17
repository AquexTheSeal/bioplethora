package io.github.bioplethora.items.weapons;

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
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import net.minecraftforge.registries.ForgeRegistries;

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

        //Adds debuffs to target after hit.
        entity.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 5, 2));
        entity.addEffect(new EffectInstance(Effects.WEAKNESS, 4, 1));
        entity.addEffect(new EffectInstance(Effects.DIG_SLOWDOWN, 3, 1));
        entity.addEffect(new EffectInstance(Effects.CONFUSION, 5, 1));

        //Deals more damage to Entities over 50 max health.
        if (entity.getMaxHealth() >= 50) {
            entity.hurt(DamageSource.GENERIC, getAttackDamage() * 2);
        }
        return retval;
    }

    @Override
    public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        list.add(new StringTextComponent("\u00A77Debuffs targets. Deals more damage to mobs with more than 50 maximum health."));
    }

    /* Special Ability: Create a damaging shockwave on block right-click position, dealing 9 damage to
    nearby entities & sending them flying into the air. 2-second cooldown.
     */
    @Override
    public ActionResultType useOn(ItemUseContext context) {
        World world = context.getLevel();
        PlayerEntity entity = context.getPlayer();
        Hand hand = context.getHand();
        BlockPos pos = context.getClickedPos();
        ItemStack stack = context.getItemInHand();

        double x = pos.getX(),
                y = pos.getY(),
                z = pos.getZ();
        if(!entity.isInWater()) {
            entity.getCooldowns().addCooldown(stack.getItem(), 40);
            if (hand != null) {
                entity.swing(hand);
            }
            world.playSound(entity, pos, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.dragon_fireball.explode")), SoundCategory.PLAYERS, 1, 1);
            if ((!(world.isClientSide()))) {
                ((ServerWorld)world).sendParticles(ParticleTypes.CLOUD, x, y + 1.2, z, 50, 3, 0.2, 3, 0);
                {
                    List<Entity> nearEntities = world
                            .getEntitiesOfClass(Entity.class, new AxisAlignedBB(x - (7 / 2d), y - (3 / 2d), z - (7 / 2d), x + (4 / 2d), y + (4 / 2d), z + (4 / 2d)), null)
                            .stream().sorted(new Object() {
                                Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
                                    return Comparator.comparing((entCnd -> entCnd.distanceToSqr(_x, _y, _z)));
                                }
                            }.compareDistOf(x, y, z)).collect(Collectors.toList());
                    for (Entity entityIterator : nearEntities) {
                        if (entityIterator instanceof LivingEntity && entityIterator != entity) {
                            entityIterator.hurt(DamageSource.mobAttack(entity), 9.0F);
                            entityIterator.setDeltaMovement((entity.getDeltaMovement().x()), 1.2, (entity.getDeltaMovement().z()));
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
