package io.github.bioplethora.network.keybindings;

import io.github.bioplethora.entity.IVerticalMount;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class VerticalMountDownPacket {

    public int key;

    public VerticalMountDownPacket() {
    }

    public VerticalMountDownPacket(int key) {
        this.key = key;
    }

    public static void encode(VerticalMountDownPacket message, PacketBuffer buffer) {
        buffer.writeInt(message.key);
    }

    public static VerticalMountDownPacket decode(PacketBuffer buffer) {
        return new VerticalMountDownPacket(buffer.readInt());
    }

    public static void verticalDown(VerticalMountDownPacket message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayerEntity player = context.getSender();
            World world = player.getCommandSenderWorld();
            Entity riddenEntity = player.getVehicle();

            if (riddenEntity instanceof IVerticalMount) {
                if (((IVerticalMount) riddenEntity).shouldVerticalMove()) {
                    riddenEntity.setDeltaMovement(riddenEntity.getDeltaMovement().x(), -0.3D, riddenEntity.getDeltaMovement().z());
                }
            }
        });
        context.setPacketHandled(true);
    }
}
