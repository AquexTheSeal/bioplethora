package io.github.bioplethora.event;

import io.github.bioplethora.item.weapons.BellophiteShieldItem;
import io.github.bioplethora.registry.BioplethoraItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class OnEntityAttackedEvent {

    @SubscribeEvent
    public static void onEntityAttacked(LivingAttackEvent event) {

        Entity defendantEnt = event.getEntity();
        Entity attackerEnt = event.getSource().getEntity();
        Item bellophiteShield = BioplethoraItems.BELLOPHITE_SHIELD.get();

        if (defendantEnt instanceof PlayerEntity) {

            ItemStack useItem = ((PlayerEntity) defendantEnt).getUseItem();

            if ((useItem.getItem() == bellophiteShield) && (useItem.getItem() instanceof BellophiteShieldItem)) {
                ((BellophiteShieldItem) useItem.getItem()).executeSkill(useItem, (LivingEntity) defendantEnt, defendantEnt.level);
            }
        }
    }
}
