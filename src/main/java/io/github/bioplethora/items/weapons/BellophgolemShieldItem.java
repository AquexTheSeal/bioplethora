package io.github.bioplethora.items.weapons;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class BellophgolemShieldItem extends ShieldItem {

    public BellophgolemShieldItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isShield(ItemStack stack, LivingEntity entity){
        return true;
    }

    @Override
    public int getUseDuration(ItemStack itemStack) {
        return 125000;
    }

    @Override
    public UseAction getUseAnimation(ItemStack itemStack) {
        return UseAction.BLOCK;
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        ItemStack itemstack = playerEntity.getItemInHand(hand);
        playerEntity.startUsingItem(hand);
        return ActionResult.consume(itemstack);
    }
}