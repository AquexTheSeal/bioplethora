package io.github.bioplethora.api.events.hooks;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.Cancelable;

@Cancelable
public class TrueDefenseHurtEvent extends LivingEvent {

    private final DamageSource source;

    public TrueDefenseHurtEvent(LivingEntity entity, DamageSource source) {
        super(entity);
        this.source = source;
    }

    public DamageSource getSource() {
        return source;
    }

    public World getWorld() {
        return getEntity().level;
    }
}
