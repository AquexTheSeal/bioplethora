package io.github.bioplethora.keybindings;

import io.github.bioplethora.Bioplethora;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class BPNetwork {

    public static String NETWORK_VERSION = "0.1.0";

    public static final SimpleChannel CHANNEL = NetworkRegistry
            .newSimpleChannel(new ResourceLocation(Bioplethora.MOD_ID, "network"), () -> NETWORK_VERSION,
                    version -> version.equals(NETWORK_VERSION), version -> version.equals(NETWORK_VERSION));

    public static void initializeNetwork() {
        CHANNEL.registerMessage(0, VerticalMountUpPacket.class, VerticalMountUpPacket::encode, VerticalMountUpPacket::decode, VerticalMountUpPacket::verticalUp);
        CHANNEL.registerMessage(1, VerticalMountDownPacket.class, VerticalMountDownPacket::encode, VerticalMountDownPacket::decode, VerticalMountDownPacket::verticalDown);
    }
}
