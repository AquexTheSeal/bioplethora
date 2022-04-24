package io.github.bioplethora.network;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.network.functions.LeftSwingPacket;
import io.github.bioplethora.network.functions.RightSwingPacket;
import io.github.bioplethora.network.keybindings.VerticalMountDownPacket;
import io.github.bioplethora.network.keybindings.VerticalMountUpPacket;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class BPNetwork {

    public static String NETWORK_VERSION = "0.1.0";
    private static int packetIndex = 0;

    public static final SimpleChannel CHANNEL = NetworkRegistry
            .newSimpleChannel(new ResourceLocation(Bioplethora.MOD_ID, "network"), () -> NETWORK_VERSION,
                    version -> version.equals(NETWORK_VERSION), version -> version.equals(NETWORK_VERSION));

    public static void initializeNetwork() {
        CHANNEL.registerMessage(packetIndex++, VerticalMountUpPacket.class, VerticalMountUpPacket::encode, VerticalMountUpPacket::decode, VerticalMountUpPacket::verticalUp);
        CHANNEL.registerMessage(packetIndex++, VerticalMountDownPacket.class, VerticalMountDownPacket::encode, VerticalMountDownPacket::decode, VerticalMountDownPacket::verticalDown);

        CHANNEL.registerMessage(packetIndex++, LeftSwingPacket.class, LeftSwingPacket::encode, LeftSwingPacket::decode, LeftSwingPacket::leftClickTrigger);
        CHANNEL.registerMessage(packetIndex++, RightSwingPacket.class, RightSwingPacket::encode, RightSwingPacket::decode, RightSwingPacket::rightClickTrigger);
    }
}
