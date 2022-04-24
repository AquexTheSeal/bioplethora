package io.github.bioplethora.network.functions;

import io.github.bioplethora.event.ServerWorldEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Off-Hand combat integration
 */
public class RightSwingPacket {

    public RightSwingPacket() {
    }

    public static void rightClickTrigger(RightSwingPacket message, Supplier<NetworkEvent.Context> context) {
        context.get().setPacketHandled(true);
        PlayerEntity player = context.get().getSender();
        if(player != null) {
            ServerWorldEvents.hitHandler(player, player.getItemInHand(Hand.OFF_HAND));
        }
    }

    public static void encode(RightSwingPacket message, PacketBuffer buffer) {
    }

    public static RightSwingPacket decode(PacketBuffer buffer) {
        return new RightSwingPacket();
    }
}
