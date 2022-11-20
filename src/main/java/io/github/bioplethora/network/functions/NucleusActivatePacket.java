package io.github.bioplethora.network.functions;

import io.github.bioplethora.blocks.tile_entities.AlphanumNucleusBlock;
import io.github.bioplethora.event.ServerWorldEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Off-Hand combat integration
 */
public class NucleusActivatePacket {

    public int x, y, z;

    public NucleusActivatePacket(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static void setState(NucleusActivatePacket message, Supplier<NetworkEvent.Context> context) {
        context.get().setPacketHandled(true);
        ((ITickableTileEntity) Minecraft.getInstance().level.getBlockEntity(new BlockPos(message.x, message.y, message.z))).tick();

    }

    public static void encode(NucleusActivatePacket message, PacketBuffer buffer) {
        buffer.writeInt(message.x);
        buffer.writeInt(message.y);
        buffer.writeInt(message.z);
    }

    public static NucleusActivatePacket decode(PacketBuffer buffer) {
        return new NucleusActivatePacket(buffer.readInt(), buffer.readInt(), buffer.readInt());
    }
}
