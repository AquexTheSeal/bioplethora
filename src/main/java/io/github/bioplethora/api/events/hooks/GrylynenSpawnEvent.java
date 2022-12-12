package io.github.bioplethora.api.events.hooks;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

@Cancelable
public class GrylynenSpawnEvent extends Event {

    PlayerEntity player;

    public GrylynenSpawnEvent(PlayerEntity player) {
        this.player = player;
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    public World getWorld() {
        return player.level;
    }
}
