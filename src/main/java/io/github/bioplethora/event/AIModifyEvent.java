package io.github.bioplethora.event;

import io.github.bioplethora.entity.ai.goals.BPAvoidEntityGoal;
import io.github.bioplethora.entity.creatures.EurydnEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.ZombifiedPiglinEntity;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class AIModifyEvent {

    @SubscribeEvent
    public static void onEntitySpawn(EntityJoinWorldEvent event) {
        World world = event.getWorld();
        Entity entity = event.getEntity();

        if (!(entity instanceof MobEntity)) return;

        MobEntity mob = ((MobEntity) event.getEntity());

        if (mob instanceof ZombifiedPiglinEntity) {
            mob.goalSelector.addGoal(2, new BPAvoidEntityGoal<>(mob, EurydnEntity.class, 6.0F, 1.0D, 1.2D));
        }
    }
}
