package io.github.bioplethora.event;

import io.github.bioplethora.config.BioplethoraConfig;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PlayerJoinEvent {

    @SubscribeEvent
    public static void onEntitySpawned(EntityJoinWorldEvent event) {
        if (BioplethoraConfig.COMMON.hellMode.get()) {
            if (((event.getEntity() instanceof PlayerEntity) || (event.getEntity() instanceof ServerPlayerEntity))) {
                if (event.getEntity() instanceof PlayerEntity && !event.getEntity().level.isClientSide()) {
                    ((PlayerEntity) event.getEntity()).displayClientMessage(new StringTextComponent("\u00A7cWelcome to the Bioplethora Hell Mode. Objective is to survive."), (false));
                }
            }
        }
    }
}