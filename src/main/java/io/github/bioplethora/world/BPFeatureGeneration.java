package io.github.bioplethora.world;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.world.feature_generation.FleignariteRemainsClusterFeature;
import io.github.bioplethora.world.feature_generation.PendentVinesFeature;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Bioplethora.MOD_ID)
public class BPFeatureGeneration {

    @SubscribeEvent
    public static void generateFeatures(final BiomeLoadingEvent event) {
        FleignariteRemainsClusterFeature.generateCluster(event);
        PendentVinesFeature.generateVines(event);
    }
}
