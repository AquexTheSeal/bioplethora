package io.github.bioplethora.blocks.api.events;

import io.github.bioplethora.blocks.api.events.hooks.GrylynenSpawnEvent;
import io.github.bioplethora.blocks.api.events.hooks.TrueDefenseHurtEvent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;

public class BPHooks {

    public static boolean onTrueDefenseHurt(LivingEntity entity, DamageSource src) {
        return MinecraftForge.EVENT_BUS.post(new TrueDefenseHurtEvent(entity, src));
    }

    public static boolean onGrylynenSpawn(PlayerEntity player) {
        return MinecraftForge.EVENT_BUS.post(new GrylynenSpawnEvent(player));
    }
}
