package io.github.bioplethora.particles;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.registry.BPParticles;
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

        engine.register(BPParticles.WIND_POOF.get(), WindPoofParticle.Factory::new);
        engine.register(BPParticles.NIGHT_GAZE.get(), NightGazeParticle.Factory::new);
        engine.register(BPParticles.ANTIBIO_SPELL.get(), AntibioSpellParticle.Factory::new);
        engine.register(BPParticles.TRUE_DEFENSE_CLASH.get(), TrueDefenseClashParticle.Factory::new);
        engine.register(BPParticles.KINGS_ROAR.get(), KingsRoarParticle.Factory::new);
        engine.register(BPParticles.FROSTBITE_EYE.get(), FrostbiteEyeParticle.Factory::new);
        engine.register(BPParticles.SHACHATH_CLASH_INNER.get(), ShachathClashInnerParticle.Factory::new);
        engine.register(BPParticles.SHACHATH_CLASH_OUTER.get(), ShachathClashOuterParticle.Factory::new);

        engine.register(BPParticles.CAERULWOOD_LEAF.get(), FallingLeavesParticle.Factory::new);
        engine.register(BPParticles.PINK_ENIVILE_LEAF.get(), FallingLeavesParticle.Factory::new);
        engine.register(BPParticles.RED_ENIVILE_LEAF.get(), FallingLeavesParticle.Factory::new);
    }
}
