package io.github.bioplethora.event;

import io.github.bioplethora.config.BioplethoraConfig;
import io.github.bioplethora.registry.BioplethoraItems;
import io.github.bioplethora.util.BioplethoraAdvancementHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.ItemHandlerHelper;

@Mod.EventBusSubscriber
public class PlayerJoinEvent {

    public static boolean hasBook = false;

    @SubscribeEvent
    public static void onEntitySpawned(EntityJoinWorldEvent event) {

        Entity eventEntity = event.getEntity();

        if (BioplethoraConfig.COMMON.hellMode.get() && BioplethoraConfig.COMMON.hellModeReminder.get()) {
            if (eventEntity instanceof PlayerEntity) {
                if (!eventEntity.level.isClientSide()) {
                    ((PlayerEntity) event.getEntity()).displayClientMessage(new StringTextComponent("\u00A7cQuick Reminder: You are in Bioplethora Hell Mode. Most Bioplethora creatures will become stronger and more powerful."), (false));
                }
            }
        }

        if (((event.getEntity() instanceof PlayerEntity) || (event.getEntity() instanceof ServerPlayerEntity))) {
            if (!event.getEntity().level.isClientSide()) {
                BioplethoraAdvancementHelper.grantBioAdvancement(eventEntity, "bioplethora:bioplethora_startup");
            }
        }

        if ((BioplethoraConfig.COMMON.startupBiopedia.get()) && (eventEntity instanceof PlayerEntity)) {

            if (!getHasBook()) {
                ItemStack stack = new ItemStack(BioplethoraItems.BIOPEDIA.get());
                stack.setCount(1);
                setHasBook(true);
                ItemHandlerHelper.giveItemToPlayer((PlayerEntity) eventEntity, stack);
            }
        }
    }

    public static boolean getHasBook() {
        return hasBook;
    }

    public static void setHasBook(boolean value) {
        hasBook = value;
    }
}