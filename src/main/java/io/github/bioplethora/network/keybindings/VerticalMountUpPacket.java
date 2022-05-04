package io.github.bioplethora.network.keybindings;

import io.github.bioplethora.entity.IVerticalMount;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class VerticalMountUpPacket {

    public int key;

    public VerticalMountUpPacket() {
    }

    public VerticalMountUpPacket(int key) {
        this.key = key;
    }

    public static void encode(VerticalMountUpPacket message, PacketBuffer buffer) {
        buffer.writeInt(message.key);
    }

    public static VerticalMountUpPacket decode(PacketBuffer buffer) {
        return new VerticalMountUpPacket(buffer.readInt());
    }

    public static void verticalUp(VerticalMountUpPacket message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayerEntity player = context.getSender();
            World world = player.getCommandSenderWorld();
            Entity riddenEntity = player.getVehicle();

            if (riddenEntity instanceof IVerticalMount) {
                if (((IVerticalMount) riddenEntity).shouldVerticalMove()) {
                    riddenEntity.setDeltaMovement(riddenEntity.getDeltaMovement().x(), 0.3D, riddenEntity.getDeltaMovement().z());
                }
            }
        });
        context.setPacketHandled(true);
    }
}
