package io.github.bioplethora.network.functions;

import io.github.bioplethora.event.ServerWorldEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class LeftSwingPacket {

    public LeftSwingPacket() {
    }

    public static void leftClickTrigger(LeftSwingPacket message, Supplier<NetworkEvent.Context> context) {
        context.get().setPacketHandled(true);
        PlayerEntity player = context.get().getSender();
        if(player != null) {
            ServerWorldEvents.hitHandler(player, player.getItemInHand(Hand.MAIN_HAND));
        }
    }

    public static void encode(LeftSwingPacket message, PacketBuffer buffer) {
    }

    public static LeftSwingPacket decode(PacketBuffer buffer) {
        return new LeftSwingPacket();
    }
}
