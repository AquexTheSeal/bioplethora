package io.github.bioplethora.integration.playeranimations;

import net.minecraftforge.fml.ModList;

public class PACompatInitializer {

    public static void initalizePlayerAnimationsCompatibility() {
        if (ModList.get().isLoaded("playeranimations")) {
        }
    }
}
