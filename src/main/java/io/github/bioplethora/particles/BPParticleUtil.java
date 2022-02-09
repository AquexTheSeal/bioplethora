package io.github.bioplethora.particles;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.registry.BioplethoraParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleManager;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Bioplethora.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BPParticleUtil {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerParticles(ParticleFactoryRegisterEvent event) {

        ParticleManager engine = Minecraft.getInstance().particleEngine;

        engine.register(BioplethoraParticles.WIND_POOF.get(), WindPoofParticle.Factory::new);
        engine.register(BioplethoraParticles.NIGHT_GAZE.get(), NightGazeParticle.Factory::new);
    }
}
