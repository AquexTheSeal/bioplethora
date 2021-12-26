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

import java.util.Iterator;

@Mod.EventBusSubscriber
public class PlayerJoinEvent {

    @SubscribeEvent
    public static void onEntitySpawned(EntityJoinWorldEvent event) {

        Entity eventEntity = event.getEntity();

        if (BioplethoraConfig.COMMON.hellMode.get()) {
            if (eventEntity instanceof PlayerEntity) {
                if (!eventEntity.level.isClientSide()) {
                    ((PlayerEntity) event.getEntity()).displayClientMessage(new StringTextComponent("\u00A7cWelcome to the Bioplethora Hell Mode. Objective is to survive."), (false));
                }
            }
        }

        if (((event.getEntity() instanceof PlayerEntity) || (event.getEntity() instanceof ServerPlayerEntity))) {
            if (event.getEntity() instanceof PlayerEntity && !event.getEntity().level.isClientSide()) {

                Advancement adv = ((ServerPlayerEntity) eventEntity).server.getAdvancements().getAdvancement(new ResourceLocation("bioplethora:bioplethora_startup"));

                assert adv != null;
                AdvancementProgress advProg = ((ServerPlayerEntity) eventEntity).getAdvancements().getOrStartProgress(adv);

                if (!advProg.isDone()) {
                    Iterator iterator = advProg.getRemainingCriteria().iterator();
                    while (iterator.hasNext()) {
                        ((ServerPlayerEntity) eventEntity).getAdvancements().award(adv, (String) iterator.next());
                    }
                }
            }
        }

        if (eventEntity instanceof PlayerEntity) {
            ItemStack stack = new ItemStack(BioplethoraItems.BIOPEDIA.get());
            stack.setCount(1);
            ItemHandlerHelper.giveItemToPlayer((PlayerEntity) eventEntity, stack);
        }
    }
}