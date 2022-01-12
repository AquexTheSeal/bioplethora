package io.github.bioplethora.event;

import io.github.bioplethora.config.BioplethoraConfig;
import io.github.bioplethora.registry.BioplethoraItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.ItemHandlerHelper;

@Mod.EventBusSubscriber
public class PlayerJoinEvent {

    private static boolean hasBook = false;

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
            if (event.getEntity() instanceof PlayerEntity && !event.getEntity().level.isClientSide()) {

                Advancement adv = ((ServerPlayerEntity) eventEntity).server.getAdvancements().getAdvancement(new ResourceLocation("bioplethora:bioplethora_startup"));

                assert adv != null;
                AdvancementProgress advProg = ((ServerPlayerEntity) eventEntity).getAdvancements().getOrStartProgress(adv);

                if (!advProg.isDone()) {
                    for (String s : advProg.getRemainingCriteria()) {
                        ((ServerPlayerEntity) eventEntity).getAdvancements().award(adv, s);
                    }
                }
            }
        }

        if ((BioplethoraConfig.COMMON.startupBiopedia.get()) && (eventEntity instanceof PlayerEntity)) {

            if (!hasBook) {
                ItemStack stack = new ItemStack(BioplethoraItems.BIOPEDIA.get());
                stack.setCount(1);
                ItemHandlerHelper.giveItemToPlayer((PlayerEntity) eventEntity, stack);
                hasBook = true;
            }
        }
    }
}